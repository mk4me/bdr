use Motion;
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




