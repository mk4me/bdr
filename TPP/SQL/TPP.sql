-- Pytania
-- 	Wartości wymagane, wartości opcjonalne ? Dopuszczamy wartości NULL we wszystkich polach, z wyjątkiem kluczy obcych oraz identyfikatra wizyty
-- 	Atrybuty dla przechowywania plików ? Zrobione

-- 	Sobota
--		Identyfikacja pacjenta ? (Numer, Hash Imienia oraz Nazwiska)
-- 		Weryfikacja danych z Excela
-- 		Wartości wymagane, wartości opcjonalne ?
-- 		Uwagi na czerwono z pliku Excela
-- 		Dane !!!

use tpp;
go
CREATE TABLE Badanie (
	IdBadanie 			int IDENTITY,
	IdPacjent			int not null,
	Wizyta  			tinyint not null,
	Plec 				tinyint,
	Wiek 				tinyint,
	CzasTrwaniaChoroby 	tinyint,
	WiekZachorowania 	tinyint,
	ObjawyObecnieWyst 	tinyint,
	Drzenie 			bit,
	Sztywnosc 			bit,
	Spowolnienie 		bit,
	DyskinezyObecnie 	bit,
	DyskinezyOdLat 		tinyint,
	FluktuacjeObecnie 	bit,
	FluktuacjeOdLat 	tinyint,
	CzasOFF 			tinyint,
	CzasDyskinez 		tinyint,
	PoprawaPoLDopie 	bit,
	LDopaObecnie 		int,
	AgonistaObecnie 	int,
	LekiInne tinyint,
	DominujacyObjawObecnie 		tinyint,
	ObjawyAutonomiczneObecnie  	tinyint,
	RLS  				bit,
	ObjawyPsychotyczne 	bit,
	Depresja 			bit,
	Otepienie 			tinyint,
	Dyzartria  			bit,
	Dysfagia 			bit,
	RBD 				bit,
	ZaburzenieRuchomosciGalekOcznych bit,
	Apraksja 			bit,
	TestKlaskania 		bit,
	ZaburzeniaWechowe 	bit,
	HY_off 				decimal(2,1),
	HY_on 				decimal(2,1),
	UPDRS_I_off 		tinyint,
	UPDRS_II_off 		tinyint,
	UPDRS_III_off 		tinyint,
	UPDRS_IV_off 		tinyint,
	UPDRS_TOTAL_off 	tinyint,
	UPDRS_18_off 		tinyint,
	UPDRS_19_off  		tinyint,
	UPDRS_20_Head_off 	tinyint,
	UPDRS_20_HandLeg_off tinyint,
	UPDRS_21_off 		tinyint,
	UPDRS_22_off 		tinyint,
	UPDRS_23_off 		tinyint,
	UPDRS_24_off 		tinyint,
	UPDRS_25_off 		tinyint,
	UPDRS_26_off 		tinyint,
	UPDRS_27_off 		tinyint,
	UPDRS_28_off 		tinyint,
	UPDRS_29_off 		tinyint,
	UPDRS_30_off 		tinyint,
	UPDRS_31_off 		tinyint,
	UPDRS_I_on 			tinyint,
	UPDRS_II_on 		tinyint,
	UPDRS_III_on 		tinyint,
	UPDRS_IV_on 		tinyint,
	UPDRS_TOTAL_on 		tinyint,
	UPDRS_18_on 		tinyint,
	UPDRS_19_on  		tinyint,
	UPDRS_20_Head_on 	tinyint,
	UPDRS_20_HandLeg_on tinyint,
	UPDRS_21_on 		tinyint,
	UPDRS_22_on 		tinyint,
	UPDRS_23_on 		tinyint,
	UPDRS_24_on 		tinyint,
	UPDRS_25_on 		tinyint,
	UPDRS_26_on 		tinyint,
	UPDRS_27_on 		tinyint,
	UPDRS_28_on 		tinyint,
	UPDRS_29_on 		tinyint,
	UPDRS_30_on 		tinyint,
	UPDRS_31_on 		tinyint,
	USGsrodmozgowia 	tinyint,
	ScyntygrafiaMozgu  	tinyint,
	Okulografia_off 	tinyint,
	Okulografia_on 		tinyint,
	Posturografia 		tinyint,
	ZapisDrżenia 		tinyint,
	BadWechu 			tinyint,
	WynikWechu 			tinyint,
	CzasOdPoczObjDoWlLDopy tinyint
)
go

-- CREATE INDEX X1Badanie ON Badanie (
--     IdBadanie
-- )
-- go

ALTER TABLE Badanie
	ADD PRIMARY KEY (IdBadanie)
go

CREATE TABLE Pacjent (
	IdPacjent 	int IDENTITY,
)
go

-- CREATE INDEX X1Pacjent ON Pacjent (
--     IdPacjent
-- )
-- go

ALTER TABLE Pacjent
	ADD PRIMARY KEY (IdPacjent)
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