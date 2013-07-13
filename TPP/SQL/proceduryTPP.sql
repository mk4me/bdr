use TPP;
go


-- PRIVILEGE AND USER MANAGEMENT
-- last rev. 
create procedure list_users_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/TPPDB/AuthorizationService')
select Login "@Login", Imie "@FirstName", Nazwisko "@LastName"
	from Uzytkownik
    for XML PATH('UserDetails'), root ('UserList')
go

-- last rev. 
create procedure get_user ( @user_login varchar(30) )
as
select Login, Imie, Nazwisko, Email
	from Uzytkownik
	where Login = @user_login
go


-- last rev. 
create procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass) and Status > 0;
if (@c = 1) set @res = 1; else set @res = 0;
end;
go



-- last rev. 2012-03-29
-- Error codes:
-- 1 login already in use
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
create procedure create_user_account(@user_login varchar(30), @user_password varchar(20),  @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @activation_code varchar(10), @result int OUTPUT)
as
begin

	declare @email_title as varchar (120);
	declare @email_body as varchar (500);
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

	insert into Uzytkownik ( Login, Haslo, Email, Imie, Nazwisko, Kod_aktywacji ) values ( @user_login, HashBytes('SHA1',@user_password), @user_email, @user_first_name, @user_last_name, @activation_code );

	set @email_title = 'TPP Database account activation for ' + @user_login;
	set @email_body = 'Your account with login '+@user_login+' has been created successfully.'+CHAR(13)
	+' To activate your account use the following link: ...AccountActivation.aspx?login='+@user_login+'&code='+@activation_code+CHAR(13)
	+'Alternatively, use activation code for login '+@user_login +' and activation code ' + @activation_code +' to perform the activation manually '
	+CHAR(13)+'on the webpage https://v21.pjwstk.edu.pl/HMDB/UserAccountCreation.aspx or through your client application.';

	exec msdb.dbo.sp_send_dbmail @profile_name='HMDB_Mail',
	@recipients=@user_email,
	@subject= @email_title,
	@body= @email_body;
	return 0;
end;
go

-- last rev. 2012-03-29
-- Error codes:
-- 1 authentication negative
create procedure activate_user_account(@user_login varchar(30), @activation_code varchar(10), @result int OUTPUT)
as
begin
	set @result = 0;

	if not exists(select * from Uzytkownik where Login = @user_login and Kod_aktywacji = @activation_code )
		begin
			set @result = 1;
			return;
		end;
	update Uzytkownik  set Status = 1  where Login = @user_login and Kod_aktywacji = @activation_code;

	return 0;
end;
go

-- last rev. 2012-12-23
-- Error codes:
-- 1 email not found
-- 2 account not yet activated
-- 3 obligatory parameter empty (length 0)
-- 4 HMDB propagate requested but account of this email missing
create procedure forgot_password(@user_email varchar(50), @activation_code varchar(20), @result int OUTPUT)
as
begin

	declare @email_title as varchar (120);
	declare @email_body as varchar (500);
	declare @link_command as varchar(30);
	declare @user_login as varchar(30);
	
	set @link_command = '';
	set @result = 0;

	if ( LEN(@user_email)=0 )
		begin
			set @result = 3;
			return;
		end;

	if not exists(select * from Uzytkownik where Email = @user_email)
		begin
			set @result = 1;
			return;
		end;
	if exists(select * from Uzytkownik where Email = @user_email and Status = 0)
		begin
			set @result = 2;
			return;
		end;

		
	select @user_login = Login from Uzytkownik where Email = @user_email;

	update Uzytkownik set Kod_aktywacji = @activation_code, Status = 3 where Email = @user_email;

	set @email_title = 'TPP Database password reset request confirmation';
	set @email_body = 'The password reset keycode for your account with login '+@user_login+' has been generated.'+CHAR(13)
	+'To set the new password authenticate using the login '+@user_login +' and the temporary password ' + @activation_code +' using '
	+CHAR(13)+'the webpage https://????/UserAccountUpdate.aspx .';

	exec msdb.dbo.sp_send_dbmail @profile_name='HMDB_Mail',
	@recipients=@user_email,
	@subject= @email_title,
	@body= @email_body;
	return 0;
end;
go


