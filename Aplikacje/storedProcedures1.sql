--alter procedure list_sessions @perf_id int
--as
--	select * from Sesja where IdPerformer=@perf_id

-- Execute sp_rename 'sp_Select_All_Employees', 'sp_Select_Employees_Details'

execute sp_helptext 'list_sessions'

execute list_sessions 1
	