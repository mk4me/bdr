-- Mail service configuration commands

--sp_CONFIGURE 'show advanced', 1
--GO
--RECONFIGURE
--GO
--sp_CONFIGURE 'Database Mail XPs', 1
--GO

--use msdb
--go
--exec sp_send_dbmail @profile_name='HMDB_Mail',
--@recipients='habela@pjwstk.edu.pl',
--@subject='Test message',
--@body='Test message' ;

--SELECT *
--FROM sysmail_mailitems
--GO
--SELECT *
--FROM sysmail_log
--GO 



use Motion;

alter table Uzytkownik add Email varchar(50) not null default 'NO_EMAIL';
go
alter table Uzytkownik add Kod_Aktywacji varchar(10);
go
alter table Uzytkownik add Status int not null default 0;
go

update Uzytkownik set Haslo = HashBytes('SHA1','Admin1');



create procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass);
if (@c = 1) set @res = 1; else set @res = 0;
end;
go


-- Error codes:
-- 1 login already in use
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
create procedure create_user_account(@user_login varchar(30), @user_password varchar(20),  @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @activation_code varchar(10), @result int OUTPUT)
as
begin

	declare @email_title as varchar (120);
	declare @email_body as varchar (200);
	set @result = 0;

	if ( LEN(@user_login)=0 or LEN(@user_password)=0 or LEN(@user_email) = 0 )
		begin
			set @result = 3;
			return;
		end;

	if exists(select * from Uzytkownik where Login = @user_login)
		begin
			set @result = 1;
			return;
		end;
	if exists(select * from Uzytkownik where Email = @user_email)
		begin
			set @result = 2;
			return;
		end;

	insert into Uzytkownik ( Login, Haslo, Email, Imie, Nazwisko, Kod_aktywacji ) values ( @user_login, HashBytes('SHA1',@user_password), @user_email, @user_first_name, @user_last_name, @activation_code );

	set @email_title = 'Human Motion Database account activation for ' + @user_login;
	set @email_body = 'Your activation code for login '+@user_login +' is: ' + @activation_code +' . Please visit the webpage https://v21.pjwstk.edu.pl/HMDB/UserAccountCreation.aspx to authenticate and perform your account activatio using this code.';

	exec msdb.dbo.sp_send_dbmail @profile_name='HMDB_Mail',
	@recipients=@user_email,
	@subject= @email_title,
	@body= @email_body;
	return 0;
end;
go

-- Error codes:
-- 1 authentication negative
create procedure activate_user_account(@user_login varchar(30), @user_password varchar(20),  @activation_code varchar(10), @result int OUTPUT)
as
begin
	set @result = 0;

	if not exists(select * from Uzytkownik where Login = @user_login and Haslo = HashBytes('SHA1',@user_password) and Kod_aktywacji = @activation_code )
		begin
			set @result = 1;
			return;
		end;
	update Uzytkownik  set Status = 1  where Login = @user_login and Haslo = HashBytes('SHA1',@user_password) and Kod_aktywacji = @activation_code;

	return 0;
end;
go

select * from Uzytkownik


-- Error codes:
-- 1 authentication failed
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
create procedure update_user_account(@user_login varchar(30), @user_password varchar(20),  @user_new_password varchar(20), @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @result int OUTPUT)
as
begin

	set @result = 0;

	if ( LEN(@user_login)=0 or LEN(@user_password)=0 )
		begin
			set @result = 3;
			return;
		end;

	if not exists(select * from Uzytkownik where Login = @user_login and Haslo = HashBytes('SHA1',@user_password) and Status > 0 )
		begin
			set @result = 1;
			return;
		end;
	if exists(select * from Uzytkownik where Login != @user_login and Email = @user_email)
		begin
			set @result = 2;
			return;
		end;

	if ( @user_first_name != '-nochange-' )
	begin
		update Uzytkownik 
		set Email = @user_email, Imie = @user_first_name, Nazwisko = @user_last_name where Login = @user_login;
	end;	
	if ( @user_new_password != '-nochange-' )
	begin
		update Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password) where Login = @user_login;
	end;
	
	return @result;
end;
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



-- last rev. 2012-02-22
create function f_time_stamp()
returns datetime
as
begin
return ( select max(ts) as time_stamp from
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
	) as q1 );
end
go