-- last rev. 
-- Error codes:
-- 1 authentication failed
-- 2 email already in use
-- 3 obligatory parameter empty (length 0)
create procedure update_user_account(@user_login varchar(30), @user_password varchar(20),  @user_new_password varchar(20), @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @result int OUTPUT)
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
			if exists(select * from Uzytkownik where Login= @user_login and Kod_Aktywacji = @user_password and Status = 3 )
				begin
					update Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password), Status = 1 where Login = @user_login;
					return;
				end
			else
				begin
					set @result = 1;
					return;
				end;
		end;
	if exists(select * from Uzytkownik where Login != @user_login and Email = @user_email)
		begin
			set @result = 2;
			return;
		end;

	if ( @user_first_name != '-nochange-' )
	begin
		update Uzytkownik 
		set Email = @user_email, Imie = @user_first_name, Nazwisko = @user_last_name where Login = @user_login;
	end;	
	if ( @user_new_password != '-nochange-' )
	begin
		update Uzytkownik set Haslo = HashBytes('SHA1',@user_new_password) where Login = @user_login;
	end;
	
	return @result;
end;
go


-- last rev. 
create procedure reset_password(@login varchar(30), @old varchar(25), @new varchar(25), @res int OUTPUT )
as
begin
if not exists(select * from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@old))
	begin
		set @res = 1;
		return;
	end;
update Uzytkownik set Haslo = HashBytes('SHA1',@new) where Login = @login and Haslo = HashBytes('SHA1',@old);
return 0;
end;
go



-- last rev. 2013-05-28
create procedure zapisz_dane_badania(
	@NrPacjenta			int,
	@Wizyta  			tinyint,
	@Plec 				tinyint,
	@Wiek 				tinyint,
	@CzasTrwaniaChoroby tinyint,
	@WiekZachorowania 	tinyint,
	@ObjawyObecnieWyst 	tinyint,
	@Drzenie 			bit,
	@Sztywnosc 			bit,
	@Spowolnienie 		bit,
	@DyskinezyObecnie 	bit,
	@DyskinezyOdLat 		tinyint,
	@FluktuacjeObecnie 	bit,
	@FluktuacjeOdLat 	tinyint,
	@CzasOFF 			tinyint,
	@CzasDyskinez 		tinyint,
	@PoprawaPoLDopie 	bit,
	@LDopaObecnie 		int,
	@AgonistaObecnie 	int,
	@LekiInne tinyint,
	--
	@komunikat varchar(200) OUTPUT,
	@result int OUTPUT )
as
begin
	/* Error codes:
		1 = blad walidacji danych - zob. parametr komunikat
		3 = badanie juz istnieje w bazie
		9 = problem z zarejestrowaniem pacjenta
	*/	


	declare @id_pacjenta int;
	set @id_pacjenta = 0;

	set @result = 0;


	/* Wprowadz do Pacjent nowy wiersz pacjenta, jesli brak */
	if( not exists ( select * from Pacjent where NumerPacjenta = @NrPacjenta )) insert into Pacjent ( NumerPacjenta ) values ( @NrPacjenta );

	/* Ustal wewnetrzny, bazodanowy identifikator pacjenta */
	select @id_pacjenta = IdPacjent from Pacjent where NumerPacjenta = @NrPacjenta;

	if ( @id_pacjenta = 0 )
	if ( exists (select * from Badanie where IdPacjent = @id_pacjenta and Wizyta = @Wizyta ) )
		begin
			set @result = 9;
			return;
		end;

	/* Sprawdz unikalnosc wprowadzanych danych */

	if ( exists (select * from Badanie where IdPacjent = @id_pacjenta and Wizyta = @Wizyta ) )
		begin
			set @komunikat = 'Badanie rodzaju '+ cast ( @Wizyta as varchar )+' pacjenta o numerze '+ cast ( @NrPacjenta as varchar )+' ju¿ istnieje w bazie.';
			set @result = 3;
			return;
		end;

	/* Dokonaj walidacji wartosci antrybutow */
	/* To Do */
	
	/* Wprowadz do Badanie wiersz danych */
	insert into Badanie ( IdPacjent, Wizyta, Plec, Wiek, CzasTrwaniaChoroby, WiekZachorowania, ObjawyObecnieWyst, Drzenie,	Sztywnosc, Spowolnienie, DyskinezyObecnie, DyskinezyOdLat, FluktuacjeObecnie, FluktuacjeOdLat, CzasOFF, CzasDyskinez, PoprawaPoLDopie, LDopaObecnie, AgonistaObecnie, LekiInne )
	values ( @id_pacjenta, @Wizyta, @Plec, @Wiek, @CzasTrwaniaChoroby, @WiekZachorowania, @ObjawyObecnieWyst, @Drzenie,	@Sztywnosc, @Spowolnienie, @DyskinezyObecnie, @DyskinezyOdLat, @FluktuacjeObecnie, @FluktuacjeOdLat, @CzasOFF, @CzasDyskinez, @PoprawaPoLDopie, @LDopaObecnie, @AgonistaObecnie, @LekiInne );

	return;
	
end;