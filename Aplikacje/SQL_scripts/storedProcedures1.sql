use Motion;
alter procedure list_sessions @perf_id int
as
	select IdSesja as SessonID, IdUzytkownik as UserID, IdLaboratorium as LabID, 
      IdRodzaj_ruchu as MotionKindID, IdPerformer as PerformerID, Data as SessionDate, 
      Opis_sesji as SessionDescription  from Sesja where IdPerformer=@perf_id


create procedure list_session_files @sess_id int
as
	select IdPlik, Nazwa_pliku from Plik where IdSesja=@sess_id



Execute sp_rename 'sp_Select_All_Employees', 'sp_Select_Employees_Details'

execute sp_helptext 'list_sessions'

execute Motion.dbo.list_sessions 1
	
	
	
execute Motion.dbo.list_session_files 1