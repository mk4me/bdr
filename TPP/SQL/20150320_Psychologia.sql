use TPP;
go



/*

Zmiany obejmuja:
* dodanie odpowiednich kolumn w tabeli Wizyta
* nowa procedure zapisu update_examination_questionnaire_partG_mod_1 zamiast update_examination_questionnaire_partG
* odpowiednie zmiany w get_database_copy

MMSE:    (cyfry 0-30, liczby całkowite)

Test zegara: (do wyboru puste/prawidłowy/nieprawidłowy)
CLOX 1-rysunek:  (cyfry 0-30, liczby całkowite)
CLOX 2-kopia: (cyfry 0-30, liczby całkowite)

AVLT próba 1: (cyfry 0-30, liczby całkowite)
AVLT próba 2: (cyfry 0-30, liczby całkowite)
AVLT próba 3: (cyfry 0-30, liczby całkowite)
AVLT próba 4: (cyfry 0-30, liczby całkowite)
AVLT próba 5: (cyfry 0-30, liczby całkowite)
AVLT suma: (liczby 0-150, liczby całkowite)
AVLT średnia: (liczby 0-30, liczby do 2 miejsc po przecinku)
AVLT krótkie odroczenie: (cyfry 0-30, liczby całkowite)
AVLT odroczony po 20 min: (cyfry 0-30, liczby całkowite)
AVLT rozpoznawanie: (cyfry 0-30, liczby całkowite)
AVLT błędy rozpoznania: (cyfry 0-30, liczby całkowite)

CVLT próba 1: (cyfry 0-30, liczby całkowite)
CVLT próba 2: (cyfry 0-30, liczby całkowite)
CVLT próba 3: (cyfry 0-30, liczby całkowite)
CVLT próba 4: (cyfry 0-30, liczby całkowite)
CVLT próba 5: (cyfry 0-30, liczby całkowite)
CVLT suma: (liczby 0-150, liczby całkowite)
CVLT OSKO krótkie odroczenie: (liczby 0-30, liczby do 2 miejsc po przecinku)
CVLT OPKO krótkie odroczenie i pomoc: (cyfry 0-30, liczby całkowite)
CVLT OSDO po 20min: (liczby 0-30, liczby do 2 miejsc po przecinku)
CVLT OPDO po 20min i pomoc: (cyfry 0-30, liczby całkowite)
CVLT perseweracje: (cyfry 0-30, liczby całkowite)
CVLT wtrącenia w odtwarzaniu swobodnym: (cyfry 0-30, liczby całkowite)
CVLT wtrącenia w odtwarzaniu z pomocą: (cyfry 0-30, liczby całkowite)
CVLT rozpoznawanie: (cyfry 0-30, liczby całkowite)
CVLT błędy rozpoznania: (cyfry 0-30, liczby całkowite)

Benton JOL: (cyfry 0-50, liczby całkowite)

Test fluencji - K: (cyfry 0-50, liczby całkowite)
Test fluencji - P: (cyfry 0-50, liczby całkowite)
Test fluencji - zwierzęta: (cyfry 0-50, liczby całkowite)
Test fluencji - owoce i warzywa: (cyfry 0-50, liczby całkowite)
Test fluencji - ostre: (cyfry 0-50, liczby całkowite)

Test łączenia punktów A: (cyfry 0-50, liczby całkowite)
Test łączenia punktów B: (cyfry 0-50, liczby całkowite)

ToL - suma ruchów: (liczby całkowite od 0 do 500, bez przecinka)
ToL - liczba prawidłowych: (liczby całkowite od 0 do 50, bez przecinka)
ToL - czas inicjowania [sek]: (liczby całkowite od 0 do 500, bez przecinka)
ToL - czas wykonania [sek]: (liczby całkowite od 0 do 5000, bez przecinka)
ToL - czas całkowity [sek]: (liczby całkowite od 0 do 5000, bez przecinka)
ToL - czas przekroczony: (liczby całkowite od 0 do 10, bez przecinka)
ToL - liczba przekroczeń zasad: (liczby całkowite od 0 do 100, bez przecinka)
ToL - reakcje ukierunkowane (liczby całkowite od 0 do 100, bez przecinka)

WAIS-R wiadomości: (cyfry 0-30, liczby całkowite)
WAIS-R powtarzanie cyfr: (cyfry 0-30, liczby całkowite)
WAIS-R podobieństwa: (cyfry 0-30, liczby całkowite)

Bostoński Test Nazywania BMT - wynik: (liczby całkowite od 0 do 100, bez przecinka)
BMT - średni czas reakcji [sek]: (liczby całkowite od 0 do 500, bez przecinka)

Skala depresji Becka: (cyfry 0-50, liczby z jednym miejscem po przecinku)
Skala depresji Becka II: (cyfry 0-50, liczby z jednym miejscem po przecinku)


Natomiast: Test Stroopa i Test Minnesota są do usunięcia
*/


