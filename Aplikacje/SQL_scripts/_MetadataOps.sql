use Motion;


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


