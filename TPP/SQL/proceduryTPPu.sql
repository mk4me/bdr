use TPP_test;
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

/* -- deleted 2014-01-20
create function disorder_duration( @start_year smallint )
returns decimal(4,2)
as
begin
return CAST(datediff(day, CAST( CAST(@start_year as varchar)+'-'+ CAST(1 as varchar)+'-'+ CAST(1 as varchar) as datetime), getDate() )/365.0 as decimal(4,2))
end
go
*/


-- created 2015-07-19
create function multivalued_examination_attribute(@attrib_name varchar(50), @patient_id int, @exam_kind int)
returns varchar(75)
as
begin
return (select Wartosci from AtrybutyWielowartoscioweWizyty aww join Wizyta w on w.IdWizyta = aww.IdWizyta 
		where aww.NazwaAtrybutu = @attrib_name and w.IdPacjent = @patient_id and w.RodzajWizyty = @exam_kind )
end
go

-- last rev. 2014-01-20
create function disorder_duration_for_examination( @IdWizyta int )
returns decimal(4,2)
as
begin
return CAST(
	datediff(	day, 
				CAST( CAST((select max(RokZachorowania) from Wizyta w where w.IdPacjent = (select x.IdPacjent from Wizyta x where IdWizyta = @IdWizyta)) as varchar)+'-'+ CAST(1 as varchar)+'-'+ CAST(1 as varchar) as datetime), 
				(select DataPrzyjecia from Wizyta where IdWizyta = @IdWizyta) )/365.0 as decimal(4,2))
end
go



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

