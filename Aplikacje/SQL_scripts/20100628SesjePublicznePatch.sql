use Motion;

alter table Sesja
	add Publiczna bit not null default 1
go

alter function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.* from Sesja s where s.Publiczna = 1)
union
(select s.* from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.* from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
go

create procedure unset_session_privileges (@granting_user_login varchar(30), @granted_user_login varchar(30), @sess_id int)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	delete from Uprawnienia_sesja  where IdUzytkownik = dbo.identify_user(@granted_user_login) and IdSesja = @sess_id;
end
go
