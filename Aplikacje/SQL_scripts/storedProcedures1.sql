use Motion;
go
-- non-XML queries
-- ==================================

create procedure list_performer_sessions @perf_id int
as
	select distinct s.IdSesja as SessionID, s.IdUzytkownik as UserID, s.IdLaboratorium as LabID, 
      s.IdRodzaj_ruchu as MotionKindID, s.Data as SessionDate, s.Opis_sesji as SessionDescription  
      from Pomiar_performer pp join Pomiar p on pp.IdPomiar = p.IdPomiar join Obserwacja o on p.IdObserwacja = o.IdObserwacja join Sesja s on o.IdSesja = s.IdSesja 
      where pp.IdPerformer=@perf_id
go

-- Accessibilty mechanism behavior
-- ===============================

create function user_sessions_by_user_id( @user_id int)
returns table
as
return
select * from Sesja where IdUzytkownik = @user_id
go

create function user_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.* from Uzytkownik u inner join Sesja s on u.IdUzytkownik = s.IdUzytkownik where u.Login = @user_login 
go

create function identify_user( @user_login varchar(30) )
returns int
as
begin
return ( select top 1 IdUzytkownik from Uzytkownik where Login = @user_login );
end
go

create function is_superuser( @user_id int )
returns bit
as
begin
return ( select count(*) from Uzytkownik where IdUzytkownik = @user_id and Login = 'motion_admin'   );
end
go

create function is_login_superuser ( @user_login varchar(30) )
returns bit
begin
 return case @user_login when 'motion_admin' then 1 else 0 end;
end
go

create function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.* from Sesja s where s.Publiczna = 1 or dbo.is_superuser(@user_id)=1)
union
(select s.* from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.* from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
go

create function user_accessible_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select * from user_accessible_sessions( dbo.identify_user( @user_login ))
go



-- Resource attribute and label functions
-- ======================================

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
	ga.Nazwa as AttributeGroup,
	'performer' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id and a.Typ_danych <> 'file'
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
where wap.IdPerformer = @perf_id  and a.Typ_danych <> 'file'));
go

create function session_label( @user_login varchar(30), @sess_id int )
returns TABLE as
return
select l.Nazwa+':'+CONVERT(CHAR(10),s.Data,126) as SessionLabel
from user_accessible_sessions_by_login(@user_login) s inner join Laboratorium l on s.IdLaboratorium = l.IdLaboratorium where s.IdSesja = @sess_id
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
		ga.Nazwa as AttributeGroup,
		'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id and a.Typ_danych <> 'file'
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
where was.IdSesja = @sess_id and a.Typ_danych <> 'file' ));
go

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
		ga.Nazwa as AttributeGroup,
		'trial' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id and a.Typ_danych <> 'file'
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
where wao.IdObserwacja = @trial_id and a.Typ_danych <> 'file'));
go

create function list_measurement_configuration_attributes ( @mc_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wakp.Wartosc_liczba as SQL_VARIANT )
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_pomiarowa = @mc_id and a.Typ_danych <> 'file'
go

