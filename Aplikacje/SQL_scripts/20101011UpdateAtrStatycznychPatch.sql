use Motion;
go

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes' and Opisywana_encja='session'), 'SessionName', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes' and Opisywana_encja='session'), 'Tags', 'string', 0, 'shortString', NULL) 
go

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('General_trial_attributes', 'trial');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_trial_attributes' and Opisywana_encja='trial'), 'TrialName', 'string', 0, 'shortString', NULL) 
go

delete from Atrybut where Nazwa = 'Duration'; -- !!! Suspended from production update
go

-- TODO: utworzyæ uzgodnione atrybuty generyczne (w tym Keywords ); zaktualizowaæ je w sampleData1.sql
-- TODO: sprawdzicStoredProcedures2 -> Czas_trwania

alter function list_trial_attributes_uniform ( @trial_id int )  -- !!! Suspended from production update
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
		when 'integer' then cast ( wao.Wartosc_liczba as SQL_VARIANT )
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'trial' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id and a.Typ_danych <> 'file'));
go

alter procedure get_trial_by_id_xml ( @res_id int )  -- !!! Suspended from production update
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdObserwacja as TrialID, 
				IdSesja as SessionID, 
				Opis_obserwacji as TrialDescription, 
				(select * from list_trial_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Obserwacja TrialDetailsWithAttributes where IdObserwacja=@res_id
			for XML AUTO, ELEMENTS
go

alter procedure list_session_trials_xml(@user_login varchar(30),  @sess_id int )  -- !!! Suspended from production update
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdObserwacja as TrialID,
	IdSesja as SessionID,
	Opis_obserwacji as TrialDescription
from Obserwacja TrialDetails where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
      for XML AUTO, root ('SessionTrialList')
go

alter table Obserwacja drop column Czas_trwania;
go

alter procedure list_session_trials_attributes_xml(@user_login varchar(30),   @sess_id int)  -- !!! Suspended from production update
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Opis_obserwacji as TrialDescription, 
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
    for XML AUTO, ELEMENTS, root ('SessionTrialWithAttributesList')
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

	set @result = 6; -- result 6 = type casting error

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

alter procedure set_file_attribute (@file_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
		'FileName', 'string', 0, 'shortString'
		'FileDescription', 'string', 0, 'longString'
		'SubdirPath', 'string', 0, 'shortString'
	*/

	set @result = 6; -- result 3 = type casting error

	if(@attr_name = 'FileName' or @attr_name = 'FileDescription' or @attr_name = 'SubdirPath' )
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'FileName')
			update Plik set Nazwa_pliku  = @attr_value where IdPlik = @file_id;
		else if(@attr_name = 'Sciezka')
			update Plik set Nazwa_pliku  =  @attr_value where IdPlik = @file_id;
		else if(@attr_name = 'SubdirPath')
			update Plik set Opis_pliku  = @attr_value where IdPlik = @file_id;
		set @result = 0;
		return;
	end;	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'file';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Plik where IdPlik = @file_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_pliku where IdAtrybut = @attr_id and IdPlik = @file_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_pliku set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdPlik = @file_id ;
					else
					insert into Wartosc_atrybutu_pliku (IdAtrybut, IdPlik, Wartosc_liczba) values (@attr_id, @file_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pliku set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdPlik = @file_id ;
					else
					insert into Wartosc_atrybutu_pliku (IdAtrybut, IdPlik, Wartosc_zmiennoprzecinkowa) values (@attr_id, @file_id, @float_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pliku set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdPlik = @file_id ;
					else
					insert into Wartosc_atrybutu_pliku (IdAtrybut, IdPlik, Wartosc_tekst) values (@attr_id, @file_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go

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

alter procedure list_basket_trials_attributes_xml(@user_login varchar(30), @basket_name varchar(30))-- !!! Suspended from production update
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Opis_obserwacji as TrialDescription, 
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) 
	and IdObserwacja in 
	(select IdObserwacja from Obserwacja_Koszyk ok join Koszyk k on ok.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketTrialWithAttributesList')
go

-- !!! Suspended from production update
alter procedure evaluate_generic_query(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @meas as bit, @mc as bit, @pc as bit, @sg as bit)
as
begin
	/* Assumed validity constraints of the filter structure:
	
		PredicateID is unique
		NextPredicate has matching PredicateID in other tuple; otherwise should be 0
		For complex subexpression (enclosed in parentheses) the ContextEntity should be 'GROUP'
		For any predicate enclosed in a group it should contain non-zero value of the ParentPredicate field, matching the PredicateID of respective 'GROUP'-type predicate
		Moreover, the first predicate that occurs directly after the opening parenthesis should have its PreviousPredicate set to 0
		For a predicate that follows some other predicate a non-zero value of the PreviousPredicate identifier field is required
		For a predicate that is not the last segment of its subexpression
		(i.e. it has some further predicate behind it (at the same level of nesting: before closing parenthesis or expression end comes)),
		it is required to provide the NextOperator with non-empty and valid value
	*/

	declare @predicatesLeft int;
	
	declare @currentId int;
	declare @currentGroup int;
	declare @previousId int;
	declare @currentContextEntity varchar(20);
	declare @nextOperator varchar(5);
	declare @currentFeatureName varchar(100);
	declare @currentOperator varchar(5);
	declare @currentValue varchar(100);
	declare @currentAggregateFunction varchar(10);
	declare @currentAggregateEntity varchar(20);
	
	declare @aggregateSearchIdentifier varchar(100); 
	declare @aggregateSearchIdentifierNonQualified varchar(100); 
	declare @aggregateEntityTranslated varchar(20); 
	declare @contextEntityTranslated varchar(20);  
	
	declare @user_id int;
	
	set @user_id = dbo.identify_user(@user_login);

	declare @groupClosingRun bit;
	set @groupClosingRun = 0;
	
	declare @fromClause varchar (500);
	set @fromClause = ' from ';
	declare @whereClause varchar (1000);
	set @whereClause = ' where ';
	declare @selectClause varchar (1000);
	set @selectClause = 'with XMLNAMESPACES (DEFAULT ''http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService'') select ';
	declare @leftOperandCode varchar (100);
	declare @sql as nvarchar(2500);

	
	/* Determine the content of the select clause */
	
	if(@sg = 1) set @selectClause = @selectClause + 'sg.Nazwa as SessionGroup ';
	if(@sg = 1) and (@perf=1 or @sess=1 or @trial=1 or @mc=1 or @meas=1) set @selectClause = @selectClause + ', ';
	if(@perf = 1) set @selectClause = @selectClause + 'p.IdPerformer as PerformerID, p.Imie as FirstName,	p.Nazwisko as LastName ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +', ';
	if(@sess = 1) set @selectClause = @selectClause + 's.IdSesja as SessionID, s.IdLaboratorium as LabID, s.IdRodzaj_ruchu as MotionKindID,	s.Data as SessionDate,	s.Opis_sesji as SessionDescription ';
	if(@sess = 1 and (@trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +', ';
	if(@trial = 1) set @selectClause = @selectClause +'t.IdObserwacja as TrialID, t.Opis_obserwacji as TrialDescription ';
	if(@trial = 1 and (@mc=1 or @meas=1)) set @selectClause = @selectClause + ', ';
	if(@mc = 1) set @selectClause = @selectClause + 'c.IdKonfiguracja_pomiarowa as MeasurementConfID,	c.Nazwa as MeasureConfName, c.Opis as MeasureConfDescription ';
	if(@mc = 1 and  @meas=1) set @selectClause = @selectClause + ', ';
	if(@meas = 1) set @selectClause = @selectClause + 'm.IdPomiar as MeasurementID ';

	
	set @selectClause = @selectClause + ' , (select * from ( ';
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	-- TODO: if(@pc=1) set @selectClause = @selectClause + 'select * from list_performer_configuration_attributes (p.IdPerformer) ';
	if(@pc = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	if(@sess = 1) set @selectClause = @selectClause + 'select * from list_session_attributes (s.IdSesja) ';
	if(@sess = 1 and (@trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +'union ';
	if(@trial = 1) set @selectClause = @selectClause +'select * from list_trial_attributes (t.IdObserwacja)  ';
	if(@trial = 1 and ( @mc=1 or @meas=1 )) set @selectClause = @selectClause + 'union ';
	if(@mc = 1) set @selectClause = @selectClause + 'select * from list_measurement_configuration_attributes(c.IdKonfiguracja_pomiarowa) ';
	if(@mc = 1 and @meas=1) set @selectClause = @selectClause + 'union ';
	if(@meas = 1) set @selectClause = @selectClause + 'select * from list_measurement_attributes(m.IdPomiar) ';
	
	set @selectClause = @selectClause + ') Attribute FOR XML AUTO, TYPE ) as Attributes ';
	
	/* Now consider, if needed, aditional joins needed in the from clause due to the conditions included */
	
	
	if( @perf = 0 and exists (select * from @filter where ContextEntity = 'performer' )) set @perf = 1;
	if( @sess = 0 and exists (select * from @filter where ContextEntity = 'session' )) set @sess = 1;
	if( @trial = 0 and exists (select * from @filter where ContextEntity = 'trial' )) set @trial = 1;
	if( @pc = 0 and exists (select * from @filter where ContextEntity = 'performer_conf' )) set @pc = 1;
	if( @sg = 0 and exists (select * from @filter where ContextEntity = 'session_group' )) set @sg = 1;
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	
	if( @meas = 0 and ( exists (select * from @filter where ContextEntity = 'measurement' )) or (@perf=1 and @mc =1)) set @meas = 1;	

	if( @sess = 1 and @perf=1 and @meas=0) set @pc = 1;
	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 or @meas=1) )
		begin
		 set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if(@pc = 1) 
		begin
			if(@sess = 1) set @fromClause = @fromClause + 'join Konfiguracja_performera pc on s.IdSesja = pc.IdSesja ';
			else set @fromClause = @fromClause + 'Konfiguracja_performera pc ';
		end;
	
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and (@mc=1 or @meas=1))
		begin
		 set @fromClause = @fromClause + 'join Pomiar as m on m.IdObserwacja = t.IdObserwacja ';
		 set @meas = 1;
		end;
	if( @trial = 0 and (@meas = 1 or (@perf = 1 and @mc =1))) 
		begin
		 set @fromClause = @fromClause + 'Pomiar as m ';
		 set @meas = 1;
		end;

	if (@meas = 1)
		begin
		if(  @mc=1)  
			set @fromClause = @fromClause + 'join Konfiguracja_pomiarowa as mc on p.IdKonfiguracja_pomiarowa = c.IdKonfiguracja_pomiarowa ';
		if( @perf=1)
			set @fromClause = @fromClause + 'join Pomiar_performer as pp on pp.IdPomiar = pp.IdPomiar join Performer as p on p.IdPerformer = pp.IdPerformer ';	
		end	
	else
		begin	
			if( @perf=1)  
				begin
					if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
					else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
				end
			if( @mc=1)  set @fromClause = @fromClause + 'Konfiguracja_pomiarowa as mc ';	
		end;


	select @predicatesLeft = count(PredicateID) from @filter
	select @predicatesLeft = @predicatesLeft + count(PredicateID) from @filter where ContextEntity = 'GROUP' -- NOWE
	select @currentId = 0;
	set @currentGroup = 0;
	while @predicatesLeft > 0
		begin
			set @predicatesLeft = @predicatesLeft-1;	
			select
				@currentId = PredicateID,
				@currentGroup = ParentPredicate,
				@currentContextEntity = ContextEntity,
				@previousId = PreviousPredicate,
				@nextOperator = NextOperator,
				@currentFeatureName = FeatureName,
				@currentOperator = Operator,
				@currentValue = Value,
				@currentAggregateFunction = AggregateFunction,
				@currentAggregateEntity = AggregateEntity
			from @filter
			where PreviousPredicate = @currentId and ParentPredicate = @currentGroup;	
		if (@currentContextEntity = 'GROUP')
		begin
			if(@groupClosingRun = 0)
			begin
				set @whereClause = @whereClause + '( ';
				set @currentGroup = @currentId;
				set @currentId = 0;
				continue; -- do nastepnej petli - wez poczatkowy element w lancuchu odwiedzonej teraz grupy
			end
			else
			begin
				set @whereClause = @whereClause + ') ';
				set @groupClosingRun = 0;
			end
		end
		else
			begin
			
			set @leftOperandCode = (
			case ( case @currentAggregateEntity  when '' then @currentContextEntity else @currentAggregateEntity end )
	-- TODO: session_group i performer_conf		
			when 'performer' then (
				case @currentFeatureName 
					when 'FirstName' then 'p.Imie'
					when 'LastName' then 'p.Nazwisko'
					when 'PerformerID' then 'p.IdPerformer'
					else '(select top 1 * from perf_attr_value(p.IdPerformer, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'session' then (
				case @currentFeatureName
					when 'SessionID' then 's.IdSesja'
					when 'UserID' then 's.IdUzytkownik'
					when 'LabID' then 's.IdLaboratorium'
					when 'MotionKindID' then 's.IdRodzaj_ruchu'
					when 'PerformerID' then 's.IdPerformer'
					when 'SessionDate' then 's.Data'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdObserwacja'
					when 'TrialDescription' then 't.Opis_obserwacji'
					else '(select top 1 * from trial_attr_value(t.IdObserwacja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'measurement' then (
				case @currentFeatureName
					when 'MeasurementID' then	'm.IdPomiar'
					else '(select top 1 * from measurement_attr_value(m.IdPomiar, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'measurement_conf' then (
				case @currentFeatureName
					when 'MeasurementConfID' then 'c.IdKonfiguracja_pomiarowa'
					when 'MeasurementConfName' then 'c.Nazwa'
					when 'MeasurementConfDescription' then 'c.Opis'
					else '(select top 1 * from measurement_conf_attr_value(c.IdKonfiguracja_pomiarowa, '+quotename(@currentFeatureName,'''')+'))' end				)
			else 'UNKNOWN' end )
			if(@currentAggregateEntity<>'')
			begin
				set @aggregateSearchIdentifier = (
					case @currentContextEntity
					when 'performer' then 'p.IdPerformer'
					when 'session' then 's.IdSesja'
					when 'trial' then 't.IdObserwacja'
					when 'measurement' then 'm.IdPomiar'
					when 'measurement_conf' then 'c.IdKonfiguracja_pomiarowa'
					else 'WRONG CONTEXT ENT.' end
				)
				set @aggregateSearchIdentifierNonQualified = substring(@aggregateSearchIdentifier,3,20);
				set @aggregateEntityTranslated = (
					case @currentAggregateEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Obserwacja'
					when 'measurement' then 'Pomiar'
					when 'measurement_conf' then 'Konfiguracja_pomiarowa'
					else 'WRONG AGGREGATE ENT.' end
				)
				set @contextEntityTranslated = (
					case @currentContextEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Obserwacja'
					when 'measurement_conf' then 'Konfiguracja_pomiarowa'
					else 'WRONG CONTEXT ENT.' end
				)
				--Id<ContextEntity> in ( select Id<ContextEntity> from <AggregateEntity> group by Id<ContextEntity> having <AggregateFunction>(<FeatureName>) <Operator> <Value> )	
				set @whereClause = @whereClause + '( ' + @aggregateSearchIdentifier + ' in ( select '+@aggregateSearchIdentifierNonQualified+' from '+@aggregateEntityTranslated+' group by '+@aggregateSearchIdentifierNonQualified+ ' having '+@currentAggregateFunction+'('+ substring(@leftOperandCode,3,20) +') '+@currentOperator + quotename(@currentValue,'''') + ' ) )';				
			end
			else
			begin
				set @whereClause = @whereClause +  @leftOperandCode + @currentOperator + quotename(@currentValue,'''')
			end;

			end -- of non-GROUP case

			if (@nextOperator = '' )
			begin
			 -- set @whereClause = @whereClause + ') '+@currentFeatureName; -- usuniete w NOWE
			 set @currentId = @currentGroup;
			 set @groupClosingRun = 1; -- NOWE

			 if @currentGroup <> 0 
			 begin 
			 	-- cofamy sie tak, by nastepny przebieg chwycil grupe zawierajaca wlasnie zakonczony lancuch:
			    -- To wyselekcjonuje grupe zawierajaca
				select @currentGroup = ParentPredicate from @filter where PredicateID=@currentId;
				-- zas to: parametry, jej poprzednika tak, aby to wlasnie ta grupa byla odwiedzona w nastepnym przebiegu  
				select @currentId = PreviousPredicate from @filter where PredicateID=@currentId;		
				-- cofamy sie tak, by nastepny przebieg chwycil grupe zawierajaca wlasnie zakonczony lancuch:
			 end
			end
		 

		 if (@nextOperator <> '' ) set @whereClause = @whereClause + ' '+ @nextOperator+' ';

	
	/* TD: Add compile directive */				
		end -- of the predictate loop
		set @whereClause = @whereClause+' for XML RAW (''GenericResultRow''), ELEMENTS, root (''GenericQueryResult'')';
		set @sql = N''+(@selectClause+@fromClause+@whereClause);
		-- set @sql = N'BEGIN TRY ' + (@selectClause+@fromClause+@whereClause) + ' END TRY BEGIN CATCH insert into Blad ( NrBledu, Dotkliwosc, Stan, Procedura, Linia, Komunikat ) values ( ERROR_NUMBER() , ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE() ) END CATCH;'

		-- select @sql;
		exec sp_executesql @statement = @sql;
		
end
go

-- !!! Suspended from production update
alter procedure evaluate_generic_query_uniform(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @mc as bit, @meas as bit, @pc as bit, @sg as bit)
as
begin
	/* Assumed validity constraints of the filter structure:
	
		PredicateID is unique
		NextPredicate has matching PredicateID in other tuple; otherwise should be 0
		For complex subexpression (enclosed in parentheses) the ContextEntity should be 'GROUP'
		For any predicate enclosed in a group it should contain non-zero value of the ParentPredicate field, matching the PredicateID of respective 'GROUP'-type predicate
		Moreover, the first predicate that occurs directly after the opening parenthesis should have its PreviousPredicate set to 0
		For a predicate that follows some other predicate a non-zero value of the PreviousPredicate identifier field is required
		For a predicate that is not the last segment of its subexpression
		(i.e. it has some further predicate behind it (at the same level of nesting: before closing parenthesis or expression end comes)),
		it is required to provide the NextOperator with non-empty and valid value
	*/

	declare @predicatesLeft int;
	
	declare @currentId int;
	declare @currentGroup int;
	declare @previousId int;
	declare @currentContextEntity varchar(20);
	declare @nextOperator varchar(5);
	declare @currentFeatureName varchar(100);
	declare @currentOperator varchar(5);
	declare @currentValue varchar(100);
	declare @currentAggregateFunction varchar(10);
	declare @currentAggregateEntity varchar(20);
	declare @groupClosingRun bit;
	set @groupClosingRun = 0;
	
	declare @aggregateSearchIdentifier varchar(100); 
	declare @aggregateSearchIdentifierNonQualified varchar(100); 
	declare @aggregateEntityTranslated varchar(20); 
	declare @contextEntityTranslated varchar(20); 
	
	declare @fromClause varchar (500);
	set @fromClause = ' from ';
	declare @whereClause varchar (1000);
	set @whereClause = ' where ';
	declare @selectClause varchar (1000);
	set @selectClause = 'with XMLNAMESPACES (DEFAULT ''http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService'') select ';
	declare @leftOperandCode varchar (100);
	declare @sql as nvarchar(2500);
	
	declare @user_id int;
	
	set @user_id = dbo.identify_user(@user_login);
	
/*	
	insert into 
	PredykatTest (PredicateID, ParentPredicate, ContextEntity, PreviousPredicate,	NextOperator,FeatureName, Operator,	Value, AggregateFunction, AggregateEntity) 
	( select PredicateID, ParentPredicate, ContextEntity, PreviousPredicate,	NextOperator,FeatureName, Operator,	Value, AggregateFunction, AggregateEntity from @filter );
*/
	
	/* Determine the content of the select clause */
	
	
	set @selectClause = @selectClause + ' (select * from ( ';
-- TODO:		if(@sg = 1) set @selectClause = @selectClause + 'sg.Nazwa as SessionGroup ';
	-- TODO: if(@sg = 1) and (@perf=1 or @sess=1 or @trial=1 or @mc=1 or @meas=1) set @selectClause = @selectClause + ', ';
	if(@perf = 1) set @selectClause = @selectClause + 'select * from list_performer_attributes_uniform (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	-- TODO: if(@pc = 1) set @selectClause = @selectClause + 'select * from list_performer_conf_attributes_uniform (p.IdPerformer) ';
	if(@sess = 1) set @selectClause = @selectClause + 'select * from list_session_attributes_uniform (s.IdSesja) ';
	if(@sess = 1 and (@trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +'union ';
	if(@trial = 1) set @selectClause = @selectClause +'select * from list_trial_attributes_uniform (t.IdObserwacja)  ';
	if(@trial = 1 and ( @mc=1 or @meas=1 )) set @selectClause = @selectClause + 'union ';
	if(@mc = 1) set @selectClause = @selectClause + 'select * from list_measurement_configuration_attributes_uniform(c.IdKonfiguracja_pomiarowa) ';
	if(@mc = 1 and @meas=1) set @selectClause = @selectClause + 'union ';
	if(@meas = 1) set @selectClause = @selectClause + 'select * from list_measurement_attributes_uniform(m.IdPomiar) ';

	set @selectClause = @selectClause + ') Attribute FOR XML AUTO, TYPE ) ';
	
	/* Now consider, if needed, aditional joins needed in the from clause due to the conditions included */
	
	
	if( @perf = 0 and exists (select * from @filter where ContextEntity = 'performer' )) set @perf = 1;
	if( @sess = 0 and exists (select * from @filter where ContextEntity = 'session' )) set @sess = 1;
	if( @trial = 0 and exists (select * from @filter where ContextEntity = 'trial' )) set @trial = 1;
	if( @pc = 0 and exists (select * from @filter where ContextEntity = 'performer_conf' )) set @pc = 1;
	if( @sg = 0 and exists (select * from @filter where ContextEntity = 'session_group' )) set @sg = 1;
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	
	if( @meas = 0 and ( exists (select * from @filter where ContextEntity = 'measurement' )) or (@perf=1 and @mc =1)) set @meas = 1;	

	if( @sess = 1 and @perf=1 and @meas=0) set @pc = 1;
	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 or @meas=1) )
		begin
		 set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if(@pc = 1) 
		begin
			if(@sess = 1) set @fromClause = @fromClause + 'join Konfiguracja_performera pc on s.IdSesja = pc.IdSesja ';
			else set @fromClause = @fromClause + 'Konfiguracja_performera pc ';
		end;
	
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and (@mc=1 or @meas=1))
		begin
		 set @fromClause = @fromClause + 'join Pomiar as m on m.IdObserwacja = t.IdObserwacja ';
		 set @meas = 1;
		end;
	if( @trial = 0 and (@meas = 1 or (@perf = 1 and @mc =1))) 
		begin
		 set @fromClause = @fromClause + 'Pomiar as m ';
		 set @meas = 1;
		end;

	if (@meas = 1)
		begin
		if(  @mc=1)  
			set @fromClause = @fromClause + 'join Konfiguracja_pomiarowa as mc on p.IdKonfiguracja_pomiarowa = c.IdKonfiguracja_pomiarowa ';
		if( @perf=1)
			set @fromClause = @fromClause + 'join Pomiar_performer as pp on pp.IdPomiar = pp.IdPomiar join Performer as p on p.IdPerformer = pp.IdPerformer ';	
		end	
	else
		begin	
			if( @perf=1)  
				begin
					if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
					else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
				end
			if( @mc=1)  set @fromClause = @fromClause + 'Konfiguracja_pomiarowa as mc ';	
		end;



	select @predicatesLeft = count(PredicateID) from @filter
	select @predicatesLeft = @predicatesLeft + count(PredicateID) from @filter where ContextEntity = 'GROUP' -- NOWE
	select @currentId = 0;
	set @currentGroup = 0;
	while @predicatesLeft > 0
		begin
			set @predicatesLeft = @predicatesLeft-1;	
			select
				@currentId = PredicateID,
				@currentGroup = ParentPredicate,
				@currentContextEntity = ContextEntity,
				@previousId = PreviousPredicate,
				@nextOperator = NextOperator,
				@currentFeatureName = FeatureName,
				@currentOperator = Operator,
				@currentValue = Value,
				@currentAggregateFunction = AggregateFunction,
				@currentAggregateEntity = AggregateEntity
			from @filter
			where PreviousPredicate = @currentId and ParentPredicate = @currentGroup;	
		if (@currentContextEntity = 'GROUP')
		begin
			if(@groupClosingRun = 0)
			begin
				set @whereClause = @whereClause + '( ';
				set @currentGroup = @currentId;
				set @currentId = 0;
				continue; -- do nastepnej petli - wez poczatkowy element w lancuchu odwiedzonej teraz grupy
			end
			else
			begin
				set @whereClause = @whereClause + ') ';
				set @groupClosingRun = 0;
			end
		end
		else
			begin
			
			set @leftOperandCode = (
			case ( case @currentAggregateEntity  when '' then @currentContextEntity else @currentAggregateEntity end )
-- TODO: performer_conf i session_group
			when 'performer' then (
				case @currentFeatureName 
					when 'FirstName' then 'p.Imie'
					when 'LastName' then 'p.Nazwisko'
					when 'PerformerID' then 'p.IdPerformer'
					else '(select top 1 * from perf_attr_value(p.IdPerformer, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'session' then (
				case @currentFeatureName
					when 'SessionID' then 's.IdSesja'
					when 'UserID' then 's.IdUzytkownik'
					when 'LabID' then 's.IdLaboratorium'
					when 'MotionKindID' then 's.IdRodzaj_ruchu'
					when 'PerformerID' then 's.IdPerformer'
					when 'SessionDate' then 's.Data'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdObserwacja'
					when 'TrialDescription' then 't.Opis_obserwacji'
					else '(select top 1 * from trial_attr_value(t.IdObserwacja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'measurement' then (
				case @currentFeatureName
					when 'MeasurementID' then	'm.IdPomiar'
					else '(select top 1 * from measurement_attr_value(m.IdPomiar, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'measurement_conf' then (
				case @currentFeatureName
					when 'MeasurementConfID' then 'c.IdKonfiguracja_pomiarowa'
					when 'MeasurementConfName' then 'c.Nazwa'
					when 'MeasurementConfDescription' then 'c.Opis'
					else '(select top 1 * from measurement_conf_attr_value(c.IdKonfiguracja_pomiarowa, '+quotename(@currentFeatureName,'''')+'))' end				)
			else 'UNKNOWN' end )
			if(@currentAggregateEntity<>'')
			begin
				set @aggregateSearchIdentifier = (
					case @currentContextEntity
					when 'performer' then 'p.IdPerformer'
					when 'session' then 's.IdSesja'
					when 'trial' then 't.IdObserwacja'
					when 'measurement' then 'm.IdPomiar'
					when 'measurement_conf' then 'c.IdKonfiguracja_pomiarowa'
					else 'WRONG CONTEXT ENT.' end
				)
				set @aggregateSearchIdentifierNonQualified = substring(@aggregateSearchIdentifier,3,20);
				set @aggregateEntityTranslated = (
					case @currentAggregateEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Obserwacja'
					when 'measurement' then 'Pomiar'
					when 'measurement_conf' then 'Konfiguracja_pomiarowa'
					else 'WRONG AGGREGATE ENT.' end
				)
				set @contextEntityTranslated = (
					case @currentContextEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Obserwacja'
					when 'measurement_conf' then 'Konfiguracja_pomiarowa'
					else 'WRONG CONTEXT ENT.' end
				)
				--Id<ContextEntity> in ( select Id<ContextEntity> from <AggregateEntity> group by Id<ContextEntity> having <AggregateFunction>(<FeatureName>) <Operator> <Value> )	
				set @whereClause = @whereClause + '( ' + @aggregateSearchIdentifier + ' in ( select '+@aggregateSearchIdentifierNonQualified+' from '+@aggregateEntityTranslated+' group by '+@aggregateSearchIdentifierNonQualified+ ' having '+@currentAggregateFunction+'('+ substring(@leftOperandCode,3,20) +') '+@currentOperator + quotename(@currentValue,'''') + ' ) )';				
			end
			else
			begin
				set @whereClause = @whereClause +  @leftOperandCode + @currentOperator + quotename(@currentValue,'''')
			end;

			end -- of non-GROUP case
			if (@nextOperator = '' )
			begin
			 -- set @whereClause = @whereClause + ') '+@currentFeatureName; -- usuniete w NOWE
			 set @currentId = @currentGroup;
			 set @groupClosingRun = 1; -- NOWE

			 if @currentGroup <> 0 
			 begin 
			 	-- cofamy sie tak, by nastepny przebieg chwycil grupe zawierajaca wlasnie zakonczony lancuch:
			    -- To wyselekcjonuje grupe zawierajaca
				select @currentGroup = ParentPredicate from @filter where PredicateID=@currentId;
				-- zas to: parametry, jej poprzednika tak, aby to wlasnie ta grupa byla odwiedzona w nastepnym przebiegu  
				select @currentId = PreviousPredicate from @filter where PredicateID=@currentId;		
				-- cofamy sie tak, by nastepny przebieg chwycil grupe zawierajaca wlasnie zakonczony lancuch:
			 end
			end
		 

		 if (@nextOperator <> '' ) set @whereClause = @whereClause + ' '+ @nextOperator+' ';

	
	/* TD: Add compile directive */				
		end -- of the predictate loop
		set @whereClause = @whereClause+' for XML RAW (''Attributes''), ELEMENTS, root (''GenericUniformAttributesQueryResult'')';

		set @sql = N''+(@selectClause+@fromClause+@whereClause);
		-- set @sql = N'BEGIN TRY ' + (@selectClause+@fromClause+@whereClause) + ' END TRY BEGIN CATCH insert into Blad ( NrBledu, Dotkliwosc, Stan, Procedura, Linia, Komunikat ) values ( ERROR_NUMBER() , ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE() ) END CATCH;'

		-- select @sql;
		exec sp_executesql @statement = @sql;
		
end
go

