use Motion;
go

-- TODO: dodac indeksy dla dat

alter table Konfiguracja_performera add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Konfiguracja_pomiarowa add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Performer add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Plik add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Proba add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Sesja add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Plik add Zmieniony datetime;
go

create index X3Konfiguracja_performera on Konfiguracja_performera
 (
        Ostatnia_zmiana
 )
go

create index X2Konfiguracja_pomiarowa on Konfiguracja_pomiarowa
 (
        Ostatnia_zmiana
 )
go

create index X1Performer on Performer
 (
        Ostatnia_zmiana
 )
go

create index X4Plik on Plik
 (
        Ostatnia_zmiana
 )
go

create index X5Plik on Plik
 (
        Zmieniony
 )
go

create index X3Proba on Proba
 (
        Ostatnia_zmiana
 )
go

create index X4Sesja on Sesja
 (
        Ostatnia_zmiana
 )
go

create trigger tr_Wartosc_atrybutu_konfiguracji_performera_Update on Wartosc_atrybutu_konfiguracji_performera
for update, insert
as
begin
	update Konfiguracja_performera
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_performera kp on i.IdKonfiguracja_performera = kp.IdKonfiguracja_performera
end
go

create trigger tr_Wartosc_atrybutu_sesji_Update on Wartosc_atrybutu_sesji
for update, insert
as
begin
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

create trigger tr_Wartosc_atrybutu_konfiguracji_pomiarowej_Update on Wartosc_atrybutu_konfiguracji_pomiarowej
for update, insert
as
begin
	update Konfiguracja_pomiarowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

create trigger tr_Wartosc_atrybutu_performera_Update on Wartosc_atrybutu_performera
for update, insert
as
begin
	update Performer
	set Ostatnia_zmiana = getdate()
	from inserted i join Performer p on i.IdPerformer = p.IdPerformer
end
go

create trigger tr_Wartosc_atrybutu_pliku_Update on Wartosc_atrybutu_pliku
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
end
go

create trigger tr_Wartosc_atrybutu_proby_Update on Wartosc_atrybutu_proby
for update, insert
as
begin
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go

create trigger tr_Sesja_Update on Sesja
for update, insert
as
begin
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

create trigger tr_Konfiguracja_pomiarowa_Update on Konfiguracja_pomiarowa
for update, insert
as
begin
	update Konfiguracja_pomiarowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

create trigger tr_Plik_Update on Plik
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
	if( COLUMNS_UPDATED() = 0x20)
	update Plik
	set Zmieniony = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik;

end
go

create trigger tr_Proba_Update on Proba
for update, insert
as
begin
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go


create procedure time_stamp
as
(
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Konfiguracja_performera 
	union
	select max(Ostatnia_zmiana) as ts from Konfiguracja_pomiarowa 
	union
	select max(Ostatnia_zmiana) as ts from Performer
	union 
	select max(Ostatnia_zmiana) as ts from Plik 
	union
	select max(Ostatnia_zmiana) as ts from Proba 
	union
	select max(Ostatnia_zmiana) as ts from Sesja
	union
	select max(Zmieniony) as ts from Plik
	) as q1
)



