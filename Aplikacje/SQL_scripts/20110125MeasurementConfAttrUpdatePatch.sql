use Motion;
go
alter procedure set_measurement_conf_attribute (@mc_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	'MeasurementConfName', 'string', 0, 'shortString'
	'MeasurementConfKind', 'string', 0, 'shortString'
	'MeasurementConfDescription', 'string', 0, 'longString'

	*/

	set @result = 6; -- result 6 = type casting error

	if(@attr_name = 'MeasurementConfName' or @attr_name = 'MeasurementConfKind' or @attr_name = 'MeasurementConfDescription')
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'MeasurementConfName')
			update Konfiguracja_pomiarowa set Nazwa  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfKind')
			update Konfiguracja_pomiarowa set Rodzaj  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfDescription')
			update Konfiguracja_pomiarowa set Opis  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		set @result = 0;
		return;
	end;
	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'measurement_conf';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Konfiguracja_pomiarowa where IdKonfiguracja_pomiarowa = @mc_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_konfiguracji_pomiarowej where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ) set @value_tuple_found = 1;
			if @update = 0 and @value_tuple_found = 1
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;
			if(@attr_enum = 1) 
			begin
				select top(1) Wartosc_wyliczeniowa from Wartosc_wyliczeniowa where Wartosc_wyliczeniowa=@attr_value ;
				if @@rowcount = 0 begin set @result = 2; return; end; -- result 2 = illegal enum attribute value
			end;
			if @attr_type = 'integer'
				begin
					set @integer_value = cast ( @attr_value as numeric(10,2) );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_liczba) values (@attr_id, @mc_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_zmiennoprzecinkowa) values (@attr_id, @mc_id, @float_value);
				end;
			else if @attr_type = 'file'
				begin
					set @id_value = cast ( @attr_value as int );
					if ( not exists ( select IdPlik from Plik where IdPlik = @id_value) )
					begin
						set @result = 7;
						return;
					end;
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_Id) values (@attr_id, @mc_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_tekst) values (@attr_id, @mc_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go	

