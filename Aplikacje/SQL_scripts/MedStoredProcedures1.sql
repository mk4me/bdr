use Motion_Med;
go

-- Shallow copy retrieval
-- ==========================

-- last rev. 2011-10-24

--MedicalData (
--  Patient ( ...
--    Disorder (... )*
--    Examination (... )*
--  )*
--)

--insert into Badanie ( IdGrupa_badan, IdPacjent, Data, Opis, Notatki, IdSesja ) values ( 1, 1, '2011-11-11 12:12:12:000', 'Desc', 'Notes', null )
--insert into Badanie ( IdGrupa_badan, IdPacjent, Data, Opis, Notatki, IdSesja ) values ( 1, 2, '2011-11-11 12:12:12:000', 'Desc', 'Notes', 1 )
--insert into Badanie ( IdGrupa_badan, IdPacjent, Data, Opis, Notatki, IdSesja ) values ( 1, 2, '2011-11-12 12:12:12:000', 'Desc', 'Notes', 2 )



-- last mod. 2012-03-19
create procedure m_get_patient_list ( @user_login as varchar(30) )
as
with
P as (select * from user_accessible_patients_by_login (@user_login ) Patient ),
DO as (select * from Pacjent_Jednostka_chorobowa DisorderOccurence where exists(select * from P where P.IdPacjent = DisorderOccurence.IdPacjent)),
E as (select * from Badanie Examination where exists (select * from P where P.IdPacjent = Examination.IdPacjent))
select
dbo.f_time_stamp() LastModified,
(select
	IdPacjent PatientID,
	Imie FirstName,
	Nazwisko LastName,
	Plec Gender,
	Data_urodzenia BirthDate,
	IdPerformer BDRPerformerID
from P Patient for XML AUTO, TYPE)  Patients,
(select
	IdPacjent PatientID,
	IdJednostka_chorobowa DisorderID,
	Data_diagnozy DiagnosisDate,
	Komentarz Comment,
	case Glowna when 1 then '0' else '1' end Focus
from DO DisorderOccurence for XML AUTO, TYPE) DisorderOccurences,
(select
	IdBadanie ExamID,
	IdPacjent PatientID,
	Data ExamDate,
	Notatki Notes,
	Opis Description,
	IdSesja BDRSessionID,
	IdGrupa_badan ExamTypeID
	from E Examination for XML AUTO, TYPE	
) Examinations,
(select
	(	select
			IdGrupa_badan ExamGroupID,
			Nazwa ExamGroupName
		from Grupa_badan ExamGroup
		for XML AUTO, TYPE
	) ExamGroups,
	(
		select
		IdJednostka_chorobowa DisorderID,
		Nazwa DisorderName
		from Jednostka_chorobowa Disorder
		for XML AUTO, TYPE
	) Disorders
for XML RAW('Dictionaries') , TYPE)
for XML RAW ('MedicalRecords'), TYPE
go


-- last modified 2012-03-06
create trigger tr_Jednostka_chorobowa_Update on Jednostka_chorobowa
for update, insert
as
begin
	update Jednostka_chorobowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Jednostka_chorobowa s on i.IdJednostka_chorobowa = s.IdJednostka_chorobowa
end
go

-- last modified 2012-03-06
create trigger tr_Grupa_badan_Update on Grupa_badan
for update, insert
as
begin
	update Grupa_badan
	set Ostatnia_zmiana = getdate()
	from inserted i join Grupa_badan kp on i.IdGrupa_badan = kp.IdGrupa_badan
end
go

-- last modified 2012-03-06
create trigger tr_Kontekst_badania_Update on Kontekst_badania
for update, insert
as
begin
	update Kontekst_badania
	set Ostatnia_zmiana = getdate()
	from inserted i join Kontekst_badania p on i.IdKontekst_badania = p.IdKontekst_badania
end
go

-- last modified 2012-03-06
create procedure metadata_time_stamp
as
(
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Jednostka_chorobowa 
	union
	select max(Ostatnia_zmiana) as ts from Grupa_badan 
	union
	select max(Ostatnia_zmiana) as ts from Kontekst_badania
	) as q1
)
go


-- last modified 2012-03-06
create function f_metadata_time_stamp()
returns datetime
as
begin
return ( 
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Jednostka_chorobowa 
	union
	select max(Ostatnia_zmiana) as ts from Grupa_badan 
	union
	select max(Ostatnia_zmiana) as ts from Kontekst_badania
	) as q1
 );
end
go


-- last modified 2012-03-06
create procedure get_med_metadata
as
select
dbo.f_metadata_time_stamp() LastModified,
(select 
	IdGrupa_badan as ExamGroupID,
	Nazwa as ExamGroupName
	from Grupa_badan ExamGroup for XML AUTO, TYPE
 ) ExamGroups,
 (select 
	IdJednostka_chorobowa as DisorderID,
	Nazwa as DisorderName
	from Jednostka_chorobowa Disorder for XML AUTO, TYPE
 ) Disorders,
 (select 
	IdKontekst_badania as ExamContextID,
	Nazwa as ContextName
	from Kontekst_badania ExamContext for XML AUTO, TYPE
 ) ExamContexts
 for XML RAW ('MedMetadata'), TYPE;
