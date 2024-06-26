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

create function identify_user( @user_login varchar(50) )
returns int
as
begin
	declare @user_id int;
	set @user_id = 0;
	select @user_id = IdUzytkownik from Uzytkownik where Login = @user_login;
	return @user_id;
end;
go


-- SLOWNIKOWANIE (listowanie opcji i walidacje)
-- ============================================

create function validate_input_int( @table_name varchar(30), @attr_name varchar(50), @value tinyint )
returns bit
as
begin
if @value IS NULL  return 1;
return ( select count(*) from SlownikInt where Tabela = @table_name and Atrybut = @attr_name and Klucz = @value   );
end
go


create procedure get_enumeration_int( @table_name varchar(30), @attr_name varchar(50) )
as
	select Klucz as Value, Definicja as Label  from SlownikInt where Tabela = @table_name and Atrybut = @attr_name
go

create function validate_input_decimal( @table_name varchar(30), @attr_name varchar(50), @value decimal(3,1) )
returns bit
as
begin
if @value IS NULL  return 1;
return ( select count(*) from SlownikDecimal where Tabela = @table_name and Atrybut = @attr_name and Klucz = @value   );
end
go

create procedure get_enumeration_decimal( @table_name varchar(30), @attr_name varchar(50) )
as
	select Klucz as Value, Definicja as Label  from SlownikDecimal where Tabela = @table_name and Atrybut = @attr_name
go

create function validate_input_varchar( @table_name varchar(30), @attr_name varchar(50), @value varchar(20) )
returns bit
as
begin
if @value IS NULL  return 1;
return ( select count(*) from SlownikVarchar where Tabela = @table_name and Atrybut = @attr_name and Klucz = @value   );
end
go

create procedure get_enumeration_varchar( @table_name varchar(30), @attr_name varchar(50) )
as
	select Klucz as Value, Definicja as Label  from SlownikVarchar where Tabela = @table_name and Atrybut = @attr_name
go


-- WGRYWANIE DANYCH I WALIDACJA
-- ============================

