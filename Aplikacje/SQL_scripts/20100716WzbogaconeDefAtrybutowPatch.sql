use Motion;
go
alter table Atrybut add Podtyp_danych	varchar(20) NULL ;
go
alter table Atrybut add Jednostka	varchar(10) NULL ;
go

-- When creating attribute definition behavior make sure the attribute name is unique at least with given subject entity
-- e.g. Two Note attributes are allowed, but this is only valid because one of them describes Performer and another describes Session


alter procedure list_attributes_defined( @att_group varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	a.Nazwa as AttributeName, a.Typ_danych as AttributeType, a.Wyliczeniowy as AttributeEnum, ga.Nazwa as AttributeGroupName,
	a.Podtyp_danych as Subtype, a.Jednostka as Unit 
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where @entity_kind=ga.Opisywana_encja and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')
go

create procedure list_attribute_enum_values ( @att_name varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select Wartosc_wyliczeniowa "EnumValue"
from Wartosc_wyliczeniowa ww join Atrybut a on ww.IdAtrybut = a.IdAtrybut join Grupa_atrybutow ga on ga.IdGrupa_atrybutow = a.IdGrupa_atrybutow
where a.Nazwa = @att_name and ga.Opisywana_encja = @entity_kind
for XML PATH(''), root ('EnumValueList')
go

alter function list_performer_attributes ( @perf_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wap.Wartosc_liczba as SQL_VARIANT )
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
	a.Typ_danych as Type,
	ga.Nazwa as AttributeGroup,
	'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id
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
		when 'integer' then cast ( wap.Wartosc_liczba as SQL_VARIANT )
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
	a.Typ_danych as Type,
	ga.Nazwa as AttributeGroup,
	'performer' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id));
go

alter function list_session_attributes ( @sess_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id
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
	(select 'PerformerID' as Name,
			s.IdPerformer as Value,
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
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id));
go

alter function list_trial_attributes ( @trial_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
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
where wao.IdObserwacja = @trial_id
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
	union
	(select 'Duration' as Name,
			t.Czas_trwania as Value,
			'integer' as Type,
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
where wao.IdObserwacja = @trial_id ));
go

alter function list_segment_attributes ( @segment_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'segment' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_segmentu was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSegment = @segment_id
go


alter function list_segment_attributes_uniform ( @segment_id int )
returns TABLE as
return 
(
 with Sgmnt as
( select * from Segment where IdSegment=@segment_id )
	(
	(select 'SegmentID' as Name,
			segm.IdSegment as Value,
			'integer' as Type,
			'_segment_static' as AttributeGroup,
			'segment' as Entity
	from Sgmnt segm)
	union
	(select 'SegmentName' as Name,
			segm.Nazwa as Value,
			'string' as Type,
			'_segment_static' as AttributeGroup,
			'segment' as Entity
	from Sgmnt segm)
	union
	(select 'StartTime' as Name,
			segm.Czas_poczatku as Value,
			'integer' as Type,
			'_segment_static' as AttributeGroup,
			'segment' as Entity
	from Sgmnt segm)
	union
	(select 'EndTime' as Name,
			segm.Czas_konca as Value,
			'integer' as Type,
			'_segment_static' as AttributeGroup,
			'segment' as Entity
	from Sgmnt segm)
	)
union
(select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'segment' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_segmentu was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSegment = @segment_id));
go

alter function list_file_attributes ( @file_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wap.Wartosc_liczba as SQL_VARIANT )
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'file' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pliku wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPlik = @file_id
go















