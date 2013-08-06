CREATE DATABASE TPP 

ON
PRIMARY ( NAME = TPP1,
    FILENAME = 'E:\Baza\TPP.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = TPPFS,
    FILENAME = 'E:\Baza\filestream')
LOG ON  ( NAME = TPPlog1,
    FILENAME = 'E:\Baza\TPPlog.ldf')
COLLATE Polish_CI_AS;
GO



use TPP;
go


-- Pytania
-- 	Wartości wymagane, wartości opcjonalne ? Dopuszczamy wartości NULL we wszystkich polach, z wyjątkiem kluczy obcych oraz identyfikatra wizyty
-- 	Atrybuty dla przechowywania plików ? Zrobione

-- 	Sobota
--		Identyfikacja pacjenta ? (Numer, Hash Imienia oraz Nazwiska)
-- 		Weryfikacja danych z Excela
-- 		Wartości wymagane, wartości opcjonalne ?
-- 		Uwagi na czerwono z pliku Excela
-- 		Dane !!!



CREATE TABLE Uzytkownik (
        IdUzytkownik         int IDENTITY,
        Login                varchar(50) NOT NULL UNIQUE,
        Imie                 varchar(30) NOT NULL,
        Nazwisko             varchar(50) NOT NULL,
        Haslo				varbinary(100) default 0x00 not null,
        Email				varchar(50) not null default 'NO_EMAIL',
		Kod_Aktywacji		varchar(20),
		Status				int not null default 0      
 )
go

ALTER TABLE Uzytkownik
	ADD PRIMARY KEY (IdUzytkownik)
go


CREATE INDEX X1Uzytkownik ON Uzytkownik (
     Login
 )
 go



CREATE TABLE Pacjent (
	IdPacjent 	int IDENTITY,
	NumerPacjenta	varchar(20) not null unique,
	RokUrodzenia smallint,
	MiesiacUrodzenia tinyint,
	Plec tinyint,	-- bit + b.d.
	Lokalizacja varchar(10), -- do potwierdzenia !!!
	LiczbaElektrod tinyint	-- do potwierdzenia, czy tutaj !!!
)
go

CREATE INDEX X1Pacjent ON Pacjent (
     NumerPacjenta
 )
 go

ALTER TABLE Pacjent
	ADD PRIMARY KEY (IdPacjent)
go



create table Wizyta (
-- dane epidemiologiczne (część A)
	IdWizyta	int		IDENTITY,
	RodzajWizyty tinyint not null,
	IdPacjent	int	not null,
	DataPrzyjecia date,
	DataWypisu date,
	Wprowadzil int,
	Wyksztalcenie	tinyint,
	Rodzinnosc	tinyint,		-- bit + b.d.
	RokZachorowania	smallint,
	MiesiacZachorowania tinyint,
	PierwszyObjaw	tinyint,
	CzasOdPoczObjDoWlLDopy	tinyint,
	DyskinezyObecnie	tinyint, 	-- bit + b.d.
	CzasDyskinez	decimal(3,1),
	FluktuacjeObecnie	tinyint,	-- bit + b.d.
	FluktuacjeOdLat	decimal(3,1),
	Papierosy	tinyint,
	Kawa	tinyint,
	ZielonaHerbata tinyint,
	Alkohol	tinyint,
	ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,
	Zamieszkanie tinyint,
	NarazenieNaToks	tinyint,
-- wywiad część B
	RLS	tinyint,
	ObjawyPsychotyczne	tinyint,
	Depresja	tinyint,
	Otepienie	decimal(2,1),
	Dyzartria 	tinyint,
	RBD	tinyint,
	ZaburzenieRuchomosciGalekOcznych	tinyint,
	Apraksja	tinyint,
	TestKlaskania	tinyint,
	ZaburzeniaWechowe	tinyint,
	MasaCiala	decimal(4,1),
	Drzenie	tinyint,
	Sztywnosc	tinyint,
	Spowolnienie	tinyint,
	ObjawyInne	tinyint,
	ObjawyInneJakie	varchar(80),
	CzasOFF	tinyint,
	PoprawaPoLDopie	tinyint,
-- leki (część) C
	Ldopa tinyint,
	LDopaObecnie smallint,
	Agonista bit,
	AgonistaObecnie	smallint,
	Amantadyna	bit,
	AmantadynaObecnie smallint,
	MAOBinh bit,
	MAOBinhObecnie smallint,
	COMTinh bit,
	COMTinhObecnie smallint,
	Cholinolityk bit,
	CholinolitykObecnie smallint,
	LekiInne bit,
	LekiInneJakie varchar(50),
-- wywiad część D
	PrzebyteLeczenieOperacyjnePD tinyint,
	Nadcisnienie tinyint,
	BlokeryKanWapn tinyint,
	DominujacyObjawObecnie tinyint,
	DominującyObjawUwagi varchar(50),
	BadanieWechu bit,
	WynikWechu tinyint,
	LimitDysfagii tinyint,
-- Objawy autonomiczne (cz. E)
	WydzielanieSliny tinyint,
	Dysfagia tinyint,
	DysfagiaCzestotliwosc tinyint,
	Nudnosci tinyint,
	Zaparcia tinyint,
	TrudnosciWOddawaniuMoczu tinyint,
	PotrzebaNaglegoOddaniaMoczu tinyint,
	NiekompletneOproznieniePecherza tinyint,
	SlabyStrumienMoczu tinyint,
	CzestotliwowscOddawaniaMoczu tinyint,
	Nykturia tinyint,
	NiekontrolowaneOddawanieMoczu tinyint,
	Omdlenia tinyint,
	ZaburzeniaRytmuSerca tinyint,
	ProbaPionizacyjna tinyint,
	WzrostPodtliwosciTwarzKark tinyint,
	WzrostPotliwosciRamionaDlonie tinyint,
	WzrostPotliwosciKonczynyDolneStopy tinyint,
	SpadekPodtliwosciTwarzKark tinyint,
	SpadekPotliwosciRamionaDlonie tinyint,
	SpadekPotliwosciKonczynyDolneStopy tinyint,
	NietolerancjaWysokichTemp tinyint,
	NietolerancjaNiskichTemp tinyint,
	Lojotok tinyint,
	SpadekLibido tinyint,
	KlopotyOsiagnieciaErekcji tinyint,
	KlopotyUtrzymaniaErekcji tinyint,
-- Badania część F
	PDQ39	tinyint,
	AIMS	tinyint,
	Epworth	tinyint,
	CGI	tinyint,
	FSS	decimal(3,1),
-- Psycholog (część G)
	TestZegara bit,
	MMSE tinyint,
	WAIS_R_Wiadomosci tinyint,
	WAIS_R_PowtarzanieCyfr tinyint,
	SkalaDepresjiBecka tinyint,
	TestFluencjiZwierzeta tinyint,
	TestFluencjiOstre tinyint,
	TestFluencjiK tinyint,
	TestLaczeniaPunktowA tinyint,
	TestLaczeniaPunktowB tinyint,
	TestUczeniaSlownoSluchowego tinyint,
	TestStroopa tinyint,
	TestMinnesota tinyint,
	InnePsychologiczne varchar(150),
-- Badania część F
	Holter bit,
	pH_metriaPrzełyku bit,
	SPECT bit,
	MRI	bit,
	MRIwynik varchar(50),
	USGsrodmozgowia tinyint,
	USGWynik tinyint,
	KwasMoczowy decimal(6,2),
	Genetyka bit,
	GenetykaWynik varchar(50),
	Surowica bit,
	SurowicaPozostało varchar(50)
)
go

