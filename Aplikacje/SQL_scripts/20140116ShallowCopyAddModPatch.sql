use Motion;
go

alter trigger tr_Plik_Update on Plik
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba t on i.IdProba = t.IdProba
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
	if update(Plik)
	update Plik
	set Zmieniony = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik;
end
go

alter table Performer
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Konfiguracja_performera
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

-- Do usuniêcia z bazy testowej
alter table Wartosc_atrybutu_konfiguracji_performera
drop column Utworzono;
go

alter table Wartosc_atrybutu_sesji
drop column Utworzono;
go

alter table Wartosc_atrybutu_konfiguracji_pomiarowej
drop column Utworzono;
go

alter table Wartosc_atrybutu_performera
drop column Utworzono;
go

alter table Wartosc_atrybutu_pliku
drop column Utworzono;
go

alter table Wartosc_atrybutu_proby
drop column Utworzono;
go
-- // Do usuniêcia z bazy testowej

alter table Sesja
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Konfiguracja_pomiarowa
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Plik
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Proba
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Grupa_sesji
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Rodzaj_ruchu
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Laboratorium
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go

alter table Grupa_atrybutow
 add Utworzono datetime default '2013-12-01 00:00:00.000' not null; --
go



-- last rev. 2011-10-17
create trigger tr_Sesja_Insert on Sesja
for insert
as
begin
	update Sesja
	set Utworzono = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

-- last rev. 2011-10-17
create trigger tr_Konfiguracja_pomiarowa_Insert on Konfiguracja_pomiarowa
for insert
as
begin
	update Konfiguracja_pomiarowa
	set Utworzono = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

-- last rev. 2014-01-16
create trigger tr_Plik_Insert on Plik
for insert
as
begin
	update Plik
	set Utworzono = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
	update Plik
	set Zmieniony = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik;
end
go

-- last rev. 2011-10-17
create trigger tr_Proba_Insert on Proba
for insert
as
begin
	update Proba
	set Utworzono = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go

-- last rev. 2011-10-24
create trigger tr_Grupa_sesji_Insert on Grupa_sesji
for insert
as
begin
	update Grupa_sesji
	set Utworzono = getdate()
	from inserted i join Grupa_sesji gs on i.IdGrupa_sesji = gs.IdGrupa_sesji
end
go

-- last rev. 2011-10-24
create trigger tr_Rodzaj_ruchu_Insert on Rodzaj_ruchu
for insert
as
begin
	update Rodzaj_ruchu
	set Utworzono = getdate()
	from inserted i join Rodzaj_ruchu rr on i.IdRodzaj_ruchu = rr.IdRodzaj_ruchu
end
go

-- last rev. 2011-10-24
create trigger tr_Laboratorium_Insert on Laboratorium
for insert
as
begin
	update Laboratorium
	set Utworzono = getdate()
	from inserted i join Laboratorium l on i.IdLaboratorium = l.IdLaboratorium
end
go

-- last rev. 2011-10-24
create trigger tr_Grupa_atrybutow_Insert on Grupa_atrybutow
for insert
as
begin
	update Grupa_atrybutow
	set Utworzono = getdate()
	from inserted i join Grupa_atrybutow ga on i.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
end
go

/*
declare @res as int;
declare @sid as int;
exec create_session 'habela',1, 'jump', '2013-10-10', '2013-10-10-B0005-S02', 'sample2', 'desc', @sid OUTPUT, @res OUTPUT 
select @sid

select * from Proba
select * from Sesja

insert into Proba ( IdSesja, Opis_proby, Nazwa)
                                    values (2, 'sample desc', 'trial 2-1' )
                                    
select * from Plik
                                    
insert into Plik ( Nazwa_pliku, IdSesja, Plik, Opis_pliku ) values ('Session2_file2', 2, 0xFFFE, 'opis pliku2')

insert into Plik ( Nazwa_pliku, IdProba, Plik, Opis_pliku ) values ('Trial1_file', 1, 0xFFFE, 'opis pliku2')

update Plik set Plik = 0xFFFF where IdSesja = 2
*/

-- last rev: 2014-01-14 ----
alter function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s where s.Publiczna = 1 or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s 
 where s.IdSesja in ( select * from user_group_assigned_session_ids( @user_id) ) )  
go

alter function user_accessible_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from user_accessible_sessions( dbo.identify_user( @user_login )) s
go


