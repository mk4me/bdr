use TPP;
go

update Wizyta set ObjawyInneJakie = REPLACE(ObjawyInneJakie,';','. ');
update Wizyta set Uwagi = REPLACE(Uwagi,';','. ');
update Wizyta set DominujacyObjawUwagi = REPLACE(DominujacyObjawUwagi,';','. ');
update Wizyta set LekiInneJakie = REPLACE(LekiInneJakie,';','. ');
update Wizyta set Wypis_LekiInneJakie = REPLACE(Wypis_LekiInneJakie,';','. ');

update Wizyta set L_STIMOpis = REPLACE(L_STIMOpis,';','. ');
update Wizyta set R_STIMOpis = REPLACE(R_STIMOpis,';','. ');

update Wizyta set Wypis_L_STIMOpis = REPLACE(Wypis_L_STIMOpis,';','. ');
update Wizyta set Wypis_R_STIMOpis = REPLACE(Wypis_R_STIMOpis,';','. ');


update Wizyta set TestZegara = REPLACE(TestZegara,';','. ');
update Wizyta set MMSE = REPLACE(MMSE,';','. ');
update Wizyta set CLOX1_Rysunek = REPLACE(CLOX1_Rysunek,';','. ');
update Wizyta set CLOX2_Kopia = REPLACE(CLOX2_Kopia,';','. ');
update Wizyta set AVLT_proba_1 = REPLACE(AVLT_proba_1,';','. ');
update Wizyta set AVLT_proba_2 = REPLACE(AVLT_proba_2,';','. ');
update Wizyta set AVLT_proba_3 = REPLACE(AVLT_proba_3,';','. ');
update Wizyta set AVLT_proba_4 = REPLACE(AVLT_proba_4,';','. ');
update Wizyta set AVLT_proba_5 = REPLACE(AVLT_proba_5,';','. ');
update Wizyta set AVLT_Suma = REPLACE(AVLT_Suma,';','. ');
update Wizyta set AVLT_Srednia = REPLACE(AVLT_Srednia,';','. ');
update Wizyta set AVLT_KrotkieOdroczenie = REPLACE(AVLT_KrotkieOdroczenie,';','. ');
update Wizyta set AVLT_Odroczony20min = REPLACE(AVLT_Odroczony20min,';','. ');
update Wizyta set AVLT_Rozpoznawanie = REPLACE(AVLT_Rozpoznawanie,';','. ');
update Wizyta set AVLT_BledyRozpoznania = REPLACE(AVLT_BledyRozpoznania,';','. ');

update Wizyta set TestAVLTSrednia = REPLACE(TestAVLTSrednia,';','. ');
update Wizyta set TestAVLTOdroczony = REPLACE(TestAVLTOdroczony,';','. ');
update Wizyta set TestAVLTPo20min = REPLACE(TestAVLTPo20min,';','. ');
update Wizyta set TestAVLTRozpoznawanie = REPLACE(TestAVLTRozpoznawanie,';','. ');

