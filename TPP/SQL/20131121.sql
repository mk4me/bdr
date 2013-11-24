use TPP;
go


alter table Wizyta
add 
	WzrostPotliwosciBrzuchPlecy tinyint,
	SpadekPotliwosciBrzuchPlecy tinyint;
go


create procedure update_examination_questionnaire_partE_x01  (
	@IdWizyta int,
	@WydzielanieSliny	tinyint,
	@Dysfagia	tinyint,
	@DysfagiaCzestotliwosc	tinyint,
	@Nudnosci	tinyint,
	@Zaparcia	tinyint,
	@TrudnosciWOddawaniuMoczu	tinyint,
	@PotrzebaNaglegoOddaniaMoczu	tinyint,
	@NiekompletneOproznieniePecherza	tinyint,
	@SlabyStrumienMoczu	tinyint,
	@CzestotliwowscOddawaniaMoczu	tinyint,
	@Nykturia	tinyint,
	@NiekontrolowaneOddawanieMoczu	tinyint,
	@Omdlenia	tinyint,
	@ZaburzeniaRytmuSerca	tinyint,
	@ProbaPionizacyjna	tinyint,
	@WzrostPodtliwosciTwarzKark	tinyint,
	@WzrostPotliwosciRamionaDlonie	tinyint,
	@WzrostPotliwosciBrzuchPlecy tinyint,
	@WzrostPotliwosciKonczynyDolneStopy	tinyint,
	@SpadekPodtliwosciTwarzKark	tinyint,
	@SpadekPotliwosciRamionaDlonie	tinyint,
	@SpadekPotliwosciBrzuchPlecy tinyint,
	@SpadekPotliwosciKonczynyDolneStopy	tinyint,
	@NietolerancjaWysokichTemp	tinyint,
	@NietolerancjaNiskichTemp	tinyint,
	@Lojotok	tinyint,
	@SpadekLibido	tinyint,
	@KlopotyOsiagnieciaErekcji	tinyint,
	@KlopotyUtrzymaniaErekcji	tinyint,
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

	-- attribute validators

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WydzielanieSliny) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute WydzielanieSliny - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Dysfagia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Dysfagia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @DysfagiaCzestotliwosc) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @DysfagiaCzestotliwosc - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Nudnosci) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Nudnosci - see enumeration for allowed values';
		return;
	end;


	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Zaparcia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Zaparcia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @TrudnosciWOddawaniuMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute TrudnosciWOddawaniuMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @PotrzebaNaglegoOddaniaMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @PotrzebaNaglegoOddaniaMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NiekompletneOproznieniePecherza) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NiekompletneOproznieniePecherza - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SlabyStrumienMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SlabyStrumienMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @CzestotliwowscOddawaniaMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @CzestotliwowscOddawaniaMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Nykturia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Nykturia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NiekontrolowaneOddawanieMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NiekontrolowaneOddawanieMoczu - see enumeration for allowed values';
		return;
	end;


	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Omdlenia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Omdlenia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @ZaburzeniaRytmuSerca) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @ZaburzeniaRytmuSerca - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @ProbaPionizacyjna) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @ProbaPionizacyjna - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPodtliwosciTwarzKark) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPodtliwosciTwarzKark - see enumeration for allowed values';
		return;
	end;

