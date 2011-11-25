-- TODO: zbudowac dla Sesji tryger on delete kasujacy pliki proby
-- TO DO: Zrobiæ trigger do usuwania pliku przy wykonywaniu stosownej ClearAttributeValue.

use Motion_Med;
go 
 CREATE TABLE Pacjent (
        IdPacjent           int not null,
        Imie                varchar(30) NOT NULL,
        Nazwisko            varchar(50) NOT NULL,
        Plec				char NOT NULL,
        Data_urodzenia		date NOT NULL,
        Zdjecie				varbinary(max) filestream,
        rowguid				uniqueidentifier rowguidcol  not null unique default NEWSEQUENTIALID(),
        Uwagi				varchar(255),
        IdPerformer			int
)
go
 ALTER TABLE Pacjent
        ADD PRIMARY KEY (IdPacjent)
go

CREATE TABLE Uzytkownik (
        IdUzytkownik        int IDENTITY,
        Login               varchar(30) NOT NULL UNIQUE,
        Imie                varchar(30) NOT NULL,
        Nazwisko            varchar(50) NOT NULL,
        LoginBDR			varchar(30)
 )
go
 
 
ALTER TABLE Uzytkownik
        ADD PRIMARY KEY (IdUzytkownik)
go


create table Badanie (
	IdBadanie	int IDENTITY,
	IdPacjent int NOT NULL,
	Data	datetime NOT NULL,
	Opis varchar(255),
	Notatki	varchar(255),
	IdSesja int,
	IdGrupa_badan int
)
go

create table Zdjecie_udostepnione (
        IdPacjent int NOT NULL,
        Data_udostepnienia datetime NOT NULL,
        Lokalizacja varchar(80) NOT NULL
 )
go

CREATE INDEX X1Zdjecie_udostepnione ON Zdjecie_udostepnione
 (
         IdPacjent
 )
go
 
 ALTER TABLE Zdjecie_udostepnione
        ADD FOREIGN KEY (IdPacjent)
			REFERENCES Pacjent
go


alter table Badanie
	add primary key (IdBadanie)
go

CREATE INDEX X1Badanie ON Badanie
 (
        IdSesja
 )
go

CREATE INDEX X2Badanie ON Badanie
 (
        IdPacjent
 )
go


create table Jednostka_chorobowa (
	IdJednostka_chorobowa	int IDENTITY,
	Nazwa	varchar(60)
)
go

alter table Jednostka_chorobowa
	add primary key (IdJednostka_chorobowa)
go

create table Pacjent_Jednostka_chorobowa (
	IdPacjent	int not null,
	IdJednostka_chorobowa	int not null,
	Komentarz varchar(255),
	Data_diagnozy date,
	Glowna bit
)
go

alter table Pacjent_Jednostka_chorobowa
	add primary key (IdPacjent, IdJednostka_chorobowa)
go

alter table Pacjent_Jednostka_chorobowa
	add foreign key (IdPacjent) references Pacjent on delete cascade;
go

alter table Pacjent_Jednostka_chorobowa
	add foreign key (IdJednostka_chorobowa) references Jednostka_chorobowa on delete cascade;
go

create index X1Pacjent_Jednostka_chorobowa on Pacjent_Jednostka_chorobowa (
	IdPacjent
)
go

create index X2Pacjent_Jednostka_chorobowa on Pacjent_Jednostka_chorobowa (
	IdJednostka_chorobowa
)
go

create table Grupa_badan (
	IdGrupa_badan int IDENTITY,
	Nazwa varchar (50)
)
go

alter table Grupa_badan
	add primary key (IdGrupa_badan)
go

alter table Badanie
	add foreign key (IdGrupa_badan) references Grupa_badan;
go

alter table Badanie
	add foreign key (IdPacjent) references Pacjent;
go


