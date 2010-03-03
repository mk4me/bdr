USE Motion

DROP INDEX XIF19Atrybut ON Atrybut
go

DROP TABLE Wartosc_wyliczeniowa 
go

DROP TABLE Wartosc_atrybutu_sesji
go

DROP TABLE Wartosc_atrybutu_segmentu 
go

DROP TABLE Wartosc_atrybutu_pliku
go
DROP TABLE Wartosc_atrybutu_performera 
go

DROP TABLE Wartosc_atrybutu_obserwacji 
go


DROP TABLE Atrybut
go

 CREATE TABLE Atrybut (
        IdAtrybut            int IDENTITY,
        IdGrupa_atrybutow    int NOT NULL,
        Nazwa                varchar(100) NOT NULL,
        Typ_danych           varchar(20) NOT NULL,
        Opisywana_encja		varchar(20) NOT NULL,
        Wyliczeniowy	bit
 )
go
 
 CREATE INDEX XIF19Atrybut ON Atrybut
 (
        IdGrupa_atrybutow
 )
go

 ALTER TABLE Atrybut
        ADD PRIMARY KEY (IdAtrybut)
go


 CREATE TABLE Wartosc_atrybutu_obserwacji (
        IdObserwacja         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 
 CREATE INDEX XIF54Wartosc_atrybutu_obserwac ON Wartosc_atrybutu_obserwacji
 (
        IdObserwacja
 )
go
 
 CREATE INDEX XIF55Wartosc_atrybutu_obserwac ON Wartosc_atrybutu_obserwacji
 (
        IdAtrybut
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_obserwacji
        ADD PRIMARY KEY (IdObserwacja, IdAtrybut)
go
 
 
 CREATE TABLE Wartosc_atrybutu_performera (
        IdAtrybut            int NOT NULL,
        IdPerformer          int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 
 CREATE INDEX XIF43Wartosc_atrybutu_performe ON Wartosc_atrybutu_performera
 (
        IdAtrybut
 )
go
 
 CREATE INDEX XIF44Wartosc_atrybutu_performe ON Wartosc_atrybutu_performera
 (
        IdPerformer
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_performera
        ADD PRIMARY KEY (IdAtrybut, IdPerformer)
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
 

 
 CREATE TABLE Wartosc_atrybutu_segmentu (
        IdAtrybut            int NOT NULL,
        IdSegment            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 
 CREATE INDEX XIF41Wartosc_atrybutu_segmentu ON Wartosc_atrybutu_segmentu
 (
        IdAtrybut
 )
go
 
 CREATE INDEX XIF42Wartosc_atrybutu_segmentu ON Wartosc_atrybutu_segmentu
 (
        IdSegment
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_segmentu
        ADD PRIMARY KEY (IdAtrybut, IdSegment)
go
 
 
 CREATE TABLE Wartosc_atrybutu_sesji (
        IdAtrybut            int NOT NULL,
        IdSesja              int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL
 )
go
 
 CREATE INDEX XIF39Wartosc_atrybutu_sesji ON Wartosc_atrybutu_sesji
 (
        IdAtrybut
 )
go
 
 CREATE INDEX XIF40Wartosc_atrybutu_sesji ON Wartosc_atrybutu_sesji
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD PRIMARY KEY (IdAtrybut, IdSesja)
go
 
 
 CREATE TABLE Wartosc_wyliczeniowa (
        IdWartosc_wyliczeniowa int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_wyliczeniowa varchar(100) NOT NULL
 )
go


ALTER TABLE Atrybut
        ADD FOREIGN KEY (IdGrupa_atrybutow)
                              REFERENCES Grupa_atrybutow
go


ALTER TABLE Wartosc_atrybutu_obserwacji
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 ALTER TABLE Wartosc_atrybutu_performera
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 
 ALTER TABLE Wartosc_atrybutu_segmentu
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_wyliczeniowa
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 