-- last rev. 2014-01-16
alter procedure get_shallow_copy_branches_increment @user_login varchar(30), @since datetime
as
with
UAS as (select * from dbo.user_accessible_sessions_by_login (@user_login) Session ),
UAGA as (select * from Sesja_grupa_sesji GroupAssignment where exists(select * from UAS where UAS.IdSesja = GroupAssignment.IdSesja and UAS.Ostatnia_zmiana > @since)),
UAT as (select * from Proba Trial where exists (select * from UAS where UAS.IdSesja = Trial.IdSesja)),
UAP as (select * from Performer Performer where exists (select * from Konfiguracja_performera KP where exists (select * from UAS where UAS.IdSesja = KP.IdSesja) )),
UAPC as (select * from Konfiguracja_performera PerformerConf where exists(select * from UAS where UAS.IdSesja = PerformerConf.IdSesja))
select
(select
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
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja and  Ostatnia_zmiana > @since and Utworzono < @since
	for XML PATH('File'), TYPE) as ModifiedFiles,
	(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja and  Utworzono > @since
	for XML PATH('File'), TYPE) as AddedFiles
	from UAS Session where Ostatnia_zmiana > @since and Utworzono < @since order by Nazwa for XML AUTO, TYPE
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
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
		from Plik p 
		where  Ostatnia_zmiana > @since and Utworzono < @since and
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as ModifiedFiles,
	(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
		from Plik p 
		where  Utworzono > @since and
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as AddedFiles
	from UAT Trial where Ostatnia_zmiana > @since and Utworzono < @since order by Nazwa FOR XML AUTO, TYPE 
 ) Trials,
 (select 
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_attributes ( IdPerformer ) A FOR XML AUTO, TYPE ) Attrs
	from UAP Performer where Ostatnia_zmiana > @since FOR XML AUTO, TYPE 
 ) Performers,
 (select 
	IdKonfiguracja_performera as PerformerConfID,
	IdSesja as SessionID,
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_configuration_attributes( IdKonfiguracja_performera ) A FOR XML AUTO, TYPE ) Attrs
	from UAPC Performer  where Ostatnia_zmiana > @since and Utworzono < @since FOR XML AUTO, TYPE 
 ) PerformerConfs
 for XML RAW ('Modified'), TYPE),
(select
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
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja
	for XML PATH('File'), TYPE) as AddedFiles
	from UAS Session where Utworzono > @since order by Nazwa for XML AUTO, TYPE
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
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
		from Plik p 
		where
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as AddedFiles
	from UAT Trial where Utworzono > @since order by Nazwa FOR XML AUTO, TYPE 
 ) Trials,
 (select 
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_attributes ( IdPerformer ) A FOR XML AUTO, TYPE ) Attrs
	from UAP Performer where Utworzono > @since FOR XML AUTO, TYPE 
 ) Performers,
 (select 
	IdKonfiguracja_performera as PerformerConfID,
	IdSesja as SessionID,
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_configuration_attributes( IdKonfiguracja_performera ) A FOR XML AUTO, TYPE ) Attrs
	from UAPC Performer  where Utworzono > @since FOR XML AUTO, TYPE 
 ) PerformerConfs
 for XML RAW ('Added'), TYPE)
 for XML RAW ('ShallowCopyBranches'), TYPE;
go

alter procedure get_shallow_copy @user_login varchar(30)
as
with
UAS as (select * from dbo.user_accessible_sessions_by_login (@user_login) Session ),
UAGA as (select * from Sesja_grupa_sesji GroupAssignment where exists(select * from UAS where UAS.IdSesja = GroupAssignment.IdSesja)),
UAT as (select * from Proba Trial where exists (select * from UAS where UAS.IdSesja = Trial.IdSesja)),
UAP as (select * from Performer Performer where exists (select * from Konfiguracja_performera KP where exists (select * from UAS where UAS.IdSesja = KP.IdSesja) )),
UAPC as (select * from Konfiguracja_performera PerformerConf where exists(select * from UAS where UAS.IdSesja = PerformerConf.IdSesja))
select
 dbo.f_time_stamp() LastModified,
(select 
	IdSesja as SessionID,
	IdUzytkownik as UserID,
	IdLaboratorium as LabID,
	dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
	Data as SessionDate,
	Nazwa as SessionName,
	Tagi as Tags,
	Opis_sesji as SessionDescription,
	Publiczna as P,
	PublicznaZapis as PW,
	(select kp.Nazwa from Konfiguracja_pomiarowa kp join Sesja_Konfiguracja_pomiarowa skp on kp.IdKonfiguracja_pomiarowa = skp.IdKonfiguracja_pomiarowa where skp.IdSesja = Session.IdSesja) as EMGConf,
	(select Name, Value from list_session_attributes ( IdSesja ) A FOR XML AUTO, TYPE ) Attrs, 
	(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed", DATALENGTH(p.Plik) "@Size",
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja
	for XML PATH('File'), TYPE) as Files
	from UAS Session order by Nazwa for XML AUTO, TYPE
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
	(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed", DATALENGTH(p.Plik) "@Size",
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
		from Plik p 
		where 
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as Files
	from UAT Trial order by Nazwa FOR XML AUTO, TYPE 
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