use Motion;
go

-- Dodanie procedur przeoczonych przy kompletowaniu skryptu dla release 1.3

-- Metadata definition procedures

create procedure define_attribute_group (@group_name varchar(100), @entity varchar(20), @result int OUTPUT )
as
begin
	set @result = 0;
  if exists ( select * from Grupa_atrybutow where Nazwa = @group_name and Opisywana_encja = @entity )
	begin
		set @result = 1;
		return;
	end;
  if @entity not in ( 'performer', 'session', 'trial', 'measurement', 'measurement_conf', 'performer_conf')
  	begin
		set @result = 2;
		return;
	end;
  insert into Grupa_atrybutow ( Nazwa, Opisywana_encja) values ( @group_name, @entity );
end;
go

-- remove attribute group ( name, entity )
create procedure remove_attribute_group (@group_name varchar(100), @entity varchar(20), @result int OUTPUT )
as
begin
	set @result = 0;
	if not exists ( select * from Grupa_atrybutow where Nazwa = @group_name and Opisywana_encja = @entity )
	begin
		set @result = 1;
		return;
	end;
	delete from Grupa_atrybutow where Nazwa = @group_name and Opisywana_encja = @entity;
end;
go

-- define attribute ( group, entity, name, type, subtype, units ) / GROUP !!!!
create procedure define_attribute (@attr_name varchar(100), @group_name varchar(100), @entity varchar(20), @storage_type varchar(20), @is_enum bit, @plugin_desc varchar(100), @data_subtype varchar(20), @unit varchar(10), @result int OUTPUT )
as
begin
	declare @group_id int;
	set @result = 0;
	
	if exists ( select * from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
				where a.Nazwa = @attr_name and ga.Nazwa = @group_name and ga.Opisywana_encja = @entity )
	begin
		set @result = 1;
		return;
	end;
	select @group_id = IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = @group_name and Opisywana_encja = @entity;
	if ( @group_id is null )
	begin
		set @result = 2;
		return;
	end;
	if not (@storage_type in ('string', 'float', 'integer') )
		begin
		set @result = 3;
		return;
	end;
	insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Plugin, Podtyp_danych, Jednostka)
				values ( @group_id, @attr_name, @storage_type, @is_enum, @plugin_desc, @data_subtype, @unit );
	
end
go

-- remove attribute ( group, entity, name )
create procedure remove_attribute (@attr_name varchar(100), @group_name varchar(100), @entity varchar(20), @result int OUTPUT  )
as
begin
	declare @att_id int;
	set @att_id = 0;
	set @result = 0;
	
	select @att_id = a.IdAtrybut from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
				where a.Nazwa = @attr_name and ga.Nazwa = @group_name and ga.Opisywana_encja = @entity;
	if @att_id is null -- attribute not found
	begin
		set @result = 1;
		return;
	end;
	
	delete from Atrybut where IdAtrybut = @att_id;	
end
go

-- Define enum values
create procedure add_attribute_enum_value (@attr_name varchar(100), @group_name varchar(100), @entity varchar(20), @value varchar(100), @replace_all bit, @result int OUTPUT  )
as
begin
	declare @att_id int;
	set @att_id = 0;
	set @result = 0;
	
	select @att_id = a.IdAtrybut from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
				where a.Nazwa = @attr_name and ga.Nazwa = @group_name and ga.Opisywana_encja = @entity
	if @att_id is null -- attribute not found
	begin
		set @result = 1;
		return;
	end;
	if @replace_all = 1 delete from Wartosc_wyliczeniowa where IdAtrybut = @att_id;
	if not exists ( select * from Wartosc_wyliczeniowa where IdAtrybut = @att_id and Wartosc_wyliczeniowa = @value ) and @value <> ''
	insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa ) values ( @att_id, @value );
	
end
go