go


-- Password related
-- ================


-- last mod. 2012-03-19
create procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass) and Status > 0;
if (@c = 1) set @res = 1; else set @res = 0;
end;
go

-- last mod. 2012-01-14 // CZY NADAL POTRZEBNE?
create procedure create_user(@name varchar(30), @surname varchar(50),  @login varchar(50), @bdr_login varchar(50), @pass varchar(25), @res int OUTPUT)
as
begin
if exists(select * from Uzytkownik where Login = @login)
	begin
		set @res = 1;
		return;
	end;
insert into Uzytkownik ( Imie, Nazwisko, Login, LoginBDR, Haslo ) values ( @name, @surname, @login, @bdr_login, HashBytes('SHA1',@pass));
return 0;
end;
go


-- last rev. 2012-04-16
-- Error codes:
-- 1 authentication negative
-- 2 hmdb account activation requested but account missing
create procedure activate_user_account(@user_login varchar(30), @activation_code varchar(10), @hmdb_propagate bit, @result int OUTPUT)
as
begin
	set @result = 0;

	if not exists(select * from Uzytkownik where Login = @user_login and Kod_aktywacji = @activation_code )
		begin
			set @result = 1;
			return;
		end;

	if @hmdb_propagate = 1 and not exists(select * from Motion.dbo.Uzytkownik where Login = @user_login )
		begin
			set @result = 2;
			return;
		end;
		
	
	update Uzytkownik  set Status = 1  where Login = @user_login and Kod_aktywacji = @activation_code;
	if @hmdb_propagate = 1
		begin
			update Motion.dbo.Uzytkownik  set Status = 1  where Login = @user_login and Kod_aktywacji = @activation_code;
		end;

	return 0;
end;
go

-- last rev. 2012-05-04
-- Error codes:
-- 1 authentication negative
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
-- 4 hmdb account activation requested but account missing
create procedure update_user_account(@user_login varchar(30), @user_password varchar(20),  @user_new_password varchar(20), @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @hmdb_propagate bit, @result int OUTPUT)
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

	if @hmdb_propagate = 1 and not exists(select * from Motion.dbo.Uzytkownik where Login = @user_login )
		begin
			set @result = 4;
			return;
		end;

	if ( @user_first_name != '-nochange-' )
	begin
		update Uzytkownik 
		set Email = @user_email, Imie = @user_first_name, Nazwisko = @user_last_name where Login = @user_login;
		if @hmdb_propagate = 1
		begin
		update Motion.dbo.Uzytkownik 
		set Email = @user_email, Imie = @user_first_name, Nazwisko = @user_last_name where Login = @user_login;

		end;		
	end;	
	if ( @user_new_password != '-nochange-' )
	begin
		update Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password) where Login = @user_login;
		if @hmdb_propagate = 1
		begin
			update Motion.dbo.Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password) where Login = @user_login;
		end;
	end;
	
	return @result;
end;
go



-- last rev. 2012-04-16
create procedure get_user ( @user_login varchar(30) )
as
select Login, Imie, Nazwisko, Email
	from Uzytkownik
	where Login = @user_login
go



-- last rev. 2012-04-16
-- Error codes:
-- 1 login already in use
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
-- 4/5 login or email already in use at HMDB
alter procedure create_user_account(@user_login varchar(30), @user_password varchar(20),  @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @activation_code varchar(10), @hmdb_propagate bit, @result int OUTPUT)
as
begin

	declare @email_title as varchar (120);
	declare @email_body as varchar (500);
	declare @link_command as varchar(30);
	
	set @link_command = '';
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

	if @hmdb_propagate = 1 and exists(select * from Motion.dbo.Uzytkownik where Login = @user_login)
		begin
			set @result = 4;
			return;
		end;
	if @hmdb_propagate = 1 and exists(select * from Motion.dbo.Uzytkownik where Email = @user_email)
		begin
			set @result = 5;
			return;
		end;


	insert into Uzytkownik ( Login, Haslo, Email, Imie, Nazwisko, Kod_aktywacji ) values ( @user_login, HashBytes('SHA1',@user_password), @user_email, @user_first_name, @user_last_name, @activation_code );
	
	if @hmdb_propagate = 1
	begin
		insert into Motion.dbo.Uzytkownik ( Login, Haslo, Email, Imie, Nazwisko, Kod_aktywacji ) values ( @user_login, HashBytes('SHA1',@user_password), @user_email, @user_first_name, @user_last_name, @activation_code );
	end

	if @hmdb_propagate = 1
	begin
		set @link_command = '&hmdb=true';
	end;
	set @email_title = 'Human Motion MEDICAL Database account activation for ' + @user_login;
	set @email_body = 'Your account with login '+@user_login+' has been created successfully.'+CHAR(13)
	+' To activate your account use the following link: https://v21.pjwstk.edu.pl/HMDBMed/AccountActivation.aspx?login='+@user_login+'&code='+@activation_code+CHAR(13)+@link_command
	+'Alternatively, use activation code for login '+@user_login +' and activation code ' + @activation_code +' to perform the activation manually '
	+CHAR(13)+'on the webpage https://v21.pjwstk.edu.pl/HMDBMed/UserAccountCreation.aspx or through your client application.';

	exec msdb.dbo.sp_send_dbmail @profile_name='HMDB_Mail',
	@recipients=@user_email,
	@subject= @email_title,
	@body= @email_body;
	return 0;
