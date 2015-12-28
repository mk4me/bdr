use TPP;
go


-- zmiana zakresu wartosci dla kolumn tremorometrii:
	alter table Badanie alter column TremorometriaLEFT_0_1 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_1_2 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_2_3 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_3_4 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_4_5 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_5_6 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_6_7 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_7_8 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_8_9 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_9_10 decimal(11,5);
	alter table Badanie alter column TremorometriaLEFT_23_24 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_0_1 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_1_2 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_2_3 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_3_4 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_4_5 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_5_6 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_6_7 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_7_8 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_8_9 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_9_10 decimal(11,5);
	alter table Badanie alter column TremorometriaRIGHT_23_24 decimal(11,5);




alter table Wizyta add TestZegaraACE_III tinyint;

update Kolumna 
set PozycjaDomyslna = PozycjaDomyslna + 1
where PozycjaDomyslna > (select PozycjaDomyslna from Kolumna where Encja = 'W' and Nazwa = 'TestZegara');
go

insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) 
select PozycjaDomyslna+1, 'W', 'TestZegaraACE_III' from Kolumna where Encja = 'W' and Nazwa = 'TestZegara';
go


alter table Wizyta add TestLaczeniaPunktowA_maly varchar(40);
alter table Wizyta add TestLaczeniaPunktowB_maly varchar(40);

update Kolumna 
set PozycjaDomyslna = PozycjaDomyslna + 2
where PozycjaDomyslna > (select PozycjaDomyslna from Kolumna where Encja = 'W' and Nazwa = 'TestLaczeniaPunktowB');
go

insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) 
select PozycjaDomyslna+1, 'W', 'TestLaczeniaPunktowA_maly' from Kolumna where Encja = 'W' and Nazwa = 'TestLaczeniaPunktowB';
go

insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) 
select PozycjaDomyslna+2, 'W', 'TestLaczeniaPunktowB_maly' from Kolumna where Encja = 'W' and Nazwa = 'TestLaczeniaPunktowB';
go

alter table Wizyta add 	TFZ_ReyaLubInny tinyint;

update Kolumna 
set PozycjaDomyslna = PozycjaDomyslna + 1
where PozycjaDomyslna > (select PozycjaDomyslna from Kolumna where Encja = 'W' and Nazwa = 'Benton_JOL');
go

insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) 
select PozycjaDomyslna+1, 'W', 'TFZ_ReyaLubInny' from Kolumna where Encja = 'W' and Nazwa = 'Benton_JOL';
go


EXEC sp_RENAME 'Wizyta.BostonskiTestNazywaniaBMT' , 'BostonskiTestNazywaniaBNT', 'COLUMN'
go

EXEC sp_RENAME 'Wizyta.BMT_SredniCzasReakcji_sek' , 'BNT_SredniCzasReakcji_sek', 'COLUMN'
go

update Kolumna
set Nazwa = 'BostonskiTestNazywaniaBNT'
where Nazwa = 'BostonskiTestNazywaniaBMT';
go

update Kolumna
set Nazwa = 'BNT_SredniCzasReakcji_sek'
where Nazwa = 'BMT_SredniCzasReakcji_sek';
go