alter procedure get_shallow_copy @user_login varchar(30)
as
with
UAS as (select * from dbo.user_accessible_sessions_by_login (@user_login) Session ),
UAGA as (select * from Sesja_grupa_sesji GroupAssignment where exists(select * from UAS where UAS.IdSesja = GroupAssignment.IdSesja)),
UAT as (select * from Proba Trial where exists (select * from UAS where UAS.IdSesja = Trial.IdSesja)),
UAP as (select * from Performer Performer where exists (select * from Konfiguracja_performera KP where exists (select * from UAS where UAS.IdSesja = KP.IdSesja) )),
UAPC as (select * from Konfiguracja_performera PerformerConf where exists(select * from UAS where UAS.IdSesja = PerformerConf.IdSesja))
select
(select 
	IdSesja as SessionID,
	IdUzytkownik as UserID,
	IdLaboratorium as LabID,
	dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
	Data as SessionDate,
	Nazwa as SessionName,
	Tagi as Tags,
	Opis_sesji as SessionDescription,
	(select Name, Value from list_session_attributes ( IdSesja ) A FOR XML AUTO, TYPE ) Attrs, 
	(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja
	for XML PATH('File'), TYPE) as Files
	from UAS Session for XML AUTO, TYPE
 ) Sessions,
 (select 
	IdSesja as SessionID,
	IdGrupa_sesji as SessionGroupID 
	from UAGA GroupAssignment for XML AUTO, TYPE
 ) GroupAssignments,
 (select 
	IdProba as TrialID,
	IdSesja as SessionID,
	Nazwa as TrialName,
	Opis_proby as TrialDescription,
	(select Name, Value from list_trial_attributes ( IdProba ) A FOR XML AUTO, TYPE ) Attrs,
	(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
		from Plik p 
		where 
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as Files
	from UAT Trial FOR XML AUTO, TYPE 
 ) Trials,
 (select 
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_attributes ( IdPerformer ) A FOR XML AUTO, TYPE ) Attrs
	from UAP Performer FOR XML AUTO, TYPE 
 ) Performers,
 (select 
	IdKonfiguracja_performera as PerformerConfID,
	IdSesja as SessionID,
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_configuration_attributes( IdKonfiguracja_performera ) A FOR XML AUTO, TYPE ) Attrs
	from UAPC Performer FOR XML AUTO, TYPE 
 ) PerformerConfs
 for XML RAW ('ShallowCopy'), TYPE;
go



-- ================================================================================

update Sesja set Tagi = 'sample1'

select * from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('x-AtrybutySesji', 'session');
go

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'x-AtrybutySesji' and Opisywana_encja='session'), 'SomeNumber', 'integer', 0, 'nonNegativeDecimal', null) 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'x-AtrybutySesji' and Opisywana_encja='session'), 'SomeText', 'string', 0, 'shortString', null) 


insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('x-AtrybutyKonfPom', 'measurement_conf');
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'x-AtrybutyKonfPom' and Opisywana_encja='measurement_conf'), 'SomeNumber', 'integer', 0, 'nonNegativeDecimal', null) 
go

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('x-AtrybutyPerf', 'performer');
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'x-AtrybutyPerf' and Opisywana_encja='performer'), 'SomeNumber', 'integer', 0, 'nonNegativeDecimal', null) 
go

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('x-AtrybutyPliku', 'file');
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'x-AtrybutyPliku' and Opisywana_encja='file'), 'SomeNumber', 'integer', 0, 'nonNegativeDecimal', null) 
go


insert into Konfiguracja_pomiarowa ( Nazwa, Opis, Rodzaj ) values ( 'Nexus1', 'Podstawowa', 'mocap');

update Konfiguracja_pomiarowa set Opis = 'Motion - podstawowa'

declare @res int;
exec set_trial_attribute 1, 'SkeletonFile', 1, 0, @res OUTPUT;
select @res;

create procedure set_session_attribute (@sess_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )

insert into Performer ( IdPerformer ) values ( 2 )

insert into Konfiguracja_performera ( IdPerformer, IdSesja ) values ( 2, 1 )

insert into Proba ( IdSesja, Opis_proby, Nazwa ) values ( 1, 'Opis proby 1', 'T01'); 

update Proba set Nazwa = 'S01-T01';

select * from Wartosc_atrybutu_sesji

select * from Sesja

insert into Plik ( IdSesja, Opis_pliku, Plik, Nazwa_pliku ) values ( 1, 'Opis pliku', (select binary_data from openrowset( bulk 'c:\MOMReinstall.log', SINGLE_BLOB) as F(binary_data)), 'DrugiA.log');

update Plik set Plik = (select binary_data from openrowset( bulk 'c:\MOMReinstall.log', SINGLE_BLOB) as F(binary_data));


update Plik set Nazwa_pliku = 'Testowy.log'

select * from Plik