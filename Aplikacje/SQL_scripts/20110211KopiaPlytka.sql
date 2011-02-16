use Motion;
go

create procedure get_shallow_copy @user_login varchar(30)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB'),

UAS as (select * from dbo.user_accessible_sessions_by_login (@user_login) Session ),
UAGA as (select * from Sesja_grupa_sesji GroupAssignment where exists(select * from UAS where UAS.IdSesja = GroupAssignment.IdSesja)),
UAT as (select * from Obserwacja Trial where exists (select * from UAS where UAS.IdSesja = Trial.IdSesja)),
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
	(select Name, Value from list_session_attributes ( IdSesja ) A FOR XML AUTO, TYPE ) Attrs 
	from UAS Session for XML AUTO, TYPE
 ) Sessions,
 (select 
	IdSesja as SessionID,
	IdGrupa_sesji as SessionGroupID 
	from UAGA GroupAssignment for XML AUTO, TYPE
 ) GroupAssignments,
 (select 
	IdObserwacja as TrialID,
	IdSesja as SessionID,
	Nazwa as TrialName,
	Opis_obserwacji as TrialDescription,
	(select Name, Value from list_trial_attributes ( IdObserwacja ) A FOR XML AUTO, TYPE ) Attrs
	from UAT Trial FOR XML AUTO, TYPE 
 ) Trials,
 (select 
	IdPerformer as PerformerID,
	Imie as FirstName,
	Nazwisko as LastName,
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

-- TO DO: AttributeGroup, Attribute, AttributeEnumValue
	

create procedure get_metadata @user_login varchar(30)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB'),
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

exec get_metadata 'habela'