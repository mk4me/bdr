use Motion;

update Atrybut set  IdGrupa_atrybutow = (select top 1 IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes') where Nazwa = 'SessionType' 

alter procedure list_attribute_groups_defined( @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	Nazwa as AttributeGroupName, Opisywana_encja as DescribedEntity
from Grupa_atrybutow
where (@entity_kind=Opisywana_encja or @entity_kind = '_ALL_ENTITIES' or @entity_kind = '_ALL')
for XML RAW ('AttributeGroupDefinition'), ELEMENTS, root ('AttributeGroupDefinitionList')
go

create procedure list_attributes_defined_with_enums( @att_group varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	a.Nazwa as AttributeName, a.Typ_danych as AttributeType, a.Wyliczeniowy as AttributeEnum, ga.Nazwa as AttributeGroupName, a.Podtyp_danych as Subtype, a.Jednostka as Unit,
	(select Wartosc_wyliczeniowa as Value from Wartosc_wyliczeniowa ww where ww.IdAtrybut = a.IdAtrybut for XML RAW(''), TYPE, ELEMENTS) "EnumValues"
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where (@entity_kind=ga.Opisywana_encja or @entity_kind = '_ALL') and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')
go