-- last rev. 2015-06-05
-- @result codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message
alter procedure update_patient  (	@NumerPacjenta	varchar(20), @NazwaGrupy varchar(3), @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @ZakonczenieUdzialu varchar(255), @allow_update_existing bit, @result int OUTPUT, @message varchar(200) OUTPUT )
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

	if( dbo.validate_input_varchar('Pacjent', 'NazwaGrupy', @NazwaGrupy) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Nazwa grupy - invalid enum value.';
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

	if(@NazwaGrupy <> 'BMT')
	begin
		if( dbo.validate_input_varchar('Pacjent', 'Lokalizacja', @Lokalizacja) = 0 )
		begin
			set @result = 2;
			set @message = @message + 'Lokalizacja - invalid enum value.';
			return;
		end;
	end;

	if(@update = 0)
		insert into Pacjent (NumerPacjenta, NazwaGrupy, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod, ZakonczenieUdzialu )
					values (@NumerPacjenta, @NazwaGrupy, @RokUrodzenia, @MiesiacUrodzenia, @Plec, @Lokalizacja, @LiczbaElektrod, @ZakonczenieUdzialu	 );
	else
		update Pacjent
		set RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod, ZakonczenieUdzialu = @ZakonczenieUdzialu
		where NumerPacjenta = @NumerPacjenta;		
end;
go
-- last rev. 2015-06-05
-- @result codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message, 3 = login user not found
create procedure update_patient_l  (	@NumerPacjenta	varchar(20), @NazwaGrupy varchar(3),  @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @ZakonczenieUdzialu varchar(255), @allow_update_existing bit, @actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
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

	if( dbo.validate_input_varchar('Pacjent', 'NazwaGrupy', @NazwaGrupy) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Nazwa grupy - invalid enum value.';
		return;
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

	if(@NazwaGrupy <> 'BMT')
	begin
		if( dbo.validate_input_varchar('Pacjent', 'Lokalizacja', @Lokalizacja) = 0 )
		begin
			set @result = 2;
			set @message = @message + 'Lokalizacja - invalid enum value.';
			return;
		end;
	end;

	if(@update = 0)
		insert into Pacjent (NumerPacjenta, NazwaGrupy, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod, ZakonczenieUdzialu, OstatniaZmiana, Wprowadzil, Zmodyfikowal )
					values (@NumerPacjenta, @NazwaGrupy, @RokUrodzenia, @MiesiacUrodzenia, @Plec, @Lokalizacja, @LiczbaElektrod, @ZakonczenieUdzialu, getdate(), @userid, @userid	 );
	else
		update Pacjent
		set RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod,
		ZakonczenieUdzialu = @ZakonczenieUdzialu,
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


-- last rev. 2015-07-20
-- @result codes: 0 = OK, 1 = visit already exists while run in no-update mode,  exist 2 = validation failed - see message, 3 = patient of this number not found, 4 = user login unknown
create procedure update_examination_questionnaire_partA  (@NumerPacjenta varchar(20), @RodzajWizyty tinyint,
	@DataPrzyjecia date,	@DataOperacji date,	@DataWypisu date, @MasaCiala decimal(4,1),
	@Wyksztalcenie	tinyint, @Rodzinnosc tinyint, @RokZachorowania	smallint, 
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

	if dbo.validate_input_int('Wizyta', 'RodzajWizyty', @RodzajWizyty) = 0
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
		insert into Wizyta (	RodzajWizyty, IdPacjent, DataPrzyjecia, DataOperacji, DataWypisu, MasaCiala, Wyksztalcenie,	Rodzinnosc,	RokZachorowania, PierwszyObjaw, 
								Drzenie, Sztywnosc,	Spowolnienie, ObjawyInne, ObjawyInneJakie,
								CzasOdPoczObjDoWlLDopy, DyskinezyObecnie,
								DyskinezyOdLat, FluktuacjeObecnie, FluktuacjeOdLat,	CzasDyskinez, CzasOFF, PoprawaPoLDopie,
								Wprowadzil, Zmodyfikowal, OstatniaZmiana )
					values (	@RodzajWizyty, @patient_id, @DataPrzyjecia, @DataOperacji, @DataWypisu, @MasaCiala, @Wyksztalcenie,	@Rodzinnosc, @RokZachorowania, @PierwszyObjaw, 
								@Drzenie, @Sztywnosc,@Spowolnienie, @ObjawyInne, REPLACE(@ObjawyInneJakie,';','. '),
								@CzasOdPoczObjDoWlLDopy, @DyskinezyObecnie,
								@DyskinezyOdLat, @FluktuacjeObecnie, @FluktuacjeOdLat, @CzasDyskinez, @CzasOFF, @PoprawaPoLDopie,
								@user_id, @user_id, getdate() )  set @visit_id = SCOPE_IDENTITY();
		end;
	else
		begin
		update Wizyta
		set DataPrzyjecia = @DataPrzyjecia, DataOperacji = @DataOperacji, DataWypisu = @DataWypisu, MasaCiala = @MasaCiala, Wyksztalcenie = @Wyksztalcenie,	Rodzinnosc = @Rodzinnosc,	RokZachorowania = @RokZachorowania, 
				PierwszyObjaw = @PierwszyObjaw, 
				Drzenie	= @Drzenie,
				Sztywnosc	= @Sztywnosc,
				Spowolnienie	= @Spowolnienie,
				ObjawyInne	= @ObjawyInne,
				ObjawyInneJakie	= REPLACE(@ObjawyInneJakie,';','. '),
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






-- drop procedure update_examination_questionnaire_partB
-- last rev. 2015-07-19
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
			Uwagi = REPLACE(@Uwagi,';','. '), 
			Nadcisnienie  = @Nadcisnienie,
			BlokeryKanWapn  = @BlokeryKanWapn,
			DominujacyObjawObecnie  = @DominujacyObjawObecnie,
			DominujacyObjawUwagi  = REPLACE(@DominujacyObjawUwagi,';','. '),

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


-- modified: 2015-07-19
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
			LekiInneJakie = REPLACE(@LekiInneJakie,';','. '),
			L_STIMOpis = REPLACE(@L_STIMOpis,';','. '),
			L_STIMAmplitude = @L_STIMAmplitude,
			L_STIMDuration = @L_STIMDuration,
			L_STIMFrequency = @L_STIMFrequency,
			R_STIMOpis = REPLACE(@R_STIMOpis,';','. '),
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
			Wypis_LekiInneJakie = REPLACE(@Wypis_LekiInneJakie,';','. '),
			Wypis_L_STIMOpis = REPLACE(@Wypis_L_STIMOpis,';','. '),
			Wypis_L_STIMAmplitude = @Wypis_L_STIMAmplitude,
			Wypis_L_STIMDuration = @Wypis_L_STIMDuration,
			Wypis_L_STIMFrequency = @Wypis_L_STIMFrequency,
			Wypis_R_STIMOpis = REPLACE(@Wypis_L_STIMOpis,';','. '),
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

-- updated (temp. name) 2013-11-21
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partE_x01  (
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
	@WzrostPotliwosciBrzuchPlecy tinyint,
	@WzrostPotliwosciKonczynyDolneStopy	tinyint,
	@SpadekPodtliwosciTwarzKark	tinyint,
	@SpadekPotliwosciRamionaDlonie	tinyint,
	@SpadekPotliwosciBrzuchPlecy tinyint,
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

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @WzrostPotliwosciBrzuchPlecy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @WzrostPotliwosciBrzuchPlecy - see enumeration for allowed values';
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

	if dbo.validate_input_int('Wizyta', '_ObjawAutonomiczny', @SpadekPotliwosciBrzuchPlecy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute @SpadekPotliwosciBrzuchPlecy - see enumeration for allowed values';
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
			WzrostPotliwosciBrzuchPlecy = @WzrostPotliwosciBrzuchPlecy,
			WzrostPotliwosciKonczynyDolneStopy = @WzrostPotliwosciKonczynyDolneStopy,
			SpadekPodtliwosciTwarzKark = @SpadekPodtliwosciTwarzKark,
			SpadekPotliwosciRamionaDlonie = @SpadekPotliwosciRamionaDlonie,
			SpadekPotliwosciBrzuchPlecy = @SpadekPotliwosciBrzuchPlecy,
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


/*
-- last rev. 2014-01-13
-- REPLACED 2015-03-20
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partG  (
	@IdWizyta int,
	@TestZegara bit,
	@MMSE tinyint,
	@WAIS_R_Wiadomosci tinyint,
	@WAIS_R_PowtarzanieCyfr tinyint,
	@SkalaDepresjiBecka tinyint,
	@TestFluencjiZwierzeta varchar(40),
	@TestFluencjiOstre varchar(40),
	@TestFluencjiK varchar(40),
	@TestLaczeniaPunktowA varchar(40),
	@TestLaczeniaPunktowB varchar(40),
	@TestAVLTSrednia varchar(40),
	@TestAVLTOdroczony varchar(40),
	@TestAVLTPo20min varchar(40),
	@TestAVLTRozpoznawanie varchar(40),
	@TestStroopa varchar(40),
	@TestMinnesota varchar(40),
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
			TestAVLTSrednia = @TestAVLTSrednia,
			TestAVLTOdroczony = @TestAVLTOdroczony,
			TestAVLTPo20min = @TestAVLTPo20min,
			TestAVLTRozpoznawanie = @TestAVLTRozpoznawanie,
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
*/


-- last rev. 2015-07-20
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partG  (
	@IdWizyta int,
	@TestZegara bit,	-- juz bylo; niezmienione
	@MMSE tinyint,	-- juz bylo; niezmienione
	@CLOX1_Rysunek tinyint, -- dodane 2015-03-20
	@CLOX2_Kopia tinyint, -- dodane 2015-03-20
	@AVLT_proba_1 tinyint,-- dodane 2015-03-20
	@AVLT_proba_2 tinyint,-- dodane 2015-03-20
	@AVLT_proba_3 tinyint,-- dodane 2015-03-20
	@AVLT_proba_4 tinyint,-- dodane 2015-03-20
	@AVLT_proba_5 tinyint,-- dodane 2015-03-20
	@AVLT_Suma tinyint,-- dodane 2015-03-20
	@AVLT_Srednia decimal(4,2), -- dodane 2015-03-20
	@AVLT_KrotkieOdroczenie tinyint,-- dodane 2015-03-20
	@AVLT_Odroczony20min decimal(4,2), -- dodane 2015-03-20
	@AVLT_Rozpoznawanie tinyint,-- dodane 2015-03-20
	@AVLT_BledyRozpoznania tinyint,-- dodane 2015-03-20

	@TestAVLTSrednia varchar(40), -- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	@TestAVLTOdroczony varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	@TestAVLTPo20min varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane
	@TestAVLTRozpoznawanie varchar(40),-- pozostawione jako redundantne ze wzgledu na zgromadzone dane

	@CVLT_proba_1 tinyint,-- dodane 2015-03-20
	@CVLT_proba_2 tinyint,-- dodane 2015-03-20
	@CVLT_proba_3 tinyint,-- dodane 2015-03-20
	@CVLT_proba_4 tinyint,-- dodane 2015-03-20
	@CVLT_proba_5 tinyint,-- dodane 2015-03-20
	@CVLT_Suma tinyint,-- dodane 2015-03-20
	@CVLT_OSKO_krotkie_odroczenie decimal(4,2), -- dodane 2015-03-20
	@CVLT_OPKO_krotkie_odroczenie_i_pomoc tinyint,-- dodane 2015-03-20
	@CVLT_OSDO_po20min decimal(4,2), -- dodane 2015-03-20
	@CVLT_OPDO_po20min_i_pomoc tinyint,-- dodane 2015-03-20
	@CVLT_perseweracje tinyint,-- dodane 2015-03-20
	@CVLT_WtraceniaOdtwarzanieSwobodne tinyint,-- dodane 2015-03-20
	@CVLT_wtraceniaOdtwarzanieZPomoca tinyint,-- dodane 2015-03-20
	@CVLT_Rozpoznawanie tinyint,-- dodane 2015-03-20
	@CVLT_BledyRozpoznania tinyint,-- dodane 2015-03-20
	@Benton_JOL tinyint,-- dodane 2015-03-20
	@WAIS_R_Wiadomosci tinyint,-- juz bylo; niezmienione
	@WAIS_R_PowtarzanieCyfr tinyint,-- juz bylo; niezmienione
	@WAIS_R_Podobienstwa tinyint, -- dodane 2015-03-20
	@BostonskiTestNazywaniaBMT tinyint, -- dodane 2015-03-20
	@BMT_SredniCzasReakcji_sek int, -- dodane 2015-03-20
	@SkalaDepresjiBecka decimal(4,1),-- zmiana na decimal;
	@SkalaDepresjiBeckaII decimal(4,1),-- dodane 2015-03-20

	@TestFluencjiK tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	@TestFluencjiP tinyint, -- dodane 2015-03-20
	@TestFluencjiZwierzeta tinyint,-- bylo; ale zmiana z varchar(40) na tinyint
	@TestFluencjiOwoceWarzywa tinyint, -- dodane 2015-03-20
	@TestFluencjiOstre tinyint, -- bylo; ale zmiana z varchar(40) na tinyint
	@TestLaczeniaPunktowA varchar(40), -- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	@TestLaczeniaPunktowB varchar(40),-- bylo; poniewaz wystepuja liczby > 50, nie zmienialem typu
	@ToL_SumaRuchow int, -- dodane 2015-03-20
	@ToL_LiczbaPrawidlowych tinyint, -- dodane 2015-03-20
	@ToL_CzasInicjowania_sek int, -- dodane 2015-03-20
	@ToL_CzasWykonania_sek int, -- dodane 2015-03-20
	@ToL_CzasCalkowity_sek int, -- dodane 2015-03-20
	@ToL_CzasPrzekroczony tinyint, -- dodane 2015-03-20
	@ToL_LiczbaPrzekroczenZasad tinyint, -- dodane 2015-03-20
	@ToL_ReakcjeUkierunkowane tinyint, -- dodane 2015-03-20
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

			TestZegara =  REPLACE(@TestZegara,';','. '),
			MMSE =  REPLACE(@MMSE,';','. '),
			CLOX1_Rysunek =  REPLACE(@CLOX1_Rysunek,';','. '),
			CLOX2_Kopia =  REPLACE(@CLOX2_Kopia,';','. '),
			AVLT_proba_1 =  REPLACE(@AVLT_proba_1,';','. '),
			AVLT_proba_2 =  REPLACE(@AVLT_proba_2,';','. '),
			AVLT_proba_3 =  REPLACE(@AVLT_proba_3,';','. '),
			AVLT_proba_4 =  REPLACE(@AVLT_proba_4,';','. '),
			AVLT_proba_5 =  REPLACE(@AVLT_proba_5,';','. '),
			AVLT_Suma =  REPLACE(@AVLT_Suma,';','. '),
			AVLT_Srednia =  REPLACE(@AVLT_Srednia,';','. '),
			AVLT_KrotkieOdroczenie =  REPLACE(@AVLT_KrotkieOdroczenie,';','. '),
			AVLT_Odroczony20min =  REPLACE(@AVLT_Odroczony20min,';','. '),
			AVLT_Rozpoznawanie =  REPLACE(@AVLT_Rozpoznawanie,';','. '),
			AVLT_BledyRozpoznania =  REPLACE(@AVLT_BledyRozpoznania,';','. '),

			TestAVLTSrednia =  REPLACE(@TestAVLTSrednia,';','. '),
			TestAVLTOdroczony =  REPLACE(@TestAVLTOdroczony,';','. '),
			TestAVLTPo20min =  REPLACE(@TestAVLTPo20min,';','. '),
			TestAVLTRozpoznawanie =  REPLACE(@TestAVLTRozpoznawanie,';','. '),

			CVLT_proba_1 =  REPLACE(@CVLT_proba_1,';','. '),
			CVLT_proba_2 =  REPLACE(@CVLT_proba_2,';','. '),
			CVLT_proba_3 =  REPLACE(@CVLT_proba_3,';','. '),
			CVLT_proba_4 =  REPLACE(@CVLT_proba_4,';','. '),
			CVLT_proba_5 =  REPLACE(@CVLT_proba_5,';','. '),
			CVLT_Suma =  REPLACE(@CVLT_Suma,';','. '),
			CVLT_OSKO_krotkie_odroczenie =  REPLACE(@CVLT_OSKO_krotkie_odroczenie,';','. '),
			CVLT_OPKO_krotkie_odroczenie_i_pomoc =  REPLACE(@CVLT_OPKO_krotkie_odroczenie_i_pomoc,';','. '),
			CVLT_OSDO_po20min =  REPLACE(@CVLT_OSDO_po20min,';','. '),
			CVLT_OPDO_po20min_i_pomoc =  REPLACE(@CVLT_OPDO_po20min_i_pomoc,';','. '),
			CVLT_perseweracje =  REPLACE(@CVLT_perseweracje,';','. '),
			CVLT_WtraceniaOdtwarzanieSwobodne =  REPLACE(@CVLT_WtraceniaOdtwarzanieSwobodne,';','. '),
			CVLT_wtraceniaOdtwarzanieZPomoca =  REPLACE(@CVLT_wtraceniaOdtwarzanieZPomoca,';','. '),
			CVLT_Rozpoznawanie =  REPLACE(@CVLT_Rozpoznawanie,';','. '),
			CVLT_BledyRozpoznania =  REPLACE(@CVLT_BledyRozpoznania,';','. '),
			Benton_JOL =  REPLACE(@Benton_JOL,';','. '),
			WAIS_R_Wiadomosci =  REPLACE(@WAIS_R_Wiadomosci,';','. '),
			WAIS_R_PowtarzanieCyfr =  REPLACE(@WAIS_R_PowtarzanieCyfr,';','. '),
			WAIS_R_Podobienstwa =  REPLACE(@WAIS_R_Podobienstwa,';','. '),
			BostonskiTestNazywaniaBMT =  REPLACE(@BostonskiTestNazywaniaBMT,';','. '),
			BMT_SredniCzasReakcji_sek =  REPLACE(@BMT_SredniCzasReakcji_sek,';','. '),
			SkalaDepresjiBecka = @SkalaDepresjiBecka,
			SkalaDepresjiBeckaII = @SkalaDepresjiBeckaII,
			TestFluencjiK = REPLACE(@TestFluencjiK,';','. '),
			TestFluencjiP = REPLACE(@TestFluencjiP,';','. '),
			TestFluencjiZwierzeta = REPLACE(@TestFluencjiZwierzeta,';','. '),
			TestFluencjiOwoceWarzywa = REPLACE(@TestFluencjiOwoceWarzywa,';','. '),
			TestFluencjiOstre = REPLACE(@TestFluencjiOstre,';','. '),
			TestLaczeniaPunktowA = REPLACE(@TestLaczeniaPunktowA,';','. '),
			TestLaczeniaPunktowB = REPLACE(@TestLaczeniaPunktowB,';','. '),
			ToL_SumaRuchow = REPLACE(@ToL_SumaRuchow,';','. '),
			ToL_LiczbaPrawidlowych = REPLACE(@ToL_LiczbaPrawidlowych,';','. '),
			ToL_CzasInicjowania_sek = REPLACE(@ToL_CzasInicjowania_sek,';','. '),
			ToL_CzasWykonania_sek = REPLACE(@ToL_CzasWykonania_sek,';','. '),
			ToL_CzasCalkowity_sek = REPLACE(@ToL_CzasCalkowity_sek,';','. '),
			ToL_CzasPrzekroczony = REPLACE(@ToL_CzasPrzekroczony,';','. '),
			ToL_LiczbaPrzekroczenZasad = REPLACE(@ToL_LiczbaPrzekroczenZasad,';','. '),
			ToL_ReakcjeUkierunkowane = REPLACE(@ToL_ReakcjeUkierunkowane,';','. '),
			InnePsychologiczne = REPLACE(@InnePsychologiczne,';','. '),
			OpisBadania = REPLACE(REPLACE(REPLACE(@OpisBadania, CHAR(13), ''), CHAR(10), ''),';','. '),
			Wnioski = REPLACE(REPLACE(REPLACE(@Wnioski, CHAR(13), ''), CHAR(10), ''),';','. '),
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- updated 2015-07-20
-- @result codes: 0 = OK, 3 = visit of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_examination_questionnaire_partH  (
	@IdWizyta int,
	@Holter bit,
	@BadanieWechu bit,
	@WynikWechu tinyint,
	@LimitDysfagii tinyint,
	@pH_metriaPrzełyku bit,
	@SPECT bit,
	@MRI bit,
	@MRIwynik varchar(2000),
	@USGsrodmozgowia bit,
	@USGWynik tinyint,
	@Genetyka bit,
	@GenetykaWynik varchar(50),
	@Surowica bit,
	@SurowicaPozostało varchar(50),
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
			pH_metriaPrzełyku = @pH_metriaPrzełyku,
			SPECT = @SPECT,
			MRI = @MRI,
			MRIwynik = REPLACE(@MRIwynik,';','. '),
			USGsrodmozgowia = @USGsrodmozgowia,
			USGWynik = @USGWynik,
			Genetyka = @Genetyka,
			GenetykaWynik = REPLACE(@GenetykaWynik,';','. '),
			Surowica = @Surowica,
			SurowicaPozostało = REPLACE(@SurowicaPozostało,';','. '),
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
			LaboratoryjneInne = REPLACE(@LaboratoryjneInne,';','. '),
			Zmodyfikowal = @user_id, 
			OstatniaZmiana = getdate() 
		where IdWizyta = @IdWizyta;

	return;
end;
go


-- altered 2015-06-05
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
	@Saccades bit,
	@SaccadesDurationLEFT decimal(6,2),
	@SaccadesLatencyMeanLEFT decimal(6,2),
	@SaccadesAmplitudeLEFT decimal(6,2),
	@SaccadesPeakVelocityLEFT decimal(6,2),
	@SaccadesDurationRIGHT decimal(6,2),
	@SaccadesLatencyMeanRIGHT decimal(6,2),
	@SaccadesAmplitudeRIGHT decimal(6,2),
	@SaccadesPeakVelocityRIGHT decimal(6,2),
	@SaccadesDurationALL decimal(6,2),
	@SaccadesLatencyMeanALL decimal(6,2),
	@SaccadesAmplitudeALL decimal(6,2),
	@SaccadesPeakVelocityALL decimal(6,2),
	@Antisaccades bit,
	@AntisaccadesPercentOfCorrectLEFT decimal(8,5),
	@AntisaccadesPercentOfCorrectRIGHT decimal(8,5),
	@AntisaccadesLatencyMeanLEFT decimal(8,5),
	@AntisaccadesLatencyMeanRIGHT decimal(8,5),
	@AntisaccadesDurationLEFT decimal(8,5),
	@AntisaccadesDurationRIGHT decimal(8,5),
	@AntisaccadesAmplitudeLEFT decimal(8,5),
	@AntisaccadesAmplitudeRIGHT decimal(8,5),
	@AntisaccadesPeakVelocityLEFT decimal(8,5),
	@AntisaccadesPeakVelocityRIGHT decimal(8,5),
	@AntisaccadesPercentOfCorrectALL decimal(8,5),
	@AntisaccadesLatencyMeanALL decimal(8,5),
	@AntisaccadesDurationALL decimal(8,5),
	@AntisaccadesAmplitudeALL decimal(8,5),
	@AntisaccadesPeakVelocityALL decimal(8,5),
	@POM_Gain_SlowSinus decimal(8,5),
	@POM_StDev_SlowSinus decimal(8,5),
	@POM_Gain_MediumSinus decimal(8,5),
	@POM_StDev_MediumSinus decimal(8,5),
	@POM_Gain_FastSinus decimal(8,5),
	@POM_StDev_FastSinus decimal(8,5),
	@POM_Accuracy_SlowSinus decimal(8,5),
	@POM_Accuracy_MediumSinus decimal(8,5),
	@POM_Accuracy_FastSinus decimal(8,5),
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
			Saccades,
			SaccadesLatencyMeanLEFT,
			SaccadesLatencyMeanRIGHT,
			SaccadesDurationLEFT,
			SaccadesDurationRIGHT,
			SaccadesAmplitudeLEFT,
			SaccadesAmplitudeRIGHT,
			SaccadesPeakVelocityLEFT,
			SaccadesPeakVelocityRIGHT,
			SaccadesLatencyMeanALL,
			SaccadesDurationALL,
			SaccadesAmplitudeALL,
			SaccadesPeakVelocityALL,
			Antisaccades,
			AntisaccadesPercentOfCorrectLEFT,
			AntisaccadesPercentOfCorrectRIGHT,
			AntisaccadesLatencyMeanLEFT,
			AntisaccadesLatencyMeanRIGHT,
			AntisaccadesDurationLEFT,
			AntisaccadesDurationRIGHT,
			AntisaccadesAmplitudeLEFT,
			AntisaccadesAmplitudeRIGHT,
			AntisaccadesPeakVelocityLEFT,
			AntisaccadesPeakVelocityRIGHT,
			AntisaccadesPercentOfCorrectALL,
			AntisaccadesLatencyMeanALL,
			AntisaccadesDurationALL,
			AntisaccadesAmplitudeALL,
			AntisaccadesPeakVelocityALL,
			POM_Gain_SlowSinus,
			POM_StDev_SlowSinus,
			POM_Gain_MediumSinus,
			POM_StDev_MediumSinus,
			POM_Gain_FastSinus,
			POM_StDev_FastSinus,
			POM_Accuracy_SlowSinus,
			POM_Accuracy_MediumSinus,
			POM_Accuracy_FastSinus,
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
			@Saccades,
			@SaccadesLatencyMeanLEFT,
			@SaccadesLatencyMeanRIGHT,
			@SaccadesDurationLEFT,
			@SaccadesDurationRIGHT,
			@SaccadesAmplitudeLEFT,
			@SaccadesAmplitudeRIGHT,
			@SaccadesPeakVelocityLEFT,
			@SaccadesPeakVelocityRIGHT,
			@SaccadesLatencyMeanALL,
			@SaccadesDurationALL,
			@SaccadesAmplitudeALL,
			@SaccadesPeakVelocityALL,
			@Antisaccades,
			@AntisaccadesPercentOfCorrectLEFT,
			@AntisaccadesPercentOfCorrectRIGHT,
			@AntisaccadesLatencyMeanLEFT,
			@AntisaccadesLatencyMeanRIGHT,
			@AntisaccadesDurationLEFT,
			@AntisaccadesDurationRIGHT,
			@AntisaccadesAmplitudeLEFT,
			@AntisaccadesAmplitudeRIGHT,
			@AntisaccadesPeakVelocityLEFT,
			@AntisaccadesPeakVelocityRIGHT,
			@AntisaccadesPercentOfCorrectALL,
			@AntisaccadesLatencyMeanALL,
			@AntisaccadesDurationALL,
			@AntisaccadesAmplitudeALL,
			@AntisaccadesPeakVelocityALL,
			@POM_Gain_SlowSinus,
			@POM_StDev_SlowSinus,
			@POM_Gain_MediumSinus,
			@POM_StDev_MediumSinus,
			@POM_Gain_FastSinus,
			@POM_StDev_FastSinus,
			@POM_Accuracy_SlowSinus,
			@POM_Accuracy_MediumSinus,
			@POM_Accuracy_FastSinus,
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
				Saccades	=	@Saccades,
				SaccadesLatencyMeanLEFT	=	@SaccadesLatencyMeanLEFT,
				SaccadesLatencyMeanRIGHT	=	@SaccadesLatencyMeanRIGHT,
				SaccadesDurationLEFT	=	@SaccadesDurationLEFT,
				SaccadesDurationRIGHT	=	@SaccadesDurationRIGHT,
				SaccadesAmplitudeLEFT	=	@SaccadesAmplitudeLEFT,
				SaccadesAmplitudeRIGHT	=	@SaccadesAmplitudeRIGHT,
				SaccadesPeakVelocityLEFT	=	@SaccadesPeakVelocityLEFT,
				SaccadesPeakVelocityRIGHT	=	@SaccadesPeakVelocityRIGHT,
				SaccadesLatencyMeanALL	=	@SaccadesLatencyMeanALL,
				SaccadesDurationALL	=	@SaccadesDurationALL,
				SaccadesAmplitudeALL	=	@SaccadesAmplitudeALL,
				SaccadesPeakVelocityALL	=	@SaccadesPeakVelocityALL,
				Antisaccades	=	@Antisaccades,
				AntisaccadesPercentOfCorrectLEFT	=	@AntisaccadesPercentOfCorrectLEFT,
				AntisaccadesPercentOfCorrectRIGHT	=	@AntisaccadesPercentOfCorrectRIGHT,
				AntisaccadesLatencyMeanLEFT	=	@AntisaccadesLatencyMeanLEFT,
				AntisaccadesLatencyMeanRIGHT	=	@AntisaccadesLatencyMeanRIGHT,
				AntisaccadesDurationLEFT	=	@AntisaccadesDurationLEFT,
				AntisaccadesDurationRIGHT	=	@AntisaccadesDurationRIGHT,
				AntisaccadesAmplitudeLEFT	=	@AntisaccadesAmplitudeLEFT,
				AntisaccadesAmplitudeRIGHT	=	@AntisaccadesAmplitudeRIGHT,
				AntisaccadesPeakVelocityLEFT	=	@AntisaccadesPeakVelocityLEFT,
				AntisaccadesPeakVelocityRIGHT	=	@AntisaccadesPeakVelocityRIGHT,
				AntisaccadesPercentOfCorrectALL	=	@AntisaccadesPercentOfCorrectALL,
				AntisaccadesLatencyMeanALL	=	@AntisaccadesLatencyMeanALL,
				AntisaccadesDurationALL	=	@AntisaccadesDurationALL,
				AntisaccadesAmplitudeALL	=	@AntisaccadesAmplitudeALL,
				AntisaccadesPeakVelocityALL	=	@AntisaccadesPeakVelocityALL,
				POM_Gain_SlowSinus	=	@POM_Gain_SlowSinus,
				POM_StDev_SlowSinus	=	@POM_StDev_SlowSinus,
				POM_Gain_MediumSinus	=	@POM_Gain_MediumSinus,
				POM_StDev_MediumSinus	=	@POM_StDev_MediumSinus,
				POM_Gain_FastSinus	=	@POM_Gain_FastSinus,
				POM_StDev_FastSinus	=	@POM_StDev_FastSinus,
				POM_Accuracy_SlowSinus	=	@POM_Accuracy_SlowSinus,
				POM_Accuracy_MediumSinus	=	@POM_Accuracy_MediumSinus,
				POM_Accuracy_FastSinus	=	@POM_Accuracy_FastSinus,
				Zmodyfikowal = @user_id, 
				OstatniaZmiana = getdate() 
			where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT ;
		end;
	return;
end;
go



-- last rev. 2014-09-25
-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_variant_examination_data_partB  (	@IdBadanie int,
	@Tremorometria bit, 
	@TremorometriaLEFT bit, 
	@TremorometriaRIGHT bit, 
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
	@TestSchodkowyWDol decimal(4,2),
	@TestSchodkowyWGore decimal(4,2),
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

	update Badanie
		set 
			Tremorometria = @Tremorometria, 
			TremorometriaLEFT = @TremorometriaLEFT,
			TremorometriaRIGHT = @TremorometriaRIGHT,
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
			TestSchodkowyWDol	= @TestSchodkowyWDol,
			TestSchodkowyWGore	= @TestSchodkowyWGore,
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


-- last rev. 2015-03-01
-- @result codes: 0 = OK, 3 = variant of this ID not found, exist 2 = validation failed - see message, 4 = user login unknown
create procedure update_variant_examination_data_partB_1  (	@IdBadanie int,

	@Otwarte_Srednia_C_o_P_X int,				
	@Otwarte_Srednia_C_o_P_Y int,				
	@Otwarte_Srednia_P_T_Predkosc_mm_sec int,	
	@Otwarte_Srednia_P_B_Predkosc_mm_sec int,	
	@Otwarte_Perimeter_mm int,					
	@Otwarte_PoleElipsy_mm2 int,					

	@Zamkniete_Srednia_C_o_P_X int,				
	@Zamkniete_Srednia_C_o_P_Y int,				
	@Zamkniete_Srednia_P_T_Predkosc_mm_sec int,	
	@Zamkniete_Srednia_P_B_Predkosc_mm_sec int,	
	@Zamkniete_Perimeter_mm int,					
	@Zamkniete_PoleElipsy_mm2 int,				

	@WspolczynnikPerymetru_E_C_E_O_obie_stopy int,		
	@WspolczynnikPowierzchni_E_C_E_O_obie_stopy int,	

	@Biofeedback_Srednia_C_o_P_X int,				
	@Biofeedback_Srednia_C_o_P_Y int,				
	@Biofeedback_Srednia_P_T_Predkosc_mm_sec int,	
	@Biofeedback_Srednia_P_B_Predkosc_mm_sec int,	
	@Biofeedback_Perimeter_mm int,					
	@Biofeedback_PoleElipsy_mm2 int,					


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

	update Badanie
		set 
			Otwarte_Srednia_C_o_P_X = @Otwarte_Srednia_C_o_P_X,				
			Otwarte_Srednia_C_o_P_Y = @Otwarte_Srednia_C_o_P_Y,				
			Otwarte_Srednia_P_T_Predkosc_mm_sec = @Otwarte_Srednia_P_T_Predkosc_mm_sec,	
			Otwarte_Srednia_P_B_Predkosc_mm_sec = @Otwarte_Srednia_P_B_Predkosc_mm_sec,	
			Otwarte_Perimeter_mm = @Otwarte_Perimeter_mm,					
			Otwarte_PoleElipsy_mm2 = @Otwarte_PoleElipsy_mm2,					

			Zamkniete_Srednia_C_o_P_X = @Zamkniete_Srednia_C_o_P_X,				
			Zamkniete_Srednia_C_o_P_Y = @Zamkniete_Srednia_C_o_P_Y,				
			Zamkniete_Srednia_P_T_Predkosc_mm_sec = @Zamkniete_Srednia_P_T_Predkosc_mm_sec,	
			Zamkniete_Srednia_P_B_Predkosc_mm_sec = @Zamkniete_Srednia_P_B_Predkosc_mm_sec,	
			Zamkniete_Perimeter_mm = @Zamkniete_Perimeter_mm,					
			Zamkniete_PoleElipsy_mm2 = @Zamkniete_PoleElipsy_mm2,				

			WspolczynnikPerymetru_E_C_E_O_obie_stopy = @WspolczynnikPerymetru_E_C_E_O_obie_stopy,		
			WspolczynnikPowierzchni_E_C_E_O_obie_stopy = @WspolczynnikPowierzchni_E_C_E_O_obie_stopy,	

			Biofeedback_Srednia_C_o_P_X = @Biofeedback_Srednia_C_o_P_X,				
			Biofeedback_Srednia_C_o_P_Y = @Biofeedback_Srednia_C_o_P_Y,				
			Biofeedback_Srednia_P_T_Predkosc_mm_sec = @Biofeedback_Srednia_P_T_Predkosc_mm_sec,	
			Biofeedback_Srednia_P_B_Predkosc_mm_sec = @Biofeedback_Srednia_P_B_Predkosc_mm_sec,	
			Biofeedback_Perimeter_mm = @Biofeedback_Perimeter_mm,					
			Biofeedback_PoleElipsy_mm2 = @Biofeedback_PoleElipsy_mm2,	
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
go




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
go

-- pobieranie plaskiej kopii bazy
-- ===============================


-- altered: 2014-09-25
create view AtrybutyWielowartoscioweWizyty as (
SELECT  t.IdWIzyta IdWizyta, a.Nazwa NazwaAtrybutu, 
        stuff(
                (
                    select  ',' + cast(t1.Wartosc as Varchar)
                    from    WartoscAtrybutuWizytyInt t1
                    where   t1.IdWizyta = t.IdWizyta and t1.IdAtrybut = t.IdAtrybut
                    order by t1.Wartosc
                    for xml path('')
                ),1,1,'') Wartosci
FROM    WartoscAtrybutuWizytyInt t join Atrybut a on t.IdAtrybut = a.IdAtrybut
GROUP BY t.IdWizyta, t.IdAtrybut, a.Nazwa
)
go

-- modified: 2015-07-19
create procedure get_database_copy
as
SELECT 
/* PACJENT */
	   P.[NumerPacjenta]
      ,P.[RokUrodzenia]
      ,P.[MiesiacUrodzenia]
      ,P.[Plec]
      ,P.[Lokalizacja]
      ,P.[LiczbaElektrod]
	  ,P.[NazwaGrupy]
/*    ,P.[Wprowadzil] as WprowadzilDanePacjenta
      ,P.[Zmodyfikowal] as KorygowalDanePacjenta
      ,P.[OstatniaZmiana] as OstatniaKorektaDanychPacjenta */
/* WIZYTA */
      ,W.[RodzajWizyty]
      ,W.[DataPrzyjecia]
      ,W.[DataWypisu]
      ,W.[MasaCiala]
      ,W.[DataOperacji]
      ,W.[Wyksztalcenie]
      ,W.[Rodzinnosc]
      ,W.[RokZachorowania]
      ,dbo.disorder_duration_for_examination(W.IdWizyta) as CzasTrwaniaChoroby
      ,W.[PierwszyObjaw]
      ,W.[Drzenie]
      ,W.[Sztywnosc]
      ,W.[Spowolnienie]
      ,W.[ObjawyInne]
      ,W.[ObjawyInneJakie] ObjawyInneJakie
      ,W.[CzasOdPoczObjDoWlLDopy]
      ,W.[DyskinezyObecnie]
      ,W.[DyskinezyOdLat]
      ,W.[FluktuacjeObecnie]
      ,W.[FluktuacjeOdLat]
      ,W.[CzasDyskinez]
      ,W.[CzasOFF]
      ,W.[PoprawaPoLDopie]
      ,W.[PrzebyteLeczenieOperacyjnePD]
      ,W.[Papierosy]
      ,W.[Kawa]
      ,W.[ZielonaHerbata]
      ,W.[Alkohol]
      ,W.[ZabiegowWZnieczOgPrzedRozpoznaniemPD]
      ,W.[Zamieszkanie]
      ,dbo.multivalued_examination_attribute('NarazenieNaToks', P.IdPacjent, W.RodzajWizyty)  as NarazeniaNaToks
      ,W.[Uwagi] Uwagi
      ,W.[Nadcisnienie]
      ,W.[BlokeryKanWapn]
      ,W.[DominujacyObjawObecnie]
      ,W.[DominujacyObjawUwagi] DominujacyObjawUwagi
	  ,dbo.multivalued_examination_attribute('ObjawyAutonomiczne', P.IdPacjent, W.RodzajWizyty) as ObjawyAutonomiczne
      ,W.[RLS]
      ,W.[ObjawyPsychotyczne]
      ,W.[Depresja]
      ,W.[Otepienie]
      ,W.[Dyzartria]
      ,W.[DysfagiaObjaw]
      ,W.[RBD]
      ,W.[ZaburzenieRuchomosciGalekOcznych]
      ,W.[Apraksja]
      ,W.[TestKlaskania]
      ,W.[ZaburzeniaWechowe]
      ,W.[Ldopa]
      ,W.[LDopaObecnie]
      ,W.[Agonista]
      ,W.[AgonistaObecnie]
      ,W.[Amantadyna]
      ,W.[AmantadynaObecnie]
      ,W.[MAOBinh]
      ,W.[MAOBinhObecnie]
      ,W.[COMTinh]
      ,W.[COMTinhObecnie]
      ,W.[Cholinolityk]
      ,W.[CholinolitykObecnie]
      ,W.[LekiInne]
      ,W.[LekiInneJakie] LekiInneJakie
      ,W.[L_STIMOpis] L_STIMOpis
      ,W.[L_STIMAmplitude]
      ,W.[L_STIMDuration]
      ,W.[L_STIMFrequency]
      ,W.[R_STIMOpis] R_STIMOpis
      ,W.[R_STIMAmplitude]
      ,W.[R_STIMDuration]
      ,W.[R_STIMFrequency]
      ,W.[Wypis_Ldopa]
      ,W.[Wypis_LDopaObecnie]
      ,W.[Wypis_Agonista]
      ,W.[Wypis_AgonistaObecnie]
      ,W.[Wypis_Amantadyna]
      ,W.[Wypis_AmantadynaObecnie]
      ,W.[Wypis_MAOBinh]
      ,W.[Wypis_MAOBinhObecnie]
      ,W.[Wypis_COMTinh]
      ,W.[Wypis_COMTinhObecnie]
      ,W.[Wypis_Cholinolityk]
      ,W.[Wypis_CholinolitykObecnie]
      ,W.[Wypis_LekiInne]
      ,W.[Wypis_LekiInneJakie]
      ,W.[Wypis_L_STIMOpis] Wypis_L_STIMOpis
      ,W.[Wypis_L_STIMAmplitude]
      ,W.[Wypis_L_STIMDuration]
      ,W.[Wypis_L_STIMFrequency]
      ,W.[Wypis_R_STIMOpis] Wypis_R_STIMOpis
      ,W.[Wypis_R_STIMAmplitude]
      ,W.[Wypis_R_STIMDuration]
      ,W.[Wypis_R_STIMFrequency]
      ,W.[WydzielanieSliny]
      ,W.[Dysfagia]
      ,W.[DysfagiaCzestotliwosc]
      ,W.[Nudnosci]
      ,W.[Zaparcia]
      ,W.[TrudnosciWOddawaniuMoczu]
      ,W.[PotrzebaNaglegoOddaniaMoczu]
      ,W.[NiekompletneOproznieniePecherza]
      ,W.[SlabyStrumienMoczu]
      ,W.[CzestotliwowscOddawaniaMoczu]
      ,W.[Nykturia]
      ,W.[NiekontrolowaneOddawanieMoczu]
      ,W.[Omdlenia]
      ,W.[ZaburzeniaRytmuSerca]
      ,W.[ProbaPionizacyjna]
      ,W.[WzrostPodtliwosciTwarzKark]
      ,W.[WzrostPotliwosciRamionaDlonie]
	  ,W.[WzrostPotliwosciBrzuchPlecy]
      ,W.[WzrostPotliwosciKonczynyDolneStopy]
      ,W.[SpadekPodtliwosciTwarzKark]
      ,W.[SpadekPotliwosciRamionaDlonie]
	  ,W.[SpadekPotliwosciBrzuchPlecy]
      ,W.[SpadekPotliwosciKonczynyDolneStopy]
      ,W.[NietolerancjaWysokichTemp]
      ,W.[NietolerancjaNiskichTemp]
      ,W.[Lojotok]
      ,W.[SpadekLibido]
      ,W.[KlopotyOsiagnieciaErekcji]
      ,W.[KlopotyUtrzymaniaErekcji]
	/* BADANIE (wariantowe) */
      ,B.[DBS]
      ,B.[BMT]
      ,B.[UPDRS_I]
      ,B.[UPDRS_II]
      ,B.[UPDRS_18]
      ,B.[UPDRS_19]
      ,B.[UPDRS_20_FaceLipsChin]
      ,B.[UPDRS_20_RHand]
      ,B.[UPDRS_20_LHand]
      ,B.[UPDRS_20_RFoot]
      ,B.[UPDRS_20_LFoot]
      ,B.[UPDRS_21_RHand]
      ,B.[UPDRS_21_LHand]
      ,B.[UPDRS_22_Neck]
      ,B.[UPDRS_22_RHand]
      ,B.[UPDRS_22_LHand]
      ,B.[UPDRS_22_RFoot]
      ,B.[UPDRS_22_LFoot]
      ,B.[UPDRS_23_R]
      ,B.[UPDRS_23_L]
      ,B.[UPDRS_24_R]
      ,B.[UPDRS_24_L]
      ,B.[UPDRS_25_R]
      ,B.[UPDRS_25_L]
      ,B.[UPDRS_26_R]
      ,B.[UPDRS_26_L]
      ,B.[UPDRS_27]
      ,B.[UPDRS_28]
      ,B.[UPDRS_29]
      ,B.[UPDRS_30]
      ,B.[UPDRS_31]
      ,B.[UPDRS_III]
      ,B.[UPDRS_IV]
      ,B.[UPDRS_TOTAL]
      ,B.[HYscale]
      ,B.[SchwabEnglandScale]
      ,B.[JazzNovo]
      ,B.[Wideookulograf]
	,B.[Saccades]
	,B.[SaccadesLatencyMeanLEFT]
	,B.[SaccadesLatencyMeanRIGHT]
	,B.[SaccadesDurationLEFT]
	,B.[SaccadesDurationRIGHT]
	,B.[SaccadesAmplitudeLEFT]
	,B.[SaccadesAmplitudeRIGHT]
	,B.[SaccadesPeakVelocityLEFT]
	,B.[SaccadesPeakVelocityRIGHT]
	,B.[SaccadesLatencyMeanALL]
	,B.[SaccadesDurationALL]
	,B.[SaccadesAmplitudeALL]
	,B.[SaccadesPeakVelocityALL]
	,B.[Antisaccades]
	,B.[AntisaccadesPercentOfCorrectLEFT]
	,B.[AntisaccadesPercentOfCorrectRIGHT]
	,B.[AntisaccadesLatencyMeanLEFT]
	,B.[AntisaccadesLatencyMeanRIGHT]
	,B.[AntisaccadesDurationLEFT]
	,B.[AntisaccadesDurationRIGHT]
	,B.[AntisaccadesAmplitudeLEFT]
	,B.[AntisaccadesAmplitudeRIGHT]
	,B.[AntisaccadesPeakVelocityLEFT]
	,B.[AntisaccadesPeakVelocityRIGHT]
	,B.[AntisaccadesPercentOfCorrectALL]
	,B.[AntisaccadesLatencyMeanALL]
	,B.[AntisaccadesDurationALL]
	,B.[AntisaccadesAmplitudeALL]
	,B.[AntisaccadesPeakVelocityALL]
	,B.[POM_Gain_SlowSinus]
	,B.[POM_StDev_SlowSinus]
	,B.[POM_Gain_MediumSinus]
	,B.[POM_StDev_MediumSinus]
	,B.[POM_Gain_FastSinus]
	,B.[POM_StDev_FastSinus]
	,B.[POM_Accuracy_SlowSinus]
	,B.[POM_Accuracy_MediumSinus]
	,B.[POM_Accuracy_FastSinus]
      ,B.[Tremorometria]
      ,B.[TremorometriaLEFT]
      ,B.[TremorometriaRIGHT]
      ,B.[TremorometriaLEFT_0_1]
      ,B.[TremorometriaLEFT_1_2]
      ,B.[TremorometriaLEFT_2_3]
      ,B.[TremorometriaLEFT_3_4]
      ,B.[TremorometriaLEFT_4_5]
      ,B.[TremorometriaLEFT_5_6]
      ,B.[TremorometriaLEFT_6_7]
      ,B.[TremorometriaLEFT_7_8]
      ,B.[TremorometriaLEFT_8_9]
      ,B.[TremorometriaLEFT_9_10]
      ,B.[TremorometriaLEFT_23_24]
      ,B.[TremorometriaRIGHT_0_1]
      ,B.[TremorometriaRIGHT_1_2]
      ,B.[TremorometriaRIGHT_2_3]
      ,B.[TremorometriaRIGHT_3_4]
      ,B.[TremorometriaRIGHT_4_5]
      ,B.[TremorometriaRIGHT_5_6]
      ,B.[TremorometriaRIGHT_6_7]
      ,B.[TremorometriaRIGHT_7_8]
      ,B.[TremorometriaRIGHT_8_9]
      ,B.[TremorometriaRIGHT_9_10]
      ,B.[TremorometriaRIGHT_23_24]
      ,B.[TestSchodkowy]
      ,B.[TestSchodkowyWDol]
      ,B.[TestSchodkowyWGore]
      ,B.[TestMarszu]
      ,B.[TestMarszuCzas1]
      ,B.[TestMarszuCzas2]
      ,B.[Posturografia]
      ,B.[MotionAnalysis]
      ,B.[Otwarte_Srednia_C_o_P_X]
      ,B.[Otwarte_Srednia_C_o_P_Y]
      ,B.[Otwarte_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Otwarte_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Otwarte_Perimeter_mm]
      ,B.[Otwarte_PoleElipsy_mm2]
      ,B.[Zamkniete_Srednia_C_o_P_X]
      ,B.[Zamkniete_Srednia_C_o_P_Y]
      ,B.[Zamkniete_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Zamkniete_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Zamkniete_Perimeter_mm]
      ,B.[Zamkniete_PoleElipsy_mm2]
      ,B.[WspolczynnikPerymetru_E_C_E_O_obie_stopy]
      ,B.[WspolczynnikPowierzchni_E_C_E_O_obie_stopy]
      ,B.[Biofeedback_Srednia_C_o_P_X]
      ,B.[Biofeedback_Srednia_C_o_P_Y]
      ,B.[Biofeedback_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Biofeedback_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Biofeedback_Perimeter_mm]
      ,B.[Biofeedback_PoleElipsy_mm2]				
      ,B.[UpAndGo]
      ,B.[UpAndGoLiczby]
      ,B.[UpAndGoKubekPrawa]
      ,B.[UpAndGoKubekLewa]
      ,B.[TST]
      ,B.[TandemPivot]
      ,B.[WTT]
      ,B.[Wprowadzil] as WariantZapisal
      ,B.[Zmodyfikowal] as WariantModyfikowal
      ,B.[OstatniaZmiana] as OstatniaEdycjaWariantu
/* WIZYTA - c.d. */
      ,W.[PDQ39]
      ,W.[AIMS]
      ,W.[Epworth]
      ,W.[CGI]
      ,W.[FSS]
	,W.[TestZegara] TestZegara
	,W.[MMSE] MMSE
	,W.[CLOX1_Rysunek] CLOX1_Rysunek
	,W.[CLOX2_Kopia] CLOX2_Kopia
	,W.[AVLT_proba_1] AVLT_proba_1
	,W.[AVLT_proba_2] AVLT_proba_2
	,W.[AVLT_proba_3] AVLT_proba_3
	,W.[AVLT_proba_4] AVLT_proba_4
	,W.[AVLT_proba_5] AVLT_proba_5
	,W.[AVLT_Suma] AVLT_Suma
	,W.[AVLT_Srednia] AVLT_Srednia
	,W.[AVLT_KrotkieOdroczenie] AVLT_KrotkieOdroczenie
	,W.[AVLT_Odroczony20min] AVLT_Odroczony20min
	,W.[AVLT_Rozpoznawanie] AVLT_Rozpoznawanie
	,W.[AVLT_BledyRozpoznania] AVLT_BledyRozpoznania

	,W.[TestAVLTSrednia] TestAVLTSrednia
	,W.[TestAVLTOdroczony] TestAVLTOdroczony
	,W.[TestAVLTPo20min] TestAVLTPo20min
	,W.[TestAVLTRozpoznawanie] TestAVLTRozpoznawanie

	,W.[CVLT_proba_1] CVLT_proba_1
	,W.[CVLT_proba_2] CVLT_proba_2
	,W.[CVLT_proba_3] CVLT_proba_3
	,W.[CVLT_proba_4] CVLT_proba_4
	,W.[CVLT_proba_5] CVLT_proba_5
	,W.[CVLT_Suma] CVLT_Suma
	,W.[CVLT_OSKO_krotkie_odroczenie] CVLT_OSKO_krotkie_odroczenie
	,W.[CVLT_OPKO_krotkie_odroczenie_i_pomoc] CVLT_OPKO_krotkie_odroczenie_i_pomoc
	,W.[CVLT_OSDO_po20min] CVLT_OSDO_po20min
	,W.[CVLT_OPDO_po20min_i_pomoc] CVLT_OPDO_po20min_i_pomoc
	,W.[CVLT_perseweracje] CVLT_perseweracje
	,W.[CVLT_WtraceniaOdtwarzanieSwobodne] CVLT_WtraceniaOdtwarzanieSwobodne
	,W.[CVLT_wtraceniaOdtwarzanieZPomoca] CVLT_wtraceniaOdtwarzanieZPomoca
	,W.[CVLT_Rozpoznawanie] CVLT_Rozpoznawanie
	,W.[CVLT_BledyRozpoznania] CVLT_BledyRozpoznania
	,W.[Benton_JOL] Benton_JOL
	,W.[WAIS_R_Wiadomosci] WAIS_R_Wiadomosci
	,W.[WAIS_R_PowtarzanieCyfr] WAIS_R_PowtarzanieCyfr
	,W.[WAIS_R_Podobienstwa] WAIS_R_Podobienstwa
	,W.[BostonskiTestNazywaniaBMT] BostonskiTestNazywaniaBMT
	,W.[BMT_SredniCzasReakcji_sek] BMT_SredniCzasReakcji_sek
	,W.[SkalaDepresjiBecka]
	,W.[SkalaDepresjiBeckaII]
	,W.[TestFluencjiK] TestFluencjiK
	,W.[TestFluencjiP] TestFluencjiP
	,W.[TestFluencjiZwierzeta] TestFluencjiZwierzeta
	,W.[TestFluencjiOwoceWarzywa] TestFluencjiOwoceWarzywa
	,W.[TestFluencjiOstre] TestFluencjiOstre
	,W.[TestLaczeniaPunktowA] TestLaczeniaPunktowA
	,W.[TestLaczeniaPunktowB] TestLaczeniaPunktowB
	,W.[ToL_SumaRuchow] ToL_SumaRuchow
	,W.[ToL_LiczbaPrawidlowych] ToL_LiczbaPrawidlowych
	,W.[ToL_CzasInicjowania_sek] ToL_CzasInicjowania_sek
	,W.[ToL_CzasWykonania_sek] ToL_CzasWykonania_sek
	,W.[ToL_CzasCalkowity_sek] ToL_CzasCalkowity_sek
	,W.[ToL_CzasPrzekroczony] ToL_CzasPrzekroczony
	,W.[ToL_LiczbaPrzekroczenZasad] ToL_LiczbaPrzekroczenZasad
	,W.[ToL_ReakcjeUkierunkowane] ToL_ReakcjeUkierunkowane
	,W.[InnePsychologiczne] InnePsychologiczne
	,W.[OpisBadania] OpisBadania
	,W.[Wnioski] Wnioski
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrzełyku]
      ,W.[SPECT]
	  ,dbo.multivalued_examination_attribute('SPECTWynik', P.IdPacjent, W.RodzajWizyty) as SPECTWyniki
      ,W.[MRI]
      ,W.[MRIwynik] MRIwynik
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,W.[GenetykaWynik] GenetykaWynik
      ,W.[Surowica]
      ,W.[SurowicaPozostało] SurowicaPozostało
      ,W.[Ferrytyna]
      ,W.[CRP]
      ,W.[NTproCNP]
      ,W.[URCA]
      ,W.[WitD]
      ,W.[CHOL]
      ,W.[TGI]
      ,W.[HDL]
      ,W.[LDL]
      ,W.[olLDL]
      ,W.[LaboratoryjneInne] LaboratoryjneInne
      ,W.[Wprowadzil] as WizyteWprowadzil
      ,W.[Zmodyfikowal] as WizyteEdytowal
      ,W.[OstatniaZmiana] as OstatniaModyfikacja

  FROM Pacjent P left join Wizyta w on P.IdPacjent = W.IdPacjent left join Badanie B on B.IdWizyta = W.IdWizyta
  order by P.NumerPacjenta, W.RodzajWizyty, B.BMT, B.DBS
  go

  
  -- wsparcie generowania numerow pacjenta

-- created: 2013-10-31
create procedure suggest_new_patient_number( @admission_date date, @patient_number varchar(20) OUTPUT )
as
begin
	declare @maxnum int;
	set @patient_number = '/PD/DBS/'+CAST(YEAR(@admission_date) as varchar);
	select @maxnum = max(IdPacjent)+1 from Pacjent;
	if @maxnum is null
		set @maxnum = 1;
	set @patient_number = cast(@maxnum as varchar)+@patient_number; -- rozwiazanie tymczasowe
	return;
end;
go

-- created: 2013-10-31
create procedure suggest_new_patient_number_with_group( @admission_date date, @patient_group varchar(3), @patient_number varchar(20) OUTPUT )
as
begin
	declare @maxnum int;
	set @patient_number = '/PD/'+@patient_group+'/'+CAST(YEAR(@admission_date) as varchar);
	select @maxnum = max(IdPacjent)+1 from Pacjent;
	if @maxnum is null
		set @maxnum = 1;
	set @patient_number = cast(@maxnum as varchar)+@patient_number; -- rozwiazanie tymczasowe
	return;
end;
go


-- modified: 2014-07-16

create procedure list_variant_files( @variant_id int)
as
select IdPlik, IdBadanie, NazwaPliku, OpisPliku from Plik where IdBadanie = @variant_id;
go


-- podglad zestawu danych i plikow
-- updated: 2015-01-22
create procedure get_database_and_file_overview
as
select 
	   P.[NumerPacjenta]
      ,W.[RodzajWizyty]
      ,max(PBDC.IdPlik) as BMT_DBS_Coord
      ,max(PBDV.IdPlik) as BMT_DBS_Video
      ,max(PBDTE.IdPlik) as BMT_DBS_ET_Excel
      ,max(PBDTG.IdPlik) as BMT_DBS_ET_Graph
      
      ,max(PBC.IdPlik) as BMT_O_Coord
      ,max(PBV.IdPlik) as BMT_O_Video
      ,max(PBTE.IdPlik) as BMT_O_ET_Excel
      ,max(PBTG.IdPlik) as BMT_O_ET_Graph

      ,max(PDC.IdPlik) as O_DBS_Coord
      ,max(PDV.IdPlik) as O_DBS_Video
      ,max(PDTE.IdPlik) as O_DBS_ET_Excel
      ,max(PDTG.IdPlik) as O_DBS_ET_Graph

      ,max(PC.IdPlik) as O_O_Coord
      ,max(PV.IdPlik) as O_O_Video
      ,max(PTE.IdPlik) as O_O_ET_Excel
      ,max(PTG.IdPlik) as O_O_ET_Graph

      
  from
  Pacjent P join Wizyta w on P.IdPacjent = W.IdPacjent join Badanie B on B.IdWizyta = W.IdWizyta
  
  left join Plik PBDC on B.IdBadanie = PBDC.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDC.OpisPliku = 'Coordinates'
  left join Plik PBDV on B.IdBadanie = PBDV.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDV.OpisPliku = 'Video'
  left join Plik PBDTE on B.IdBadanie = PBDC.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PBDTG on B.IdBadanie = PBDV.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDTG.OpisPliku = 'EyeTrackingGraph'
  
  left join Plik PBC on B.IdBadanie = PBC.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBC.OpisPliku = 'Coordinates'
  left join Plik PBV on B.IdBadanie = PBV.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBV.OpisPliku = 'Video'
  left join Plik PBTE on B.IdBadanie = PBC.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PBTG on B.IdBadanie = PBV.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBTG.OpisPliku = 'EyeTrackingGraph'
  
  left join Plik PDC on B.IdBadanie = PDC.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDC.OpisPliku = 'Coordinates'
  left join Plik PDV on B.IdBadanie = PDV.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDV.OpisPliku = 'Video'
  left join Plik PDTE on B.IdBadanie = PDC.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PDTG on B.IdBadanie = PDV.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDTG.OpisPliku = 'EyeTrackingGraph'
  
  left join Plik PC on B.IdBadanie = PC.IdBadanie and B.BMT = 0 and B.DBS = 0 and PC.OpisPliku = 'Coordinates'
  left join Plik PV on B.IdBadanie = PV.IdBadanie and B.BMT = 0 and B.DBS = 0 and PV.OpisPliku = 'Video' 
  left join Plik PTE on B.IdBadanie = PC.IdBadanie and B.BMT = 0 and B.DBS = 0 and PTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PTG on B.IdBadanie = PV.IdBadanie and B.BMT = 0 and B.DBS = 0 and PTG.OpisPliku = 'EyeTrackingGraph' 		
  group by P.NumerPacjenta, W.RodzajWizyty	
  order by P.NumerPacjenta, W.RodzajWizyty
  go

-- updated: 2015-01-22
create function generate_file_name( @file_id int )
returns varchar(80)
as
begin
declare @file_name varchar(80);
declare @bmt bit;
declare @dbs bit;
declare @step varchar(3);
declare @patient_no varchar(14);
declare @file_type varchar(12);

select 
	@file_name = p.NazwaPliku, 
	@bmt = b.BMT, 
	@dbs = b.DBS, 
	@step = w.RodzajWizyty, 
	@patient_no = pa.NumerPacjenta, 
	@file_type = p.OpisPliku
	from Plik p join Badanie b on b.IdBadanie = p.IdBadanie join Wizyta w on w.IdWizyta = b.IdWizyta join Pacjent pa on pa.IdPacjent = w.IdPacjent
	where p.IdPlik = @file_id;
	
	set @file_name = rtrim(right(@file_name, charindex('.', reverse(@file_name))));
	
	if @file_type = 'Coordinates' set @file_name = '_C'+@file_name;
	if @file_type = 'Video' set @file_name = '_V'+@file_name;
	if @file_type = 'EyeTrackingExcel' set @file_name = '_ETe'+@file_name;
	if @file_type = 'EyeTrackingGraph' set @file_name = '_ETg'+@file_name;
	
	if @dbs = 1 set @file_name = '-DBS'+@file_name; else set @file_name = '-O'+@file_name;
	if @bmt = 1 set @file_name = '-BMT'+@file_name; else set @file_name = '-O'+@file_name;
	
	set @file_name = '-' + @step + @file_name;
	
	set @patient_no = replace( @patient_no, '/', '_');
	set @file_name = @patient_no+@file_name;
	return @file_name;
end
go  



/*




-- wywiad A (część epidemiologiczna)

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0,	'przedoperacyjna' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	6,	'po pół roku' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	12,	'rok po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	18,	'półtora roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	24,	'2 lata po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	30,	'2 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	36,	'3 lata po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	42,	'3 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	48,	'4 lata po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	54,	'4 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	60,	'5 lat po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	66,	'5 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	72,	'6 lat po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	78,	'6 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	84,	'7 lat po DBS' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	1,	'podstawowe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	2,	'zawodowe' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	3,	'średnie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	4,	'wyższe' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	1,	'zaburzenia równowagi' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	2,	'spowolnienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	3,	'sztywność' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	4,	'drżenie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	5,	'otępienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	6,	'dyskinezy i fluktuacje' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	7,	'objawy autonomiczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	8,	'inne' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	1,	'nigdy' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	2,	'w przeszłości' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	3,	'obecnie' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	1,	'mniej niż 1/tyg.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	2,	'kilka / tydz.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	3,	'codziennie' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	1,	'mniej niż 1/tyg.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	2,	'kilka / mies.' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	3,	'codziennie niewiele' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	4,	'nadużywa' );

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	0,	'wieś' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	1,	'miasto' );

insert into Atrybut ( Tabela, Nazwa ) values ( 'Wizyta', 'NarazenieNaToks');

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	0,	'brak' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	1,	'zatrucie CO' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	2,	'toksyczne substancje przemysłowe' );
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
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	3,	'sztywność' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	4,	'drżenie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	5,	'otępienie' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	6,	'dyskinezy i fluktuacje' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	7,	'objawy autonomiczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'DominujacyObjawObecnie',	8,	'inne' );

insert into Atrybut ( Tabela, Nazwa ) values ( 'Wizyta', 'ObjawyAutonomiczne');

insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	1,	'zaparcia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	2,	'objawy dyzuryczne' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	3,	'hipotonia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	4,	'nadmierna potliwość' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	5,	'ślinotok' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'ObjawyAutonomiczne',	6,	'łojotok' );
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




-- insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'test', HashBytes('SHA1','pass'), 'Użytkownik1', 'Testowy')
-- insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'testowy', HashBytes('SHA1','testowy'), 'Użytkownik2', 'Testowy')

update Uzytkownik set Status = 1
-- wywiad B


delete from SlownikDecimal where Atrybut = 'Otepienie'

insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	0,	'nie' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	0.5,	'MCI' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	1,	'tak' );
insert into SlownikDecimal ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Otepienie',	2,	'brak danych' );



insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'STN',	'STN' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'Gpi',	'Gpi' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'Lokalizacja',	'Vim',	'Vim' );



insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'NazwaGrupy',	'DBS',	'DBS' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'NazwaGrupy',	'BMT',	'BMT' );
insert into SlownikVarchar ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Pacjent',	'NazwaGrupy',	'POP',	'POP' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	0,	'0=norma' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	1,	'1=niewielkie zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	2,	'2=umiarkowane zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	3,	'3=poważne zaburzenia' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'_ObjawAutonomiczny',	4,	'4=ciężkie zaburzenia' );


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	0,	'brak okna' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	1,	'brak hyperechogeniczności' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	2,	'hyperechogeniczność - L' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	4,	'hyperechogeniczność - P' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'USGWynik',	6,	'hyperechogeniczność - Obydwie' );

insert into Atrybut ( Tabela, Nazwa ) values ( 'Wizyta', 'SPECTWynik');




insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',1,'prawidłowy rozkład znacznika, bez ogniskowych zaburzeń perfuzji');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',2,'uogólnione zmniejszenie przepływu krwi w obrębie kory mózgowej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',3,'zmniejszeni przepływu krwi w obrębie lewej wyspy');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',4,'zmniejszenie prezpływu krwi w płacie skroniowym prawym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',5,'zmniejszenie przepływu krwi obustronnie w zakrętach obręczy');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',6,'zmniejszenie przepływu krwi w korze bruzdy ostrogowej obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',7,'zmniejszenie przepływu krwi w korze bruzdy ostrogowej po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',8,'zmniejszenie przepływu krwi w korze bruzdy ostrogowej po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',9,'zmniejszenie przepływu krwi w obrębie jąder podkorowych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',10,'zmniejszenie przepływu krwi w obrębie jąder podkorowych po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',11,'zmniejszenie przepływu krwi w obrębie jąder podkorowych po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',12,'zmniejszenie przepływu krwi w obrębie lewego wzgórza');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',13,'zmniejszenie przepływu krwi w obrębie płata potylicznego po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',14,'zmniejszenie przepływu krwi w obrębie płata potylicznego po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',15,'zmniejszenie przepływu krwi w obrębie prawego wzgórza');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',16,'zmniejszenie przepływu krwi w obrębie prawej wyspy');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',17,'zmniejszenie przepływu krwi w obrębie skorupy obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',18,'zmniejszenie przepływu krwi w obrębie skorupy po stronie lewej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',19,'zmniejszenie przepływu krwi w obrębie skorupy po stronie prawej');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',20,'zmniejszenie przepływu krwi w obrębie wysp obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',21,'zmniejszenie przepływu krwi w płacie czołowym lewym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',22,'zmniejszenie przepływu krwi w płacie czołowym prawym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',23,'zmniejszenie przepływu krwi w płacie skroniowym lewym');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',24,'zmniejszenie przepływu krwi w płatach skroniowych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',25,'zmniejszenie przepływu krwi w zakrętach potylicznych dolnych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',26,'zmniejszenie przepływu w płatach czołowych obustronnie');
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta', 'SPECTWynik',27,'zmniejszenie przepływu krwi w zakrętach czołowych środkowych');




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



insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (1, 'P', 'NumerPacjenta');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (2, 'P', 'RokUrodzenia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (3, 'P', 'MiesiacUrodzenia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (4, 'P', 'Plec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (5, 'P', 'Lokalizacja');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (6, 'P', 'LiczbaElektrod');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (7, 'P', 'NazwaGrupy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (8, 'W', 'RodzajWizyty');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (9, 'W', 'DataPrzyjecia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (10, 'W', 'DataWypisu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (11, 'W', 'MasaCiala');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (12, 'W', 'DataOperacji');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (13, 'W', 'Wyksztalcenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (14, 'W', 'Rodzinnosc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (15, 'W', 'RokZachorowania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (16, 'W', 'CzasTrwaniaChoroby');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (17, 'W', 'PierwszyObjaw');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (18, 'W', 'Drzenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (19, 'W', 'Sztywnosc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (20, 'W', 'Spowolnienie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (21, 'W', 'ObjawyInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (22, 'W', 'ObjawyInneJakie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (23, 'W', 'CzasOdPoczObjDoWlLDopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (24, 'W', 'DyskinezyObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (25, 'W', 'DyskinezyOdLat');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (26, 'W', 'FluktuacjeObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (27, 'W', 'FluktuacjeOdLat');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (28, 'W', 'CzasDyskinez');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (29, 'W', 'CzasOFF');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (30, 'W', 'PoprawaPoLDopie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (31, 'W', 'PrzebyteLeczenieOperacyjnePD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (32, 'W', 'Papierosy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (33, 'W', 'Kawa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (34, 'W', 'ZielonaHerbata');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (35, 'W', 'Alkohol');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (36, 'W', 'ZabiegowWZnieczOgPrzedRozpoznaniemPD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (37, 'W', 'Zamieszkanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (38, 'W', 'NarazeniaNaToks');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (39, 'W', 'Uwagi');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (40, 'W', 'Nadcisnienie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (41, 'W', 'BlokeryKanWapn');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (42, 'W', 'DominujacyObjawObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (43, 'W', 'DominujacyObjawUwagi');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (44, 'W', 'ObjawyAutonomiczne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (45, 'W', 'RLS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (46, 'W', 'ObjawyPsychotyczne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (47, 'W', 'Depresja');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (48, 'W', 'Otepienie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (49, 'W', 'Dyzartria');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (50, 'W', 'DysfagiaObjaw');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (51, 'W', 'RBD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (52, 'W', 'ZaburzenieRuchomosciGalekOcznych');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (53, 'W', 'Apraksja');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (54, 'W', 'TestKlaskania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (55, 'W', 'ZaburzeniaWechowe');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (56, 'W', 'Ldopa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (57, 'W', 'LDopaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (58, 'W', 'Agonista');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (59, 'W', 'AgonistaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (60, 'W', 'Amantadyna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (61, 'W', 'AmantadynaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (62, 'W', 'MAOBinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (63, 'W', 'MAOBinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (64, 'W', 'COMTinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (65, 'W', 'COMTinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (66, 'W', 'Cholinolityk');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (67, 'W', 'CholinolitykObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (68, 'W', 'LekiInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (69, 'W', 'LekiInneJakie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (70, 'W', 'L_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (71, 'W', 'L_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (72, 'W', 'L_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (73, 'W', 'L_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (74, 'W', 'R_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (75, 'W', 'R_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (76, 'W', 'R_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (77, 'W', 'R_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (78, 'W', 'Wypis_Ldopa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (79, 'W', 'Wypis_LDopaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (80, 'W', 'Wypis_Agonista');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (81, 'W', 'Wypis_AgonistaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (82, 'W', 'Wypis_Amantadyna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (83, 'W', 'Wypis_AmantadynaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (84, 'W', 'Wypis_MAOBinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (85, 'W', 'Wypis_MAOBinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (86, 'W', 'Wypis_COMTinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (87, 'W', 'Wypis_COMTinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (88, 'W', 'Wypis_Cholinolityk');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (89, 'W', 'Wypis_CholinolitykObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (90, 'W', 'Wypis_LekiInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (91, 'W', 'Wypis_LekiInneJakie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (92, 'W', 'Wypis_L_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (93, 'W', 'Wypis_L_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (94, 'W', 'Wypis_L_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (95, 'W', 'Wypis_L_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (96, 'W', 'Wypis_R_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (97, 'W', 'Wypis_R_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (98, 'W', 'Wypis_R_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (99, 'W', 'Wypis_R_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (100, 'W', 'WydzielanieSliny');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (101, 'W', 'Dysfagia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (102, 'W', 'DysfagiaCzestotliwosc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (103, 'W', 'Nudnosci');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (104, 'W', 'Zaparcia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (105, 'W', 'TrudnosciWOddawaniuMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (106, 'W', 'PotrzebaNaglegoOddaniaMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (107, 'W', 'NiekompletneOproznieniePecherza');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (108, 'W', 'SlabyStrumienMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (109, 'W', 'CzestotliwowscOddawaniaMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (110, 'W', 'Nykturia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (111, 'W', 'NiekontrolowaneOddawanieMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (112, 'W', 'Omdlenia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (113, 'W', 'ZaburzeniaRytmuSerca');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (114, 'W', 'ProbaPionizacyjna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (115, 'W', 'WzrostPodtliwosciTwarzKark');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (116, 'W', 'WzrostPotliwosciRamionaDlonie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (117, 'W', 'WzrostPotliwosciBrzuchPlecy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (118, 'W', 'WzrostPotliwosciKonczynyDolneStopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (119, 'W', 'SpadekPodtliwosciTwarzKark');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (120, 'W', 'SpadekPotliwosciRamionaDlonie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (121, 'W', 'SpadekPotliwosciBrzuchPlecy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (122, 'W', 'SpadekPotliwosciKonczynyDolneStopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (123, 'W', 'NietolerancjaWysokichTemp');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (124, 'W', 'NietolerancjaNiskichTemp');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (125, 'W', 'Lojotok');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (126, 'W', 'SpadekLibido');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (127, 'W', 'KlopotyOsiagnieciaErekcji');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (128, 'W', 'KlopotyUtrzymaniaErekcji');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (129, 'B', 'DBS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (130, 'B', 'BMT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (131, 'B', 'UPDRS_I');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (132, 'B', 'UPDRS_II');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (133, 'B', 'UPDRS_18');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (134, 'B', 'UPDRS_19');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (135, 'B', 'UPDRS_20_FaceLipsChin');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (136, 'B', 'UPDRS_20_RHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (137, 'B', 'UPDRS_20_LHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (138, 'B', 'UPDRS_20_RFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (139, 'B', 'UPDRS_20_LFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (140, 'B', 'UPDRS_21_RHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (141, 'B', 'UPDRS_21_LHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (142, 'B', 'UPDRS_22_Neck');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (143, 'B', 'UPDRS_22_RHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (144, 'B', 'UPDRS_22_LHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (145, 'B', 'UPDRS_22_RFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (146, 'B', 'UPDRS_22_LFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (147, 'B', 'UPDRS_23_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (148, 'B', 'UPDRS_23_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (149, 'B', 'UPDRS_24_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (150, 'B', 'UPDRS_24_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (151, 'B', 'UPDRS_25_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (152, 'B', 'UPDRS_25_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (153, 'B', 'UPDRS_26_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (154, 'B', 'UPDRS_26_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (155, 'B', 'UPDRS_27');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (156, 'B', 'UPDRS_28');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (157, 'B', 'UPDRS_29');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (158, 'B', 'UPDRS_30');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (159, 'B', 'UPDRS_31');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (160, 'B', 'UPDRS_III');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (161, 'B', 'UPDRS_IV');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (162, 'B', 'UPDRS_TOTAL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (163, 'B', 'HYscale');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (164, 'B', 'SchwabEnglandScale');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (165, 'B', 'JazzNovo');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (166, 'B', 'Wideookulograf');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (167, 'B', 'Saccades');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (168, 'B', 'SaccadesLatencyMeanLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (169, 'B', 'SaccadesLatencyMeanRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (170, 'B', 'SaccadesDurationLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (171, 'B', 'SaccadesDurationRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (172, 'B', 'SaccadesAmplitudeLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (173, 'B', 'SaccadesAmplitudeRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (174, 'B', 'SaccadesPeakVelocityLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (175, 'B', 'SaccadesPeakVelocityRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (176, 'B', 'SaccadesLatencyMeanALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (177, 'B', 'SaccadesDurationALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (178, 'B', 'SaccadesAmplitudeALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (179, 'B', 'SaccadesPeakVelocityALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (180, 'B', 'Antisaccades');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (181, 'B', 'AntisaccadesPercentOfCorrectLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (182, 'B', 'AntisaccadesPercentOfCorrectRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (183, 'B', 'AntisaccadesLatencyMeanLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (184, 'B', 'AntisaccadesLatencyMeanRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (185, 'B', 'AntisaccadesDurationLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (186, 'B', 'AntisaccadesDurationRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (187, 'B', 'AntisaccadesAmplitudeLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (188, 'B', 'AntisaccadesAmplitudeRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (189, 'B', 'AntisaccadesPeakVelocityLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (190, 'B', 'AntisaccadesPeakVelocityRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (191, 'B', 'AntisaccadesPercentOfCorrectALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (192, 'B', 'AntisaccadesLatencyMeanALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (193, 'B', 'AntisaccadesDurationALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (194, 'B', 'AntisaccadesAmplitudeALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (195, 'B', 'AntisaccadesPeakVelocityALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (196, 'B', 'POM_Gain_SlowSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (197, 'B', 'POM_StDev_SlowSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (198, 'B', 'POM_Gain_MediumSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (199, 'B', 'POM_StDev_MediumSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (200, 'B', 'POM_Gain_FastSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (201, 'B', 'POM_StDev_FastSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (202, 'B', 'POM_Accuracy_SlowSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (203, 'B', 'POM_Accuracy_MediumSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (204, 'B', 'POM_Accuracy_FastSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (205, 'B', 'Tremorometria');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (206, 'B', 'TremorometriaLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (207, 'B', 'TremorometriaRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (208, 'B', 'TremorometriaLEFT_0_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (209, 'B', 'TremorometriaLEFT_1_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (210, 'B', 'TremorometriaLEFT_2_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (211, 'B', 'TremorometriaLEFT_3_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (212, 'B', 'TremorometriaLEFT_4_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (213, 'B', 'TremorometriaLEFT_5_6');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (214, 'B', 'TremorometriaLEFT_6_7');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (215, 'B', 'TremorometriaLEFT_7_8');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (216, 'B', 'TremorometriaLEFT_8_9');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (217, 'B', 'TremorometriaLEFT_9_10');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (218, 'B', 'TremorometriaLEFT_23_24');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (219, 'B', 'TremorometriaRIGHT_0_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (220, 'B', 'TremorometriaRIGHT_1_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (221, 'B', 'TremorometriaRIGHT_2_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (222, 'B', 'TremorometriaRIGHT_3_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (223, 'B', 'TremorometriaRIGHT_4_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (224, 'B', 'TremorometriaRIGHT_5_6');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (225, 'B', 'TremorometriaRIGHT_6_7');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (226, 'B', 'TremorometriaRIGHT_7_8');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (227, 'B', 'TremorometriaRIGHT_8_9');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (228, 'B', 'TremorometriaRIGHT_9_10');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (229, 'B', 'TremorometriaRIGHT_23_24');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (230, 'B', 'TestSchodkowy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (231, 'B', 'TestSchodkowyWDol');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (232, 'B', 'TestSchodkowyWGore');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (233, 'B', 'TestMarszu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (234, 'B', 'TestMarszuCzas1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (235, 'B', 'TestMarszuCzas2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (236, 'B', 'Posturografia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (237, 'B', 'MotionAnalysis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (238, 'B', 'Otwarte_Srednia_C_o_P_X');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (239, 'B', 'Otwarte_Srednia_C_o_P_Y');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (240, 'B', 'Otwarte_Srednia_P_T_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (241, 'B', 'Otwarte_Srednia_P_B_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (242, 'B', 'Otwarte_Perimeter_mm');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (243, 'B', 'Otwarte_PoleElipsy_mm2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (244, 'B', 'Zamkniete_Srednia_C_o_P_X');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (245, 'B', 'Zamkniete_Srednia_C_o_P_Y');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (246, 'B', 'Zamkniete_Srednia_P_T_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (247, 'B', 'Zamkniete_Srednia_P_B_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (248, 'B', 'Zamkniete_Perimeter_mm');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (249, 'B', 'Zamkniete_PoleElipsy_mm2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (250, 'B', 'WspolczynnikPerymetru_E_C_E_O_obie_stopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (251, 'B', 'WspolczynnikPowierzchni_E_C_E_O_obie_stopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (252, 'B', 'Biofeedback_Srednia_C_o_P_X');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (253, 'B', 'Biofeedback_Srednia_C_o_P_Y');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (254, 'B', 'Biofeedback_Srednia_P_T_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (255, 'B', 'Biofeedback_Srednia_P_B_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (256, 'B', 'Biofeedback_Perimeter_mm');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (257, 'B', 'Biofeedback_PoleElipsy_mm2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (258, 'B', 'UpAndGo');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (259, 'B', 'UpAndGoLiczby');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (260, 'B', 'UpAndGoKubekPrawa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (261, 'B', 'UpAndGoKubekLewa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (262, 'B', 'TST');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (263, 'B', 'TandemPivot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (264, 'B', 'WTT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (265, 'B', 'WprowadzilasWariantZapisal');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (266, 'B', 'ZmodyfikowalasWariantModyfikowal');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (267, 'B', 'OstatniaZmianaasOstatniaEdycjaWariantu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (268, 'W', 'PDQ39');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (269, 'W', 'AIMS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (270, 'W', 'Epworth');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (271, 'W', 'CGI');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (272, 'W', 'FSS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (273, 'W', 'TestZegara');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (274, 'W', 'MMSE');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (275, 'W', 'CLOX1_Rysunek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (276, 'W', 'CLOX2_Kopia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (277, 'W', 'AVLT_proba_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (278, 'W', 'AVLT_proba_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (279, 'W', 'AVLT_proba_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (280, 'W', 'AVLT_proba_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (281, 'W', 'AVLT_proba_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (282, 'W', 'AVLT_Suma');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (283, 'W', 'AVLT_Srednia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (284, 'W', 'AVLT_KrotkieOdroczenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (285, 'W', 'AVLT_Odroczony20min');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (286, 'W', 'AVLT_Rozpoznawanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (287, 'W', 'AVLT_BledyRozpoznania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (288, 'W', 'TestAVLTSrednia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (289, 'W', 'TestAVLTOdroczony');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (290, 'W', 'TestAVLTPo20min');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (291, 'W', 'TestAVLTRozpoznawanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (292, 'W', 'CVLT_proba_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (293, 'W', 'CVLT_proba_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (294, 'W', 'CVLT_proba_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (295, 'W', 'CVLT_proba_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (296, 'W', 'CVLT_proba_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (297, 'W', 'CVLT_Suma');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (298, 'W', 'CVLT_OSKO_krotkie_odroczenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (299, 'W', 'CVLT_OPKO_krotkie_odroczenie_i_pomoc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (300, 'W', 'CVLT_OSDO_po20min');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (301, 'W', 'CVLT_OPDO_po20min_i_pomoc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (302, 'W', 'CVLT_perseweracje');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (303, 'W', 'CVLT_WtraceniaOdtwarzanieSwobodne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (304, 'W', 'CVLT_wtraceniaOdtwarzanieZPomoca');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (305, 'W', 'CVLT_Rozpoznawanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (306, 'W', 'CVLT_BledyRozpoznania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (307, 'W', 'Benton_JOL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (308, 'W', 'WAIS_R_Wiadomosci');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (309, 'W', 'WAIS_R_PowtarzanieCyfr');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (310, 'W', 'WAIS_R_Podobienstwa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (311, 'W', 'BostonskiTestNazywaniaBMT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (312, 'W', 'BMT_SredniCzasReakcji_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (313, 'W', 'SkalaDepresjiBecka');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (314, 'W', 'SkalaDepresjiBeckaII');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (315, 'W', 'TestFluencjiK');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (316, 'W', 'TestFluencjiP');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (317, 'W', 'TestFluencjiZwierzeta');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (318, 'W', 'TestFluencjiOwoceWarzywa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (319, 'W', 'TestFluencjiOstre');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (320, 'W', 'TestLaczeniaPunktowA');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (321, 'W', 'TestLaczeniaPunktowB');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (322, 'W', 'ToL_SumaRuchow');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (323, 'W', 'ToL_LiczbaPrawidlowych');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (324, 'W', 'ToL_CzasInicjowania_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (325, 'W', 'ToL_CzasWykonania_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (326, 'W', 'ToL_CzasCalkowity_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (327, 'W', 'ToL_CzasPrzekroczony');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (328, 'W', 'ToL_LiczbaPrzekroczenZasad');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (329, 'W', 'ToL_ReakcjeUkierunkowane');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (330, 'W', 'InnePsychologiczne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (331, 'W', 'OpisBadania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (332, 'W', 'Wnioski');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (333, 'W', 'Holter');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (334, 'W', 'BadanieWechu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (335, 'W', 'WynikWechu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (336, 'W', 'LimitDysfagii');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (337, 'W', 'pH_metriaPrzełyku');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (338, 'W', 'SPECT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (339, 'W', 'SPECTWyniki');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (340, 'W', 'MRI');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (341, 'W', 'MRIwynik');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (342, 'W', 'USGsrodmozgowia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (343, 'W', 'USGWynik');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (344, 'W', 'Genetyka');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (345, 'W', 'GenetykaWynik');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (346, 'W', 'Surowica');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (347, 'W', 'SurowicaPozostało');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (348, 'W', 'Ferrytyna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (349, 'W', 'CRP');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (350, 'W', 'NTproCNP');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (351, 'W', 'URCA');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (352, 'W', 'WitD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (353, 'W', 'CHOL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (354, 'W', 'TGI');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (355, 'W', 'HDL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (356, 'W', 'LDL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (357, 'W', 'olLDL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (358, 'W', 'LaboratoryjneInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (359, 'W', 'WprowadzilasWizyteWprowadzil');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (360, 'W', 'ZmodyfikowalasWizyteEdytowal');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (361, 'W', 'OstatniaZmianaasOstatniaModyfikacja');




*/



/*




*/

-- testowanie wprowadzania wizyt
-- ==============================
/*


-- 
declare @message varchar(200);
declare @res int;
exec update_patient_l '2013-08-07/TEST1', 'BMT', 1951, 12, 1, 'STN', 2, 1, 'testowy', @res OUTPUT, @message OUTPUT;
	select @message, @res;

	select * from Pacjent

declare @message varchar(200);
declare @res int;
declare @vis_id int;
exec update_examination_questionnaire_partA '2013-08-07/TEST1', 1, '2012-11-23', '2012-11-23', '2012-11-24', 50, 2, 1, 2008, 1, 2012, 11, 6, 1, 0, 1, 1, 'Inne jakie', 3, 1, 2, 0, null, 2.5, 2.5, 1,
 1, 'test', @res OUTPUT, @vis_id OUTPUT, @message OUTPUT;
	select @message as Message, @vis_id as Visit_ID, @res as Result;
go



declare @message varchar(200);
declare @res int;
exec update_examination_questionnaire_partB 1, 1, 1, 2, 0, 1, 0, 1, 'uwagi', 1, 1, 2, 'dom.objaw uwagi', 2, 1, 0, 0.5, 1, 1, 2, 1, 1, 1, 2,
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
go 

declare @vals as IntValueListUdt;
declare @res int;
insert into @vals values ( 1 );
insert into @vals values ( 6 );

exec update_exam_int_attribute  1, 'ObjawyAutonomiczne', @vals, 'test', @res OUTPUT
select @res;
select * from WartoscAtrybutuWizytyInt
go

declare @vals as IntValueListUdt;
declare @res int;
insert into @vals values ( 1 );
insert into @vals values ( 3 );

exec update_exam_int_attribute  1, 'SPECTWynik', @vals, 'test', @res OUTPUT
select @res;
select * from WartoscAtrybutuWizytyInt
go

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

-- deprecated!
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
exec update_examination_questionnaire_partE_x01 1, 
	0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 3, 4, 0, 1, 2, 3, 3, 4, 0, 1,
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
	0, 1, 1, 'MRI wynik', 1, 2, 1, 'genetyka wynik', 1, 'surowica pozostało',
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



/*

select * from Uzytkownik

declare @pn varchar(20);
exec suggest_new_patient_number_with_group '2013-10-29', 'BMT', @pn OUTPUT;
select @pn

insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 's.szlufik', HashBytes('SHA1','pass4tpp'), 'Stanisław', 'Szlufik', 'stanislaw.szlufik@gmail.com', 1 )
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 'j.dutkiewicz', HashBytes('SHA1','jd4tpp'), 'Justyna', 'Dutkiewicz', 'justyna_dutkiewicz@wp.pl', 1 )
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 'p.habela', HashBytes('SHA1','pass;'), 'Piotr', 'Habela', 'habela@pjwstk.edu.pl', 1 )
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 'a.przybyszewski', HashBytes('SHA1','ap4tpp'), 'Andrzej', 'Przybyszewski', 'przy@pjwstk.edu.pl', 1 )
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 'd.koziorowski', HashBytes('SHA1','dk4tpp'), 'Dariusz', 'Koziorowski', 'dkoziorowski@esculap.pl', 1 )
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 'm.tomaszewski', HashBytes('SHA1','mt4tpp'), 'Michał', 'Tomaszewski', 'tomaszew@pjwstk.edu.pl', 1 )
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko, Email, Status ) values ( 'a.szymanski', HashBytes('SHA1','4722tpp'), 'Artur', 'Szymański', 's10248@pjwstk.edu.pl', 1 )
*/

