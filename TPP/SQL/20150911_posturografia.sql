use TPP;
go

/*
dodanie kilku atrybutów w czêœci "UPDRS i skale kliniczne", w podczêœci "Posturografia" na pocz¹tku przed atrybutem "Otwarte_œrednia_CoPX":
- Zebris_1,5_Step_width (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Step_length_left (liczby do dwóch miejsc po przecinku, zakres 0-300)
- Zebris_1,5_Step_length_right (liczby do dwóch miejsc po przecinku, zakres 0-300)
- Zebris_1,5_Step_time_left (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Step_time_right (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Stance_phase_left (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Stance_phase_right (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Swing_phase_left (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Swing_phase_right (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Total_Double_Support (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_1,5_Cadence (liczby do dwóch miejsc po przecinku, zakres 0-300)
- Zebris_3,0_Step_width (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Step_length_left (liczby do dwóch miejsc po przecinku, zakres 0-300)
- Zebris_3,0_Step_length_right (liczby do dwóch miejsc po przecinku, zakres 0-300)
- Zebris_3,0_Step_time_left (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Step_time_right (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Stance_phase_left (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Stance_phase_right (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Swing_phase_left (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Swing_phase_right (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Total_Double_Support (liczby do dwóch miejsc po przecinku, zakres 0-100)
- Zebris_3,0_Cadence (liczby do dwóch miejsc po przecinku, zakres 0-300)
*/

alter table Badanie add
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
	Zebris_3_0_Cadence decimal(5,2) NULL
go


