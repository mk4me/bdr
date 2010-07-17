use Motion;


alter table Sesja add PublicznaZapis bit
-- Constraint removal pattern
--declare @sql nvarchar(200);
--WHILE EXISTS(select * from INFORMATION_SCHEMA.TABLE_CONSTRAINTS where constraint_catalog = 'Motion' and table_name = @table)
--BEGIN
--    select    @sql = 'ALTER TABLE ' + @table + ' DROP CONSTRAINT ' + CONSTRAINT_NAME 
--    from    INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
--    where    constraint_catalog = 'Motion' and 
--            table_name = @table
--    exec    sp_executesql @sql
--END


-- Accessing default constraints:

--SELECT name
--FROM sys.default_constraints where parent_object_id = OBJECT_ID('dbo.Sesja')


-- Find all constranits:
--SELECT OBJECT_NAME(id) AS TableName, OBJECT_NAME(constid) AS ConstraintName
--FROM sysconstraints where OBJECT_NAME(id) = 'Uzytkownik'

declare @sql nvarchar(200);
WHILE EXISTS(SELECT * FROM sys.default_constraints where parent_object_id = OBJECT_ID('dbo.Sesja'))
BEGIN
    select    @sql = 'ALTER TABLE Sesja DROP CONSTRAINT ' + name
    FROM sys.default_constraints where parent_object_id = OBJECT_ID('dbo.Sesja')
    exec    sp_executesql @sql
END

ALTER TABLE Sesja WITH NOCHECK
ADD CONSTRAINT DF__Sesja__Publiczna DEFAULT 0 FOR Publiczna
go
ALTER TABLE Sesja WITH NOCHECK
ADD CONSTRAINT DF__Sesja__PublicznaZapis DEFAULT 0 FOR PublicznaZapis
go

ALTER TABLE Uzytkownik WITH NOCHECK
ADD CONSTRAINT U_UzytkownikLogin UNIQUE (Login)
go

select * from Sesja


create procedure alter_session_visibility (@granting_user_login varchar(30), @sess_id int, @public bit, @writeable bit)
as
begin

	if (select COUNT(*) from user_sessions_by_login(@granting_user_login) where IdSesja = @sess_id)<>1 RAISERROR ('Session not owned by granting user', 12, 1 )
	else
	update Sesja set Publiczna = @public, PublicznaZapis = @writeable where IdSesja = @sess_id;
end
go
create function user_accessible_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select * from user_accessible_sessions( dbo.identify_user( @user_login ))
go
