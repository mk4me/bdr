use Motion;
go

alter procedure list_performer_sessions_xml (@user_login varchar(30), @perf_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdSesja as SessionID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, IdPerformer as PerformerID, Data as SessionDate, 
      Opis_sesji as SessionDescription, (select * from session_label(@user_login,IdSesja)) as SessionLabel
      from user_accessible_sessions_by_login(@user_login) SessionDetails where IdPerformer=@perf_id
      for XML AUTO, root ('PerformerSessionList')
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
	from Sesja SessionDetailsWithAttributes where IdPerformer=@perf_id
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
	from Sesja SessionDetailsWithAttributes where IdLaboratorium=@lab_id
      for XML AUTO, ELEMENTS, root ('LabSessionWithAttributesList')

go
