use Motion_Med;
go

alter procedure m_get_patient_list
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
		case PD.Glowna when 1 then '0' else '1' end "@Focus"
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
