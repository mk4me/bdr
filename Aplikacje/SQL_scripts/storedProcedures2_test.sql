use Motion;

create type FileNameListUdt as table
(
	Name varchar(255)
)
go

declare @nazwy as FileNameListUdt;
insert into @nazwy values ('	2013-02-28-B0114-S01-T02.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T02.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T02.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T03.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T03.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T03.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T03.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T03.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T03.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T04.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T04.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T04.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T04.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T04.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T04.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T05.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T05.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T05.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T05.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T05.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T05.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T06.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T06.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T06.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T06.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T06.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T06.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T08.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T08.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T08.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T08.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T08.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T08.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T09.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T09.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T09.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T09.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T09.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T09.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01.asf	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01.zip	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T01.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T01.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T01.59461898.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T01.60981847.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T01.amc	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T01.c3d	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T02.53875336.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T02.56339527.avi	'	);
insert into @nazwy values ('	2013-02-28-B0114-S01-T02.59461898.avi	'	);




exec validate_file_list_xml @nazwy;



declare @nazwy as FileNameListUdt;
declare @res as int;


insert into @nazwy values ('2013-02-28-S01.xml'	);
insert into @nazwy values ('2013-02-28-S01-T01.png'	);
insert into @nazwy values ('2013-02-28-S01-T01.png'	);

insert into @nazwy values ('2013-02-28-S01-T01.xml'	);
insert into @nazwy values ('2013-02-28-S01-T02.xml'	);
insert into @nazwy values ('2013-02-28-S01-T02.png'	);
insert into @nazwy values ('2013-02-28-S01-T02.png'	);




exec create_session_from_file_list 'habela', @nazwy, @res OUTPUT

select @res

select * from Sesja

select * from Proba

delete from Proba
delete from Sesja


select * from Rodzaj_ruchu

delete from Rodzaj_ruchu where IdRodzaj_ruchu = 9



declare @filters as PredicateUdt;



insert into @filters values (3, 0, 'GROUP', 0, '', 'BRANCH', '', '', '', '');
	insert into @filters values (4, 3, 'GROUP', 0, 'AND', 'all', '', '', '', '');
		insert into @filters values (5, 4, 'session', 0, '', 'SessionID', '>', '0', '', '');

	insert into @filters values (6, 3, 'GROUP', 4, '', 'SIBLING', '', '', '', '');
		insert into @filters values (7, 6, 'GROUP', 0, 'OR', '<130', '', '', '', '');
			insert into @filters values (8, 7, 'session', 0, '', 'SessionID', '<', '130', '', '');
		insert into @filters values (9, 6, 'GROUP', 7, '', '>=2', '', '', '', '');
			insert into @filters values (10, 9, 'session', 0, '', 'SessionID', '>=', '2', '', '');
exec dbo.evaluate_generic_query_uniform 'habela', @filters,  0,  1,  0,  0 

declare @filters as PredicateUdt;
insert into @filters values (1, 0, 'GROUP', 0, '', 'BRANCH', '', '', '', '');
	insert into @filters values (6, 1, 'GROUP', 0, 'AND', 'perfid<10', '', '', '', '');
		insert into @filters values (8, 6, 'performer', 0, '', 'PerformerID', '<', '10', '', '');
	insert into @filters values (5, 1, 'GROUP', 6, '', 'SIBLING', '', '', '', '');
		insert into @filters values (7, 5, 'GROUP', 0, '', 'more_sessions', '', '', '', '');
			insert into @filters values (9, 7, 'performer', 0, '', 'SessionID', '>', '2', 'count', 'session');

exec dbo.evaluate_generic_query_uniform 'habela', @filters,  1,  0,  0,  0 


select * from Sesja where IdPerformer < 10

exec dbo.evaluate_generic_query_uniform 'habela', @filters,  0,  1,  0,  0 



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

