use Motion;

alter table Predykat drop column Nazwa;
go


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
		(select * from Predykat where IdUzytkownik = @user_id)
		except
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
		IdRodzaj_ruchu as MotionKindID,
		IdPerformer as PerformerID,
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
	Czas_trwania as Duration,
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) 
	and IdObserwacja in 
	(select IdObserwacja from Obserwacja_Koszyk ok join Koszyk k on ok.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketTrialWithAttributesList')
go

create procedure list_basket_segments_attributes_xml ( @user_login varchar(30), @basket_name varchar(30) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
	IdSegment as SegmentID,
	IdObserwacja as TrialID,
	Nazwa as SegmentName,
	Czas_poczatku as StartTime,
	Czas_konca as EndTime,
	(select * from list_segment_attributes ( IdSegment ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Segment SegmentDetailsWithAttributes where IdSegment in
	(select IdSegment from Segment_Koszyk sk join Koszyk k on sk.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketSegmentWithAttributesList')
go

alter procedure list_performer_sessions_attributes_xml (@user_login varchar(30), @perf_id int)
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
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdPerformer=@perf_id
      for XML AUTO, ELEMENTS, root ('PerformerSessionWithAttributesList')
go
alter procedure list_lab_sessions_attributes_xml (@user_login varchar(30), @lab_id int)
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
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdLaboratorium=@lab_id
      for XML AUTO, ELEMENTS, root ('LabSessionWithAttributesList')
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