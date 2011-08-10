use Motion_Med;
go

-- Shallow copy retrieval
-- ==========================

-- last rev. 2011-08-09

--MedicalData (
--  Patient ( ...
--    Disorder (... )*
--    Examination (... )*
--  )*
--)

select * from Typ_badania

insert into Typ_badania ( Nazwa ) values ('Testowe');
go

--insert into Badanie ( IdTyp_badania, IdPacjent, Data, Opis, Notatki, IdSesja ) values ( 1, 1, '2011-11-11 12:12:12:000', 'Desc', 'Notes', null )
--insert into Badanie ( IdTyp_badania, IdPacjent, Data, Opis, Notatki, IdSesja ) values ( 1, 2, '2011-11-11 12:12:12:000', 'Desc', 'Notes', 1 )
--insert into Badanie ( IdTyp_badania, IdPacjent, Data, Opis, Notatki, IdSesja ) values ( 1, 2, '2011-11-12 12:12:12:000', 'Desc', 'Notes', 2 )

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
		PD.Komentarz "@Comment" 
	  from Pacjent_schorzenie PD join Schorzenie S on PD.IdSchorzenie = S.IdSchorzenie where PD.IdPacjent = Patient.IdPacjent
	  FOR XML PATH('Disorder'), TYPE
	) "Disorders",
	(
		select
			ET.Nazwa "@Type",
			E.Data "@Date",
			E.Opis "@Desc",
			E.Notatki "@Notes",
			E.IdSesja "@SessionID"			
		from Badanie E join Typ_badania ET on E.IdTyp_badania = ET.IdTyp_badania where E.IdPacjent = Patient.IdPacjent
		FOR XML PATH('Examination'), TYPE
	) "Examinations"
from Pacjent Patient for XML PATH('Patient'), TYPE, ROOT('PatientList')
go

create procedure get_med_metadata
as
select
(select 
	IdTyp_badania as ExamTypeID,
	Nazwa as ExamTypeName
	from Typ_badania ExamType for XML AUTO, TYPE
 ) ExamTypes,
 (select 
	IdSchorzenie as DisorderID,
	Nazwa as DisorderName
	from Schorzenie Disorder for XML AUTO, TYPE
 ) Disorders
 for XML RAW ('MedMetadata'), TYPE;
go


