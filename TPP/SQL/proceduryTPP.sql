use TPP;
go

-- last rev. 2013-05-28
create procedure zapisz_dane_badania(
	@NrPacjenta			int,
	@Wizyta  			tinyint,
	@Plec 				tinyint,
	@Wiek 				tinyint,
	@CzasTrwaniaChoroby tinyint,
	@WiekZachorowania 	tinyint,
	@ObjawyObecnieWyst 	tinyint,
	@Drzenie 			bit,
	@Sztywnosc 			bit,
	@Spowolnienie 		bit,
	@DyskinezyObecnie 	bit,
	@DyskinezyOdLat 		tinyint,
	@FluktuacjeObecnie 	bit,
	@FluktuacjeOdLat 	tinyint,
	@CzasOFF 			tinyint,
	@CzasDyskinez 		tinyint,
	@PoprawaPoLDopie 	bit,
	@LDopaObecnie 		int,
	@AgonistaObecnie 	int,
	@LekiInne tinyint,
	--
	@komunikat varchar(200) OUTPUT,
	@result int OUTPUT )
as
begin
	/* Error codes:
		1 = blad walidacji danych - zob. parametr komunikat
		3 = badanie juz istnieje w bazie
		9 = problem z zarejestrowaniem pacjenta
	*/	


	declare @id_pacjenta int;
	set @id_pacjenta = 0;

	set @result = 0;


	/* Wprowadz do Pacjent nowy wiersz pacjenta, jesli brak */
	if( not exists ( select * from Pacjent where NumerPacjenta = @NrPacjenta )) insert into Pacjent ( NumerPacjenta ) values ( @NrPacjenta );

	/* Ustal wewnetrzny, bazodanowy identifikator pacjenta */
	select @id_pacjenta = IdPacjent from Pacjent where NumerPacjenta = @NrPacjenta;

	if ( @id_pacjenta = 0 )
	if ( exists (select * from Badanie where IdPacjent = @id_pacjenta and Wizyta = @Wizyta ) )
		begin
			set @result = 9;
			return;
		end;

	/* Sprawdz unikalnosc wprowadzanych danych */

	if ( exists (select * from Badanie where IdPacjent = @id_pacjenta and Wizyta = @Wizyta ) )
		begin
			set @komunikat = 'Badanie rodzaju '+ cast ( @Wizyta as varchar )+' pacjenta o numerze '+ cast ( @NrPacjenta as varchar )+' ju¿ istnieje w bazie.';
			set @result = 3;
			return;
		end;

	/* Dokonaj walidacji wartosci antrybutow */
	/* To Do */
	
	/* Wprowadz do Badanie wiersz danych */
	insert into Badanie ( IdPacjent, Wizyta, Plec, Wiek, CzasTrwaniaChoroby, WiekZachorowania, ObjawyObecnieWyst, Drzenie,	Sztywnosc, Spowolnienie, DyskinezyObecnie, DyskinezyOdLat, FluktuacjeObecnie, FluktuacjeOdLat, CzasOFF, CzasDyskinez, PoprawaPoLDopie, LDopaObecnie, AgonistaObecnie, LekiInne )
	values ( @id_pacjenta, @Wizyta, @Plec, @Wiek, @CzasTrwaniaChoroby, @WiekZachorowania, @ObjawyObecnieWyst, @Drzenie,	@Sztywnosc, @Spowolnienie, @DyskinezyObecnie, @DyskinezyOdLat, @FluktuacjeObecnie, @FluktuacjeOdLat, @CzasOFF, @CzasDyskinez, @PoprawaPoLDopie, @LDopaObecnie, @AgonistaObecnie, @LekiInne );

	return;
	
end;