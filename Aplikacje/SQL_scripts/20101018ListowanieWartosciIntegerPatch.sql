use Motion;
go

alter function list_performer_attributes ( @perf_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wap.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
	a.Typ_danych as Type,
	ga.Nazwa as AttributeGroup,
	'performer' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id and a.Typ_danych <> 'file'
go


alter function list_performer_attributes_uniform ( @perf_id int )
returns TABLE as
return 
(
 with Perf as
( select * from Performer where IdPerformer=@perf_id )
	(
	(select 'FirstName' as Name,
			p.Imie as Value,
			'string' as Type,
			'_performer_static' as AttributeGroup,
			'performer' as Entity
	from Perf p)
	union
	(select 'LastName' as Name,
			p.Nazwisko as Value,
			'string' as Type,
			'_performer_static' as AttributeGroup,
			'performer' as Entity
	from Perf p)
	union
	(select 'PerformerID' as Name,
			p.IdPerformer as Value,
			'integer' as Type,
			'_performer_static' as AttributeGroup,
			'performer' as Entity
	from Perf p)
	)
union
(select
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wap.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
	a.Typ_danych as Type,
	ga.Nazwa as AttributeGroup,
	'performer' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id  and a.Typ_danych <> 'file'));
go


alter function list_session_attributes ( @sess_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( was.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( was.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id and a.Typ_danych <> 'file'
go


alter function list_session_attributes_uniform ( @sess_id int )
returns TABLE as
return 
(
 with Sess as
( select * from Sesja where IdSesja=@sess_id )
	(
	(select 'SessionID' as Name,
			s.IdSesja as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'UserID' as Name,
			s.IdUzytkownik as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'LabID' as Name,
			s.IdLaboratorium as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'MotionKindID' as Name,
			s.IdRodzaj_ruchu as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'SessionDate' as Name,
			s.Data as Value,
			'string' as Type,
			'_session_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'SessionDescription' as Name,
			s.Opis_sesji as Value,
			'string' as Type,
			'_session_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	)
union
(select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( was.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( was.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id and a.Typ_danych <> 'file' ));
go

alter function list_trial_attributes ( @trial_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wao.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wao.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wao.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'trial' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id and a.Typ_danych <> 'file'
go

alter function list_trial_attributes_uniform ( @trial_id int )
returns TABLE as
return 
(
 with Trial as
( select * from Obserwacja where IdObserwacja=@trial_id )
	(
	(select 'TrialID' as Name,
			t.IdObserwacja as Value,
			'integer' as Type,
			'_trial_static' as AttributeGroup,
			'trial' as Entity
	from Trial t)
	union
	(select 'TrialDescription' as Name,
			t.Opis_obserwacji as Value,
			'string' as Type,
			'_trial_static' as AttributeGroup,
			'trial' as Entity
	from Trial t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wao.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wao.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wao.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'trial' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id and a.Typ_danych <> 'file'));
go

alter function list_measurement_configuration_attributes ( @mc_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wakp.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_pomiarowa = @mc_id and a.Typ_danych <> 'file'
go

alter function list_measurement_configuration_attributes_uniform ( @mc_id int )
returns TABLE as
return 
(
 with MeasurementConfiguration as
( select * from Konfiguracja_pomiarowa where IdKonfiguracja_pomiarowa=@mc_id )
	(
	(select 'MeasurementConfID' as Name,
			t.IdKonfiguracja_pomiarowa as Value,
			'integer' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union
	(select 'MeasurementConfName' as Name,
			t.Nazwa as Value,
			'string' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union
	(select 'MeasurementConfKind' as Name,
			t.Rodzaj as Value,
			'string' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union	
	(select 'MeasurementConfDescription' as Name,
			t.Opis as Value,
			'string' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wakp.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_pomiarowa = @mc_id and a.Typ_danych <> 'file' ));
go

alter function list_measurement_attributes ( @meas_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wap.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPomiar = @meas_id and a.Typ_danych <> 'file'
go

alter function list_measurement_attributes_uniform ( @meas_id int )
returns TABLE as
return 
(
 with Measurement as
( select * from Pomiar where IdPomiar=@meas_id )
	(
	(select 'MeasurementID' as Name,
			t.IdPomiar as Value,
			'integer' as Type,
			'_measurement_static' as AttributeGroup,
			'measurement' as Entity
	from Measurement t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wap.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPomiar = @meas_id  and a.Typ_danych <> 'file'));
go

alter function list_performer_configuration_attributes ( @pc_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wakp.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'performer_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_performera wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_performera = @pc_id and a.Typ_danych <> 'file'
go

-- ----------------------------------------
alter procedure set_session_attribute (@sess_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int ;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
		'LabID', 'int', 0, 'ID'
		'MotionKindID', 'int', 0, 'ID'
		'SessionDate', 'string', 0, 'dateTime'
		'SessionDescription', 'string', 0, 'longString'

	*/

	set @result = 6; -- result 3 = type casting error

	if(@attr_name = 'LabID' or @attr_name = 'MotionKindID' or @attr_name = 'SessionDate' or @attr_name = 'SessionDescription' )
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
		else if(@attr_name = 'MotionKindID')
			update Sesja set IdRodzaj_ruchu  =  cast ( @attr_value as int ) where IdSesja = @sess_id;
		else if(@attr_name = 'SessionDate')
			update Sesja set Data  =  cast ( @attr_value as datetime ) where IdSesja = @sess_id;
		else if(@attr_name = 'SessionDescription')
			update Sesja set Opis_sesji  = @attr_value where IdSesja = @sess_id;
		set @result = 0;
		return;
	end;
	
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


alter procedure set_performer_attribute (@perf_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	'FirstName', 'string', 0, 'shortString'
	'LastName', 'string', 0, 'shortString'

	*/

	set @result = 6; -- result 3 = type casting error

	if(@attr_name = 'FirstName' or @attr_name = 'LastName')
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'FirstName')
			update Performer set Imie  = @attr_value where IdPerformer = @perf_id;
		if(@attr_name = 'LastName')
			update Performer set Nazwisko  = @attr_value where IdPerformer = @perf_id;
		set @result = 0;
		return;
	end;

	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja = 'performer';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Performer where IdPerformer = @perf_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_performera where IdAtrybut = @attr_id and IdPerformer = @perf_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_performera set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdPerformer = @perf_id ;
					else
					insert into Wartosc_atrybutu_performera (IdAtrybut, IdPerformer, Wartosc_liczba) values (@attr_id, @perf_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_performera set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdPerformer = @perf_id ;
					else
					insert into Wartosc_atrybutu_performera (IdAtrybut, IdPerformer, Wartosc_zmiennoprzecinkowa) values (@attr_id, @perf_id, @float_value);
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
					update Wartosc_atrybutu_performera set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdPerformer = @perf_id ;
					else
					insert into Wartosc_atrybutu_performera (IdAtrybut, IdPerformer, Wartosc_Id) values (@attr_id, @perf_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_performera set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdPerformer = @perf_id ;
					else
					insert into Wartosc_atrybutu_performera (IdAtrybut, IdPerformer, Wartosc_tekst) values (@attr_id, @perf_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go


alter procedure set_trial_attribute (@trial_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	


	/*
	Current static non-id attributes:
	'TrialDescription', 'string', 0, 'longString'

	*/

	set @result = 6; -- result 6 = type casting error

	if(@attr_name = 'TrialDescription')
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'TrialDescription')
			update Obserwacja set Opis_obserwacji  = @attr_value where IdObserwacja = @trial_id;

		set @result = 0;
		return;
	end;

	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja = 'trial';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Obserwacja where IdObserwacja = @trial_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_obserwacji where IdAtrybut = @attr_id and IdObserwacja = @trial_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_obserwacji set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdObserwacja = @trial_id ;
					else
					insert into Wartosc_atrybutu_obserwacji (IdAtrybut, IdObserwacja, Wartosc_liczba) values (@attr_id, @trial_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_obserwacji set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdObserwacja = @trial_id ;
					else
					insert into Wartosc_atrybutu_obserwacji (IdAtrybut, IdObserwacja, Wartosc_zmiennoprzecinkowa) values (@attr_id, @trial_id, @float_value);
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
					update Wartosc_atrybutu_obserwacji set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdObserwacja = @trial_id ;
					else
					insert into Wartosc_atrybutu_obserwacji (IdAtrybut, IdObserwacja, Wartosc_Id) values (@attr_id, @trial_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_obserwacji set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdObserwacja = @trial_id ;
					else
					insert into Wartosc_atrybutu_obserwacji (IdAtrybut, IdObserwacja, Wartosc_tekst) values (@attr_id, @trial_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
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
			update Konfiguracja_pomiarowa set Opis  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfDescription')
			update Konfiguracja_pomiarowa set Rodzaj  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
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

alter procedure set_measurement_attribute (@meas_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	

	*/

	set @result = 6; -- result 6 = type casting error
	/*
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
			update Konfiguracja_pomiarowa set Opis  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfDescription')
			update Konfiguracja_pomiarowa set Rodzaj  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		set @result = 0;
		return;
	end;
	*/
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'measurement_conf';
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


create procedure set_performer_conf_attribute (@pc_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	

	*/

	set @result = 6; -- result 6 = type casting error
	/*
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
			update Konfiguracja_pomiarowa set Opis  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfDescription')
			update Konfiguracja_pomiarowa set Rodzaj  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		set @result = 0;
		return;
	end;
	*/
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'performer_conf';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Konfiguracja_performera where IdKonfiguracja_performera = @pc_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_konfiguracji_performera where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_liczba) values (@attr_id, @pc_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_zmiennoprzecinkowa) values (@attr_id, @pc_id, @float_value);
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
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_Id) values (@attr_id, @pc_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_tekst) values (@attr_id, @pc_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go

alter procedure clear_attribute_value(@attr_name varchar(100), @entity varchar(20), @res_id int, @result int OUTPUT  )
as
begin
	set @result = 0;
	if(@entity = 'performer') 
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_performera wap on a.IdAtrybut = wap.IdAtrybut
				where a.Nazwa = @attr_name and wap.IdPerformer = @res_id )
		begin
			set @result = 1;
			return;
		end;	
	delete from wa from Wartosc_atrybutu_performera wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdPerformer = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'session')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_sesji was on a.IdAtrybut = was.IdAtrybut
				where a.Nazwa = @attr_name and was.IdSesja = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_sesji wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdSesja = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'trial')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut = wao.IdAtrybut
				where a.Nazwa = @attr_name and wao.IdObserwacja = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_obserwacji wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdObserwacja = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'file')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_pliku wapl on a.IdAtrybut = wapl.IdAtrybut
				where a.Nazwa = @attr_name and wapl.IdPlik = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_pliku wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdPlik = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'measurement_conf')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut = wakp.IdAtrybut
				where a.Nazwa = @attr_name and wakp.IdKonfiguracja_pomiarowa = @res_id )
		begin
			set @result = 1;
			return;
		end;
		delete from wa from Wartosc_atrybutu_konfiguracji_pomiarowej wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdKonfiguracja_pomiarowa = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'performer_conf')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_konfiguracji_performera wakp on a.IdAtrybut = wakp.IdAtrybut
				where a.Nazwa = @attr_name and wakp.IdKonfiguracja_performera = @res_id )
		begin
			set @result = 1;
			return;
		end;
		delete from wa from Wartosc_atrybutu_konfiguracji_performera wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdKonfiguracja_performera= @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'measurement')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut = wap.IdAtrybut
				where a.Nazwa = @attr_name and wap.IdPomiar = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_pomiaru wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdPomiar = @res_id and a.Nazwa = @attr_name;
	end;
end
go

create procedure list_performer_conf_attr_files_xml(@user_login varchar(30),  @pc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_konfiguracji_performera wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut
	where wakp.IdKonfiguracja_performera=@pc_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_performer_conf_attr_files_attributes_xml(@user_login varchar(30),  @pc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_konfiguracji_performera wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut 
	where wakp.IdKonfiguracja_performera=@pc_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go