-- last rev. 2013-07-25
-- @result codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message
alter procedure update_patient  (	@NumerPacjenta	varchar(20), @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @allow_update_existing bit, @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @update bit;
	set @result = 0;
	set @update = 0;
	set @message = '';
	if(( select count(*) from Pacjent where NumerPacjenta = @NumerPacjenta )>0 )
	begin
		if ( @allow_update_existing = 0 )
		begin
			set @result = 1;
			return;
		end
		else set @update = 1;
	end;
	if( dbo.validate_ext_bit( @Plec) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Plec - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_year_month( @RokUrodzenia, @MiesiacUrodzenia) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'MiesiacUrodzenia / RokUrodzenia - invalid value.';
		return;
	end;

	if( dbo.validate_input_varchar('Pacjent', 'Lokalizacja', @Lokalizacja) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Lokalizacja - invalid enum value.';
		return;
	end;

	if(@update = 0)
		insert into Pacjent (NumerPacjenta, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod )
					values (@NumerPacjenta, @RokUrodzenia, @MiesiacUrodzenia, @Plec, @Lokalizacja, @LiczbaElektrod	 );
	else
		update Pacjent
		set RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod
		where NumerPacjenta = @NumerPacjenta;		
end;
go


-- last rev. 2013-10-02
-- @result codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message, 3 = login user not found
alter procedure update_patient_l  (	@NumerPacjenta	varchar(20), @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @allow_update_existing bit, @actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @update bit;
	set @result = 0;
	set @update = 0;
	declare @userid int;
	set @userid = 0;
	set @message = '';
	if(( select count(*) from Pacjent where NumerPacjenta = @NumerPacjenta )>0 )
	begin
		if ( @allow_update_existing = 0 )
		begin
			set @result = 1;
			return;
		end
		else set @update = 1;
	end;

	select @userid = dbo.identify_user(@actor_login);

	if ( @userid = 0 )
	begin
		set @result = 3;
		set @message = 'Unknown login '+ @actor_login ;
		return;
	end;

	if( dbo.validate_ext_bit( @Plec) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Plec - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_year_month( @RokUrodzenia, @MiesiacUrodzenia) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'MiesiacUrodzenia / RokUrodzenia - invalid value.';
		return;
	end;

	if( dbo.validate_input_varchar('Pacjent', 'Lokalizacja', @Lokalizacja) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Lokalizacja - invalid enum value.';
		return;
	end;

	if(@update = 0)
		insert into Pacjent (NumerPacjenta, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod, Wprowadzil, Zmodyfikowal, OstatniaZmiana  )
					values (@NumerPacjenta, @RokUrodzenia, @MiesiacUrodzenia, @Plec, @Lokalizacja, @LiczbaElektrod, @userid, @userid, getdate()	 );
	else
		update Pacjent
		set RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod,
		Zmodyfikowal = @userid,
		OstatniaZmiana = getdate()
		where NumerPacjenta = @NumerPacjenta;		
end;
go

create function validate_ext_bit ( @val tinyint )
returns bit
as
begin
return case @val 
		when 0 then 1
		when 1 then 1
		when 2 then 1
		else 0 end;
end;
go
	
create function validate_year_month ( @year smallint, @month tinyint )
returns bit
as
begin
	declare @res bit;
	set @res = 1;
	if( @year < 1920 or @year > 2050 or @month < 1 or @month > 12) set @res = 0;
	return @res;
end;
go


-- last rev. 2013-10-05
-- @result codes: 0 = OK, 1 = visit already exists while run in no-update mode,  exist 2 = validation failed - see message, 3 = patient of this number not found, 4 = user login unknown
create procedure update_examination_questionnaire_partA  (@NumerPacjenta varchar(20), @RodzajWizyty decimal(2,1),
	@DataPrzyjecia date,	@DataOperacji date,	@DataWypisu date, @MasaCiala decimal(4,1),
	@Wyksztalcenie	tinyint, @Rodzinnosc tinyint, @RokZachorowania	smallint, @MiesiacZachorowania tinyint, @RokBadania	smallint, @MiesiacBadania tinyint,
	@PierwszyObjaw	tinyint, 
	@Drzenie	tinyint,
	@Sztywnosc	tinyint,
	@Spowolnienie	tinyint,
	@ObjawyInne	tinyint,
	@ObjawyInneJakie	varchar(80),
	@CzasOdPoczObjDoWlLDopy	tinyint, @DyskinezyObecnie	tinyint, @DyskinezyOdLat	decimal(3,1), @FluktuacjeObecnie tinyint, @FluktuacjeOdLat	decimal(3,1), 
	@CzasDyskinez decimal(3,1), @CzasOFF decimal(3,1), @PoprawaPoLDopie tinyint,
	@allow_update_existing bit,
	@actor_login varchar(50),
	@result int OUTPUT, @visit_id int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @patient_id int;
	declare @user_id int;
	declare @update int;
	set @patient_id = 0;
	set @result = 0;
	set @message = '';
	set @update = 0;
	select @patient_id = IdPacjent from Pacjent where NumerPacjenta = @NumerPacjenta;
	
	if(@patient_id = 0)
	begin
		set @result = 3;
		set @message = 'patient of this number not found';
		return;
	end;
	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if (select count(*) from Wizyta where IdPacjent = @patient_id and RodzajWizyty = @RodzajWizyty ) > 0 
	begin
		if (@allow_update_existing = 0)
			begin
				set @result = 1;
				return;
			end;
		set @update = 1;
		select @visit_id = IdWizyta from Wizyta where IdPacjent = @patient_id and RodzajWizyty = @RodzajWizyty;
	end;

	-- validations

	if dbo.validate_input_decimal('Wizyta', 'RodzajWizyty', @RodzajWizyty) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute RodzajWizyty';
		return;
	end;
	if dbo.validate_input_int('Wizyta', 'Wyksztalcenie', @Wyksztalcenie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Wyksztalcenie';
		return;
	end;
	if( dbo.validate_ext_bit( @Rodzinnosc) = 0 )
	begin
		set @result = 2;
		set @message = 'Rodzinnosc - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_year_month( @RokZachorowania, @MiesiacZachorowania) = 0 )
	begin
		set @result = 2;
		set @message = 'RokZachorowania / MiesiacZachorowania - invalid value.';
		return;
	end;

	if( dbo.validate_year_month( @RokBadania, @MiesiacBadania) = 0 )
	begin
		set @result = 2;
		set @message = 'RokBadania / MiesiacBadania - invalid value.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'PierwszyObjaw', @PierwszyObjaw) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute PierwszyObjaw';
		return;
	end;
	
	if( dbo.validate_ext_bit( @Drzenie) = 0 )
	begin
		set @result = 2;
		set @message = 'Drzenie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Sztywnosc) = 0 )
	begin
		set @result = 2;
		set @message = 'Sztywnosc - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Spowolnienie) = 0 )
	begin
		set @result = 2;
		set @message = 'Spowolnienie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ObjawyInne) = 0 )
	begin
		set @result = 2;
		set @message = 'ObjawyInne - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	if( dbo.validate_ext_bit( @DyskinezyObecnie) = 0 )
	begin
		set @result = 2;
		set @message = 'DyskinezyObecnie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;
	if( dbo.validate_ext_bit( @FluktuacjeObecnie) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'FluktuacjeObecnie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @PoprawaPoLDopie) = 0 )
	begin
		set @result = 2;
		set @message = 'PoprawaPoLDopie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	-- /validations
	-- depending on update
	if(@update = 0)
		begin
		insert into Wizyta (	RodzajWizyty, IdPacjent, DataPrzyjecia, DataOperacji, DataWypisu, MasaCiala, Wyksztalcenie,	Rodzinnosc,	RokZachorowania, MiesiacZachorowania, RokBadania, MiesiacBadania, PierwszyObjaw, 
								Drzenie, Sztywnosc,	Spowolnienie, ObjawyInne, ObjawyInneJakie,
								CzasOdPoczObjDoWlLDopy, DyskinezyObecnie,
								DyskinezyOdLat, FluktuacjeObecnie, FluktuacjeOdLat,	CzasDyskinez, CzasOFF, PoprawaPoLDopie,
								Wprowadzil, Zmodyfikowal, OstatniaZmiana )
					values (	@RodzajWizyty, @patient_id, @DataPrzyjecia, @DataOperacji, @DataWypisu, @MasaCiala, @Wyksztalcenie,	@Rodzinnosc, @RokZachorowania, @MiesiacZachorowania, @RokBadania, @MiesiacBadania, @PierwszyObjaw, 
								@Drzenie, @Sztywnosc,@Spowolnienie, @ObjawyInne, @ObjawyInneJakie,
								@CzasOdPoczObjDoWlLDopy, @DyskinezyObecnie,
								@DyskinezyOdLat, @FluktuacjeObecnie, @FluktuacjeOdLat, @CzasDyskinez, @CzasOFF, @PoprawaPoLDopie,
								@user_id, @user_id, getdate() )  set @visit_id = SCOPE_IDENTITY();
		end;
	else
		begin
		update Wizyta
		set DataPrzyjecia = @DataPrzyjecia, DataOperacji = @DataOperacji, DataWypisu = @DataWypisu, MasaCiala = @MasaCiala, Wyksztalcenie = @Wyksztalcenie,	Rodzinnosc = @Rodzinnosc,	RokZachorowania = @RokZachorowania, 
				MiesiacZachorowania = @MiesiacZachorowania, RokBadania = @RokBadania, MiesiacBadania = @MiesiacBadania, PierwszyObjaw = @PierwszyObjaw, 
				Drzenie	= @Drzenie,
				Sztywnosc	= @Sztywnosc,
				Spowolnienie	= @Spowolnienie,
				ObjawyInne	= @ObjawyInne,
				ObjawyInneJakie	= @ObjawyInneJakie,
				CzasOdPoczObjDoWlLDopy = @CzasOdPoczObjDoWlLDopy, DyskinezyObecnie = @DyskinezyObecnie,
								DyskinezyOdLat = @DyskinezyOdLat, FluktuacjeObecnie = @FluktuacjeObecnie, FluktuacjeOdLat = @FluktuacjeOdLat,
								CzasDyskinez = @CzasDyskinez, CzasOFF = @CzasOFF,
								PoprawaPoLDopie	= @PoprawaPoLDopie,
								Zmodyfikowal = @user_id, OstatniaZmiana = getdate() 
		where RodzajWizyty = @RodzajWizyty and IdPacjent = @patient_id;
		end;
	return;
end;
go







-- last rev. 2013-08-15

-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partB  (@IdWizyta int,
	@PrzebyteLeczenieOperacyjnePD tinyint,
	@Papierosy tinyint,
	@Kawa	tinyint, 
	@ZielonaHerbata tinyint,	
	@Alkohol	tinyint, 
	@ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,	
	@Zamieszkanie tinyint, 
	@Uwagi varchar(50),
	@Nadcisnienie tinyint,
	@BlokeryKanWapn tinyint,
	@DominujacyObjawObecnie tinyint,
	@DominujacyObjawUwagi varchar(50),
	@ObjawyAutonomiczne tinyint,
	@RLS	tinyint,
	@ObjawyPsychotyczne	tinyint,
	@Depresja	tinyint,
	@Otepienie	decimal(2,1),
	@Dyzartria 	tinyint,
	@DysfagiaObjaw tinyint,
	@RBD	tinyint,
	@ZaburzenieRuchomosciGalekOcznych	tinyint,
	@Apraksja	tinyint,
	@TestKlaskania	tinyint,
	@ZaburzeniaWechowe	tinyint,
	@actor_login varchar(50),
	@result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;


	-- validations

	if dbo.validate_input_int('Wizyta', 'PrzebyteLeczenieOperacyjnePD', @PrzebyteLeczenieOperacyjnePD) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute PrzebyteLeczenieOperacyjnePD - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'Papierosy', @Papierosy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Papierosy';
		return;
	end;
	if dbo.validate_input_int('Wizyta', 'Kawa', @Kawa) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Kawa';
		return;
	end;

	if( dbo.validate_ext_bit( @ZielonaHerbata) = 0 )
	begin
		set @result = 2;
		set @message = 'ZielonaHerbata - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'Alkohol', @Alkohol) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Alkohol';
		return;
	end;
	if dbo.validate_input_int('Wizyta', 'Zamieszkanie', @Zamieszkanie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Zamieszkanie';
		return;
	end;

	if( dbo.validate_ext_bit( @Nadcisnienie ) = 0 )
	begin
		set @result = 2;
		set @message = 'Nadcisnienie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @BlokeryKanWapn ) = 0 )
	begin
		set @result = 2;
		set @message = 'BlokeryKanWapn - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'DominujacyObjawObecnie', @DominujacyObjawObecnie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute DominujacyObjawObecnie - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'ObjawyAutonomiczne', @ObjawyAutonomiczne) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute ObjawyAutonomiczne - see enumeration for allowed values';
		return;
	end;

	if( dbo.validate_ext_bit( @RLS) = 0 )
	begin
		set @result = 2;
		set @message = 'RLS - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ObjawyPsychotyczne) = 0 )
	begin
		set @result = 2;
		set @message = 'ObjawyPsychotyczne - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Depresja) = 0 )
	begin
		set @result = 2;
		set @message = 'Depresja - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	if dbo.validate_input_decimal('Wizyta', 'Otepienie', @Otepienie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Otepienie';
		return;
	end;

	if( dbo.validate_ext_bit( @Dyzartria ) = 0 )
	begin
		set @result = 2;
		set @message = 'Dyzartria  - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @DysfagiaObjaw) = 0 )
	begin
		set @result = 2;
		set @message = 'Dysfagia objaw - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @RBD) = 0 )
	begin
		set @result = 2;
		set @message = 'RBD - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ZaburzenieRuchomosciGalekOcznych) = 0 )
	begin
		set @result = 2;
		set @message = 'ZaburzenieRuchomosciGalekOcznych - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @Apraksja) = 0 )
	begin
		set @result = 2;
		set @message = 'Apraksja - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @TestKlaskania) = 0 )
	begin
		set @result = 2;
		set @message = 'TestKlaskania - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @ZaburzeniaWechowe) = 0 )
	begin
		set @result = 2;
		set @message = 'ZaburzeniaWechowe - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	update Wizyta
		set 
			PrzebyteLeczenieOperacyjnePD  = @PrzebyteLeczenieOperacyjnePD,
			Papierosy = @Papierosy,	Kawa = @Kawa, 
			ZielonaHerbata = @ZielonaHerbata, 
			Alkohol = @Alkohol, 
			ZabiegowWZnieczOgPrzedRozpoznaniemPD = @ZabiegowWZnieczOgPrzedRozpoznaniemPD,
			Zamieszkanie = @Zamieszkanie, 
			Uwagi = @Uwagi, 
			Nadcisnienie  = @Nadcisnienie,
			BlokeryKanWapn  = @BlokeryKanWapn,
			DominujacyObjawObecnie  = @DominujacyObjawObecnie,
			DominujacyObjawUwagi  = @DominujacyObjawUwagi,
			ObjawyAutonomiczne = @ObjawyAutonomiczne,
			RLS	= @RLS,
			ObjawyPsychotyczne	= @ObjawyPsychotyczne,
			Depresja	= @Depresja,
			Otepienie	= @Otepienie,
			Dyzartria 	= @Dyzartria,
			DysfagiaObjaw = @DysfagiaObjaw,
			RBD	= @RBD,
			ZaburzenieRuchomosciGalekOcznych	= @ZaburzenieRuchomosciGalekOcznych,
			Apraksja	= @Apraksja,
			TestKlaskania	= @TestKlaskania,
			ZaburzeniaWechowe = @ZaburzeniaWechowe,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go


/*



*/


-- modified: 2013-09-08
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partC  (
	@IdWizyta int,
	@Ldopa tinyint,
	@LDopaObecnie smallint,
	@Agonista bit,
	@AgonistaObecnie	smallint,
	@Amantadyna	bit,
	@AmantadynaObecnie smallint,
	@MAOBinh bit,
	@MAOBinhObecnie smallint,
	@COMTinh bit,
	@COMTinhObecnie smallint,
	@Cholinolityk bit,
	@CholinolitykObecnie smallint,
	@LekiInne bit,
	@LekiInneJakie varchar(50),
	@L_STIMOpis varchar(30),
	@L_STIMAmplitude decimal(5,1),
	@L_STIMDuration decimal(5,1),
	@L_STIMFrequency decimal(5,1),
	@R_STIMOpis varchar(30),
	@R_STIMAmplitude decimal(5,1),
	@R_STIMDuration decimal(5,1),
	@R_STIMFrequency decimal(5,1),
	@Wypis_Ldopa tinyint,
	@Wypis_LDopaObecnie smallint,
	@Wypis_Agonista bit,
	@Wypis_AgonistaObecnie smallint,
	@Wypis_Amantadyna bit,
	@Wypis_AmantadynaObecnie smallint,
	@Wypis_MAOBinh bit,
	@Wypis_MAOBinhObecnie smallint,
	@Wypis_COMTinh bit,
	@Wypis_COMTinhObecnie smallint,
	@Wypis_Cholinolityk bit,
	@Wypis_CholinolitykObecnie smallint,
	@Wypis_LekiInne bit,
	@Wypis_LekiInneJakie varchar(50),
	@Wypis_L_STIMOpis varchar(30),
	@Wypis_L_STIMAmplitude decimal(5,1),
	@Wypis_L_STIMDuration decimal(5,1),
	@Wypis_L_STIMFrequency decimal(5,1),
	@Wypis_R_STIMOpis varchar(30),
	@Wypis_R_STIMAmplitude decimal(5,1),
	@Wypis_R_STIMDuration decimal(5,1),
	@Wypis_R_STIMFrequency decimal(5,1),
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;



	update Wizyta
		set 
			Ldopa = @Ldopa,
			LDopaObecnie = @LDopaObecnie,
			Agonista = @Agonista,
			AgonistaObecnie = @AgonistaObecnie,
			Amantadyna = @Amantadyna,
			AmantadynaObecnie = @AmantadynaObecnie,
			MAOBinh = @MAOBinh,
			MAOBinhObecnie = @MAOBinhObecnie,
			COMTinh = @COMTinh,
			COMTinhObecnie = @COMTinhObecnie,
			Cholinolityk = @Cholinolityk,
			CholinolitykObecnie = @CholinolitykObecnie,
			LekiInne = @LekiInne,
			LekiInneJakie = @LekiInneJakie,
			L_STIMOpis = @L_STIMOpis,
			L_STIMAmplitude = @L_STIMAmplitude,
			L_STIMDuration = @L_STIMDuration,
			L_STIMFrequency = @L_STIMFrequency,
			R_STIMOpis = @R_STIMOpis,
			R_STIMAmplitude = @R_STIMAmplitude,
			R_STIMDuration = @R_STIMDuration,
			R_STIMFrequency = @R_STIMFrequency,
			Wypis_Ldopa = @Wypis_Ldopa,
			Wypis_LDopaObecnie = @Wypis_LDopaObecnie,
			Wypis_Agonista = @Wypis_Agonista,
			Wypis_AgonistaObecnie = @Wypis_AgonistaObecnie,
			Wypis_Amantadyna = @Wypis_Amantadyna,
			Wypis_AmantadynaObecnie = @Wypis_AmantadynaObecnie,
			Wypis_MAOBinh = @Wypis_MAOBinh,
			Wypis_MAOBinhObecnie = @Wypis_MAOBinhObecnie,
			Wypis_COMTinh = @Wypis_COMTinh,
			Wypis_COMTinhObecnie = @Wypis_COMTinhObecnie,
			Wypis_Cholinolityk = @Wypis_Cholinolityk,
			Wypis_CholinolitykObecnie = @Wypis_CholinolitykObecnie,
			Wypis_LekiInne = @Wypis_LekiInne,
			Wypis_LekiInneJakie = @Wypis_LekiInneJakie,
			Wypis_L_STIMOpis = @Wypis_L_STIMOpis,
			Wypis_L_STIMAmplitude = @Wypis_L_STIMAmplitude,
			Wypis_L_STIMDuration = @Wypis_L_STIMDuration,
			Wypis_L_STIMFrequency = @Wypis_L_STIMFrequency,
			Wypis_R_STIMOpis = @Wypis_R_STIMOpis,
			Wypis_R_STIMAmplitude = @Wypis_R_STIMAmplitude,
			Wypis_R_STIMDuration = @Wypis_R_STIMDuration,
			Wypis_R_STIMFrequency = @Wypis_R_STIMFrequency,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go

-- DEPRECATED. 2013-08-15 (attributes moved to B and H)
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partD  (
	@IdWizyta int,
	@PrzebyteLeczenieOperacyjnePD tinyint,
	@Nadcisnienie tinyint,
	@BlokeryKanWapn tinyint,
	@DominujacyObjawObecnie tinyint,
	@DominujacyObjawUwagi varchar(50),
	@BadanieWechu bit,
	@WynikWechu tinyint,
	@LimitDysfagii tinyint,
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;

	-- attribute validators

	if dbo.validate_input_int('Wizyta', 'PrzebyteLeczenieOperacyjnePD', @PrzebyteLeczenieOperacyjnePD) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute PrzebyteLeczenieOperacyjnePD - see enumeration for allowed values';
		return;
	end;

	if( dbo.validate_ext_bit( @Nadcisnienie ) = 0 )
	begin
		set @result = 2;
		set @message = 'Nadcisnienie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_ext_bit( @BlokeryKanWapn ) = 0 )
	begin
		set @result = 2;
		set @message = 'BlokeryKanWapn - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'DominujacyObjawObecnie', @DominujacyObjawObecnie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute DominujacyObjawObecnie - see enumeration for allowed values';
		return;
	end;

	if( dbo.validate_ext_bit( @BadanieWechu ) = 0 )
	begin
		set @result = 2;
		set @message = 'BadanieWechu - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'WynikWechu', @WynikWechu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute WynikWechu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'LimitDysfagii', @LimitDysfagii) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute LimitDysfagii - see enumeration for allowed values';
		return;
	end;


	update Wizyta
		set 
			PrzebyteLeczenieOperacyjnePD  = @PrzebyteLeczenieOperacyjnePD,
			Nadcisnienie  = @Nadcisnienie,
			BlokeryKanWapn  = @BlokeryKanWapn,
			DominujacyObjawObecnie  = @DominujacyObjawObecnie,
			DominujacyObjawUwagi  = @DominujacyObjawUwagi,
			BadanieWechu  = @BadanieWechu,
			WynikWechu  = @WynikWechu,
			LimitDysfagii  = @LimitDysfagii,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partE  (
	@IdWizyta int,
	@WydzielanieSliny	tinyint,
	@Dysfagia	tinyint,
	@DysfagiaCzestotliwosc	tinyint,
	@Nudnosci	tinyint,
	@Zaparcia	tinyint,
	@TrudnosciWOddawaniuMoczu	tinyint,
	@PotrzebaNaglegoOddaniaMoczu	tinyint,
	@NiekompletneOproznieniePecherza	tinyint,
	@SlabyStrumienMoczu	tinyint,
	@CzestotliwowscOddawaniaMoczu	tinyint,
	@Nykturia	tinyint,
	@NiekontrolowaneOddawanieMoczu	tinyint,
	@Omdlenia	tinyint,
	@ZaburzeniaRytmuSerca	tinyint,
	@ProbaPionizacyjna	tinyint,
	@WzrostPodtliwosciTwarzKark	tinyint,
	@WzrostPotliwosciRamionaDlonie	tinyint,
	@WzrostPotliwosciKonczynyDolneStopy	tinyint,
	@SpadekPodtliwosciTwarzKark	tinyint,
	@SpadekPotliwosciRamionaDlonie	tinyint,
	@SpadekPotliwosciKonczynyDolneStopy	tinyint,
	@NietolerancjaWysokichTemp	tinyint,
	@NietolerancjaNiskichTemp	tinyint,
	@Lojotok	tinyint,
	@SpadekLibido	tinyint,
	@KlopotyOsiagnieciaErekcji	tinyint,
	@KlopotyUtrzymaniaErekcji	tinyint,
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;

	-- attribute validators

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WydzielanieSliny) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute WydzielanieSliny - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Dysfagia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Dysfagia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @DysfagiaCzestotliwosc) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @DysfagiaCzestotliwosc - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Nudnosci) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Nudnosci - see enumeration for allowed values';
		return;
	end;


	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Zaparcia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Zaparcia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @TrudnosciWOddawaniuMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute TrudnosciWOddawaniuMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @PotrzebaNaglegoOddaniaMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @PotrzebaNaglegoOddaniaMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NiekompletneOproznieniePecherza) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NiekompletneOproznieniePecherza - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SlabyStrumienMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SlabyStrumienMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @CzestotliwowscOddawaniaMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @CzestotliwowscOddawaniaMoczu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Nykturia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Nykturia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NiekontrolowaneOddawanieMoczu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NiekontrolowaneOddawanieMoczu - see enumeration for allowed values';
		return;
	end;


	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Omdlenia) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Omdlenia - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @ZaburzeniaRytmuSerca) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @ZaburzeniaRytmuSerca - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @ProbaPionizacyjna) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @ProbaPionizacyjna - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPodtliwosciTwarzKark) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPodtliwosciTwarzKark - see enumeration for allowed values';
		return;
	end;

--
	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPotliwosciRamionaDlonie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPotliwosciRamionaDlonie - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPotliwosciKonczynyDolneStopy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPotliwosciKonczynyDolneStopy - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPodtliwosciTwarzKark) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekPodtliwosciTwarzKark - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPotliwosciRamionaDlonie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekPotliwosciRamionaDlonie - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPotliwosciKonczynyDolneStopy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute SpadekPotliwosciKonczynyDolneStopy - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NietolerancjaWysokichTemp) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NietolerancjaWysokichTemp - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @NietolerancjaNiskichTemp) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @NietolerancjaNiskichTemp - see enumeration for allowed values';
		return;
	end;


	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @Lojotok) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @Lojotok - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekLibido) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekLibido - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @KlopotyOsiagnieciaErekcji) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @KlopotyOsiagnieciaErekcji - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @KlopotyUtrzymaniaErekcji) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @KlopotyUtrzymaniaErekcji - see enumeration for allowed values';
		return;
	end;

	update Wizyta
		set 
			WydzielanieSliny = @WydzielanieSliny,
			Dysfagia = @Dysfagia,
			DysfagiaCzestotliwosc = @DysfagiaCzestotliwosc,
			Nudnosci = @Nudnosci,
			Zaparcia = @Zaparcia,
			TrudnosciWOddawaniuMoczu = @TrudnosciWOddawaniuMoczu,
			PotrzebaNaglegoOddaniaMoczu = @PotrzebaNaglegoOddaniaMoczu,
			NiekompletneOproznieniePecherza = @NiekompletneOproznieniePecherza,
			SlabyStrumienMoczu = @SlabyStrumienMoczu,
			CzestotliwowscOddawaniaMoczu = @CzestotliwowscOddawaniaMoczu,
			Nykturia = @Nykturia,
			NiekontrolowaneOddawanieMoczu = @NiekontrolowaneOddawanieMoczu,
			Omdlenia = @Omdlenia,
			ZaburzeniaRytmuSerca = @ZaburzeniaRytmuSerca,
			ProbaPionizacyjna = @ProbaPionizacyjna,
			WzrostPodtliwosciTwarzKark = @WzrostPodtliwosciTwarzKark,
			WzrostPotliwosciRamionaDlonie = @WzrostPotliwosciRamionaDlonie,
			WzrostPotliwosciKonczynyDolneStopy = @WzrostPotliwosciKonczynyDolneStopy,
			SpadekPodtliwosciTwarzKark = @SpadekPodtliwosciTwarzKark,
			SpadekPotliwosciRamionaDlonie = @SpadekPotliwosciRamionaDlonie,
			SpadekPotliwosciKonczynyDolneStopy = @SpadekPotliwosciKonczynyDolneStopy,
			NietolerancjaWysokichTemp = @NietolerancjaWysokichTemp,
			NietolerancjaNiskichTemp = @NietolerancjaNiskichTemp,
			Lojotok = @Lojotok,
			SpadekLibido = @SpadekLibido,
			KlopotyOsiagnieciaErekcji = @KlopotyOsiagnieciaErekcji,
			KlopotyUtrzymaniaErekcji = @KlopotyUtrzymaniaErekcji,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go