update Wizyta set CVLT_proba_1 = REPLACE(CVLT_proba_1,';','. ');
update Wizyta set CVLT_proba_2 = REPLACE(CVLT_proba_2,';','. ');
update Wizyta set CVLT_proba_3 = REPLACE(CVLT_proba_3,';','. ');
update Wizyta set CVLT_proba_4 = REPLACE(CVLT_proba_4,';','. ');
update Wizyta set CVLT_proba_5 = REPLACE(CVLT_proba_5,';','. ');
update Wizyta set CVLT_Suma = REPLACE(CVLT_Suma,';','. ');
update Wizyta set CVLT_OSKO_krotkie_odroczenie = REPLACE(CVLT_OSKO_krotkie_odroczenie,';','. ');
update Wizyta set CVLT_OPKO_krotkie_odroczenie_i_pomoc = REPLACE(CVLT_OPKO_krotkie_odroczenie_i_pomoc,';','. ');
update Wizyta set CVLT_OSDO_po20min = REPLACE(CVLT_OSDO_po20min,';','. ');
update Wizyta set CVLT_OPDO_po20min_i_pomoc = REPLACE(CVLT_OPDO_po20min_i_pomoc,';','. ');
update Wizyta set CVLT_perseweracje = REPLACE(CVLT_perseweracje,';','. ');
update Wizyta set CVLT_WtraceniaOdtwarzanieSwobodne = REPLACE(CVLT_WtraceniaOdtwarzanieSwobodne,';','. ');
update Wizyta set CVLT_wtraceniaOdtwarzanieZPomoca = REPLACE(CVLT_wtraceniaOdtwarzanieZPomoca,';','. ');
update Wizyta set CVLT_Rozpoznawanie = REPLACE(CVLT_Rozpoznawanie,';','. ');
update Wizyta set CVLT_BledyRozpoznania = REPLACE(CVLT_BledyRozpoznania,';','. ');
update Wizyta set Benton_JOL = REPLACE(Benton_JOL,';','. ');
update Wizyta set WAIS_R_Wiadomosci = REPLACE(WAIS_R_Wiadomosci,';','. ');
update Wizyta set WAIS_R_PowtarzanieCyfr = REPLACE(WAIS_R_PowtarzanieCyfr,';','. ');
update Wizyta set WAIS_R_Podobienstwa = REPLACE(WAIS_R_Podobienstwa,';','. ');
update Wizyta set BostonskiTestNazywaniaBMT = REPLACE(BostonskiTestNazywaniaBMT,';','. ');
update Wizyta set BMT_SredniCzasReakcji_sek = REPLACE(BMT_SredniCzasReakcji_sek,';','. ');


update Wizyta set TestFluencjiK = REPLACE(TestFluencjiK,';','. ');
update Wizyta set TestFluencjiP = REPLACE(TestFluencjiP,';','. ');
update Wizyta set TestFluencjiZwierzeta = REPLACE(TestFluencjiZwierzeta,';','. ');
update Wizyta set TestFluencjiOwoceWarzywa = REPLACE(TestFluencjiOwoceWarzywa,';','. ');
update Wizyta set TestFluencjiOstre = REPLACE(TestFluencjiOstre,';','. ');
update Wizyta set TestLaczeniaPunktowA = REPLACE(TestLaczeniaPunktowA,';','. ');
update Wizyta set TestLaczeniaPunktowB = REPLACE(TestLaczeniaPunktowB,';','. ');
update Wizyta set ToL_SumaRuchow = REPLACE(ToL_SumaRuchow,';','. ');
update Wizyta set ToL_LiczbaPrawidlowych = REPLACE(ToL_LiczbaPrawidlowych,';','. ');
update Wizyta set ToL_CzasInicjowania_sek = REPLACE(ToL_CzasInicjowania_sek,';','. ');
update Wizyta set ToL_CzasWykonania_sek = REPLACE(ToL_CzasWykonania_sek,';','. ');
update Wizyta set ToL_CzasCalkowity_sek = REPLACE(ToL_CzasCalkowity_sek,';','. ');
update Wizyta set ToL_CzasPrzekroczony = REPLACE(ToL_CzasPrzekroczony,';','. ');
update Wizyta set ToL_LiczbaPrzekroczenZasad = REPLACE(ToL_LiczbaPrzekroczenZasad,';','. ');
update Wizyta set ToL_ReakcjeUkierunkowane = REPLACE(ToL_ReakcjeUkierunkowane,';','. ');
update Wizyta set InnePsychologiczne = REPLACE(InnePsychologiczne,';','. ');
update Wizyta set OpisBadania = REPLACE(REPLACE(REPLACE(OpisBadania, CHAR(13), ''), CHAR(10), ''),';','. ');
update Wizyta set Wnioski = REPLACE(REPLACE(REPLACE(Wnioski, CHAR(13), ''), CHAR(10), ''),';','. ');

update Wizyta set MRIwynik = REPLACE(MRIwynik,';','. ');
update Wizyta set GenetykaWynik = REPLACE(GenetykaWynik,';','. ');
update Wizyta set SurowicaPozosta這 = REPLACE(SurowicaPozosta這,';','. ');

