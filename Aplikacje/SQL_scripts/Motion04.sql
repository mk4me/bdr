
 
 CREATE TABLE Atrybut (
        IdAtrybut            int IDENTITY,
        IdGrupa_atrybutow    int NOT NULL,
        Nazwa                varchar(100) NOT NULL,
        Typ_danych           varchar(20) NOT NULL,
        Wyliczeniowy		bit NULL,
        Plugin varchar(100) NULL
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
 
 
 CREATE TABLE Dane_antropometryczne (
        IdSesja              int NOT NULL,
        Wzrost               smallint NULL,
        Masa                 smallint NULL
 )
go
 
 CREATE INDEX XIF58Dane_antropometryczne ON Dane_antropometryczne
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Dane_antropometryczne
        ADD PRIMARY KEY (IdSesja)
go
 
 
 CREATE TABLE Grupa_atrybutow (
        IdGrupa_atrybutow    int IDENTITY,
        Nazwa                varchar(100) NOT NULL,
        Opisywana_encja		varchar(20) NOT NULL
 )
go
 
 
 ALTER TABLE Grupa_atrybutow
        ADD PRIMARY KEY (IdGrupa_atrybutow)
go
 
 
 CREATE TABLE Grupa_sesji (
        IdGrupa_sesji        int IDENTITY,
        Nazwa                varchar(100) NOT NULL
 )
go
 
 
 ALTER TABLE Grupa_sesji
        ADD PRIMARY KEY (IdGrupa_sesji)
go
 
 
 CREATE TABLE Laboratorium (
        IdLaboratorium       int IDENTITY,
        Nazwa                varchar(100) NOT NULL
 )
go
 
 
 ALTER TABLE Laboratorium
        ADD PRIMARY KEY (IdLaboratorium)
go
 
 
 CREATE TABLE Obraz_2D (
        IdPlik               int NOT NULL
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
 
 
 CREATE TABLE Obserwacja (
        IdObserwacja         int IDENTITY,
        IdSesja              int NOT NULL,
        Opis_obserwacji      varchar(100) NOT NULL,
        Czas_trwania         int NOT NULL
 )
go
 
 CREATE INDEX XIF48Obserwacja ON Obserwacja
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Obserwacja
        ADD PRIMARY KEY (IdObserwacja)
go
 
  
 
 ALTER TABLE Pacjent
        ADD PRIMARY KEY (IdPerformer)
go
 
 
 CREATE TABLE Performer (
        IdPerformer          int IDENTITY,
        Imie                 varchar(30) NULL,
        Nazwisko             varchar(50) NOT NULL
 )
go
 
 
 ALTER TABLE Performer
        ADD PRIMARY KEY (IdPerformer)
go
 
 
 CREATE TABLE Plik (
        IdPlik               int NOT NULL,
        IdPerformer          int NULL,
        IdSesja              int NULL,
        IdObserwacja         int NULL,
        Opis_pliku           varchar(100) NOT NULL,
        Plik                 varbinary(max) filestream not null,
	rowguid uniqueidentifier rowguidcol not null unique,
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

 CREATE INDEX XIF57Plik ON Plik
 (
        IdPerformer
 )
go 
 
 ALTER TABLE Plik
        ADD PRIMARY KEY (IdPlik)
go
 
 
 CREATE TABLE Rodzaj_ruchu (
        IdRodzaj_ruchu       int IDENTITY,
        Nazwa                varchar(50) NOT NULL
 )
go
 
 
 ALTER TABLE Rodzaj_ruchu
        ADD PRIMARY KEY (IdRodzaj_ruchu)
go
 
 
 CREATE TABLE Segment (
        IdSegment            int IDENTITY,
        IdObserwacja         int NOT NULL,
        Nazwa                varchar(100) NOT NULL,
        Czas_poczatku        int NOT NULL,
        Czas_konca           int NOT NULL
 )
go
 
 CREATE INDEX XIF49Segment ON Segment
 (
        IdObserwacja
 )
go
 
 
 ALTER TABLE Segment
        ADD PRIMARY KEY (IdSegment)
go
 
 
 CREATE TABLE Sesja (
        IdSesja              int IDENTITY,
        IdUzytkownik         int NOT NULL,
        IdLaboratorium       int NOT NULL,
        IdRodzaj_ruchu       int NULL,
        IdPerformer          int NULL,
        Data                 datetime NOT NULL,
        Opis_sesji           varchar(100) NULL
 )
go
 
 CREATE INDEX XIF1Sesja ON Sesja
 (
        IdPerformer
 )
go
 
 CREATE INDEX XIF2Sesja ON Sesja
 (
        IdRodzaj_ruchu
 )
go
 
 CREATE INDEX XIF37Sesja ON Sesja
 (
        IdLaboratorium
 )
go
 
 CREATE INDEX XIF38Sesja ON Sesja
 (
        IdUzytkownik
 )
go
 
 
 ALTER TABLE Sesja
        ADD PRIMARY KEY (IdSesja)
go
 
 
 CREATE TABLE Sesja_grupa_sesji (
        IdSesja              int NOT NULL,
        IdGrupa_sesji        int NOT NULL
 )
go
 
 CREATE INDEX XIF59Sesja_grupa_sesji ON Sesja_grupa_sesji
 (
        IdSesja
 )
go
 
 CREATE INDEX XIF60Sesja_grupa_sesji ON Sesja_grupa_sesji
 (
        IdGrupa_sesji
 )
go
 
 
 ALTER TABLE Sesja_grupa_sesji
        ADD PRIMARY KEY (IdSesja, IdGrupa_sesji)
go
 
 
 CREATE TABLE Uprawnienia_grupa_atrybutow (
        IdUzytkownik         int NOT NULL,
        IdGrupa_atrybutow    int NOT NULL,
        Odczyt               bit,
        Zapis                bit
 )
go
 
 CREATE INDEX XIF61Uprawnienia_grupa_atrybut ON Uprawnienia_grupa_atrybutow
 (
        IdUzytkownik
 )
go
 
 CREATE INDEX XIF62Uprawnienia_grupa_atrybut ON Uprawnienia_grupa_atrybutow
 (
        IdGrupa_atrybutow
 )
go
 
 
 ALTER TABLE Uprawnienia_grupa_atrybutow
        ADD PRIMARY KEY (IdUzytkownik, IdGrupa_atrybutow)
go
 
 
 CREATE TABLE Uprawnienia_ogolne (
        IdUprawnienia_ogolne int IDENTITY,
        Nazwa                varchar(100) NOT NULL
 )
go
 
 
 ALTER TABLE Uprawnienia_ogolne
        ADD PRIMARY KEY (IdUprawnienia_ogolne)
go
 
 
 CREATE TABLE Uprawnienia_sesja (
        IdUzytkownik         int NOT NULL,
        IdSesja              int NOT NULL,
        Zapis                bit
 )
go
 
 CREATE INDEX XIF15Uprawnienia_sesja ON Uprawnienia_sesja
 (
        IdUzytkownik
 )
go
 
 CREATE INDEX XIF16Uprawnienia_sesja ON Uprawnienia_sesja
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Uprawnienia_sesja
        ADD PRIMARY KEY (IdUzytkownik, IdSesja)
go
 
 
 CREATE TABLE Uzytkownik (
        IdUzytkownik         int IDENTITY,
        Login                varchar(30) NOT NULL,
        Imie                 varchar(30) NOT NULL,
        Nazwisko             varchar(50) NOT NULL
 )
go
 
 
 ALTER TABLE Uzytkownik
        ADD PRIMARY KEY (IdUzytkownik)
go
 
 
 CREATE TABLE Uzytkownik_uprawnienia (
        IdUzytkownik         int NOT NULL,
        IdUprawnienia_ogolne int NOT NULL
 )
go
 
 CREATE INDEX XIF29Uzytkownik_uprawnienia ON Uzytkownik_uprawnienia
 (
        IdUzytkownik
 )
go
 
 CREATE INDEX XIF30Uzytkownik_uprawnienia ON Uzytkownik_uprawnienia
 (
        IdUprawnienia_ogolne
 )
go
 
 
 ALTER TABLE Uzytkownik_uprawnienia
        ADD PRIMARY KEY (IdUzytkownik, IdUprawnienia_ogolne)
go
 
 
 CREATE TABLE Video (
        IdPlik               int NOT NULL,
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
        IdWartosc_wyliczeniowa int IDENTITY,
        IdAtrybut            int NOT NULL,
        Wartosc_wyliczeniowa varchar(100) NOT NULL
 )
go

CREATE TABLE Plik_udostepniony (
        IdPlik_udostepniony int NOT NULL,
        Data_udostepnienia datetime NOT NULL,
        Lokalizacja varchar(80) NOT NULL
 )
go

 CREATE TABLE Blad (
        IdBlad         int IDENTITY,
        NrBledu		int,
        Dotkliwosc	int,
        Stan		int,
        Procedura	nvarchar(100),
        Linia		int,
        Komunikat  nvarchar(500)
 )
 go

create table Predykat -- czesc funkcjonalnosci UPS
(
	IdPredykat int not null,
	IdRodzicPredykat int not null,
	EncjaKontekst varchar(20) not null,
	IdPoprzedniPredykat int not null,
	NastepnyOperator varchar(5) not null,
	NazwaWlasciwosci varchar(100) not null,
	Operator varchar(5) not null,
	Wartosc varchar(100) not null,
	FunkcjaAgregujaca varchar(10) not null,
	EncjaAgregowana varchar(20) not null,
	Nazwa varchar(20) not null,
	IdUzytkownik int not null
)
go
 ALTER TABLE Predykat
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik
go

ALTER TABLE Predykat
		ADD PRIMARY KEY (IdUzytkownik, IdPredykat)
go

 CREATE INDEX XIFPredykat ON Predykat
 (
        IdUzytkownik
 )
go

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




 CREATE INDEX XIF46Wartosc_wyliczeniowa ON Wartosc_wyliczeniowa
 (
        IdAtrybut
 )
go
 
 CREATE INDEX XIF47Plik_udostepniony ON Plik_udostepniony
 (
         IdPlik_udostepniony
 )
go
 
 ALTER TABLE Plik_udostepniony
        ADD FOREIGN KEY (IdPlik_udostepniony)
			REFERENCES Plik
go

 
 ALTER TABLE Wartosc_wyliczeniowa
        ADD PRIMARY KEY (IdWartosc_wyliczeniowa)
go
 
 
 ALTER TABLE Atrybut
        ADD FOREIGN KEY (IdGrupa_atrybutow)
                              REFERENCES Grupa_atrybutow
go
 
 
 ALTER TABLE Audio
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
 
 
 ALTER TABLE Dane_antropometryczne
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Obraz_2D
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
 
 
 ALTER TABLE Obserwacja
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Pacjent
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer
go
 
 
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja
go
 
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer
go 
 
 ALTER TABLE Segment
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja
go
 
 
 ALTER TABLE Sesja
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik
go
 
 
 ALTER TABLE Sesja
        ADD FOREIGN KEY (IdLaboratorium)
                              REFERENCES Laboratorium
go
 
 
 ALTER TABLE Sesja
        ADD FOREIGN KEY (IdRodzaj_ruchu)
                              REFERENCES Rodzaj_ruchu
go
 
 
 ALTER TABLE Sesja
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer
go
 
 
 ALTER TABLE Sesja_grupa_sesji
        ADD FOREIGN KEY (IdGrupa_sesji)
                              REFERENCES Grupa_sesji
go
 
 
 ALTER TABLE Sesja_grupa_sesji
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Uprawnienia_grupa_atrybutow
        ADD FOREIGN KEY (IdGrupa_atrybutow)
                              REFERENCES Grupa_atrybutow
go
 
 
 ALTER TABLE Uprawnienia_grupa_atrybutow
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik
go
 
 
 
 ALTER TABLE Uprawnienia_sesja
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Uprawnienia_sesja
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik
go
 
 
 ALTER TABLE Uzytkownik_uprawnienia
        ADD FOREIGN KEY (IdUprawnienia_ogolne)
                              REFERENCES Uprawnienia_ogolne
go
 
 
 ALTER TABLE Uzytkownik_uprawnienia
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik
go
 
 
 ALTER TABLE Video
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
 
 
 ALTER TABLE Wartosc_atrybutu_obserwacji
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_obserwacji
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja
go
 
 
  
 ALTER TABLE Wartosc_atrybutu_performera
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer
go
 
 
 ALTER TABLE Wartosc_atrybutu_performera
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go
 
  
 ALTER TABLE Wartosc_atrybutu_segmentu
        ADD FOREIGN KEY (IdSegment)
                              REFERENCES Segment
go
 
 
 ALTER TABLE Wartosc_atrybutu_segmentu
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja
go
 
 
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 
 ALTER TABLE Wartosc_wyliczeniowa
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 