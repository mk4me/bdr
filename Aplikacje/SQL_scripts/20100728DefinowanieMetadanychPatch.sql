use Motion;
go


alter table Atrybut drop column pluginDescriptor;
go
alter table Atrybut add Plugin varchar(100) NULL;
go

alter table Plik drop column Czas_poczatku;
go
alter table Plik drop column Czas_konca;
go



create procedure clear_attribute_value(@attr_name varchar(100), @entity varchar(20), @res_id int, @result int OUTPUT  )
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
	else if (@entity = 'segment')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_segmentu wase on a.IdAtrybut = wase.IdAtrybut
				where a.Nazwa = @attr_name and wase.IdSegment = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_segmentu wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdSegment = @res_id and a.Nazwa = @attr_name;
	end;
end
go
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
  if @entity not in ( 'performer', 'session', 'trial', 'file', 'segment')
  	begin
		set @result = 2;
		return;
	end;
  insert into Grupa_atrybutow ( Nazwa, Opisywana_encja) values ( @group_name, @entity );
end;
go

declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Atrybut' and OBJECT_NAME(constid) like '%FK_%')
BEGIN
select @sql = 'alter table Atrybut drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Atrybut' and OBJECT_NAME(constid) like '%FK_%';
exec    sp_executesql @sql;
END;
go
alter table Atrybut add constraint FK_IdGrupa_atrybutow foreign key (IdGrupa_atrybutow) references Grupa_atrybutow(IdGrupa_atrybutow) on delete cascade;
go
declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_performera' and OBJECT_NAME(constid) like '%FK_%')
	BEGIN
		select @sql = 'alter table Wartosc_atrybutu_performera drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_performera' and OBJECT_NAME(constid) like '%FK_%';
		exec    sp_executesql @sql;
	END;
go
alter table Wartosc_atrybutu_performera add constraint FK_WAP_IdAtrybut foreign key (IdAtrybut) references Atrybut(IdAtrybut) on delete cascade;
go
alter table Wartosc_atrybutu_performera add constraint FK_WAP_IdPerformer foreign key (IdPerformer) references Performer(IdPerformer) on delete cascade;
go
declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_sesji' and OBJECT_NAME(constid) like '%FK_%')
	BEGIN
		select @sql = 'alter table Wartosc_atrybutu_sesji drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_sesji' and OBJECT_NAME(constid) like '%FK_%';
		exec    sp_executesql @sql;
	END;
go
alter table Wartosc_atrybutu_sesji add constraint FK_WAS_IdAtrybut foreign key (IdAtrybut) references Atrybut(IdAtrybut) on delete cascade;
go
alter table Wartosc_atrybutu_sesji add constraint FK_WAS_IdSesja foreign key (IdSesja) references Sesja(IdSesja) on delete cascade;
go
declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_obserwacji' and OBJECT_NAME(constid) like '%FK_%')
	BEGIN
		select @sql = 'alter table Wartosc_atrybutu_obserwacji drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_obserwacji' and OBJECT_NAME(constid) like '%FK_%';
		exec    sp_executesql @sql;
	END;
go
alter table Wartosc_atrybutu_obserwacji add constraint FK_WAO_IdAtrybut foreign key (IdAtrybut) references Atrybut(IdAtrybut) on delete cascade;
go
alter table Wartosc_atrybutu_obserwacji add constraint FK_WAO_IdObserwacja foreign key (IdObserwacja) references Obserwacja(IdObserwacja) on delete cascade;
go
declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_segmentu' and OBJECT_NAME(constid) like '%FK_%')
	BEGIN
		select @sql = 'alter table Wartosc_atrybutu_segmentu drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_segmentu' and OBJECT_NAME(constid) like '%FK_%';
		exec    sp_executesql @sql;
	END;
go
alter table Wartosc_atrybutu_segmentu add constraint FK_WASE_IdAtrybut foreign key (IdAtrybut) references Atrybut(IdAtrybut) on delete cascade;
go
alter table Wartosc_atrybutu_segmentu add constraint FK_WASE_IdSegment foreign key (IdSegment) references Segment(IdSegment) on delete cascade;
go
declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_wyliczeniowa' and OBJECT_NAME(constid) like '%FK_%')
	BEGIN
		select @sql = 'alter table Wartosc_wyliczeniowa drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_wyliczeniowa' and OBJECT_NAME(constid) like '%FK_%';
		exec    sp_executesql @sql;
	END;
go
alter table Wartosc_wyliczeniowa add constraint FK_WW_IdAtrybut foreign key (IdAtrybut) references Atrybut(IdAtrybut) on delete cascade;
go



declare @sql nvarchar(200);
WHILE EXISTS(select * FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_pliku' and OBJECT_NAME(constid) like '%FK_%')
	BEGIN
		select @sql = 'alter table Wartosc_atrybutu_pliku drop constraint ' + OBJECT_NAME(constid) FROM sysconstraints where OBJECT_NAME(id) = 'Wartosc_atrybutu_pliku' and OBJECT_NAME(constid) like '%FK_%';
		exec    sp_executesql @sql;
	END;
go
alter table Wartosc_atrybutu_pliku add constraint FK_WAPL_IdAtrybut foreign key (IdAtrybut) references Atrybut(IdAtrybut) on delete cascade;
go
alter table Wartosc_atrybutu_pliku add constraint FK_WAPL_IdPlik foreign key (IdPlik) references Plik(IdPlik) on delete cascade;
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
