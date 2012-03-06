use Motion;
go
-- last rev. 2012-02-28
alter procedure validate_password(@login varchar(30), @pass varchar(25), @res bit OUTPUT)
as
begin
declare @c int = 0;
select @c = COUNT(*) from Uzytkownik where Login = @login and Haslo = HashBytes('SHA1',@pass) and Status > 0;
if (@c = 1) set @res = 1; else set @res = 0;
end;
go

alter procedure update_user_account(@user_login varchar(30), @user_password varchar(20),  @user_new_password varchar(20), @user_email varchar(50), @user_first_name varchar(30), @user_last_name varchar(50), @result int OUTPUT)
as
begin

	set @result = 0;

	if ( LEN(@user_login)=0 or LEN(@user_password)=0 or LEN(@user_email) = 0 )
		begin
			set @result = 3;
			return;
		end;

	if not exists(select * from Uzytkownik where Login = @user_login and Haslo = HashBytes('SHA1',@user_password) and Status > 0 )
		begin
			set @result = 1;
			return;
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


-- last rev. 2012-02-28
create function f_metadata_time_stamp()
returns datetime
as
begin
return ( 
select max(ts) as time_stamp from
	(
	select max(Ostatnia_zmiana) as ts from Grupa_atrybutow 
	union
	select max(Ostatnia_zmiana) as ts from Laboratorium 
	union
	select max(Ostatnia_zmiana) as ts from Rodzaj_ruchu
	union 
	select max(Ostatnia_zmiana) as ts from Grupa_sesji 
	) as q1
 );
end
go


alter procedure get_metadata @user_login varchar(30)
as
with
SG as (select * from Grupa_sesji SessionGroup ),
MK as (select * from Rodzaj_ruchu MotionKind),
LB as (select * from Laboratorium Lab),
AG as (select * from Grupa_atrybutow AttributeGroup )
select
 dbo.f_metadata_time_stamp() LastModified,
 (select 
	IdGrupa_sesji as SessionGroupID,
	Nazwa as SessionGroupName
	from SG SessionGroup for XML AUTO, TYPE
 ) SessionGroups,
 (select 
	IdRodzaj_ruchu as MotionKindID,
	Nazwa as MotionKindName
	from MK MotionKind for XML AUTO, TYPE
 ) MotionKinds,
 (select 
	IdLaboratorium as LabID,
	Nazwa as LabName
	from LB Lab for XML AUTO, TYPE
 ) Labs,
 (select 
	IdGrupa_atrybutow as AttributeGroupID,
	Nazwa as AttributeGroupName,
	Opisywana_encja as DescribedEntity,
	(select 
		Nazwa as AttributeName,
		Podtyp_danych as AttributeType,
		Jednostka as Unit,
		(select
			Wartosc_wyliczeniowa EnumValue
		 from Wartosc_wyliczeniowa Enumeration where Enumeration.IdAtrybut = Attribute.IdAtrybut for XML AUTO, TYPE ) EnumValues
		from Atrybut Attribute where Attribute.IdGrupa_atrybutow = AttributeGroup.IdGrupa_atrybutow FOR XML AUTO, TYPE 
	) Attributes
	from AG AttributeGroup FOR XML AUTO, TYPE 
 ) AttributeGroups
 for XML RAW ('Metadata'), TYPE;
go

