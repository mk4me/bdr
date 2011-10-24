use Motion;

-- last rev. 2010-01-02
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

-- created: 2010-11-25
create type FileNameListUdt as table
(
	Name varchar(255)
)
go


-- last rev. 2010-01-02
create function perf_attr_value(@perf_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wap.Wartosc_tekst
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wap.Wartosc_liczba as int ) as varchar(100)) end
		)
		else cast ( cast ( wap.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
where wap.IdPerformer = @perf_id and a.Nazwa = @attributeName
go

-- last rev. 2010-01-02
create function sess_attr_value(@sess_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then was.Wartosc_tekst
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( was.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( was.Wartosc_liczba as int ) as varchar(100)) end
		)
		else cast ( cast ( was.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
where was.IdSesja = @sess_id and a.Nazwa = @attributeName
go

-- last rev. 2010-01-02
create function trial_attr_value(@trial_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wao.Wartosc_tekst
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wao.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wao.Wartosc_liczba as int ) as varchar(100)) end
		)
		else cast ( cast ( wao.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_proby wao on a.IdAtrybut=wao.IdAtrybut
where wao.IdProba = @trial_id and a.Nazwa = @attributeName
go

-- last rev. 2011-07-11
create function measurement_conf_attr_value(@mc_id int, @attributeName as varchar(100))
returns table
as return
select
	(case a.Typ_danych 
		when 'string' then wakp.Wartosc_tekst
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wakp.Wartosc_liczba as int ) as varchar(100)) end
		)
		else cast ( cast ( wakp.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
where wakp.IdKonfiguracja_pomiarowa = @mc_id and a.Nazwa = @attributeName
go

-- last rev. 2011-07-11
create procedure evaluate_generic_query(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @mc as bit, @pc as bit, @sg as bit)
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
	
	if(@sg = 1) set @selectClause = @selectClause + 'sg.Nazwa as SessionGroup ';
	if(@sg = 1) and (@perf=1 or @sess=1 or @trial=1 or @mc=1) set @selectClause = @selectClause + ', ';
	if(@perf = 1) set @selectClause = @selectClause + 'p.IdPerformer as PerformerID ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1)) set @selectClause = @selectClause +', ';
	if(@sess = 1) set @selectClause = @selectClause + 's.IdSesja as SessionID, s.IdLaboratorium as LabID, dbo.motion_kind_name(s.IdRodzaj_ruchu) as MotionKind,	s.Data as SessionDate,	s.Nazwa as SessionName, s.Tagi as Tags, s.Opis_sesji as SessionDescription ';
	if(@sess = 1 and (@trial=1 or @mc=1)) set @selectClause = @selectClause +', ';
	if(@trial = 1) set @selectClause = @selectClause +'t.IdProba as TrialID, t.Opis_proby as TrialDescription ';
	if(@trial = 1 and @mc=1) set @selectClause = @selectClause + ', ';
	if(@mc = 1) set @selectClause = @selectClause + 'c.IdKonfiguracja_pomiarowa as MeasurementConfID,	c.Nazwa as MeasureConfName, c.Opis as MeasureConfDescription ';
	
	
	set @selectClause = @selectClause + ' , (select * from ( ';
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	-- TODO: if(@pc=1) set @selectClause = @selectClause + 'select * from list_performer_configuration_attributes (p.IdPerformer) ';
	if(@pc = 1 and (@sess=1 or @trial=1 or @mc=1)) set @selectClause = @selectClause + 'union ';
	if(@sess = 1) set @selectClause = @selectClause + 'select * from list_session_attributes (s.IdSesja) ';
	if(@sess = 1 and (@trial=1 or @mc=1)) set @selectClause = @selectClause +'union ';
	if(@trial = 1) set @selectClause = @selectClause +'select * from list_trial_attributes (t.IdProba)  ';
	if(@trial = 1 and @mc=1 ) set @selectClause = @selectClause + 'union ';
	if(@mc = 1) set @selectClause = @selectClause + 'select * from list_measurement_configuration_attributes(c.IdKonfiguracja_pomiarowa) ';

	
	set @selectClause = @selectClause + ') Attribute FOR XML AUTO, TYPE ) as Attributes ';
	
	/* Now consider, if needed, aditional joins needed in the from clause due to the conditions included */
	
	
	if( @perf = 0 and exists (select * from @filter where ContextEntity = 'performer' )) set @perf = 1;
	if( @sess = 0 and exists (select * from @filter where ContextEntity = 'session' )) set @sess = 1;
	if( @trial = 0 and exists (select * from @filter where ContextEntity = 'trial' )) set @trial = 1;
	if( @pc = 0 and exists (select * from @filter where ContextEntity = 'performer_conf' )) set @pc = 1;
	if( @sg = 0 and exists (select * from @filter where ContextEntity = 'session_group' )) set @sg = 1;
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	

	if (@mc = 1) and (@trial = 1 or @perf = 1) set @sess = 1;
	if( @sess = 1 and @perf=1) set @pc = 1;
	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 ) )
		begin
		 set @fromClause = @fromClause + 'join Proba as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if(@pc = 1) 
		begin
			if(@sess = 1) set @fromClause = @fromClause + 'join Konfiguracja_performera pc on s.IdSesja = pc.IdSesja ';
			else set @fromClause = @fromClause + 'Konfiguracja_performera pc ';
		end;
	
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Proba as t ';

	if (@sess = 1 and @mc = 1)
		begin
			set @fromClause = @fromClause + 'join Sesja_Konfiguracja_pomiarowa smc on s.IdSesja = smc.IdSesja join Konfiguracja_pomiarowa as mc on smc.IdKonfiguracja_pomiarowa = mc.IdKonfiguracja_pomiarowa ';
		end	
	if( @perf=1)  
		begin
			if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
			else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
		end


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
			
			set @leftOperandCode = (
			case ( case @currentAggregateEntity  when '' then @currentContextEntity else @currentAggregateEntity end )
	-- TODO: session_group i performer_conf		
			when 'performer' then (
				case @currentFeatureName 
					when 'PerformerID' then 'p.IdPerformer'
					else '(select top 1 * from perf_attr_value(p.IdPerformer, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'session' then (
				case @currentFeatureName
					when 'SessionID' then 's.IdSesja'
					when 'UserID' then 's.IdUzytkownik'
					when 'LabID' then 's.IdLaboratorium'
					when 'MotionKind' then 'dbo.motion_kind_name(s.IdRodzaj_ruchu)'
					when 'SessionDate' then 's.Data'
					when 'SessionName' then 's.Nazwa'
					when 'Tags' then 's.Tagi'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdProba'
					when 'TrialName' then 't.Nazwa'
					when 'TrialDescription' then 't.Opis_proby'
					else '(select top 1 * from trial_attr_value(t.IdProba, '+quotename(@currentFeatureName,'''')+'))' end				)
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
					when 'trial' then 't.IdProba'
					when 'measurement' then 'm.IdPomiar'
					when 'measurement_conf' then 'c.IdKonfiguracja_pomiarowa'
					else 'WRONG CONTEXT ENT.' end
				)
				set @aggregateSearchIdentifierNonQualified = substring(@aggregateSearchIdentifier,3,20);
				set @aggregateEntityTranslated = (
					case @currentAggregateEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Proba'
					when 'measurement_conf' then 'Konfiguracja_pomiarowa'
					else 'WRONG AGGREGATE ENT.' end
				)
				set @contextEntityTranslated = (
					case @currentContextEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Proba'
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

-- last rev. 2011-07-11
create procedure evaluate_generic_query_uniform(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @mc as bit, @pc as bit, @sg as bit)
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
-- TODO:		if(@sg = 1) set @selectClause = @selectClause + 'sg.Nazwa as SessionGroup ';
	-- TODO: if(@sg = 1) and (@perf=1 or @sess=1 or @trial=1 or @mc=1 or @meas=1) set @selectClause = @selectClause + ', ';
	if(@perf = 1) set @selectClause = @selectClause + 'select * from list_performer_attributes_uniform (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	-- TODO: if(@pc = 1) set @selectClause = @selectClause + 'select * from list_performer_conf_attributes_uniform (p.IdPerformer) ';
	if(@sess = 1) set @selectClause = @selectClause + 'select * from list_session_attributes_uniform (s.IdSesja) ';
	if(@sess = 1 and (@trial=1 or @mc=1)) set @selectClause = @selectClause +'union ';
	if(@trial = 1) set @selectClause = @selectClause +'select * from list_trial_attributes_uniform (t.IdProba)  ';
	if(@trial = 1 and @mc=1) set @selectClause = @selectClause + 'union ';
	if(@mc = 1) set @selectClause = @selectClause + 'select * from list_measurement_configuration_attributes_uniform(c.IdKonfiguracja_pomiarowa) ';

	set @selectClause = @selectClause + ') Attribute FOR XML AUTO, TYPE ) ';
	
	/* Now consider, if needed, aditional joins needed in the from clause due to the conditions included */
	
	
	if( @perf = 0 and exists (select * from @filter where ContextEntity = 'performer' )) set @perf = 1;
	if( @sess = 0 and exists (select * from @filter where ContextEntity = 'session' )) set @sess = 1;
	if( @trial = 0 and exists (select * from @filter where ContextEntity = 'trial' )) set @trial = 1;
	if( @pc = 0 and exists (select * from @filter where ContextEntity = 'performer_conf' )) set @pc = 1;
	if( @sg = 0 and exists (select * from @filter where ContextEntity = 'session_group' )) set @sg = 1;
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	

	if (@mc = 1) and (@trial = 1 or @perf = 1) set @sess = 1;
	if( @sess = 1 and @perf=1) set @pc = 1;
	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 ) )
		begin
		 set @fromClause = @fromClause + 'join Proba as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if(@pc = 1) 
		begin
			if(@sess = 1) set @fromClause = @fromClause + 'join Konfiguracja_performera pc on s.IdSesja = pc.IdSesja ';
			else set @fromClause = @fromClause + 'Konfiguracja_performera pc ';
		end;
	
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Proba as t ';



	if( @perf=1)  
		begin
			if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
			else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
		end
	if( @mc=1)  set @fromClause = @fromClause + 'Konfiguracja_pomiarowa as mc ';	


	if (@sess = 1 and @mc = 1)
		begin
			set @fromClause = @fromClause + 'join Sesja_Konfiguracja_pomiarowa smc on s.IdSesja = smc.IdSesja join Konfiguracja_pomiarowa as mc on smc.IdKonfiguracja_pomiarowa = mc.IdKonfiguracja_pomiarowa ';
		end	
	if( @perf=1)  
		begin
			if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
			else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
		end

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
			
			set @leftOperandCode = (
			case ( case @currentAggregateEntity  when '' then @currentContextEntity else @currentAggregateEntity end )
-- TODO: performer_conf i session_group
			when 'performer' then (
				case @currentFeatureName 
					when 'PerformerID' then 'p.IdPerformer'
					else '(select top 1 * from perf_attr_value(p.IdPerformer, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'session' then (
				case @currentFeatureName
					when 'SessionID' then 's.IdSesja'
					when 'UserID' then 's.IdUzytkownik'
					when 'LabID' then 's.IdLaboratorium'
					when 'MotionKind' then 'dbo.motion_kind_name(s.IdRodzaj_ruchu)'
					when 'SessionDate' then 's.Data'
					when 'SessionName' then 's.Nazwa'
					when 'Tags' then 's.Tagi'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdProba'
					when 'TrialName' then 't.Nazwa'
					when 'TrialDescription' then 't.Opis_proby'
					else '(select top 1 * from trial_attr_value(t.IdProba, '+quotename(@currentFeatureName,'''')+'))' end				)
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
					when 'trial' then 't.IdProba'
					when 'measurement' then 'm.IdPomiar'
					when 'measurement_conf' then 'c.IdKonfiguracja_pomiarowa'
					else 'WRONG CONTEXT ENT.' end
				)
				set @aggregateSearchIdentifierNonQualified = substring(@aggregateSearchIdentifier,3,20);
				set @aggregateEntityTranslated = (
					case @currentAggregateEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Proba'
					when 'measurement' then 'Pomiar'
					when 'measurement_conf' then 'Konfiguracja_pomiarowa'
					else 'WRONG AGGREGATE ENT.' end
				)
				set @contextEntityTranslated = (
					case @currentContextEntity
					when 'performer' then 'Performer'
					when 'session' then 'Sesja'
					when 'trial' then 'Proba'
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

-- UPS Procedures
-- last rev. 2010-10-25
create procedure update_stored_filters(@user_login as varchar(30), @filter as PredicateUdt readonly)
as
begin

	declare @user_id int;
	set @user_id = dbo.identify_user(@user_login);
	
	
		delete from Predykat 
		where IdUzytkownik = @user_id
		and not exists ( select * from @filter as f where	f.PredicateID = IdPredykat and
															f.ParentPredicate = IdRodzicPredykat and
														f.ContextEntity = EncjaKontekst and
														f.PreviousPredicate = IdPoprzedniPredykat and
														f.NextOperator = NastepnyOperator and
														f.FeatureName = NazwaWlasciwosci and
														f.Operator = Operator and
														f.Value = Wartosc and
														f.AggregateFunction = FunkcjaAgregujaca and
														f.AggregateEntity = EncjaAgregowana and
														@user_id = IdUzytkownik	);

	insert into Predykat
	( IdPredykat, IdRodzicPredykat,	EncjaKontekst, IdPoprzedniPredykat, NastepnyOperator, NazwaWlasciwosci, 
	Operator, Wartosc,	FunkcjaAgregujaca,	EncjaAgregowana, IdUzytkownik )
		(
		(select PredicateID as IdPredykat,
				ParentPredicate as IdRodzicPredykat,
				ContextEntity as EncjaKontekst,
				PreviousPredicate as IdPoprzedniPredykat,
				NextOperator as NastepnyOperator,
				FeatureName as NazwaWlasciwosci,
				Operator as Operator,
				Value as Wartosc,
				AggregateFunction as FunkcjaAgregujaca,
				AggregateEntity as EncjaAgregowana,
				@user_id as IdUzytkownik	
		from @filter )
		except
		(select * from Predykat where IdUzytkownik = @user_id)

		);
end
go

-- last rev. 2010-07-20
create procedure list_user_filters_xml ( @user_login as varchar(30) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
				IdPredykat "PredicateID",
				IdRodzicPredykat "ParentPredicate",
				EncjaKontekst "ContextEntity",
				IdPoprzedniPredykat "PreviousPredicate",
				NastepnyOperator "NextOperator",
				NazwaWlasciwosci "FeatureName",
				Operator "Operator",
				Wartosc "Value",
				FunkcjaAgregujaca "AggregateFunction",
				EncjaAgregowana "AggregateEntity"
	from Predykat
	where IdUzytkownik = dbo.identify_user(@user_login)
    for XML PATH('Filter'), root ('FilterList')
go

-- last rev. 2010-07-20
create procedure create_basket ( @user_login as varchar(30), @basket_name as varchar(30), @result int OUTPUT)
as
begin
	declare @user_id int;
	set @user_id = dbo.identify_user(@user_login);


	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	if exists ( select * from Koszyk where IdUzytkownik = @user_id and Nazwa = @basket_name )
	begin
		set @result = 2;
		return;
	end;
	
	insert into Koszyk ( Nazwa, IdUzytkownik ) values ( @basket_name, @user_id );
	set @result = 0;
end
go

-- last rev. 2010-12-13
create procedure remove_basket ( @user_login as varchar(30), @basket_name as varchar(30), @result int OUTPUT)
as
begin
	declare @user_id int;
	declare @basket_id int;
	set @user_id = dbo.identify_user(@user_login);

	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	select @basket_id = IdKoszyk from Koszyk where IdUzytkownik = @user_id and Nazwa = @basket_name;
	if @basket_id is NULL
	begin
		set @result = 2;
		return;
	end;
	
	delete from Performer_Koszyk where IdKoszyk = @basket_id;
	delete from Sesja_Koszyk where IdKoszyk = @basket_id;
	delete from Proba_Koszyk where IdKoszyk = @basket_id;
	delete from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	set @result = 0;
end
go

-- last rev. 2010-12-13
/* Error codes:
	1 - login not found
	2 - basket does not exist
	3 - resource not available to the user
	7 - entity not supported */
create procedure add_entity_to_basket( @user_login as varchar(30), @basket_name as varchar(30), @entity as varchar(20), @res_id as int, @result int OUTPUT )
as
begin
	declare @user_id int;
	declare @basket_id int;
	set @user_id = dbo.identify_user(@user_login);

	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	select @basket_id = IdKoszyk from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	
	if (@basket_id is NULL )
	begin 
		set @result = 2;
		return;
	end;	
	if @entity = 'performer'
		begin
			if not exists ( select * from Performer_Koszyk where IdPerformer = @res_id and IdKoszyk = @basket_id )
			insert into Performer_Koszyk ( IdKoszyk, IdPerformer ) values ( @basket_id, @res_id );
		end;
	else if @entity = 'session'
		begin
			if @res_id not in (select IdSesja from dbo.user_accessible_sessions(@user_id))
				begin
					set @result = 3;
					return;
				end
			if not exists ( select * from Sesja_Koszyk where IdSesja = @res_id and IdKoszyk = @basket_id )
			insert into Sesja_Koszyk ( IdKoszyk, IdSesja ) values ( @basket_id, @res_id );
		end;
	else if @entity = 'trial'
		begin

			if (select top 1 IdSesja from Proba where IdProba = @res_id) not in (select IdSesja from dbo.user_accessible_sessions(@user_id))
				begin
					set @result = 3;
					return;
				end
			if not exists ( select * from Proba_Koszyk where IdProba = @res_id and IdKoszyk = @basket_id )
			insert into Proba_Koszyk ( IdKoszyk, IdProba ) values ( @basket_id, @res_id );
		end;
	else
		begin
			set @result = 7;
			return;
		end;
	set @result = 0;
end
go

-- last rev. 2010-12-13
/* Error codes:
	1 - login not found
	2 - basket does not exist
	7 - entity not supported */
create procedure remove_entity_from_basket( @user_login as varchar(30), @basket_name as varchar(30), @entity as varchar(20), @res_id as int, @result int OUTPUT )
as
begin
	declare @user_id int;
	declare @basket_id int;
	
	set @user_id = dbo.identify_user(@user_login);
	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	select @basket_id = IdKoszyk from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	
	if (@basket_id is NULL )
	begin 
		set @result = 2;
		return;
	end;	
	if @entity = 'performer'
		delete from Performer_Koszyk where IdKoszyk = @basket_id and IdPerformer = @res_id;
	else if @entity = 'session'
		delete from Sesja_Koszyk where IdKoszyk = @basket_id and IdSesja = @res_id;
	else if @entity = 'trial'
		delete from Proba_Koszyk where IdKoszyk = @basket_id and IdProba = @res_id;
	else
		begin
			set @result = 7;
			return;
		end;
	set @result = 0;
end
go

-- last rev. 2010-07-20
create procedure list_basket_performers_attributes_xml (@user_login varchar(30), @basket_name varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select
	IdPerformer as PerformerID,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where IdPerformer in (select IdPerformer from Performer_Koszyk pk join Koszyk k on pk.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketPerformerWithAttributesList')
go

-- last rev. 2010-11-27
create procedure list_basket_sessions_attributes_xml (@user_login varchar(30), @basket_name varchar(30))
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
	select
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
		Data as SessionDate,
		Nazwa as SessionName,
		Tagi as Tags,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdSesja in
	(select IdSesja from Sesja_Koszyk sk join Koszyk k on sk.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
      for XML AUTO, ELEMENTS, root ('BasketSessionWithAttributesList')
go

-- last rev. 2010-11-27
create procedure list_basket_trials_attributes_xml(@user_login varchar(30), @basket_name varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
	IdProba as TrialID, 
	IdSesja as SessionID, 
	Nazwa as TrialName,
	Opis_proby as TrialDescription, 
	(select * from list_trial_attributes ( IdProba ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Proba TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) 
	and IdProba in 
	(select IdProba from Proba_Koszyk ok join Koszyk k on ok.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketTrialWithAttributesList')
go

-- last rev. 2010-07-20
create procedure list_user_baskets( @user_login varchar(30) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select
	Nazwa as BasketName
from Koszyk
where IdUzytkownik = dbo.identify_user( @user_login )
for XML RAW ('BasketDefinition'), ELEMENTS, root ('BasketDefinitionList')
go

-- PRIVILEGE AND USER MANAGEMENT
-- last rev. 2010-07-17
create procedure list_users_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService')
select Login "@Login", Imie "@FirstName", Nazwisko "@LastName"
	from Uzytkownik
    for XML PATH('UserDetails'), root ('UserList')
go

-- last rev. 2010-10-28
create procedure list_session_privileges_xml (@user_login varchar(30), @sess_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService')
select
s.Publiczna "@IsPublic",
s.PublicznaZapis "@IsPublicWritable",
(select 
	u.Login "@Login", case us.Zapis when 0 then 'false' else 'true' end "@CanWrite"
	from Uprawnienia_Sesja us join Uzytkownik u on us.IdUzytkownik = u.IdUzytkownik
	where us.IdSesja = s.IdSesja
    for XML PATH('SessionPrivilege'), TYPE )
 from  user_accessible_sessions_by_login(@user_login) s
 where s.IdSesja = @sess_id
  for XML PATH ('SessionPrivilegeList')
go

-- last rev. 2010-07-10
create procedure check_user_account( @user_login varchar(30), @result int OUTPUT )
as
 set @result = ((select count(*) from Uzytkownik where Login = @user_login))
go

-- last rev. 2010-07-10
create procedure create_user_account (@user_login varchar(30), @user_first_name varchar(30), @user_last_name varchar(50))
as
insert into Uzytkownik ( Login, Imie, Nazwisko) values (@user_login, @user_first_name, @user_last_name );
go

-- last rev. 2010-07-10
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

-- last rev. 2010-07-10
create procedure unset_session_privileges (@granting_user_login varchar(30), @granted_user_login varchar(30), @sess_id int)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	delete from Uprawnienia_sesja  where IdUzytkownik = dbo.identify_user(@granted_user_login) and IdSesja = @sess_id;
end
go

-- last rev. 2010-07-10
create procedure alter_session_visibility (@granting_user_login varchar(30), @sess_id int, @public bit, @writeable bit)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	update Sesja set Publiczna = @public, PublicznaZapis = @writeable where IdSesja = @sess_id;
end
go


-- Metadata definition procedures
-- last rev. 2011-07-11
create procedure define_attribute_group (@group_name varchar(100), @entity varchar(20), @result int OUTPUT )
as
begin
	set @result = 0;
  if exists ( select * from Grupa_atrybutow where Nazwa = @group_name and Opisywana_encja = @entity )
	begin
		set @result = 1;
		return;
	end;
  if @entity not in ( 'performer', 'session', 'trial', 'measurement_conf', 'performer_conf')
  	begin
		set @result = 2;
		return;
	end;
  insert into Grupa_atrybutow ( Nazwa, Opisywana_encja) values ( @group_name, @entity );
end;
go

-- last rev. 2010-10-19
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

-- last rev. 2010-11-03
/* define attribute ( group, entity, name, type, subtype, units )
	0 - success
	1 - attribute already exists
	2 - group name not recognized
	3 - illegal type
*/ 
create procedure define_attribute (@attr_name varchar(100), @group_name varchar(100), @entity varchar(20), @is_enum bit, @plugin_desc varchar(100), @type varchar(20), @unit varchar(10), @result int OUTPUT )
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

-- last rev. 2010-10-19
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
-- -------------------

-- last rev. 2010-10-19
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


-- Utility procedures 
-- ------------------

-- last rev. 2010-07-10
create procedure validate_session_group_id( @group_id int )
as
 select count(*) from Grupa_sesji where IdGrupa_sesji = @group_id;
go

-- Wizard procedures
-- -----------------

-- last rev. 2011-10-06
create procedure validate_file_list_xml (  @files as FileNameListUdt readonly )
as
	declare @errorList table(err varchar(200) );
	declare @sessionName varchar(30);
	set @sessionName = 'Not determined';
	declare @trialNames table(tname varchar(30));
	

	-- Czy lista nie jest pusta?
	if ( (select COUNT(*) from @files where CHARINDEX ('-T', Name ) > 0) = 0 )
		insert @errorList values ( 'No files provided at all or files with trial -T** naming convention not found');
	else
	begin
	  -- Ustalam nazwe sesji
	  select top 1 @sessionName = SUBSTRING (Name, 1, CHARINDEX ('-T', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  -- Czy nie ma plikow o niezgodnych nazwach?
	  if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
			insert @errorList select (Name+' name does not start with '+@sessionName) from @files where CHARINDEX (@sessionName , Name)=0 
	  if exists( select * from Sesja where Nazwa = @sessionName )
			insert into @errorList values ('Session with name '+@sessionName+' already exists in the database!');
	  -- Kompletuje liste triali
	  insert @trialNames  select distinct SUBSTRING (Name, 1, CHARINDEX ('.', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  -- jesli jakakolwiek proba posiada pliki .avi - wymagamy po 4 takowe dla kazdej z nich
	  if exists(select * from @files where CHARINDEX ('.avi', Name ) > 0)
	   and exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)>4
		)
		insert into @errorList select ('Trial '+tname+' does not meet the requirement of having at most 4 .avi files') 
		from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4

      if exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)		
		insert into @errorList select ('Trial '+tname+' does not meet the requirement of having 1 .c3d file') 
		from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1	

	end;
	
/*
	-- czy dla kazdego trial-a sa po 4 pliki .avi
	-- czy dla kazdego trial-a jest 1 plik .c3d
	-- czy kazdy z pozostalych plikow jest plikiem nazwa-sesji.zip
	-- czy nie ma juz sesji o zadanej nazwie
	*/
	-- insert into @errorList values ('VALIDATOR NOT YET IMPLEMENTED' from @trialNames));
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
		select
			(select
					0 "SessionDetailsWithAttributes/SessionID",
					@sessionName "SessionDetailsWithAttributes/SessionName",
					0 "SessionDetailsWithAttributes/UserID",
					0 "SessionDetailsWithAttributes/LabID",
					'' "SessionDetailsWithAttributes/MotionKind",
					'2000-01-01 00:00:00.000' "SessionDetailsWithAttributes/SessionDate",
					'' "SessionDetailsWithAttributes/SessionDescription",
					'' "SessionDetailsWithAttributes/SessionLabel",
					(	
						select	
							a.Nazwa "Name", 
							'' "Value",
							a.Typ_danych "Type",
							ga.Nazwa "AttributeGroup",
							'session' "Entity"
						from Atrybut a 
							inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
						where ga.Opisywana_encja = 'session' and ga.Nazwa <> '_static'				
						FOR XML PATH('Attribute'), TYPE 
					) "SessionDetailsWithAttributes/Attributes",
					(	
						select 
							0 "@FileID", 
							p.Name "@FileName", 
							'...' "@FileDescription", 
							'' "@SubdirPath",
							(
								select
									a.Nazwa "Name", 
									'' "Value",
									a.Typ_danych "Type",
									ga.Nazwa "AttributeGroup",
									'file' "Entity"
								from Atrybut a 
									inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
								where ga.Opisywana_encja = 'file' and ga.Nazwa <> '_static'
								FOR XML PATH('Attribute'), TYPE 
							) "Attributes"
							from @files p where CHARINDEX ('-T', Name ) = 0
							for XML PATH('FileDetailsWithAttributes'), TYPE
					) "FileWithAttributesList",
					( 
						select 
							0 "TrialDetailsWithAttributes/TrialID",
							tname "TrialDetailsWithAttributes/TrialName",
							'...' "TrialDetailsWithAttributes/TrialDescription",
							(
									select
											a.Nazwa "Name", 
											'' "Value",
											a.Typ_danych "Type",
											ga.Nazwa "AttributeGroup",
											'file' "Entity"
										from Atrybut a 
											inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
										where ga.Opisywana_encja = 'trial' and ga.Nazwa <> '_static'
										FOR XML PATH('Attribute'), TYPE 
							) "TrialDetailsWithAttributes/Attributes",
							(	
								select 
									0 "@FileID", 
									p.Name "@FileName", 
									'' "@FileDescription", 
									'' "@SubdirPath",
									(
										select
											a.Nazwa "Name", 
											'' "Value",
											a.Typ_danych "Type",
											ga.Nazwa "AttributeGroup",
											'file' "Entity"
										from Atrybut a 
											inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
										where ga.Opisywana_encja = 'file' and ga.Nazwa <> '_static'
										FOR XML PATH('Attribute'), TYPE 
									) "Attributes"
						from @files p where CHARINDEX (tname, Name ) > 0 
						for XML PATH('FileDetailsWithAttributes'), TYPE
					) as FileWithAttributesList
				from @trialNames tc FOR XML PATH('TrialContent'), ELEMENTS, TYPE 
				) "TrialContentList"
				for XML Path('SessionContent'), TYPE, ELEMENTS),
				(select err as Error from @errorList ErrorList FOR XML AUTO, TYPE, ELEMENTS ) 
				for XML PATH('FileSetValidationResult'), ELEMENTS
				
go


-- last rev. 2011-10-06
create procedure create_session_from_file_list ( @user_login as varchar(30), @files as FileNameListUdt readonly, @result int output )
as
	set @result = 0;
	declare @fileStoreList table(fname varchar(100), resid int, entity varchar(20) );
	declare @sessionName varchar(30);
	declare @sessionDate DateTime;
	declare @trialNames table(tname varchar(30));
	declare @trialName varchar(30);
	
	declare @sessionId int;
	declare @trialId int;
	declare @labId int;
	declare @res int;
	
	declare @perf_id int;
	
	declare @trialsToCreate int;
	set @sessionId = 0;

	 select top(1) @labId=IdLaboratorium from Laboratorium;
	

	-- Czy lista nie jest pusta?
	if ( (select COUNT(*) from @files where CHARINDEX ('-T', Name ) > 0) = 0 )
		begin
			set @result = 1;
			return;
		end;

	  -- Ustalam nazwe sesji
	  select top 1 @sessionName = SUBSTRING (Name, 1, CHARINDEX ('-T', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  if( ISDATE ( SUBSTRING(@sessionName,1,10))<>1)
		begin
			set @result = 1;
			return;
		end;
	  set @sessionDate = CAST ( SUBSTRING(@sessionName,1,10) as Date);
	  
	  -- Okreslam numer performera
	  set @perf_id = CAST ( SUBSTRING(@sessionName,13,4) as int );
	  
	  -- Czy nie ma plikow o niezgodnych nazwach?
	  if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
		begin
			set @result = 1;
			return;
		end;
	  if exists( select * from Sesja where Nazwa = @sessionName )
		begin
			set @result = 1;
			return;
		end;
	  -- Kompletuje liste triali
	  insert @trialNames  select distinct SUBSTRING (Name, 1, CHARINDEX ('.', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  if exists(select * from @files where CHARINDEX ('.avi', Name ) > 0) and exists( select * from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)		
		)
		begin
			set @result = 1;
			return;
		end;

	  if exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1		
		)
		begin
			set @result = 1;
			return;
		end;
	
		if not exists ( select * from Performer where IdPerformer = @perf_id )
		begin
			insert into Performer ( IdPerformer ) values ( @perf_id );
		end;
	
	exec create_session  @user_login, 1, 'walk', @sessionDate, @sessionName, '', '', @sessionId OUTPUT, @res OUTPUT; 
	
	if (@result<>0) 
		begin
			set @result = 1;
			return;
		end;
		
	insert into Konfiguracja_performera ( IdPerformer, IdSesja ) values (@perf_id, @sessionId );
	-- po przetestowaniu zamien wykomentowania gora-dol
	-- set @sessionId = 1;
									
	insert @fileStoreList ( fname, entity, resid ) select Name, 'session', @sessionId from @files f where ( CHARINDEX ('-T', f.Name)=0 );
	
	select @trialsToCreate = COUNT(*) from @trialNames;
	
	while @trialsToCreate > 0
		begin
			select top(1) @trialName = tn.tname from @trialNames tn where not exists ( select * from @fileStoreList fsl where ( CHARINDEX(tn.tname,fsl.fname)>0  ));

			
			insert into Proba ( IdSesja, Opis_proby, Nazwa) values (@sessionId, '', @trialName ) set @trialId = SCOPE_IDENTITY();
			-- po przetestowaniu zamien wykomentowania gora-dol
			-- set @trialId = @trialsToCreate;
            insert @fileStoreList ( fname, entity, resid ) select Name, 'trial', @trialId from @files f where ( CHARINDEX (@trialName, f.Name)>0 );
			set @trialsToCreate = @trialsToCreate-1;						
		end;

	select * from @fileStoreList order by entity;				
go


-- Shallow copy retrieval
-- ==========================
-- TODO: konfiguracje pomiarowe / ew. - grupy atrybutow
-- last rev. 2011-10-24
create procedure get_shallow_copy @user_login varchar(30)
as
with
UAS as (select * from dbo.user_accessible_sessions_by_login (@user_login) Session ),
UAGA as (select * from Sesja_grupa_sesji GroupAssignment where exists(select * from UAS where UAS.IdSesja = GroupAssignment.IdSesja)),
UAT as (select * from Proba Trial where exists (select * from UAS where UAS.IdSesja = Trial.IdSesja)),
UAP as (select * from Performer Performer where exists (select * from Konfiguracja_performera KP where exists (select * from UAS where UAS.IdSesja = KP.IdSesja) )),
UAPC as (select * from Konfiguracja_performera PerformerConf where exists(select * from UAS where UAS.IdSesja = PerformerConf.IdSesja))
select
(select 
	IdSesja as SessionID,
	IdUzytkownik as UserID,
	IdLaboratorium as LabID,
	dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
	Data as SessionDate,
	Nazwa as SessionName,
	Tagi as Tags,
	Opis_sesji as SessionDescription,
	(select Name, Value from list_session_attributes ( IdSesja ) A FOR XML AUTO, TYPE ) Attrs, 
	(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja
	for XML PATH('File'), TYPE) as Files
	from UAS Session for XML AUTO, TYPE
 ) Sessions,
 (select 
	IdSesja as SessionID,
	IdGrupa_sesji as SessionGroupID 
	from UAGA GroupAssignment for XML AUTO, TYPE
 ) GroupAssignments,
 (select 
	IdProba as TrialID,
	IdSesja as SessionID,
	Nazwa as TrialName,
	Opis_proby as TrialDescription,
	(select Name, Value from list_trial_attributes ( IdProba ) A FOR XML AUTO, TYPE ) Attrs,
	(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
		from Plik p 
		where 
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as Files
	from UAT Trial FOR XML AUTO, TYPE 
 ) Trials,
 (select 
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_attributes ( IdPerformer ) A FOR XML AUTO, TYPE ) Attrs
	from UAP Performer FOR XML AUTO, TYPE 
 ) Performers,
 (select 
	IdKonfiguracja_performera as PerformerConfID,
	IdSesja as SessionID,
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_configuration_attributes( IdKonfiguracja_performera ) A FOR XML AUTO, TYPE ) Attrs
	from UAPC Performer FOR XML AUTO, TYPE 
 ) PerformerConfs
 for XML RAW ('ShallowCopy'), TYPE;
go


-- last rev. 2011-10-24
create procedure get_shallow_copy_increment @user_login varchar(30), @since datetime
as
with
UAS as (select * from dbo.user_accessible_sessions_by_login (@user_login) Session ),
UAGA as (select * from Sesja_grupa_sesji GroupAssignment where exists(select * from UAS where UAS.IdSesja = GroupAssignment.IdSesja)),
UAT as (select * from Proba Trial where exists (select * from UAS where UAS.IdSesja = Trial.IdSesja)),
UAP as (select * from Performer Performer where exists (select * from Konfiguracja_performera KP where exists (select * from UAS where UAS.IdSesja = KP.IdSesja) )),
UAPC as (select * from Konfiguracja_performera PerformerConf where exists(select * from UAS where UAS.IdSesja = PerformerConf.IdSesja))
select
(select 
	IdSesja as SessionID,
	IdUzytkownik as UserID,
	IdLaboratorium as LabID,
	dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
	Data as SessionDate,
	Nazwa as SessionName,
	Tagi as Tags,
	Opis_sesji as SessionDescription,
	(select Name, Value from list_session_attributes ( IdSesja ) A FOR XML AUTO, TYPE ) Attrs, 
	(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
	(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
	from Plik p where p.IdSesja=Session.IdSesja and  Ostatnia_zmiana > @since
	for XML PATH('File'), TYPE) as Files
	from UAS Session where Ostatnia_zmiana > @since for XML AUTO, TYPE
 ) Sessions,
 (select 
	IdSesja as SessionID,
	IdGrupa_sesji as SessionGroupID 
	from UAGA GroupAssignment for XML AUTO, TYPE
 ) GroupAssignments,
 (select 
	IdProba as TrialID,
	IdSesja as SessionID,
	Nazwa as TrialName,
	Opis_proby as TrialDescription,
	(select Name, Value from list_trial_attributes ( IdProba ) A FOR XML AUTO, TYPE ) Attrs,
	(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", p.Zmieniony "@Changed",
		(select Name, Value  from list_file_attributes ( IdPlik ) A FOR XML AUTO, TYPE ) as Attrs
		from Plik p 
		where  Ostatnia_zmiana > @since and
		p.IdProba=Trial.IdProba for XML PATH('File'), TYPE) as Files
	from UAT Trial where Ostatnia_zmiana > @since FOR XML AUTO, TYPE 
 ) Trials,
 (select 
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_attributes ( IdPerformer ) A FOR XML AUTO, TYPE ) Attrs
	from UAP Performer where Ostatnia_zmiana > @since FOR XML AUTO, TYPE 
 ) Performers,
 (select 
	IdKonfiguracja_performera as PerformerConfID,
	IdSesja as SessionID,
	IdPerformer as PerformerID,
	(select Name, Value from list_performer_configuration_attributes( IdKonfiguracja_performera ) A FOR XML AUTO, TYPE ) Attrs
	from UAPC Performer  where Ostatnia_zmiana > @since FOR XML AUTO, TYPE 
 ) PerformerConfs
 for XML RAW ('ShallowCopy'), TYPE;
go



-- last rev. 2011-02-11
create procedure get_metadata @user_login varchar(30)
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


-- last rev. 2011-08-17
create procedure feed_anthropometric_data(
	@pid int,
	@sname varchar(4),
	@BodyMass decimal (5, 2),
	@Height int,
	@InterAsisDistance int,
	@LeftLegLength int,
	@RightLegLenght int,
	@LeftKneeWidth int,
	@RightKneeWidth int,
	@LeftAnkleWidth int,
	@RightAnkleWidth int,
	@LeftCircuitThigh int,
	@RightCircuitThight int,
	@LeftCircuitShank int,
	@RightCircuitShank int,
	@LeftShoulderOffset int,
	@RightShoulderOffset int,
	@LeftElbowWidth int,
	@RightElbowWidth int,
	@LeftWristWidth int,
	@RightWristWidth int,
	@LeftWristThickness int,
	@RightWristThickness int,
	@LeftHandWidth int,
	@RightHandWidth int,
	@LeftHandThickness int,
	@RightHandThickness int,
	@result int OUTPUT )
as
begin
	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
		11 = invalid format of the session label = S99 pattern expected
	*/	
	declare @pc_id int;
	declare @sid int;
	set @result = 0;
	
	  if( ISNUMERIC ( SUBSTRING(@sname,2,2))<>1)
		begin
			set @result = 11;
			return;
		end;
	set @sid = CAST ( SUBSTRING(@sname,2,2) as int);
	
	select @pc_id = kp.IdKonfiguracja_performera from
	 Konfiguracja_performera kp join Sesja s on kp.IdSesja = s.IdSesja where kp.IdPerformer = @pid and CHARINDEX (@sname, s.Nazwa ) > 0;
	exec set_performer_conf_attribute @pc_id, 'BodyMass', @BodyMass, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'Height', @Height, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'InterAsisDistance', @InterAsisDistance, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftLegLength', @LeftLegLength, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightLegLenght', @RightLegLenght, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftKneeWidth', @LeftKneeWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightKneeWidth', @RightKneeWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftAnkleWidth', @LeftAnkleWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightAnkleWidth', @RightAnkleWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftCircuitThigh', @LeftCircuitThigh, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightCircuitThight', @RightCircuitThight, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftCircuitShank', @LeftCircuitShank, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightCircuitShank', @RightCircuitShank, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftShoulderOffset', @LeftShoulderOffset, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightShoulderOffset', @RightShoulderOffset, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftElbowWidth', @LeftElbowWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightElbowWidth', @RightElbowWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftWristWidth', @LeftWristWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightWristWidth', @RightWristWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftWristThickness', @LeftWristThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightWristThickness', @RightWristThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftHandWidth', @LeftHandWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightHandWidth', @RightHandWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftHandThickness', @LeftHandThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightHandThickness', @RightHandThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
end;

-- Timestamp procedures and triggers

-- last rev. 2011-10-17
create trigger tr_Wartosc_atrybutu_konfiguracji_performera_Update on Wartosc_atrybutu_konfiguracji_performera
for update, insert
as
begin
	update Konfiguracja_performera
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_performera kp on i.IdKonfiguracja_performera = kp.IdKonfiguracja_performera
end
go

-- last rev. 2011-10-17
create trigger tr_Wartosc_atrybutu_sesji_Update on Wartosc_atrybutu_sesji
for update, insert
as
begin
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

-- last rev. 2011-10-17
create trigger tr_Wartosc_atrybutu_konfiguracji_pomiarowej_Update on Wartosc_atrybutu_konfiguracji_pomiarowej
for update, insert
as
begin
	update Konfiguracja_pomiarowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

-- last rev. 2011-10-17
create trigger tr_Wartosc_atrybutu_performera_Update on Wartosc_atrybutu_performera
for update, insert
as
begin
	update Performer
	set Ostatnia_zmiana = getdate()
	from inserted i join Performer p on i.IdPerformer = p.IdPerformer
end
go

-- last rev. 2011-10-17
create trigger tr_Wartosc_atrybutu_pliku_Update on Wartosc_atrybutu_pliku
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
end
go

-- last rev. 2011-10-17
create trigger tr_Wartosc_atrybutu_proby_Update on Wartosc_atrybutu_proby
for update, insert
as
begin
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go

-- last rev. 2011-10-17
create trigger tr_Sesja_Update on Sesja
for update, insert
as
begin
	update Sesja
	set Ostatnia_zmiana = getdate()
	from inserted i join Sesja s on i.IdSesja = s.IdSesja
end
go

-- last rev. 2011-10-17
create trigger tr_Konfiguracja_pomiarowa_Update on Konfiguracja_pomiarowa
for update, insert
as
begin
	update Konfiguracja_pomiarowa
	set Ostatnia_zmiana = getdate()
	from inserted i join Konfiguracja_pomiarowa kp on i.IdKonfiguracja_pomiarowa = kp.IdKonfiguracja_pomiarowa
end
go

-- last rev. 2011-10-17
create trigger tr_Plik_Update on Plik
for update, insert
as
begin
	update Plik
	set Ostatnia_zmiana = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik
	if( COLUMNS_UPDATED() = 0x20)
	update Plik
	set Zmieniony = getdate()
	from inserted i join Plik p on i.IdPlik = p.IdPlik;

end
go

-- last rev. 2011-10-17
create trigger tr_Proba_Update on Proba
for update, insert
as
begin
	update Proba
	set Ostatnia_zmiana = getdate()
	from inserted i join Proba p on i.IdProba = p.IdProba
end
go

-- last rev. 2011-10-17
create procedure time_stamp
as
(
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Konfiguracja_performera 
	union
	select max(Ostatnia_zmiana) as ts from Konfiguracja_pomiarowa 
	union
	select max(Ostatnia_zmiana) as ts from Performer
	union 
	select max(Ostatnia_zmiana) as ts from Plik 
	union
	select max(Ostatnia_zmiana) as ts from Proba 
	union
	select max(Ostatnia_zmiana) as ts from Sesja
	union
	select max(Zmieniony) as ts from Plik
	) as q1
)

-- last rev. 2011-10-24
create trigger tr_Grupa_sesji_Update on Grupa_sesji
for update, insert
as
begin
	update Grupa_sesji
	set Ostatnia_zmiana = getdate()
	from inserted i join Grupa_sesji gs on i.IdGrupa_sesji = gs.IdGrupa_sesji
end
go

-- last rev. 2011-10-24
create trigger tr_Rodzaj_ruchu_Update on Rodzaj_ruchu
for update, insert
as
begin
	update Rodzaj_ruchu
	set Ostatnia_zmiana = getdate()
	from inserted i join Rodzaj_ruchu rr on i.IdRodzaj_ruchu = rr.IdRodzaj_ruchu
end
go

-- last rev. 2011-10-24
create trigger tr_Laboratorium_Update on Laboratorium
for update, insert
as
begin
	update Laboratorium
	set Ostatnia_zmiana = getdate()
	from inserted i join Laboratorium l on i.IdLaboratorium = l.IdLaboratorium
end
go

-- last rev. 2011-10-24
create trigger tr_Grupa_atrybutow_Update on Grupa_atrybutow
for update, insert
as
begin
	update Grupa_atrybutow
	set Ostatnia_zmiana = getdate()
	from inserted i join Grupa_atrybutow ga on i.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
end
go

-- last rev. 2011-10-24
create procedure metadata_time_stamp
as
(
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Grupa_atrybutow 
	union
	select max(Ostatnia_zmiana) as ts from Laboratorium 
	union
	select max(Ostatnia_zmiana) as ts from Rodzaj_ruchu
	union 
	select max(Ostatnia_zmiana) as ts from Grupa_sesji 
	) as q1
)
go