/* -- TEST
CREATE TABLE PredykatTest (
        IdPredykat            int IDENTITY,
		PredicateID int,
		ParentPredicate int,
		ContextEntity varchar(20),
		PreviousPredicate int,
		NextOperator varchar(5),
		FeatureName varchar(100),
		Operator varchar(5),
		Value varchar(100),
		AggregateFunction varchar(10),
		AggregateEntity varchar(20),
		timestamp
 )
*/

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

create function measurement_conf_attr_value(@mc_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wakp.Wartosc_tekst
		when 'integer' then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as varchar(100))
		else cast ( cast ( wakp.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
where wakp.IdKonfiguracja_pomiarowa = @mc_id and a.Nazwa = @attributeName
go

create function measurement_attr_value(@meas_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wap.Wartosc_tekst
		when 'integer' then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as varchar(100))
		else cast ( cast ( wap.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
where wap.IdPomiar = @meas_id and a.Nazwa = @attributeName
go

/* sample calls of filter-based queries */





--declare @filters as PredicateUdt;
--insert into @filters values (1, 0, 'GROUP', 0, 'AND', '', '', '', '', '');
--insert into @filters values (2, 1, 'performer', 0, 'OR', 'date_of_birth', '>', '1980-01-01', '', '');
--insert into @filters values (3, 1, 'session', 2, '', 'SessionID', '=', '1', '', '');
--insert into @filters values (4, 0, 'performer', 1, '', 'LastName', '=', 'Kowalski', '', '');

--insert into @filters values (1, 3, 'session', 0, '', 'MotionKindID', '=', '2', '', '');
--insert into @filters values (3, 5, 'GROUP', 0, 'OR', 'Walk', '', '', '', '');
--insert into @filters values (2, 4, 'session', 0, '', 'MotionKindID', '=', '3', '', '');
--insert into @filters values (4, 5, 'GROUP', 3, '', 'Run', '', '', '', '');
--insert into @filters values (5, 8, 'GROUP', 7, '', 'SIBLING', '', '', '', '');
--insert into @filters values (6, 7, 'session', 0, '', 'SessionID', '<=', '10', '', '');
--insert into @filters values (7, 8, 'GROUP', 0, 'AND', 'NiskieNry', '', '', '', '');
--insert into @filters values (8, 0, 'GROUP', 0, '', 'BRANCH', '', '', '', '');


--exec dbo.evaluate_generic_query @filters,  1,  1,  0,  0 

--declare @filters as PredicateUdt;
----insert into @filters values (1, 0, 'GROUP', 0, 'AND', '', '', '', '', '');
----insert into @filters values (2, 1, 'performer', 0, 'OR', 'date_of_birth', '>', '1980-01-01', '', '');
----insert into @filters values (3, 1, 'session', 2, '', 'SessionID', '=', '1', '', '');
----insert into @filters values (4, 0, 'performer', 1, '', 'LastName', '=', 'Kowalski', '', '');

--insert into @filters values (1, 3, 'session', 0, '', 'MotionKindID', '=', '2', '', '');
--insert into @filters values (3, 5, 'GROUP', 0, 'OR', 'Walk', '', '', '', '');
--insert into @filters values (2, 4, 'session', 0, '', 'MotionKindID', '=', '3', '', '');
--insert into @filters values (4, 5, 'GROUP', 3, '', 'Run', '', '', '', '');
--insert into @filters values (5, 8, 'GROUP', 7, '', 'SIBLING', '', '', '', '');
--insert into @filters values (6, 7, 'session', 0, '', 'SessionID', '<=', '10', '', '');
--insert into @filters values (7, 8, 'GROUP', 0, 'AND', 'NiskieNry', '', '', '', '');
--insert into @filters values (8, 15, 'GROUP', 0, 'OR', 'BRANCH', '', '', '', '');

--insert into @filters values (9, 10, 'performer', 0, '', 'PerformerID', '>', '10', '', '');
--insert into @filters values (10, 11, 'GROUP', 0, '', 'PerfIDHigherTen', '', '', '', '');
--insert into @filters values (11, 14, 'GROUP', 13, '', 'SIBLING', '', '', '', '');

--insert into @filters values (12, 13, 'performer', 0, '', 'FirstName', '=', 'Jan', '', '');
--insert into @filters values (13, 14, 'GROUP', 0, 'AND', 'Jan', '', '', '', '');
--insert into @filters values (14, 15, 'GROUP', 8, '', 'BRANCH', '', '', '', '');
--insert into @filters values (15, 0, 'GROUP', 0, '', 'BRANCH', '', '', '', '');

--exec dbo.evaluate_generic_query @filters,  1,  1,  0,  0 


use Motion;
declare @filters as PredicateUdt;
insert into @filters values (3, 0, 'GROUP', 0, '', 'AllPerformers','','','','');
insert into @filters values (4, 3, 'performer', 0, '', 'PerformerID', '>', '0', '', '');
exec dbo.evaluate_generic_query_uniform 'applet_user', @filters, 1, 1, 0, 0, 0, 0, 0




--declare @filters as PredicateUdt;
--insert into @filters values (8, 0, 'sesja', 0, '', 'sessionID', '=', '1', '', '');
--exec dbo.evaluate_generic_query_uniform @filters,  0,  1,  0,  0 



alter procedure evaluate_generic_query(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @meas as bit, @mc as bit)
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
	
	if(@perf = 1) set @selectClause = @selectClause + 'p.IdPerformer as PerformerID, p.Imie as FirstName,	p.Nazwisko as LastName ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +', ';
	if(@sess = 1) set @selectClause = @selectClause + 's.IdSesja as SessionID, s.IdLaboratorium as LabID, s.IdRodzaj_ruchu as MotionKindID,	s.Data as SessionDate,	s.Opis_sesji as SessionDescription ';
	if(@sess = 1 and (@trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +', ';
	if(@trial = 1) set @selectClause = @selectClause +'t.IdObserwacja as TrialID, t.Opis_obserwacji as TrialDescription, t.Czas_trwania as TrialDuration ';
	if(@trial = 1 and (@mc=1 or @meas=1)) set @selectClause = @selectClause + ', ';
	if(@mc = 1) set @selectClause = @selectClause + 'c.IdKonfiguracja_pomiarowa as MeasurementConfID,	c.Nazwa as MeasureConfName, c.Opis as MeasureConfDescription ';
	if(@mc = 1 and  @meas=1) set @selectClause = @selectClause + ', ';
	if(@mc = 1) set @selectClause = @selectClause + 'm.IdPomiar as MeasurementID ';
	
	set @selectClause = @selectClause + ' , (select * from ( ';
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause + 'union ';	
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
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	
	if( @meas = 0 and exists (select * from @filter where ContextEntity = 'measurement' )) set @meas = 1;	


	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 or @meas=1 or @perf=1) )
		begin
		 set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and (@mc=1 or @meas=1 or @perf=1))
		begin
		 set @fromClause = @fromClause + 'join Pomiar as m on m.IdObserwacja = t.IdObserwacja ';
		 set @meas = 1;
		end;
	if( @trial = 0 and (@meas = 1 or (@perf = 1 and @mc =1))) 
		begin
		 set @fromClause = @fromClause + 'Pomiar as m ';
		 set @meas = 1;
		end;
	if( (@meas = 1 and @mc=1)  )
		 set @fromClause = @fromClause + 'join Konfiguracja_pomiarowa as mc on p.IdKonfiguracja_pomiarowa = c.IdKonfiguracja_pomiarowa ';
	if( (@meas = 1 and @perf=1)  )
		 set @fromClause = @fromClause + 'join Pomiar_performer as pp on pp.IdPomiar = pp.IdPomiar join Performer as p on p.IdPerformer = pp.IdPerformer ';	

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
			
--Id<ContextEntity> in ( select Id<ContextEntity> from <AggregateEntity> group by Id<ContextEntity> having <AggregateFunction>(<FeatureName>) <Operator> <Value> )	
			set @leftOperandCode = (
			case ( case @currentAggregateEntity  when '' then @currentContextEntity else @currentAggregateEntity end )
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

alter procedure evaluate_generic_query_uniform(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @mc as bit, @meas as bit)
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
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes_uniform (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause + 'union ';	
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
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	
	if( @meas = 0 and exists (select * from @filter where ContextEntity = 'measurement' )) set @meas = 1;	


	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 or @meas=1 or @perf=1) )
		begin
		 set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and (@mc=1 or @meas=1 or @perf=1))
		begin
		 set @fromClause = @fromClause + 'join Pomiar as m on m.IdObserwacja = t.IdObserwacja ';
		 set @meas = 1;
		end;
	if( @trial = 0 and (@meas = 1 or (@perf = 1 and @mc =1))) 
		begin
		 set @fromClause = @fromClause + 'Pomiar as m ';
		 set @meas = 1;
		end;
	if( (@meas = 1 and @mc=1)  )
		 set @fromClause = @fromClause + 'join Konfiguracja_pomiarowa as mc on p.IdKonfiguracja_pomiarowa = c.IdKonfiguracja_pomiarowa ';
	if( (@meas = 1 and @perf=1)  )
		 set @fromClause = @fromClause + 'join Pomiar_performer as pp on pp.IdPomiar = pp.IdPomiar join Performer as p on p.IdPerformer = pp.IdPerformer ';	



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
			
--Id<ContextEntity> in ( select Id<ContextEntity> from <AggregateEntity> group by Id<ContextEntity> having <AggregateFunction>(<FeatureName>) <Operator> <Value> )	
			set @leftOperandCode = (
			case ( case @currentAggregateEntity  when '' then @currentContextEntity else @currentAggregateEntity end )
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


-- Utility procedures 

create procedure validate_session_group_id( @group_id int )
as
 select count(*) from Grupa_sesji where IdGrupa_sesji = @group_id;
go

-- exec validate_session_group_id 5

create procedure list_session_groups_defined
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdGrupa_sesji as SessionGroupID, Nazwa as SessionGroupName from Grupa_sesji
for XML RAW ('SessionGroupDefinition'), ELEMENTS, root ('SessionGroupDefinitionList')
go

create procedure check_user_account( @user_login varchar(30), @result int OUTPUT )
as
 set @result = ((select count(*) from Uzytkownik where Login = @user_login))
go

create procedure create_user_account (@user_login varchar(30), @user_first_name varchar(30), @user_last_name varchar(50))
as
insert into Uzytkownik ( Login, Imie, Nazwisko) values (@user_login, @user_first_name, @user_last_name );
go




create procedure set_session_privileges (@granting_user_login varchar(30), @granted_user_login varchar(30), @sess_id int, @write bit)
as
begin
	declare @granted_user int;
	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	begin
		set @granted_user = dbo.identify_user(@granted_user_login);
		if @granted_user is NULL 
			begin
				RAISERROR ('Granted user does not exist', 12, 1 )
				return;
			end
		else
		update Uprawnienia_sesja set Zapis = @write where IdSesja = @sess_id and IdUzytkownik = @granted_user;
		if @@ROWCOUNT = 0
		insert into Uprawnienia_sesja ( IdSesja, IdUzytkownik, Zapis) values (@sess_id, @granted_user, @write);
	end
end
go

create procedure unset_session_privileges (@granting_user_login varchar(30), @granted_user_login varchar(30), @sess_id int)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	delete from Uprawnienia_sesja  where IdUzytkownik = dbo.identify_user(@granted_user_login) and IdSesja = @sess_id;
end
go

create procedure alter_session_visibility (@granting_user_login varchar(30), @sess_id int, @public bit, @writeable bit)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	update Sesja set Publiczna = @public, PublicznaZapis = @writeable where IdSesja = @sess_id;
end
go

exec set_session_privileges 'habela', 'kaczmarski', 8, 1