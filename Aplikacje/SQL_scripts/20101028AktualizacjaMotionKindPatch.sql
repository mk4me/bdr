use Motion;
go


update Atrybut set Nazwa='MotionKind', Typ_danych='string', Wyliczeniowy=1, Podtyp_danych='shortString' where Nazwa='MotionKindID';
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'walk') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'run') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'jump') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'hop') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'sit') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'trot') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'MotionKind'), 'dance') 
go

alter procedure set_session_attribute (@sess_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int ;
	declare @value_tuple_found as bit = 0;
	declare @calculated_id as int;	




	/*
	Current static non-id attributes:
		'LabID', 'int', 0, 'ID'
		'MotionKindID', 'int', 0, 'ID'
		'SessionDate', 'string', 0, 'dateTime'
		'SessionDescription', 'string', 0, 'longString'

	*/

	set @result = 6; -- result 3 = type casting error


	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja = 'session';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;

	if not exists ( select * from Sesja where IdSesja = @sess_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin


			if(@attr_name = 'LabID' or @attr_name = 'MotionKind' or @attr_name = 'SessionDate' or @attr_name = 'SessionDescription' )
			begin
				if(@update = 0)
						begin
							set @result = 5; -- result 5 = value exists while update has not been allowed
							return;
						end;	
				if(@attr_name = 'LabID')
					begin
						update Sesja set IdLaboratorium  = cast ( @attr_value as int ) where IdSesja = @sess_id;
					end;
				else if(@attr_name = 'MotionKind')
					begin
						select top(1) @calculated_id = IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa=@attr_value ;
						if (@calculated_id is null) begin set @result = 2; return; end; -- result 2 = illegal enum attribute value
						update Sesja set IdRodzaj_ruchu  =  @calculated_id where IdSesja = @sess_id;
					end
				else if(@attr_name = 'SessionDate')
					update Sesja set Data  =  cast ( @attr_value as datetime ) where IdSesja = @sess_id;
				else if(@attr_name = 'SessionDescription')
					update Sesja set Opis_sesji  = @attr_value where IdSesja = @sess_id;
				set @result = 0;
				return;
			end;
	

			if exists ( select * from Wartosc_atrybutu_sesji where IdAtrybut = @attr_id and IdSesja = @sess_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_sesji set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdSesja = @sess_id ;
					else
					insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_liczba) values (@attr_id, @sess_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_sesji set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdSesja = @sess_id ;
					else
					insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_zmiennoprzecinkowa) values (@attr_id, @sess_id, @float_value);
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
					update Wartosc_atrybutu_sesji set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdSesja = @sess_id ;
					else
					insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_Id) values (@attr_id, @sess_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_sesji set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdSesja = @sess_id ;
					else
					insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_tekst) values (@attr_id, @sess_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go
