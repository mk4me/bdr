use Motion;
go
alter procedure set_measurement_attribute (@meas_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	

	*/

	set @result = 6; -- result 6 = type casting erroron

	if(@attr_name = 'MeasurementConfId') 
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'MeasurementConfId')
			begin
				set @integer_value = cast ( @attr_value as numeric(10,2) );
				if(exists(select * from Konfiguracja_pomiarowa where IdKonfiguracja_pomiarowa = @integer_value))
					begin
						update Pomiar set IdKonfiguracja_pomiarowa  = @integer_value where IdPomiar = @meas_id;	
						set @result = 0;
					end;
				else
					begin
						set @result = 7; -- result 7 = illegal ID value
					end;
				return;
			end;
	end;

	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'measurement';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Pomiar where IdPomiar = @meas_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_pomiaru where IdAtrybut = @attr_id and IdPomiar = @meas_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_pomiaru set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_liczba) values (@attr_id, @meas_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pomiaru set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_zmiennoprzecinkowa) values (@attr_id, @meas_id, @float_value);
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
					update Wartosc_atrybutu_pomiaru set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_Id) values (@attr_id, @meas_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pomiaru set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_tekst) values (@attr_id, @meas_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go


