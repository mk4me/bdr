
use Motion_Med;
go


create table Konfiguracja (
		Klucz		varchar(20) NOT NULL UNIQUE,
		Wartosc		varchar(100) NOT NULL,
		Szczegoly	varchar(500)
)
go

CREATE INDEX X1Konfiguracja ON Konfiguracja
 (
         Klucz
 )
go
 



-- last rev. 2012-04
-- Error codes:
-- 1 authentication negative
-- 2 hmdb account activation requested but account missing
alter procedure activate_user_account(@user_login varchar(30), @activation_code varchar(10), @hmdb_propagate bit, @result int OUTPUT)
as
begin
	set @result = 0;

	if not exists(select * from Uzytkownik where Login = @user_login and Kod_aktywacji = @activation_code )
		begin
			set @result = 1;
			return;
		end;

	if @hmdb_propagate = 1 and not exists(select * from Motion.dbo.Uzytkownik where Login = @user_login )
		begin
			set @result = 2;
			return;
		end;
		
	
	update Uzytkownik  set Status = 1  where Login = @user_login and Kod_aktywacji = @activation_code;
	if @hmdb_propagate = 1
		begin
			update Motion.dbo.Uzytkownik  set Status = 1  where Login = @user_login and Kod_aktywacji = @activation_code;
		end;

	return 0;
end;
go


-- last rev. 2012-04-16
create procedure get_user ( @user_login varchar(30) )
as
select Login, Imie, Nazwisko, Email
	from Uzytkownik
	where Login = @user_login
go



-- last rev. 2012-04-16
-- Error codes:
-- 1 login already in use
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
-- 4/5 login or email already in use at HMDB
alter procedure create_user_account(@user_login varchar(30), @user_password varchar(20),  @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @activation_code varchar(10), @hmdb_propagate bit, @result int OUTPUT)
as
begin

	declare @email_title as varchar (120);
	declare @email_body as varchar (500);
	declare @link_command as varchar(30);
	
	set @link_command = '';
	set @result = 0;

	if ( LEN(@user_login)=0 or LEN(@user_password)=0 or LEN(@user_email) = 0 )
		begin
			set @result = 3;
			return;
		end;

	if exists(select * from Uzytkownik where Login = @user_login)
		begin
			set @result = 1;
			return;
		end;
	if exists(select * from Uzytkownik where Email = @user_email)
		begin
			set @result = 2;
			return;
		end;

	if @hmdb_propagate = 1 and exists(select * from Motion.dbo.Uzytkownik where Login = @user_login)
		begin
			set @result = 4;
			return;
		end;
	if @hmdb_propagate = 1 and exists(select * from Motion.dbo.Uzytkownik where Email = @user_email)
		begin
			set @result = 5;
			return;
		end;


	insert into Uzytkownik ( Login, Haslo, Email, Imie, Nazwisko, Kod_aktywacji ) values ( @user_login, HashBytes('SHA1',@user_password), @user_email, @user_first_name, @user_last_name, @activation_code );
	
	if @hmdb_propagate = 1
	begin
		insert into Motion.dbo.Uzytkownik ( Login, Haslo, Email, Imie, Nazwisko, Kod_aktywacji ) values ( @user_login, HashBytes('SHA1',@user_password), @user_email, @user_first_name, @user_last_name, @activation_code );
	end

	if @hmdb_propagate = 1
	begin
		set @link_command = '&hmdb=true';
	end;
	set @email_title = 'Human Motion MEDICAL Database account activation for ' + @user_login;
	set @email_body = 'Your account with login '+@user_login+' has been created successfully.'+CHAR(13)
	+' To activate your account use the following link: https://v21.pjwstk.edu.pl/HMDBMed/AccountActivation.aspx?login='+@user_login+'&code='+@activation_code+CHAR(13)+@link_command
	+'Alternatively, use activation code for login '+@user_login +' and activation code ' + @activation_code +' to perform the activation manually '
	+CHAR(13)+'on the webpage https://v21.pjwstk.edu.pl/HMDBMed/UserAccountCreation.aspx or through your client application.';

	exec msdb.dbo.sp_send_dbmail @profile_name='HMDB_Mail',
	@recipients=@user_email,
	@subject= @email_title,
	@body= @email_body;
	return 0;
end;
go



--declare @res int;
--exec create_user_account 'test1', 'Admin1', 'si@pjwstk.edu.pl', 'Test', 'Tester', 'qwerty', 1, @res OUTPUT;
--select @res;

--declare @res int;
--exec activate_user_account 'test1', 'qwerty', 1, @res;
--select @res;





-- delete from Motion.dbo.Uzytkownik where Login = 'test3'

select * from Motion.dbo.Uzytkownik

select * from Uzytkownik

--delete from Motion.dbo.Uzytkownik where IdUzytkownik > 21