alter table Wizyta
        add foreign key (IdPacjent)
                              references Pacjent on delete cascade;
go

alter table Wizyta
        add foreign key (Wprowadzil)
                              references Uzytkownik;
go







CREATE TABLE Plik (
    IdPlik		int IDENTITY,
	IdBadanie	int not null,
    OpisPliku	varchar(100) not null,
    Plik 		varbinary(max) not null,
	rowguid		uniqueidentifier rowguidcol not null unique default NEWSEQUENTIALID(),
	NazwaPliku 	varchar(255) null,	-- zmiana na 100 (20100726) ???
	-- Sciezka 	varchar(100) null,
	-- OstatniaZmiana datetime default getdate() not null,
	-- Zmieniony datetime
)
go
 
ALTER TABLE Plik
    ADD PRIMARY KEY (IdPlik)
go

CREATE INDEX X1Plik ON Plik (
	IdBadanie
)
go


CREATE TABLE SlownikInt (
	Tabela  varchar(30) not null,
	Atrybut	varchar(50) not null,
	Klucz	tinyint not null,
	Definicja	varchar(50) not null
)
go

ALTER TABLE SlownikInt
	ADD PRIMARY KEY (Tabela, Atrybut, Klucz)
go


CREATE INDEX X1SlownikInt ON SlownikInt (
	Tabela, Atrybut
)

CREATE INDEX X2SlownikInt ON SlownikInt (
	Klucz
)
go

CREATE TABLE SlownikDecimal (
	Tabela  varchar(30) not null,
	Atrybut	varchar(50) not null,
	Klucz	decimal(3,1) not null,
	Definicja	varchar(50) not null
)
go

ALTER TABLE SlownikDecimal
	ADD PRIMARY KEY (Tabela, Atrybut, Klucz)
go


CREATE INDEX X1SlownikDecimal ON SlownikDecimal (
	Tabela, Atrybut
)

CREATE INDEX X2SlownikDecimal ON SlownikDecimal (
	Klucz
)
go

CREATE TABLE SlownikVarchar (
	Tabela  varchar(30) not null,
	Atrybut	varchar(50) not null,
	Klucz	varchar(20) not null,
	Definicja	varchar(50) not null
)
go

ALTER TABLE SlownikVarchar
	ADD PRIMARY KEY (Tabela, Atrybut, Klucz)
go


CREATE INDEX X1SlownikVarchar ON SlownikVarchar (
	Tabela, Atrybut
)

CREATE INDEX X2SlownikVarchar ON SlownikVarchar (
	Klucz
)
go
