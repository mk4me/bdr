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

create table Atrybut (
	IdAtrybut	int,
	PozycjaDomyslna int,
	Encja	char(1),
	Nazwa	varchar(50)
)
go


CREATE INDEX X1Atrybut ON Atrybut (
     Nazwa
 )
 go


CREATE TABLE Pacjent (
	IdPacjent 	int IDENTITY,
	NumerPacjenta	varchar(20) not null unique,
	RokUrodzenia smallint,
	MiesiacUrodzenia tinyint,
	Plec tinyint,
	Lokalizacja varchar(10), 
	LiczbaElektrod tinyint,
	NazwaGrupy varchar(3),	
	ZakonczenieUdzialu varchar(255),
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
	TestZegara bit,	-- juz bylo; niezmienione
	TestZegaraACE_III tinyint, -- dodane 2015-12-13
	MMSE tinyint,	-- juz bylo; niezmienione
	CLOX1_Rysunek tinyint, -- dodane 2015-03-20
	CLOX2_Kopia tinyint, -- dodane 2015-03-20
	AVLT_proba_1 tinyint,-- dodane 2015-03-20
	AVLT_proba_2 tinyint,-- dodane 2015-03-20
	AVLT_proba_3 tinyint,-- dodane 2015-03-20
	AVLT_proba_4 tinyint,-- dodane 2015-03-20
	AVLT_proba_5 tinyint,-- dodane 2015-03-20
	AVLT_Suma tinyint,-- dodane 2015-03-20
	AVLT_Srednia decimal(4,2), -- dodane 2015-03-20
	AVLT_KrotkieOdroczenie tinyint,-- dodane 2015-03-20
	AVLT_Odroczony20min decimal(4,2), -- dodane 2015-03-20
	AVLT_Rozpoznawanie tinyint,-- dodane 2015-03-20
	AVLT_BledyRozpoznania tinyint,-- dodane 2015-03-20

	TestAVLTSrednia varchar(40), -- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	TestAVLTOdroczony varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	TestAVLTPo20min varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	TestAVLTRozpoznawanie varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane

	CVLT_proba_1 tinyint,-- dodane 2015-03-20
	CVLT_proba_2 tinyint,-- dodane 2015-03-20
	CVLT_proba_3 tinyint,-- dodane 2015-03-20
	CVLT_proba_4 tinyint,-- dodane 2015-03-20
	CVLT_proba_5 tinyint,-- dodane 2015-03-20
	CVLT_Suma tinyint,-- dodane 2015-03-20
	CVLT_OSKO_krotkie_odroczenie decimal(4,2), -- dodane 2015-03-20
	CVLT_OPKO_krotkie_odroczenie_i_pomoc tinyint,-- dodane 2015-03-20
	CVLT_OSDO_po20min decimal(4,2), -- dodane 2015-03-20
	CVLT_OPDO_po20min_i_pomoc tinyint,-- dodane 2015-03-20
	CVLT_perseweracje tinyint,-- dodane 2015-03-20
	CVLT_WtraceniaOdtwarzanieSwobodne tinyint,-- dodane 2015-03-20
	CVLT_wtraceniaOdtwarzanieZPomoca tinyint,-- dodane 2015-03-20
	CVLT_Rozpoznawanie tinyint,-- dodane 2015-03-20
	CVLT_BledyRozpoznania tinyint,-- dodane 2015-03-20
	Benton_JOL tinyint,-- dodane 2015-03-20
	TFZ_ReyaLubInny tinyint, -- dodane 2015-12-23
	WAIS_R_Wiadomosci tinyint,-- juz bylo; niezmienione
	WAIS_R_PowtarzanieCyfr tinyint,-- juz bylo; niezmienione
	WAIS_R_Podobienstwa tinyint, -- dodane 2015-03-20
	BostonskiTestNazywaniaBNT tinyint, -- dodane 2015-03-20
	BMT_SredniCzasReakcji_sek int, -- dodane 2015-03-20
	SkalaDepresjiBecka decimal(4,1),-- juz bylo; zmieniono z tinyint na decimal
	SkalaDepresjiBeckaII decimal(4,1),-- dodane 2015-03-20
	TestFluencjiK tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	TestFluencjiP tinyint, -- dodane 2015-03-20
	TestFluencjiZwierzeta tinyint,-- bylo; ale zmiana z varchar(40) na tinyint
	TestFluencjiOwoceWarzywa tinyint, -- dodane 2015-03-20
	TestFluencjiOstre tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	TestLaczeniaPunktowA varchar(40), -- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	TestLaczeniaPunktowB varchar(40),-- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	TestLaczeniaPunktowA_maly varchar(40), -- dodano 2015-12-23
	TestLaczeniaPunktowB_maly varchar(40), -- dodano 2015-12-23
	ToL_SumaRuchow int, -- dodane 2015-03-20
	ToL_LiczbaPrawidlowych tinyint, -- dodane 2015-03-20
	ToL_CzasInicjowania_sek int, -- dodane 2015-03-20
	ToL_CzasWykonania_sek int, -- dodane 2015-03-20
	ToL_CzasCalkowity_sek int, -- dodane 2015-03-20
	ToL_CzasPrzekroczony tinyint, -- dodane 2015-03-20
	ToL_LiczbaPrzekroczenZasad tinyint, -- dodane 2015-03-20
	ToL_ReakcjeUkierunkowane tinyint, -- dodane 2015-03-20

	-- TestStroopa varchar(40), -- usunieto 2015-03-20
	-- TestMinnesota varchar(40), -- usunieto 2015-03-20
	InnePsychologiczne varchar(150),-- bez zmian
	OpisBadania varchar(2000),-- bez zmian
	Wnioski varchar(2000),-- bez zmian
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
	Saccades bit,
	SaccadesLatencyMeanLEFT decimal(6,2),
	SaccadesLatencyMeanRIGHT decimal(6,2),
	SaccadesDurationLEFT decimal(6,2),
	SaccadesDurationRIGHT decimal(6,2),
	SaccadesAmplitudeLEFT decimal(6,2),
	SaccadesAmplitudeRIGHT decimal(6,2),
	SaccadesPeakVelocityLEFT decimal(6,2),
	SaccadesPeakVelocityRIGHT decimal(6,2),
	SaccadesLatencyALL decimal(6,2),
	SaccadesDurationALL decimal(6,2),
	SaccadesAmplitudeALL decimal(6,2),
	SaccadesPeakVelocityALL decimal(6,2), -- ponizsze dodane 2012-06-04
	Antisaccades bit,
	AntisaccadesPercentOfCorrectLEFT decimal(8,5),
	AntisaccadesPercentOfCorrectRIGHT decimal(8,5),
	AntisaccadesLatencyMeanLEFT decimal(8,5),
	AntisaccadesLatencyMeanRIGHT decimal(8,5),
	AntisaccadesDurationLEFT decimal(8,5),
	AntisaccadesDurationRIGHT decimal(8,5),
	AntisaccadesAmplitudeLEFT decimal(8,5),
	AntisaccadesAmplitudeRIGHT decimal(8,5),
	AntisaccadesPeakVelocityLEFT decimal(8,5),
	AntisaccadesPeakVelocityRIGHT decimal(8,5),
	AntisaccadesPercentOfCorrectALL decimal(8,5),
	AntisaccadesLatencyALL decimal(8,5),
	AntisaccadesDurationALL decimal(8,5),
	AntisaccadesAmplitudeALL decimal(8,5),
	AntisaccadesPeakVelocityALL decimal(8,5),
	POM_Gain_SlowSinus decimal(8,5),
	POM_StDev_SlowSinus decimal(8,5),
	POM_Gain_MediumSinus decimal(8,5),
	POM_StDev_MediumSinus decimal(8,5),
	POM_Gain_FastSinus decimal(8,5),
	POM_StDev_FastSinus decimal(8,5),
	POM_Accuracy_SlowSinus decimal(8,5),
	POM_Accuracy_MediumSinus decimal(8,5),
	POM_Accuracy_FastSinus decimal(8,5), -- /dodane
-- variant tests B
	Tremorometria	bit,
	TremorometriaLEFT bit,
	TremorometriaRIGHT bit,
	TremorometriaLEFT_0_1 decimal(11,5),
	TremorometriaLEFT_1_2 decimal(11,5),
	TremorometriaLEFT_2_3 decimal(11,5),
	TremorometriaLEFT_3_4 decimal(11,5),
	TremorometriaLEFT_4_5 decimal(11,5),
	TremorometriaLEFT_5_6 decimal(11,5),
	TremorometriaLEFT_6_7 decimal(11,5),
	TremorometriaLEFT_7_8 decimal(11,5),
	TremorometriaLEFT_8_9 decimal(11,5),
	TremorometriaLEFT_9_10 decimal(11,5),
	TremorometriaLEFT_23_24 decimal(11,5),
	TremorometriaRIGHT_0_1 decimal(11,5),
	TremorometriaRIGHT_1_2 decimal(11,5),
	TremorometriaRIGHT_2_3 decimal(11,5),
	TremorometriaRIGHT_3_4 decimal(11,5),
	TremorometriaRIGHT_4_5 decimal(11,5),
	TremorometriaRIGHT_5_6 decimal(11,5),
	TremorometriaRIGHT_6_7 decimal(11,5),
	TremorometriaRIGHT_7_8 decimal(11,5),
	TremorometriaRIGHT_8_9 decimal(11,5),
	TremorometriaRIGHT_9_10 decimal(11,5),
	TremorometriaRIGHT_23_24 decimal(11,5),	
	TestSchodkowy	bit,
	TestSchodkowyWDol	decimal(4,2),
	TestSchodkowyWGore	decimal(4,2),
	TestMarszu	bit,
	TestMarszuCzas1	decimal(4,2),
	TestMarszuCzas2	decimal(4,2),
	Posturografia	bit,
	MotionAnalysis	bit,

-- variant test B_0
	Zebris_1_5_Step_width decimal(5,2) NULL,
	Zebris_1_5_Step_length_left decimal(5,2) NULL,
	Zebris_1_5_Step_length_right decimal(5,2) NULL,
	Zebris_1_5_Step_time_left decimal(5,2) NULL,
	Zebris_1_5_Step_time_right decimal(5,2) NULL,
	Zebris_1_5_Stance_phase_left decimal(5,2) NULL,
	Zebris_1_5_Stance_phase_right decimal(5,2) NULL,
	Zebris_1_5_Swing_phase_left decimal(5,2) NULL,
	Zebris_1_5_Swing_phase_right decimal(5,2) NULL,
	Zebris_1_5_Total_Double_Support decimal(5,2) NULL,
	Zebris_1_5_Cadence decimal(5,2) NULL,
	Zebris_3_0_Step_width decimal(5,2) NULL,
	Zebris_3_0_Step_length_left decimal(5,2) NULL,
	Zebris_3_0_Step_length_right decimal(5,2) NULL,
	Zebris_3_0_Step_time_left decimal(5,2) NULL,
	Zebris_3_0_Step_time_right decimal(5,2) NULL,
	Zebris_3_0_Stance_phase_left decimal(5,2) NULL,
	Zebris_3_0_Stance_phase_right decimal(5,2) NULL,
	Zebris_3_0_Swing_phase_left decimal(5,2) NULL,
	Zebris_3_0_Swing_phase_right decimal(5,2) NULL,
	Zebris_3_0_Total_Double_Support decimal(5,2) NULL,
	Zebris_3_0_Cadence decimal(5,2) NULL,
-- variant test B_1

	Otwarte_Srednia_C_o_P_X int,				-- (wartości liczbowe - liczby całkowite od -500 do +500)
	Otwarte_Srednia_C_o_P_Y int,				-- (wartości liczbowe - liczby całkowite od -500 do +500)
	Otwarte_Srednia_P_T_Predkosc_mm_sec int,	-- (wartości liczbowe - liczby całkowite od 0 do 500)
	Otwarte_Srednia_P_B_Predkosc_mm_sec int,	-- (wartości liczbowe - liczby całkowite od 0 do 500)
	Otwarte_Perimeter_mm int,					-- (wartości liczbowe - liczby całkowite od 0 do 10 000)
	Otwarte_PoleElipsy_mm2 int,					-- (wartości liczbowe - liczby całkowite od 0 do 10 000)

	Zamkniete_Srednia_C_o_P_X int,				-- (wartości liczbowe - liczby całkowite od -500 do +500)
	Zamkniete_Srednia_C_o_P_Y int,				-- (wartości liczbowe - liczby całkowite od -500 do +500)
	Zamkniete_Srednia_P_T_Predkosc_mm_sec int,	-- (wartości liczbowe - liczby całkowite od 0 do 500)
	Zamkniete_Srednia_P_B_Predkosc_mm_sec int,	-- (wartości liczbowe - liczby całkowite od 0 do 500)
	Zamkniete_Perimeter_mm int,					-- (wartości liczbowe - liczby całkowite od 0 do 10 000)
	Zamkniete_PoleElipsy_mm2 int,				-- (wartości liczbowe - liczby całkowite od 0 do 10 000)

	WspolczynnikPerymetru_E_C_E_O_obie_stopy int,		-- (wartości liczbowe - liczby całkowite od 0 do 1 000)
	WspolczynnikPowierzchni_E_C_E_O_obie_stopy int,	-- (wartości liczbowe - liczby całkowite od 0 do 1 000)

	Biofeedback_Srednia_C_o_P_X int,				-- (wartości liczbowe - liczby całkowite od -500 do +500)
	Biofeedback_Srednia_C_o_P_Y int,				-- (wartości liczbowe - liczby całkowite od -500 do +500)
	Biofeedback_Srednia_P_T_Predkosc_mm_sec int,	-- (wartości liczbowe - liczby całkowite od 0 do 500)
	Biofeedback_Srednia_P_B_Predkosc_mm_sec int,	-- (wartości liczbowe - liczby całkowite od 0 do 500)
	Biofeedback_Perimeter_mm int,					-- (wartości liczbowe - liczby całkowite od 0 do 10 000)
	Biofeedback_PoleElipsy_mm2 int,					-- (wartości liczbowe - liczby całkowite od 0 do 10 000)

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
	PodRodzajPliku varchar(2),
	NazwaEksportowaPliku varchar(50)
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

create table Kolumna (
	IdKolumna	int	identity,
	PozycjaDomyslna int,
	Encja	varchar(30),
	Nazwa	varchar(50),
	CustPodzapytanie varchar(200) NULL
)
go


CREATE INDEX X1Kolumna ON Kolumna (
     Nazwa
 )
 go

CREATE INDEX X2Kolumna ON Kolumna (
     Encja, Nazwa
 )
 go
