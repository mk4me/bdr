-- TODO: zbudowac dla Sesji tryger on delete kasujacy pliki obserwacji
use Motion;
go 
 CREATE TABLE Atrybut (
        IdAtrybut            int IDENTITY,
        IdGrupa_atrybutow    int NOT NULL,
        Nazwa                varchar(100) NOT NULL,
        Typ_danych           varchar(20) NOT NULL,
        Wyliczeniowy		bit NULL,
        Plugin varchar(100) NULL,
        Podtyp_danych	varchar(20) NULL,
        Jednostka	varchar(10) NULL
        
 )
go
 
 CREATE INDEX X1Atrybut ON Atrybut
 (
        IdGrupa_atrybutow
 )
go
 
 
 ALTER TABLE Atrybut
        ADD PRIMARY KEY (IdAtrybut)
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
  
 CREATE TABLE Obserwacja (
        IdObserwacja         int IDENTITY,
        IdSesja              int NOT NULL,
        Opis_obserwacji      varchar(100) NOT NULL
 )
go
 
 CREATE INDEX X1Obserwacja ON Obserwacja
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Obserwacja
        ADD PRIMARY KEY (IdObserwacja)
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
 
 CREATE TABLE Konfiguracja_performera (
        IdKonfiguracja_performera	int IDENTITY,
        IdSesja                int NOT NULL,
        IdPerformer            int NOT NULL
 )
go

 CREATE INDEX X1Konfiguracja_performera ON Konfiguracja_performera
 (
        IdSesja
 )
go
 
 CREATE INDEX X2Konfiguracja_performera ON Konfiguracja_performera
 (
        IdPerformer
 )
go
 
 
 ALTER TABLE Konfiguracja_performera
        ADD PRIMARY KEY (IdKonfiguracja_performera)
go
 
 
 
 CREATE TABLE Plik (
        IdPlik              int IDENTITY,
		IdKonfiguracja_pomiarowa int NULL,
        IdSesja              int NULL,
        IdObserwacja         int NULL,
        Opis_pliku          varchar(100) NOT NULL,
        Plik                varbinary(max) filestream not null,
		rowguid				uniqueidentifier rowguidcol not null unique,
		Nazwa_pliku         varchar(255) null,
		Sciezka				varchar(100) null
 )
go


 CREATE INDEX X1Plik ON Plik
 (
        IdObserwacja
 )
go
 
 CREATE INDEX X2Plik ON Plik
 (
        IdSesja
 )
go

 CREATE INDEX X3Plik ON Plik
 (
        IdKonfiguracja_pomiarowa
 )
go 
 
 ALTER TABLE Plik
        ADD PRIMARY KEY (IdPlik)
go 

CREATE TABLE Konfiguracja_pomiarowa
(
	IdKonfiguracja_pomiarowa int IDENTITY,
	Nazwa varchar(50) UNIQUE NOT NULL,
	Opis varchar(255) NULL,
	Rodzaj varchar(50) NOT NULL
)
go

ALTER TABLE Konfiguracja_pomiarowa
        ADD PRIMARY KEY (IdKonfiguracja_pomiarowa)
go


 CREATE INDEX X1Konfiguracja_pomiarowa ON Konfiguracja_pomiarowa
 (
        Nazwa
 )
go


CREATE TABLE Pomiar
(
	IdPomiar int IDENTITY,
	IdObserwacja int NOT NULL,
	IdKonfiguracja_pomiarowa int NOT NULL
)
go

ALTER TABLE Pomiar
        ADD PRIMARY KEY (IdPomiar)
go

 ALTER TABLE Pomiar
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja on delete cascade; -- wymaga dodania!
go 
 CREATE INDEX X1Pomiar ON Pomiar
 (
        IdObserwacja
 )
go

 CREATE INDEX X2Pomiar ON Pomiar
 (
        IdKonfiguracja_pomiarowa
 )
go

 ALTER TABLE Pomiar
        ADD FOREIGN KEY (IdKonfiguracja_pomiarowa)
                              REFERENCES Konfiguracja_pomiarowa
go 


CREATE TABLE Wynik_pomiaru
(
	IdPomiar int NOT NULL,
	IdPlik int NOT NULL,
	IdPlikPodglad int NULL
)
go

