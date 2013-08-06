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
-- @res codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message
create procedure update_patient  (	@NumerPacjenta	varchar(20), @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @allow_update_existing bit, @result int OUTPUT, @message varchar(200) OUTPUT )
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
		set NumerPacjenta = @NumerPacjenta, RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod
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


-- last rev. 2013-08-05
-- Dodano sufiks nazwy: _partA
-- @res codes: 0 = OK, 1 = visit already exists while run in no-update mode,  exist 2 = validation failed - see message, 3 = patient of this number not found, 4 = user login unknown
alter procedure update_examination_questionnaire_partA  (@NumerPacjenta varchar(20), @RodzajWizyty tinyint,
	@DataPrzyjecia date,	@DataWypisu date,
	@Wyksztalcenie	tinyint, @Rodzinnosc tinyint, @RokZachorowania	smallint, @MiesiacZachorowania tinyint,
	@PierwszyObjaw	tinyint, @CzasOdPoczObjDoWlLDopy	tinyint, @DyskinezyObecnie	tinyint, @CzasDyskinez	decimal(3,1), @FluktuacjeObecnie tinyint, @FluktuacjeOdLat	decimal(3,1), @Papierosy tinyint,
	@Kawa	tinyint, @ZielonaHerbata tinyint,	@Alkohol	tinyint, @ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,	@Zamieszkanie tinyint, @NarazenieNaToks	tinyint, 
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

	if dbo.validate_input_int('Wizyta', 'PierwszyObjaw', @PierwszyObjaw) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute PierwszyObjaw';
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
	if dbo.validate_input_int('Wizyta', 'NarazenieNaToks', @NarazenieNaToks) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute NarazenieNaToks';
		return;
	end;
	-- /validations
	-- depending on update
	if(@update = 0)
		begin
		insert into Wizyta (	RodzajWizyty, IdPacjent, DataPrzyjecia, DataWypisu, Wyksztalcenie,	Rodzinnosc,	RokZachorowania, MiesiacZachorowania, PierwszyObjaw, CzasOdPoczObjDoWlLDopy, DyskinezyObecnie,
								CzasDyskinez, FluktuacjeObecnie, FluktuacjeOdLat,	Papierosy,	Kawa, ZielonaHerbata, Alkohol, ZabiegowWZnieczOgPrzedRozpoznaniemPD,
								Zamieszkanie, NarazenieNaToks, Wprowadzil )
					values (	@RodzajWizyty, @patient_id, @DataPrzyjecia, @DataWypisu, @Wyksztalcenie,	@Rodzinnosc, @RokZachorowania, @MiesiacZachorowania, @PierwszyObjaw, @CzasOdPoczObjDoWlLDopy, @DyskinezyObecnie,
								@CzasDyskinez, @FluktuacjeObecnie, @FluktuacjeOdLat, @Papierosy,	@Kawa, @ZielonaHerbata, @Alkohol, @ZabiegowWZnieczOgPrzedRozpoznaniemPD,
								@Zamieszkanie, @NarazenieNaToks, @user_id )  set @visit_id = SCOPE_IDENTITY();
		end;
	else
		begin
		update Wizyta
		set DataPrzyjecia = @DataPrzyjecia, DataWypisu = @DataWypisu, Wyksztalcenie = @Wyksztalcenie,	Rodzinnosc = @Rodzinnosc,	RokZachorowania = @RokZachorowania, 
				MiesiacZachorowania = @MiesiacZachorowania, PierwszyObjaw = @PierwszyObjaw, CzasOdPoczObjDoWlLDopy = @CzasOdPoczObjDoWlLDopy, DyskinezyObecnie = @DyskinezyObecnie,
								CzasDyskinez = @CzasDyskinez, FluktuacjeObecnie = @FluktuacjeObecnie, FluktuacjeOdLat = @FluktuacjeOdLat,	Papierosy = @Papierosy,	Kawa = @Kawa, 
								ZielonaHerbata = @ZielonaHerbata, Alkohol = @Alkohol, ZabiegowWZnieczOgPrzedRozpoznaniemPD = @ZabiegowWZnieczOgPrzedRozpoznaniemPD,
								Zamieszkanie = @Zamieszkanie, NarazenieNaToks = @NarazenieNaToks 
		where RodzajWizyty = @RodzajWizyty and IdPacjent = @patient_id  set @visit_id = SCOPE_IDENTITY();
		end;
	return;
end;
go


-- created 2013-08-05

-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partB  (@IdWizyta int,
	@RLS	tinyint,
	@ObjawyPsychotyczne	tinyint,
	@Depresja	tinyint,
	@Otepienie	decimal(2,1),
	@Dyzartria 	tinyint,
	@RBD	tinyint,
	@ZaburzenieRuchomosciGalekOcznych	tinyint,
	@Apraksja	tinyint,
	@TestKlaskania	tinyint,
	@ZaburzeniaWechowe	tinyint,
	@MasaCiala	decimal(4,1),
	@Drzenie	tinyint,
	@Sztywnosc	tinyint,
	@Spowolnienie	tinyint,
	@ObjawyInne	tinyint,
	@ObjawyInneJakie	varchar(80),
	@CzasOFF	tinyint,
	@PoprawaPoLDopie	tinyint,
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

	if( dbo.validate_ext_bit( @PoprawaPoLDopie) = 0 )
	begin
		set @result = 2;
		set @message = 'PoprawaPoLDopie - invalid value. Allowed: 0, 1, 2.';
		return;
	end;


	update Wizyta
		set 
			RLS	= @RLS,
			ObjawyPsychotyczne	= @ObjawyPsychotyczne,
			Depresja	= @Depresja,
			Otepienie	= @Otepienie,
			Dyzartria 	= @Dyzartria,
			RBD	= @RBD,
			ZaburzenieRuchomosciGalekOcznych	= @ZaburzenieRuchomosciGalekOcznych,
			Apraksja	= @Apraksja,
			TestKlaskania	= @TestKlaskania,
			ZaburzeniaWechowe = @ZaburzeniaWechowe,
			MasaCiala	= @MasaCiala,
			Drzenie	= @Drzenie,
			Sztywnosc	= @Sztywnosc,
			Spowolnienie	= @Spowolnienie,
			ObjawyInne	= @ObjawyInne,
			ObjawyInneJakie	= @ObjawyInneJakie,
			CzasOFF	= @CzasOFF,
			PoprawaPoLDopie	= @PoprawaPoLDopie
		where IdWizyta = @IdWizyta;

	return;
end;
go

-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
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
			LekiInneJakie = @LekiInneJakie
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partD  (
	@IdWizyta int,
	@PrzebyteLeczenieOperacyjnePD tinyint,
	@Nadcisnienie tinyint,
	@BlokeryKanWapn tinyint,
	@DominujacyObjawObecnie tinyint,
	@Dominuj¹cyObjawUwagi varchar(50),
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



	update Wizyta
		set 
			PrzebyteLeczenieOperacyjnePD  = @PrzebyteLeczenieOperacyjnePD,
			Nadcisnienie  = @Nadcisnienie,
			BlokeryKanWapn  = @BlokeryKanWapn,
			DominujacyObjawObecnie  = @DominujacyObjawObecnie,
			Dominuj¹cyObjawUwagi  = @Dominuj¹cyObjawUwagi,
			BadanieWechu  = @BadanieWechu,
			WynikWechu  = @WynikWechu,
			LimitDysfagii  = @LimitDysfagii
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
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
			KlopotyUtrzymaniaErekcji = @KlopotyUtrzymaniaErekcji
		where IdWizyta = @IdWizyta;

	return;
end;
go

-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
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

-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
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
			InnePsychologiczne = @InnePsychologiczne
		where IdWizyta = @IdWizyta;

	return;
end;
go

-- @res codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partH  (
	@IdWizyta int,
	@Holter bit,
	@pH_metriaPrze³yku bit,
	@SPECT bit,
	@MRI bit,
	@MRIwynik varchar(50),
	@USGsrodmozgowia tinyint,
	@USGWynik tinyint,
	@KwasMoczowy decimal(6,2),
	@Genetyka bit,
	@GenetykaWynik varchar(50),
	@Surowica bit,
	@SurowicaPozosta³o varchar(50),
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
			pH_metriaPrze³yku = @pH_metriaPrze³yku,
			SPECT = @SPECT,
			MRI = @MRI,
			MRIwynik = @MRIwynik,
			USGsrodmozgowia = @USGsrodmozgowia,
			USGWynik = @USGWynik,
			KwasMoczowy = @KwasMoczowy,
			Genetyka = @Genetyka,
			GenetykaWynik = @GenetykaWynik,
			Surowica = @Surowica,
			SurowicaPozosta³o = @SurowicaPozosta³o
		where IdWizyta = @IdWizyta;

	return;
end;
go


	if dbo.validate_input_decimal('Wizyta', 'xxx', @xxx) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute xxx - see enumeration for allowed values';
		return;
	end;

	if( dbo.validate_ext_bit( @Dxxx ) = 0 )
	begin
		set @result = 2;
		set @message = 'xxx  - invalid value. Allowed: 0, 1, 2.';
		return;
	end;





/*




-- wywiad A (czêœæ epidemiologiczna)

insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0,	'przedoperacyjna' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0.5,	'po pó³ roku' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	1,	'rok po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	2,	'2 lata po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	3,	'3 lata po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	4,	'4 lata po DBS' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	5,	'5 lata po DBS' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	1,	'podstawowe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	2,	'zawodowe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	3,	'?rednie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	4,	'wy¿sze' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	1,	'zaburzenia równowagi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	2,	'spowolnienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	3,	'sztywnoœæ' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	4,	'dr¿enie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	5,	'otêpienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	6,	'dyskinezy i fluktuacje' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	7,	'objawy autonomiczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	8,	'inne' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	1,	'nigdy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	2,	'w przesz³oœci' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	3,	'obecnie' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	1,	'mniej ni¿ 1/tyg.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	2,	'kilka / tydz.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	3,	'codziennie' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	1,	'mniej ni¿ 1/tyg.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	2,	'kilka / mies.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	3,	'codziennie niewiele' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	4,	'nadu¿ywa' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	0,	'wie?' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	1,	'miasto' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	0,	'brak' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	1,	'zatrucie CO' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	2,	'toksyczne substancje przemys³owe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	3,	'narkotyki' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	1,	'brak' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	2,	'palidotomia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	3,	'talamotomia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	4,	'DBS STN' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	5,	'DBS Gpi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	6,	'DBS Vim' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PrzebyteLeczenieOperacyjnePD',	7,	'DBS PPN' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	1,	'zaburzenia równowagi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	2,	'spowolnienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	3,	'sztywnoœæ' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	4,	'dr¿enie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	5,	'otêpienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	6,	'dyskinezy i fluktuacje' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	7,	'objawy autonomiczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	8,	'inne' );





insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'test', HashBytes('SHA1','pass'), 'U¿ytkownik1', 'Testowy')
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'testowy', HashBytes('SHA1','testowy'), 'U¿ytkownik2', 'Testowy')

-- wywiad B

insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	0,	'brak' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	0.5,	'czêœciowo' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	1,	'tak' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	2,	'brak danych' );



insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'STN',	'STN' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'Gpi',	'Gpi' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'Vim',	'Vim' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	0,	'norma' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	1,	'niewielkie zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	2,	'umiarkowane zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	3,	'powa¿ne zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	4,	'ciê¿kie zaburzenia' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	0,	'brak okna' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	1,	'brak hyperechogenicznoœci' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	2,	'hyperechogenicznoœæ' );

*/



/*

declare @res int;
declare @message varchar(200);

exec update_patient 'NUMER/TESTOWY', 1922, 12, 2, 'yxz', 2, 1, @res OUTPUT, @message OUTPUT;

select @res, @message;

select * from Pacjent


declare @res int;
declare @message varchar(200);

exec update_examination_questionnaire  'NUMER/TESTOWY', 0, 3, 2, 1999, 11,
	3, 1, 1, 5.5, 0, null, 
	1, 1, 1, 2, 1, 1, 2,
	1,
	@res OUTPUT, @message OUTPUT 

select @res, @message;

select * from Wizyta
@NumerPacjenta varchar(20), @RodzajWizyty tinyint, @Wyksztalcenie	tinyint, @Rodzinnosc tinyint, @RokZachorowania	smallint, @MiesiacZachorowania tinyint,
	@PierwszyObjaw	tinyint, @CzasOdPoczObjDoWlLDopy	tinyint, @DyskinezyObecnie	tinyint, @CzasDyskinez	decimal(3,1), @FluktuacjeObecnie tinyint, @FluktuacjeOdLat	decimal(3,1), 
	@Papierosy tinyint,	@Kawa	tinyint, @ZielonaHerbata tinyint,	@Alkohol	tinyint, @ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,	@Zamieszkanie tinyint, @NarazenieNaToks	tinyint, 
	@allow_updtate_existing bit,
	@result int OUTPUT, @message varchar(200) OUTPUT

*/
select * from SlownikInt

-- testowanie wprowadzania wizyt
-- ==============================
/*



declare @message varchar(200);
declare @res int;
exec update_patient '2013-08-06/TEST1', 1950, 11, 1, 'Gpi', 2, 1, @res OUTPUT, @message OUTPUT;
select @message, @res;

declare @message varchar(200);
declare @res int;
declare @vis_id int;
exec update_examination_questionnaire_partA '2013-08-06/TEST1', 0, '2011-11-12', '2011-11-13', 2, 1, 2008, 1, 6, 1, 1, 3.5, 1, 2.5, 1,
 1, 0, 2, 1, 0, 0, 
 1, 'test', @res OUTPUT, @vis_id OUTPUT, @message OUTPUT;
select @message as Message, @vis_id as Visit_ID, @res as Result;

declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partB 1, 1, 2, 0, 0.5, 0, 2, 1, 1, 1, 2, 100.5, 0, 1, 0, 1, 'jeszcze inne', 10, 1,
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta


declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partC 1, 1, 10, 1, 30, 0, null, 0, null, 0, null, 1, 30, 1, 'leki inne xyz',
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta


declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partD 1, 3, 1, 0, 3, 'uwagi', 1, 100, 200,
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta


declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partE 1, 
	0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1,
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta

declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partF 1, 
	4, 7, 10, 13, 17,
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta



declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partG 1, 
	1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 'psychologiczne inne',
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta



declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partH 1, 
	1, 0, 1, 1, 'MRI wynik', 1, 2, 300.55, 1, 'genetyka wynik', 1, 'surowica pozosta³o',
 'test', @res OUTPUT,  @message OUTPUT;
select @message as Message, @res as Result;

select * from Wizyta