create procedure update_variant_examination_data_partB_0  (	@IdBadanie int,
	@Zebris_1_5_Step_width decimal(5,2),
	@Zebris_1_5_Step_length_left decimal(5,2),
	@Zebris_1_5_Step_length_right decimal(5,2),
	@Zebris_1_5_Step_time_left decimal(5,2),
	@Zebris_1_5_Step_time_right decimal(5,2),
	@Zebris_1_5_Stance_phase_left decimal(5,2),
	@Zebris_1_5_Stance_phase_right decimal(5,2),
	@Zebris_1_5_Swing_phase_left decimal(5,2),
	@Zebris_1_5_Swing_phase_right decimal(5,2),
	@Zebris_1_5_Total_Double_Support decimal(5,2),
	@Zebris_1_5_Cadence decimal(5,2),
	@Zebris_3_0_Step_width decimal(5,2),
	@Zebris_3_0_Step_length_left decimal(5,2),
	@Zebris_3_0_Step_length_right decimal(5,2),
	@Zebris_3_0_Step_time_left decimal(5,2),
	@Zebris_3_0_Step_time_right decimal(5,2),
	@Zebris_3_0_Stance_phase_left decimal(5,2),
	@Zebris_3_0_Stance_phase_right decimal(5,2),
	@Zebris_3_0_Swing_phase_left decimal(5,2),
	@Zebris_3_0_Swing_phase_right decimal(5,2),
	@Zebris_3_0_Total_Double_Support decimal(5,2),
	@Zebris_3_0_Cadence decimal(5,2),
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
			Zebris_1_5_Step_width = @Zebris_1_5_Step_width,
			Zebris_1_5_Step_length_left = @Zebris_1_5_Step_length_left,
			Zebris_1_5_Step_length_right = @Zebris_1_5_Step_length_right,
			Zebris_1_5_Step_time_left = @Zebris_1_5_Step_time_left,
			Zebris_1_5_Step_time_right = @Zebris_1_5_Step_time_right,
			Zebris_1_5_Stance_phase_left = @Zebris_1_5_Stance_phase_left,
			Zebris_1_5_Stance_phase_right = @Zebris_1_5_Stance_phase_right,
			Zebris_1_5_Swing_phase_left = @Zebris_1_5_Swing_phase_left,
			Zebris_1_5_Swing_phase_right = @Zebris_1_5_Swing_phase_right,
			Zebris_1_5_Total_Double_Support = @Zebris_1_5_Total_Double_Support,
			Zebris_1_5_Cadence = @Zebris_1_5_Cadence,
			Zebris_3_0_Step_width = @Zebris_3_0_Step_width,
			Zebris_3_0_Step_length_left = @Zebris_3_0_Step_length_left,
			Zebris_3_0_Step_length_right = @Zebris_3_0_Step_length_right,
			Zebris_3_0_Step_time_left = @Zebris_3_0_Step_time_left,
			Zebris_3_0_Step_time_right = @Zebris_3_0_Step_time_right,
			Zebris_3_0_Stance_phase_left = @Zebris_3_0_Stance_phase_left,
			Zebris_3_0_Stance_phase_right = @Zebris_3_0_Stance_phase_right,
			Zebris_3_0_Swing_phase_left = @Zebris_3_0_Swing_phase_left,
			Zebris_3_0_Swing_phase_right = @Zebris_3_0_Swing_phase_right,
			Zebris_3_0_Total_Double_Support = @Zebris_3_0_Total_Double_Support,
			Zebris_3_0_Cadence = @Zebris_3_0_Cadence,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdBadanie = @IdBadanie;

	return;
end;
go
	

-- modified: 2015-09-11
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
      ,dbo.disorder_duration_for_examination(W.IdWizyta) as CzasTrwaniaChoroby
      ,W.[PierwszyObjaw]
      ,W.[Drzenie]
      ,W.[Sztywnosc]
      ,W.[Spowolnienie]
      ,W.[ObjawyInne]
      ,W.[ObjawyInneJakie] ObjawyInneJakie
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
      ,dbo.multivalued_examination_attribute('NarazenieNaToks', P.IdPacjent, W.RodzajWizyty)  as NarazeniaNaToks
      ,W.[Uwagi] Uwagi
      ,W.[Nadcisnienie]
      ,W.[BlokeryKanWapn]
      ,W.[DominujacyObjawObecnie]
      ,W.[DominujacyObjawUwagi] DominujacyObjawUwagi
	  ,dbo.multivalued_examination_attribute('ObjawyAutonomiczne', P.IdPacjent, W.RodzajWizyty) as ObjawyAutonomiczne
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
      ,W.[LekiInneJakie] LekiInneJakie
      ,W.[L_STIMOpis] L_STIMOpis
      ,W.[L_STIMAmplitude]
      ,W.[L_STIMDuration]
      ,W.[L_STIMFrequency]
      ,W.[R_STIMOpis] R_STIMOpis
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
      ,W.[Wypis_L_STIMOpis] Wypis_L_STIMOpis
      ,W.[Wypis_L_STIMAmplitude]
      ,W.[Wypis_L_STIMDuration]
      ,W.[Wypis_L_STIMFrequency]
      ,W.[Wypis_R_STIMOpis] Wypis_R_STIMOpis
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
	,B.[Saccades]
	,B.[SaccadesLatencyMeanLEFT]
	,B.[SaccadesLatencyMeanRIGHT]
	,B.[SaccadesDurationLEFT]
	,B.[SaccadesDurationRIGHT]
	,B.[SaccadesAmplitudeLEFT]
	,B.[SaccadesAmplitudeRIGHT]
	,B.[SaccadesPeakVelocityLEFT]
	,B.[SaccadesPeakVelocityRIGHT]
	,B.[SaccadesLatencyMeanALL]
	,B.[SaccadesDurationALL]
	,B.[SaccadesAmplitudeALL]
	,B.[SaccadesPeakVelocityALL]
	,B.[Antisaccades]
	,B.[AntisaccadesPercentOfCorrectLEFT]
	,B.[AntisaccadesPercentOfCorrectRIGHT]
	,B.[AntisaccadesLatencyMeanLEFT]
	,B.[AntisaccadesLatencyMeanRIGHT]
	,B.[AntisaccadesDurationLEFT]
	,B.[AntisaccadesDurationRIGHT]
	,B.[AntisaccadesAmplitudeLEFT]
	,B.[AntisaccadesAmplitudeRIGHT]
	,B.[AntisaccadesPeakVelocityLEFT]
	,B.[AntisaccadesPeakVelocityRIGHT]
	,B.[AntisaccadesPercentOfCorrectALL]
	,B.[AntisaccadesLatencyMeanALL]
	,B.[AntisaccadesDurationALL]
	,B.[AntisaccadesAmplitudeALL]
	,B.[AntisaccadesPeakVelocityALL]
	,B.[POM_Gain_SlowSinus]
	,B.[POM_StDev_SlowSinus]
	,B.[POM_Gain_MediumSinus]
	,B.[POM_StDev_MediumSinus]
	,B.[POM_Gain_FastSinus]
	,B.[POM_StDev_FastSinus]
	,B.[POM_Accuracy_SlowSinus]
	,B.[POM_Accuracy_MediumSinus]
	,B.[POM_Accuracy_FastSinus]
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
 		,B.[Zebris_1_5_Step_width] 
 		,B.[Zebris_1_5_Step_length_left] 
 		,B.[Zebris_1_5_Step_length_right] 
 		,B.[Zebris_1_5_Step_time_left] 
 		,B.[Zebris_1_5_Step_time_right] 
 		,B.[Zebris_1_5_Stance_phase_left] 
 		,B.[Zebris_1_5_Stance_phase_right] 
 		,B.[Zebris_1_5_Swing_phase_left] 
 		,B.[Zebris_1_5_Swing_phase_right] 
 		,B.[Zebris_1_5_Total_Double_Support] 
 		,B.[Zebris_1_5_Cadence] 
 		,B.[Zebris_3_0_Step_width] 
 		,B.[Zebris_3_0_Step_length_left] 
 		,B.[Zebris_3_0_Step_length_right] 
 		,B.[Zebris_3_0_Step_time_left] 
 		,B.[Zebris_3_0_Step_time_right] 
 		,B.[Zebris_3_0_Stance_phase_left] 
 		,B.[Zebris_3_0_Stance_phase_right] 
 		,B.[Zebris_3_0_Swing_phase_left] 
 		,B.[Zebris_3_0_Swing_phase_right] 
 		,B.[Zebris_3_0_Total_Double_Support] 
 		,B.[Zebris_3_0_Cadence]
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
	,W.[TestZegara] TestZegara
	,W.[MMSE] MMSE
	,W.[CLOX1_Rysunek] CLOX1_Rysunek
	,W.[CLOX2_Kopia] CLOX2_Kopia
	,W.[AVLT_proba_1] AVLT_proba_1
	,W.[AVLT_proba_2] AVLT_proba_2
	,W.[AVLT_proba_3] AVLT_proba_3
	,W.[AVLT_proba_4] AVLT_proba_4
	,W.[AVLT_proba_5] AVLT_proba_5
	,W.[AVLT_Suma] AVLT_Suma
	,W.[AVLT_Srednia] AVLT_Srednia
	,W.[AVLT_KrotkieOdroczenie] AVLT_KrotkieOdroczenie
	,W.[AVLT_Odroczony20min] AVLT_Odroczony20min
	,W.[AVLT_Rozpoznawanie] AVLT_Rozpoznawanie
	,W.[AVLT_BledyRozpoznania] AVLT_BledyRozpoznania

	,W.[TestAVLTSrednia] TestAVLTSrednia
	,W.[TestAVLTOdroczony] TestAVLTOdroczony
	,W.[TestAVLTPo20min] TestAVLTPo20min
	,W.[TestAVLTRozpoznawanie] TestAVLTRozpoznawanie

	,W.[CVLT_proba_1] CVLT_proba_1
	,W.[CVLT_proba_2] CVLT_proba_2
	,W.[CVLT_proba_3] CVLT_proba_3
	,W.[CVLT_proba_4] CVLT_proba_4
	,W.[CVLT_proba_5] CVLT_proba_5
	,W.[CVLT_Suma] CVLT_Suma
	,W.[CVLT_OSKO_krotkie_odroczenie] CVLT_OSKO_krotkie_odroczenie
	,W.[CVLT_OPKO_krotkie_odroczenie_i_pomoc] CVLT_OPKO_krotkie_odroczenie_i_pomoc
	,W.[CVLT_OSDO_po20min] CVLT_OSDO_po20min
	,W.[CVLT_OPDO_po20min_i_pomoc] CVLT_OPDO_po20min_i_pomoc
	,W.[CVLT_perseweracje] CVLT_perseweracje
	,W.[CVLT_WtraceniaOdtwarzanieSwobodne] CVLT_WtraceniaOdtwarzanieSwobodne
	,W.[CVLT_wtraceniaOdtwarzanieZPomoca] CVLT_wtraceniaOdtwarzanieZPomoca
	,W.[CVLT_Rozpoznawanie] CVLT_Rozpoznawanie
	,W.[CVLT_BledyRozpoznania] CVLT_BledyRozpoznania
	,W.[Benton_JOL] Benton_JOL
	,W.[WAIS_R_Wiadomosci] WAIS_R_Wiadomosci
	,W.[WAIS_R_PowtarzanieCyfr] WAIS_R_PowtarzanieCyfr
	,W.[WAIS_R_Podobienstwa] WAIS_R_Podobienstwa
	,W.[BostonskiTestNazywaniaBMT] BostonskiTestNazywaniaBMT
	,W.[BMT_SredniCzasReakcji_sek] BMT_SredniCzasReakcji_sek
	,W.[SkalaDepresjiBecka]
	,W.[SkalaDepresjiBeckaII]
	,W.[TestFluencjiK] TestFluencjiK
	,W.[TestFluencjiP] TestFluencjiP
	,W.[TestFluencjiZwierzeta] TestFluencjiZwierzeta
	,W.[TestFluencjiOwoceWarzywa] TestFluencjiOwoceWarzywa
	,W.[TestFluencjiOstre] TestFluencjiOstre
	,W.[TestLaczeniaPunktowA] TestLaczeniaPunktowA
	,W.[TestLaczeniaPunktowB] TestLaczeniaPunktowB
	,W.[ToL_SumaRuchow] ToL_SumaRuchow
	,W.[ToL_LiczbaPrawidlowych] ToL_LiczbaPrawidlowych
	,W.[ToL_CzasInicjowania_sek] ToL_CzasInicjowania_sek
	,W.[ToL_CzasWykonania_sek] ToL_CzasWykonania_sek
	,W.[ToL_CzasCalkowity_sek] ToL_CzasCalkowity_sek
	,W.[ToL_CzasPrzekroczony] ToL_CzasPrzekroczony
	,W.[ToL_LiczbaPrzekroczenZasad] ToL_LiczbaPrzekroczenZasad
	,W.[ToL_ReakcjeUkierunkowane] ToL_ReakcjeUkierunkowane
	,W.[InnePsychologiczne] InnePsychologiczne
	,W.[OpisBadania] OpisBadania
	,W.[Wnioski] Wnioski
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrze³yku]
      ,W.[SPECT]
	  ,dbo.multivalued_examination_attribute('SPECTWynik', P.IdPacjent, W.RodzajWizyty) as SPECTWyniki
      ,W.[MRI]
      ,W.[MRIwynik] MRIwynik
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,W.[GenetykaWynik] GenetykaWynik
      ,W.[Surowica]
      ,W.[SurowicaPozosta³o] SurowicaPozosta³o
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
      ,W.[LaboratoryjneInne] LaboratoryjneInne
      ,W.[Wprowadzil] as WizyteWprowadzil
      ,W.[Zmodyfikowal] as WizyteEdytowal
      ,W.[OstatniaZmiana] as OstatniaModyfikacja

  FROM Pacjent P left join Wizyta w on P.IdPacjent = W.IdPacjent left join Badanie B on B.IdWizyta = W.IdWizyta
  order by P.NumerPacjenta, W.RodzajWizyty, B.BMT, B.DBS
  go



