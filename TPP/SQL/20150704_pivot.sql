use TPP_test;
go



-- created 2015-07-19
create function multivalued_examination_attribute(@attrib_name varchar(50), @patient_id int, @exam_kind int)
returns varchar(75)
as
begin
return (select Wartosci from AtrybutyWielowartoscioweWizyty aww join Wizyta w on w.IdWizyta = aww.IdWizyta 
		where aww.NazwaAtrybutu = @attrib_name and w.IdPacjent = @patient_id and w.RodzajWizyty = @exam_kind )
end
go




-- modified: 2015-07-19
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
      ,W.[pH_metriaPrzełyku]
      ,W.[SPECT]
	   ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'SPECTWynik' and IdWizyta = W.IdWizyta) as SPECTWyniki
      ,W.[MRI]
      ,W.[MRIwynik] MRIwynik
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,W.[GenetykaWynik] GenetykaWynik
      ,W.[Surowica]
      ,W.[SurowicaPozostało] SurowicaPozostało
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

 


create type KolumnyUdt as table
(
	Pozycja int,
	KolumnaID int
)
go

alter procedure get_transformed_copy(@column_filter as KolumnyUdt readonly, @timeline_filter int)
as
begin
-- TODO: timeline filter will limit the visit kinds; for now all the visits 0..84 are included;
	declare @sql as nvarchar(max);
	declare @current_column_name as varchar(50);
	declare @current_column_id as int;

	select CF.*, K.Encja, K.Nazwa into #Temp from @column_filter CF join Kolumna k on k.IdKolumna = CF.KolumnaID
	
	
-- Patient basic data are always included
	set @sql = 'select 
	   P.[NumerPacjenta]
      ,P.[RokUrodzenia]
      ,P.[MiesiacUrodzenia]
      ,P.[Plec]
      ,P.[Lokalizacja]
      ,P.[LiczbaElektrod]
	  ,P.[NazwaGrupy]';
	-- set @sql = ', W.[DataPrzyjecia]


	while( (select COUNT(*) from #Temp ) > 0 )
	begin
		declare @visit_date int;
		select top 1 @current_column_id = KolumnaID from #Temp order by Pozycja;
		set @sql = @sql + '#';
		set @visit_date = 0;
		while @visit_date <= 78
		begin
			-- if (((@timeline_filter & POWER(2,((@visit_date / 6) + 1))) <> 0)
			
			begin
				set @visit_date = @visit_date + 6;
			end;
			set @sql = @sql + CAST(@visit_date as Varchar);
		end;
		
		delete #Temp where KolumnaID = @current_column_id;
		
	end;
	
	set @sql = @sql + ' from Pacjent P join Wizyta W on W.IdPacjent = P.IdPacjent group by 
	   P.[NumerPacjenta]
      ,P.[RokUrodzenia]
      ,P.[MiesiacUrodzenia]
      ,P.[Plec]
      ,P.[Lokalizacja]
      ,P.[LiczbaElektrod]
	  ,P.[NazwaGrupy]
	   order by P.[NumerPacjenta]';
		select @sql;
		-- exec sp_executesql @statement = @sql;
		
end
go

declare @filtr as KolumnyUdt;
insert into @filtr (Pozycja, KolumnaID) values ( 1, 1 );
insert into @filtr (Pozycja, KolumnaID)values ( 2, 2 );

exec get_transformed_copy @filtr, 20;

select * from Kolumna


select   
P.[NumerPacjenta]
  ,P.[RokUrodzenia]        
  ,P.[MiesiacUrodzenia]        
  ,P.[Plec]        
  ,P.[Lokalizacja]        
  ,P.[LiczbaElektrod]     
  ,P.[NazwaGrupy] 
  , MAX(CASE WHEN W.RodzajWizyty = 0 THEN CAST(W.DataPrzyjecia as Varchar) ELSE '' END) as DataPrzyjecia00
  , MAX(CASE WHEN W.RodzajWizyty = 12 THEN CAST(W.DataPrzyjecia as Varchar) ELSE '' END) as DataPrzyjecia12
  , COUNT(W.IdPacjent) as LiczbaWizyt
 from Pacjent P join Wizyta W on W.IdPacjent = P.IdPacjent 
 group by       P.[NumerPacjenta]        ,P.[RokUrodzenia]        ,P.[MiesiacUrodzenia]        ,P.[Plec]        ,P.[Lokalizacja]        ,P.[LiczbaElektrod]     ,P.[NazwaGrupy]      order by P.[NumerPacjenta]
 
 select * from Wizyta