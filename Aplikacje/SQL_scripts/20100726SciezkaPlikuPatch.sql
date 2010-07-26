use Motion;
go
alter table Plik add Sciezka varchar(100) null
go
alter table Plik alter column Nazwa_pliku varchar(100) null
go
alter procedure list_performer_files_xml @perf_id int
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription, Sciezka as SubdirPath from Plik FileDetails where IdPerformer=@perf_id
	for XML AUTO, root ('FileList')
go

alter procedure list_session_files_xml(@user_login varchar(30), @sess_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription, Sciezka as SubdirPath from Plik FileDetails where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
	for XML AUTO, root ('FileList')
go

alter procedure list_trial_files_xml(@user_login varchar(30), @trial_id int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select IdPlik as FileID, Nazwa_pliku as FileName, Opis_pliku as FileDescription, Sciezka as SubdirPath from Plik FileDetails where 
	((select top 1 IdSesja from Obserwacja where IdObserwacja = @trial_id) in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdObserwacja=@trial_id
	for XML AUTO, root ('FileList')
go