-- updated: 2015-09-11
alter function generate_file_name( @file_id int )
returns varchar(80)
as
begin
declare @file_name varchar(80);
declare @bmt bit;
declare @dbs bit;
declare @step varchar(3);
declare @patient_no varchar(14);
declare @file_type varchar(100);

select 
	@file_name = p.NazwaPliku, 
	@bmt = b.BMT, 
	@dbs = b.DBS, 
	@step = w.RodzajWizyty, 
	@patient_no = pa.NumerPacjenta, 
	@file_type = p.OpisPliku
	from Plik p join Badanie b on b.IdBadanie = p.IdBadanie join Wizyta w on w.IdWizyta = b.IdWizyta join Pacjent pa on pa.IdPacjent = w.IdPacjent
	where p.IdPlik = @file_id;
	
	set @file_name = rtrim(right(@file_name, charindex('.', reverse(@file_name))));
	
	if @file_type = 'Coordinates' set @file_name = '_C'+@file_name;
	if @file_type like '%Video%' 
		begin 
			if @file_type like '%desktop%' set @file_name = '_D'+@file_name;
			if @file_type like '%laptop%' set @file_name = '_L'+@file_name;
			if @file_type like '%tablet%' set @file_name = '_T'+@file_name;
			if @file_type like '%jazznovo%' set @file_name = '_J'+@file_name;
			set @file_name = '_V'+@file_name;
		end;
	if @file_type = 'EyeTrackingExcel' set @file_name = '_ETe'+@file_name;
	if @file_type = 'EyeTrackingGraph' set @file_name = '_ETg'+@file_name;
	
	if @dbs = 1 set @file_name = '-DBS'+@file_name; else set @file_name = '-O'+@file_name;
	if @bmt = 1 set @file_name = '-BMT'+@file_name; else set @file_name = '-O'+@file_name;
	
	set @file_name = '-' + @step + @file_name;
	
	set @patient_no = replace( @patient_no, '/', '_');
	set @file_name = @patient_no+@file_name;
	return @file_name;