alter table Wizyta
	alter column TestFluencjiZwierzeta tinyint;

alter table Wizyta
	alter column TestFluencjiOstre tinyint;

alter table Wizyta
	alter column TestFluencjiK tinyint;


alter table Wizyta
	drop column TestStroopa;
	
	
alter table Wizyta
	drop column TestMinnesota;


alter Table Wizyta
	alter column SkalaDepresjiBecka decimal(4,1);
	
alter table Wizyta add
	SkalaDepresjiBeckaII decimal(4,1);


alter Table Wizyta add

-- Psycholog (część G)

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

	WAIS_R_Podobienstwa tinyint, -- dodane 2015-03-20
	BostonskiTestNazywaniaBMT tinyint, -- dodane 2015-03-20
	BMT_SredniCzasReakcji_sek int, -- dodane 2015-03-20


	TestFluencjiP tinyint, -- dodane 2015-03-20
	TestFluencjiOwoceWarzywa tinyint, -- dodane 2015-03-20


	ToL_SumaRuchow int, -- dodane 2015-03-20
	ToL_LiczbaPrawidlowych tinyint, -- dodane 2015-03-20
	ToL_CzasInicjowania_sek int, -- dodane 2015-03-20
	ToL_CzasWykonania_sek int, -- dodane 2015-03-20
	ToL_CzasCalkowity_sek int, -- dodane 2015-03-20
	ToL_CzasPrzekroczony tinyint, -- dodane 2015-03-20
	ToL_LiczbaPrzekroczenZasad tinyint, -- dodane 2015-03-20
	ToL_ReakcjeUkierunkowane tinyint -- dodane 2015-03-20

go

