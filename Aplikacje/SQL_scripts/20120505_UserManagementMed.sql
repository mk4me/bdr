use Motion_Med;
go


-- last rev. 2012-05-04
-- Error codes:
-- 1 authentication negative
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
-- 4 hmdb account activation requested but account missing
alter procedure update_user_account(@user_login varchar(30), @user_password varchar(20),  @user_new_password varchar(20), @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @hmdb_propagate bit, @result int OUTPUT)
as
begin

	set @result = 0;

	if ( LEN(@user_login)=0 or LEN(@user_password)=0 )
		begin
			set @result = 3;
			return;
		end;

	if not exists(select * from Uzytkownik where Login = @user_login and Haslo = HashBytes('SHA1',@user_password) and Status > 0 )
		begin
			set @result = 1;
			return;
		end;
	if exists(select * from Uzytkownik where Login != @user_login and Email = @user_email)
		begin
			set @result = 2;
			return;
		end;

	if @hmdb_propagate = 1 and not exists(select * from Motion.dbo.Uzytkownik where Login = @user_login )
		begin
			set @result = 4;
			return;
		end;

	if ( @user_first_name != '-nochange-' )
	begin
		update Uzytkownik 
		set Email = @user_email, Imie = @user_first_name, Nazwisko = @user_last_name where Login = @user_login;
		if @hmdb_propagate = 1
		begin
		update Motion.dbo.Uzytkownik 
		set Email = @user_email, Imie = @user_first_name, Nazwisko = @user_last_name where Login = @user_login;

		end;		
	end;	
	if ( @user_new_password != '-nochange-' )
	begin
		update Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password) where Login = @user_login;
		if @hmdb_propagate = 1
		begin
			update Motion.dbo.Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password) where Login = @user_login;
		end;
	end;
	
	return @result;
end;
go
