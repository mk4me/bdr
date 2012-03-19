use Motion_Med;
go

-- TODO: dodac indeksy dla dat

alter table Pacjent add Ostatnia_zmiana datetime default getdate() not null;
go

create index X3Pacjent on Pacjent
 (
        Ostatnia_zmiana
 )
go


alter table Badanie add Ostatnia_zmiana datetime default getdate() not null;
go

create index X3Badanie on Badanie
 (
        Ostatnia_zmiana
 )
go

alter table Pacjent_Jednostka_chorobowa add Ostatnia_zmiana datetime default getdate() not null;
go

create index X3Pacjent_Jednostka_chorobowa on Pacjent_Jednostka_chorobowa
 (
        Ostatnia_zmiana
 )
go

alter table Zdjecie_udostepnione add Zmieniony datetime;
go




create trigger tr_Pacjent_Update on Pacjent
for update, insert
as
begin
	update Pacjent
	set Ostatnia_zmiana = getdate()
	from inserted i join Pacjent s on i.IdPacjent = s.IdPacjent
end
go


create trigger tr_Badanie_Update on Badanie
for update, insert
as
begin
	update Badanie
	set Ostatnia_zmiana = getdate()
	from inserted i join Badanie kp on i.IdBadanie = kp.IdBadanie
end
go

create trigger tr_Pacjent_Jednostka_chorobowa_Update on Pacjent_Jednostka_chorobowa
for update, insert
as
begin
	update Pacjent_Jednostka_chorobowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Pacjent_Jednostka_chorobowa p on ( i.IdPacjent = p.IdPacjent and i.IdJednostka_chorobowa = p.IdJednostka_chorobowa )
end
go


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


-- last mod. 2012-03-06
alter procedure m_get_patient_list
as
with
P as (select * from Pacjent Patient ),
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


alter table Jednostka_chorobowa add Ostatnia_zmiana datetime default getdate() not null;
go

create index X1Jednostka_chorobowa on Jednostka_chorobowa
 (
        Ostatnia_zmiana
 )
go


alter table Grupa_badan add Ostatnia_zmiana datetime default getdate() not null;
go

create index X1Grupa_badan on Grupa_badan
 (
        Ostatnia_zmiana
 )
go

alter table Kontekst_badania add Ostatnia_zmiana datetime default getdate() not null;
go

create index X1Kontekst_badania on Kontekst_badania
 (
        Ostatnia_zmiana
 )
go

create trigger tr_Jednostka_chorobowa_Update on Jednostka_chorobowa
for update, insert
as
begin
	update Jednostka_chorobowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Jednostka_chorobowa s on i.IdJednostka_chorobowa = s.IdJednostka_chorobowa
end
go


create trigger tr_Grupa_badan_Update on Grupa_badan
for update, insert
as
begin
	update Grupa_badan
	set Ostatnia_zmiana = getdate()
	from inserted i join Grupa_badan kp on i.IdGrupa_badan = kp.IdGrupa_badan
end
go

create trigger tr_Kontekst_badania_Update on Kontekst_badania
for update, insert
as
begin
	update Kontekst_badania
	set Ostatnia_zmiana = getdate()
	from inserted i join Kontekst_badania p on i.IdKontekst_badania = p.IdKontekst_badania
end
go


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
alter procedure get_med_metadata
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


