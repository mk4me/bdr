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


-- last mod. 2011-11-25
create procedure m_get_patient_list
as
with
P as (select * from Pacjent Patient ),
DO as (select * from Pacjent_Jednostka_chorobowa DisorderOccurence where exists(select * from P where P.IdPacjent = DisorderOccurence.IdPacjent)),
E as (select * from Badanie Examination where exists (select * from P where P.IdPacjent = Examination.IdPacjent))
select
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

-- Password related
-- ================


-- last mod. 2012-01-14
create procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass);
if (@c = 1) set @res = 1; else set @res = 0;
end;
go

-- last mod. 2012-01-14
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

