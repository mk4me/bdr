﻿use TPP;
go

/*
	Jak niżej - dodano do tabeli Badanie następujące kolumny
	oraz procedurę update_variant_examination_data_partB_1 do ich wprowadzania i aktualizacji:

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
*/


alter Table Badanie add

	Otwarte_Srednia_C_o_P_X int,				
	Otwarte_Srednia_C_o_P_Y int,				
	Otwarte_Srednia_P_T_Predkosc_mm_sec int,	
	Otwarte_Srednia_P_B_Predkosc_mm_sec int,	
	Otwarte_Perimeter_mm int,					
	Otwarte_PoleElipsy_mm2 int,					

	Zamkniete_Srednia_C_o_P_X int,				
	Zamkniete_Srednia_C_o_P_Y int,				
	Zamkniete_Srednia_P_T_Predkosc_mm_sec int,	
	Zamkniete_Srednia_P_B_Predkosc_mm_sec int,	
	Zamkniete_Perimeter_mm int,					
	Zamkniete_PoleElipsy_mm2 int,				

	WspolczynnikPerymetru_E_C_E_O_obie_stopy int,		
	WspolczynnikPowierzchni_E_C_E_O_obie_stopy int,	

	Biofeedback_Srednia_C_o_P_X int,				
	Biofeedback_Srednia_C_o_P_Y int,				
	Biofeedback_Srednia_P_T_Predkosc_mm_sec int,	
	Biofeedback_Srednia_P_B_Predkosc_mm_sec int,	
	Biofeedback_Perimeter_mm int,					
	Biofeedback_PoleElipsy_mm2 int
go


