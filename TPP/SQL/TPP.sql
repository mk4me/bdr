CREATE DATABASE TPP

ON
PRIMARY ( NAME = TPP1,
    FILENAME = 'F:\TPP\TPP.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = TPPFS,
    FILENAME = 'F:\TPP\filestream')
LOG ON  ( NAME = TPPlog1,
    FILENAME = 'F:\TPP\TPPlog.ldf')
COLLATE Polish_CI_AS;
GO

use TPP;
go

/*
CREATE DATABASE TPP_Test

ON
PRIMARY ( NAME = TPP1,
    FILENAME = 'F:\TPP_test\TPP.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = TPPFS,
    FILENAME = 'F:\TPP_test\filestream')
LOG ON  ( NAME = TPPlog1,
    FILENAME = 'F:\TPP_test\TPPlog.ldf')
COLLATE Polish_CI_AS;
GO

use TPP_Test;
go
*/

-- Pytania
-- 	Wartości wymagane, wartości opcjonalne ? Dopuszczamy wartości NULL we wszystkich polach, z wyjątkiem kluczy obcych oraz identyfikatra wizyty
-- 	Atrybuty dla przechowywania plików ? Zrobione





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


--!
CREATE TABLE Pacjent (
	IdPacjent 	int IDENTITY,
	NumerPacjenta	varchar(20) not null unique,
	RokUrodzenia smallint,
	MiesiacUrodzenia tinyint,
	Plec tinyint,
	Lokalizacja varchar(10), 
	LiczbaElektrod tinyint,
	NazwaGrupy varchar(3),	
	Wprowadzil	int not null,
	Zmodyfikowal int not null,
	OstatniaZmiana datetime not null
)
go

CREATE INDEX X1Pacjent ON Pacjent (
     NumerPacjenta
 )
 go

ALTER TABLE Pacjent
	ADD PRIMARY KEY (IdPacjent)
go



/* 
alter table Wizyta drop column MiesiacZachorowania
alter table Wizyta drop column MiesiacBadania
alter table Wizyta drop column RokBadania
alter table Pacjent add NazwaGrupy varchar(3) not null default 'DBS';
alter table Pacjent add Wprowadzil int not null default 1;
alter table Pacjent add Zmodyfikowal int not null default 1;
alter table Pacjent add OstatniaZmiana datetime not null default '2013-10-01T12:12:12.000';

*/
alter table Pacjent
	add foreign key ( Wprowadzil ) references Uzytkownik;
go

alter table Pacjent
	add foreign key ( Zmodyfikowal ) references Uzytkownik;
go



