use Motion;
go


create procedure get_session_content_xml ( @user_login as varchar(30), @sess_id int )
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
					for XML PATH('FileDetailsWithAttributes')
				) as FileWithAttributesList,
				(select 
					IdObserwacja as TrialID,
					Opis_obserwacji as TrialDescription,
					(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
					(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
						(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
						from Plik p 
						where 
						p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes')
					) as FileWithAttributesList
					from Obserwacja TrialContent where TrialContent.IdSesja = SessionContent.IdSesja FOR XML AUTO, ELEMENTS, TYPE 
				) as TrialContentList
				from user_accessible_sessions_by_login(@user_login) SessionContent where IdSesja=@sess_id
				for XML AUTO, ELEMENTS
go


create procedure list_session_contents_xml (@user_login varchar(30), @page_size int, @page_no int)
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
	from Plik p where p.IdSesja=SessionDetailsWithAttributes.IdSesja
	for XML PATH('FileDetailsWithAttributes')) as FileWithAttributesList,
	(select 
		IdObserwacja as TrialID,
		Opis_obserwacji as TrialDescription,
		(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
		from Plik p 
		where 
		p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes')) as FileWithAttributesList
	from Obserwacja TrialContent where TrialContent.IdSesja = SessionDetailsWithAttributes.IdSesja FOR XML AUTO, ELEMENTS, TYPE ) as TrialContentList
	from user_accessible_sessions_by_login(@user_login) SessionDetailsWithAttributes
      for XML AUTO, ELEMENTS, root ('SessionContentList')
go



