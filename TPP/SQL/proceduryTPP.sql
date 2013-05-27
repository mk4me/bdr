use TPP;
go

-- last rev. 
create procedure zapisz_dane_badania(
	@pid int,
	@IdPacjent			int,
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
	*/	


	/* Sprawdz unikalnosc wprowadzanych danych */



	/* Dokonaj walidacji wartosci antrybutow */

	/* Wprowadz do Pacjent nowy wiersz pacjenta, jesli brak */
	
	/* Wprowadz do Badanie wiersz danych */


	set @result = 0;
	
	  if( ISNUMERIC ( SUBSTRING(@sname,2,2))<>1)
		begin
			set @result = 11;
			return;
		end;
	set @sid = CAST ( SUBSTRING(@sname,2,2) as int);
	
	select @pc_id = kp.IdKonfiguracja_performera from
	 Konfiguracja_performera kp join Sesja s on kp.IdSesja = s.IdSesja where kp.IdPerformer = @pid and CHARINDEX (@sname, s.Nazwa ) > 0;


// przyklad skladni wolania procedury - do wyciecia
	exec set_performer_conf_attribute @pc_id, 'BodyMass', @BodyMass, 0, @result OUTPUT;
	if( @result <> 0 ) return;

end;