create table Wizyta (
-- dane epidemiologiczne (część A)
	IdWizyta	int		IDENTITY,
	RodzajWizyty decimal(2,1) not null,
	IdPacjent	int	not null,
	DataPrzyjecia date,
	DataWypisu date,
	MasaCiala	decimal(4,1),
	DataOperacji date,
	Wyksztalcenie	tinyint,
	Rodzinnosc	tinyint,		-- bit + b.d.
	RokZachorowania	smallint,
--	MiesiacZachorowania tinyint, -- usunięto 2014-01-03
--	RokBadania smallint, -- usunięto 2014-01-03
--	MiesiacBadania tinyint, -- usunięto 2014-01-03
--	zamast powyższych: atrybut CzasTrwaniaChoroby DD.DD obliczany: today() - styczeń(RokZachorowania)
	PierwszyObjaw	tinyint,
	Drzenie	tinyint,
	Sztywnosc	tinyint,
	Spowolnienie	tinyint,
	ObjawyInne	tinyint,
	ObjawyInneJakie	varchar(80),
	CzasOdPoczObjDoWlLDopy	tinyint,	-- miesiecy
	DyskinezyObecnie	tinyint, 	-- bit + b.d.
	DyskinezyOdLat	decimal(3,1), -- od lat
	FluktuacjeObecnie	tinyint,	-- bit + b.d.
	FluktuacjeOdLat	decimal(3,1),
	CzasDyskinez decimal(3,1), -- h/dobe
	CzasOFF decimal(3,1), -- h/dobe
	PoprawaPoLDopie	tinyint,



-- wywiad część B

	PrzebyteLeczenieOperacyjnePD tinyint,
	Papierosy	tinyint,
	Kawa	tinyint,
	ZielonaHerbata tinyint,
	Alkohol	tinyint,
	ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,
	Zamieszkanie tinyint,
	Uwagi varchar(50),
	Nadcisnienie tinyint,
	BlokeryKanWapn tinyint,
	DominujacyObjawObecnie tinyint,
	DominujacyObjawUwagi varchar(50),
--	ObjawyAutonomiczne tinyint,

	RLS	tinyint,
	ObjawyPsychotyczne	tinyint,
	Depresja	tinyint,
	Otepienie	decimal(2,1),
	Dyzartria 	tinyint,
	DysfagiaObjaw tinyint,
	RBD	tinyint,
	ZaburzenieRuchomosciGalekOcznych	tinyint,
	Apraksja	tinyint,
	TestKlaskania	tinyint,
	ZaburzeniaWechowe	tinyint,






-- leki - dotychczasowe (czesc C)
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
-- parametry stymulacji - dotychczasowe
	L_STIMOpis varchar(30),
	L_STIMAmplitude decimal(5,1),
	L_STIMDuration decimal(5,1),
	L_STIMFrequency decimal(5,1),
	R_STIMOpis varchar(30),
	R_STIMAmplitude decimal(5,1),
	R_STIMDuration decimal(5,1),
	R_STIMFrequency decimal(5,1),
-- leki - przy wipisie
	Wypis_Ldopa tinyint,
	Wypis_LDopaObecnie smallint,
	Wypis_Agonista bit,
	Wypis_AgonistaObecnie	smallint,
	Wypis_Amantadyna	bit,
	Wypis_AmantadynaObecnie smallint,
	Wypis_MAOBinh bit,
	Wypis_MAOBinhObecnie smallint,
	Wypis_COMTinh bit,
	Wypis_COMTinhObecnie smallint,
	Wypis_Cholinolityk bit,
	Wypis_CholinolitykObecnie smallint,
	Wypis_LekiInne bit,
	Wypis_LekiInneJakie varchar(50),
-- parametry stymulacji  - zmienione
	Wypis_L_STIMOpis varchar(30),
	Wypis_L_STIMAmplitude decimal(5,1),
	Wypis_L_STIMDuration decimal(5,1),
	Wypis_L_STIMFrequency decimal(5,1),
	Wypis_R_STIMOpis varchar(30),
	Wypis_R_STIMAmplitude decimal(5,1),
	Wypis_R_STIMDuration decimal(5,1),
	Wypis_R_STIMFrequency decimal(5,1),
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
	WzrostPotliwosciBrzuchPlecy tinyint,
	WzrostPotliwosciKonczynyDolneStopy tinyint,
	SpadekPodtliwosciTwarzKark tinyint,
	SpadekPotliwosciRamionaDlonie tinyint,
	SpadekPotliwosciBrzuchPlecy tinyint,
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
	TestFluencjiZwierzeta varchar(40),
	TestFluencjiOstre varchar(40),
	TestFluencjiK varchar(40),
	TestLaczeniaPunktowA varchar(40),
	TestLaczeniaPunktowB varchar(40),
	--TestUczeniaSlownoSluchowego tinyint, -- będzie zastąpiony 4 parametrami: AVLT średnia, AVLT odroczony, AVLT po 20 minutach, AVLT rozpoznawanie
	TestAVLTSrednia varchar(40),
	TestAVLTOdroczony varchar(40),
	TestAVLTPo20min varchar(40),
	TestAVLTRozpoznawanie varchar(40),
	TestStroopa varchar(40),
	TestMinnesota varchar(40),
	InnePsychologiczne varchar(150),
	OpisBadania varchar(2000),
	Wnioski varchar(2000),
-- Badania część H
	Holter bit,
	BadanieWechu bit,
	WynikWechu tinyint,
	LimitDysfagii tinyint,
	pH_metriaPrzełyku bit,
	SPECT bit,
	-- SPECTWynik - zastapiony wielowarosciowym;
	MRI	bit,
	MRIwynik varchar(2000),
	USGsrodmozgowia bit,
	USGWynik tinyint,
	Genetyka bit,
	GenetykaWynik varchar(50),
	Surowica bit,
	SurowicaPozostało varchar(50),
	-- Laboratoryjne
	Ferrytyna decimal(7,3),
	CRP decimal(7,3),
	NTproCNP decimal(7,3),
	URCA decimal(7,3),
	WitD decimal(7,3),
	CHOL decimal(7,3),
	TGI decimal(7,3),
	HDL decimal(7,3),
	LDL decimal(7,3),
	olLDL decimal(7,3),
	LaboratoryjneInne varchar(1000),
	Wprowadzil	int not null,
	Zmodyfikowal int not null,
	OstatniaZmiana datetime not null
)
go