end
go 


 		
update Kolumna set PozycjaDomyslna= PozycjaDomyslna+22 where PozycjaDomyslna > 237;
go		
 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	238	,	'B', 'Zebris_1_5_Step_width'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	239	,	'B', 'Zebris_1_5_Step_length_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	240	,	'B', 'Zebris_1_5_Step_length_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	241	,	'B', 'Zebris_1_5_Step_time_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	242	,	'B', 'Zebris_1_5_Step_time_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	243	,	'B', 'Zebris_1_5_Stance_phase_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	244	,	'B', 'Zebris_1_5_Stance_phase_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	245	,	'B', 'Zebris_1_5_Swing_phase_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	246	,	'B', 'Zebris_1_5_Swing_phase_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	247	,	'B', 'Zebris_1_5_Total_Double_Support'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	248	,	'B', 'Zebris_1_5_Cadence'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	249	,	'B', 'Zebris_3_0_Step_width'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	250	,	'B', 'Zebris_3_0_Step_length_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	251	,	'B', 'Zebris_3_0_Step_length_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	252	,	'B', 'Zebris_3_0_Step_time_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	253	,	'B', 'Zebris_3_0_Step_time_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	254	,	'B', 'Zebris_3_0_Stance_phase_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	255	,	'B', 'Zebris_3_0_Stance_phase_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	256	,	'B', 'Zebris_3_0_Swing_phase_left'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	257	,	'B', 'Zebris_3_0_Swing_phase_right'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	258	,	'B', 'Zebris_3_0_Total_Double_Support'); 
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (	259	,	'B', 'Zebris_3_0_Cadence');
go