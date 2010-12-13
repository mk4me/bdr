use Motion;
go

alter table Sesja add Nazwa varchar(20) null;
alter table Obserwacja add Nazwa varchar(30) null;
alter table Sesja add Tagi varchar(100) null;

-- alter table Obserwacja alter column Nazwa varchar(30) null;

update Atrybut 
set  IdGrupa_atrybutow = (select top 1 IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session')
where Nazwa = 'SessionName'

update Atrybut 
set  IdGrupa_atrybutow = (select top 1 IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session')
where Nazwa = 'Tags'

update Atrybut 
set  IdGrupa_atrybutow = (select top 1 IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial')
where Nazwa = 'TrialName'



-- alter: 'dbo.user_accessible_sessions'
-- alter: 'dbo.user_accessible_sessions_by_login'
-- alter: 'dbo.user_updateable_sessions'







alter function session_label( @user_login varchar(30), @sess_id int )
returns TABLE as
return
select l.Nazwa+':'+s.Nazwa as SessionLabel
from user_accessible_sessions_by_login(@user_login) s inner join Laboratorium l on s.IdLaboratorium = l.IdLaboratorium where s.IdSesja = @sess_id
go	

alter procedure create_session (	@sess_user varchar(20), @sess_lab int, @mk_name varchar(50), @sess_date DateTime, 
									@sess_name varchar(20), @sess_tags varchar(100), @sess_desc varchar(100), @sess_id int OUTPUT, @result int OUTPUT )
as
begin
	declare @mc_id as int;

	set @result = 2;
	if (select COUNT(*) from Rodzaj_ruchu where Nazwa = @mk_name )!=1
	begin
		set @mc_id = 1;
		return;
	end;
	
	insert into Sesja ( IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, Data, Opis_sesji, Nazwa, Tagi)
		values ((select top(1) IdUzytkownik from Uzytkownik where Login = @sess_user), @sess_lab, (select top(1) IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa = @mk_name), 
		@sess_date, @sess_desc, @sess_name, @sess_tags ) set @sess_id = SCOPE_IDENTITY() ;
	set @result = 0;

end;
go

alter procedure list_performer_sessions @perf_id int
as
	select s.IdSesja as SessionID, s.IdUzytkownik as UserID, s.IdLaboratorium as LabID, 
      s.IdRodzaj_ruchu as MotionKindID, s.Data as SessionDate, s.Nazwa as SessionName, s.Tagi as Tags, s.Opis_sesji as SessionDescription  
      from Konfiguracja_performera kp join Sesja s on kp.IdSesja = s.IdSesja 
      where kp.IdPerformer=@perf_id
go

alter function user_sessions_by_user_id( @user_id int)
returns table
as
return
select IdSesja, IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, Data, Nazwa, Tagi, Opis_sesji, Publiczna, PublicznaZapis from Sesja where IdUzytkownik = @user_id
go

alter function user_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Uzytkownik u inner join Sesja s on u.IdUzytkownik = s.IdUzytkownik where u.Login = @user_login 
go

alter function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s where s.Publiczna = 1 or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
go

alter function user_accessible_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from user_accessible_sessions( dbo.identify_user( @user_login )) s
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
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'UserID' as Name,
			s.IdUzytkownik as Value,
			'integer' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'LabID' as Name,
			s.IdLaboratorium as Value,
			'integer' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'MotionKind' as Name,
			dbo.motion_kind_name(s.IdRodzaj_ruchu) as Value,
			'integer' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'SessionDate' as Name,
			s.Data as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'SessionName' as Name,
			s.Nazwa as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'Tags' as Name,
			s.Tagi as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	union
	(select 'SessionDescription' as Name,
			s.Opis_sesji as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'session' as Entity
	from Sess s)
	)
union
(select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( was.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( was.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( was.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		when 'file' then cast (was.Wartosc_Id  as SQL_VARIANT)
		else cast ( was.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'session' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_sesji was on a.IdAtrybut=was.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where was.IdSesja = @sess_id ));
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
			'_static' as AttributeGroup,
			'trial' as Entity
	from Trial t)
	union
	(select 'TrialName' as Name,
			t.Nazwa as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'trial' as Entity
	from Trial t)
	union
	(select 'TrialDescription' as Name,
			t.Opis_obserwacji as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'trial' as Entity
	from Trial t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wao.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wao.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wao.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		when 'file' then cast (wao.Wartosc_Id  as SQL_VARIANT)
		else cast ( wao.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'trial' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_obserwacji wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdObserwacja = @trial_id ));
go

alter function list_measurement_configuration_attributes_uniform ( @mc_id int )
returns TABLE as
return 
(
 with MeasurementConfiguration as
( select * from Konfiguracja_pomiarowa where IdKonfiguracja_pomiarowa=@mc_id )
	(
	(select 'MeasurementConfID' as Name,
			t.IdKonfiguracja_pomiarowa as Value,
			'integer' as Type,
			'_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union
	(select 'MeasurementConfName' as Name,
			t.Nazwa as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union
	(select 'MeasurementConfKind' as Name,
			t.Rodzaj as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	union	
	(select 'MeasurementConfDescription' as Name,
			t.Opis as Value,
			'string' as Type,
			'_static' as AttributeGroup,
			'measurement_conf' as Entity
	from MeasurementConfiguration t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wakp.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wakp.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		when 'file' then cast (wakp.Wartosc_Id  as SQL_VARIANT)
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_pomiarowa = @mc_id ));
go

alter function list_measurement_attributes_uniform ( @meas_id int )
returns TABLE as
return 
(
 with Measurement as
( select * from Pomiar where IdPomiar=@meas_id )
	(
	(select 'MeasurementID' as Name,
			t.IdPomiar as Value,
			'integer' as Type,
			'_static' as AttributeGroup,
			'measurement' as Entity
	from Measurement t)
	)
union
(	select a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wap.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wap.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		when 'file' then cast (wap.Wartosc_Id  as SQL_VARIANT)
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'measurement' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_pomiaru wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPomiar = @meas_id ));
go

alter procedure get_session_by_id_xml ( @user_login as varchar(30), @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdSesja as SessionID,
				IdUzytkownik as UserID,
				IdLaboratorium as LabID,
				dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
				Data as SessionDate,
				Nazwa as SessionName,
				Tagi as Tags,
				Opis_sesji as SessionDescription,
				(select * from list_session_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdSesja=@res_id
			for XML AUTO, ELEMENTS
go

alter procedure get_trial_by_id_xml ( @res_id int ) 
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdObserwacja as TrialID, 
				IdSesja as SessionID,
				Nazwa as TrialName,
				Opis_obserwacji as TrialDescription, 
				(select * from list_trial_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Obserwacja TrialDetailsWithAttributes where IdObserwacja=@res_id
			for XML AUTO, ELEMENTS
go

alter procedure get_session_content_xml ( @user_login as varchar(30), @sess_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
					IdSesja "SessionDetailsWithAttributes/SessionID",
					IdUzytkownik "SessionDetailsWithAttributes/UserID",
					IdLaboratorium "SessionDetailsWithAttributes/LabID",
					dbo.motion_kind_name(IdRodzaj_ruchu) "SessionDetailsWithAttributes/MotionKind",
					Data "SessionDetailsWithAttributes/SessionDate",
					Nazwa "SessionDetailsWithAttributes/SessionName",
					Tagi "SessionDetailsWithAttributes/Tags",
					Opis_sesji "SessionDetailsWithAttributes/SessionDescription",
					(select * from session_label(@user_login, IdSesja)) "SessionDetailsWithAttributes/SessionLabel",
					(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) "SessionDetailsWithAttributes/Attributes"
					,
				(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
					(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
					from Plik p where p.IdSesja=sc.IdSesja
					for XML PATH('FileDetailsWithAttributes'), TYPE
				) as FileWithAttributesList,
				( 
				   select 
					IdObserwacja "TrialDetailsWithAttributes/TrialID",
					Nazwa "TrialDetailsWithAttributes/TrialName",
					Opis_obserwacji "TrialDetailsWithAttributes/TrialDescription",
					(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) "TrialDetailsWithAttributes/Attributes",
					(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
						(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
						from Plik p 
						where 
						p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes'), TYPE
					) as FileWithAttributesList
				from Obserwacja TrialContent where TrialContent.IdSesja = sc.IdSesja FOR XML PATH('TrialContent'), ELEMENTS, TYPE 
				) as TrialContentList
				from user_accessible_sessions_by_login(@user_login) sc where sc.IdSesja=@sess_id
				for XML Path('SessionContent'), ELEMENTS
go


alter procedure list_performer_sessions_xml (@user_login varchar(30), @perf_id int) -- mozna uproscic do wykorzystujacej PerformerConfiguration
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdSesja as SessionID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind, Data as SessionDate, 
      Nazwa as SessionName, Tagi as Tags, 
      Opis_sesji as SessionDescription, (select * from session_label(@user_login,IdSesja)) as SessionLabel
      from user_accessible_sessions_by_login(@user_login) SessionDetails
      where exists (
		select * from Konfiguracja_performera kp where kp.IdPerformer = @perf_id and kp.IdSesja = SessionDetails.IdSesja
      )
      for XML AUTO, root ('PerformerSessionList')
go

-- last rev: 2010-11-27
alter procedure list_performer_sessions_attributes_xml (@user_login varchar(30), @perf_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
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
		from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes
		where exists (
			select * from Konfiguracja_performera kp where kp.IdPerformer = @perf_id and kp.IdSesja = SessionDetailsWithAttributes.IdSesja
		)
      for XML AUTO, ELEMENTS, root ('PerformerSessionWithAttributesList')
go

-- last rev: 2010-11-27
alter procedure list_lab_sessions_attributes_xml (@user_login varchar(30), @lab_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
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
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes where IdLaboratorium=@lab_id
      for XML AUTO, ELEMENTS, root ('LabSessionWithAttributesList')
go

-- last rev: 2010-11-27
alter procedure list_group_sessions_attributes_xml (@user_login varchar(30), @group_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
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
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes 
		where exists ( select * from Sesja_grupa_sesji sgs where sgs.IdGrupa_sesji = @group_id and sgs.IdSesja = SessionDetailsWithAttributes.IdSesja)
      for XML AUTO, ELEMENTS, root ('GroupSessionWithAttributesList')
go

-- last rev: 2010-11-27
alter procedure list_measurement_conf_sessions_attributes_xml (@user_login varchar(30), @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
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
		from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes
		where exists (
			select * from Pomiar p join Obserwacja o on p.IdObserwacja = o.IdObserwacja 
			where p.IdKonfiguracja_pomiarowa = @mc_id and o.IdSesja = SessionDetailsWithAttributes.IdSesja
		)
      for XML AUTO, ELEMENTS, root ('MeasurementConfSessionWithAttributesList')
go

-- last rev: 2010-11-27
alter procedure list_session_contents_xml (@user_login varchar(30), @page_size int, @page_no int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		-- top @page_size ...
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
		Data as SessionDate,
		Nazwa as SessionName,
		Tagi as Tags, 
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdSesja=SessionContent.IdSesja
	for XML PATH('FileDetailsWithAttributes')) as FileWithAttributesList,
	(select 
		IdObserwacja as TrialID,
		Nazwa as TrialName,
		Opis_obserwacji as TrialDescription,
		(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
		from Plik p 
		where 
		p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes')) as FileWithAttributesList
	from Obserwacja TrialContent where TrialContent.IdSesja = SessionContent.IdSesja FOR XML AUTO, ELEMENTS, TYPE ) as TrialContentList
	from user_accessible_sessions_by_login(@user_login) SessionContent
      for XML AUTO, ELEMENTS, root ('SessionContentList')
go

-- last rev: 2010-11-27
alter procedure list_session_trials_xml(@user_login varchar(30),  @sess_id int )  
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdObserwacja as TrialID,
	IdSesja as SessionID,
	Nazwa as TrialName,
	Opis_obserwacji as TrialDescription
from Obserwacja TrialDetails where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
      for XML AUTO, root ('SessionTrialList')
go

-- last rev: 2010-11-27
alter procedure list_session_trials_attributes_xml(@user_login varchar(30),   @sess_id int)  -- !!! Suspended from production update
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Nazwa as TrialName,
	Opis_obserwacji as TrialDescription, 
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
    for XML AUTO, ELEMENTS, root ('SessionTrialWithAttributesList')
go


-- last rev: 2010-11-27
alter procedure set_session_attribute (@sess_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int ;
	declare @value_tuple_found as bit = 0;
	declare @calculated_id as int;	




	/*
	Current static non-id attributes:
		'LabID', 'int', 0, 'ID'
		'MotionKindID', 'int', 0, 'ID'
		'SessionDate', 'string', 0, 'dateTime'
		'SessionName', 'string', 0, 'shortString'
		'Tags', 'string', 0, 'shortString'
		'SessionDescription', 'string', 0, 'longString'

	*/

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


			if(@attr_name = 'LabID' or @attr_name = 'MotionKind' or @attr_name = 'SessionDate' or @attr_name = 'SessionName' or @attr_name = 'Tags' or @attr_name = 'SessionDescription' )
			begin
				if(@update = 0)
						begin
							set @result = 5; -- result 5 = value exists while update has not been allowed
							return;
						end;	
				if(@attr_name = 'LabID')
					begin
						update Sesja set IdLaboratorium  = cast ( @attr_value as int ) where IdSesja = @sess_id;
					end;
				else if(@attr_name = 'MotionKind')
					begin
						select top(1) @calculated_id = IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa=@attr_value ;
						if (@calculated_id is null) begin set @result = 2; return; end; -- result 2 = illegal enum attribute value
						update Sesja set IdRodzaj_ruchu  =  @calculated_id where IdSesja = @sess_id;
					end
				else if(@attr_name = 'SessionDate')
					update Sesja set Data  =  cast ( @attr_value as datetime ) where IdSesja = @sess_id;
				else if(@attr_name = 'SessionName')
					update Sesja set Nazwa  =  @attr_value where IdSesja = @sess_id;
				else if(@attr_name = 'Tags')
					update Sesja set Tagi  =  @attr_value where IdSesja = @sess_id;
				else if(@attr_name = 'SessionDescription')
					update Sesja set Opis_sesji  = @attr_value where IdSesja = @sess_id;
				set @result = 0;
				return;
			end;
	

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
					if ( not exists ( select IdPlik from Plik where IdPlik = @id_value) )
					begin
						set @result = 7;
						return;
					end;
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

-- last rev. 2010-11-27

alter procedure set_trial_attribute (@trial_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	


	/*
	Current static non-id attributes:
	'TrialDescription', 'string', 0, 'longString'

	*/

	set @result = 6; -- result 6 = type casting error

	if((@attr_name = 'TrialDescription') or (@attr_name = 'TrialName'))
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'TrialDescription')
			update Obserwacja set Opis_obserwacji  = @attr_value where IdObserwacja = @trial_id;
		else if(@attr_name = 'TrialName')
					update Obserwacja set Nazwa  =  @attr_value where IdSesja = @trial_id;

		set @result = 0;
		return;
	end;

	
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
					if ( not exists ( select IdPlik from Plik where IdPlik = @id_value) )
					begin
						set @result = 7;
						return;
					end;
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

alter procedure evaluate_generic_query(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @meas as bit, @mc as bit, @pc as bit, @sg as bit)
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
	if(@sess = 1) set @selectClause = @selectClause + 's.IdSesja as SessionID, s.IdLaboratorium as LabID, dbo.motion_kind_name(s.IdRodzaj_ruchu) as MotionKind,	s.Data as SessionDate,	s.Nazwa as SessionName, s.Tagi as Tags, s.Opis_sesji as SessionDescription ';
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
					when 'SessionDate' then 's.Data'
					when 'SessionName' then 's.Nazwa'
					when 'Tags' then 's.Tagi'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdObserwacja'
					when 'TrialName' then 't.Nazwa'
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


alter procedure evaluate_generic_query_uniform(@user_login as varchar(30), @filter as PredicateUdt readonly, @perf as bit, @sess as bit, @trial as bit, @mc as bit, @meas as bit, @pc as bit, @sg as bit)
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
					when 'SessionDate' then 's.Data'
					when 'SessionName' then 's.Nazwa'
					when 'Tags' then 's.Tagi'
					when 'SessionDescription' then 's.Opis_sesji'
					else '(select top 1 * from sess_attr_value(s.IdSesja, '+quotename(@currentFeatureName,'''')+'))' end				)
			when 'trial' then (
				case @currentFeatureName
					when 'TrialID' then	't.IdObserwacja'
					when 'TrialName' then 't.Nazwa'
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


alter procedure list_basket_sessions_attributes_xml (@user_login varchar(30), @basket_name varchar(30))
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

alter procedure list_basket_trials_attributes_xml(@user_login varchar(30), @basket_name varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
	IdObserwacja as TrialID, 
	IdSesja as SessionID, 
	Nazwa as TrialName,
	Opis_obserwacji as TrialDescription, 
	(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Obserwacja TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) 
	and IdObserwacja in 
	(select IdObserwacja from Obserwacja_Koszyk ok join Koszyk k on ok.IdKoszyk = k.IdKoszyk where k.Nazwa = @basket_name and k.IdUzytkownik = dbo.identify_user(@user_login) )
    for XML AUTO, ELEMENTS, root ('BasketTrialWithAttributesList')
go

/*
declare @sfn varchar(30);
set @sfn = '2010-10-10-P12-S01-T03.c3d';
select SUBSTRING (@sfn, 1, CHARINDEX ('-S', @sfn )-1 )

use Motion;
go


declare @files as FileNameListUdt;
insert into @files values ('2011-02-02kkkk.zip');
insert into @files values ('2011-02-02kkkk-trial1.c3d');
insert into @files values ('2011-02-02kkkk-trial1.cam1.avi');
insert into @files values ('2011-02-02kkkk-trial1.cam2.avi');
insert into @files values ('2011-02-02kkkk-trial1.cam3.avi');
insert into @files values ('2011-02-02kkkk-trial1.cam4.avi');
exec validate_file_list_xml @files

declare @res as int;
declare @files as FileNameListUdt;
insert into @files values ('2000-02-02-P01-S01.zip');
insert into @files values ('2000-02-02-P01-S01-T01.cam1.avi');
insert into @files values ('2000-02-02-P01-S01-T01.cam2.avi');
insert into @files values ('2000-02-02-P01-S01-T01.cam3.avi');
insert into @files values ('2000-02-02-P01-S01-T01.cam4.avi');
insert into @files values ('2000-02-02-P01-S01-T01.c3d');
exec create_session_from_file_list 'habela', @files, @res output;

delete from Sesja where IdSesja > 74
*/



drop type FileNameListUdt

create type FileNameListUdt as table
(
	Name varchar(255)
)
go



alter procedure validate_file_list_xml (  @files as FileNameListUdt readonly )
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
	  if exists( select * from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)		
		)
		insert into @errorList select ('Trial '+tname+' does not meet the requirement of having 4 .avi files AND 1 .c3d file') 
		from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)	

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



alter procedure create_session_from_file_list ( @user_login as varchar(30), @files as FileNameListUdt readonly, @result int output )
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
	  set @sessionDate = CAST ( SUBSTRING(@sessionName,1,10) as DateTime);
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
	  if exists( select * from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)		
		)
		begin
			set @result = 1;
			return;
		end;
	

	
	exec create_session  @user_login, 1, 'walk', @sessionDate, @sessionName, '', '', @sessionId OUTPUT, @res OUTPUT; 
	
	if (@result<>0) 
		begin
			set @result = 1;
			return;
		end;
	-- po przetestowaniu zamien wykomentowania gora-dol
	-- set @sessionId = 1;
									
	insert @fileStoreList ( fname, entity, resid ) select Name, 'session', @sessionId from @files f where ( CHARINDEX ('-T', f.Name)=0 );
	
	select @trialsToCreate = COUNT(*) from @trialNames;
	
	while @trialsToCreate > 0
		begin
			select top(1) @trialName = tn.tname from @trialNames tn where not exists ( select * from @fileStoreList fsl where ( CHARINDEX(tn.tname,fsl.fname)>0  ));

			
			insert into Obserwacja ( IdSesja, Opis_obserwacji, Nazwa) values (@sessionId, '', @trialName ) set @trialId = SCOPE_IDENTITY();
			-- po przetestowaniu zamien wykomentowania gora-dol
			-- set @trialId = @trialsToCreate;
            insert @fileStoreList ( fname, entity, resid ) select Name, 'trial', @trialId from @files f where ( CHARINDEX (@trialName, f.Name)>0 );
			set @trialsToCreate = @trialsToCreate-1;						
		end;

	select * from @fileStoreList;

				
go