-- last rev. 2014-01-13
-- REPLACED 2015-03-20
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_examination_questionnaire_partG  (
	@IdWizyta int,
	@TestZegara bit,	-- juz bylo; niezmienione
	@MMSE tinyint,	-- juz bylo; niezmienione
	@CLOX1_Rysunek tinyint, -- dodane 2015-03-20
	@CLOX2_Kopia tinyint, -- dodane 2015-03-20
	@AVLT_proba_1 tinyint,-- dodane 2015-03-20
	@AVLT_proba_2 tinyint,-- dodane 2015-03-20
	@AVLT_proba_3 tinyint,-- dodane 2015-03-20
	@AVLT_proba_4 tinyint,-- dodane 2015-03-20
	@AVLT_proba_5 tinyint,-- dodane 2015-03-20
	@AVLT_Suma tinyint,-- dodane 2015-03-20
	@AVLT_Srednia decimal(4,2), -- dodane 2015-03-20
	@AVLT_KrotkieOdroczenie tinyint,-- dodane 2015-03-20
	@AVLT_Odroczony20min decimal(4,2), -- dodane 2015-03-20
	@AVLT_Rozpoznawanie tinyint,-- dodane 2015-03-20
	@AVLT_BledyRozpoznania tinyint,-- dodane 2015-03-20

	@TestAVLTSrednia varchar(40), -- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	@TestAVLTOdroczony varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	@TestAVLTPo20min varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	@TestAVLTRozpoznawanie varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane

	@CVLT_proba_1 tinyint,-- dodane 2015-03-20
	@CVLT_proba_2 tinyint,-- dodane 2015-03-20
	@CVLT_proba_3 tinyint,-- dodane 2015-03-20
	@CVLT_proba_4 tinyint,-- dodane 2015-03-20
	@CVLT_proba_5 tinyint,-- dodane 2015-03-20
	@CVLT_Suma tinyint,-- dodane 2015-03-20
	@CVLT_OSKO_krotkie_odroczenie decimal(4,2), -- dodane 2015-03-20
	@CVLT_OPKO_krotkie_odroczenie_i_pomoc tinyint,-- dodane 2015-03-20
	@CVLT_OSDO_po20min decimal(4,2), -- dodane 2015-03-20
	@CVLT_OPDO_po20min_i_pomoc tinyint,-- dodane 2015-03-20
	@CVLT_perseweracje tinyint,-- dodane 2015-03-20
	@CVLT_WtraceniaOdtwarzanieSwobodne tinyint,-- dodane 2015-03-20
	@CVLT_wtraceniaOdtwarzanieZPomoca tinyint,-- dodane 2015-03-20
	@CVLT_Rozpoznawanie tinyint,-- dodane 2015-03-20
	@CVLT_BledyRozpoznania tinyint,-- dodane 2015-03-20
	@Benton_JOL tinyint,-- dodane 2015-03-20
	@WAIS_R_Wiadomosci tinyint,-- juz bylo; niezmienione
	@WAIS_R_PowtarzanieCyfr tinyint,-- juz bylo; niezmienione
	@WAIS_R_Podobienstwa tinyint, -- dodane 2015-03-20
	@BostonskiTestNazywaniaBMT tinyint, -- dodane 2015-03-20
	@BMT_SredniCzasReakcji_sek int, -- dodane 2015-03-20
	@SkalaDepresjiBecka decimal(4,1),-- zmiana na decimal;
	@SkalaDepresjiBeckaII decimal(4,1),-- dodane 2015-03-20

	@TestFluencjiK tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	@TestFluencjiP tinyint, -- dodane 2015-03-20
	@TestFluencjiZwierzeta tinyint,-- bylo; ale zmiana z varchar(40) na tinyint
	@TestFluencjiOwoceWarzywa tinyint, -- dodane 2015-03-20
	@TestFluencjiOstre tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	@TestLaczeniaPunktowA varchar(40), -- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	@TestLaczeniaPunktowB varchar(40),-- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	@ToL_SumaRuchow int, -- dodane 2015-03-20
	@ToL_LiczbaPrawidlowych tinyint, -- dodane 2015-03-20
	@ToL_CzasInicjowania_sek int, -- dodane 2015-03-20
	@ToL_CzasWykonania_sek int, -- dodane 2015-03-20
	@ToL_CzasCalkowity_sek int, -- dodane 2015-03-20
	@ToL_CzasPrzekroczony tinyint, -- dodane 2015-03-20
	@ToL_LiczbaPrzekroczenZasad tinyint, -- dodane 2015-03-20
	@ToL_ReakcjeUkierunkowane tinyint, -- dodane 2015-03-20
	@InnePsychologiczne varchar(150),
	@OpisBadania varchar(2000),
	@Wnioski varchar(2000),
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;


	update Wizyta
		set 

			TestZegara = @TestZegara,
			MMSE = @MMSE,
			CLOX1_Rysunek = @CLOX1_Rysunek,
			CLOX2_Kopia = @CLOX2_Kopia,
			AVLT_proba_1 = @AVLT_proba_1,
			AVLT_proba_2 = @AVLT_proba_2,
			AVLT_proba_3 = @AVLT_proba_3,
			AVLT_proba_4 = @AVLT_proba_4,
			AVLT_proba_5 = @AVLT_proba_5,
			AVLT_Suma = @AVLT_Suma,
			AVLT_Srednia = @AVLT_Srednia,
			AVLT_KrotkieOdroczenie = @AVLT_KrotkieOdroczenie,
			AVLT_Odroczony20min = @AVLT_Odroczony20min,
			AVLT_Rozpoznawanie = @AVLT_Rozpoznawanie,
			AVLT_BledyRozpoznania = @AVLT_BledyRozpoznania,

			TestAVLTSrednia = @TestAVLTSrednia,
			TestAVLTOdroczony = @TestAVLTOdroczony,
			TestAVLTPo20min = @TestAVLTPo20min,
			TestAVLTRozpoznawanie = @TestAVLTRozpoznawanie,

			CVLT_proba_1 = @CVLT_proba_1,
			CVLT_proba_2 = @CVLT_proba_2,
			CVLT_proba_3 = @CVLT_proba_3,
			CVLT_proba_4 = @CVLT_proba_4,
			CVLT_proba_5 = @CVLT_proba_5,
			CVLT_Suma = @CVLT_Suma,
			CVLT_OSKO_krotkie_odroczenie = @CVLT_OSKO_krotkie_odroczenie,
			CVLT_OPKO_krotkie_odroczenie_i_pomoc = @CVLT_OPKO_krotkie_odroczenie_i_pomoc,
			CVLT_OSDO_po20min = @CVLT_OSDO_po20min,
			CVLT_OPDO_po20min_i_pomoc = @CVLT_OPDO_po20min_i_pomoc,
			CVLT_perseweracje = @CVLT_perseweracje,
			CVLT_WtraceniaOdtwarzanieSwobodne = @CVLT_WtraceniaOdtwarzanieSwobodne,
			CVLT_wtraceniaOdtwarzanieZPomoca = @CVLT_wtraceniaOdtwarzanieZPomoca,
			CVLT_Rozpoznawanie = @CVLT_Rozpoznawanie,
			CVLT_BledyRozpoznania = @CVLT_BledyRozpoznania,
			Benton_JOL = @Benton_JOL,
			WAIS_R_Wiadomosci = @WAIS_R_Wiadomosci,
			WAIS_R_PowtarzanieCyfr = @WAIS_R_PowtarzanieCyfr,
			WAIS_R_Podobienstwa = @WAIS_R_Podobienstwa,
			BostonskiTestNazywaniaBMT = @BostonskiTestNazywaniaBMT,
			BMT_SredniCzasReakcji_sek = @BMT_SredniCzasReakcji_sek,
			SkalaDepresjiBecka = @SkalaDepresjiBecka,
			SkalaDepresjiBeckaII = @SkalaDepresjiBeckaII,
			TestFluencjiK = @TestFluencjiK,
			TestFluencjiP = @TestFluencjiP,
			TestFluencjiZwierzeta = @TestFluencjiZwierzeta,
			TestFluencjiOwoceWarzywa = @TestFluencjiOwoceWarzywa,
			TestFluencjiOstre = @TestFluencjiOstre,
			TestLaczeniaPunktowA = @TestLaczeniaPunktowA,
			TestLaczeniaPunktowB = @TestLaczeniaPunktowB,
			ToL_SumaRuchow = @ToL_SumaRuchow,
			ToL_LiczbaPrawidlowych = @ToL_LiczbaPrawidlowych,
			ToL_CzasInicjowania_sek = @ToL_CzasInicjowania_sek,
			ToL_CzasWykonania_sek = @ToL_CzasWykonania_sek,
			ToL_CzasCalkowity_sek = @ToL_CzasCalkowity_sek,
			ToL_CzasPrzekroczony = @ToL_CzasPrzekroczony,
			ToL_LiczbaPrzekroczenZasad = @ToL_LiczbaPrzekroczenZasad,
			ToL_ReakcjeUkierunkowane = @ToL_ReakcjeUkierunkowane,
			InnePsychologiczne = @InnePsychologiczne,
			OpisBadania = @OpisBadania,
			Wnioski = @Wnioski,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go



alter procedure get_database_copy
as
SELECT 
/* PACJENT */
	   P.[NumerPacjenta]
      ,P.[RokUrodzenia]
      ,P.[MiesiacUrodzenia]
      ,P.[Plec]
      ,P.[Lokalizacja]
      ,P.[LiczbaElektrod]
	  ,P.[NazwaGrupy]
/*    ,P.[Wprowadzil] as WprowadzilDanePacjenta
      ,P.[Zmodyfikowal] as KorygowalDanePacjenta
      ,P.[OstatniaZmiana] as OstatniaKorektaDanychPacjenta */
/* WIZYTA */
      ,W.[RodzajWizyty]
      ,W.[DataPrzyjecia]
      ,W.[DataWypisu]
      ,W.[MasaCiala]
      ,W.[DataOperacji]
      ,W.[Wyksztalcenie]
      ,W.[Rodzinnosc]
      ,W.[RokZachorowania]
	  ,CAST(datediff(day, CAST( CAST((select max(RokZachorowania) from Wizyta x where x.IdPacjent = p.IdPacjent) as varchar)+'-'+ CAST(1 as varchar)+'-'+ CAST(1 as varchar) as datetime), W.DataPrzyjecia )/365.0 as decimal(4,2)) as CzasTrwaniaChoroby
      ,W.[PierwszyObjaw]
      ,W.[Drzenie]
      ,W.[Sztywnosc]
      ,W.[Spowolnienie]
      ,W.[ObjawyInne]
      ,REPLACE(W.[ObjawyInneJakie],';','. ') ObjawyInneJakie
      ,W.[CzasOdPoczObjDoWlLDopy]
      ,W.[DyskinezyObecnie]
      ,W.[DyskinezyOdLat]
      ,W.[FluktuacjeObecnie]
      ,W.[FluktuacjeOdLat]
      ,W.[CzasDyskinez]
      ,W.[CzasOFF]
      ,W.[PoprawaPoLDopie]
      ,W.[PrzebyteLeczenieOperacyjnePD]
      ,W.[Papierosy]
      ,W.[Kawa]
      ,W.[ZielonaHerbata]
      ,W.[Alkohol]
      ,W.[ZabiegowWZnieczOgPrzedRozpoznaniemPD]
      ,W.[Zamieszkanie]
	  ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'NarazenieNaToks' and IdWizyta = W.IdWizyta) as NarazeniaNaToks
      ,REPLACE(W.[Uwagi],';','. ') Uwagi
      ,W.[Nadcisnienie]
      ,W.[BlokeryKanWapn]
      ,W.[DominujacyObjawObecnie]
      ,REPLACE(W.[DominujacyObjawUwagi],';','. ') DominujacyObjawUwagi
	  ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'ObjawyAutonomiczne' and IdWizyta = W.IdWizyta) as ObjawyAutonomiczne
      ,W.[RLS]
      ,W.[ObjawyPsychotyczne]
      ,W.[Depresja]
      ,W.[Otepienie]
      ,W.[Dyzartria]
      ,W.[DysfagiaObjaw]
      ,W.[RBD]
      ,W.[ZaburzenieRuchomosciGalekOcznych]
      ,W.[Apraksja]
      ,W.[TestKlaskania]
      ,W.[ZaburzeniaWechowe]
      ,W.[Ldopa]
      ,W.[LDopaObecnie]
      ,W.[Agonista]
      ,W.[AgonistaObecnie]
      ,W.[Amantadyna]
      ,W.[AmantadynaObecnie]
      ,W.[MAOBinh]
      ,W.[MAOBinhObecnie]
      ,W.[COMTinh]
      ,W.[COMTinhObecnie]
      ,W.[Cholinolityk]
      ,W.[CholinolitykObecnie]
      ,W.[LekiInne]
      ,REPLACE(W.[LekiInneJakie],';','. ') LekiInneJakie
      ,REPLACE(W.[L_STIMOpis],';','. ') L_STIMOpis
      ,W.[L_STIMAmplitude]
      ,W.[L_STIMDuration]
      ,W.[L_STIMFrequency]
      ,REPLACE(W.[R_STIMOpis],';','. ') R_STIMOpis
      ,W.[R_STIMAmplitude]
      ,W.[R_STIMDuration]
      ,W.[R_STIMFrequency]
      ,W.[Wypis_Ldopa]
      ,W.[Wypis_LDopaObecnie]
      ,W.[Wypis_Agonista]
      ,W.[Wypis_AgonistaObecnie]
      ,W.[Wypis_Amantadyna]
      ,W.[Wypis_AmantadynaObecnie]
      ,W.[Wypis_MAOBinh]
      ,W.[Wypis_MAOBinhObecnie]
      ,W.[Wypis_COMTinh]
      ,W.[Wypis_COMTinhObecnie]
      ,W.[Wypis_Cholinolityk]
      ,W.[Wypis_CholinolitykObecnie]
      ,W.[Wypis_LekiInne]
      ,W.[Wypis_LekiInneJakie]
      ,REPLACE(W.[Wypis_L_STIMOpis],';','. ') Wypis_L_STIMOpis
      ,W.[Wypis_L_STIMAmplitude]
      ,W.[Wypis_L_STIMDuration]
      ,W.[Wypis_L_STIMFrequency]
      ,REPLACE(W.[Wypis_R_STIMOpis],';','. ') Wypis_R_STIMOpis
      ,W.[Wypis_R_STIMAmplitude]
      ,W.[Wypis_R_STIMDuration]
      ,W.[Wypis_R_STIMFrequency]
      ,W.[WydzielanieSliny]
      ,W.[Dysfagia]
      ,W.[DysfagiaCzestotliwosc]
      ,W.[Nudnosci]
      ,W.[Zaparcia]
      ,W.[TrudnosciWOddawaniuMoczu]
      ,W.[PotrzebaNaglegoOddaniaMoczu]
      ,W.[NiekompletneOproznieniePecherza]
      ,W.[SlabyStrumienMoczu]
      ,W.[CzestotliwowscOddawaniaMoczu]
      ,W.[Nykturia]
      ,W.[NiekontrolowaneOddawanieMoczu]
      ,W.[Omdlenia]
      ,W.[ZaburzeniaRytmuSerca]
      ,W.[ProbaPionizacyjna]
      ,W.[WzrostPodtliwosciTwarzKark]
      ,W.[WzrostPotliwosciRamionaDlonie]
	  ,W.[WzrostPotliwosciBrzuchPlecy]
      ,W.[WzrostPotliwosciKonczynyDolneStopy]
      ,W.[SpadekPodtliwosciTwarzKark]
      ,W.[SpadekPotliwosciRamionaDlonie]
	  ,W.[SpadekPotliwosciBrzuchPlecy]
      ,W.[SpadekPotliwosciKonczynyDolneStopy]
      ,W.[NietolerancjaWysokichTemp]
      ,W.[NietolerancjaNiskichTemp]
      ,W.[Lojotok]
      ,W.[SpadekLibido]
      ,W.[KlopotyOsiagnieciaErekcji]
      ,W.[KlopotyUtrzymaniaErekcji]
	/* BADANIE (wariantowe) */
      ,B.[DBS]
      ,B.[BMT]
      ,B.[UPDRS_I]
      ,B.[UPDRS_II]
      ,B.[UPDRS_18]
      ,B.[UPDRS_19]
      ,B.[UPDRS_20_FaceLipsChin]
      ,B.[UPDRS_20_RHand]
      ,B.[UPDRS_20_LHand]
      ,B.[UPDRS_20_RFoot]
      ,B.[UPDRS_20_LFoot]
      ,B.[UPDRS_21_RHand]
      ,B.[UPDRS_21_LHand]
      ,B.[UPDRS_22_Neck]
      ,B.[UPDRS_22_RHand]
      ,B.[UPDRS_22_LHand]
      ,B.[UPDRS_22_RFoot]
      ,B.[UPDRS_22_LFoot]
      ,B.[UPDRS_23_R]
      ,B.[UPDRS_23_L]
      ,B.[UPDRS_24_R]
      ,B.[UPDRS_24_L]
      ,B.[UPDRS_25_R]
      ,B.[UPDRS_25_L]
      ,B.[UPDRS_26_R]
      ,B.[UPDRS_26_L]
      ,B.[UPDRS_27]
      ,B.[UPDRS_28]
      ,B.[UPDRS_29]
      ,B.[UPDRS_30]
      ,B.[UPDRS_31]
      ,B.[UPDRS_III]
      ,B.[UPDRS_IV]
      ,B.[UPDRS_TOTAL]
      ,B.[HYscale]
      ,B.[SchwabEnglandScale]
      ,B.[JazzNovo]
      ,B.[Wideookulograf]
      ,B.[Latencymeter]
      ,B.[LatencymeterDurationLEFT]
      ,B.[LatencymeterLatencyLEFT]
      ,B.[LatencymeterAmplitudeLEFT]
      ,B.[LatencymeterPeakVelocityLEFT]
      ,B.[LatencymeterDurationRIGHT]
      ,B.[LatencymeterLatencyRIGHT]
      ,B.[LatencymeterAmplitudeRIGHT]
      ,B.[LatencymeterPeakVelocityRIGHT]
      ,B.[LatencymeterDurationALL]
      ,B.[LatencymeterLatencyALL]
      ,B.[LatencymeterAmplitudeALL]
      ,B.[LatencymeterPeakVelocityALL]
      ,B.[Tremorometria]
      ,B.[TremorometriaLEFT]
      ,B.[TremorometriaRIGHT]
      ,B.[TremorometriaLEFT_0_1]
      ,B.[TremorometriaLEFT_1_2]
      ,B.[TremorometriaLEFT_2_3]
      ,B.[TremorometriaLEFT_3_4]
      ,B.[TremorometriaLEFT_4_5]
      ,B.[TremorometriaLEFT_5_6]
      ,B.[TremorometriaLEFT_6_7]
      ,B.[TremorometriaLEFT_7_8]
      ,B.[TremorometriaLEFT_8_9]
      ,B.[TremorometriaLEFT_9_10]
      ,B.[TremorometriaLEFT_23_24]
      ,B.[TremorometriaRIGHT_0_1]
      ,B.[TremorometriaRIGHT_1_2]
      ,B.[TremorometriaRIGHT_2_3]
      ,B.[TremorometriaRIGHT_3_4]
      ,B.[TremorometriaRIGHT_4_5]
      ,B.[TremorometriaRIGHT_5_6]
      ,B.[TremorometriaRIGHT_6_7]
      ,B.[TremorometriaRIGHT_7_8]
      ,B.[TremorometriaRIGHT_8_9]
      ,B.[TremorometriaRIGHT_9_10]
      ,B.[TremorometriaRIGHT_23_24]
      ,B.[TestSchodkowy]
      ,B.[TestSchodkowyWDol]
      ,B.[TestSchodkowyWGore]
      ,B.[TestMarszu]
      ,B.[TestMarszuCzas1]
      ,B.[TestMarszuCzas2]
      ,B.[Posturografia]
      ,B.[MotionAnalysis]
      ,B.[Otwarte_Srednia_C_o_P_X]
      ,B.[Otwarte_Srednia_C_o_P_Y]
      ,B.[Otwarte_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Otwarte_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Otwarte_Perimeter_mm]
      ,B.[Otwarte_PoleElipsy_mm2]
      ,B.[Zamkniete_Srednia_C_o_P_X]
      ,B.[Zamkniete_Srednia_C_o_P_Y]
      ,B.[Zamkniete_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Zamkniete_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Zamkniete_Perimeter_mm]
      ,B.[Zamkniete_PoleElipsy_mm2]
      ,B.[WspolczynnikPerymetru_E_C_E_O_obie_stopy]
      ,B.[WspolczynnikPowierzchni_E_C_E_O_obie_stopy]
      ,B.[Biofeedback_Srednia_C_o_P_X]
      ,B.[Biofeedback_Srednia_C_o_P_Y]
      ,B.[Biofeedback_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Biofeedback_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Biofeedback_Perimeter_mm]
      ,B.[Biofeedback_PoleElipsy_mm2]				
      ,B.[UpAndGo]
      ,B.[UpAndGoLiczby]
      ,B.[UpAndGoKubekPrawa]
      ,B.[UpAndGoKubekLewa]
      ,B.[TST]
      ,B.[TandemPivot]
      ,B.[WTT]
      ,B.[Wprowadzil] as WariantZapisal
      ,B.[Zmodyfikowal] as WariantModyfikowal
      ,B.[OstatniaZmiana] as OstatniaEdycjaWariantu