update Wizyta set LaboratoryjneInne = REPLACE(LaboratoryjneInne,';','. ');
go



-- last rev. 2015-07-20
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
								@Drzenie, @Sztywnosc,@Spowolnienie, @ObjawyInne, REPLACE(@ObjawyInneJakie,';','. '),
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
				ObjawyInneJakie	= REPLACE(@ObjawyInneJakie,';','. '),
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






-- drop procedure update_examination_questionnaire_partB
-- last rev. 2015-07-19
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_examination_questionnaire_partB  (@IdWizyta int,
	@PrzebyteLeczenieOperacyjnePD tinyint,
	@Papierosy tinyint,
	@Kawa	tinyint, 
	@ZielonaHerbata tinyint,	
	@Alkohol	tinyint, 
	@ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,	
	@Zamieszkanie tinyint, 
	@Uwagi varchar(50),
	@Nadcisnienie tinyint,
	@BlokeryKanWapn tinyint,
	@DominujacyObjawObecnie tinyint,
	@DominujacyObjawUwagi varchar(50),
	@RLS	tinyint,
	@ObjawyPsychotyczne	tinyint,
	@Depresja	tinyint,
	@Otepienie	decimal(2,1),
	@Dyzartria 	tinyint,
	@DysfagiaObjaw tinyint,
	@RBD	tinyint,
	@ZaburzenieRuchomosciGalekOcznych	tinyint,
	@Apraksja	tinyint,
	@TestKlaskania	tinyint,
	@ZaburzeniaWechowe	tinyint,
	@actor_login varchar(50),
	@result int OUTPUT, @message varchar(200) OUTPUT )
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


	-- validations

	if dbo.validate_input_int('Wizyta', 'PrzebyteLeczenieOperacyjnePD', @PrzebyteLeczenieOperacyjnePD) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute PrzebyteLeczenieOperacyjnePD - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'Papierosy', @Papierosy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Papierosy';
		return;
	end;
	if dbo.validate_input_int('Wizyta', 'Kawa', @Kawa) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Kawa';
		return;
	end;

	if( dbo.validate_ext_bit( @ZielonaHerbata) = 0 )
	begin
		set @result = 2;
		set @message = 'ZielonaHerbata - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'Alkohol', @Alkohol) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Alkohol';
		return;
	end;
	if dbo.validate_input_int('Wizyta', 'Zamieszkanie', @Zamieszkanie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Zamieszkanie';
		return;
	end;

	if( dbo.validate_ext_bit( @Nadcisnienie ) = 0 )
	begin
		set @result = 2;
		set @message = 'Nadcisnienie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @BlokeryKanWapn ) = 0 )
	begin
		set @result = 2;
		set @message = 'BlokeryKanWapn - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'DominujacyObjawObecnie', @DominujacyObjawObecnie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute DominujacyObjawObecnie - see enumeration for allowed values';
		return;
	end;

	if( dbo.validate_ext_bit( @RLS) = 0 )
	begin
		set @result = 2;
		set @message = 'RLS - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ObjawyPsychotyczne) = 0 )
	begin
		set @result = 2;
		set @message = 'ObjawyPsychotyczne - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Depresja) = 0 )
	begin
		set @result = 2;
		set @message = 'Depresja - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	if dbo.validate_input_decimal('Wizyta', 'Otepienie', @Otepienie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Otepienie';
		return;
	end;

	if( dbo.validate_ext_bit( @Dyzartria ) = 0 )
	begin
		set @result = 2;
		set @message = 'Dyzartria  - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @DysfagiaObjaw) = 0 )
	begin
		set @result = 2;
		set @message = 'Dysfagia objaw - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @RBD) = 0 )
	begin
		set @result = 2;
		set @message = 'RBD - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ZaburzenieRuchomosciGalekOcznych) = 0 )
	begin
		set @result = 2;
		set @message = 'ZaburzenieRuchomosciGalekOcznych - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Apraksja) = 0 )
	begin
		set @result = 2;
		set @message = 'Apraksja - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @TestKlaskania) = 0 )
	begin
		set @result = 2;
		set @message = 'TestKlaskania - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ZaburzeniaWechowe) = 0 )
	begin
		set @result = 2;
		set @message = 'ZaburzeniaWechowe - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	update Wizyta
		set 
			PrzebyteLeczenieOperacyjnePD  = @PrzebyteLeczenieOperacyjnePD,
			Papierosy = @Papierosy,	Kawa = @Kawa, 
			ZielonaHerbata = @ZielonaHerbata, 
			Alkohol = @Alkohol, 
			ZabiegowWZnieczOgPrzedRozpoznaniemPD = @ZabiegowWZnieczOgPrzedRozpoznaniemPD,
			Zamieszkanie = @Zamieszkanie, 
			Uwagi = REPLACE(@Uwagi,';','. '), 
			Nadcisnienie  = @Nadcisnienie,
			BlokeryKanWapn  = @BlokeryKanWapn,
			DominujacyObjawObecnie  = @DominujacyObjawObecnie,
			DominujacyObjawUwagi  = REPLACE(@DominujacyObjawUwagi,';','. '),

			RLS	= @RLS,
			ObjawyPsychotyczne	= @ObjawyPsychotyczne,
			Depresja	= @Depresja,
			Otepienie	= @Otepienie,
			Dyzartria 	= @Dyzartria,
			DysfagiaObjaw = @DysfagiaObjaw,
			RBD	= @RBD,
			ZaburzenieRuchomosciGalekOcznych	= @ZaburzenieRuchomosciGalekOcznych,
			Apraksja	= @Apraksja,
			TestKlaskania	= @TestKlaskania,
			ZaburzeniaWechowe = @ZaburzeniaWechowe,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go



-- modified: 2015-07-19
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_examination_questionnaire_partC  (
	@IdWizyta int,
	@Ldopa tinyint,
	@LDopaObecnie smallint,
	@Agonista bit,
	@AgonistaObecnie	smallint,
	@Amantadyna	bit,
	@AmantadynaObecnie smallint,
	@MAOBinh bit,
	@MAOBinhObecnie smallint,
	@COMTinh bit,
	@COMTinhObecnie smallint,
	@Cholinolityk bit,
	@CholinolitykObecnie smallint,
	@LekiInne bit,
	@LekiInneJakie varchar(50),
	@L_STIMOpis varchar(30),
	@L_STIMAmplitude decimal(5,1),
	@L_STIMDuration decimal(5,1),
	@L_STIMFrequency decimal(5,1),
	@R_STIMOpis varchar(30),
	@R_STIMAmplitude decimal(5,1),
	@R_STIMDuration decimal(5,1),
	@R_STIMFrequency decimal(5,1),
	@Wypis_Ldopa tinyint,
	@Wypis_LDopaObecnie smallint,
	@Wypis_Agonista bit,
	@Wypis_AgonistaObecnie smallint,
	@Wypis_Amantadyna bit,
	@Wypis_AmantadynaObecnie smallint,
	@Wypis_MAOBinh bit,
	@Wypis_MAOBinhObecnie smallint,
	@Wypis_COMTinh bit,
	@Wypis_COMTinhObecnie smallint,
	@Wypis_Cholinolityk bit,
	@Wypis_CholinolitykObecnie smallint,
	@Wypis_LekiInne bit,
	@Wypis_LekiInneJakie varchar(50),
	@Wypis_L_STIMOpis varchar(30),
	@Wypis_L_STIMAmplitude decimal(5,1),
	@Wypis_L_STIMDuration decimal(5,1),
	@Wypis_L_STIMFrequency decimal(5,1),
	@Wypis_R_STIMOpis varchar(30),
	@Wypis_R_STIMAmplitude decimal(5,1),
	@Wypis_R_STIMDuration decimal(5,1),
	@Wypis_R_STIMFrequency decimal(5,1),
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
			Ldopa = @Ldopa,
			LDopaObecnie = @LDopaObecnie,
			Agonista = @Agonista,
			AgonistaObecnie = @AgonistaObecnie,
			Amantadyna = @Amantadyna,
			AmantadynaObecnie = @AmantadynaObecnie,
			MAOBinh = @MAOBinh,
			MAOBinhObecnie = @MAOBinhObecnie,
			COMTinh = @COMTinh,
			COMTinhObecnie = @COMTinhObecnie,
			Cholinolityk = @Cholinolityk,
			CholinolitykObecnie = @CholinolitykObecnie,
			LekiInne = @LekiInne,
			LekiInneJakie = REPLACE(@LekiInneJakie,';','. '),
			L_STIMOpis = REPLACE(@L_STIMOpis,';','. '),
			L_STIMAmplitude = @L_STIMAmplitude,
			L_STIMDuration = @L_STIMDuration,
			L_STIMFrequency = @L_STIMFrequency,
			R_STIMOpis = REPLACE(@R_STIMOpis,';','. '),
			R_STIMAmplitude = @R_STIMAmplitude,
			R_STIMDuration = @R_STIMDuration,
			R_STIMFrequency = @R_STIMFrequency,
			Wypis_Ldopa = @Wypis_Ldopa,
			Wypis_LDopaObecnie = @Wypis_LDopaObecnie,
			Wypis_Agonista = @Wypis_Agonista,
			Wypis_AgonistaObecnie = @Wypis_AgonistaObecnie,
			Wypis_Amantadyna = @Wypis_Amantadyna,
			Wypis_AmantadynaObecnie = @Wypis_AmantadynaObecnie,
			Wypis_MAOBinh = @Wypis_MAOBinh,
			Wypis_MAOBinhObecnie = @Wypis_MAOBinhObecnie,
			Wypis_COMTinh = @Wypis_COMTinh,
			Wypis_COMTinhObecnie = @Wypis_COMTinhObecnie,
			Wypis_Cholinolityk = @Wypis_Cholinolityk,
			Wypis_CholinolitykObecnie = @Wypis_CholinolitykObecnie,
			Wypis_LekiInne = @Wypis_LekiInne,
			Wypis_LekiInneJakie = REPLACE(@Wypis_LekiInneJakie,';','. '),
			Wypis_L_STIMOpis = REPLACE(@Wypis_L_STIMOpis,';','. '),
			Wypis_L_STIMAmplitude = @Wypis_L_STIMAmplitude,
			Wypis_L_STIMDuration = @Wypis_L_STIMDuration,
			Wypis_L_STIMFrequency = @Wypis_L_STIMFrequency,
			Wypis_R_STIMOpis = REPLACE(@Wypis_L_STIMOpis,';','. '),
			Wypis_R_STIMAmplitude = @Wypis_R_STIMAmplitude,
			Wypis_R_STIMDuration = @Wypis_R_STIMDuration,
			Wypis_R_STIMFrequency = @Wypis_R_STIMFrequency,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go



-- last rev. 2015-07-20
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

			TestZegara =  REPLACE(@TestZegara,';','. '),
			MMSE =  REPLACE(@MMSE,';','. '),
			CLOX1_Rysunek =  REPLACE(@CLOX1_Rysunek,';','. '),
			CLOX2_Kopia =  REPLACE(@CLOX2_Kopia,';','. '),
			AVLT_proba_1 =  REPLACE(@AVLT_proba_1,';','. '),
			AVLT_proba_2 =  REPLACE(@AVLT_proba_2,';','. '),
			AVLT_proba_3 =  REPLACE(@AVLT_proba_3,';','. '),
			AVLT_proba_4 =  REPLACE(@AVLT_proba_4,';','. '),
			AVLT_proba_5 =  REPLACE(@AVLT_proba_5,';','. '),
			AVLT_Suma =  REPLACE(@AVLT_Suma,';','. '),
			AVLT_Srednia =  REPLACE(@AVLT_Srednia,';','. '),
			AVLT_KrotkieOdroczenie =  REPLACE(@AVLT_KrotkieOdroczenie,';','. '),
			AVLT_Odroczony20min =  REPLACE(@AVLT_Odroczony20min,';','. '),
			AVLT_Rozpoznawanie =  REPLACE(@AVLT_Rozpoznawanie,';','. '),
			AVLT_BledyRozpoznania =  REPLACE(@AVLT_BledyRozpoznania,';','. '),

			TestAVLTSrednia =  REPLACE(@TestAVLTSrednia,';','. '),
			TestAVLTOdroczony =  REPLACE(@TestAVLTOdroczony,';','. '),
			TestAVLTPo20min =  REPLACE(@TestAVLTPo20min,';','. '),
			TestAVLTRozpoznawanie =  REPLACE(@TestAVLTRozpoznawanie,';','. '),

			CVLT_proba_1 =  REPLACE(@CVLT_proba_1,';','. '),
			CVLT_proba_2 =  REPLACE(@CVLT_proba_2,';','. '),
			CVLT_proba_3 =  REPLACE(@CVLT_proba_3,';','. '),
			CVLT_proba_4 =  REPLACE(@CVLT_proba_4,';','. '),
			CVLT_proba_5 =  REPLACE(@CVLT_proba_5,';','. '),
			CVLT_Suma =  REPLACE(@CVLT_Suma,';','. '),
			CVLT_OSKO_krotkie_odroczenie =  REPLACE(@CVLT_OSKO_krotkie_odroczenie,';','. '),
			CVLT_OPKO_krotkie_odroczenie_i_pomoc =  REPLACE(@CVLT_OPKO_krotkie_odroczenie_i_pomoc,';','. '),
			CVLT_OSDO_po20min =  REPLACE(@CVLT_OSDO_po20min,';','. '),
			CVLT_OPDO_po20min_i_pomoc =  REPLACE(@CVLT_OPDO_po20min_i_pomoc,';','. '),
			CVLT_perseweracje =  REPLACE(@CVLT_perseweracje,';','. '),
			CVLT_WtraceniaOdtwarzanieSwobodne =  REPLACE(@CVLT_WtraceniaOdtwarzanieSwobodne,';','. '),
			CVLT_wtraceniaOdtwarzanieZPomoca =  REPLACE(@CVLT_wtraceniaOdtwarzanieZPomoca,';','. '),
			CVLT_Rozpoznawanie =  REPLACE(@CVLT_Rozpoznawanie,';','. '),
			CVLT_BledyRozpoznania =  REPLACE(@CVLT_BledyRozpoznania,';','. '),
			Benton_JOL =  REPLACE(@Benton_JOL,';','. '),
			WAIS_R_Wiadomosci =  REPLACE(@WAIS_R_Wiadomosci,';','. '),
			WAIS_R_PowtarzanieCyfr =  REPLACE(@WAIS_R_PowtarzanieCyfr,';','. '),
			WAIS_R_Podobienstwa =  REPLACE(@WAIS_R_Podobienstwa,';','. '),
			BostonskiTestNazywaniaBMT =  REPLACE(@BostonskiTestNazywaniaBMT,';','. '),
			BMT_SredniCzasReakcji_sek =  REPLACE(@BMT_SredniCzasReakcji_sek,';','. '),
			SkalaDepresjiBecka = @SkalaDepresjiBecka,
			SkalaDepresjiBeckaII = @SkalaDepresjiBeckaII,
			TestFluencjiK = REPLACE(@TestFluencjiK,';','. '),
			TestFluencjiP = REPLACE(@TestFluencjiP,';','. '),
			TestFluencjiZwierzeta = REPLACE(@TestFluencjiZwierzeta,';','. '),
			TestFluencjiOwoceWarzywa = REPLACE(@TestFluencjiOwoceWarzywa,';','. '),
			TestFluencjiOstre = REPLACE(@TestFluencjiOstre,';','. '),
			TestLaczeniaPunktowA = REPLACE(@TestLaczeniaPunktowA,';','. '),
			TestLaczeniaPunktowB = REPLACE(@TestLaczeniaPunktowB,';','. '),
			ToL_SumaRuchow = REPLACE(@ToL_SumaRuchow,';','. '),
			ToL_LiczbaPrawidlowych = REPLACE(@ToL_LiczbaPrawidlowych,';','. '),
			ToL_CzasInicjowania_sek = REPLACE(@ToL_CzasInicjowania_sek,';','. '),
			ToL_CzasWykonania_sek = REPLACE(@ToL_CzasWykonania_sek,';','. '),
			ToL_CzasCalkowity_sek = REPLACE(@ToL_CzasCalkowity_sek,';','. '),
			ToL_CzasPrzekroczony = REPLACE(@ToL_CzasPrzekroczony,';','. '),
			ToL_LiczbaPrzekroczenZasad = REPLACE(@ToL_LiczbaPrzekroczenZasad,';','. '),
			ToL_ReakcjeUkierunkowane = REPLACE(@ToL_ReakcjeUkierunkowane,';','. '),
			InnePsychologiczne = REPLACE(@InnePsychologiczne,';','. '),
			OpisBadania = REPLACE(REPLACE(REPLACE(@OpisBadania, CHAR(13), ''), CHAR(10), ''),';','. '),
			Wnioski = REPLACE(REPLACE(REPLACE(@Wnioski, CHAR(13), ''), CHAR(10), ''),';','. '),
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- updated 2015-07-20
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_examination_questionnaire_partH  (
	@IdWizyta int,
	@Holter bit,
	@BadanieWechu bit,
	@WynikWechu tinyint,
	@LimitDysfagii tinyint,
	@pH_metriaPrze造ku bit,
	@SPECT bit,
	@MRI bit,
	@MRIwynik varchar(2000),
	@USGsrodmozgowia bit,
	@USGWynik tinyint,
	@Genetyka bit,
	@GenetykaWynik varchar(50),
	@Surowica bit,
	@SurowicaPozosta這 varchar(50),
	@Ferrytyna decimal(7,3),
	@CRP decimal(7,3),
	@NTproCNP decimal(7,3),
	@URCA decimal(7,3),
	@WitD decimal(7,3),
	@CHOL decimal(7,3),
	@TGI decimal(7,3),
	@HDL decimal(7,3),
	@LDL decimal(7,3),
	@olLDL decimal(7,3),
	@LaboratoryjneInne varchar(1000),
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

	-- validators

	if dbo.validate_input_int('Wizyta', 'WynikWechu', @WynikWechu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute WynikWechu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'LimitDysfagii', @LimitDysfagii) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute LimitDysfagii - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'USGWynik ', @USGWynik ) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute USGWynik  - see enumeration for allowed values';
		return;
	end;

	update Wizyta
		set 
			Holter = @Holter,
			BadanieWechu  = @BadanieWechu,
			WynikWechu  = @WynikWechu,
			LimitDysfagii  = @LimitDysfagii,
			pH_metriaPrze造ku = @pH_metriaPrze造ku,
			SPECT = @SPECT,
			MRI = @MRI,
			MRIwynik = REPLACE(@MRIwynik,';','. '),
			USGsrodmozgowia = @USGsrodmozgowia,
			USGWynik = @USGWynik,
			Genetyka = @Genetyka,
			GenetykaWynik = REPLACE(@GenetykaWynik,';','. '),
			Surowica = @Surowica,
			SurowicaPozosta這 = REPLACE(@SurowicaPozosta這,';','. '),
			Ferrytyna = @Ferrytyna,
			CRP = @CRP,
			NTproCNP = @NTproCNP,
			URCA = @URCA,
			WitD = @WitD,
			CHOL = @CHOL,
			TGI = @TGI,
			HDL = @HDL,
			LDL = @LDL,
			olLDL = @olLDL,
			LaboratoryjneInne = REPLACE(@LaboratoryjneInne,';','. '),
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go
