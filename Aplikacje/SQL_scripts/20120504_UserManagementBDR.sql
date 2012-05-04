use Motion;
go

create procedure get_user_assignments
as
with
SG as (select * from Grupa_sesji SessionGroup ),
UG as (select * from Grupa_uzytkownikow UserGroup),
UGA as (select * from Grupa_sesji_grupa_uzytkownikow UserGroupAssignment),
UA as (select * from Uzytkownik_grupa_uzytkownikow UserAssignment )
select
 (select 
	IdGrupa_sesji as SessionGroupID,
	Nazwa as SessionGroupName
	from SG SessionGroup for XML AUTO, TYPE
 ) SessionGroups,
 (select 
	IdGrupa_uzytkownikow as UserGroupID,
	Nazwa as UserGroupName
	from UG UserGroup for XML AUTO, TYPE
 ) UserGroups,
 (select 
	IdGrupa_uzytkownikow as UserGroupID,
	IdGrupa_sesji as SessionGroupID
	from UGA UserGroupAssignment for XML AUTO, TYPE
 ) UserGroupAssignments,
  (select 
	IdUzytkownik as UserID,
	Imie as FirstName,
	Nazwisko as LastName,
	Email as Email
	from Uzytkownik DBUser where Status > 0
	FOR XML AUTO, TYPE 
 ) DBUsers,
 (select 
	IdUzytkownik as UserID,
	IdGrupa_uzytkownikow as UserGroupID
	from UA UserAssignment FOR XML AUTO, TYPE 
 ) UserAssignments
 for XML RAW ('UserAssignments'), TYPE;
go

exec get_user_assignments