-- last rev. 2015-12-23
-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_variant_examination_data_partB  (	@IdBadanie int,
	@Tremorometria bit, 
	@TremorometriaLEFT bit, 
	@TremorometriaRIGHT bit, 
	@TremorometriaLEFT_0_1 decimal(11,5),
	@TremorometriaLEFT_1_2 decimal(11,5),
	@TremorometriaLEFT_2_3 decimal(11,5),
	@TremorometriaLEFT_3_4 decimal(11,5),
	@TremorometriaLEFT_4_5 decimal(11,5),
	@TremorometriaLEFT_5_6 decimal(11,5),
	@TremorometriaLEFT_6_7 decimal(11,5),
	@TremorometriaLEFT_7_8 decimal(11,5),
	@TremorometriaLEFT_8_9 decimal(11,5),
	@TremorometriaLEFT_9_10 decimal(11,5),
	@TremorometriaLEFT_23_24 decimal(11,5),
	@TremorometriaRIGHT_0_1 decimal(11,5),
	@TremorometriaRIGHT_1_2 decimal(11,5),
	@TremorometriaRIGHT_2_3 decimal(11,5),
	@TremorometriaRIGHT_3_4 decimal(11,5),
	@TremorometriaRIGHT_4_5 decimal(11,5),
	@TremorometriaRIGHT_5_6 decimal(11,5),
	@TremorometriaRIGHT_6_7 decimal(11,5),
	@TremorometriaRIGHT_7_8 decimal(11,5),
	@TremorometriaRIGHT_8_9 decimal(11,5),
	@TremorometriaRIGHT_9_10 decimal(11,5),
	@TremorometriaRIGHT_23_24 decimal(11,5),	
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


-- last rev. 2015-12-23
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
alter procedure update_examination_questionnaire_partG  (
	@IdWizyta int,
	@TestZegara bit,	-- juz bylo; niezmienione
	@TestZegaraACE_III tinyint, -- dodane 2015-12-23
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
	@TFZ_ReyaLubInny tinyint, -- dodane 2015-12-23
	@WAIS_R_Wiadomosci tinyint,-- juz bylo; niezmienione
	@WAIS_R_PowtarzanieCyfr tinyint,-- juz bylo; niezmienione
	@WAIS_R_Podobienstwa tinyint, -- dodane 2015-03-20
	@BostonskiTestNazywaniaBNT tinyint, -- dodane 2015-03-20
	@BNT_SredniCzasReakcji_sek int, -- dodane 2015-03-20
	@SkalaDepresjiBecka decimal(4,1),-- zmiana na decimal;
	@SkalaDepresjiBeckaII decimal(4,1),-- dodane 2015-03-20

	@TestFluencjiK tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	@TestFluencjiP tinyint, -- dodane 2015-03-20
	@TestFluencjiZwierzeta tinyint,-- bylo; ale zmiana z varchar(40) na tinyint
	@TestFluencjiOwoceWarzywa tinyint, -- dodane 2015-03-20
	@TestFluencjiOstre tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	@TestLaczeniaPunktowA varchar(40), -- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	@TestLaczeniaPunktowB varchar(40),-- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	@TestLaczeniaPunktowA_maly varchar(40), -- dodano 2015-12-23
	@TestLaczeniaPunktowB_maly varchar(40), -- dodano 2015-12-13
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
			TestZegaraACE_III = @TestZegaraACE_III,
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
			TFZ_ReyaLubInny = @TFZ_ReyaLubInny,
			WAIS_R_Wiadomosci =  REPLACE(@WAIS_R_Wiadomosci,';','. '),
			WAIS_R_PowtarzanieCyfr =  REPLACE(@WAIS_R_PowtarzanieCyfr,';','. '),
			WAIS_R_Podobienstwa =  REPLACE(@WAIS_R_Podobienstwa,';','. '),
			BostonskiTestNazywaniaBNT =  REPLACE(@BostonskiTestNazywaniaBNT,';','. '),
			BNT_SredniCzasReakcji_sek =  REPLACE(@BNT_SredniCzasReakcji_sek,';','. '),
			SkalaDepresjiBecka = @SkalaDepresjiBecka,
			SkalaDepresjiBeckaII = @SkalaDepresjiBeckaII,
			TestFluencjiK = REPLACE(@TestFluencjiK,';','. '),
			TestFluencjiP = REPLACE(@TestFluencjiP,';','. '),
			TestFluencjiZwierzeta = REPLACE(@TestFluencjiZwierzeta,';','. '),
			TestFluencjiOwoceWarzywa = REPLACE(@TestFluencjiOwoceWarzywa,';','. '),
			TestFluencjiOstre = REPLACE(@TestFluencjiOstre,';','. '),
			TestLaczeniaPunktowA = REPLACE(@TestLaczeniaPunktowA,';','. '),
			TestLaczeniaPunktowB = REPLACE(@TestLaczeniaPunktowB,';','. '),
			TestLaczeniaPunktowA_maly = REPLACE(@TestLaczeniaPunktowA_maly,';','. '),
			TestLaczeniaPunktowB_maly = REPLACE(@TestLaczeniaPunktowB_maly,';','. '),
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
	,W.[TestZegaraACE_III] TestZegaraACE_III
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
	,W.[TFZ_ReyaLubInny] TFZ_ReyaLubInny
	,W.[WAIS_R_Wiadomosci] WAIS_R_Wiadomosci
	,W.[WAIS_R_PowtarzanieCyfr] WAIS_R_PowtarzanieCyfr
	,W.[WAIS_R_Podobienstwa] WAIS_R_Podobienstwa
	,W.[BostonskiTestNazywaniaBNT] BostonskiTestNazywaniaBNT
	,W.[BNT_SredniCzasReakcji_sek] BNT_SredniCzasReakcji_sek
	,W.[SkalaDepresjiBecka]
	,W.[SkalaDepresjiBeckaII]
	,W.[TestFluencjiK] TestFluencjiK
	,W.[TestFluencjiP] TestFluencjiP
	,W.[TestFluencjiZwierzeta] TestFluencjiZwierzeta
	,W.[TestFluencjiOwoceWarzywa] TestFluencjiOwoceWarzywa
	,W.[TestFluencjiOstre] TestFluencjiOstre
	,W.[TestLaczeniaPunktowA] TestLaczeniaPunktowA
	,W.[TestLaczeniaPunktowB] TestLaczeniaPunktowB
	,W.[TestLaczeniaPunktowA_maly] TestLaczeniaPunktowA_maly
	,W.[TestLaczeniaPunktowB_maly] TestLaczeniaPunktowB_maly
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



