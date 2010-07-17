use Motion;
go

create procedure list_users_xml
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService')
select Login "@Login", Imie "@FirstName", Nazwisko "@LastName"
	from Uzytkownik
    for XML PATH('UserDetails'), root ('UserList')
go

create procedure list_session_privileges_xml (@user_login varchar(30), @sess_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService')
select 
	u.Login "@Login", case us.Zapis when 0 then 'false' else 'true' end "@CanWrite"
	from Uprawnienia_Sesja us join Uzytkownik u on us.IdUzytkownik = u.IdUzytkownik
	where us.IdSesja = @sess_id and us.IdSesja in (select IdSesja from user_accessible_sessions_by_login(@user_login) )
    for XML PATH('SessionPrivilege'), root ('SessionPrivilegeList')
go