-- last rev. 2015-03-01
-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_variant_examination_data_partB_1  (	@IdBadanie int,

	@Otwarte_Srednia_C_o_P_X int,				
	@Otwarte_Srednia_C_o_P_Y int,				
	@Otwarte_Srednia_P_T_Predkosc_mm_sec int,	
	@Otwarte_Srednia_P_B_Predkosc_mm_sec int,	
	@Otwarte_Perimeter_mm int,					
	@Otwarte_PoleElipsy_mm2 int,					

	@Zamkniete_Srednia_C_o_P_X int,				
	@Zamkniete_Srednia_C_o_P_Y int,				
	@Zamkniete_Srednia_P_T_Predkosc_mm_sec int,	
	@Zamkniete_Srednia_P_B_Predkosc_mm_sec int,	
	@Zamkniete_Perimeter_mm int,					
	@Zamkniete_PoleElipsy_mm2 int,				

	@WspolczynnikPerymetru_E_C_E_O_obie_stopy int,		
	@WspolczynnikPowierzchni_E_C_E_O_obie_stopy int,	

	@Biofeedback_Srednia_C_o_P_X int,				
	@Biofeedback_Srednia_C_o_P_Y int,				
	@Biofeedback_Srednia_P_T_Predkosc_mm_sec int,	
	@Biofeedback_Srednia_P_B_Predkosc_mm_sec int,	
	@Biofeedback_Perimeter_mm int,					
	@Biofeedback_PoleElipsy_mm2 int,					


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


	if(not exists(select * from Badanie where IdBadanie = @IdBadanie ) )
	begin
		set @result = 3;
		set @message = 'variant of this ID ='+ CAST(@IdBadanie as varchar)+' not found';
		return;
	end;

	update Badanie
		set 
			Otwarte_Srednia_C_o_P_X = @Otwarte_Srednia_C_o_P_X,				
			Otwarte_Srednia_C_o_P_Y = @Otwarte_Srednia_C_o_P_Y,				
			Otwarte_Srednia_P_T_Predkosc_mm_sec = @Otwarte_Srednia_P_T_Predkosc_mm_sec,	
			Otwarte_Srednia_P_B_Predkosc_mm_sec = @Otwarte_Srednia_P_B_Predkosc_mm_sec,	
			Otwarte_Perimeter_mm = @Otwarte_Perimeter_mm,					
			Otwarte_PoleElipsy_mm2 = @Otwarte_PoleElipsy_mm2,					

			Zamkniete_Srednia_C_o_P_X = @Zamkniete_Srednia_C_o_P_X,				
			Zamkniete_Srednia_C_o_P_Y = @Zamkniete_Srednia_C_o_P_Y,				
			Zamkniete_Srednia_P_T_Predkosc_mm_sec = @Zamkniete_Srednia_P_T_Predkosc_mm_sec,	
			Zamkniete_Srednia_P_B_Predkosc_mm_sec = @Zamkniete_Srednia_P_B_Predkosc_mm_sec,	
			Zamkniete_Perimeter_mm = @Zamkniete_Perimeter_mm,					
			Zamkniete_PoleElipsy_mm2 = @Zamkniete_PoleElipsy_mm2,				

			WspolczynnikPerymetru_E_C_E_O_obie_stopy = @WspolczynnikPerymetru_E_C_E_O_obie_stopy,		
			WspolczynnikPowierzchni_E_C_E_O_obie_stopy = @WspolczynnikPowierzchni_E_C_E_O_obie_stopy,	

			Biofeedback_Srednia_C_o_P_X = @Biofeedback_Srednia_C_o_P_X,				
			Biofeedback_Srednia_C_o_P_Y = @Biofeedback_Srednia_C_o_P_Y,				
			Biofeedback_Srednia_P_T_Predkosc_mm_sec = @Biofeedback_Srednia_P_T_Predkosc_mm_sec,	
			Biofeedback_Srednia_P_B_Predkosc_mm_sec = @Biofeedback_Srednia_P_B_Predkosc_mm_sec,	
			Biofeedback_Perimeter_mm = @Biofeedback_Perimeter_mm,					
			Biofeedback_PoleElipsy_mm2 = @Biofeedback_PoleElipsy_mm2,	
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdBadanie = @IdBadanie;

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
      ,REPLACE(W.[ObjawyInneJakie],';','. ')
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
      ,REPLACE(W.[Uwagi],';','. ')
      ,W.[Nadcisnienie]
      ,W.[BlokeryKanWapn]
      ,W.[DominujacyObjawObecnie]
      ,REPLACE(W.[DominujacyObjawUwagi],';','. ')
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
      ,REPLACE(W.[LekiInneJakie],';','. ')
      ,REPLACE(W.[L_STIMOpis],';','. ')
      ,W.[L_STIMAmplitude]
      ,W.[L_STIMDuration]
      ,W.[L_STIMFrequency]
      ,REPLACE(W.[R_STIMOpis],';','. ')
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
      ,REPLACE(W.[Wypis_L_STIMOpis],';','. ')
      ,W.[Wypis_L_STIMAmplitude]
      ,W.[Wypis_L_STIMDuration]
      ,W.[Wypis_L_STIMFrequency]
      ,REPLACE(W.[Wypis_R_STIMOpis],';','. ')
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
      ,W.[TestZegara]
      ,W.[MMSE]
      ,W.[WAIS_R_Wiadomosci]
      ,W.[WAIS_R_PowtarzanieCyfr]
      ,W.[SkalaDepresjiBecka]
      ,REPLACE(W.[TestFluencjiZwierzeta],';','. ')
      ,REPLACE(W.[TestFluencjiOstre],';','. ')
      ,REPLACE(W.[TestFluencjiK],';','. ')
      ,REPLACE(W.[TestLaczeniaPunktowA],';','. ')
      ,REPLACE(W.[TestLaczeniaPunktowB],';','. ')
	  ,REPLACE(W.[TestAVLTSrednia],';','. ')
	  ,REPLACE(W.[TestAVLTOdroczony],';','. ')
	  ,REPLACE(W.[TestAVLTPo20min],';','. ')
	  ,REPLACE(W.[TestAVLTRozpoznawanie],';','. ')
      ,W.[TestStroopa]
      ,REPLACE(W.[TestMinnesota],';','. ')
      ,REPLACE(W.[InnePsychologiczne],';','. ')
      ,REPLACE(W.[OpisBadania],';','. ')
      ,REPLACE(W.[Wnioski],';','. ')
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrzełyku]
      ,W.[SPECT]
	   ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'SPECTWynik' and IdWizyta = W.IdWizyta) as SPECTWyniki
      ,W.[MRI]
      ,REPLACE(W.[MRIwynik],';','. ')
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,REPLACE(W.[GenetykaWynik],';','. ')
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
      ,REPLACE(W.[LaboratoryjneInne],';','. ')
      ,W.[Wprowadzil] as WizyteWprowadzil
      ,W.[Zmodyfikowal] as WizyteEdytowal
      ,W.[OstatniaZmiana] as OstatniaModyfikacja

  FROM Pacjent P left join Wizyta w on P.IdPacjent = W.IdPacjent left join Badanie B on B.IdWizyta = W.IdWizyta
  order by P.NumerPacjenta, W.RodzajWizyty, B.BMT, B.DBS
  go