use Motion;

-- non-XML queries
-- ==================================

create procedure list_performer_sessions @perf_id int
as
	select IdSesja as SessonID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, IdPerformer as PerformerID, Data as SessionDate, 
      Opis_sesji as SessionDescription  from Sesja where IdPerformer=@perf_id
go

create procedure list_session_files @sess_id int
as
	select IdPlik as FileID, Nazwa_pliku as FileName from Plik where IdSesja=@sess_id
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
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Performer p on wap.IdPerformer=p.IdPerformer
where p.IdPerformer = @perf_id
go

create procedure list_performers_xml
as
select IdPerformer as PerformerID, Imie as FirstName, Nazwisko as LastName
	from Performer Performer
    for XML AUTO, root ('PerformerList')
go

create procedure list_performers_attributes_xml
as
select
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer Performer
    for XML AUTO, ELEMENTS, root ('PerformerWithAttributesList')
go


-- Session queries
-- =======================


create function list_session_attributes ( @sess_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( was.Wartosc_liczba as SQL_VARIANT )
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Sesja s on was.IdSesja=s.IdSesja
where s.IdSesja = @sess_id

go

create procedure list_performer_sessions_xml @perf_id int
as
	select IdSesja as SessonID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, IdPerformer as PerformerID, Data as SessionDate, 
      Opis_sesji as SessionDescription  from Sesja SessionDetails where IdPerformer=@perf_id
      for XML AUTO, root ('PerformerSessionList')
     
create procedure list_performer_sessions_attributes_xml @perf_id int
as
	select
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		IdRodzaj_ruchu as MotionKindID,
		IdPerformer as PerformerID,
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Sesja SessionDetailsWithAttributes where IdPerformer=@perf_id
      for XML AUTO, ELEMENTS, root ('PerformerSessionWithAttributesList')

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
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Obserwacja o on wao.IdObserwacja=o.IdObserwacja
where o.IdObserwacja = @trial_id

go

create procedure list_session_trials_xml @sess_id int
as
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
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_segmentu was on a.IdAtrybut=was.IdAtrybut
inner join Segment s on was.IdSegment=s.IdSegment
where s.IdSegment = @segment_id

create procedure list_trial_segments_xml @trial_id int
as
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
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_pliku wap on a.IdAtrybut=wap.IdAtrybut
where wap.IdPlik = @file_id
go

create procedure list_performer_files_xml @perf_id int
as
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription from Plik FileDetails where IdPerformer=@perf_id
	for XML AUTO, root ('PerformerFileList')
go

create procedure list_session_files_xml @sess_id int
as
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription from Plik FileDetails where IdSesja=@sess_id
	for XML AUTO, root ('SessionFileList')
go

create procedure list_trial_files_xml @trial_id int
as
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription from Plik FileDetails where IdObserwacja=@trial_id
	for XML AUTO, root ('TrialFileList')
go

create procedure list_performer_files_attributes_xml @perf_id int
as
	select
	IdPlik as FileID,
	Nazwa_pliku as FileName,
	Opis_pliku as FileDescription, 
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes where IdPerformer=@perf_id
	for XML AUTO, root ('PerformerFileWithAttributesList')
go

create procedure list_session_files_attributes_xml @sess_id int
as
	select
	IdPlik as FileID,
	Nazwa_pliku as FileName,
	Opis_pliku as FileDescription, 
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes where IdSesja=@sess_id
	for XML AUTO, root ('SessionFileWithAttributesList')
go

create procedure list_trial_files_attributes_xml @trial_id int
as
	select
		IdPlik as FileID,
		Nazwa_pliku as FileName,
		Opis_pliku as FileDescription,
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes
		where IdObserwacja=@trial_id
	for XML AUTO, root ('TrialFileWithAttributesList')
go




-- Metadata queries
-- ===================

create procedure list_attributes_defined( @att_group varchar(100), @entity_kind varchar(20) )
as
select
	a.Nazwa as attributeName, a.Typ_danych as attributeType, a.Wyliczeniowy as attributeEnum, ga.Nazwa as attributeGroupName
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where @entity_kind=a.Opisywana_encja and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')

go

execute list_attributes_defined  'General_session_attributes', 'session';


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
		from Atrybut where Nazwa = @attr_name and Opisywana_encja = 'session';
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

create procedure set_performer_attribute (@perf_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut where Nazwa = @attr_name and Opisywana_encja = 'performer';
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



create procedure set_trial_attribute (@trial_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float ;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut where Nazwa = @attr_name and Opisywana_encja = 'trial';
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
		from Atrybut where Nazwa = @attr_name and Opisywana_encja = 'segment';
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
		from Atrybut where Nazwa = @attr_name and Opisywana_encja = 'file';
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


DECLARE @result int
EXECUTE set_session_attribute 4, 'marker_set', abc, 1, @result OUTPUT;
select @result



select * from list_session_attributes(1)


execute list_performer_sessions_xml 1

execute list_session_files_xml 1

--Execute sp_rename 'sp_Select_All_Employees', 'sp_Select_Employees_Details'

--execute sp_helptext 'list_sessions'

--execute Motion.dbo.list_sessions 1
	
	
	
--execute Motion.dbo.list_session_files 1