/* Do usuni�cia???

 ALTER TABLE Wynik_pomiaru
        ADD FOREIGN KEY (IdPomiar)
                              REFERENCES Pomiar
go 
 CREATE INDEX X1Wynik_pomiaru ON Wynik_pomiaru
 (
        IdPomiar
 )
go

 ALTER TABLE Wynik_pomiaru
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik
go 
 CREATE INDEX X2Wynik_pomiaru ON Wynik_pomiaru
 (
        IdPlik
 )
go

*/

CREATE TABLE Pomiar_performer
(
	IdPomiar int NOT NULL,
	IdPerformer int NOT NULL
)
go

 ALTER TABLE Pomiar_performer
        ADD FOREIGN KEY (IdPomiar)
                              REFERENCES Pomiar on delete cascade; -- wymaga dodania!
go 
 CREATE INDEX X1Pomiar_performer ON Pomiar_performer
 (
        IdPomiar
 )
go

 ALTER TABLE Pomiar_performer
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer
go 
 CREATE INDEX X2Pomiar_performer ON Pomiar_performer
 (
        IdPerformer
 )
go 
 
 
 
 CREATE TABLE Rodzaj_ruchu (
        IdRodzaj_ruchu       int IDENTITY,
        Nazwa                varchar(50) NOT NULL
 )
go
 
 
 ALTER TABLE Rodzaj_ruchu
        ADD PRIMARY KEY (IdRodzaj_ruchu)
go
 
 
 
 CREATE TABLE Sesja (
        IdSesja              int IDENTITY,
        IdUzytkownik         int NOT NULL,
        IdLaboratorium       int NOT NULL,
        IdRodzaj_ruchu       int NULL,
        Data                 datetime NOT NULL,
        Opis_sesji           varchar(100) NULL,
        Publiczna			bit not null default 0,
        PublicznaZapis		 bit not null default 0
 )
go
 

 
 CREATE INDEX X1Sesja ON Sesja
 (
        IdRodzaj_ruchu
 )
go
 
 CREATE INDEX X2Sesja ON Sesja
 (
        IdLaboratorium
 )
go
 
 CREATE INDEX X3Sesja ON Sesja
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
 
 CREATE INDEX X1Sesja_grupa_sesji ON Sesja_grupa_sesji
 (
        IdSesja
 )
go
 
 CREATE INDEX X2Sesja_grupa_sesji ON Sesja_grupa_sesji
 (
        IdGrupa_sesji
 )
go
 
 
 ALTER TABLE Sesja_grupa_sesji
        ADD PRIMARY KEY (IdSesja, IdGrupa_sesji)
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
 
 
 CREATE TABLE Uprawnienia_grupa_atrybutow (
        IdUzytkownik         int NOT NULL,
        IdGrupa_atrybutow    int NOT NULL,
        Odczyt               bit,
        Zapis                bit
 )
go
 
 CREATE INDEX X1Uprawnienia_grupa_atrybutow ON Uprawnienia_grupa_atrybutow
 (
        IdUzytkownik
 )
go
 
 CREATE INDEX X2Uprawnienia_grupa_atrybutow ON Uprawnienia_grupa_atrybutow
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
 
 CREATE INDEX X1Uprawnienia_sesja ON Uprawnienia_sesja
 (
        IdUzytkownik
 )
go
 
 CREATE INDEX X2Uprawnienia_sesja ON Uprawnienia_sesja
 (
        IdSesja
 )
go
 
 
 ALTER TABLE Uprawnienia_sesja
        ADD PRIMARY KEY (IdUzytkownik, IdSesja)
go
 
 
 CREATE TABLE Uzytkownik_uprawnienia (
        IdUzytkownik         int NOT NULL,
        IdUprawnienia_ogolne int NOT NULL
 )
go
 
 CREATE INDEX X1Uzytkownik_uprawnienia ON Uzytkownik_uprawnienia
 (
        IdUzytkownik
 )
go
 
 CREATE INDEX X2Uzytkownik_uprawnienia ON Uzytkownik_uprawnienia
 (
        IdUprawnienia_ogolne
 )
go
 
 
 ALTER TABLE Uzytkownik_uprawnienia
        ADD PRIMARY KEY (IdUzytkownik, IdUprawnienia_ogolne)
