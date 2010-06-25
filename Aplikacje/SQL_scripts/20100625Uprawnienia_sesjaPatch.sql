use Motion;

alter table Uzytkownik
 alter column Login varchar(30)
go


alter table Uprawnienia_sesja
	drop column Odczyt
go

alter table Uprawnienia_sesja
	drop column Wglad_w_dane_pacjenta
go

create procedure check_user_account( @user_login varchar(30), @result int OUTPUT )
as
 set @result = ((select count(*) from Uzytkownik where Login = @user_login))
go

create procedure create_user_account (@user_login varchar(30), @user_first_name varchar(30), @user_last_name varchar(50))
as
insert into Uzytkownik ( Login, Imie, Nazwisko) values (@user_login, @user_first_name, @user_last_name );
go

create function user_sessions_by_user_id( @user_id int)
returns table
as
return
select * from Sesja where IdUzytkownik = @user_id
go

create function user_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.* from Uzytkownik u inner join Sesja s on u.IdUzytkownik = s.IdUzytkownik where u.Login = @user_login 
go

create function identify_user( @user_login varchar(30) )
returns int
as
begin
return ( select top 1 IdUzytkownik from Uzytkownik where Login = @user_login );
end
go

create function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.* from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.* from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
go




create procedure set_session_privileges (@granting_user_login varchar(30), @granted_user_login varchar(30), @sess_id int, @write bit)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	insert into Uprawnienia_sesja ( IdSesja, IdUzytkownik, Zapis) values (@sess_id, dbo.identify_user(@granted_user_login), @write);
end
go

