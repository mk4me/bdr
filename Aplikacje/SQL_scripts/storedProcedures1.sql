use Motion;
go
-- non-XML queries
-- ==================================

-- latest revision: 2010-11-27
create procedure list_performer_sessions @perf_id int
as
	select s.IdSesja as SessionID, s.IdUzytkownik as UserID, s.IdLaboratorium as LabID, 
      s.IdRodzaj_ruchu as MotionKindID, s.Data as SessionDate, s.Nazwa as SessionName, s.Tagi as Tags, s.Opis_sesji as SessionDescription  
      from Konfiguracja_performera kp join Sesja s on kp.IdSesja = s.IdSesja 
      where kp.IdPerformer=@perf_id
go



-- Authorization-related behavior
-- ===============================

-- last rev: 2010-11-27
create function user_sessions_by_user_id( @user_id int)
returns table
as
return
select IdSesja, IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, Data, Nazwa, Tagi, Opis_sesji, Publiczna, PublicznaZapis from Sesja where IdUzytkownik = @user_id
go

-- last rev: 2010-11-27
create function user_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Uzytkownik u inner join Sesja s on u.IdUzytkownik = s.IdUzytkownik where u.Login = @user_login 
go


-- last rev: 2010-07-10
create function identify_user( @user_login varchar(30) )
returns int
as
begin
return ( select top 1 IdUzytkownik from Uzytkownik where Login = @user_login );
end
go

-- last rev: 2010-07-10
create function is_superuser( @user_id int )
returns bit
as
begin
return ( select count(*) from Uzytkownik where IdUzytkownik = @user_id and Login = 'motion_admin'   );
end
go

-- last rev: 2010-07-10
create function is_login_superuser ( @user_login varchar(30) )
returns bit
begin
 return case @user_login when 'motion_admin' then 1 else 0 end;
end
go

-- last rev: 2010-11-27
create function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s where s.Publiczna = 1 or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
go

-- last rev: 2010-11-27
create function user_accessible_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from user_accessible_sessions( dbo.identify_user( @user_login )) s
go

-- last rev. 2010-12-06
create function user_updateable_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where (s.Publiczna = 1 and s.PublicznaZapis = 1) or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id and us.Zapis = 1 )
go


-- Resource attribute and label functions
-- ======================================


-- created: 2010-10-20
create function motion_kind_name( @mk_id int )
returns varchar(50)
as
begin
	return ( select top 1 Nazwa from Rodzaj_ruchu where IdRodzaj_ruchu = @mk_id );
end
go

-- last rev: 20101127
create function session_label( @user_login varchar(30), @sess_id int )
returns TABLE as
return
select l.Nazwa+':'+s.Nazwa as SessionLabel
from user_accessible_sessions_by_login(@user_login) s inner join Laboratorium l on s.IdLaboratorium = l.IdLaboratorium where s.IdSesja = @sess_id
go	


-- last rev: 2010-11-16
create function list_performer_attributes ( @perf_id int )
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

-- last rev: 2011-07-10
create function list_performer_attributes_uniform ( @perf_id int )
returns TABLE as
return 
(
 with Perf as
( select * from Performer where IdPerformer=@perf_id )

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
where wap.IdPerformer = @perf_id  );
go

-- last rev: 20101116
create function list_session_attributes ( @sess_id int )
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

-- last rev: 20101127
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

-- last rev. 2010-10-18
create function list_performer_configuration_attributes ( @pc_id int )
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
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'performer_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_performera wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_performera = @pc_id and a.Typ_danych <> 'file'
go



-- last rev: 20101116
create function list_trial_attributes ( @trial_id int )
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
inner join Wartosc_atrybutu_proby wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdProba = @trial_id 
go

