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



-- last rev. 2013-05-28 -- DO ZASTAPIENIA - zmiana struktury !!!
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
			set @komunikat = 'Badanie rodzaju '+ cast ( @Wizyta as varchar )+' pacjenta o numerze '+ cast ( @NrPacjenta as varchar )+' ju? istnieje w bazie.';
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
go

-- SLOWNIKOWANIE (listowanie opcji i walidacje)
-- ============================================

create function validate_input( @table_name varchar(30), @attr_name varchar(50), @value tinyint )
returns bit
as
begin
return ( select count(*) from Slownik where Tabela = @table_name and Atrybut = @attr_name and Klucz = @value   );
end
go

create procedure get_enumeration( @table_name varchar(30), @attr_name varchar(50) )
as
select Klucz as Value, Definicja as Label  from Slownik where Tabela = @table_name and Atrybut = @attr_name
go


/*
CREATE TABLE Slownik (
	Tabela  varchar(30) not null,
	Atrybut	varchar(50) not null,
	Klucz	tinyint not null,
	Definicja	varchar(50) not null
)
go
*/


-- WGRYWANIE DANYCH I WALIDACJA
-- ============================

-- @res codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message

create procedure update_patient  (	@NumerPacjenta	varchar(20), @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @allow_updtate_existing bit, @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @update bit;
	set @result = 0;
	set @update = 0;
	set @message = '';
	if(( select count(*) from Pacjent where NumerPacjenta = @NumerPacjenta )>0 )
	begin
		if ( @allow_updtate_existing = 0 )
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

-- @res codes: 0 = OK, 3 = patient of this number not found, 1 = visit already exists while run in no-update mode,  exist 2 = validation failed - see message
create procedure update_examination_questionnaire  (@NumerPacjenta varchar(20), @RodzajWizyty tinyint, @Wyksztalcenie	tinyint, @Rodzinnosc tinyint, @RokZachorowania	smallint, @MiesiacZachorowania tinyint,
	@PierwszyObjaw	tinyint, @CzasOdPoczObjDoWlLDopy	tinyint, @DyskinezyObecnie	tinyint, @CzasDyskinez	decimal(3,1), @FluktuacjeObecnie tinyint, @FluktuacjeOdLat	decimal(3,1), @Papierosy tinyint,
	@Kawa	tinyint, @ZielonaHerbata tinyint,	@Alkohol	tinyint, @ZabiegowWZnieczOgPrzedRozpoznaniemPD tinyint,	@Zamieszkanie tinyint, @NarazenieNaToks	tinyint, 
	@allow_updtate_existing bit,
	@result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @patient_id int;
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
	if (select count(*) from Wizyta where IdPacjent = @patient_id and RodzajWizyty = @RodzajWizyty ) > 0 
	begin
		if (@allow_updtate_existing = 0)
			begin
				set @result = 1;
				return;
			end;
		set @update = 1;
	end;

	-- validations

	if dbo.validate_input('Wizyta', 'RodzajWizyty', @RodzajWizyty) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute RodzajWizyty';
		return;
	end;
	if dbo.validate_input('Wizyta', 'Wyksztalcenie', @Wyksztalcenie) = 0
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

	if dbo.validate_input('Wizyta', 'PierwszyObjaw', @PierwszyObjaw) = 0
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

	if dbo.validate_input('Wizyta', 'Papierosy', @Papierosy) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Papierosy';
		return;
	end;
	if dbo.validate_input('Wizyta', 'Kawa', @Kawa) = 0
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

	if dbo.validate_input('Wizyta', 'Alkohol', @Alkohol) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Alkohol';
		return;
	end;
	if dbo.validate_input('Wizyta', 'Zamieszkanie', @Zamieszkanie) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute Zamieszkanie';
		return;
	end;
	if dbo.validate_input('Wizyta', 'NarazenieNaToks', @NarazenieNaToks) = 0
	begin
		set @result = 2;
		set @message = 'Invalid value for attribute NarazenieNaToks';
		return;
	end;
	-- /validations
	-- depending on update
	if(@update = 0)
		insert into Wizyta (	RodzajWizyty, IdPacjent, Wyksztalcenie,	Rodzinnosc,	RokZachorowania, MiesiacZachorowania, PierwszyObjaw, CzasOdPoczObjDoWlLDopy, DyskinezyObecnie,
								CzasDyskinez, FluktuacjeObecnie, FluktuacjeOdLat,	Papierosy,	Kawa, ZielonaHerbata, Alkohol, ZabiegowWZnieczOgPrzedRozpoznaniemPD,
								Zamieszkanie, NarazenieNaToks )
					values (	@RodzajWizyty, @patient_id, @Wyksztalcenie,	@Rodzinnosc, @RokZachorowania, @MiesiacZachorowania, @PierwszyObjaw, @CzasOdPoczObjDoWlLDopy, @DyskinezyObecnie,
								@CzasDyskinez, @FluktuacjeObecnie, @FluktuacjeOdLat, @Papierosy,	@Kawa, @ZielonaHerbata, @Alkohol, @ZabiegowWZnieczOgPrzedRozpoznaniemPD,
								@Zamieszkanie, @NarazenieNaToks );
	else
		update Wizyta
		set Wyksztalcenie = @Wyksztalcenie,	Rodzinnosc = @Rodzinnosc,	RokZachorowania = @RokZachorowania, 
				MiesiacZachorowania = @MiesiacZachorowania, PierwszyObjaw = @PierwszyObjaw, CzasOdPoczObjDoWlLDopy = @CzasOdPoczObjDoWlLDopy, DyskinezyObecnie = @DyskinezyObecnie,
								CzasDyskinez = @CzasDyskinez, FluktuacjeObecnie = @FluktuacjeObecnie, FluktuacjeOdLat = @FluktuacjeOdLat,	Papierosy = @Papierosy,	Kawa = @Kawa, 
								ZielonaHerbata = @ZielonaHerbata, Alkohol = @Alkohol, ZabiegowWZnieczOgPrzedRozpoznaniemPD = @ZabiegowWZnieczOgPrzedRozpoznaniemPD,
								Zamieszkanie = @Zamieszkanie, NarazenieNaToks = @NarazenieNaToks 
		where RodzajWizyty = @RodzajWizyty and IdPacjent = @patient_id;

	return;
end;


/*

delete from Slownik

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	0,	'przedoperacyjna' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	5,	'po pó³ roku' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	10,	'rok po DBS' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	20,	'2 lata po DBS' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	30,	'3 lata po DBS' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	40,	'4 lata po DBS' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	50,	'5 lata po DBS' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	1,	'podstawowe' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	2,	'zawodowe' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	3,	'œrednie' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Wyksztalcenie',	4,	'wy¿sze' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	1,	'zaburzenia równowagi' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	2,	'spowolnienie' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	3,	'sztywnoœæ' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	4,	'dr¿enie' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	5,	'otêpienie' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	6,	'dyskinezy i fluktuacje' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	7,	'objawy autonomiczne' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'PierwszyObjaw',	8,	'inne' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	1,	'nigdy' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	2,	'w przesz³oœci' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Papierosy',	3,	'obecnie' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	1,	'mniej ni¿ 1/tyg.' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	2,	'kilka / tydz.' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Kawa',	3,	'codziennie' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	1,	'mniej ni¿ 1/tyg.' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	2,	'kilka / mies.' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	3,	'codziennie niewiele' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Alkohol',	4,	'nadu¿ywa' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	0,	'wieœ' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'Zamieszkanie',	1,	'miasto' );

insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	0,	'brak' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	1,	'zatrucie CO' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	2,	'toksyczne substancje przemys³owe' );
insert into Slownik ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'NarazenieNaToks',	3,	'narkotyki' );

insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'test', HashBytes('SHA1','pass'), 'U¿ytkownik1', 'Testowy')
insert into Uzytkownik ( Login, Haslo, Imie, Nazwisko ) values ( 'testowy', HashBytes('SHA1','testowy'), 'U¿ytkownik2', 'Testowy')


*/


select dbo.validate_input ('Wizyta', 'PierwszyObjaw', 3 )

exec get_enumeration  'Wizyta', 'PierwszyObjaw'


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