use Motion;
go

insert into Konfiguracja_pomiarowa ( Nazwa, Rodzaj) values ('LB_1', 'emg');
insert into Konfiguracja_pomiarowa ( Nazwa, Rodzaj) values ('LB_2', 'emg');
insert into Konfiguracja_pomiarowa ( Nazwa, Rodzaj) values ('LB_3', 'emg');
insert into Konfiguracja_pomiarowa ( Nazwa, Rodzaj) values ('UB_1', 'emg');


insert into Sesja_Konfiguracja_pomiarowa ( IdKonfiguracja_pomiarowa, IdSesja )
select kp.IdKonfiguracja_pomiarowa, s.IdSesja from Konfiguracja_pomiarowa kp, Sesja s where kp.Nazwa= 'LB_3' and s.Nazwa = '2011-02-24-B0001-S01'


alter procedure feed_measurement_conf ( @session_name as varchar(20), @mc_kind as varchar(50), @mc_name as varchar(50), @result int output )
as
begin
	set @result = 0;
	declare @mcid int;
	set @mcid = 0;
	declare @sid int;
	set @sid = 0;
	select @mcid = IdKonfiguracja_pomiarowa from Konfiguracja_pomiarowa where Rodzaj = @mc_kind and Nazwa = @mc_name;
	if (@mcid = 0)
	begin
		set @result = 2;
		return;
	end;
	select @sid = IdSesja from Sesja where Nazwa = @session_name;
	if (@sid = 0)
	begin
		set @result = 1;
		return;
	end;
	if (exists (select * from Sesja_Konfiguracja_pomiarowa where IdSesja = @sid and IdKonfiguracja_pomiarowa = @mcid))
	begin
		set @result = 3;
		return;
	end;
	insert into Sesja_Konfiguracja_pomiarowa ( IdKonfiguracja_pomiarowa, IdSesja ) values ( @mcid, @sid );
	return;
end;
		


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
	(select kp.Nazwa from Konfiguracja_pomiarowa kp join Sesja_Konfiguracja_pomiarowa skp on kp.IdKonfiguracja_pomiarowa = skp.IdKonfiguracja_pomiarowa where skp.IdSesja = Session.IdSesja) as EMGConf,
	(select Name, Value from list_session_attributes ( IdSesja ) A FOR XML AUTO, TYPE ) Attrs, 
	(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed", DATALENGTH(p.Plik) "@Size",
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
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
	(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed", DATALENGTH(p.Plik) "@Size",
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
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

exec get_shallow_copy 'habela'