use Motion_Med;
go

alter table Pacjent add Uwagi varchar(255);
alter table Pacjent add IdPerformer	int;


-- change it !!!!
alter table Konfiguracja_performera add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Konfiguracja_pomiarowa add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Performer add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Plik add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Proba add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Sesja add Ostatnia_zmiana datetime default getdate() not null;
go

alter table Plik add Zmieniony datetime;
go

create trigger tr_Wartosc_atrybutu_konfiguracji_performera_Update on Wartosc_atrybutu_konfiguracji_performera
for update, insert
as
begin
	update Konfiguracja_performera
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_performera kp on i.IdKonfiguracja_performera = kp.IdKonfiguracja_performera
end
go

create trigger tr_Wartosc_atrybutu_sesji_Update on Wartosc_atrybutu_sesji
for update, insert
as
begin
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

create trigger tr_Wartosc_atrybutu_konfiguracji_pomiarowej_Update on Wartosc_atrybutu_konfiguracji_pomiarowej
for update, insert
as
begin
	update Konfiguracja_pomiarowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

create trigger tr_Wartosc_atrybutu_performera_Update on Wartosc_atrybutu_performera
for update, insert
as
begin
	update Performer
	set Ostatnia_zmiana = getdate()
	from inserted i join Performer p on i.IdPerformer = p.IdPerformer
end
go

create trigger tr_Wartosc_atrybutu_pliku_Update on Wartosc_atrybutu_pliku
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
end
go

create trigger tr_Wartosc_atrybutu_proby_Update on Wartosc_atrybutu_proby
for update, insert
as
begin
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go

create trigger tr_Sesja_Update on Sesja
for update, insert
as
begin
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

create trigger tr_Konfiguracja_pomiarowa_Update on Konfiguracja_pomiarowa
for update, insert
as
begin
	update Konfiguracja_pomiarowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

create trigger tr_Plik_Update on Plik
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
	if( COLUMNS_UPDATED() = 0x20)
	update Plik
	set Zmieniony = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik;

end
go

create trigger tr_Proba_Update on Proba
for update, insert
as
begin
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go


create procedure time_stamp
as
(
select max(ts) as time_stamp from
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
	) as q1
)


exec m_get_patient_list

alter procedure m_get_patient_list
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
	case Glowna when 1 then 'primary' else 'secondary' end Focus
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