/* WIZYTA - c.d. */
      ,W.[PDQ39]
      ,W.[AIMS]
      ,W.[Epworth]
      ,W.[CGI]
      ,W.[FSS]
	,REPLACE(W.[TestZegara],';','. ') TestZegara
	,REPLACE(W.[MMSE],';','. ') MMSE
	,REPLACE(W.[CLOX1_Rysunek],';','. ') CLOX1_Rysunek
	,REPLACE(W.[CLOX2_Kopia],';','. ') CLOX2_Kopia
	,REPLACE(W.[AVLT_proba_1],';','. ') AVLT_proba_1
	,REPLACE(W.[AVLT_proba_2],';','. ') AVLT_proba_2
	,REPLACE(W.[AVLT_proba_3],';','. ') AVLT_proba_3
	,REPLACE(W.[AVLT_proba_4],';','. ') AVLT_proba_4
	,REPLACE(W.[AVLT_proba_5],';','. ') AVLT_proba_5
	,REPLACE(W.[AVLT_Suma],';','. ') AVLT_Suma
	,REPLACE(W.[AVLT_Srednia],';','. ') AVLT_Srednia
	,REPLACE(W.[AVLT_KrotkieOdroczenie],';','. ') AVLT_KrotkieOdroczenie
	,REPLACE(W.[AVLT_Odroczony20min],';','. ') AVLT_Odroczony20min
	,REPLACE(W.[AVLT_Rozpoznawanie],';','. ') AVLT_Rozpoznawanie
	,REPLACE(W.[AVLT_BledyRozpoznania],';','. ') AVLT_BledyRozpoznania

	,REPLACE(W.[TestAVLTSrednia],';','. ') TestAVLTSrednia
	,REPLACE(W.[TestAVLTOdroczony],';','. ') TestAVLTOdroczony
	,REPLACE(W.[TestAVLTPo20min],';','. ') TestAVLTPo20min
	,REPLACE(W.[TestAVLTRozpoznawanie],';','. ') TestAVLTRozpoznawanie

	,REPLACE(W.[CVLT_proba_1],';','. ') CVLT_proba_1
	,REPLACE(W.[CVLT_proba_2],';','. ') CVLT_proba_2
	,REPLACE(W.[CVLT_proba_3],';','. ') CVLT_proba_3
	,REPLACE(W.[CVLT_proba_4],';','. ') CVLT_proba_4
	,REPLACE(W.[CVLT_proba_5],';','. ') CVLT_proba_5
	,REPLACE(W.[CVLT_Suma],';','. ') CVLT_Suma
	,REPLACE(W.[CVLT_OSKO_krotkie_odroczenie],';','. ') CVLT_OSKO_krotkie_odroczenie
	,REPLACE(W.[CVLT_OPKO_krotkie_odroczenie_i_pomoc],';','. ') CVLT_OPKO_krotkie_odroczenie_i_pomoc
	,REPLACE(W.[CVLT_OSDO_po20min],';','. ') CVLT_OSDO_po20min
	,REPLACE(W.[CVLT_OPDO_po20min_i_pomoc],';','. ') CVLT_OPDO_po20min_i_pomoc
	,REPLACE(W.[CVLT_perseweracje],';','. ') CVLT_perseweracje
	,REPLACE(W.[CVLT_WtraceniaOdtwarzanieSwobodne],';','. ') CVLT_WtraceniaOdtwarzanieSwobodne
	,REPLACE(W.[CVLT_wtraceniaOdtwarzanieZPomoca],';','. ') CVLT_wtraceniaOdtwarzanieZPomoca
	,REPLACE(W.[CVLT_Rozpoznawanie],';','. ') CVLT_Rozpoznawanie
	,REPLACE(W.[CVLT_BledyRozpoznania],';','. ') CVLT_BledyRozpoznania
	,REPLACE(W.[Benton_JOL],';','. ') Benton_JOL
	,REPLACE(W.[WAIS_R_Wiadomosci],';','. ') WAIS_R_Wiadomosci
	,REPLACE(W.[WAIS_R_PowtarzanieCyfr],';','. ') WAIS_R_PowtarzanieCyfr
	,REPLACE(W.[WAIS_R_Podobienstwa],';','. ') WAIS_R_Podobienstwa
	,REPLACE(W.[BostonskiTestNazywaniaBMT],';','. ') BostonskiTestNazywaniaBMT
	,REPLACE(W.[BMT_SredniCzasReakcji_sek],';','. ') BMT_SredniCzasReakcji_sek
	,W.[SkalaDepresjiBecka]
	,W.[SkalaDepresjiBeckaII]
	,REPLACE(W.[TestFluencjiK],';','. ') TestFluencjiK
	,REPLACE(W.[TestFluencjiP],';','. ') TestFluencjiP
	,REPLACE(W.[TestFluencjiZwierzeta],';','. ') TestFluencjiZwierzeta
	,REPLACE(W.[TestFluencjiOwoceWarzywa],';','. ') TestFluencjiOwoceWarzywa
	,REPLACE(W.[TestFluencjiOstre],';','. ') TestFluencjiOstre
	,REPLACE(W.[TestLaczeniaPunktowA],';','. ') TestLaczeniaPunktowA
	,REPLACE(W.[TestLaczeniaPunktowB],';','. ') TestLaczeniaPunktowB
	,REPLACE(W.[ToL_SumaRuchow],';','. ') ToL_SumaRuchow
	,REPLACE(W.[ToL_LiczbaPrawidlowych],';','. ') ToL_LiczbaPrawidlowych
	,REPLACE(W.[ToL_CzasInicjowania_sek],';','. ') ToL_CzasInicjowania_sek
	,REPLACE(W.[ToL_CzasWykonania_sek],';','. ') ToL_CzasWykonania_sek
	,REPLACE(W.[ToL_CzasCalkowity_sek],';','. ') ToL_CzasCalkowity_sek
	,REPLACE(W.[ToL_CzasPrzekroczony],';','. ') ToL_CzasPrzekroczony
	,REPLACE(W.[ToL_LiczbaPrzekroczenZasad],';','. ') ToL_LiczbaPrzekroczenZasad
	,REPLACE(W.[ToL_ReakcjeUkierunkowane],';','. ') ToL_ReakcjeUkierunkowane
	,REPLACE(W.[InnePsychologiczne],';','. ') InnePsychologiczne
	,REPLACE(W.[OpisBadania],';','. ') OpisBadania
	,REPLACE(W.[Wnioski],';','. ') Wnioski
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrzełyku]
      ,W.[SPECT]
	   ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'SPECTWynik' and IdWizyta = W.IdWizyta) as SPECTWyniki
      ,W.[MRI]
      ,REPLACE(W.[MRIwynik],';','. ') MRIwynik
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,REPLACE(W.[GenetykaWynik],';','. ') GenetykaWynik
      ,W.[Surowica]
      ,REPLACE(W.[SurowicaPozostało],';','. ')
      ,W.[Ferrytyna]
      ,W.[CRP]
      ,W.[NTproCNP]
      ,W.[URCA]
      ,W.[WitD]
      ,W.[CHOL]
      ,W.[TGI]
      ,W.[HDL]
      ,W.[LDL]
      ,W.[olLDL]
      ,REPLACE(W.[LaboratoryjneInne],';','. ') LaboratoryjneInne
      ,W.[Wprowadzil] as WizyteWprowadzil
      ,W.[Zmodyfikowal] as WizyteEdytowal
      ,W.[OstatniaZmiana] as OstatniaModyfikacja

  FROM Pacjent P left join Wizyta w on P.IdPacjent = W.IdPacjent left join Badanie B on B.IdWizyta = W.IdWizyta
  order by P.NumerPacjenta, W.RodzajWizyty, B.BMT, B.DBS
  go