--
	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPotliwosciRamionaDlonie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPotliwosciRamionaDlonie - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPotliwosciBrzuchPlecy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPotliwosciBrzuchPlecy - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPotliwosciKonczynyDolneStopy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPotliwosciKonczynyDolneStopy - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPodtliwosciTwarzKark) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekPodtliwosciTwarzKark - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPotliwosciRamionaDlonie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekPotliwosciRamionaDlonie - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPotliwosciBrzuchPlecy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekPotliwosciBrzuchPlecy - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPotliwosciKonczynyDolneStopy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute SpadekPotliwosciKonczynyDolneStopy - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NietolerancjaWysokichTemp) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NietolerancjaWysokichTemp - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NietolerancjaNiskichTemp) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NietolerancjaNiskichTemp - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Lojotok) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Lojotok - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekLibido) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekLibido - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @KlopotyOsiagnieciaErekcji) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @KlopotyOsiagnieciaErekcji - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @KlopotyUtrzymaniaErekcji) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @KlopotyUtrzymaniaErekcji - see enumeration for allowed values';
		return;
	end;

	update Wizyta
		set 
			WydzielanieSliny = @WydzielanieSliny,
			Dysfagia = @Dysfagia,
			DysfagiaCzestotliwosc = @DysfagiaCzestotliwosc,
			Nudnosci = @Nudnosci,
			Zaparcia = @Zaparcia,
			TrudnosciWOddawaniuMoczu = @TrudnosciWOddawaniuMoczu,
			PotrzebaNaglegoOddaniaMoczu = @PotrzebaNaglegoOddaniaMoczu,
			NiekompletneOproznieniePecherza = @NiekompletneOproznieniePecherza,
			SlabyStrumienMoczu = @SlabyStrumienMoczu,
			CzestotliwowscOddawaniaMoczu = @CzestotliwowscOddawaniaMoczu,
			Nykturia = @Nykturia,
			NiekontrolowaneOddawanieMoczu = @NiekontrolowaneOddawanieMoczu,
			Omdlenia = @Omdlenia,
			ZaburzeniaRytmuSerca = @ZaburzeniaRytmuSerca,
			ProbaPionizacyjna = @ProbaPionizacyjna,
			WzrostPodtliwosciTwarzKark = @WzrostPodtliwosciTwarzKark,
			WzrostPotliwosciRamionaDlonie = @WzrostPotliwosciRamionaDlonie,
			WzrostPotliwosciBrzuchPlecy = @WzrostPotliwosciBrzuchPlecy,
			WzrostPotliwosciKonczynyDolneStopy = @WzrostPotliwosciKonczynyDolneStopy,
			SpadekPodtliwosciTwarzKark = @SpadekPodtliwosciTwarzKark,
			SpadekPotliwosciRamionaDlonie = @SpadekPotliwosciRamionaDlonie,
			SpadekPotliwosciBrzuchPlecy = @SpadekPotliwosciBrzuchPlecy,
			SpadekPotliwosciKonczynyDolneStopy = @SpadekPotliwosciKonczynyDolneStopy,
			NietolerancjaWysokichTemp = @NietolerancjaWysokichTemp,
			NietolerancjaNiskichTemp = @NietolerancjaNiskichTemp,
			Lojotok = @Lojotok,
			SpadekLibido = @SpadekLibido,
			KlopotyOsiagnieciaErekcji = @KlopotyOsiagnieciaErekcji,
			KlopotyUtrzymaniaErekcji = @KlopotyUtrzymaniaErekcji,
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
      ,W.[MiesiacZachorowania]
      ,W.[RokBadania]
      ,W.[MiesiacBadania]
      ,W.[PierwszyObjaw]
      ,W.[Drzenie]
      ,W.[Sztywnosc]
      ,W.[Spowolnienie]
      ,W.[ObjawyInne]
      ,W.[ObjawyInneJakie]
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
      ,W.[Uwagi]
      ,W.[Nadcisnienie]
      ,W.[BlokeryKanWapn]
      ,W.[DominujacyObjawObecnie]
      ,W.[DominujacyObjawUwagi]
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
      ,W.[LekiInneJakie]
      ,W.[L_STIMOpis]
      ,W.[L_STIMAmplitude]
      ,W.[L_STIMDuration]
      ,W.[L_STIMFrequency]
      ,W.[R_STIMOpis]
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
      ,W.[Wypis_L_STIMOpis]
      ,W.[Wypis_L_STIMAmplitude]
      ,W.[Wypis_L_STIMDuration]
      ,W.[Wypis_L_STIMFrequency]
      ,W.[Wypis_R_STIMOpis]
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
      ,B.[TestSchodkowyCzas1]
      ,B.[TestSchodkowyCzas2]
      ,B.[TestMarszu]
      ,B.[TestMarszuCzas1]
      ,B.[TestMarszuCzas2]
      ,B.[Posturografia]
      ,B.[MotionAnalysis]
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
      ,W.[TestFluencjiZwierzeta]
      ,W.[TestFluencjiOstre]
      ,W.[TestFluencjiK]
      ,W.[TestLaczeniaPunktowA]
      ,W.[TestLaczeniaPunktowB]
      ,W.[TestUczeniaSlownoSluchowego]
      ,W.[TestStroopa]
      ,W.[TestMinnesota]
      ,W.[InnePsychologiczne]
      ,W.[OpisBadania]
      ,W.[Wnioski]
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrzełyku]
      ,W.[SPECT]
	   ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'SPECTWynik' and IdWizyta = W.IdWizyta) as SPECTWyniki
      ,W.[MRI]
      ,W.[MRIwynik]
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,W.[GenetykaWynik]
      ,W.[Surowica]
      ,W.[SurowicaPozostało]
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
      ,W.[LaboratoryjneInne]
      ,W.[Wprowadzil] as WizyteWprowadzil
      ,W.[Zmodyfikowal] as WizyteEdytowal
      ,W.[OstatniaZmiana] as OstatniaModyfikacja

  FROM Pacjent P left join Wizyta w on P.IdPacjent = W.IdPacjent left join Badanie B on B.IdWizyta = W.IdWizyta
  order by P.NumerPacjenta, W.RodzajWizyty, B.BMT, B.DBS
