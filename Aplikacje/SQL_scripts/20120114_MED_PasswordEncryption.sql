use Motion_Med;

/*
DROP ASYMMETRIC KEY HMDB_PJWSTK;

CREATE ASYMMETRIC KEY HMDB_PJWSTKga
    WITH ALGORITHM = RSA_512
    ENCRYPTION BY PASSWORD = 'M0t10n8aza1228'; 
GO

alter table Uzytkownik drop DF__Uzytkowni__Haslo
alter table Uzytkownik alter column Haslo varbinary(100) -- default 0x00 not null;
alter table Uzytkownik add constraint DF__Uzytkowni__Haslo default 0x00 for Haslo

select EncryptByAsymKey(AsymKey_ID('HMDB_PJWSTK'), 'aplet4Motion')

update Uzytkownik set Haslo = EncryptByAsymKey(AsymKey_ID('HMDB_PJWSTK'), 'aplet4Motion') where Login = 'applet_user'
*/


insert into Uzytkownik ( Imie, Nazwisko, Login, LoginBDR, Haslo ) values ( 'Uzytkownik', 'Testowy', 'applet_user', 'applet_user', HashBytes('SHA1','aplet4Motion'));
go

create procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass);
if (@c = 1) set @res = 1; else set @res = 0;
end;
go

create procedure create_user(@name varchar(30), @surname varchar(50),  @login varchar(50), @bdr_login varchar(50), @pass varchar(25), @res int OUTPUT)
as
begin
if exists(select * from Uzytkownik where Login = @login)
	begin
		set @res = 1;
		return;
	end;
insert into Uzytkownik ( Imie, Nazwisko, Login, LoginBDR, Haslo ) values ( @name, @surname, @login, @bdr_login, HashBytes('SHA1',@pass));
return 0;
end;
go

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






