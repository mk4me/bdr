use Motion;
go


alter procedure get_session_content_xml ( @user_login as varchar(30), @sess_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdSesja as SessionID,
				IdUzytkownik as UserID,
				IdLaboratorium as LabID,
				dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
				Data as SessionDate,
				Opis_sesji as SessionDescription,
				(select * from session_label(@user_login, IdSesja)) as SessionLabel,
				(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
				(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
					(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
					from Plik p where p.IdSesja=SessionContent.IdSesja
					for XML PATH('FileDetailsWithAttributes'), TYPE
				) as FileWithAttributesList,
				(select 
					IdObserwacja as TrialID,
					Opis_obserwacji as TrialDescription,
					(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
					(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
						(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
						from Plik p 
						where 
						p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes'), TYPE
					) as FileWithAttributesList
					from Obserwacja TrialContent where TrialContent.IdSesja = SessionContent.IdSesja FOR XML AUTO, ELEMENTS, TYPE 
				) as TrialContentList
				from user_accessible_sessions_by_login(@user_login) SessionContent where IdSesja=@sess_id
				for XML AUTO, ELEMENTS
go


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
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdSesja=SessionContent.IdSesja
	for XML PATH('FileDetailsWithAttributes'), TYPE) as FileWithAttributesList,
	(select 
		IdObserwacja as TrialID,
		Opis_obserwacji as TrialDescription,
		(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
		from Plik p 
		where 
		p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes'), TYPE) as FileWithAttributesList
	from Obserwacja TrialContent where TrialContent.IdSesja = SessionContent.IdSesja FOR XML AUTO, ELEMENTS, TYPE ) as TrialContentList
	from user_accessible_sessions_by_login(@user_login) SessionContent
      for XML AUTO, ELEMENTS, root ('SessionContentList')
go


-- last rev: 2010-11-16
alter function list_performer_attributes ( @perf_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
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
	'performer' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id 
go

-- last rev: 2010-11-16
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
		when 'integer' then (
			case a.Podtyp_danych when 'nonNegativeDecimal'	then cast (cast ( wap.Wartosc_liczba as numeric(10,2) ) as SQL_VARIANT)
			else cast (cast ( wap.Wartosc_liczba as int ) as SQL_VARIANT) end
		)
		when 'file' then cast (wap.Wartosc_Id  as SQL_VARIANT)
		else cast ( wap.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
	a.Typ_danych as Type,
	ga.Nazwa as AttributeGroup,
	'performer' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_performera wap on a.IdAtrybut=wap.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wap.IdPerformer = @perf_id  ));
go

-- last rev: 20101116
alter function list_session_attributes ( @sess_id int )
returns TABLE as
return 
select 
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
where was.IdSesja = @sess_id
go

-- last rev: 20101116
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
	(select 'MotionKind' as Name,
			dbo.motion_kind_name(s.IdRodzaj_ruchu) as Value,
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

-- last rev: 20101116
alter function list_trial_attributes ( @trial_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
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
where wao.IdObserwacja = @trial_id 
go

-- last rev: 20101116
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

-- last rev: 20101116
alter function list_measurement_configuration_attributes ( @mc_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
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
where wakp.IdKonfiguracja_pomiarowa = @mc_id 
go

-- last rev: 20101116
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

-- last rev: 20101116
alter function list_measurement_attributes ( @meas_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
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
where wap.IdPomiar = @meas_id
go

-- last rev: 20101116
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
			'_measurement_static' as AttributeGroup,
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


