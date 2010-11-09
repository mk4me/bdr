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
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wap.Wartosc_liczba as int ) as varchar(100)) end
		)
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
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( was.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( was.Wartosc_liczba as int ) as varchar(100)) end
		)
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
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wao.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wao.Wartosc_liczba as int ) as varchar(100)) end
		)
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
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wakp.Wartosc_liczba as int ) as varchar(100)) end
		)
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
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as varchar(100))
			else cast (cast ( wap.Wartosc_liczba as int ) as varchar(100)) end
		)
		else cast ( cast ( wap.Wartosc_zmiennoprzecinkowa as float) as varchar(100) ) end  ) as Value
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
where wap.IdPomiar = @meas_id and a.Nazwa = @attributeName
go

create procedure evaluate_generic_query(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @meas as bit, @mc as bit, @pc as bit, @sg as bit)
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
	if(@sg = 1) and (@perf=1 or @sess=1 or @trial=1 or @mc=1 or @meas=1) set @selectClause = @selectClause + ', ';
	if(@perf = 1) set @selectClause = @selectClause + 'p.IdPerformer as PerformerID, p.Imie as FirstName,	p.Nazwisko as LastName ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +', ';
	if(@sess = 1) set @selectClause = @selectClause + 's.IdSesja as SessionID, s.IdLaboratorium as LabID, dbo.motion_kind_name(s.IdRodzaj_ruchu) as MotionKind,	s.Data as SessionDate,	s.Opis_sesji as SessionDescription ';
	if(@sess = 1 and (@trial=1 or @mc=1 or @meas=1)) set @selectClause = @selectClause +', ';
	if(@trial = 1) set @selectClause = @selectClause +'t.IdObserwacja as TrialID, t.Opis_obserwacji as TrialDescription ';
	if(@trial = 1 and (@mc=1 or @meas=1)) set @selectClause = @selectClause + ', ';
	if(@mc = 1) set @selectClause = @selectClause + 'c.IdKonfiguracja_pomiarowa as MeasurementConfID,	c.Nazwa as MeasureConfName, c.Opis as MeasureConfDescription ';
	if(@mc = 1 and  @meas=1) set @selectClause = @selectClause + ', ';
	if(@meas = 1) set @selectClause = @selectClause + 'm.IdPomiar as MeasurementID ';

	
	set @selectClause = @selectClause + ' , (select * from ( ';
	if(@perf =1) set @selectClause = @selectClause + 'select * from list_performer_attributes (p.IdPerformer) ';
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	-- TODO: if(@pc=1) set @selectClause = @selectClause + 'select * from list_performer_configuration_attributes (p.IdPerformer) ';
	if(@pc = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
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
	if( @pc = 0 and exists (select * from @filter where ContextEntity = 'performer_conf' )) set @pc = 1;
	if( @sg = 0 and exists (select * from @filter where ContextEntity = 'session_group' )) set @sg = 1;
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	
	if( @meas = 0 and ( exists (select * from @filter where ContextEntity = 'measurement' )) or (@perf=1 and @mc =1)) set @meas = 1;	

	if( @sess = 1 and @perf=1 and @meas=0) set @pc = 1;
	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 or @meas=1) )
		begin
		 set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if(@pc = 1) 
		begin
			if(@sess = 1) set @fromClause = @fromClause + 'join Konfiguracja_performera pc on s.IdSesja = pc.IdSesja ';
			else set @fromClause = @fromClause + 'Konfiguracja_performera pc ';
		end;
	
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and (@mc=1 or @meas=1))
		begin
		 set @fromClause = @fromClause + 'join Pomiar as m on m.IdObserwacja = t.IdObserwacja ';
		 set @meas = 1;
		end;
	if( @trial = 0 and (@meas = 1 or (@perf = 1 and @mc =1))) 
		begin
		 set @fromClause = @fromClause + 'Pomiar as m ';
		 set @meas = 1;
		end;

	if (@meas = 1)
		begin
		if(  @mc=1)  
			set @fromClause = @fromClause + 'join Konfiguracja_pomiarowa as mc on p.IdKonfiguracja_pomiarowa = c.IdKonfiguracja_pomiarowa ';
		if( @perf=1)
			set @fromClause = @fromClause + 'join Pomiar_performer as pp on pp.IdPomiar = pp.IdPomiar join Performer as p on p.IdPerformer = pp.IdPerformer ';	
		end	
	else
		begin	
			if( @perf=1)  
				begin
					if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
					else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
				end
			if( @mc=1)  set @fromClause = @fromClause + 'Konfiguracja_pomiarowa as mc ';	
		end;


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
					when 'FirstName' then 'p.Imie'
					when 'LastName' then 'p.Nazwisko'
					when 'PerformerID' then 'p.IdPerformer'
					else '(select top 1 * from perf_attr_value(p.IdPerformer, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'session' then (
				case @currentFeatureName
					when 'SessionID' then 's.IdSesja'
					when 'UserID' then 's.IdUzytkownik'
					when 'LabID' then 's.IdLaboratorium'
					when 'MotionKind' then 'dbo.motion_kind_name(s.IdRodzaj_ruchu)'
					when 'PerformerID' then 's.IdPerformer'
					when 'SessionDate' then 's.Data'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdObserwacja'
					when 'TrialDescription' then 't.Opis_obserwacji'
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