alter table Wizyta
		add primary key ( IdWizyta)
go


/* 
alter table Wizyta add Wprowadzil int not null default 1;
alter table Wizyta add Zmodyfikowal int not null default 1;
alter table Wizyta add OstatniaZmiana datetime not null default '2013-10-01T12:12:12.000';
*/
alter table Wizyta
	add foreign key ( Wprowadzil ) references Uzytkownik;
go

alter table Wizyta
	add foreign key ( Zmodyfikowal ) references Uzytkownik;
go

alter table Wizyta
        add foreign key (IdPacjent)
                              references Pacjent on delete cascade;
go

alter table Wizyta
        add foreign key (Wprowadzil)
                              references Uzytkownik;
go


create table Badanie  (
	IdBadanie	int	identity,
	IdWizyta	int not null,
	DBS	tinyint not null,
	BMT bit	not null,
-- variant tests A
	UPDRS_I	tinyint,
	UPDRS_II	tinyint,
	UPDRS_18	tinyint,
	UPDRS_19 	tinyint,
	UPDRS_20_FaceLipsChin	tinyint,
	UPDRS_20_RHand	tinyint,
	UPDRS_20_LHand	tinyint,
	UPDRS_20_RFoot	tinyint,
	UPDRS_20_LFoot	tinyint,
	UPDRS_21_RHand	tinyint,
	UPDRS_21_LHand	tinyint,
	UPDRS_22_Neck	tinyint,
	UPDRS_22_RHand	tinyint,
	UPDRS_22_LHand	tinyint,
	UPDRS_22_RFoot	tinyint,
	UPDRS_22_LFoot	tinyint,
	UPDRS_23_R	tinyint,
	UPDRS_23_L	tinyint,
	UPDRS_24_R	tinyint,
	UPDRS_24_L	tinyint,
	UPDRS_25_R	tinyint,
	UPDRS_25_L	tinyint,
	UPDRS_26_R	tinyint,
	UPDRS_26_L	tinyint,
	UPDRS_27	tinyint,
	UPDRS_28	tinyint,
	UPDRS_29	tinyint,
	UPDRS_30	tinyint,
	UPDRS_31	tinyint,
	UPDRS_III	tinyint,
	UPDRS_IV	tinyint,
	UPDRS_TOTAL	tinyint,
	HYscale	decimal(2,1),
	SchwabEnglandScale	tinyint,
	JazzNovo	bit,
	Wideookulograf	bit,
	Latencymeter bit,
	LatencymeterDurationLEFT decimal(6,2),
	LatencymeterLatencyLEFT decimal(6,2),
	LatencymeterAmplitudeLEFT decimal(6,2),
	LatencymeterPeakVelocityLEFT decimal(6,2),
	LatencymeterDurationRIGHT decimal(6,2),
	LatencymeterLatencyRIGHT decimal(6,2),
	LatencymeterAmplitudeRIGHT decimal(6,2),
	LatencymeterPeakVelocityRIGHT decimal(6,2),
	LatencymeterDurationALL decimal(6,2),
	LatencymeterLatencyALL decimal(6,2),
	LatencymeterAmplitudeALL decimal(6,2),
	LatencymeterPeakVelocityALL decimal(6,2),
-- variant tests B
	Tremorometria	bit,
	TremorometriaLEFT_0_1 decimal(7,2),
	TremorometriaLEFT_1_2 decimal(7,2),
	TremorometriaLEFT_2_3 decimal(7,2),
	TremorometriaLEFT_3_4 decimal(7,2),
	TremorometriaLEFT_4_5 decimal(7,2),
	TremorometriaLEFT_5_6 decimal(7,2),
	TremorometriaLEFT_6_7 decimal(7,2),
	TremorometriaLEFT_7_8 decimal(7,2),
	TremorometriaLEFT_8_9 decimal(7,2),
	TremorometriaLEFT_9_10 decimal(7,2),
	TremorometriaLEFT_23_24 decimal(7,2),
	TremorometriaRIGHT_0_1 decimal(7,2),
	TremorometriaRIGHT_1_2 decimal(7,2),
	TremorometriaRIGHT_2_3 decimal(7,2),
	TremorometriaRIGHT_3_4 decimal(7,2),
	TremorometriaRIGHT_4_5 decimal(7,2),
	TremorometriaRIGHT_5_6 decimal(7,2),
	TremorometriaRIGHT_6_7 decimal(7,2),
	TremorometriaRIGHT_7_8 decimal(7,2),
	TremorometriaRIGHT_8_9 decimal(7,2),
	TremorometriaRIGHT_9_10 decimal(7,2),
	TremorometriaRIGHT_23_24 decimal(7,2),	
	TestSchodkowy	bit,
	TestSchodkowyWDol	decimal(4,2),
	TestSchodkowyWGore	decimal(4,2),
	TestMarszu	bit,
	TestMarszuCzas1	decimal(4,2),
	TestMarszuCzas2	decimal(4,2),
	Posturografia	bit,
	MotionAnalysis	bit,
-- variant tests C
	UpAndGo	decimal(3,1),
	UpAndGoLiczby	decimal(3,1),
	UpAndGoKubekPrawa	decimal(3,1),
	UpAndGoKubekLewa	decimal(3,1),
	TST	decimal(3,1),
	TandemPivot	tinyint,
	WTT	decimal(3,1),
	Wprowadzil	int not null,
	Zmodyfikowal int not null,
	OstatniaZmiana datetime not null	
)
go
-- 2014-01-03
/*
EXEC sp_rename 'Badanie.TestSchodkowyCzas1', 'TestSchodkowyWDol', 'COLUMN'
EXEC sp_rename 'Badanie.TestSchodkowyCzas2', 'TestSchodkowyWGore', 'COLUMN'
*/


