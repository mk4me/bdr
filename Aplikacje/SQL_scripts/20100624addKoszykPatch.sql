use Motion;


create table Koszyk
(
	IdKoszyk int identity,
	Nazwa varchar(40),
	IdUzytkownik int not null
)
go
 ALTER TABLE Koszyk
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik
go

ALTER TABLE Koszyk
		ADD PRIMARY KEY ( IdKoszyk )
go

 CREATE INDEX XIFKoszyk ON Koszyk
 (
        IdKoszyk
 )
go

create table Performer_Koszyk (
        IdPerformer	int NOT NULL,
        IdKoszyk	int NOT NULL
 )
go
 
 CREATE INDEX XIF1Performer_Koszyk ON Performer_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX XIF2Performer_Koszyk ON Performer_Koszyk
 (
        IdPerformer
 )
go
 
ALTER TABLE Performer_Koszyk
        ADD PRIMARY KEY (IdKoszyk, IdPerformer)
go

create table Sesja_Koszyk (
        IdSesja	int NOT NULL,
        IdKoszyk	int NOT NULL
 )
go
 
 CREATE INDEX XIF1Sesja_Koszyk ON Sesja_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX XIF2Sesja_Koszyk ON Sesja_Koszyk
 (
        IdSesja
 )
go
 
ALTER TABLE Sesja_Koszyk
        ADD PRIMARY KEY (IdKoszyk, IdSesja)
go

create table Obserwacja_Koszyk (
        IdObserwacja	int NOT NULL,
        IdKoszyk	int NOT NULL
 )
go
 
 CREATE INDEX XIF1Obserwacja_Koszyk ON Obserwacja_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX XIF2Obserwacja_Koszyk ON Obserwacja_Koszyk
 (
        IdObserwacja
 )
go
 
ALTER TABLE Obserwacja_Koszyk
        ADD PRIMARY KEY (IdKoszyk, IdObserwacja)
go

create table Segment_Koszyk (
        IdSegment	int NOT NULL,
        IdKoszyk	int NOT NULL
 )
go
 
 CREATE INDEX XIF1Segment_Koszyk ON Segment_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX XIF2Segment_Koszyk ON Segment_Koszyk
 (
        IdSegment
 )
go
 
ALTER TABLE Segment_Koszyk
        ADD PRIMARY KEY (IdKoszyk, IdSegment)
go



alter table Atrybut add pluginDescriptor varchar(100) null ;
go

create table Materializacja_Atrybutu_Performera (
        IdPerformer         int NOT NULL,
        IdAtrybut            int NOT NULL,
        IdUzytkownik		int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 CREATE INDEX XIF1Materializacja_Atrybutu_Performera ON Materializacja_Atrybutu_Performera
 (
        IdPerformer
 )
go
 CREATE INDEX XIF2Materializacja_Atrybutu_Performera ON Materializacja_Atrybutu_Performera
 (
        IdAtrybut
 )
go
 CREATE INDEX XIF3Materializacja_Atrybutu_Performera ON Materializacja_Atrybutu_Performera
 (
        IdUzytkownik
 )
go
create table Materializacja_Atrybutu_Sesji (
        IdSesja         int NOT NULL,
        IdAtrybut            int NOT NULL,
        IdUzytkownik		int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 CREATE INDEX XIF1Materializacja_Atrybutu_Sesji ON Materializacja_Atrybutu_Sesji
 (
        IdSesja
 )
go
 CREATE INDEX XIF2Materializacja_Atrybutu_Sesji ON Materializacja_Atrybutu_Sesji
 (
        IdAtrybut
 )
go
 CREATE INDEX XIF3Materializacja_Atrybutu_Sesji ON Materializacja_Atrybutu_Sesji
 (
        IdUzytkownik
 )
go
create table Materializacja_Atrybutu_Obserwacji (
        IdObserwacja         int NOT NULL,
        IdAtrybut            int NOT NULL,
        IdUzytkownik		int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 CREATE INDEX XIF1Materializacja_Atrybutu_Obserwacji ON Materializacja_Atrybutu_Obserwacji
 (
        IdObserwacja
 )
go
 CREATE INDEX XIF2Materializacja_Atrybutu_Obserwacji ON Materializacja_Atrybutu_Obserwacji
 (
        IdAtrybut
 )
go
 CREATE INDEX XIF3Materializacja_Atrybutu_Obserwacji ON Materializacja_Atrybutu_Obserwacji
 (
        IdUzytkownik
 )
go
create table Materializacja_Atrybutu_Segmentu (
        IdSegment         int NOT NULL,
        IdAtrybut            int NOT NULL,
        IdUzytkownik		int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 CREATE INDEX XIF1Materializacja_Atrybutu_Segmentu ON Materializacja_Atrybutu_Segmentu
 (
        IdSegment
 )
go
 CREATE INDEX XIF2Materializacja_Atrybutu_Segmentu ON Materializacja_Atrybutu_Segmentu
 (
        IdAtrybut
 )
go
 CREATE INDEX XIF3Materializacja_Atrybutu_Segmentu ON Materializacja_Atrybutu_Segmentu
 (
        IdUzytkownik
 )
go
create table Materializacja_Atrybutu_Pliku (
        IdPlik         int NOT NULL,
        IdAtrybut            int NOT NULL,
        IdUzytkownik		int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 CREATE INDEX XIF1Materializacja_Atrybutu_Pliku ON Materializacja_Atrybutu_Pliku
 (
        IdPlik
 )
go
 CREATE INDEX XIF2Materializacja_Atrybutu_Pliku ON Materializacja_Atrybutu_Pliku
 (
        IdAtrybut
 )
go
 CREATE INDEX XIF3Materializacja_Atrybutu_Pliku ON Materializacja_Atrybutu_Pliku
 (
        IdUzytkownik
 )
go