create procedure evaluate_generic_query_uniform(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @mc as bit, @meas as bit, @pc as bit, @sg as bit)
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
	if(@perf = 1 and (@sess=1 or @trial=1 or @mc=1 or @meas=1 or @pc=1)) set @selectClause = @selectClause + 'union ';
	-- TODO: if(@pc = 1) set @selectClause = @selectClause + 'select * from list_performer_conf_attributes_uniform (p.IdPerformer) ';
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
	if( @pc = 0 and exists (select * from @filter where ContextEntity = 'performer_conf' )) set @pc = 1;
	if( @sg = 0 and exists (select * from @filter where ContextEntity = 'session_group' )) set @sg = 1;
	if( @mc = 0 and exists (select * from @filter where ContextEntity = 'measurement_conf' )) set @mc = 1;	
	if( @meas = 0 and ( exists (select * from @filter where ContextEntity = 'measurement' )) or (@perf=1 and @mc =1)) set @meas = 1;	

	if( @sess = 1 and @perf=1 and @meas=0) set @pc = 1;
	if( @sess = 1) set @fromClause = @fromClause + 'user_accessible_sessions('+ cast ( @user_id as VARCHAR ) +') as s ';
	if( @sess = 1 and (@trial=1 or @mc=1 or @meas=1) )
		begin
		 set @fromClause = @fromClause + 'join Obserwacja as t on t.IdSesja = s.IdSesja ';
		 set @trial = 1;
		end;
	if(@pc = 1) 
		begin
			if(@sess = 1) set @fromClause = @fromClause + 'join Konfiguracja_performera pc on s.IdSesja = pc.IdSesja ';
			else set @fromClause = @fromClause + 'Konfiguracja_performera pc ';
		end;
	
	if( @sess = 0 and @trial = 1) set @fromClause = @fromClause + 'Obserwacja as t ';
	if( @trial = 1 and (@mc=1 or @meas=1))
		begin
		 set @fromClause = @fromClause + 'join Pomiar as m on m.IdObserwacja = t.IdObserwacja ';
		 set @meas = 1;
		end;
	if( @trial = 0 and (@meas = 1 or (@perf = 1 and @mc =1))) 
		begin
		 set @fromClause = @fromClause + 'Pomiar as m ';
		 set @meas = 1;
		end;

	if (@meas = 1)
		begin
		if(  @mc=1)  
			set @fromClause = @fromClause + 'join Konfiguracja_pomiarowa as mc on p.IdKonfiguracja_pomiarowa = c.IdKonfiguracja_pomiarowa ';
		if( @perf=1)
			set @fromClause = @fromClause + 'join Pomiar_performer as pp on pp.IdPomiar = pp.IdPomiar join Performer as p on p.IdPerformer = pp.IdPerformer ';	
		end	
	else
		begin	
			if( @perf=1)  
				begin
					if(@pc = 0) set @fromClause = @fromClause + 'Performer as p ';	
					else set @fromClause = @fromClause + 'join Performer p on pc.IdPerformer = p.IdPerformer ';
				end
			if( @mc=1)  set @fromClause = @fromClause + 'Konfiguracja_pomiarowa as mc ';	
		end;



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
					when 'FirstName' then 'p.Imie'
					when 'LastName' then 'p.Nazwisko'
					when 'PerformerID' then 'p.IdPerformer'
					else '(select top 1 * from perf_attr_value(p.IdPerformer, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'session' then (
				case @currentFeatureName
					when 'SessionID' then 's.IdSesja'
					when 'UserID' then 's.IdUzytkownik'
					when 'LabID' then 's.IdLaboratorium'
					when 'MotionKind' then 'dbo.motion_kind_name(s.IdRodzaj_ruchu)'
					when 'PerformerID' then 's.IdPerformer'
					when 'SessionDate' then 's.Data'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdObserwacja'
					when 'TrialDescription' then 't.Opis_obserwacji'
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