-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partF  (
	@IdWizyta int,
	@PDQ39	tinyint,
	@AIMS	tinyint,
	@Epworth	tinyint,
	@CGI	tinyint,
	@FSS	decimal(3,1),
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;

	-- attribute validators



	update Wizyta
		set 
			PDQ39 = @PDQ39,
			AIMS = @AIMS,
			Epworth = @Epworth,
			CGI = @CGI,
			FSS = @FSS
		where IdWizyta = @IdWizyta;

	return;
end;
go

-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partG  (
	@IdWizyta int,
	@TestZegara bit,
	@MMSE tinyint,
	@WAIS_R_Wiadomosci tinyint,
	@WAIS_R_PowtarzanieCyfr tinyint,
	@SkalaDepresjiBecka tinyint,
	@TestFluencjiZwierzeta tinyint,
	@TestFluencjiOstre tinyint,
	@TestFluencjiK tinyint,
	@TestLaczeniaPunktowA tinyint,
	@TestLaczeniaPunktowB tinyint,
	@TestUczeniaSlownoSluchowego tinyint,
	@TestStroopa tinyint,
	@TestMinnesota tinyint,
	@InnePsychologiczne varchar(150),
	@OpisBadania varchar(2000),
	@Wnioski varchar(2000),
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;


	update Wizyta
		set 
			TestZegara = @TestZegara,
			MMSE = @MMSE,
			WAIS_R_Wiadomosci = @WAIS_R_Wiadomosci,
			WAIS_R_PowtarzanieCyfr = @WAIS_R_PowtarzanieCyfr,
			SkalaDepresjiBecka = @SkalaDepresjiBecka,
			TestFluencjiZwierzeta = @TestFluencjiZwierzeta,
			TestFluencjiOstre = @TestFluencjiOstre,
			TestFluencjiK = @TestFluencjiK,
			TestLaczeniaPunktowA = @TestLaczeniaPunktowA,
			TestLaczeniaPunktowB = @TestLaczeniaPunktowB,
			TestUczeniaSlownoSluchowego = @TestUczeniaSlownoSluchowego,
			TestStroopa = @TestStroopa,
			TestMinnesota = @TestMinnesota,
			InnePsychologiczne = @InnePsychologiczne,
			OpisBadania = @OpisBadania,
			Wnioski = @Wnioski,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- created 2013-09-08
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partH  (
	@IdWizyta int,
	@Holter bit,
	@BadanieWechu bit,
	@WynikWechu tinyint,
	@LimitDysfagii tinyint,
	@pH_metriaPrze造ku bit,
	@SPECT bit,
	@MRI bit,
	@MRIwynik varchar(2000),
	@USGsrodmozgowia bit,
	@USGWynik tinyint,
	@Genetyka bit,
	@GenetykaWynik varchar(50),
	@Surowica bit,
	@SurowicaPozosta這 varchar(50),
	@Ferrytyna decimal(7,3),
	@CRP decimal(7,3),
	@NTproCNP decimal(7,3),
	@URCA decimal(7,3),
	@WitD decimal(7,3),
	@CHOL decimal(7,3),
	@TGI decimal(7,3),
	@HDL decimal(7,3),
	@LDL decimal(7,3),
	@olLDL decimal(7,3),
	@LaboratoryjneInne varchar(1000),
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this ID ='+ CAST(@IdWizyta as varchar)+' not found';
		return;
	end;

	-- validators

	if dbo.validate_input_int('Wizyta', 'WynikWechu', @WynikWechu) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute WynikWechu - see enumeration for allowed values';
		return;
	end;

	if dbo.validate_input_int('Wizyta', 'LimitDysfagii', @LimitDysfagii) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute LimitDysfagii - see enumeration for allowed values';
		return;
	end;

	if( dbo.validate_ext_bit( @USGsrodmozgowia ) = 0 )
	begin
		set @result = 2;
		set @message = 'USGsrodmozgowia  - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	if dbo.validate_input_int('Wizyta', 'USGWynik ', @USGWynik ) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute USGWynik  - see enumeration for allowed values';
		return;
	end;

	update Wizyta
		set 
			Holter = @Holter,
			BadanieWechu  = @BadanieWechu,
			WynikWechu  = @WynikWechu,
			LimitDysfagii  = @LimitDysfagii,
			pH_metriaPrze造ku = @pH_metriaPrze造ku,
			SPECT = @SPECT,
			MRI = @MRI,
			MRIwynik = @MRIwynik,
			USGsrodmozgowia = @USGsrodmozgowia,
			USGWynik = @USGWynik,
			Genetyka = @Genetyka,
			GenetykaWynik = @GenetykaWynik,
			Surowica = @Surowica,
			SurowicaPozosta這 = @SurowicaPozosta這,
			Ferrytyna = @Ferrytyna,
			CRP = @CRP,
			NTproCNP = @NTproCNP,
			URCA = @URCA,
			WitD = @WitD,
			CHOL = @CHOL,
			TGI = @TGI,
			HDL = @HDL,
			LDL = @LDL,
			olLDL = @olLDL,
			LaboratoryjneInne = @LaboratoryjneInne,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go



-- @result codes: 0 = OK, 1 = variant already exists while run in no-update mode,  exist 2 = validation failed - see message, 3 = visit of this ID not found, 4 = user login unknown
create procedure update_variant_examination_data_partA  (@IdWizyta int, @DBS tinyint, @BMT bit,
	@UPDRS_I	tinyint,
	@UPDRS_II	tinyint,
	@UPDRS_18	tinyint,
	@UPDRS_19 	tinyint,
	@UPDRS_20_FaceLipsChin	tinyint,
	@UPDRS_20_RHand	tinyint,
	@UPDRS_20_LHand	tinyint,
	@UPDRS_20_RFoot	tinyint,
	@UPDRS_20_LFoot	tinyint,
	@UPDRS_21_RHand	tinyint,
	@UPDRS_21_LHand	tinyint,
	@UPDRS_22_Neck	tinyint,
	@UPDRS_22_RHand	tinyint,
	@UPDRS_22_LHand	tinyint,
	@UPDRS_22_RFoot	tinyint,
	@UPDRS_22_LFoot	tinyint,
	@UPDRS_23_R	tinyint,
	@UPDRS_23_L	tinyint,
	@UPDRS_24_R	tinyint,
	@UPDRS_24_L	tinyint,
	@UPDRS_25_R	tinyint,
	@UPDRS_25_L	tinyint,
	@UPDRS_26_R	tinyint,
	@UPDRS_26_L	tinyint,
	@UPDRS_27	tinyint,
	@UPDRS_28	tinyint,
	@UPDRS_29	tinyint,
	@UPDRS_30	tinyint,
	@UPDRS_31	tinyint,
	@UPDRS_III	tinyint,
	@UPDRS_IV	tinyint,
	@UPDRS_TOTAL	tinyint,
	@HYscale	decimal(2,1),
	@SchwabEnglandScale	tinyint,
	@JazzNovo bit,
	@Wideookulograf bit,
	@Latencymeter bit,
	@LatencymeterDurationLEFT decimal(6,2),
	@LatencymeterLatencyLEFT decimal(6,2),
	@LatencymeterAmplitudeLEFT decimal(6,2),
	@LatencymeterPeakVelocityLEFT decimal(6,2),
	@LatencymeterDurationRIGHT decimal(6,2),
	@LatencymeterLatencyRIGHT decimal(6,2),
	@LatencymeterAmplitudeRIGHT decimal(6,2),
	@LatencymeterPeakVelocityRIGHT decimal(6,2),
	@LatencymeterDurationALL decimal(6,2),
	@LatencymeterLatencyALL decimal(6,2),
	@LatencymeterAmplitudeALL decimal(6,2),
	@LatencymeterPeakVelocityALL decimal(6,2),
	@allow_update_existing bit,
	@actor_login varchar(50),
	@result int OUTPUT, @variant_id int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @user_id int;
	declare @update int;
	set @result = 0;
	set @message = '';
	set @update = 0;

	
	if( not exists (select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this number not found';
		return;
	end;
	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if (select count(*) from Badanie where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT ) > 0 
	begin
		if (@allow_update_existing = 0)
			begin
				set @result = 1;
				return;
			end;
		set @update = 1;
		select @variant_id = IdBadanie from Badanie where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT;
	end;

	-- validations

	-- TODO!
	if dbo.validate_input_int('Badanie', 'DBS', @DBS) = 0	begin	set @result = 2;	set @message = 'Invalid value for attribute DBS';	return;	end;

	if dbo.validate_input_int('Badanie', 'UPDRS_I', @UPDRS_I) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_I'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_II', @UPDRS_II) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_II'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_18', @UPDRS_18) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_18'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_19', @UPDRS_19) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_19'; return; end; 
	if dbo.validate_input_int('Badanie', 'UPDRS_20_FaceLipsChin', @UPDRS_20_FaceLipsChin) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_FaceLipsChin'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_RHand', @UPDRS_20_RHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_RHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_LHand', @UPDRS_20_LHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_LHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_RFoot', @UPDRS_20_RFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_RFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_LFoot', @UPDRS_20_LFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_LFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_21_RHand', @UPDRS_21_RHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_21_RHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_21_LHand', @UPDRS_21_LHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_21_LHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_Neck', @UPDRS_22_Neck) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_Neck'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_RHand', @UPDRS_22_RHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_RHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_LHand', @UPDRS_22_LHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_LHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_RFoot', @UPDRS_22_RFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_RFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_LFoot', @UPDRS_22_LFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_LFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_23_R', @UPDRS_23_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_23_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_23_L', @UPDRS_23_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_23_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_24_R', @UPDRS_24_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_24_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_24_L', @UPDRS_24_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_24_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_25_R', @UPDRS_25_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_25_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_25_L', @UPDRS_25_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_25_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_26_R', @UPDRS_26_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_26_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_26_L', @UPDRS_26_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_26_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_27', @UPDRS_27) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_27'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_28', @UPDRS_28) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_28'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_29', @UPDRS_29) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_29'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_30', @UPDRS_30) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_30'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_31', @UPDRS_31) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_31'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_IV', @UPDRS_IV) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_IV'; return; end;

	if dbo.validate_input_int('Badanie', 'SchwabEnglandScale', @SchwabEnglandScale) = 0 begin set @result = 2; set @message = 'Invalid value for attribute SchwabEnglandScale'; return; end;
	if dbo.validate_input_decimal('Badanie', 'HYscale', @HYscale) = 0 begin set @result = 2; set @message = 'Invalid value for attribute HYscale'; return; end;

	-- /validations
	-- depending on update
	if(@update = 0)
		begin
		insert into Badanie (
			IdWizyta,
			DBS,
			BMT,
			UPDRS_I,
			UPDRS_II,
			UPDRS_18,
			UPDRS_19 ,
			UPDRS_20_FaceLipsChin,
			UPDRS_20_RHand,
			UPDRS_20_LHand,
			UPDRS_20_RFoot,
			UPDRS_20_LFoot,
			UPDRS_21_RHand,
			UPDRS_21_LHand,
			UPDRS_22_Neck,
			UPDRS_22_RHand,
			UPDRS_22_LHand,
			UPDRS_22_RFoot,
			UPDRS_22_LFoot,
			UPDRS_23_R,
			UPDRS_23_L,
			UPDRS_24_R,
			UPDRS_24_L,
			UPDRS_25_R,
			UPDRS_25_L,
			UPDRS_26_R,
			UPDRS_26_L,
			UPDRS_27,
			UPDRS_28,
			UPDRS_29,
			UPDRS_30,
			UPDRS_31,
			UPDRS_III,
			UPDRS_IV,
			UPDRS_TOTAL,
			HYscale,
			SchwabEnglandScale,
			JazzNovo,
			Wideookulograf,
			Latencymeter,
			LatencymeterDurationLEFT ,
			LatencymeterLatencyLEFT ,
			LatencymeterAmplitudeLEFT ,
			LatencymeterPeakVelocityLEFT ,
			LatencymeterDurationRIGHT ,
			LatencymeterLatencyRIGHT ,
			LatencymeterAmplitudeRIGHT ,
			LatencymeterPeakVelocityRIGHT ,
			LatencymeterDurationALL ,
			LatencymeterLatencyALL ,
			LatencymeterAmplitudeALL ,
			LatencymeterPeakVelocityALL,
			Wprowadzil,
			Zmodyfikowal,
			OstatniaZmiana
		 )
		values (
			@IdWizyta,
			@DBS,
			@BMT,
			@UPDRS_I,
			@UPDRS_II,
			@UPDRS_18,
			@UPDRS_19 ,
			@UPDRS_20_FaceLipsChin,
			@UPDRS_20_RHand,
			@UPDRS_20_LHand,
			@UPDRS_20_RFoot,
			@UPDRS_20_LFoot,
			@UPDRS_21_RHand,
			@UPDRS_21_LHand,
			@UPDRS_22_Neck,
			@UPDRS_22_RHand,
			@UPDRS_22_LHand,
			@UPDRS_22_RFoot,
			@UPDRS_22_LFoot,
			@UPDRS_23_R,
			@UPDRS_23_L,
			@UPDRS_24_R,
			@UPDRS_24_L,
			@UPDRS_25_R,
			@UPDRS_25_L,
			@UPDRS_26_R,
			@UPDRS_26_L,
			@UPDRS_27,
			@UPDRS_28,
			@UPDRS_29,
			@UPDRS_30,
			@UPDRS_31,
			@UPDRS_III,
			@UPDRS_IV,
			@UPDRS_TOTAL,
			@HYscale,
			@SchwabEnglandScale,
			@JazzNovo,
			@Wideookulograf,
			@Latencymeter,
			@LatencymeterDurationLEFT ,
			@LatencymeterLatencyLEFT ,
			@LatencymeterAmplitudeLEFT ,
			@LatencymeterPeakVelocityLEFT ,
			@LatencymeterDurationRIGHT ,
			@LatencymeterLatencyRIGHT ,
			@LatencymeterAmplitudeRIGHT ,
			@LatencymeterPeakVelocityRIGHT ,
			@LatencymeterDurationALL ,
			@LatencymeterLatencyALL ,
			@LatencymeterAmplitudeALL ,
			@LatencymeterPeakVelocityALL,
			@user_id,
			@user_id, 
			getdate() 		
		)  set @variant_id = SCOPE_IDENTITY();
		end;
	else
		begin
			update Badanie
			set 
				UPDRS_I	=	@UPDRS_I,
				UPDRS_II	=	@UPDRS_II,
				UPDRS_18	=	@UPDRS_18,
				UPDRS_19 	=	@UPDRS_19,
				UPDRS_20_FaceLipsChin	=	@UPDRS_20_FaceLipsChin,
				UPDRS_20_RHand	=	@UPDRS_20_RHand,
				UPDRS_20_LHand	=	@UPDRS_20_LHand,
				UPDRS_20_RFoot	=	@UPDRS_20_RFoot,
				UPDRS_20_LFoot	=	@UPDRS_20_LFoot,
				UPDRS_21_RHand	=	@UPDRS_21_RHand,
				UPDRS_21_LHand	=	@UPDRS_21_LHand,
				UPDRS_22_Neck	=	@UPDRS_22_Neck,
				UPDRS_22_RHand	=	@UPDRS_22_RHand,
				UPDRS_22_LHand	=	@UPDRS_22_LHand,
				UPDRS_22_RFoot	=	@UPDRS_22_RFoot,
				UPDRS_22_LFoot	=	@UPDRS_22_LFoot,
				UPDRS_23_R	=	@UPDRS_23_R,
				UPDRS_23_L	=	@UPDRS_23_L,
				UPDRS_24_R	=	@UPDRS_24_R,
				UPDRS_24_L	=	@UPDRS_24_L,
				UPDRS_25_R	=	@UPDRS_25_R,
				UPDRS_25_L	=	@UPDRS_25_L,
				UPDRS_26_R	=	@UPDRS_26_R,
				UPDRS_26_L	=	@UPDRS_26_L,
				UPDRS_27	=	@UPDRS_27,
				UPDRS_28	=	@UPDRS_28,
				UPDRS_29	=	@UPDRS_29,
				UPDRS_30	=	@UPDRS_30,
				UPDRS_31	=	@UPDRS_31,
				UPDRS_III	=	@UPDRS_III,
				UPDRS_IV	=	@UPDRS_IV,
				UPDRS_TOTAL	=	@UPDRS_TOTAL,
				HYscale	=	@HYscale,
				SchwabEnglandScale	=	@SchwabEnglandScale,
				JazzNovo = @JazzNovo,
				Wideookulograf = @Wideookulograf,
				Latencymeter = @Latencymeter,
				LatencymeterDurationLEFT = @LatencymeterDurationLEFT ,
				LatencymeterLatencyLEFT = @LatencymeterLatencyLEFT ,
				LatencymeterAmplitudeLEFT = @LatencymeterAmplitudeLEFT ,
				LatencymeterPeakVelocityLEFT = @LatencymeterPeakVelocityLEFT ,
				LatencymeterDurationRIGHT = @LatencymeterDurationRIGHT ,
				LatencymeterLatencyRIGHT = @LatencymeterLatencyRIGHT ,
				LatencymeterAmplitudeRIGHT = @LatencymeterAmplitudeRIGHT ,
				LatencymeterPeakVelocityRIGHT = @LatencymeterPeakVelocityRIGHT ,
				LatencymeterDurationALL = @LatencymeterDurationALL ,
				LatencymeterLatencyALL = @LatencymeterLatencyALL ,
				LatencymeterAmplitudeALL = @LatencymeterAmplitudeALL ,
				LatencymeterPeakVelocityALL = @LatencymeterPeakVelocityALL,
				Zmodyfikowal = @user_id, 
				OstatniaZmiana = getdate() 
			where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT ;
		end;
	return;
