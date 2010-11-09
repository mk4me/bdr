use Motion;
go

alter procedure define_attribute (@attr_name varchar(100), @group_name varchar(100), @entity varchar(20), @is_enum bit, @plugin_desc varchar(100), @type varchar(20), @unit varchar(10), @result int OUTPUT )
as
begin
	declare @group_id int;
	declare @storage_type varchar(20);
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
	if @type = 'float'
		set @storage_type = 'float';
	else if @type in ('int', 'decimal', 'nonNegativeInteger', 'nonNegativeDecimal') set @storage_type = 'integer';
	else if @type in ( 'shortString', 'longString', 'dateTime', 'date', 'TIMECODE' ) set @storage_type = 'string';
	else if @type = 'file' set @storage_type = 'file';
	else
		begin
			set @result = 3;
			return;
		end;
	insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Plugin, Podtyp_danych, Jednostka)
				values ( @group_id, @attr_name, @storage_type, @is_enum, @plugin_desc, @type, @unit );
	
end
go

alter procedure list_attributes_defined_with_enums( @user_login varchar(30), @att_group varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	a.Nazwa as AttributeName, a.Podtyp_danych as AttributeType, a.Wyliczeniowy as AttributeEnum, 
	ga.Nazwa as AttributeGroupName, a.Jednostka as Unit, 
	(select wa.Wyswietlic from Widocznosc_atrybutu wa where wa.IdUzytkownik = dbo.identify_user(@user_login) and wa.IdAtrybut = a.IdAtrybut) as Show, 
	(select Wartosc_wyliczeniowa as Value from Wartosc_wyliczeniowa ww where ww.IdAtrybut = a.IdAtrybut for XML RAW(''), TYPE, ELEMENTS) as EnumValues
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where (@entity_kind=ga.Opisywana_encja or @entity_kind = '_ALL') and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')
go

alter procedure list_attribute_groups_defined(@user_login varchar(30), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	ga.Nazwa as AttributeGroupName, ga.Opisywana_encja as DescribedEntity,
	(select wga.Wyswietlic from Widocznosc_grupy_atrybutow wga where wga.IdGrupa_atrybutow = ga.IdGrupa_atrybutow and wga.IdUzytkownik = dbo.identify_user(@user_login) ) as Show
from Grupa_atrybutow ga 
where (@entity_kind=Opisywana_encja or @entity_kind = '_ALL_ENTITIES' or @entity_kind = '_ALL')
for XML RAW ('AttributeGroupDefinition'), ELEMENTS, root ('AttributeGroupDefinitionList')
go
