use Motion;

create type PredicateUdt as table
(
	PredicateID int,
	ParentPredicate int,
	ContextEntity varchar(20),
	PreviousPredicate int,
	NextOperator varchar(5),
	FeatureName varchar(100),
	Operator varchar(5),
	Value varchar(100),
	AggregateFunction varchar(10),
	AggregateEntity varchar(20)
)
go

create function perf_attr_value(@perf_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wap.Wartosc_tekst
		when 'integer' then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as varchar(100))
		else cast ( cast ( wap.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
where wap.IdPerformer = @perf_id and a.Nazwa = @attributeName
go

create function sess_attr_value(@sess_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then was.Wartosc_tekst
		when 'integer' then cast (cast ( was.Wartosc_liczba as numeric(10,2) ) as varchar(100))
		else cast ( cast ( was.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
where was.IdSesja = @sess_id and a.Nazwa = @attributeName
go

create function trial_attr_value(@trial_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wao.Wartosc_tekst
		when 'integer' then cast (cast ( wao.Wartosc_liczba as numeric(10,2) ) as varchar(100))
		else cast ( cast ( wao.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
where wao.IdObserwacja = @trial_id and a.Nazwa = @attributeName
go

create function segm_attr_value(@segm_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wasg.Wartosc_tekst
		when 'integer' then cast (cast ( wasg.Wartosc_liczba as numeric(10,2) ) as varchar(100))
		else cast ( cast ( wasg.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_segmentu wasg on a.IdAtrybut=wasg.IdAtrybut
where wasg.IdSegment = @segm_id and a.Nazwa = @attributeName
go

/* sample calls of filter-based queries */

declare @filters as PredicateUdt;
insert into @filters values (1, 0, 'GROUP', 0, 'AND', '', '', '', '', '');
insert into @filters values (2, 1, 'performer', 0, 'OR', 'date_of_birth', '>', '1980-01-01', '', '');
insert into @filters values (3, 1, 'session', 2, '', 'SessionID', '=', '1', '', '');
insert into @filters values (4, 0, 'performer', 1, '', 'LastName', '=', 'Kowalski', '', '');
exec dbo.evaluate_generic_query @filters,  1,  1,  0,  0 

declare @filters as PredicateUdt;
insert into @filters values (4, 0, 'performer', 0, '', 'LastName', '=', 'Kowalski', '', '');
exec dbo.evaluate_generic_query_uniform @filters,  1,  1,  0,  0 



alter procedure evaluate_generic_query(@filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @segm as bit)
as
begin
	/* Assumed validity constraints of the filter structure:
	
		PredicateID is unique
		NextPredicate has matching PredicateID in other tuple; otherwise should be 0
		For complex subexpression (enclosed in parentheses) the FeatureName should be 'GROUP'
		For any predicate enclosed in parentheses it should contain non-zero value of the ParentPredicate field, matching the PredicateID of respective 'GROUP'-type predicate
		Moreover, the first predicate that occurs directly after the opening parenthesis should have its PreviousPredicate set to 0
		For a predicate that follows some other predicate a non-zero value of the PreviousPredicate identifier field is required
		For a predicate that is not the last segment of its subexpression
		(i.e. it has some further predicate behind it (at the same level of nesting: before closing parenthesis or expression end comes)),
		it is required to provide the NextOperator non-empty and valid value
		
		NO AGGREGATE PREDICATES IMPLEMENTED YET!
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

	
	declare @fromClause varchar (500);
	set @fromClause = ' from ';
	declare @whereClause varchar (1000);
	set @whereClause = ' where ';
	declare @selectClause varchar (1000);
	set @selectClause = 'with XMLNAMESPACES (DEFAULT ''http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService'') select ';
	declare @leftOperandCode varchar (100);
	declare @sql as nvarchar(2500);
	
	/* Determine the content of the select clause */
	
	if(@perf = 1) set @selectClause = @selectClause + 'p.IdPerformer as PerformerID, p.Imie as FirstName,	p.Nazwisko as LastName ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @segm=1)) set @selectClause = @selectClause +', ';
	if(@sess = 1) set @selectClause = @selectClause + 's.IdSesja as SessionID, s.IdLaboratorium as LabID, s.IdRodzaj_ruchu as MotionKindID,	s.Data as SessionDate,	s.Opis_sesji as SessionDescription ';
	if(@sess = 1 and (@trial=1 or @segm=1)) set @selectClause = @selectClause +', ';
	if(@trial = 1) set @selectClause = @selectClause +'t.IdObserwacja as TrialID, t.Opis_obserwacji as TrialDescription, t.Czas_trwania as TrialDuration ';
	if(@trial = 1 and @segm=1) set @selectClause = @selectClause + ', ';
	if(@segm = 1) set @selectClause = @selectClause + 'seg.IdSegment as SegmentID,	seg.Nazwa as SegmentName, seg.Czas_poczatku as StartTime, seg.Czas_konca as EndTime ';
	
	set @selectClause = @selectClause + ' , (select * from ( ';
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @segm=1)) set @selectClause = @selectClause + 'union ';	

	if(@sess = 1) set @selectClause = @selectClause + 'select * from list_session_attributes (s.IdSesja) ';
	if(@sess = 1 and (@trial=1 or @segm=1)) set @selectClause = @selectClause +'union ';
	if(@trial = 1) set @selectClause = @selectClause +'select * from list_trial_attributes (t.IdObserwacja)  ';
	if(@trial = 1 and @segm=1) set @selectClause = @selectClause + 'union ';
	if(@segm = 1) set @selectClause = @selectClause + 'select * from list_segment_attributes(seg.IdSegment) ';
	
	set @selectClause = @selectClause + ') Attribute FOR XML AUTO, TYPE ) as Attributes ';
	
	/* Now consider, if needed, aditional joins needed in the from clause due to the conditions included */
	
	
	if( @perf = 0 and exists (select * from @filter where ContextEntity = 'performer' or AggregateEntity = 'performer')) set @perf = 1;
	if( @sess = 0 and exists (select * from @filter where ContextEntity = 'session' or AggregateEntity = 'session')) set @sess = 1;
	if( @trial = 0 and exists (select * from @filter where ContextEntity = 'trial' or AggregateEntity = 'trial')) set @trial = 1;
	if( @segm = 0 and exists (select * from @filter where ContextEntity = 'segment' or AggregateEntity = 'segment')) set @segm = 1;	

	if( @perf = 1) set @fromClause = @fromClause + 'Performer as p ';
	if( @perf = 1 and (@sess=1 or @trial=1 or @segm=1)) set @fromClause = @fromClause +'join Sesja as s on p.IdPerformer = s.IdPerformer ';
	if( @perf = 0 and @sess = 1) set @fromClause = @fromClause +'Sesja as s ';
	if( @sess = 1 and (@trial = 1 or @segm = 1)) set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and @segm = 1) set @fromClause = @fromClause + 'join Segment as seg on seg.IdObserwacja = t.IdObserwacja ';
	if( @trial = 0 and @segm = 1) set @fromClause = @fromClause + 'Segment as seg ';
	set @fromClause = @fromClause ;


	select @predicatesLeft = count(PredicateID) from @filter;
	select @currentId = 0;
	set @currentGroup = 0;
	while @predicatesLeft > 0
		begin
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
			set @whereClause = @whereClause + '( ';
			set @currentGroup = @currentId;
			set @currentId = 0;
			set @nextOperator = '';
		end
		else
			begin
			
			set @leftOperandCode = (
			case @currentContextEntity 
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
					when 'Duration' then 't.Czas_trwania'
					else '(select top 1 * from trial_attr_value(t.IdObserwacja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'segment' then (
				case @currentFeatureName
					when 'SegmentID' then 'segm.IdSegment'
					when 'SegmentName' then 'segm.Nazwa'
					when 'StartTime' then 'segm.Czas_poczatku'
					when 'EndTime' then 'segm.Czas_konca'
					else '(select top 1 * from segm_attr_value(seg.IdSegment, '+quotename(@currentFeatureName,'''')+'))' end				)
			else 'UNKNOWN' end
			)
			set @whereClause = @whereClause +  @leftOperandCode + @currentOperator + quotename(@currentValue,'''');

			if (@nextOperator = '' and @currentGroup <> 0)
			begin
			 set @whereClause = @whereClause + ') ';
			 set @currentId = @currentGroup;
			 select @currentGroup = ParentPredicate, @nextOperator = NextOperator from @filter where PredicateID=@currentId;
			end
			
			end
		 

		 set @whereClause = @whereClause + ' '+ @nextOperator+' ';
		set @predicatesLeft = @predicatesLeft-1;	
	
	/* Add compile directive */				
		end
		set @whereClause = @whereClause+' for XML RAW (''GenericResultRow''), ELEMENTS, root (''GenericQueryResult'')';
		set @sql = N''+(@selectClause+@fromClause+@whereClause);
		
		exec sp_executesql @statement = @sql;
end
go

alter procedure evaluate_generic_query_uniform(@filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @segm as bit)
as
begin
	/* Assumed validity constraints of the filter structure:
	
		PredicateID is unique
		NextPredicate has matching PredicateID in other tuple; otherwise should be 0
		For complex subexpression (enclosed in parentheses) the FeatureName should be 'GROUP'
		For any predicate enclosed in parentheses it should contain non-zero value of the ParentPredicate field, matching the PredicateID of respective 'GROUP'-type predicate
		Moreover, the first predicate that occurs directly after the opening parenthesis should have its PreviousPredicate set to 0
		For a predicate that follows some other predicate a non-zero value of the PreviousPredicate identifier field is required
		For a predicate that is not the last segment of its subexpression
		(i.e. it has some further predicate behind it (at the same level of nesting: before closing parenthesis or expression end comes)),
		it is required to provide the NextOperator non-empty and valid value
		
		NO AGGREGATE PREDICATES IMPLEMENTED YET!
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

	
	declare @fromClause varchar (500);
	set @fromClause = ' from ';
	declare @whereClause varchar (1000);
	set @whereClause = ' where ';
	declare @selectClause varchar (1000);
	set @selectClause = 'with XMLNAMESPACES (DEFAULT ''http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService'') select ';
	declare @leftOperandCode varchar (100);
	declare @sql as nvarchar(2500);
	
	/* Determine the content of the select clause */
	
	set @selectClause = @selectClause + '(select * from ( ';
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes_uniform (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @segm=1)) set @selectClause = @selectClause + 'union ';	

	if(@sess = 1) set @selectClause = @selectClause + 'select * from list_session_attributes_uniform (s.IdSesja) ';
	if(@sess = 1 and (@trial=1 or @segm=1)) set @selectClause = @selectClause +'union ';
	if(@trial = 1) set @selectClause = @selectClause +'select * from list_trial_attributes_uniform (t.IdObserwacja)  ';
	if(@trial = 1 and @segm=1) set @selectClause = @selectClause + 'union ';
	if(@segm = 1) set @selectClause = @selectClause + 'select * from list_segment_attributes_uniform(seg.IdSegment) ';
	
	set @selectClause = @selectClause + ') Attribute FOR XML AUTO, TYPE ) ';
	
	/* Now consider, if needed, aditional joins needed in the from clause due to the conditions included */
	
	
	if( @perf = 0 and exists (select * from @filter where ContextEntity = 'performer' or AggregateEntity = 'performer')) set @perf = 1;
	if( @sess = 0 and exists (select * from @filter where ContextEntity = 'session' or AggregateEntity = 'session')) set @sess = 1;
	if( @trial = 0 and exists (select * from @filter where ContextEntity = 'trial' or AggregateEntity = 'trial')) set @trial = 1;
	if( @segm = 0 and exists (select * from @filter where ContextEntity = 'segment' or AggregateEntity = 'segment')) set @segm = 1;	

	if( @perf = 1) set @fromClause = @fromClause + 'Performer as p ';
	if( @perf = 1 and (@sess=1 or @trial=1 or @segm=1)) set @fromClause = @fromClause +'join Sesja as s on p.IdPerformer = s.IdPerformer ';
	if( @perf = 0 and @sess = 1) set @fromClause = @fromClause +'Sesja as s ';
	if( @sess = 1 and (@trial = 1 or @segm = 1)) set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and @segm = 1) set @fromClause = @fromClause + 'join Segment as seg on seg.IdObserwacja = t.IdObserwacja ';
	if( @trial = 0 and @segm = 1) set @fromClause = @fromClause + 'Segment as seg ';
	set @fromClause = @fromClause ;


	select @predicatesLeft = count(PredicateID) from @filter;
	select @currentId = 0;
	set @currentGroup = 0;
	while @predicatesLeft > 0
		begin
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
			set @whereClause = @whereClause + '( ';
			set @currentGroup = @currentId;
			set @currentId = 0;
			set @nextOperator = '';
		end
		else
			begin
			
			set @leftOperandCode = (
			case @currentContextEntity 
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
					when 'Duration' then 't.Czas_trwania'
					else '(select top 1 * from trial_attr_value(t.IdObserwacja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'segment' then (
				case @currentFeatureName
					when 'SegmentID' then 'segm.IdSegment'
					when 'SegmentName' then 'segm.Nazwa'
					when 'StartTime' then 'segm.Czas_poczatku'
					when 'EndTime' then 'segm.Czas_konca'
					else '(select top 1 * from segm_attr_value(seg.IdSegment, '+quotename(@currentFeatureName,'''')+'))' end				)
			else 'UNKNOWN' end
			)
			set @whereClause = @whereClause +  @leftOperandCode + @currentOperator + quotename(@currentValue,'''');

			if (@nextOperator = '' and @currentGroup <> 0)
			begin
			 set @whereClause = @whereClause + ') ';
			 set @currentId = @currentGroup;
			 select @currentGroup = ParentPredicate, @nextOperator = NextOperator from @filter where PredicateID=@currentId;
			end
			
			end
		 

		 set @whereClause = @whereClause + ' '+ @nextOperator+' ';
		set @predicatesLeft = @predicatesLeft-1;	
	
	/* Add compile directive */				
		end
		set @whereClause = @whereClause+' for XML RAW (''Attributes''), ELEMENTS, root (''GenericUniformAttributesQueryResult'')';
		set @sql = N''+(@selectClause+@fromClause+@whereClause);
		exec sp_executesql @statement = @sql; 
end
go