alter table Badanie
	add primary key (IdBadanie)
go

alter table Badanie
	add foreign key ( IdWizyta )
		references Wizyta;
go
	
/* 
alter table Badanie add Wprowadzil int not null default 1;
alter table Badanie add Zmodyfikowal int not null default 1;
alter table Badanie add OstatniaZmiana datetime not null default '2013-10-01T12:12:12.000';
*/
alter table Badanie
	add foreign key ( Wprowadzil ) references Uzytkownik;

alter table Badanie
	add foreign key ( Zmodyfikowal ) references Uzytkownik;

CREATE UNIQUE NONCLUSTERED INDEX X1Badanie
ON Badanie ( IdWizyta, DBS, BMT)
go

CREATE TABLE Plik (
    IdPlik		int IDENTITY,
	IdWizyta	int,
	IdBadanie	int,
    OpisPliku	varchar(100) not null,
    Plik 		varbinary(max) not null,
	rowguid		uniqueidentifier rowguidcol not null unique default NEWSEQUENTIALID(),
	NazwaPliku 	varchar(255) null,
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

alter table Plik
	add foreign key ( IdBadanie )
		references Badanie;
go


-- drop table SlownikInt

CREATE TABLE SlownikInt (
	IdSlownikInt int identity,
	Tabela  varchar(30) not null,
	Atrybut	varchar(50) not null,
	Klucz	tinyint not null,
	Definicja	varchar(100) not null
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

CREATE TABLE Atrybut (
	IdAtrybut int identity,
	Tabela  varchar(30) not null,
	Nazwa	varchar(50) not null,
)
go

ALTER TABLE Atrybut
	ADD PRIMARY KEY (IdAtrybut)
go


CREATE INDEX X1Atrybut ON Atrybut (
	Tabela, Nazwa
)
go

create table WartoscAtrybutuWizytyInt (
	IdAtrybut int not null,
	IdWizyta int not null,
	Wartosc int
)
go

alter table WartoscAtrybutuWizytyInt
	add foreign key  (IdAtrybut) references Atrybut;
go


alter table WartoscAtrybutuWizytyInt
	add foreign key  (IdWizyta) references Wizyta on delete cascade;
go




create table WartoscAtrybutuBadaniaInt (
	IdAtrybut int not null,
	IdBadanie int not null,
	Wartosc int
)
go

alter table WartoscAtrybutuBadaniaInt
	add foreign key  (IdAtrybut) references Atrybut;
go

alter table WartoscAtrybutuBadaniaInt
	add foreign key  (IdBadanie) references Badanie on delete cascade;
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
