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


-- last rev. 2012-04-05
-- Error codes:
-- 1 user not found
-- 2 user group not found
create procedure assign_user_to_user_group (  @user_id int, @user_group_id int, @assigned bit, @result int OUTPUT)
as
begin
	set @result = 0;
	if not exists ( select * from Uzytkownik where IdUzytkownik = @user_id ) 
	begin
		set @result = 1;
		return;
	end;
	if not exists ( select * from Grupa_uzytkownikow where IdGrupa_uzytkownikow = @user_group_id ) 
	begin
		set @result = 2;
		return;
	end;
	if @assigned = 0
		delete from Uzytkownik_grupa_uzytkownikow where IdGrupa_uzytkownikow = @user_group_id and IdUzytkownik = @user_id;
	else
	if not exists ( select * from	Uzytkownik_grupa_uzytkownikow where IdGrupa_uzytkownikow = @user_group_id and IdUzytkownik = @user_id ) 
		insert into Uzytkownik_grupa_uzytkownikow ( IdGrupa_uzytkownikow, IdUzytkownik ) values ( @user_group_id, @user_id );
end;
go

-- last rev. 2012-04-05
-- Error codes:
-- 1 session not found
-- 2 session group not found
-- 3 obligatory parameter empty (length 0)
create procedure assign_session_to_session_group (  @session_id int, @session_group_id int, @assigned bit, @result int OUTPUT)
as
begin
	set @result = 0;
	if not exists ( select * from Sesja where IdSesja = @session_id ) 
	begin
		set @result = 1;
		return;
	end;
	if not exists ( select * from Grupa_sesji where IdGrupa_sesji= @session_group_id ) 
	begin
		set @result = 2;
		return;
	end;
	if @assigned = 0
		delete from Sesja_grupa_sesji where IdGrupa_sesji = @session_group_id and IdSesja = @session_id;
	else
	if not exists ( select * from	Sesja_grupa_sesji where IdGrupa_sesji = @session_group_id and IdSesja = @session_id ) 
		insert into Sesja_grupa_sesji ( IdGrupa_sesji, IdSesja ) values ( @session_group_id, @session_id );
end;
go


-- last rev. 2012-04-05
-- Error codes:
-- 1 session group not found
-- 2 user group not found
create procedure c (  @session_group_id int, @user_group_id int, @assigned bit, @result int OUTPUT)
as
begin
	set @result = 0;
	if not exists ( select * from Grupa_sesji where IdGrupa_sesji = @session_group_id ) 
	begin
		set @result = 1;
		return;
	end;
	if not exists ( select * from Grupa_uzytkownikow where IdGrupa_uzytkownikow= @user_group_id ) 
	begin
		set @result = 2;
		return;
	end;
	if @assigned = 0
		delete from Grupa_sesji_grupa_uzytkownikow where IdGrupa_sesji = @session_group_id and IdGrupa_uzytkownikow = @user_group_id;
	else
	if not exists ( select * from	Grupa_sesji_grupa_uzytkownikow where IdGrupa_sesji = @session_group_id and IdGrupa_uzytkownikow = @user_group_id ) 
		insert into Grupa_sesji_grupa_uzytkownikow ( IdGrupa_sesji, IdGrupa_uzytkownikow ) values ( @session_group_id, @user_group_id )
end;
go