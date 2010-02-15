drop index XIF27Wartosc_atrybutu_pliku on Wartosc_atrybutu_pliku
go
drop index XIF28Wartosc_atrybutu_pliku on Wartosc_atrybutu_pliku
go
drop table Wartosc_atrybutu_pliku
go

drop index XIF12Audio on Audio
go
drop table Audio
go

drop index XIF10Obraz_2D on Obraz_2D
go
drop table Obraz_2D
go
drop index XIF11Video on Video
go
drop table Video
go

drop index XIF50Plik on Plik
go
drop table Plik
go


 CREATE TABLE Plik (
        IdPlik               int IDENTITY,
        IdSesja              int NULL,
        IdObserwacja         int NULL,
        Opis_pliku           varchar(100) NOT NULL,
        Plik                 varbinary(max) filestream not null,
	rowguid uniqueidentifier rowguidcol DEFAULT NEWSEQUENTIALID() NOT NULL unique,
        Czas_poczatku        int NULL,
        Czas_konca           int NULL,
	Nazwa_pliku          varchar(255) null
 )
go
 
 CREATE INDEX XIF50Plik ON Plik
 (
        IdObserwacja
 )
go
 
 CREATE INDEX XIF56Plik ON Plik
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Plik
        ADD PRIMARY KEY (IdPlik)
go
 

 CREATE TABLE Audio (
        IdPlik               int IDENTITY,
        Kanal                int NOT NULL,
        Bity_na_sekunde      int NOT NULL
 )
go
 
 CREATE INDEX XIF12Audio ON Audio
 (
        IdPlik
 )
go
 
 
 ALTER TABLE Audio
        ADD PRIMARY KEY (IdPlik)
go

 CREATE TABLE Obraz_2D (
        IdPlik               int IDENTITY
 )
go
 
 CREATE INDEX XIF10Obraz_2D ON Obraz_2D
 (
        IdPlik
 )
go
 
 
 ALTER TABLE Obraz_2D
        ADD PRIMARY KEY (IdPlik)
go
 
 CREATE TABLE Video (
        IdPlik               int IDENTITY,
        Nr_kamery            int NOT NULL,
        Klatki_na_sekunde    int NOT NULL
 )
go
 
 CREATE INDEX XIF11Video ON Video
 (
        IdPlik
 )
go
 
 
 ALTER TABLE Video
        ADD PRIMARY KEY (IdPlik)
go

 CREATE TABLE Wartosc_atrybutu_pliku (
        IdAtrybut            int NOT NULL,
        IdPlik               int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 
 CREATE INDEX XIF27Wartosc_atrybutu_pliku ON Wartosc_atrybutu_pliku
 (
        IdPlik
 )
go
 
 CREATE INDEX XIF28Wartosc_atrybutu_pliku ON Wartosc_atrybutu_pliku
 (
        IdAtrybut
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD PRIMARY KEY (IdAtrybut, IdPlik)
go

ALTER TABLE Audio
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
 
 
 ALTER TABLE Obraz_2D
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
  
 
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja
go
 
 
 ALTER TABLE Video
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go 

