use Motion;
go


-- last rev. 2011-11-03
alter procedure get_metadata @user_login varchar(30)
as
with
SG as (select * from Grupa_sesji SessionGroup ),
MK as (select * from Rodzaj_ruchu MotionKind),
LB as (select * from Laboratorium Lab),
AG as (select * from Grupa_atrybutow AttributeGroup )
select
(select 
	IdGrupa_sesji as SessionGroupID,
	Nazwa as SessionGroupName
	from SG SessionGroup for XML AUTO, TYPE
 ) SessionGroups,
 (select 
	IdRodzaj_ruchu as MotionKindID,
	Nazwa as MotionKindName
	from MK MotionKind for XML AUTO, TYPE
 ) MotionKinds,
 (select 
	IdLaboratorium as LabID,
	Nazwa as LabName
	from LB Lab for XML AUTO, TYPE
 ) Labs,
 (select 
	IdGrupa_atrybutow as AttributeGroupID,
	Nazwa as AttributeGroupName,
	Opisywana_encja as DescribedEntity,
	(select 
		Nazwa as AttributeName,
		Podtyp_danych as AttributeType,
		Jednostka as Unit,
		(select
			Wartosc_wyliczeniowa EnumValue
		 from Wartosc_wyliczeniowa Enumeration where Enumeration.IdAtrybut = Attribute.IdAtrybut for XML AUTO, TYPE ) EnumValues
		from Atrybut Attribute where Attribute.IdGrupa_atrybutow = AttributeGroup.IdGrupa_atrybutow FOR XML AUTO, TYPE 
	) Attributes
	from AG AttributeGroup FOR XML AUTO, TYPE 
 ) AttributeGroups
 for XML RAW ('Metadata'), TYPE;
go
