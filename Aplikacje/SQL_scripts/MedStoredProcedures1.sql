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

create procedure m_get_patient_list
as
select
	IdPacjent "@PatientID",
	Imie "@FirstName",
	Nazwisko "@LastName",
	Plec "@Gender",
	Data_urodzenia "@BirthDate",
	(
	  select 
		S.Nazwa "@Name",
		PD.Komentarz "@Comment",
		case PD.Glowna when 1 then 'primary' else 'secondary' end "@Focus"
	  from Pacjent_Jednostka_chorobowa PD join Jednostka_chorobowa S on PD.IdJednostka_chorobowa = S.IdJednostka_chorobowa where PD.IdPacjent = Patient.IdPacjent
	  FOR XML PATH('Disorder'), TYPE
	) "Disorders",
	(
		select
			ET.Nazwa "@ExaminationGroup",
			E.Data "@Date",
			E.Opis "@Desc",
			E.Notatki "@Notes",
			E.IdSesja "@SessionID"			
		from Badanie E join Grupa_badan ET on E.IdGrupa_badan = ET.IdGrupa_badan where E.IdPacjent = Patient.IdPacjent
		FOR XML PATH('Examination'), TYPE
	) "Examinations"
from Pacjent Patient for XML PATH('Patient'), TYPE, ROOT('PatientList')
go

create procedure get_med_metadata
as
select
(select 
	IdGrupa_badan as ExamTypeID,
	Nazwa as ExamTypeName
	from Grupa_badan ExamType for XML AUTO, TYPE
 ) ExamTypes,
 (select 
	IdJednostka_chorobowa as DisorderID,
	Nazwa as DisorderName
	from Jednostka_chorobowa Disorder for XML AUTO, TYPE
 ) Disorders
 for XML RAW ('MedMetadata'), TYPE;
go