end;
go


-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_variant_examination_data_partB  (	@IdBadanie int,
	@Tremorometria bit, 
	@TremorometriaLEFT_0_1 decimal(7,2),
	@TremorometriaLEFT_1_2 decimal(7,2),
	@TremorometriaLEFT_2_3 decimal(7,2),
	@TremorometriaLEFT_3_4 decimal(7,2),
	@TremorometriaLEFT_4_5 decimal(7,2),
	@TremorometriaLEFT_5_6 decimal(7,2),
	@TremorometriaLEFT_6_7 decimal(7,2),
	@TremorometriaLEFT_7_8 decimal(7,2),
	@TremorometriaLEFT_8_9 decimal(7,2),
	@TremorometriaLEFT_9_10 decimal(7,2),
	@TremorometriaLEFT_23_24 decimal(7,2),
	@TremorometriaRIGHT_0_1 decimal(7,2),
	@TremorometriaRIGHT_1_2 decimal(7,2),
	@TremorometriaRIGHT_2_3 decimal(7,2),
	@TremorometriaRIGHT_3_4 decimal(7,2),
	@TremorometriaRIGHT_4_5 decimal(7,2),
	@TremorometriaRIGHT_5_6 decimal(7,2),
	@TremorometriaRIGHT_6_7 decimal(7,2),
	@TremorometriaRIGHT_7_8 decimal(7,2),
	@TremorometriaRIGHT_8_9 decimal(7,2),
	@TremorometriaRIGHT_9_10 decimal(7,2),
	@TremorometriaRIGHT_23_24 decimal(7,2),	
	@TestSchodkowy bit,
	@TestSchodkowyCzas1 decimal(4,2),
	@TestSchodkowyCzas2 decimal(4,2),
	@TestMarszu bit,
	@TestMarszuCzas1 decimal(4,2),
	@TestMarszuCzas2 decimal(4,2),
	@Posturografia bit,
	@MotionAnalysis bit,
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if(not exists(select * from Badanie where IdBadanie = @IdBadanie ) )
	begin
		set @result = 3;
		set @message = 'variant of this ID ='+ CAST(@IdBadanie as varchar)+' not found';
		return;
	end;

	if( dbo.validate_ext_bit( @Tremorometria) = 0 )
	begin
		set @result = 2;
		set @message = 'Tremorometria - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	update Badanie
		set 
			Tremorometria = @Tremorometria, 
			TremorometriaLEFT_0_1 = @TremorometriaLEFT_0_1,
			TremorometriaLEFT_1_2 = @TremorometriaLEFT_1_2,
			TremorometriaLEFT_2_3 = @TremorometriaLEFT_2_3,
			TremorometriaLEFT_3_4 = @TremorometriaLEFT_3_4,
			TremorometriaLEFT_4_5 = @TremorometriaLEFT_4_5,
			TremorometriaLEFT_5_6 = @TremorometriaLEFT_5_6,
			TremorometriaLEFT_6_7 = @TremorometriaLEFT_6_7,
			TremorometriaLEFT_7_8 = @TremorometriaLEFT_7_8,
			TremorometriaLEFT_8_9 = @TremorometriaLEFT_8_9,
			TremorometriaLEFT_9_10 = @TremorometriaLEFT_9_10,
			TremorometriaLEFT_23_24 = @TremorometriaLEFT_23_24,
			TremorometriaRIGHT_0_1 = @TremorometriaRIGHT_0_1,
			TremorometriaRIGHT_1_2 = @TremorometriaRIGHT_1_2,
			TremorometriaRIGHT_2_3 = @TremorometriaRIGHT_2_3,
			TremorometriaRIGHT_3_4 = @TremorometriaRIGHT_3_4,
			TremorometriaRIGHT_4_5 = @TremorometriaRIGHT_4_5,
			TremorometriaRIGHT_5_6 = @TremorometriaRIGHT_5_6,
			TremorometriaRIGHT_6_7 = @TremorometriaRIGHT_6_7,
			TremorometriaRIGHT_7_8 = @TremorometriaRIGHT_7_8,
			TremorometriaRIGHT_8_9 = @TremorometriaRIGHT_8_9,
			TremorometriaRIGHT_9_10 = @TremorometriaRIGHT_9_10,
			TremorometriaRIGHT_23_24 = @TremorometriaRIGHT_23_24,
			TestSchodkowy	= @TestSchodkowy,
			TestSchodkowyCzas1	= @TestSchodkowyCzas1,
			TestSchodkowyCzas2	= @TestSchodkowyCzas2,
			TestMarszu	= @TestMarszu,
			TestMarszuCzas1	= @TestMarszuCzas1,
			TestMarszuCzas2	= @TestMarszuCzas2,
			Posturografia	= @Posturografia,
			MotionAnalysis	= @MotionAnalysis,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdBadanie = @IdBadanie;

	return;
end;
go




-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_variant_examination_data_partC  (	@IdBadanie int,
	@UpAndGo	decimal(3,1),
	@UpAndGoLiczby	decimal(3,1),
	@UpAndGoKubekPrawa	decimal(3,1),
	@UpAndGoKubekLewa	decimal(3,1),
	@TST	decimal(3,1),
	@TandemPivot	tinyint,
	@WTT	decimal(3,1),
	@actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	
	declare @user_id int;
	set @user_id = 0;

	set @result = 0;

	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;

	if(not exists(select * from Badanie where IdBadanie = @IdBadanie ) )
	begin
		set @result = 3;
		set @message = 'variant of this ID ='+ CAST(@IdBadanie as varchar)+' not found';
		return;
	end;

	-- validation
	if dbo.validate_input_int('Badanie', 'TandemPivot', @TandemPivot) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute TandemPivot - see enumeration for allowed values';
		return;
	end;


	update Badanie
		set 
			UpAndGo	=	@UpAndGo,
			UpAndGoLiczby	=	@UpAndGoLiczby,
			UpAndGoKubekPrawa	=	@UpAndGoKubekPrawa,
			UpAndGoKubekLewa	=	@UpAndGoKubekLewa,
			TST	=	@TST,
			TandemPivot	=	@TandemPivot,
			WTT	=	@WTT,
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdBadanie = @IdBadanie;

	return;
end;
go


-- atrybuty wielowartosciowe

-- created: 2013-10-05
create type IntValueListUdt as table
(
	Value int
)
go


create function get_exam_multi_values( @attr_name varchar(50), @IdWizyta int )
returns table
as
return
select Wartosc from WartoscAtrybutuWizytyInt waw join Atrybut a on waw.IdAtrybut = a.IdAtrybut where IdWizyta = @IdWizyta and a.Nazwa = @attr_name;
go

-- created: 2013-10-05
-- 1 = attribute name unknown; 2 = exam_id not found; 3 = validatiation of value failed;
create procedure update_exam_int_attribute ( @IdWizyta int, @attr_name varchar(50), @values as IntValueListUdt readonly, @actor_login varchar(50), @result int OUTPUT )
as
begin	
	declare @attr_id int;
	set @result = 0;
	set @attr_id = 0;
	
	select @attr_id = IdAtrybut from Atrybut where Tabela = 'Wizyta' and Nazwa = @attr_name;

	-- validation
	if @attr_id = 0
	begin
		set @result = 1;
		return;
	end;

	if not exists (select * from Wizyta where IdWizyta = @IdWizyta )
	begin
		set @result = 2;
		return;
	end;

	if exists ( select * from @values where dbo.validate_input_int('Wizyta', @attr_name, Value) = 0 )
	begin
		set @result = 3;
		return;
	end;
	delete from WartoscAtrybutuWizytyInt where IdWizyta = @IdWizyta and IdAtrybut = @attr_id and Wartosc not in (select Value from @values );

	insert into WartoscAtrybutuWizytyInt (IdWizyta, IdAtrybut, Wartosc) select @IdWizyta, @attr_id, Value from
		@values where Value not in (select Wartosc v from WartoscAtrybutuWizytyInt where IdWizyta = @IdWizyta and IdAtrybut = @attr_id) ;
	

end;

-- created: 2013-10-05 //
create function get_variant_multi_values( @attr_name varchar(50), @IdBadanie int )
returns table
as
return
select Wartosc from WartoscAtrybutuBadaniaInt where IdBadanie = @IdBadanie;
go

-- created: 2013-10-05 // 
-- 1 = attribute name unknown; 2 = exam_id not found
create procedure update_variant_int_attribute ( @IdBadanie int, @attr_name varchar(50), @values as IntValueListUdt readonly, @actor_login varchar(50), @result int OUTPUT )
as
begin	
	declare @attr_id int;
	set @attr_id = 0;
	
	select @attr_id = IdAtrybut from Atrybut where Tabela = 'Badanie' and Nazwa = @attr_name;

	-- validation
	if @attr_id = 0
	begin
		set @result = 1;
		return;
	end;

	if not exists (select * from Badanie where IdBadanie = @IdBadanie )
	begin
		set @result = 2;
		return;
	end;


	delete from WartoscAtrybutuBadaniaInt where IdBadanie = @IdBadanie and IdAtrybut = @attr_id and Wartosc not in (select Value from @values );

	insert into WartoscAtrybutuBadaniaInt (IdBadanie, IdAtrybut, Wartosc) select @IdBadanie, @attr_id, 
		( (select Value from @values) except (select Wartosc from WartoscAtrybutuBadaniaInt where IdBadanie = @IdBadanie and IdAtrybut = @attr_id) ) ;
	

end;



-- wsparcie generowania numerow pacjenta


create procedure suggest_new_patient_number( @admission_date date, @patient_number varchar(20) OUTPUT )
as
begin
	declare @maxnum int;
	set @patient_number = '/PD/DBS/'+CAST(YEAR(@admission_date) as varchar);
	select @maxnum = max(IdPacjent)+1 from Pacjent;
	set @patient_number = cast(@maxnum as varchar)+@patient_number; -- rozwiazanie tymczasowe
	return;
end;
go






/*




-- wywiad A (cz��� epidemiologiczna)

insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0,	'przedoperacyjna' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0.5,	'po p馧 roku' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	1,	'rok po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	2,	'2 lata po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	3,	'3 lata po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	4,	'4 lata po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	5,	'5 lata po DBS' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	1,	'podstawowe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	2,	'zawodowe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	3,	'�rednie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	4,	'wy窺ze' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	1,	'zaburzenia r闚nowagi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	2,	'spowolnienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	3,	'sztywno��' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	4,	'dr瞠nie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	5,	'ot瘼ienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	6,	'dyskinezy i fluktuacje' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	7,	'objawy autonomiczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	8,	'inne' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	1,	'nigdy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	2,	'w przesz這�ci' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	3,	'obecnie' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	1,	'mniej ni� 1/tyg.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	2,	'kilka / tydz.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	3,	'codziennie' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	1,	'mniej ni� 1/tyg.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	2,	'kilka / mies.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	3,	'codziennie niewiele' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	4,	'nadu篡wa' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	0,	'wie�' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	1,	'miasto' );

insert into Atrybut ( Tabela, Nazwa ) values ( 'Wizyta', 'NarazenieNaToks');

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	0,	'brak' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	1,	'zatrucie CO' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	2,	'toksyczne substancje przemys這we' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	3,	'narkotyki' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	1,	'brak' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	2,	'palidotomia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	3,	'talamotomia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	4,	'DBS STN' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	5,	'DBS Gpi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	6,	'DBS Vim' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	7,	'DBS PPN' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	1,	'zaburzenia r闚nowagi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	2,	'spowolnienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	3,	'sztywno��' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	4,	'dr瞠nie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	5,	'ot瘼ienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	6,	'dyskinezy i fluktuacje' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	7,	'objawy autonomiczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	8,	'inne' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	1,	'zaparcia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	2,	'objawy dyzuryczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	3,	'hipotonia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	4,	'nadmierna potliwo��' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	5,	'�linotok' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	6,	'這jotok' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	0,	'brak' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	4,	'4' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	5,	'5' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	6,	'6' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	7,	'7' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	8,	'8' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	9,	'9' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	10,	'10' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	11,	'11' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'WynikWechu',	12,	'12' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	4,	'4' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	5,	'5' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	6,	'6' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	7,	'7' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	8,	'8' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	9,	'9' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'LimitDysfagii',	10,	'10' );




-- insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'test', HashBytes('SHA1','pass'), 'U篡tkownik1', 'Testowy')
-- insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'testowy', HashBytes('SHA1','testowy'), 'U篡tkownik2', 'Testowy')

-- wywiad B


delete from SlownikDecimal where Atrybut = 'Otepienie'

insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	0,	'nie' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	0.5,	'MCI' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	1,	'tak' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	2,	'brak danych' );

// /

insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'STN',	'STN' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'Gpi',	'Gpi' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'Vim',	'Vim' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	0,	'0=norma' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	1,	'1=niewielkie zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	2,	'2=umiarkowane zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	3,	'3=powa積e zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	4,	'4=ci篹kie zaburzenia' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	0,	'brak okna' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	1,	'brak hyperechogeniczno�ci' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	2,	'hyperechogeniczno�� - L' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	4,	'hyperechogeniczno�� - P' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	6,	'hyperechogeniczno�� - Obydwie' );

insert into Atrybut ( Tabela, Nazwa ) values ( 'Wizyta', 'SPECTWynik');




insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',1,'prawid這wy rozk豉d znacznika, bez ogniskowych zaburze� perfuzji');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',2,'uog鏊nione zmniejszenie przep造wu krwi w obr瑿ie kory m霩gowej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',3,'zmniejszeni przep造wu krwi w obr瑿ie lewej wyspy');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',4,'zmniejszenie prezp造wu krwi w p豉cie skroniowym prawym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',5,'zmniejszenie przep造wu krwi obustronnie w zakr皻ach obr璚zy');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',6,'zmniejszenie przep造wu krwi w korze bruzdy ostrogowej obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',7,'zmniejszenie przep造wu krwi w korze bruzdy ostrogowej po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',8,'zmniejszenie przep造wu krwi w korze bruzdy ostrogowej po stronie�lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',9,'zmniejszenie przep造wu krwi w obr瑿ie j鉅er podkorowych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',10,'zmniejszenie przep造wu krwi w obr瑿ie j鉅er podkorowych po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',11,'zmniejszenie przep造wu krwi w obr瑿ie j鉅er podkorowych po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',12,'zmniejszenie przep造wu krwi w obr瑿ie lewego wzg鏎za');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',13,'zmniejszenie przep造wu krwi w obr瑿ie p豉ta potylicznego po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',14,'zmniejszenie przep造wu krwi w obr瑿ie p豉ta potylicznego po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',15,'zmniejszenie przep造wu krwi w obr瑿ie prawego wzg鏎za');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',16,'zmniejszenie przep造wu krwi w obr瑿ie prawej wyspy');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',17,'zmniejszenie przep造wu krwi w obr瑿ie skorupy obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',18,'zmniejszenie przep造wu krwi w obr瑿ie skorupy po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',19,'zmniejszenie przep造wu krwi w obr瑿ie skorupy po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',20,'zmniejszenie przep造wu krwi w obr瑿ie wysp obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',21,'zmniejszenie przep造wu krwi w p豉cie czo這wym lewym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',22,'zmniejszenie przep造wu krwi w p豉cie czo這wym prawym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',23,'zmniejszenie przep造wu krwi w p豉cie skroniowym lewym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',24,'zmniejszenie przep造wu krwi w p豉tach skroniowych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',25,'zmniejszenie przep造wu krwi w zakr皻ach potylicznych dolnych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',26,'zmniejszenie przep造wu w p豉tach czo這wych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',27,'zmniejszenie�przep造wu krwi w zakr皻ach czo這wych �rodkowych');




-- warainty A

-- DBS
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'DBS',	0,	'OFF' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'DBS',	1,	'ON-L' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'DBS',	2,	'ON-P' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'DBS',	3,	'ON-LP' );


-- UPDRS_I
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 0, '0'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 1, '1'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 2, '2'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 3, '3'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 4, '4'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 5, '5'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 6, '6'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 7, '7'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 8, '8'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 9, '9'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 10, '10'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 11, '11'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 12, '12'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 13, '13'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 14, '14'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 15, '15'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_I', 16, '16'); 


-- UPDRS_II
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 0, '0'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 1, '1'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 2, '2'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 3, '3'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 4, '4'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 5, '5'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 6, '6'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 7, '7'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 8, '8'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 9, '9'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 10, '10'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 11, '11'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 12, '12'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 13, '13'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 14, '14'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 15, '15'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 16, '16'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 17, '17'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 18, '18'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 19, '19'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 20, '20'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 21, '21'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 22, '22'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 23, '23'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 24, '24'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 25, '25'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 26, '26'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 27, '27'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 28, '28'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 29, '29'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 30, '30'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 31, '31'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 32, '32'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 33, '33'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 34, '34'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 35, '35'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 36, '36'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 37, '37'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 38, '38'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 39, '39'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 40, '40'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 41, '41'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 42, '42'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 43, '43'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 44, '44'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 45, '45'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 46, '46'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 47, '47'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 48, '48'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 49, '49'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 50, '50'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 51, '51'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_II', 52, '52'); 


-- UPDRS_18
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_18',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_18',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_18',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_18',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_18',	4,	'4' );


-- UPDRS_19 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_19',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_19',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_19',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_19',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_19',	4,	'4' );

-- UPDRS_20_FaceLipsChin
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_FaceLipsChin',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_FaceLipsChin',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_FaceLipsChin',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_FaceLipsChin',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_FaceLipsChin',	4,	'4' );

-- UPDRS_20_RHand
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RHand',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RHand',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RHand',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RHand',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RHand',	4,	'4' );

-- UPDRS_20_LHand
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LHand',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LHand',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LHand',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LHand',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LHand',	4,	'4' );

-- UPDRS_20_RFoot
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RFoot',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RFoot',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RFoot',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RFoot',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_RFoot',	4,	'4' );

-- UPDRS_20_LFoot
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LFoot',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LFoot',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LFoot',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LFoot',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_20_LFoot',	4,	'4' );

-- UPDRS_21_RHand
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_RHand',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_RHand',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_RHand',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_RHand',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_RHand',	4,	'4' );

-- UPDRS_21_LHand
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_LHand',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_LHand',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_LHand',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_LHand',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_21_LHand',	4,	'4' );

-- UPDRS_22_Neck
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_Neck',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_Neck',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_Neck',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_Neck',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_Neck',	4,	'4' );

-- UPDRS_22_RHand
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RHand',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RHand',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RHand',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RHand',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RHand',	4,	'4' );

-- UPDRS_22_LHand
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LHand',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LHand',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LHand',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LHand',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LHand',	4,	'4' );

-- UPDRS_22_RFoot
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RFoot',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RFoot',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RFoot',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RFoot',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_RFoot',	4,	'4' );

-- UPDRS_22_LFoot
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LFoot',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LFoot',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LFoot',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LFoot',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_22_LFoot',	4,	'4' );

-- UPDRS_23_R
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_R',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_R',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_R',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_R',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_R',	4,	'4' );

-- UPDRS_23_L
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_L',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_L',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_L',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_L',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_23_L',	4,	'4' );

-- UPDRS_24_R
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_R',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_R',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_R',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_R',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_R',	4,	'4' );

-- UPDRS_24_L
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_L',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_L',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_L',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_L',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_24_L',	4,	'4' );

-- UPDRS_25_R
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_R',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_R',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_R',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_R',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_R',	4,	'4' );

-- UPDRS_25_L
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_L',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_L',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_L',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_L',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_25_L',	4,	'4' );

-- UPDRS_26_R
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_R',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_R',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_R',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_R',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_R',	4,	'4' );

-- UPDRS_26_L
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_L',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_L',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_L',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_L',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_26_L',	4,	'4' );

-- UPDRS_27
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_27',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_27',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_27',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_27',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_27',	4,	'4' );

-- UPDRS_28
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_28',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_28',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_28',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_28',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_28',	4,	'4' );

-- UPDRS_29
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_29',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_29',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_29',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_29',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_29',	4,	'4' );

-- UPDRS_30
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_30',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_30',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_30',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_30',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_30',	4,	'4' );

-- UPDRS_31
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_31',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_31',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_31',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_31',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'UPDRS_31',	4,	'4' );

-- UPDRS_III
-- N/A 

-- UPDRS_IV
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 0, '0'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 1, '1'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 2, '2'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 3, '3'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 4, '4'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 5, '5'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 6, '6'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 7, '7'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 8, '8'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 9, '9'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 10, '10'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 11, '11'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 12, '12'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 13, '13'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 14, '14'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 15, '15'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 16, '16'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 17, '17'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 18, '18'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 19, '19'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 20, '20'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 21, '21'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 22, '22'); 
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie','UPDRS_IV', 23, '23'); 



insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	0,	'0%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	10,	'10%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	20,	'20%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	30,	'30%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	40,	'40%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	50,	'50%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	60,	'60%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	70,	'70%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	80,	'80%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	90,	'90%' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'SchwabEnglandScale',	100,	'100%' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'OkulografiaUrzadzenie',	0,	'LatencyMeter' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'OkulografiaUrzadzenie',	1,	'JazzNovo' );


insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	0,	'0' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	1,	'1' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	1.5,	'1.5' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	2,	'2' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	2.5,	'2.5' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	3,	'3' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	4,	'4' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'HYscale',	5,	'5' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'TandemPivot',	0,	'0' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'TandemPivot',	1,	'1' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'TandemPivot',	2,	'2' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'TandemPivot',	3,	'3' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'TandemPivot',	4,	'4' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Badanie',	'TandemPivot',	5,	'5' );


*/



/*

declare @res int;
declare @message varchar(200);

exec update_patient 'NUMER/TESTOWY', 1922, 12, 2, 'yxz', 2, 1, @res OUTPUT, @message OUTPUT;

	select @res, @message;

	select * from Pacjent


*/

-- testowanie wprowadzania wizyt
-- ==============================
/*



declare @message varchar(200);
declare @res int;
exec update_patient_l '2013-08-07/TEST1', 1951, 11, 1, 'Gpi', 2, 1, testowy, @res OUTPUT, @message OUTPUT;
	select @message, @res;

	select * from Pacjent

declare @message varchar(200);
declare @res int;
declare @vis_id int;
exec update_examination_questionnaire_partA '2013-08-06/TEST1', 1, '2012-11-23', '2012-11-23', '2012-11-24', 50, 2, 1, 2008, 1, 2012, 11, 6, 1, 0, 1, 1, 'Inne jakie', 3, 1, 2, 0, null, 2.5, 2.5, 1,
 1, 'test', @res OUTPUT, @vis_id OUTPUT, @message OUTPUT;
	select @message as Message, @vis_id as Visit_ID, @res as Result;
go

	select * from Wizyta

declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partB 1, 1, 1, 2, 0, 1, 0, 1, 'uwagi', 1, 1, 2, 'dom.objaw uwagi', null, 1, 0, 0, 1, 1, 2, 1, 1, 1, 2, 1,
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;

	select * from Wizyta
go

declare @vals as IntValueListUdt;
declare @res int;
insert into @vals values ( 1 );
insert into @vals values ( 2 );

exec update_exam_int_attribute  1, 'NarazenieNaToks', @vals, 'test', @res OUTPUT
select @res;
select * from WartoscAtrybutuWizytyInt


declare @vals as IntValueListUdt;
declare @res int;
insert into @vals values ( 1 );
insert into @vals values ( 3 );

exec update_exam_int_attribute  1, 'SPECTWynik', @vals, 'test', @res OUTPUT
select @res;
select * from WartoscAtrybutuWizytyInt

select * from dbo.get_exam_multi_values( 'SPECTWynik', 1 )


declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partC 1, 
	1, 10, 1, 30, 0, null, 0, null, 0, null, 1, 30, 1, 'leki inne xyz',
	'opis LS', 3300.1, 2200.1, 1000.0, 'opis RS', 2000.1, 1200.1, 1000.0, 
	null, null, null, null, null, null, null, null, null, null, null, null, null, null,
	'opis LS', 3300.1, 2200.1, 1000.0, 'opis RS', 2000.1, 1200.1, 1000.0, 
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;

	select * from Wizyta
go

declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partD 1, 3, 1, 0, 3, 'uwagi', 1, 12, 10,
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;

	select dbo.validate_input_int('Wizyta', 'LimitDysfagii', 10)
	select dbo.validate_input_int('Wizyta', 'WynikWechu', 10)

	select * from Wizyta
go

declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partE 1, 
	0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1,
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;
	select * from Wizyta
go

declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partF 1, 
	4, 7, 10, 13, 17,
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;

	select * from Wizyta
go


declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partG 1, 
	1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 'psychologiczne inne', 'opis', 'wnioski',
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;

	select * from Wizyta
go



declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partH 1, 
	1, 
	1, 5, 10,
	0, 1, 1, 'MRI wynik', 1, 2, 1, 'genetyka wynik', 1, 'surowica pozosta這',
	3333.333, 1111.111, 2222.222, 3333.333, 1111.111, 2222.222, 3333.333, 1111.111, 2222.222, 4444.444, 'wynik laboratoryjne',
 'test', @res OUTPUT,  @message OUTPUT;
	select @message as Message, @res as Result;

	select * from Wizyta
go

-- testowanie wariantow


declare @message varchar(200);
declare @res int;
declare @variant_id int;
exec
update_variant_examination_data_partA  1, 3, 0,
14,
52,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
3,
40,
23,
87,
2.5,
70,
1,
1,
0,
1,
2222.00, 2222.00, 2222.00, 2222.00,
2222.00, 2222.00, 2222.00, 2222.00,
2222.00, 2222.00, 2222.00, 2222.00,
'test',
@res OUTPUT, @variant_id OUTPUT, @message OUTPUT
	select @message as Message, @res as Result, @variant_id as IdBadanie;

	select * from Badanie


declare @message varchar(200);
declare @res int;

exec
update_variant_examination_data_partB  1,
1,
44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50,
44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50, 44444.50,
1, 11.11, 11.11, 1, 22.22, 22.22,
1, 0,
'test',
@res OUTPUT, @message OUTPUT
	select @message as Message, @res as Result;

	select * from Badanie

declare @message varchar(200);
declare @res int;

exec
update_variant_examination_data_partC  1,
	30.1,
	28.1,
	10.2,
	10.3,
	0.5,
	5,
	11.9,
'test',
@res OUTPUT, @message OUTPUT
	select @message as Message, @res as Result;

	select * from Badanie

*/