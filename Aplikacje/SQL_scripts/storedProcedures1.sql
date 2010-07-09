use Motion;
go
-- non-XML queries
-- ==================================

create procedure list_performer_sessions @perf_id int
as
	select IdSesja as SessionID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, IdPerformer as PerformerID, Data as SessionDate, 
      Opis_sesji as SessionDescription  from Sesja where IdPerformer=@perf_id
go

create procedure list_session_files @sess_id int
as
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription  from Plik where IdSesja=@sess_id
go

-- Generic By-ID retrieval
-- =======================

create procedure get_performer_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdPerformer as PerformerID,
				Imie as FirstName,
				Nazwisko as LastName,
				(select * from list_performer_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE) as Attributes
			from Performer PerformerDetailsWithAttributes where IdPerformer = @res_id
			for XML AUTO, ELEMENTS
go

create procedure get_session_by_id_xml ( @user_login as varchar(30), @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdSesja as SessionID,
				IdUzytkownik as UserID,
				IdLaboratorium as LabID,
				IdRodzaj_ruchu as MotionKindID,
				IdPerformer as PerformerID,
				Data as SessionDate,
				Opis_sesji as SessionDescription,
				(select * from list_session_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdSesja=@res_id
			for XML AUTO, ELEMENTS
go
create procedure get_trial_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdObserwacja as TrialID, 
				IdSesja as SessionID, 
				Opis_obserwacji as TrialDescription, 
				Czas_trwania as Duration,
				(select * from list_trial_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Obserwacja TrialDetailsWithAttributes where IdObserwacja=@res_id
			for XML AUTO, ELEMENTS
go
create procedure get_segment_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdSegment as SegmentID,
				IdObserwacja as TrialID,
				Nazwa as SegmentName,
				Czas_poczatku as StartTime,
				Czas_konca as EndTime,
				(select * from list_segment_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Segment SegmentDetailsWithAttributes where IdSegment=@res_id
			for XML AUTO, ELEMENTS
go




-- Performer queries
-- =======================
create function list_performer_attributes ( @perf_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wap.Wartosc_liczba as SQL_VARIANT )
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
	a.Typ_danych as Type,
	ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id
go

create function list_performer_attributes_uniform ( @perf_id int )
returns TABLE as
return 
(
 with Perf as
( select * from Performer where IdPerformer=@perf_id )
	(
	(select 'FirstName' as Name,
			p.Imie as Value,
			'string' as Type,
			'_performer_static' as AttributeGroup
	from Perf p)
	union
	(select 'LastName' as Name,
			p.Nazwisko as Value,
			'string' as Type,
			'_performer_static' as AttributeGroup
	from Perf p)
	union
	(select 'PerformerID' as Name,
			p.IdPerformer as Value,
			'integer' as Type,
			'_performer_static' as AttributeGroup
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
	ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id));
go



create procedure list_performers_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdPerformer as PerformerID, Imie as FirstName, Nazwisko as LastName
	from Performer Performer
    for XML AUTO, root ('PerformerList')
go

create procedure list_performers_attributes_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
    for XML AUTO, ELEMENTS, root ('PerformerWithAttributesList')
go

create procedure list_lab_performers_attributes_xml (@lab_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where exists(select * from Sesja s join Performer p on p.IdPerformer = s.IdPerformer where s.IdLaboratorium = @lab_id)
    for XML AUTO, ELEMENTS, root ('LabPerformerWithAttributesList')
go


-- Session queries
-- =======================

create function session_label( @user_login varchar(30), @sess_id int )
returns TABLE as
return
select p.Nazwisko+','+p.Imie+':'+CONVERT(CHAR(10),s.Data,126) as SessionLabel
from user_accessible_sessions_by_login(@user_login) s inner join Performer p on s.IdPerformer = p.IdPerformer where s.IdSesja = @sess_id
go	




create function list_session_attributes ( @sess_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id
go


create function list_session_attributes_uniform ( @sess_id int )
returns TABLE as
return 
(
 with Sess as
( select * from Sesja where IdSesja=@sess_id )
	(
	(select 'SessionID' as Name,
			s.IdSesja as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup
	from Sess s)
	union
	(select 'UserID' as Name,
			s.IdUzytkownik as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup
	from Sess s)
	union
	(select 'LabID' as Name,
			s.IdLaboratorium as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup
	from Sess s)
	union
	(select 'MotionKindID' as Name,
			s.IdRodzaj_ruchu as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup
	from Sess s)
	union
	(select 'PerformerID' as Name,
			s.IdPerformer as Value,
			'integer' as Type,
			'_session_static' as AttributeGroup
	from Sess s)
	union
	(select 'SessionDate' as Name,
			s.Data as Value,
			'string' as Type,
			'_session_static' as AttributeGroup
	from Sess s)
	union
	(select 'SessionDescription' as Name,
			s.Opis_sesji as Value,
			'string' as Type,
			'_session_static' as AttributeGroup
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
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id));
go

create procedure list_performer_sessions_xml (@user_login varchar(30), @perf_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdSesja as SessionID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, IdPerformer as PerformerID, Data as SessionDate, 
      Opis_sesji as SessionDescription, (select * from session_label(@user_login,IdSesja)) as SessionLabel
      from user_accessible_sessions_by_login(@user_login) SessionDetails where IdPerformer=@perf_id
      for XML AUTO, root ('PerformerSessionList')
go


create procedure list_performer_sessions_attributes_xml (@user_login varchar(30), @perf_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		IdRodzaj_ruchu as MotionKindID,
		IdPerformer as PerformerID,
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Sesja SessionDetailsWithAttributes where IdPerformer=@perf_id
      for XML AUTO, ELEMENTS, root ('PerformerSessionWithAttributesList')
go

create procedure list_lab_sessions_attributes_xml (@user_login varchar(30), @lab_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		IdRodzaj_ruchu as MotionKindID,
		IdPerformer as PerformerID,
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Sesja SessionDetailsWithAttributes where IdLaboratorium=@lab_id
      for XML AUTO, ELEMENTS, root ('LabSessionWithAttributesList')
go

-- Trial queries
-- ===============

create function list_trial_attributes ( @trial_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wao.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wao.Wartosc_liczba as SQL_VARIANT )
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id
go


create function list_trial_attributes_uniform ( @trial_id int )
returns TABLE as
return 
(
 with Trial as
( select * from Obserwacja where IdObserwacja=@trial_id )
	(
	(select 'TrialID' as Name,
			t.IdObserwacja as Value,
			'integer' as Type,
			'_trial_static' as AttributeGroup
	from Trial t)
	union
	(select 'TrialDescription' as Name,
			t.Opis_obserwacji as Value,
			'string' as Type,
			'_trial_static' as AttributeGroup
	from Trial t)
	union
	(select 'Duration' as Name,
			t.Czas_trwania as Value,
			'integer' as Type,
			'_trial_static' as AttributeGroup
	from Trial t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wao.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wao.Wartosc_liczba as SQL_VARIANT )
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id ));
go

create procedure list_session_trials_xml @sess_id int
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdObserwacja as TrialID,
	IdSesja as SessionID,
	Opis_obserwacji as TrialDescription,
	Czas_trwania as Duration
from Obserwacja TrialDetails where IdSesja=@sess_id
      for XML AUTO, root ('SessionTrialList')
go

create procedure list_session_trials_attributes_xml @sess_id int
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Opis_obserwacji as TrialDescription, 
	Czas_trwania as Duration,
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where IdSesja=@sess_id
    for XML AUTO, ELEMENTS, root ('SessionTrialWithAttributesList')
go

-- Segment queries
-- ==================

create function list_segment_attributes ( @segment_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_segmentu was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSegment = @segment_id
go


create function list_segment_attributes_uniform ( @segment_id int )
returns TABLE as
return 
(
 with Sgmnt as
( select * from Segment where IdSegment=@segment_id )
	(
	(select 'SegmentID' as Name,
			segm.IdSegment as Value,
			'integer' as Type,
			'_segment_static' as AttributeGroup
	from Sgmnt segm)
	union
	(select 'SegmentName' as Name,
			segm.Nazwa as Value,
			'string' as Type,
			'_segment_static' as AttributeGroup
	from Sgmnt segm)
	union
	(select 'StartTime' as Name,
			segm.Czas_poczatku as Value,
			'integer' as Type,
			'_segment_static' as AttributeGroup
	from Sgmnt segm)
	union
	(select 'EndTime' as Name,
			segm.Czas_konca as Value,
			'integer' as Type,
			'_segment_static' as AttributeGroup
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
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_segmentu was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSegment = @segment_id));
go

create procedure list_trial_segments_xml @trial_id int
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdSegment as SegmentID,
	IdObserwacja as TrialID,
	Nazwa as SegmentName,
	Czas_poczatku as StartTime,
	Czas_konca as EndTime
from Segment SegmentDetails where IdObserwacja=@trial_id
      for XML AUTO, root ('TrailSegmentList')
go

create procedure list_trial_segments_attributes_xml @trial_id int
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdSegment as SegmentID,
	IdObserwacja as TrialID,
	Nazwa as SegmentName,
	Czas_poczatku as StartTime,
	Czas_konca as EndTime,
	(select * from list_segment_attributes ( IdSegment ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Segment SegmentDetailsWithAttributes where IdObserwacja=@trial_id
    for XML AUTO, ELEMENTS, root ('TrailSegmentWithAttributesList')
go

-- File queries
-- ===================

create function list_file_attributes ( @file_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wap.Wartosc_liczba as SQL_VARIANT )
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup
from Atrybut a 
inner join Wartosc_atrybutu_pliku wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPlik = @file_id
go

create procedure list_performer_files_xml @perf_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription from Plik FileDetails where IdPerformer=@perf_id
	for XML AUTO, root ('FileList')
go

create procedure list_session_files_xml @sess_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription from Plik FileDetails where IdSesja=@sess_id
	for XML AUTO, root ('FileList')
go

create procedure list_trial_files_xml @trial_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription from Plik FileDetails where IdObserwacja=@trial_id
	for XML AUTO, root ('FileList')
go

create procedure list_performer_files_attributes_xml @perf_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
	IdPlik as FileID,
	Nazwa_pliku as FileName,
	Opis_pliku as FileDescription, 
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes where IdPerformer=@perf_id
	for XML AUTO, root ('FileWithAttributesList')
go

create procedure list_session_files_attributes_xml @sess_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
	IdPlik as FileID,
	Nazwa_pliku as FileName,
	Opis_pliku as FileDescription, 
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes where IdSesja=@sess_id
	for XML AUTO, root ('FileWithAttributesList')
go

create procedure list_trial_files_attributes_xml @trial_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		IdPlik as FileID,
		Nazwa_pliku as FileName,
		Opis_pliku as FileDescription,
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes
		where IdObserwacja=@trial_id
	for XML AUTO, root ('FileWithAttributesList')
go




-- Metadata queries
-- ===================

create procedure list_attributes_defined( @att_group varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	a.Nazwa as AttributeName, a.Typ_danych as AttributeType, a.Wyliczeniowy as AttributeEnum, ga.Nazwa as AttributeGroupName
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where @entity_kind=ga.Opisywana_encja and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')

go

create procedure list_attribute_groups_defined( @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	Nazwa as AttributeGroupName, Opisywana_encja as DescribedEntity
from Grupa_atrybutow
where (@entity_kind=Opisywana_encja or @entity_kind = '_ALL_ENTITIES')
for XML RAW ('AttributeGroupDefinition'), ELEMENTS, root ('AttributeGroupDefinitionList')

go


create procedure list_motion_kinds_defined
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdRodzaj_ruchu as MotionKindID, Nazwa as MotionKindName from Rodzaj_ruchu
for XML RAW ('MotionKindDefinition'), ELEMENTS, root ('MotionKindDefinitionList')

go
create procedure list_session_groups_defined
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdGrupa_sesji as SessionGroupID, Nazwa as SessionGroupName from Grupa_sesji
for XML RAW ('SessionGroupDefinition'), ELEMENTS, root ('SessionGroupDefinitionList')
go

exec list_session_groups_defined

-- Attribute setting operations
-- ============================

/*
Output parameter "result" meaning:
0 - attribute value set successfully
1 - attribute of this name is not applicable to session
2 - the value provided is not valid for this enum-type attribute
3 - session of given session id does not exist
4 - (not assigned)
5 - value of this attribute for this session exists, whille you called this operation in "no overwrite" mode
6 - the value provided is not valid for this numeric-type attribute

*/
create procedure set_session_attribute (@sess_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

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
create procedure set_performer_attribute (@perf_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
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


create procedure set_trial_attribute (@trial_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
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

create procedure set_segment_attribute (@segment_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja = 'segment';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Segment where IdSegment = @segment_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_segmentu where IdAtrybut = @attr_id and IdSegment = @segment_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_segmentu set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdSegment = @segment_id ;
					else
					insert into Wartosc_atrybutu_segmentu (IdAtrybut, IdSegment, Wartosc_liczba) values (@attr_id, @segment_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_segmentu set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdSegment = @segment_id ;
					else
					insert into Wartosc_atrybutu_segmentu (IdAtrybut, IdSegment, Wartosc_zmiennoprzecinkowa) values (@attr_id, @segment_id, @float_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_segmentu set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdSegment = @segment_id ;
					else
					insert into Wartosc_atrybutu_segmentu (IdAtrybut, IdSegment, Wartosc_tekst) values (@attr_id, @segment_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go

create procedure set_file_attribute (@file_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
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

--DECLARE @result int
--EXECUTE set_session_attribute 4, 'marker_set', abc, 1, @result OUTPUT;
--select @result


--DECLARE @res int
--execute set_performer_attribute 1, 'date_of_birth', '2000-01-01', 1, @res output


--execute list_performer_sessions_xml 1

--execute list_session_files_xml 1

--Execute sp_rename 'sp_Select_All_Employees', 'sp_Select_Employees_Details'

--execute sp_helptext 'list_sessions'

--execute Motion.dbo.list_sessions 1
	
	
	
--execute Motion.dbo.list_session_files 1