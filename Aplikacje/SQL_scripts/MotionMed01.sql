-- TODO: zbudowac dla Sesji tryger on delete kasujacy pliki proby
-- TO DO: Zrobiæ trigger do usuwania pliku przy wykonywaniu stosownej ClearAttributeValue.

use Motion_Med;
go 
 CREATE TABLE Pacjent (
        IdPacjent           int IDENTITY, -- zmienic!!!
        Imie                varchar(30) NOT NULL,
        Nazwisko            varchar(50) NOT NULL,
        Plec				char NOT NULL,
        Data_urodzenia		date NOT NULL,
        Zdjecie				varbinary(max) filestream,
        rowguid				uniqueidentifier rowguidcol not null unique
)
go
 ALTER TABLE Pacjent
        ADD PRIMARY KEY (IdPacjent)
go

CREATE TABLE Uzytkownik (
        IdUzytkownik         int IDENTITY,
        Login                varchar(30) NOT NULL UNIQUE,
        Imie                 varchar(30) NOT NULL,
        Nazwisko             varchar(50) NOT NULL
 )
go
 
 
ALTER TABLE Uzytkownik
        ADD PRIMARY KEY (IdUzytkownik)
go


create table Badanie (
	IdBadanie	int IDENTITY,
	Data	date NOT NULL, -- datetime ???
	Opis varchar(255),
	Notatki	varchar(255),
	IdSesja int,
	IdTyp_badania int
)
go

alter table Badanie
	add primary key (IdBadanie)
go

CREATE INDEX X1Badanie ON Badanie
 (
        IdSesja
 )
go

create table Schorzenie (
	IdSchorzenie	int IDENTITY,
	Nazwa	varchar(60)
)
go

alter table Schorzenie
	add primary key (IdSchorzenie)
go

create table Badanie_schorzenie ( -- przeniesc do pacjenta?
	IdBadanie	int not null,
	IdSchorzenie	int not null,
	Komentarz varchar(255)
)
go

alter table Badanie_schorzenie
	add primary key (IdBadanie, IdSchorzenie)
go

alter table Badanie_schorzenie
	add foreign key (IdBadanie) references Badanie on delete cascade;
go

alter table Badanie_schorzenie
	add foreign key (IdSchorzenie) references Schorzenie on delete cascade;
go

create index X1Badanie_schorzenie on Badanie_schorzenie (
	IdBadanie
)
go

create index X2Badanie_schorzenie on Badanie_schorzenie (
	IdSchorzenie
)
go

create table Typ_badania (
	IdTyp_badania int IDENTITY,
	Nazwa varchar (50)
)
go

alter table Typ_badania
	add primary key (IdTyp_badania)
go

alter table Badanie
	add foreign key (IdTyp_badania) references Typ_badania on delete cascade;
go