-- UPS Procedures

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
	delete from Obserwacja_Koszyk where IdKoszyk = @basket_id;
	delete from Segment_Koszyk where IdKoszyk = @basket_id;
	delete from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	set @result = 0;
end
go
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

			if (select top 1 IdSesja from Obserwacja where IdObserwacja = @res_id) not in (select IdSesja from dbo.user_accessible_sessions(@user_id))
				begin
					set @result = 3;
					return;
				end
			if not exists ( select * from Obserwacja_Koszyk where IdObserwacja = @res_id and IdKoszyk = @basket_id )
			insert into Obserwacja_Koszyk ( IdKoszyk, IdObserwacja ) values ( @basket_id, @res_id );
		end;
	else if @entity = 'segment'
		begin
			if not exists ( select * from Segment_Koszyk where IdSegment = @res_id and IdKoszyk = @basket_id )
			insert into Segment_Koszyk ( IdKoszyk, IdSegment ) values ( @basket_id, @res_id );
		end;
	else
		begin
			set @result = 7;
			return;
		end;
	set @result = 0;
end
go
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
		delete from Obserwacja_Koszyk where IdKoszyk = @basket_id and IdObserwacja = @res_id;
	else if @entity = 'segment'
		delete from Segment_Koszyk where IdKoszyk = @basket_id and IdSegment = @res_id;
	else
		begin
			set @result = 7;
			return;
		end;
	set @result = 0;
end
go

create procedure list_basket_performers_attributes_xml (@user_login varchar(30), @basket_name varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where IdPerformer in (select IdPerformer from Performer_Koszyk pk join Koszyk k on pk.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketPerformerWithAttributesList')
go

create procedure list_basket_sessions_attributes_xml (@user_login varchar(30), @basket_name varchar(30))
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
	select
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKindID,
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdSesja in
	(select IdSesja from Sesja_Koszyk sk join Koszyk k on sk.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
      for XML AUTO, ELEMENTS, root ('BasketSessionWithAttributesList')
go

create procedure list_basket_trials_attributes_xml(@user_login varchar(30), @basket_name varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Opis_obserwacji as TrialDescription, 
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) 
	and IdObserwacja in 
	(select IdObserwacja from Obserwacja_Koszyk ok join Koszyk k on ok.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketTrialWithAttributesList')
go


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

create procedure list_users_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService')
select Login "@Login", Imie "@FirstName", Nazwisko "@LastName"
	from Uzytkownik
    for XML PATH('UserDetails'), root ('UserList')
go

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
  if @entity not in ( 'performer', 'session', 'trial', 'measurement', 'measurement_conf', 'performer_conf')
  	begin
		set @result = 2;
		return;
	end;
  insert into Grupa_atrybutow ( Nazwa, Opisywana_encja) values ( @group_name, @entity );
end;
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