-- last rev: 2010-11-27
create function list_trial_attributes_uniform ( @trial_id int )
returns TABLE as
return 
(
 with Trial as
( select * from Proba where IdProba=@trial_id )
	(
	(select 'TrialID' as Name,
			t.IdProba as Value,
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
			t.Opis_proby as Value,
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
inner join Wartosc_atrybutu_proby wao on a.IdAtrybut=wao.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wao.IdProba = @trial_id ));
go

-- last rev: 2010-11-16
create function list_measurement_configuration_attributes ( @mc_id int )
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

-- last rev: 2010-11-27
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




-- Resource By-ID retrieval
-- ========================

-- last rev: 2011-08-05
-- deprecated
create procedure get_performer_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdPerformer as PerformerID,
				(select * from list_performer_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE) as Attributes
			from Performer PerformerDetailsWithAttributes where IdPerformer = @res_id
			for XML AUTO, ELEMENTS
go

-- last rev: 2010-11-27
create procedure get_session_by_id_xml ( @user_login as varchar(30), @res_id int )
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

-- last rev: 2010-11-27
create procedure get_trial_by_id_xml ( @res_id int ) 
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdProba as TrialID, 
				IdSesja as SessionID,
				Nazwa as TrialName,
				Opis_proby as TrialDescription, 
				(select * from list_trial_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Proba TrialDetailsWithAttributes where IdProba=@res_id
			for XML AUTO, ELEMENTS
go


-- last rev: 20101013
create procedure get_measurement_configuration_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdKonfiguracja_pomiarowa as MeasurementConfID,
				Nazwa as MeasurementConfName,
				Rodzaj as MeasurementConfKind,
				Opis as MeasurementConfDescription,
				(select * from list_measurement_configuration_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Konfiguracja_pomiarowa MeasurementConfDetailsWithAttributes where IdKonfiguracja_pomiarowa=@res_id
			for XML AUTO, ELEMENTS
go

-- last rev: 20101013
create procedure get_performer_configuration_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdKonfiguracja_performera as PerformerConfID,
				IdSesja as SessionID,
				IdPerformer as PerformerID,
				(select * from list_performer_configuration_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Konfiguracja_performera PerformerConfDetailsWithAttributes where IdKonfiguracja_performera=@res_id
			for XML AUTO, ELEMENTS
go

-- last rev: 2010-12-06
create procedure get_file_data_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdPlik as FileID,
				Nazwa_pliku as FileName,
				Opis_pliku as FileDescription,
				Sciezka SubdirPath,
				(select * from list_file_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE) as Attributes
			from Plik FileDetailsWithAttributes where IdPlik = @res_id
			for XML AUTO, ELEMENTS
go

-- last rev: 20101127
create procedure get_session_content_xml ( @user_login as varchar(30), @sess_id int )
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
					IdProba "TrialDetailsWithAttributes/TrialID",
					Nazwa "TrialDetailsWithAttributes/TrialName",
					Opis_proby "TrialDetailsWithAttributes/TrialDescription",
					(select * from list_trial_attributes ( IdProba ) Attribute FOR XML AUTO, TYPE ) "TrialDetailsWithAttributes/Attributes",
					(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
						(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
						from Plik p 
						where 
						p.IdProba=TrialContent.IdProba for XML PATH('FileDetailsWithAttributes'), TYPE
					) as FileWithAttributesList
				from Proba TrialContent where TrialContent.IdSesja = sc.IdSesja FOR XML PATH('TrialContent'), ELEMENTS, TYPE 
				) as TrialContentList
				from user_accessible_sessions_by_login(@user_login) sc where sc.IdSesja=@sess_id
				for XML Path('SessionContent'), ELEMENTS
go


-- Performer listing queries
-- =========================

-- last rev: 2011-08-05
create procedure list_performers_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdPerformer as PerformerID
	from Performer PerformerDetails
    for XML AUTO, root ('PerformerList')
go

-- last rev: 2011-08-05
create procedure list_performers_attributes_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
    for XML AUTO, ELEMENTS, root ('PerformerWithAttributesList')
go

-- last rev: 2011-07-12
create procedure list_session_performers_attributes_xml (@user_login varchar(30), @sess_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where exists ( select * from user_accessible_sessions_by_login (@user_login) where IdSesja = @sess_id) 
	and exists ( select * from Konfiguracja_performera where IdSesja = @sess_id and IdPerformer = PerformerDetailsWithAttributes.IdPerformer )
    for XML AUTO, ELEMENTS, root ('SessionPerformerWithAttributesList')
go

-- last rev: 2011-07-12
create procedure list_lab_performers_attributes_xml (@user_login varchar(30), @lab_id int)  -- Mozna pytac o przypisania na poziomie sesji
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdPerformer as PerformerID,
	(select * from list_performer_attributes ( IdPerformer ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Performer PerformerDetailsWithAttributes
	where exists(
	select * from user_accessible_sessions_by_login(@user_login) s join Konfiguracja_performera kp on s.IdSesja = kp.IdSesja 
	where s.IdLaboratorium = @lab_id)
    for XML AUTO, ELEMENTS, root ('LabPerformerWithAttributesList')
go



-- Session listing queries
-- =======================

-- last rev: 2010-11-27
create procedure list_performer_sessions_xml (@user_login varchar(30), @perf_id int) -- mozna uproscic do wykorzystujacej PerformerConfiguration
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
create procedure list_performer_sessions_attributes_xml (@user_login varchar(30), @perf_id int)
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
create procedure list_lab_sessions_attributes_xml (@user_login varchar(30), @lab_id int)
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
create procedure list_group_sessions_attributes_xml (@user_login varchar(30), @group_id int)
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

-- last rev: 2011-07-12
create procedure list_measurement_conf_sessions_attributes_xml (@user_login varchar(30), @mc_id int)
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
			select * from Sesja_Konfiguracja_pomiarowa skp
			where skp.IdKonfiguracja_pomiarowa = @mc_id and skp.IdSesja = SessionDetailsWithAttributes.IdSesja
		)
      for XML AUTO, ELEMENTS, root ('MeasurementConfSessionWithAttributesList')
go

-- last rev: 2010-12-21
create procedure list_session_contents_xml (@user_login varchar(30), @page_size int, @page_no int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		-- top @page_size ...
		IdSesja "SessionDetailsWithAttributes/SessionID",
		IdUzytkownik "SessionDetailsWithAttributes/UserID",
		IdLaboratorium "SessionDetailsWithAttributes/LabID",
		dbo.motion_kind_name(IdRodzaj_ruchu) "SessionDetailsWithAttributes/MotionKind",
		Data "SessionDetailsWithAttributes/SessionDate",
		Nazwa "SessionDetailsWithAttributes/SessionName",
		Tagi "SessionDetailsWithAttributes/Tags", 
		Opis_sesji "SessionDetailsWithAttributes/SessionDescription",
		(select * from session_label(@user_login, IdSesja)) "SessionDetailsWithAttributes/SessionLabel",
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) "SessionDetailsWithAttributes/Attributes",
		(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdSesja=SessionContent.IdSesja
	for XML PATH('FileDetailsWithAttributes'), TYPE) as FileWithAttributesList,
	(select 
		IdProba "TrialDetailsWithAttributes/TrialID",
		Nazwa "TrialDetailsWithAttributes/TrialName",
		Opis_proby "TrialDetailsWithAttributes/TrialDescription",
		(select * from list_trial_attributes ( IdProba ) Attribute FOR XML AUTO, TYPE ) "TrialDetailsWithAttributes/Attributes",
		(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
		from Plik p 
		where 
		p.IdProba=TrialContent.IdProba for XML PATH('FileDetailsWithAttributes'), TYPE) as FileWithAttributesList
	from Proba TrialContent where TrialContent.IdSesja = SessionContent.IdSesja FOR XML PATH('TrialContent'), ELEMENTS, TYPE, ROOT('TrialContentList') ) 
	from user_accessible_sessions_by_login(@user_login) SessionContent
      for XML PATH ('SessionContent'), ELEMENTS, ROOT('SessionContentList')
go

-- Session group listing
-- ===============================

-- last rev: 20100102
create procedure list_session_session_groups_xml (@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdGrupa_sesji as SessionGroupID, Nazwa as SessionGroupName from Grupa_sesji SessionGroupDefinition
	where (@sess_id in ( select IdSesja from user_accessible_sessions_by_login(@user_login) ))
	and SessionGroupDefinition.IdGrupa_sesji in 
	( select sgs.IdGrupa_sesji from Sesja_grupa_sesji sgs where IdSesja = @sess_id)
	for XML AUTO, ELEMENTS, root ('SessionSessionGroupList')
go

-- last rev: 2010-07-10
create procedure list_session_groups_defined
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdGrupa_sesji as SessionGroupID, Nazwa as SessionGroupName from Grupa_sesji
for XML RAW ('SessionGroupDefinition'), ELEMENTS, root ('SessionGroupDefinitionList')
go

-- Trial listing queries
-- =====================

-- last rev: 2010-11-27
create procedure list_session_trials_xml(@user_login varchar(30),  @sess_id int )  -- !!! Suspended from production update
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdProba as TrialID,
	IdSesja as SessionID,
	Nazwa as TrialName,
	Opis_proby as TrialDescription
from Proba TrialDetails where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
      for XML AUTO, root ('SessionTrialList')
go

-- last rev: 2010-11-27
create procedure list_session_trials_attributes_xml(@user_login varchar(30),   @sess_id int)  -- !!! Suspended from production update
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdProba as TrialID, 
	IdSesja as SessionID, 
	Nazwa as TrialName,
	Opis_proby as TrialDescription, 
	(select * from list_trial_attributes ( IdProba ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Proba TrialDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
    for XML AUTO, ELEMENTS, root ('SessionTrialWithAttributesList')
go

-- Measurement configuration listing queries
-- =========================================

-- last rev: 20100102
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

-- last rev: 20100102
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



-- File queries
-- ===================

-- last rev: 2010-07-16
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

-- last rev: 2010-07-26
create procedure list_session_files_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription, Sciezka as SubdirPath from Plik FileDetails where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
	for XML AUTO, root ('FileList')
go

-- last rev: 2010-07-26
create procedure list_trial_files_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription, Sciezka as SubdirPath from Plik FileDetails where 
	((select top 1 IdSesja from Proba where IdProba = @trial_id) in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdProba=@trial_id
	for XML AUTO, root ('FileList')
go

-- last rev: 2010-01-02
create procedure list_measurement_conf_files_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription"
	from Plik p where p.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetails'), root ('FileList')
go

-- last rev: 2010-01-02
create procedure list_session_attr_files_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_sesji was on p.IdPlik = was.Wartosc_Id join Atrybut a on a.IdAtrybut = was.IdAtrybut where was.IdSesja=@sess_id
	for XML PATH('FileDetails'), root ('FileList')
go

-- last rev: 2010-01-02
create procedure list_trial_attr_files_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_proby wao on p.IdPlik = wao.Wartosc_Id join Atrybut a on a.IdAtrybut = wao.IdAtrybut 
	where 
	((select top 1 IdSesja from Proba where IdProba = @trial_id) 
	in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and wao.IdProba=@trial_id
	for XML PATH('FileDetails'), root ('FileList')
go

-- last rev: 2010-01-02
create procedure list_measurement_conf_attr_files_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut where wakp.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetails'), root ('FileList')
go


-- last rev: 2010-01-02
create procedure list_performer_attr_files_attributes_xml(@user_login varchar(30),  @perf_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_performera wap on p.IdPlik = wap.Wartosc_Id join Atrybut a on a.IdAtrybut = wap.IdAtrybut where wap.IdPerformer=@perf_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

-- last rev: 2010-10-18
create procedure list_performer_conf_attr_files_xml(@user_login varchar(30),  @pc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName"
	from Plik p join Wartosc_atrybutu_konfiguracji_performera wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut
	where wakp.IdKonfiguracja_performera=@pc_id
	for XML PATH('FileDetails'), root ('FileList')
go

-- last rev: 2010-10-18
create procedure list_performer_conf_attr_files_attributes_xml(@user_login varchar(30),  @pc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_konfiguracji_performera wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut 
	where wakp.IdKonfiguracja_performera=@pc_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

-- last rev: 2010-07-10
create procedure list_session_files_attributes_xml(@user_login varchar(30),  @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
	IdPlik as FileID,
	Nazwa_pliku as FileName,
	Opis_pliku as FileDescription, 
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
	for XML AUTO, root ('FileWithAttributesList')
go

-- last rev: 2010-01-02
create procedure list_session_attr_files_attributes_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_sesji was on p.IdPlik = was.Wartosc_Id join Atrybut a on a.IdAtrybut = was.IdAtrybut where was.IdSesja=@sess_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

-- last rev: 2010-07-10
create procedure list_trial_files_attributes_xml(@user_login varchar(30),  @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		IdPlik as FileID,
		Nazwa_pliku as FileName,
		Opis_pliku as FileDescription,
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes 
	from Plik FileDetailsWithAttributes
		where ((select top 1 IdSesja from Proba where IdProba = @trial_id) in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdProba=@trial_id
	for XML AUTO, root ('FileWithAttributesList')
go

-- last rev: 2010-01-02
create procedure list_trial_attr_files_attributes_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_proby wao on p.IdPlik = wao.Wartosc_Id join Atrybut a on a.IdAtrybut = wao.IdAtrybut 
	where 
	((select top 1 IdSesja from Proba where IdProba = @trial_id) 
	in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and wao.IdProba=@trial_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

-- last rev: 2010-01-02
create procedure list_measurement_conf_files_attributes_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go

-- last rev: 2010-01-02
create procedure list_measurement_conf_attr_files_attributes_xml(@user_login varchar(30),  @mc_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath", a.Nazwa "@AttributeName",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p join Wartosc_atrybutu_konfiguracji_pomiarowej wakp on p.IdPlik = wakp.Wartosc_Id join Atrybut a on a.IdAtrybut = wakp.IdAtrybut where wakp.IdKonfiguracja_pomiarowa=@mc_id
	for XML PATH('FileDetailsWithAttributes'), root ('FileWithAttributesList')
go



-- Metadata queries
-- ================
-- last rev: 2010-11-03
create procedure list_attribute_groups_defined(@user_login varchar(30), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	ga.Nazwa as AttributeGroupName, ga.Opisywana_encja as DescribedEntity,
	(select wga.Wyswietlic from Widocznosc_grupy_atrybutow wga where wga.IdGrupa_atrybutow = ga.IdGrupa_atrybutow and wga.IdUzytkownik = dbo.identify_user(@user_login) ) as Show
from Grupa_atrybutow ga 
where (@entity_kind=Opisywana_encja or @entity_kind = '_ALL_ENTITIES' or @entity_kind = '_ALL')
for XML RAW ('AttributeGroupDefinition'), ELEMENTS, root ('AttributeGroupDefinitionList')
go


-- last rev: 2010-11-03
create procedure list_attributes_defined_with_enums( @user_login varchar(30), @att_group varchar(100), @entity_kind varchar(20) )
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	a.Nazwa as AttributeName, a.Podtyp_danych as AttributeType, a.Wyliczeniowy as AttributeEnum, 
	ga.Nazwa as AttributeGroupName, a.Jednostka as Unit, 
	(select wa.Wyswietlic from Widocznosc_atrybutu wa where wa.IdUzytkownik = dbo.identify_user(@user_login) and wa.IdAtrybut = a.IdAtrybut) as Show, 
	(select Wartosc_wyliczeniowa as Value from Wartosc_wyliczeniowa ww where ww.IdAtrybut = a.IdAtrybut for XML RAW(''), TYPE, ELEMENTS) as EnumValues
from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
where (@entity_kind=ga.Opisywana_encja or @entity_kind = '_ALL') and ( @att_group = '_ALL' or ga.Nazwa = @att_group )
for XML RAW ('AttributeDefinition'), ELEMENTS, root ('AttributeDefinitionList')
go


-- last rev: 2010-01-02
create procedure list_motion_kinds_defined
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select IdRodzaj_ruchu as MotionKindID, Nazwa as MotionKindName from Rodzaj_ruchu
for XML RAW ('MotionKindDefinition'), ELEMENTS, root ('MotionKindDefinitionList')
go



-- last rev: 2010-07-16
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


-- last rev: 2010-11-27
create procedure set_session_attribute (@sess_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin


	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
	*/
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

-- last rev: 2011-07-10
create procedure set_performer_attribute (@perf_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin

	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
	*/
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:


	*/

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
					if ( not exists ( select IdPlik from Plik where IdPlik = @id_value) )
					begin
						set @result = 7;
						return;
					end;
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

-- last rev: 2010-10-18
create procedure set_performer_conf_attribute (@pc_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin

	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
	*/

	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	

	*/

	set @result = 6; -- result 6 = type casting error
	/*
	if(@attr_name = 'MeasurementConfName' or @attr_name = 'MeasurementConfKind' or @attr_name = 'MeasurementConfDescription')
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'MeasurementConfName')
			update Konfiguracja_pomiarowa set Nazwa  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfKind')
			update Konfiguracja_pomiarowa set Opis  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfDescription')
			update Konfiguracja_pomiarowa set Rodzaj  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		set @result = 0;
		return;
	end;
	*/
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'performer_conf';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Konfiguracja_performera where IdKonfiguracja_performera = @pc_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_konfiguracji_performera where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_liczba) values (@attr_id, @pc_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_zmiennoprzecinkowa) values (@attr_id, @pc_id, @float_value);
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
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_Id) values (@attr_id, @pc_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_konfiguracji_performera set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdKonfiguracja_performera = @pc_id ;
					else
					insert into Wartosc_atrybutu_konfiguracji_performera (IdAtrybut, IdKonfiguracja_performera, Wartosc_tekst) values (@attr_id, @pc_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go


-- last rev. 2010-11-27
create procedure set_trial_attribute (@trial_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin


	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
	*/
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
			update Proba set Opis_proby  = @attr_value where IdProba = @trial_id;
		else if(@attr_name = 'TrialName')
					update Proba set Nazwa  =  @attr_value where IdSesja = @trial_id;

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
	if not exists ( select * from Proba where IdProba = @trial_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_proby where IdAtrybut = @attr_id and IdProba = @trial_id ) set @value_tuple_found = 1;
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
					update Wartosc_atrybutu_proby set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdProba = @trial_id ;
					else
					insert into Wartosc_atrybutu_proby (IdAtrybut, IdProba, Wartosc_liczba) values (@attr_id, @trial_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_proby set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdProba = @trial_id ;
					else
					insert into Wartosc_atrybutu_proby (IdAtrybut, IdProba, Wartosc_zmiennoprzecinkowa) values (@attr_id, @trial_id, @float_value);
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
					update Wartosc_atrybutu_proby set Wartosc_Id  = @id_value where IdAtrybut = @attr_id and IdProba = @trial_id ;
					else
					insert into Wartosc_atrybutu_proby (IdAtrybut, IdProba, Wartosc_Id) values (@attr_id, @trial_id, @id_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_proby set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdProba = @trial_id ;
					else
					insert into Wartosc_atrybutu_proby (IdAtrybut, IdProba, Wartosc_tekst) values (@attr_id, @trial_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go

-- last rev: 2010-12-29
create procedure set_file_attribute (@file_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin

	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
	*/
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
		'FileName', 'string', 0, 'shortString'
		'FileDescription', 'string', 0, 'longString'
		'SubdirPath', 'string', 0, 'shortString'
	*/

	set @result = 6; -- result 3 = type casting error

	if(@attr_name = 'FileName' or @attr_name = 'FileDescription' or @attr_name = 'SubdirPath' )
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'FileName')
			update Plik set Nazwa_pliku  = @attr_value where IdPlik = @file_id;
		else if(@attr_name = 'SubdirPath')
			update Plik set Sciezka  =  @attr_value where IdPlik = @file_id;
		else if(@attr_name = 'FileDescription')
			update Plik set Opis_pliku  = @attr_value where IdPlik = @file_id;
		set @result = 0;
		return;
	end;	
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

-- last rev: 2011-01-25
create procedure set_measurement_conf_attribute (@mc_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin

	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
	*/
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
	'MeasurementConfName', 'string', 0, 'shortString'
	'MeasurementConfKind', 'string', 0, 'shortString'
	'MeasurementConfDescription', 'string', 0, 'longString'

	*/

	set @result = 6; -- result 6 = type casting error

	if(@attr_name = 'MeasurementConfName' or @attr_name = 'MeasurementConfKind' or @attr_name = 'MeasurementConfDescription')
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'MeasurementConfName')
			update Konfiguracja_pomiarowa set Nazwa  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfKind')
			update Konfiguracja_pomiarowa set Rodzaj  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		if(@attr_name = 'MeasurementConfDescription')
			update Konfiguracja_pomiarowa set Opis  = @attr_value where IdKonfiguracja_pomiarowa = @mc_id;
		set @result = 0;
		return;
	end;
	
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
					if ( not exists ( select IdPlik from Plik where IdPlik = @id_value) )
					begin
						set @result = 7;
						return;
					end;
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


-- Attribute value removal
-- last rev: 2010-10-18
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
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_proby wao on a.IdAtrybut = wao.IdAtrybut
				where a.Nazwa = @attr_name and wao.IdProba = @res_id )
		begin
			set @result = 1;
			return;
		end;
	delete from wa from Wartosc_atrybutu_proby wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdProba = @res_id and a.Nazwa = @attr_name;
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
	else if (@entity = 'performer_conf')
	begin
		if not exists ( select * from Atrybut a join Wartosc_atrybutu_konfiguracji_performera wakp on a.IdAtrybut = wakp.IdAtrybut
				where a.Nazwa = @attr_name and wakp.IdKonfiguracja_performera = @res_id )
		begin
			set @result = 1;
			return;
		end;
		delete from wa from Wartosc_atrybutu_konfiguracji_performera wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut where wa.IdKonfiguracja_performera= @res_id and a.Nazwa = @attr_name;
	end;
end
go

-- Session creation
-- ----------------

-- last rev. 2011-07-13
create procedure create_session (	@sess_user varchar(20), @sess_lab int, @mk_name varchar(50), @sess_date Date, 
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

-- Assignment procedures
-- ---------------------

-- last rev. 2010-11-20
create procedure assign_performer_to_session (@user_login varchar(30), @sess_id int, @perf_id int, @perf_conf_id int OUTPUT, @result int OUTPUT )
as
begin
	declare @user_id as int
	
	set @result = 0;
	set @user_id = dbo.identify_user(@user_login);
	if (@user_id is NULL )
		begin
			set @result = 9;
			return;
		end;
	if not exists ( select * from dbo.user_updateable_sessions(@user_id) )
		begin
			set @result = 1;
			return;
		end;
	if not exists ( select * from Performer where IdPerformer = @perf_id )
		begin
			set @result = 2;
			return;
		end;
	select top(1) @perf_conf_id = pc.IdKonfiguracja_performera from Konfiguracja_performera pc where pc.IdPerformer = @perf_id and pc.IdSesja = @sess_id;
	if ( @perf_conf_id is NULL )
		begin 
		insert into Konfiguracja_performera ( IdSesja, IdPerformer) values (@sess_id, @perf_id )
                                  set @perf_conf_id = SCOPE_IDENTITY()
		end;                           
end;
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