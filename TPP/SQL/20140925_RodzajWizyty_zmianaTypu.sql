use TPP;
go 

select * from Wizyta

update SlownikInt set Definicja = '0 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 0;
update SlownikInt set Definicja = '6 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 6;
update SlownikInt set Definicja = '12 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 12;
update SlownikInt set Definicja = '18 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 18;
update SlownikInt set Definicja = '24 miesi¹ce' where Atrybut = 'RodzajWizyty' and Klucz = 24;
update SlownikInt set Definicja = '30 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 30;
update SlownikInt set Definicja = '36 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 36;
update SlownikInt set Definicja = '42 miesi¹ce' where Atrybut = 'RodzajWizyty' and Klucz = 42;
update SlownikInt set Definicja = '48 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 48;
update SlownikInt set Definicja = '54 miesi¹ce' where Atrybut = 'RodzajWizyty' and Klucz = 54;
update SlownikInt set Definicja = '60 miesiêcy' where Atrybut = 'RodzajWizyty' and Klucz = 60;

/*
alter table Wizyta
alter column RodzajWizyty decimal(3,1) not null;
go

update Wizyta set RodzajWizyty = RodzajWizyty * 12;
go

alter table Wizyta
alter column RodzajWizyty tinyint not null;
go

delete from SlownikDecimal where Atrybut = 'RodzajWizyty';
go

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0,	'0 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	6,	'6 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	12,	'12 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	18,	'18 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	24,	'24 miesiace' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	30,	'30 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	36,	'36 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	42,	'42 miesi¹ce' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	48,	'48 miesiêcy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	54,	'54 miesi¹ce' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	60,	'60 miesiêcy' );
go

-- last rev. 2014-09-25
-- @result codes: 0 = OK, 1 = visit already exists while run in no-update mode,  exist 2 = validation failed - see message, 3 = patient of this number not found, 4 = user login unknown
alter procedure update_examination_questionnaire_partA  (@NumerPacjenta varchar(20), @RodzajWizyty tinyint,
	@DataPrzyjecia date,	@DataOperacji date,	@DataWypisu date, @MasaCiala decimal(4,1),
	@Wyksztalcenie	tinyint, @Rodzinnosc tinyint, @RokZachorowania	smallint, 
	@PierwszyObjaw	tinyint, 
	@Drzenie	tinyint,
	@Sztywnosc	tinyint,
	@Spowolnienie	tinyint,
	@ObjawyInne	tinyint,
	@ObjawyInneJakie	varchar(80),
	@CzasOdPoczObjDoWlLDopy	tinyint, @DyskinezyObecnie	tinyint, @DyskinezyOdLat	decimal(3,1), @FluktuacjeObecnie tinyint, @FluktuacjeOdLat	decimal(3,1), 
	@CzasDyskinez decimal(3,1), @CzasOFF decimal(3,1), @PoprawaPoLDopie tinyint,
	@allow_update_existing bit,
	@actor_login varchar(50),
	@result int OUTPUT, @visit_id int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @patient_id int;
	declare @user_id int;
	declare @update int;
	set @patient_id = 0;
	set @result = 0;
	set @message = '';
	set @update = 0;
	select @patient_id = IdPacjent from Pacjent where NumerPacjenta = @NumerPacjenta;
	
	if(@patient_id = 0)
	begin
		set @result = 3;
		set @message = 'patient of this number not found';
		return;
	end;
	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if (select count(*) from Wizyta where IdPacjent = @patient_id and RodzajWizyty = @RodzajWizyty ) > 0 
	begin
		if (@allow_update_existing = 0)
			begin
				set @result = 1;
				return;
			end;
		set @update = 1;
		select @visit_id = IdWizyta from Wizyta where IdPacjent = @patient_id and RodzajWizyty = @RodzajWizyty;
	end;

	-- validations

	if dbo.validate_input_int('Wizyta', 'RodzajWizyty', @RodzajWizyty) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute RodzajWizyty';
		return;
	end;
	if dbo.validate_input_int('Wizyta', 'Wyksztalcenie', @Wyksztalcenie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Wyksztalcenie';
		return;
	end;
	if( dbo.validate_ext_bit( @Rodzinnosc) = 0 )
	begin
		set @result = 2;
		set @message = 'Rodzinnosc - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	if dbo.validate_input_int('Wizyta', 'PierwszyObjaw', @PierwszyObjaw) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute PierwszyObjaw';
		return;
	end;
	
	if( dbo.validate_ext_bit( @Drzenie) = 0 )
	begin
		set @result = 2;
		set @message = 'Drzenie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Sztywnosc) = 0 )
	begin
		set @result = 2;
		set @message = 'Sztywnosc - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Spowolnienie) = 0 )
	begin
		set @result = 2;
		set @message = 'Spowolnienie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ObjawyInne) = 0 )
	begin
		set @result = 2;
		set @message = 'ObjawyInne - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	if( dbo.validate_ext_bit( @DyskinezyObecnie) = 0 )
	begin
		set @result = 2;
		set @message = 'DyskinezyObecnie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;
	if( dbo.validate_ext_bit( @FluktuacjeObecnie) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'FluktuacjeObecnie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @PoprawaPoLDopie) = 0 )
	begin
		set @result = 2;
		set @message = 'PoprawaPoLDopie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	-- /validations
	-- depending on update
	if(@update = 0)
		begin
		insert into Wizyta (	RodzajWizyty, IdPacjent, DataPrzyjecia, DataOperacji, DataWypisu, MasaCiala, Wyksztalcenie,	Rodzinnosc,	RokZachorowania, PierwszyObjaw, 
								Drzenie, Sztywnosc,	Spowolnienie, ObjawyInne, ObjawyInneJakie,
								CzasOdPoczObjDoWlLDopy, DyskinezyObecnie,
								DyskinezyOdLat, FluktuacjeObecnie, FluktuacjeOdLat,	CzasDyskinez, CzasOFF, PoprawaPoLDopie,
								Wprowadzil, Zmodyfikowal, OstatniaZmiana )
					values (	@RodzajWizyty, @patient_id, @DataPrzyjecia, @DataOperacji, @DataWypisu, @MasaCiala, @Wyksztalcenie,	@Rodzinnosc, @RokZachorowania, @PierwszyObjaw, 
								@Drzenie, @Sztywnosc,@Spowolnienie, @ObjawyInne, @ObjawyInneJakie,
								@CzasOdPoczObjDoWlLDopy, @DyskinezyObecnie,
								@DyskinezyOdLat, @FluktuacjeObecnie, @FluktuacjeOdLat, @CzasDyskinez, @CzasOFF, @PoprawaPoLDopie,
								@user_id, @user_id, getdate() )  set @visit_id = SCOPE_IDENTITY();
		end;
	else
		begin
		update Wizyta
		set DataPrzyjecia = @DataPrzyjecia, DataOperacji = @DataOperacji, DataWypisu = @DataWypisu, MasaCiala = @MasaCiala, Wyksztalcenie = @Wyksztalcenie,	Rodzinnosc = @Rodzinnosc,	RokZachorowania = @RokZachorowania, 
				PierwszyObjaw = @PierwszyObjaw, 
				Drzenie	= @Drzenie,
				Sztywnosc	= @Sztywnosc,
				Spowolnienie	= @Spowolnienie,
				ObjawyInne	= @ObjawyInne,
				ObjawyInneJakie	= @ObjawyInneJakie,
				CzasOdPoczObjDoWlLDopy = @CzasOdPoczObjDoWlLDopy, DyskinezyObecnie = @DyskinezyObecnie,
								DyskinezyOdLat = @DyskinezyOdLat, FluktuacjeObecnie = @FluktuacjeObecnie, FluktuacjeOdLat = @FluktuacjeOdLat,
								CzasDyskinez = @CzasDyskinez, CzasOFF = @CzasOFF,
								PoprawaPoLDopie	= @PoprawaPoLDopie,
								Zmodyfikowal = @user_id, OstatniaZmiana = getdate() 
		where RodzajWizyty = @RodzajWizyty and IdPacjent = @patient_id;
		end;
	return;
end;
go


alter table Badanie add TremorometriaLEFT bit;
alter table Badanie add TremorometriaRIGHT bit;
go



-- last rev. 2014-01-03
-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_variant_examination_data_partB  (	@IdBadanie int,
	@Tremorometria bit, 
	@TremorometriaLEFT bit, 
	@TremorometriaRIGHT bit, 
	@TremorometriaLEFT_0_1 decimal(7,2),
	@TremorometriaLEFT_1_2 decimal(7,2),
	@TremorometriaLEFT_2_3 decimal(7,2),
	@TremorometriaLEFT_3_4 decimal(7,2),
	@TremorometriaLEFT_4_5 decimal(7,2),
	@TremorometriaLEFT_5_6 decimal(7,2),
	@TremorometriaLEFT_6_7 decimal(7,2),
	@TremorometriaLEFT_7_8 decimal(7,2),
	@TremorometriaLEFT_8_9 decimal(7,2),
	@TremorometriaLEFT_9_10 decimal(7,2),
	@TremorometriaLEFT_23_24 decimal(7,2),
	@TremorometriaRIGHT_0_1 decimal(7,2),
	@TremorometriaRIGHT_1_2 decimal(7,2),
	@TremorometriaRIGHT_2_3 decimal(7,2),
	@TremorometriaRIGHT_3_4 decimal(7,2),
	@TremorometriaRIGHT_4_5 decimal(7,2),
	@TremorometriaRIGHT_5_6 decimal(7,2),
	@TremorometriaRIGHT_6_7 decimal(7,2),
	@TremorometriaRIGHT_7_8 decimal(7,2),
	@TremorometriaRIGHT_8_9 decimal(7,2),
	@TremorometriaRIGHT_9_10 decimal(7,2),
	@TremorometriaRIGHT_23_24 decimal(7,2),	
	@TestSchodkowy bit,
	@TestSchodkowyWDol decimal(4,2),
	@TestSchodkowyWGore decimal(4,2),
	@TestMarszu bit,
	@TestMarszuCzas1 decimal(4,2),
	@TestMarszuCzas2 decimal(4,2),
	@Posturografia bit,
	@MotionAnalysis bit,
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
			Tremorometria = @Tremorometria, 
			TremorometriaLEFT = @TremorometriaLEFT,
			TremorometriaRIGHT = @TremorometriaRIGHT,
			TremorometriaLEFT_0_1 = @TremorometriaLEFT_0_1,
			TremorometriaLEFT_1_2 = @TremorometriaLEFT_1_2,
			TremorometriaLEFT_2_3 = @TremorometriaLEFT_2_3,
			TremorometriaLEFT_3_4 = @TremorometriaLEFT_3_4,
			TremorometriaLEFT_4_5 = @TremorometriaLEFT_4_5,
			TremorometriaLEFT_5_6 = @TremorometriaLEFT_5_6,
			TremorometriaLEFT_6_7 = @TremorometriaLEFT_6_7,
			TremorometriaLEFT_7_8 = @TremorometriaLEFT_7_8,
			TremorometriaLEFT_8_9 = @TremorometriaLEFT_8_9,
			TremorometriaLEFT_9_10 = @TremorometriaLEFT_9_10,
			TremorometriaLEFT_23_24 = @TremorometriaLEFT_23_24,
			TremorometriaRIGHT_0_1 = @TremorometriaRIGHT_0_1,
			TremorometriaRIGHT_1_2 = @TremorometriaRIGHT_1_2,
			TremorometriaRIGHT_2_3 = @TremorometriaRIGHT_2_3,
			TremorometriaRIGHT_3_4 = @TremorometriaRIGHT_3_4,
			TremorometriaRIGHT_4_5 = @TremorometriaRIGHT_4_5,
			TremorometriaRIGHT_5_6 = @TremorometriaRIGHT_5_6,
			TremorometriaRIGHT_6_7 = @TremorometriaRIGHT_6_7,
			TremorometriaRIGHT_7_8 = @TremorometriaRIGHT_7_8,
			TremorometriaRIGHT_8_9 = @TremorometriaRIGHT_8_9,
			TremorometriaRIGHT_9_10 = @TremorometriaRIGHT_9_10,
			TremorometriaRIGHT_23_24 = @TremorometriaRIGHT_23_24,
			TestSchodkowy	= @TestSchodkowy,
			TestSchodkowyWDol	= @TestSchodkowyWDol,
			TestSchodkowyWGore	= @TestSchodkowyWGore,
			TestMarszu	= @TestMarszu,
			TestMarszuCzas1	= @TestMarszuCzas1,
			TestMarszuCzas2	= @TestMarszuCzas2,
			Posturografia	= @Posturografia,
			MotionAnalysis	= @MotionAnalysis,
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
	  ,W.[TestAVLTSrednia]
	  ,W.[TestAVLTOdroczony]
	  ,W.[TestAVLTPo20min]
	  ,W.[TestAVLTRozpoznawanie]
      ,W.[TestStroopa]
      ,W.[TestMinnesota]
      ,W.[InnePsychologiczne]
      ,W.[OpisBadania]
      ,W.[Wnioski]
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrze³yku]
      ,W.[SPECT]
	   ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'SPECTWynik' and IdWizyta = W.IdWizyta) as SPECTWyniki
      ,W.[MRI]
      ,W.[MRIwynik]
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,W.[GenetykaWynik]
      ,W.[Surowica]
      ,W.[SurowicaPozosta³o]
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
  go
  */