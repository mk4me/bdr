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
        IdPerformer			int,
        Ostatnia_zmiana datetime default getdate() not null
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
        LoginBDR			varchar(30),
        Haslo				varbinary(100) default 0x00 not null,
        Email				varchar(50) not null default 'NO_EMAIL',
		Kod_Aktywacji		varchar(20),
		Status				int not null default 0        
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
	IdGrupa_badan int,
	Ostatnia_zmiana datetime default getdate() not null
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
	Nazwa	varchar(60),
	Ostatnia_zmiana datetime default getdate() not null
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
	Glowna bit,
	Ostatnia_zmiana datetime default getdate() not null
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
	Nazwa varchar (50),
    Ostatnia_zmiana datetime default getdate() not null
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

create table Badanie_kontekst_badania (
	IdKontekst_badania	int not null,
	IdBadanie	int not null,
	Rola	varchar(50)
)
go

create table Kontekst_badania (
        IdKontekst_badania    int IDENTITY,
        Nazwa					varchar(100) NOT NULL,
        Ostatnia_zmiana datetime default getdate() not null
 )
go

alter table Kontekst_badania
	add primary key (IdKontekst_badania)
go

CREATE INDEX X1Badanie_kontekst_badania ON Badanie_kontekst_badania
 (
        IdKontekst_badania
 )
go

CREATE INDEX X2Badanie_kontekst_badania ON Badanie_kontekst_badania
 (
        IdBadanie
 )
go

ALTER TABLE Badanie_kontekst_badania
        ADD FOREIGN KEY (IdKontekst_badania)
                              REFERENCES Kontekst_badania;
go
 
 
 ALTER TABLE Badanie_kontekst_badania
        ADD FOREIGN KEY (IdBadanie)
                              REFERENCES Badanie on delete cascade;
go


CREATE TABLE Grupa_uzytkownikow (
        IdGrupa_uzytkownikow    int IDENTITY,
        Nazwa					varchar(100) NOT NULL
 )
go

create table Uzytkownik_grupa_uzytkownikow (
	IdGrupa_uzytkownikow	int not null,
	IdUzytkownik	int not null
)
go

alter table Grupa_uzytkownikow
	add primary key (IdGrupa_uzytkownikow)
go

CREATE INDEX X1Uzytkownik_grupa_uzytkownikow ON Uzytkownik_grupa_uzytkownikow
 (
        IdUzytkownik
 )
go

CREATE INDEX X2Uzytkownik_grupa_uzytkownikow ON Uzytkownik_grupa_uzytkownikow
 (
        IdGrupa_uzytkownikow
 )
go

ALTER TABLE Uzytkownik_grupa_uzytkownikow
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik;
go
 
 
 ALTER TABLE Uzytkownik_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_uzytkownikow)
                              REFERENCES Grupa_uzytkownikow on delete cascade;
go


create table Grupa_badan_grupa_uzytkownikow (
	IdGrupa_uzytkownikow	int not null,
	IdGrupa_badan	int not null
)
go

CREATE INDEX X1Grupa_badan_grupa_uzytkownikow ON Grupa_badan_grupa_uzytkownikow
 (
        IdGrupa_badan
 )
go

CREATE INDEX X2Grupa_badan_grupa_uzytkownikow ON Grupa_badan_grupa_uzytkownikow
 (
        IdGrupa_uzytkownikow
 )
go

ALTER TABLE Grupa_badan_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_badan)
                              REFERENCES Grupa_badan;
go
 
 
 ALTER TABLE Grupa_badan_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_uzytkownikow)
                              REFERENCES Grupa_uzytkownikow on delete cascade;
go




create index X3Pacjent on Pacjent
 (
        Ostatnia_zmiana
 )
go



create index X3Badanie on Badanie
 (
        Ostatnia_zmiana
 )
go


create index X3Pacjent_Jednostka_chorobowa on Pacjent_Jednostka_chorobowa
 (
        Ostatnia_zmiana
 )
go



create index X1Jednostka_chorobowa on Jednostka_chorobowa
 (
        Ostatnia_zmiana
 )
go


create index X1Grupa_badan on Grupa_badan
 (
        Ostatnia_zmiana
 )
go

create index X1Kontekst_badania on Kontekst_badania
 (
        Ostatnia_zmiana
 )
go



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