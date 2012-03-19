use Motion_Med;
go
select * from Uzytkownik

alter table Uzytkownik add Email varchar(50) not null default 'NO_EMAIL';
go
alter table Uzytkownik add Kod_Aktywacji varchar(20);
go
alter table Uzytkownik add Status int not null default 0;
go

-- update Uzytkownik set Haslo = HashBytes('SHA1','Admin1');

insert into Grupa_uzytkownikow ( Nazwa ) values ( 'administrators' );
insert into Grupa_uzytkownikow ( Nazwa ) values ( 'opetators' );
insert into Grupa_uzytkownikow ( Nazwa ) values ( 'motion_project' );
go


insert into Grupa_badan ( Nazwa ) values ( 'Gocza³kowice' );
insert into Grupa_badan ( Nazwa ) values ( 'CZD' );
go


insert into Grupa_pacjentow ( Nazwa ) values ( 'Gocza³kowice' );
insert into Grupa_pacjentow ( Nazwa ) values ( 'CZD' );
insert into Grupa_pacjentow ( Nazwa ) values ( 'PJWSTK' );
go



/*
insert into Pacjent_Grupa_pacjentow ( IdPacjent, IdGrupa_pacjentow ) select p.IdPacjent, gp.IdGrupa_pacjentow  from Pacjent p, Grupa_pacjentow gp where p.IdPacjent >= 35 and p.IdPacjent <= 43 and gp.Nazwa = 'PJWSTK'

select MAX(sgs.
select max(kp.IdPerformer), MIN(kp.IdPerformer)  from Motion.dbo.Grupa_sesji gs join Motion.dbo.Sesja_grupa_sesji sgs on gs.IdGrupa_sesji = sgs.IdGrupa_sesji 
join Motion.dbo.Konfiguracja_performera kp on sgs.IdSesja = kp.IdSesja
where gs.Nazwa = 'PJWSTK'
*/

-- TODO !!!

-- insert into Uzytkownik_grupa_uzytkownikow ( IdGrupa_uzytkownikow, IdUzytkownik ) select gu.IdGrupa_uzytkownikow, u.IdUzytkownik from Uzytkownik u, Grupa_uzytkownikow gu where u.Login = 'habela' -- and gu.Nazwa = 'administrators'

insert into Uzytkownik_grupa_uzytkownikow ( IdGrupa_uzytkownikow, IdUzytkownik ) select gu.IdGrupa_uzytkownikow, u.IdUzytkownik from Uzytkownik u, Grupa_uzytkownikow gu where u.Login = 'applet_user' and gu.Nazwa = 'motion_project'

insert into Grupa_pacjentow_grupa_uzytkownikow ( IdGrupa_pacjentow, IdGrupa_uzytkownikow) select gp.IdGrupa_pacjentow, gu.IdGrupa_uzytkownikow from Grupa_pacjentow gp, Grupa_uzytkownikow gu where gu.Nazwa = 'motion_project' 



alter procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass) and Status > 0;
if (@c = 1) set @res = 1; else set @res = 0;
end;
go





-- Error codes:
-- 1 login already in use
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
alter procedure create_user_account(@user_login varchar(30), @user_password varchar(20),  @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @activation_code varchar(20), @result int OUTPUT)
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
	set @email_body = 'Your activation code for login '+@user_login +' is: ' + @activation_code +' . Please visit the webpage https://v21.pjwstk.edu.pl/HMDBMed/UserAccountCreation.aspx to authenticate and perform your account activatio using this code.';

	exec msdb.dbo.sp_send_dbmail @profile_name='HMDB_Mail',
	@recipients=@user_email,
	@subject= @email_title,
	@body= @email_body;
	return 0;
end;
go



-- Error codes:
-- 1 authentication negative
create procedure activate_user_account(@user_login varchar(30), @user_password varchar(20),  @activation_code varchar(20), @result int OUTPUT)
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

-- =================================================================


create table Grupa_pacjentow (
	IdGrupa_pacjentow int IDENTITY,
	Nazwa varchar (50)
)
go

alter table Grupa_pacjentow
	add primary key (IdGrupa_pacjentow)
go


create table Grupa_pacjentow_grupa_uzytkownikow (
	IdGrupa_uzytkownikow	int not null,
	IdGrupa_pacjentow	int not null
)
go

CREATE INDEX X1Grupa_pacjentow_grupa_uzytkownikow ON Grupa_pacjentow_grupa_uzytkownikow
 (
        IdGrupa_pacjentow
 )
go

CREATE INDEX X2Grupa_pacjentow_grupa_uzytkownikow ON Grupa_pacjentow_grupa_uzytkownikow
 (
        IdGrupa_uzytkownikow
 )
go

ALTER TABLE Grupa_pacjentow_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_pacjentow)
                              REFERENCES Grupa_pacjentow;
go
 
 
 ALTER TABLE Grupa_pacjentow_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_uzytkownikow)
                              REFERENCES Grupa_uzytkownikow on delete cascade;
go

CREATE TABLE Pacjent_grupa_Pacjentow (
        IdPacjent              int NOT NULL,
        IdGrupa_Pacjentow        int NOT NULL
 )
go
 
CREATE INDEX X1Pacjent_grupa_Pacjentow ON Pacjent_grupa_Pacjentow
 (
        IdPacjent
 )
go
 
CREATE INDEX X2Pacjent_grupa_Pacjentow ON Pacjent_grupa_Pacjentow
 (
        IdGrupa_Pacjentow
 )
go
 
 
ALTER TABLE Pacjent_grupa_Pacjentow
        ADD PRIMARY KEY (IdPacjent, IdGrupa_Pacjentow)
go



alter procedure m_get_patient_list ( @user_login as varchar(30) )
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