create function list_measurement_configuration_attributes_uniform ( @mc_id int )
returns TABLE as
return 
(
 with MeasurementConfiguration as
( select * from Konfiguracja_pomiarowa where IdKonfiguracja_pomiarowa=@mc_id )
	(
	(select 'MeasurementConfID' as Name,
			t.IdKonfiguracja_pomiarowa as Value,
			'integer' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union
	(select 'MeasurementConfName' as Name,
			t.Nazwa as Value,
			'string' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union
	(select 'MeasurementConfKind' as Name,
			t.Rodzaj as Value,
			'string' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union	
	(select 'MeasurementConfDescription' as Name,
			t.Opis as Value,
			'string' as Type,
			'_measurement_conf_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wakp.Wartosc_liczba as SQL_VARIANT )
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_pomiarowa = @mc_id and a.Typ_danych <> 'file' ));
go

create function list_measurement_attributes ( @meas_id int )
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
		'measurement' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPomiar = @meas_id and a.Typ_danych <> 'file'
go

create function list_measurement_attributes_uniform ( @meas_id int )
returns TABLE as
return 
(
 with Measurement as
( select * from Pomiar where IdPomiar=@meas_id )
	(
	(select 'MeasurementID' as Name,
			t.IdPomiar as Value,
			'integer' as Type,
			'_measurement_static' as AttributeGroup,
			'measurement' as Entity
	from Measurement t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wap.Wartosc_liczba as SQL_VARIANT )
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPomiar = @meas_id  and a.Typ_danych <> 'file'));
go


-- Resource By-ID retrieval
-- ========================

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
create procedure get_measurement_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdPomiar as MeasurementID,
				(select * from list_trial_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Pomiar MeasurementDetailsWithAttributes where IdPomiar=@res_id
			for XML AUTO, ELEMENTS
go

create procedure get_measurement_configuration_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdKonfiguracja_pomiarowa as MeasurementConfID,
				Nazwa as MeasurementConfName,
				Rodzaj as MeasurementConfKind,
				Opis as MeasurementConfDescription,
				(select * from list_trial_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Konfiguracja_pomiarowa MeasurementConfDetailsWithAttributes where IdKonfiguracja_pomiarowa=@res_id
			for XML AUTO, ELEMENTS
go

-- Performer listing queries
-- =========================


create procedure list_performers_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdPerformer as PerformerID, Imie as FirstName, Nazwisko as LastName
	from Performer PerformerDetails
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

create procedure list_lab_performers_attributes_xml (@lab_id int)  -- Mozna pytac o przypisania na poziomie sesji
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where exists(
	select * from Pomiar_performer pp join Pomiar p on pp.IdPomiar = p.IdPomiar join Obserwacja o on p.IdObserwacja = o.IdObserwacja join Sesja s  on o.IdSesja = s.IdSesja 
	where s.IdLaboratorium = @lab_id and pp.IdPerformer = PerformerDetailsWithAttributes.IdPerformer)
    for XML AUTO, ELEMENTS, root ('LabPerformerWithAttributesList')
go

create procedure list_measurement_performers_attributes_xml (@meas_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where exists(
	select * from Pomiar_performer pp where pp.IdPomiar = @meas_id and PerformerDetailsWithAttributes.IdPerformer = pp.IdPerformer)
    for XML AUTO, ELEMENTS, root ('MeasurementPerformerWithAttributesList')
go


-- Session listing queries
-- =======================

create procedure list_performer_sessions_xml (@user_login varchar(30), @perf_id int) -- mozna uproscic do wykorzystujacej PerformerConfiguration
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdSesja as SessionID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, Data as SessionDate, 
      Opis_sesji as SessionDescription, (select * from session_label(@user_login,IdSesja)) as SessionLabel
      from user_accessible_sessions_by_login(@user_login) SessionDetails where exists (
		select * from Pomiar_performer pp join Pomiar p on pp.IdPomiar = p.IdPomiar join Obserwacja o on p.IdObserwacja = o.IdObserwacja where pp.IdPerformer=@perf_id
      )
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
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where exists (
		select * from Pomiar_performer pp join Pomiar p on pp.IdPomiar = p.IdPomiar join Obserwacja o on p.IdObserwacja = o.IdObserwacja where pp.IdPerformer=@perf_id
      )
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
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdLaboratorium=@lab_id
      for XML AUTO, ELEMENTS, root ('LabSessionWithAttributesList')
go

create procedure list_group_sessions_attributes_xml (@user_login varchar(30), @group_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		IdRodzaj_ruchu as MotionKindID,
		Data as SessionDate,
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes 
		where exists ( select * from Sesja_grupa_sesji sgs where sgs.IdGrupa_sesji = @group_id and sgs.IdSesja = SessionDetailsWithAttributes.IdSesja)
      for XML AUTO, ELEMENTS, root ('GroupSessionWithAttributesList')
go

-- Trial listing queries
-- =====================

create procedure list_session_trials_xml(@user_login varchar(30),  @sess_id int )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdObserwacja as TrialID,
	IdSesja as SessionID,
	Opis_obserwacji as TrialDescription,
	Czas_trwania as Duration
from Obserwacja TrialDetails where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
      for XML AUTO, root ('SessionTrialList')
go

create procedure list_session_trials_attributes_xml(@user_login varchar(30),   @sess_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Opis_obserwacji as TrialDescription, 
	Czas_trwania as Duration,
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
    for XML AUTO, ELEMENTS, root ('SessionTrialWithAttributesList')
go

-- Measurement configuration listing queries
-- =========================================

create procedure list_measurement_configurations_xml(@user_login varchar(30) ) -- UNUSED?
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdKonfiguracja_pomiarowa MeasurementConfID,
	Nazwa MeasurementConfName,
	Rodzaj MeasurementConfKind,
	Opis MeasurementConfDescription
from Konfiguracja_pomiarowa MeasurementConfDetails
      for XML AUTO, root ('MeasurementConfList')
go

create procedure list_measurement_configurations_attributes_xml(@user_login varchar(30) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdKonfiguracja_pomiarowa MeasurementConfID,
	Nazwa MeasurementConfName,
	Rodzaj MeasurementConfKind,
	Opis MeasurementConfDescription,
	(select * from list_measurement_configuration_attributes(IdKonfiguracja_pomiarowa) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Konfiguracja_pomiarowa MeasurementConfDetailsWithAttributes
    for XML AUTO, ELEMENTS, root ('MeasurementConfWithAttributesList')
go


-- Measurement listing queries
-- ===========================

create procedure list_trial_measurements_xml(@user_login varchar(30), @trial_id int ) -- UNUSED CURRENTLY
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPomiar MeasurementID,
	IdKonfiguracja_pomiarowa MeasurementConfID,
	IdObserwacja TrialID
from Pomiar MeasurementDetails
where IdObserwacja = @trial_id
      for XML AUTO, root ('TrialMeasurementList')
go

create procedure list_trial_measurements_attributes_xml(@user_login varchar(30), @trial_id int )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdPomiar MeasurementID,
	IdKonfiguracja_pomiarowa MeasurementConfID,
	IdObserwacja TrialID,
	(select * from list_measurement_attributes(IdPomiar) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Pomiar MeasurementDetailsWithAttributes
where IdPomiar = @trial_id
    for XML AUTO, ELEMENTS, root ('TrialMeasurementWithAttributesList')
go

-- Measurement result listing queries
-- ==================================

create procedure list_measurement_results_attributes_xml(@user_login varchar(30), @meas_id int )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdPomiar MeasurementID,
	IdKonfiguracja_pomiarowa MeasurementConfID,
	IdObserwacja TrialID,
	(select * from list_measurement_attributes(IdPomiar) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Pomiar MeasurementDetailsWithAttributes
where IdPomiar = @meas_id
    for XML AUTO, ELEMENTS, root ('MeasureResultWithAttributesList')
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
		ga.Nazwa as AttributeGroup,
		'file' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pliku wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPlik = @file_id
go

-- UWAGA! W przyszlosci moze byc potrzebna takze list_file_attributes_uniform .

-- POPRAWIÆ PONI¯SZE! ATRYBUTY I NAZWA ELEMENTU!
/*
create procedure list_measurement_result_files_attributes_xml(@user_login varchar(30),  @meas_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath"
	from Plik p join Wynik_pomiaru wp on p.IdPlik = wp.IdPlik where wp.IdPlik=@meas_id
	for XML PATH('ResultFileDetailsWithAttributes'), root ('MeasurementResultFileWithAttributesList')
go
*/

create procedure list_session_files_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath"
	from Plik p join user_accessible_sessions_by_login(@user_login) s on p.IdSesja = s.IdSesja where p.IdSesja = @sess_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_trial_files_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath"
	from Plik p 
	where 
	((select top 1 IdSesja from Obserwacja where IdObserwacja = @trial_id) 
	in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and p.IdObserwacja=@trial_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_measurement_conf_files_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription"
	from Plik p where p.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_session_attr_files_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_sesji was on p.IdPlik = was.Wartosc_Id join Atrybut a on a.IdAtrybut = was.IdAtrybut where was.IdSesja=@sess_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_trial_attr_files_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_obserwacji wao on p.IdPlik = wao.Wartosc_Id join Atrybut a on a.IdAtrybut = wao.IdAtrybut 
	where 
	((select top 1 IdSesja from Obserwacja where IdObserwacja = @trial_id) 
	in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and wao.IdObserwacja=@trial_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_measurement_conf_attr_files_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut where wakp.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_measurement_attr_files_xml(@user_login varchar(30),  @meas_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_pomiaru wap on p.IdPlik = wap.Wartosc_Id join Atrybut a on a.IdAtrybut = wap.IdAtrybut 
	where wap.IdPomiar = @meas_id
	for XML PATH('FileDetails'), root ('FileList')
go

create procedure list_performer_attr_files_attributes_xml(@user_login varchar(30),  @perf_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_performera wap on p.IdPlik = wap.Wartosc_Id join Atrybut a on a.IdAtrybut = wap.IdAtrybut where wap.IdPerformer=@perf_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

create procedure list_session_files_attributes_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdSesja=@sess_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go
create procedure list_session_attr_files_attributes_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_sesji was on p.IdPlik = was.Wartosc_Id join Atrybut a on a.IdAtrybut = was.IdAtrybut where was.IdSesja=@sess_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

create procedure list_trial_files_attributes_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p 
	where 
	((select top 1 IdSesja from Obserwacja where IdObserwacja = @trial_id) 
	in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and p.IdObserwacja=@trial_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

create procedure list_trial_attr_files_attributes_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_obserwacji wao on p.IdPlik = wao.Wartosc_Id join Atrybut a on a.IdAtrybut = wao.IdAtrybut 
	where 
	((select top 1 IdSesja from Obserwacja where IdObserwacja = @trial_id) 
	in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and wao.IdObserwacja=@trial_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

create procedure list_measurement_conf_files_attributes_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

create procedure list_measurement_conf_attr_files_attributes_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut where wakp.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

create procedure list_measurement_attr_files_attributes_xml(@user_login varchar(30),  @meas_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_pomiaru wap on p.IdPlik = wap.Wartosc_Id join Atrybut a on a.IdAtrybut = wap.IdAtrybut 
	where wap.IdPomiar = @meas_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go


-- Metadata queries
-- ===================

create procedure list_attributes_defined( @att_group varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	a.Nazwa as AttributeName, a.Typ_danych as AttributeType, a.Wyliczeniowy as AttributeEnum, ga.Nazwa as AttributeGroupName,
	a.Podtyp_danych as Subtype, a.Jednostka as Unit 
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where @entity_kind=ga.Opisywana_encja and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')
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


create procedure list_attribute_groups_defined( @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	Nazwa as AttributeGroupName, Opisywana_encja as DescribedEntity
from Grupa_atrybutow
where (@entity_kind=Opisywana_encja or @entity_kind = '_ALL_ENTITIES' or @entity_kind = '_ALL')
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

create procedure list_attribute_enum_values ( @att_name varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select Wartosc_wyliczeniowa "EnumValue"
from Wartosc_wyliczeniowa ww join Atrybut a on ww.IdAtrybut = a.IdAtrybut join Grupa_atrybutow ga on ga.IdGrupa_atrybutow = a.IdGrupa_atrybutow
where a.Nazwa = @att_name and ga.Opisywana_encja = @entity_kind
for XML PATH(''), root ('EnumValueList')
go

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
	declare @integer_value numeric(10,2), @float_value float, @id_value int ;
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
			else if @attr_type = 'file'
				begin
					set @id_value = cast ( @attr_value as int );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_sesji set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdSesja = @sess_id ;
					else
					insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_Id) values (@attr_id, @sess_id, @id_value);
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
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
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
			else if @attr_type = 'file'
				begin
					set @id_value = cast ( @attr_value as int );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_performera set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdPerformer = @perf_id ;
					else
					insert into Wartosc_atrybutu_performera (IdAtrybut, IdPerformer, Wartosc_Id) values (@attr_id, @perf_id, @id_value);
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
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
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
			else if @attr_type = 'file'
				begin
					set @id_value = cast ( @attr_value as int );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_obserwacji set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdObserwacja = @trial_id ;
					else
					insert into Wartosc_atrybutu_obserwacji (IdAtrybut, IdObserwacja, Wartosc_Id) values (@attr_id, @trial_id, @id_value);
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

create procedure set_file_attribute (@file_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
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

create procedure set_measurement_conf_attribute (@mc_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'measurement_conf';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Konfiguracja_pomiarowa where IdKonfiguracja_pomiarowa = @mc_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_konfiguracji_pomiarowej where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_liczba) values (@attr_id, @mc_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_zmiennoprzecinkowa) values (@attr_id, @mc_id, @float_value);
				end;
			else if @attr_type = 'file'
				begin
					set @id_value = cast ( @attr_value as int );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_Id) values (@attr_id, @mc_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_pomiarowej set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdKonfiguracja_pomiarowa = @mc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_pomiarowej (IdAtrybut, IdKonfiguracja_pomiarowa, Wartosc_tekst) values (@attr_id, @mc_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go

create procedure set_measurement_attribute (@meas_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	set @result = 6; -- result 3 = type casting error
	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'measurement_conf';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Pomiar where IdPomiar = @meas_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_pomiaru where IdAtrybut = @attr_id and IdPomiar = @meas_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_pomiaru set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_liczba) values (@attr_id, @meas_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pomiaru set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_zmiennoprzecinkowa) values (@attr_id, @meas_id, @float_value);
				end;
			else if @attr_type = 'file'
				begin
					set @id_value = cast ( @attr_value as int );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pomiaru set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_Id) values (@attr_id, @meas_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pomiaru set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdPomiar = @meas_id ;
					else
					insert into Wartosc_atrybutu_pomiaru (IdAtrybut, IdPomiar, Wartosc_tekst) values (@attr_id, @meas_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go



-- Attribute value removal

create procedure clear_attribute_value(@attr_name varchar(100), @entity varchar(20), @res_id int, @result int OUTPUT  )
as
begin
	set @result = 0;
	if(@entity = 'performer') 
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_performera wap on a.IdAtrybut = wap.IdAtrybut
				where a.Nazwa = @attr_name and wap.IdPerformer = @res_id )
		begin
			set @result = 1;
			return;
		end;	
	delete from wa from Wartosc_atrybutu_performera wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdPerformer = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'session')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_sesji was on a.IdAtrybut = was.IdAtrybut
				where a.Nazwa = @attr_name and was.IdSesja = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_sesji wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdSesja = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'trial')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut = wao.IdAtrybut
				where a.Nazwa = @attr_name and wao.IdObserwacja = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_obserwacji wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdObserwacja = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'file')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_pliku wapl on a.IdAtrybut = wapl.IdAtrybut
				where a.Nazwa = @attr_name and wapl.IdPlik = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_pliku wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdPlik = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'measurement_conf')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut = wakp.IdAtrybut
				where a.Nazwa = @attr_name and wakp.IdKonfiguracja_pomiarowa = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_konfiguracji_pomiarowej wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdKonfiguracja_pomiarowa = @res_id and a.Nazwa = @attr_name;
	end
	else if (@entity = 'measurement')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut = wap.IdAtrybut
				where a.Nazwa = @attr_name and wap.IdPomiar = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_pomiaru wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdPomiar = @res_id and a.Nazwa = @attr_name;
	end;
end
go

-- Metadata definition procedures



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