go
 
 
 CREATE TABLE Wartosc_atrybutu_obserwacji (
        IdObserwacja         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X1Wartosc_atrybutu_obserwacji ON Wartosc_atrybutu_obserwacji
 (
        IdObserwacja
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_obserwacji ON Wartosc_atrybutu_obserwacji
 (
        IdAtrybut
 )
go

 CREATE INDEX X3Wartosc_atrybutu_obserwacji ON Wartosc_atrybutu_obserwacji
 (
        Wartosc_Id
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
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X1Wartosc_atrybutu_performera ON Wartosc_atrybutu_performera
 (
        IdAtrybut
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_performera ON Wartosc_atrybutu_performera
 (
        IdPerformer
 )
go
  CREATE INDEX X3Wartosc_atrybutu_performera ON Wartosc_atrybutu_performera
 (
        Wartosc_Id
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
 
 CREATE INDEX X1Wartosc_atrybutu_pliku ON Wartosc_atrybutu_pliku
 (
        IdPlik
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_pliku ON Wartosc_atrybutu_pliku
 (
        IdAtrybut
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD PRIMARY KEY (IdAtrybut, IdPlik)
go
 
 
 CREATE TABLE Wartosc_atrybutu_sesji (
        IdAtrybut            int NOT NULL,
        IdSesja              int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X39Wartosc_atrybutu_sesji ON Wartosc_atrybutu_sesji
 (
        IdAtrybut
 )
go
 
 CREATE INDEX X40Wartosc_atrybutu_sesji ON Wartosc_atrybutu_sesji
 (
        IdSesja
 )
go

 CREATE INDEX X3Wartosc_atrybutu_sesji ON Wartosc_atrybutu_sesji
 (
        Wartosc_Id
 )
go
 
 
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD PRIMARY KEY (IdAtrybut, IdSesja)
go
 
 
  CREATE TABLE Wartosc_atrybutu_konfiguracji_pomiarowej (
        IdKonfiguracja_pomiarowa         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X1Wartosc_atrybutu_konfiguracji_pomiarowej ON Wartosc_atrybutu_konfiguracji_pomiarowej
 (
        IdKonfiguracja_pomiarowa
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_konfiguracji_pomiarowej ON Wartosc_atrybutu_konfiguracji_pomiarowej
 (
        IdAtrybut
 )
go

 CREATE INDEX X3Wartosc_atrybutu_konfiguracji_pomiarowej ON Wartosc_atrybutu_konfiguracji_pomiarowej
 (
        Wartosc_Id
 )
go 
 
 ALTER TABLE Wartosc_atrybutu_konfiguracji_pomiarowej
        ADD PRIMARY KEY (IdKonfiguracja_pomiarowa, IdAtrybut)
go
 
 
  ALTER TABLE Wartosc_atrybutu_konfiguracji_pomiarowej
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_konfiguracji_pomiarowej
        ADD FOREIGN KEY (IdKonfiguracja_pomiarowa)
                              REFERENCES Konfiguracja_pomiarowa on delete cascade;
go
 
  CREATE TABLE Wartosc_atrybutu_pomiaru (
        IdPomiar         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X1Wartosc_atrybutu_pomiaru ON Wartosc_atrybutu_pomiaru
 (
        IdPomiar
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_pomiaru ON Wartosc_atrybutu_pomiaru
 (
        IdAtrybut
 )
go

 CREATE INDEX X3Wartosc_atrybutu_pomiaru ON Wartosc_atrybutu_pomiaru
 (
        Wartosc_Id
 )
go  
 
 ALTER TABLE Wartosc_atrybutu_pomiaru
        ADD PRIMARY KEY (IdPomiar, IdAtrybut)
go
 
 
  ALTER TABLE Wartosc_atrybutu_pomiaru
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_pomiaru
        ADD FOREIGN KEY (IdPomiar)
                              REFERENCES Pomiar on delete cascade;
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
	IdUzytkownik int not null
)
go
 ALTER TABLE Predykat
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go

ALTER TABLE Predykat
		ADD PRIMARY KEY (IdUzytkownik, IdPredykat)
go

 CREATE INDEX XPredykat ON Predykat
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
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go

ALTER TABLE Koszyk
		ADD PRIMARY KEY ( IdKoszyk )
go

 CREATE INDEX X1Koszyk ON Koszyk
 (
        IdKoszyk
 )
go

create table Performer_Koszyk (
        IdPerformer	int NOT NULL,
        IdKoszyk	int NOT NULL
 )
go
 
 CREATE INDEX X1Performer_Koszyk ON Performer_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX X2Performer_Koszyk ON Performer_Koszyk
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
 
 CREATE INDEX X1Sesja_Koszyk ON Sesja_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX X2Sesja_Koszyk ON Sesja_Koszyk
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
 
 CREATE INDEX X1Obserwacja_Koszyk ON Obserwacja_Koszyk
 (
        IdKoszyk
 )
go
 
 CREATE INDEX X2Obserwacja_Koszyk ON Obserwacja_Koszyk
 (
        IdObserwacja
 )
go
 
ALTER TABLE Obserwacja_Koszyk
        ADD PRIMARY KEY (IdKoszyk, IdObserwacja)
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
 CREATE INDEX X1Materializacja_Atrybutu_Performera ON Materializacja_Atrybutu_Performera
 (
        IdPerformer
 )
go
 CREATE INDEX X2Materializacja_Atrybutu_Performera ON Materializacja_Atrybutu_Performera
 (
        IdAtrybut
 )
go
 CREATE INDEX X3Materializacja_Atrybutu_Performera ON Materializacja_Atrybutu_Performera
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
 CREATE INDEX X1Materializacja_Atrybutu_Sesji ON Materializacja_Atrybutu_Sesji
 (
        IdSesja
 )
go
 CREATE INDEX X2Materializacja_Atrybutu_Sesji ON Materializacja_Atrybutu_Sesji
 (
        IdAtrybut
 )
go
 CREATE INDEX X3Materializacja_Atrybutu_Sesji ON Materializacja_Atrybutu_Sesji
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
 CREATE INDEX X1Materializacja_Atrybutu_Obserwacji ON Materializacja_Atrybutu_Obserwacji
 (
        IdObserwacja
 )
go
 CREATE INDEX X2Materializacja_Atrybutu_Obserwacji ON Materializacja_Atrybutu_Obserwacji
 (
        IdAtrybut
 )
go
 CREATE INDEX X3Materializacja_Atrybutu_Obserwacji ON Materializacja_Atrybutu_Obserwacji
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
 CREATE INDEX X1Materializacja_Atrybutu_Pliku ON Materializacja_Atrybutu_Pliku
 (
        IdPlik
 )
go
 CREATE INDEX X2Materializacja_Atrybutu_Pliku ON Materializacja_Atrybutu_Pliku
 (
        IdAtrybut
 )
go
 CREATE INDEX X3Materializacja_Atrybutu_Pliku ON Materializacja_Atrybutu_Pliku
 (
        IdUzytkownik
 )
go




 CREATE INDEX X1Wartosc_wyliczeniowa ON Wartosc_wyliczeniowa
 (
        IdAtrybut
 )
go
 
 CREATE INDEX X2Plik_udostepniony ON Plik_udostepniony
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
                              REFERENCES Grupa_atrybutow on delete cascade;
go
 
 ALTER TABLE Obserwacja
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja on delete cascade; -- wymaga dodania!
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
 
 
 ALTER TABLE Sesja_grupa_sesji
        ADD FOREIGN KEY (IdGrupa_sesji)
                              REFERENCES Grupa_sesji
go
 
 
 ALTER TABLE Sesja_grupa_sesji
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja on delete cascade; -- wymaga dodania!
go
 
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdKonfiguracja_pomiarowa)
                              REFERENCES Konfiguracja_pomiarowa on delete cascade;
go

-- Usunieto on cascade delete, by uniknac multiple paths
 ALTER TABLE Plik
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja on delete no action;
go

 ALTER TABLE Plik
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja on delete cascade;
go
 
 ALTER TABLE Uprawnienia_grupa_atrybutow
        ADD FOREIGN KEY (IdGrupa_atrybutow)
                              REFERENCES Grupa_atrybutow on delete cascade; -- wymaga dodania!
go
 
 
 ALTER TABLE Uprawnienia_grupa_atrybutow
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
 
 
 
 ALTER TABLE Uprawnienia_sesja
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja on delete cascade; -- wymaga dodania!
go
 
 
 ALTER TABLE Uprawnienia_sesja
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
 
 
 ALTER TABLE Uzytkownik_uprawnienia
        ADD FOREIGN KEY (IdUprawnienia_ogolne)
                              REFERENCES Uprawnienia_ogolne on delete cascade; -- wymaga dodania!
go
 
 
ALTER TABLE Uzytkownik_uprawnienia
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
 

ALTER TABLE Wartosc_atrybutu_obserwacji
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_obserwacji
        ADD FOREIGN KEY (IdObserwacja)
                              REFERENCES Obserwacja on delete cascade;
go
 
 
  
 ALTER TABLE Wartosc_atrybutu_performera
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_performera
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
ALTER TABLE Wartosc_atrybutu_pliku
        ADD FOREIGN KEY (IdPlik)
                              REFERENCES Plik on delete cascade; 
go
  
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD FOREIGN KEY (IdSesja)
                              REFERENCES Sesja on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_sesji
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_wyliczeniowa
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go

 ALTER TABLE Performer_Koszyk
        ADD FOREIGN KEY (IdKoszyk) REFERENCES Koszyk on delete cascade; -- wymaga dodania!
go
 ALTER TABLE Performer_Koszyk
        ADD FOREIGN KEY (IdPerformer) REFERENCES Performer on delete cascade; -- wymaga dodania!
go
 ALTER TABLE Sesja_Koszyk
        ADD FOREIGN KEY (IdKoszyk) REFERENCES Koszyk on delete cascade; -- wymaga dodania!
go
 ALTER TABLE Sesja_Koszyk
        ADD FOREIGN KEY (IdSesja) REFERENCES Sesja on delete cascade; -- wymaga dodania!
go
 ALTER TABLE Obserwacja_Koszyk
        ADD FOREIGN KEY (IdKoszyk) REFERENCES Koszyk on delete cascade; -- wymaga dodania!
go
 ALTER TABLE Obserwacja_Koszyk
        ADD FOREIGN KEY (IdObserwacja) REFERENCES Obserwacja on delete cascade; -- wymaga dodania!
go
-- zalezonosci cascade delete dla materializacji - pominiete na diagramie
ALTER TABLE Materializacja_Atrybutu_Performera
        ADD PRIMARY KEY (IdUzytkownik, IdAtrybut, IdPerformer)
go
ALTER TABLE Materializacja_Atrybutu_Performera
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Performera
        ADD FOREIGN KEY (IdAtrybut) REFERENCES Atrybut on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Performera
        ADD FOREIGN KEY (IdPerformer) REFERENCES Performer on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Sesji
        ADD PRIMARY KEY (IdUzytkownik, IdAtrybut, IdSesja)
go
ALTER TABLE Materializacja_Atrybutu_Sesji
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Sesji
        ADD FOREIGN KEY (IdAtrybut) REFERENCES Atrybut on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Sesji
        ADD FOREIGN KEY (IdSesja) REFERENCES Sesja on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Obserwacji
        ADD PRIMARY KEY (IdUzytkownik, IdAtrybut, IdObserwacja)
go
ALTER TABLE Materializacja_Atrybutu_Obserwacji
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Obserwacji
        ADD FOREIGN KEY (IdAtrybut) REFERENCES Atrybut on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Obserwacji
        ADD FOREIGN KEY (IdObserwacja) REFERENCES Obserwacja on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Pliku
        ADD PRIMARY KEY (IdUzytkownik, IdAtrybut, IdPlik)
go
ALTER TABLE Materializacja_Atrybutu_Pliku
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Pliku
        ADD FOREIGN KEY (IdAtrybut) REFERENCES Atrybut on delete cascade; -- wymaga dodania!
go
ALTER TABLE Materializacja_Atrybutu_Pliku
        ADD FOREIGN KEY (IdPlik) REFERENCES Plik on delete cascade; -- wymaga dodania!
go
-- /zalezonosci cascade delete dla materializacji - pominiete na diagramie
ALTER TABLE Predykat
        ADD FOREIGN KEY (IdPredykat, IdUzytkownik) REFERENCES Predykat
go

-- Usunieto kaskade, aby uniknac wielu sciezek propagacji usuwania
ALTER TABLE Wartosc_Atrybutu_performera
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik on delete no action;
go

ALTER TABLE Wartosc_Atrybutu_sesji
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik on delete no action;
go


ALTER TABLE Wartosc_Atrybutu_obserwacji
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik on delete no action;
go

ALTER TABLE Wartosc_Atrybutu_konfiguracji_pomiarowej
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik on delete no action;
go

ALTER TABLE Wartosc_Atrybutu_pomiaru
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik on delete no action;
go
-- /Usunieto kaskade, aby uniknac wielu sciezek propagacji usuwania


ALTER TABLE Konfiguracja_performera
        ADD FOREIGN KEY (IdPerformer) REFERENCES Performer on delete cascade;
go

ALTER TABLE Konfiguracja_performera
        ADD FOREIGN KEY (IdSesja) REFERENCES Sesja on delete cascade;
go

----------- NEW

  CREATE TABLE Wartosc_atrybutu_konfiguracji_performera (
        IdKonfiguracja_performera         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X1Wartosc_atrybutu_konfiguracji_performera ON Wartosc_atrybutu_konfiguracji_performera
 (
        IdKonfiguracja_performera
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_konfiguracji_performera ON Wartosc_atrybutu_konfiguracji_performera
 (
        IdAtrybut
 )
go

 CREATE INDEX X3Wartosc_atrybutu_konfiguracji_performera ON Wartosc_atrybutu_konfiguracji_performera
 (
        Wartosc_Id
 )
go 
 
 ALTER TABLE Wartosc_atrybutu_konfiguracji_performera
        ADD PRIMARY KEY (IdKonfiguracja_performera, IdAtrybut)
go
 
 
  ALTER TABLE Wartosc_atrybutu_konfiguracji_performera
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_konfiguracji_performera
        ADD FOREIGN KEY (IdKonfiguracja_performera)
                              REFERENCES Konfiguracja_performera on delete cascade;
go
ALTER TABLE Wartosc_Atrybutu_konfiguracji_performera
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik
go


CREATE TABLE Widocznosc_atrybutu (
        IdUzytkownik         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wyswietlic        bit,
 )
go
 
CREATE INDEX X1Widocznosc_atrybutu ON Widocznosc_atrybutu
 (
        IdUzytkownik
 )
go
 
CREATE INDEX X2Widocznosc_atrybutu ON Widocznosc_atrybutu
 (
        IdAtrybut
 )
go

ALTER TABLE Widocznosc_atrybutu
        ADD PRIMARY KEY (IdUzytkownik, IdAtrybut)
go
 
 
ALTER TABLE Widocznosc_atrybutu
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
ALTER TABLE Widocznosc_atrybutu
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade;
go

CREATE TABLE Widocznosc_grupy_atrybutow (
        IdUzytkownik         int NOT NULL,
        IdGrupa_atrybutow            int NOT NULL,
        Wyswietlic        bit,
 )
go
 
CREATE INDEX X1Widocznosc_grupy_atrybutow ON Widocznosc_grupy_atrybutow
 (
        IdUzytkownik
 )
go
 
CREATE INDEX X2Widocznosc_grupy_atrybutow ON Widocznosc_grupy_atrybutow
 (
        IdGrupa_atrybutow
 )
go

ALTER TABLE Widocznosc_grupy_atrybutow
        ADD PRIMARY KEY (IdUzytkownik, IdGrupa_atrybutow)
go
 
 
ALTER TABLE Widocznosc_grupy_atrybutow
        ADD FOREIGN KEY (IdGrupa_atrybutow)
                              REFERENCES Grupa_atrybutow on delete cascade;
go
 
 
ALTER TABLE Widocznosc_grupy_atrybutow
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade;
go



/*
SELECT OBJECT_NAME(id) AS TableName, OBJECT_NAME(constid) AS ConstraintName
FROM sysconstraints where OBJECT_NAME(id) = 'Uzytkownik'

declare @sql nvarchar(200);
WHILE EXISTS(SELECT * FROM sys.default_constraints where parent_object_id = OBJECT_ID('dbo.Sesja'))
BEGIN
    select    @sql = 'ALTER TABLE Sesja DROP CONSTRAINT ' + name
    FROM sys.default_constraints where parent_object_id = OBJECT_ID('dbo.Sesja')
    exec    sp_executesql @sql
END

ALTER TABLE Sesja WITH NOCHECK
ADD CONSTRAINT DF__Sesja__Publiczna DEFAULT 0 FOR Publiczna
go
ALTER TABLE Sesja WITH NOCHECK
ADD CONSTRAINT DF__Sesja__PublicznaZapis DEFAULT 0 FOR PublicznaZapis
go

ALTER TABLE Uzytkownik WITH NOCHECK
ADD CONSTRAINT U_UzytkownikLogin UNIQUE (Login)
go
*/

-- TO DO: Zrobi� trigger do usuwania pliku przy wykonywaniu stosownej ClearAttributeValue.