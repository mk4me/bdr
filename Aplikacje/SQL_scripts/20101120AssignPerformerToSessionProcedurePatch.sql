use Motion;
go

create function user_updateable_sessions( @user_id int )
returns table
as
return
(select s.* from Sesja s where (s.Publiczna = 1 and s.PublicznaZapis = 1) or dbo.is_superuser(@user_id)=1)
union
(select s.* from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.* from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id and us.Zapis = 1 )
go

/*
	1 - session not visible for this user
	2 - performer not found
	9 - user login not recognized
*/

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

