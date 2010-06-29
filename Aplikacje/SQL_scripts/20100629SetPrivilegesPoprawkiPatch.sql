use Motion;
go
alter procedure set_session_privileges (@granting_user_login varchar(30), @granted_user_login varchar(30), @sess_id int, @write bit)
as
begin
	declare @granted_user int;
	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	begin
		set @granted_user = dbo.identify_user(@granted_user_login);
		if @granted_user is NULL 
			begin
				RAISERROR ('Granted user does not exist', 12, 1 )
				return;
			end
		else
		update Uprawnienia_sesja set Zapis = @write where IdSesja = @sess_id and IdUzytkownik = @granted_user;
		if @@ROWCOUNT = 0
		insert into Uprawnienia_sesja ( IdSesja, IdUzytkownik, Zapis) values (@sess_id, @granted_user, @write);
	end
end
go
