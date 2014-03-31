use Motion;
go

-- last rev. 2014-03-31
alter procedure list_users_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService')
select IDUzytkownik "@ID" , Login "@Login", Imie "@FirstName", Nazwisko "@LastName"
	from Uzytkownik
    for XML PATH('UserDetails'), root ('UserList')
go

-- last rev. 2014-03-31
alter procedure get_user ( @user_login varchar(30) )
as
select IdUzytkownik, Login, Imie, Nazwisko, Email
	from Uzytkownik
	where Login = @user_login
go

