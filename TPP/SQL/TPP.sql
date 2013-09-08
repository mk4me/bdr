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
	RodzajWizyty decimal(2,1) not null,
	IdPacjent	int	not null,
	DataPrzyjecia date,
	DataOperacji date,
	DataWypisu date,
	Wprowadzil int,
	Wyksztalcenie	tinyint,
	Rodzinnosc	tinyint,		-- bit + b.d.
	RokZachorowania	smallint,
	MiesiacZachorowania tinyint,
	PierwszyObjaw	tinyint,
	CzasOdPoczObjDoWlLDopy	tinyint,	-- miesiecy
	DyskinezyObecnie	tinyint, 	-- bit + b.d.
	CzasDyskinezOdLat	decimal(3,1), -- od lat
	FluktuacjeObecnie	tinyint,	-- bit + b.d.
	FluktuacjeOdLat	decimal(3,1),
	Papierosy	tinyint,
	Kawa	tinyint,
	ZielonaHerbata tinyint,
	Alkohol	tinyint,
	ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,
	Zamieszkanie tinyint,
	NarazenieNaToks	tinyint,
	Uwagi varchar(50),
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
	PrzebyteLeczenieOperacyjnePD tinyint,
	Nadcisnienie tinyint,
	BlokeryKanWapn tinyint,
	DominujacyObjawObecnie tinyint,
	DominujacyObjawUwagi varchar(50),
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
	OpisBadania varchar(2000),
	Wnioski varchar(2000),
-- Badania część H
	Holter bit,
	BadanieWechu bit,
	WynikWechu tinyint,
	LimitDysfagii tinyint,
	pH_metriaPrzełyku bit,
	SPECT bit,
	SPECTWynik varchar(2000),
	MRI	bit,
	MRIwynik varchar(2000),
	USGsrodmozgowia tinyint,
	USGWynik tinyint,
	KwasMoczowy decimal(6,2),
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
	LaboratoryjneInne varchar(1000)
)
go

alter table Wizyta
		add primary key ( IdWizyta)
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
	Tremorometria	tinyint, -- zmiana z bit
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
	TestSchodkowyCzas1	decimal(4,2),
	TestSchodkowyCzas2	decimal(4,2),
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
	WTT	decimal(3,1)
)
go


alter table Badanie
	add primary key (IdBadanie)
go

alter table Badanie
	add foreign key ( IdWizyta )
		references Wizyta;
go
	


CREATE UNIQUE NONCLUSTERED INDEX X1Badanie
ON Badanie ( IdWizyta, DBS, BMT)
go

CREATE TABLE Plik (
    IdPlik		int IDENTITY,
	IdBadanie	int not null,
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