end;
go



-- last mod. 2012-03-19
-- Error codes:
-- 1 authentication failed
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
alter procedure update_user_account(@user_login varchar(30), @user_password varchar(20),  @user_new_password varchar(20), @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @result int OUTPUT)
as
begin

	set @result = 0;

	if ( LEN(@user_login)=0 or LEN(@user_password)=0 or LEN(@user_email) = 0 )
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

-- last mod. 2012-01-14
create procedure reset_password(@login varchar(30), @old varchar(25), @new varchar(25), @res int OUTPUT )
as
begin
if not exists(select * from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@old))
	begin
		set @res = 1;
		return;
	end;
update Uzytkownik set Haslo = HashBytes('SHA1',@new) where Login = @login and Haslo = HashBytes('SHA1',@old);
return 0;
end;
go


-- last mod. 2012-03-19
create procedure get_user_roles @login varchar(30)
as
select gu.Nazwa from Uzytkownik u join Uzytkownik_grupa_uzytkownikow ugu on u.IdUzytkownik = ugu.IdUzytkownik join Grupa_uzytkownikow gu on ugu.IdGrupa_uzytkownikow = gu.IdGrupa_uzytkownikow
where u.Login = @login
go

-- last rev. 2012-03-19
create function user_group_assigned_patient_ids( @user_id int )
returns table
as
return
select pgp.IdPacjent from Uzytkownik_grupa_uzytkownikow ugu
join Grupa_uzytkownikow gu on ugu.IdGrupa_uzytkownikow = gu.IdGrupa_uzytkownikow
join Grupa_pacjentow_grupa_uzytkownikow gpgu on gu.IdGrupa_uzytkownikow = gpgu.IdGrupa_uzytkownikow
join Grupa_pacjentow gp on gp.IdGrupa_pacjentow = gpgu.IdGrupa_pacjentow
join Pacjent_grupa_pacjentow pgp on gp.IdGrupa_pacjentow = pgp.IdGrupa_pacjentow
where ugu.IdUzytkownik = @user_id;
go


-- last rev. 2012-03-19
create function user_accessible_patients( @user_id int )
returns table
as
return
(SELECT [IdPacjent], [Imie], [Nazwisko], [Plec], [Data_urodzenia], [Zdjecie], [rowguid], [Uwagi], [IdPerformer], [Ostatnia_zmiana] FROM [Pacjent] p
 where p.IdPacjent in ( select * from user_group_assigned_patient_ids( @user_id) ) )  
go


-- last rev. 2012-03-19
create function identify_user( @user_login varchar(30) )
returns int
as
begin
return ( select top 1 IdUzytkownik from Uzytkownik where Login = @user_login );
end
go

-- last rev. 2012-03-19
create function user_accessible_patients_by_login( @user_login varchar(30) )
returns table
as
return
select [IdPacjent], [Imie], [Nazwisko], [Plec], [Data_urodzenia], [Zdjecie], [rowguid], [Uwagi], [IdPerformer], [Ostatnia_zmiana] FROM user_accessible_patients( dbo.identify_user( @user_login ))
go



-- last mod. 2012-03-06
create trigger tr_Pacjent_Update on Pacjent
for update, insert
as
begin
	update Pacjent
	set Ostatnia_zmiana = getdate()
	from inserted i join Pacjent s on i.IdPacjent = s.IdPacjent
end
go

-- last mod. 2012-03-06
create trigger tr_Badanie_Update on Badanie
for update, insert
as
begin
	update Badanie
	set Ostatnia_zmiana = getdate()
	from inserted i join Badanie kp on i.IdBadanie = kp.IdBadanie
end
go

-- last mod. 2012-03-06
create trigger tr_Pacjent_Jednostka_chorobowa_Update on Pacjent_Jednostka_chorobowa
for update, insert
as
begin
	update Pacjent_Jednostka_chorobowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Pacjent_Jednostka_chorobowa p on ( i.IdPacjent = p.IdPacjent and i.IdJednostka_chorobowa = p.IdJednostka_chorobowa )
end
go

-- last mod. 2012-03-06
create procedure time_stamp
as
(
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Pacjent 
	union
	select max(Ostatnia_zmiana) as ts from Badanie 
	union
	select max(Ostatnia_zmiana) as ts from Pacjent_Jednostka_chorobowa
	) as q1
)
go

-- last mod. 2012-03-06
create function f_time_stamp()
returns datetime
as
begin
return ( 
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Pacjent 
	union
	select max(Ostatnia_zmiana) as ts from Badanie 
	union
	select max(Ostatnia_zmiana) as ts from Pacjent_Jednostka_chorobowa
	) as q1
 );
end
go