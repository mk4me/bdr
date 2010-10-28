use Motion;
go


CREATE TABLE Widocznosc_atrybutu (
        IdUzytkownik         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wyswietlic        bit,
 )
go
 
CREATE INDEX X1Widocznosc_atrybutu ON Widocznosc_atrybutu
 (
        IdUzytkownik
 )
go
 
CREATE INDEX X2Widocznosc_atrybutu ON Widocznosc_atrybutu
 (
        IdAtrybut
 )
go

ALTER TABLE Widocznosc_atrybutu
        ADD PRIMARY KEY (IdUzytkownik, IdAtrybut)
go
 
 
ALTER TABLE Widocznosc_atrybutu
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
ALTER TABLE Widocznosc_atrybutu
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade;
go

CREATE TABLE Widocznosc_grupy_atrybutow (
        IdUzytkownik         int NOT NULL,
        IdGrupa_atrybutow            int NOT NULL,
        Wyswietlic        bit,
 )
go
 
CREATE INDEX X1Widocznosc_grupy_atrybutow ON Widocznosc_grupy_atrybutow
 (
        IdUzytkownik
 )
go
 
CREATE INDEX X2Widocznosc_grupy_atrybutow ON Widocznosc_grupy_atrybutow
 (
        IdGrupa_atrybutow
 )
go

ALTER TABLE Widocznosc_grupy_atrybutow
        ADD PRIMARY KEY (IdUzytkownik, IdGrupa_atrybutow)
go
 
 
ALTER TABLE Widocznosc_grupy_atrybutow
        ADD FOREIGN KEY (IdGrupa_atrybutow)
                              REFERENCES Grupa_atrybutow on delete cascade;
go
 
 
ALTER TABLE Widocznosc_grupy_atrybutow
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik on delete cascade;
go


create type AttributeViewSettingUdt as table
(
	AttributeName varchar(100),
	DescribedEntity varchar(20),
	Show bit
)
go

create type AttributeGroupViewSettingUdt as table
(
	AttributeGroupName varchar(100),
	DescribedEntity varchar(20),
	Show bit
)
go

create function identify_attribute( @att_name varchar(100), @att_entity varchar(20) )
returns int
as
begin
return ( select top 1 a.IdAtrybut from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow where a.Nazwa = @att_name and ga.Opisywana_encja = @att_entity );
end
go 

create function identify_attribute_group( @att_group_name varchar(100), @att_entity varchar(20) )
returns int
as
begin
return ( select top 1 IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = @att_group_name and Opisywana_encja = @att_entity );
end
go 

create procedure update_view_configuration(@user_login as varchar(30),  @att_vis as AttributeViewSettingUdt readonly, @grp_vis as AttributeGroupViewSettingUdt readonly, @result int OUTPUT )
as
begin
	declare @user_id int;
	set @user_id = dbo.identify_user(@user_login);
	
	set @result = 0;

	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;

	insert into Widocznosc_atrybutu ( IdUzytkownik, IdAtrybut, Wyswietlic )
	( select @user_id, dbo.identify_attribute(AttributeName,DescribedEntity), Show
	from @att_vis av where not exists (select * from Widocznosc_atrybutu where IdUzytkownik = @user_id and IdAtrybut = dbo.identify_attribute(AttributeName,DescribedEntity))) ;


	update Widocznosc_atrybutu set  Wyswietlic  = ( select top 1 Show from @att_vis where dbo.identify_attribute(AttributeName,DescribedEntity)=IdAtrybut )
	where IdUzytkownik = @user_id and exists (select * from @att_vis where dbo.identify_attribute(AttributeName,DescribedEntity)=IdAtrybut);

	insert into Widocznosc_grupy_atrybutow ( IdUzytkownik, IdGrupa_atrybutow, Wyswietlic )
	( select @user_id, dbo.identify_attribute_group(AttributeGroupName,DescribedEntity), Show 
	from @grp_vis where not exists (select * from Widocznosc_grupy_atrybutow where IdUzytkownik = @user_id and IdGrupa_atrybutow = dbo.identify_attribute_group(AttributeGroupName,DescribedEntity)) );

	update Widocznosc_grupy_atrybutow set  Wyswietlic  = ( select top 1 Show from @grp_vis where dbo.identify_attribute_group(AttributeGroupName,DescribedEntity)=IdGrupa_atrybutow ) 
	where IdUzytkownik = @user_id and exists (select * from @grp_vis where dbo.identify_attribute_group(AttributeGroupName,DescribedEntity)=IdGrupa_atrybutow);

end;
go


create procedure list_view_configuration_xml(@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService')
select 
	( select top 1 ga.Nazwa from Grupa_atrybutow ga where ga.IdGrupa_atrybutow = wga.IdGrupa_atrybutow ) "@AttributeGroupName",  
	( select top 1 ga.Opisywana_encja from Grupa_atrybutow ga where ga.IdGrupa_atrybutow = wga.IdGrupa_atrybutow ) "@DescribedEntity",  
	wga.Wyswietlic "@Show",
	(select a.Nazwa "@AttributeName", wa.Wyswietlic as "@Show"
		from Widocznosc_atrybutu wa join Atrybut a on wa.IdAtrybut = a.IdAtrybut 
		where wa.IdUzytkownik = dbo.identify_user(@user_login) and a.IdGrupa_atrybutow = wga.IdGrupa_atrybutow FOR XML PATH('AttributeView'), TYPE ) "AttributeViewList"
from Widocznosc_grupy_atrybutow wga 
 where wga.IdUzytkownik = dbo.identify_user(@user_login)  
    for XML PATH('AttributeGroupViewConfiguration'), ELEMENTS, root ('AttributeGroupViewConfigurationList')
go




create procedure update_stored_filters(@user_login as varchar(30), @filter as PredicateUdt readonly)
as
begin

	declare @user_id int;
	set @user_id = dbo.identify_user(@user_login);
	
	
		delete from Predykat 
		where IdUzytkownik = @user_id
		and not exists ( select * from @filter as f where	f.PredicateID = IdPredykat and
															f.ParentPredicate = IdRodzicPredykat and
														f.ContextEntity = EncjaKontekst and
														f.PreviousPredicate = IdPoprzedniPredykat and
														f.NextOperator = NastepnyOperator and
														f.FeatureName = NazwaWlasciwosci and
														f.Operator = Operator and
														f.Value = Wartosc and
														f.AggregateFunction = FunkcjaAgregujaca and
														f.AggregateEntity = EncjaAgregowana and
														@user_id = IdUzytkownik	);

	insert into Predykat
	( IdPredykat, IdRodzicPredykat,	EncjaKontekst, IdPoprzedniPredykat, NastepnyOperator, NazwaWlasciwosci, 
	Operator, Wartosc,	FunkcjaAgregujaca,	EncjaAgregowana, IdUzytkownik )
		(
		(select PredicateID as IdPredykat,
				ParentPredicate as IdRodzicPredykat,
				ContextEntity as EncjaKontekst,
				PreviousPredicate as IdPoprzedniPredykat,
				NextOperator as NastepnyOperator,
				FeatureName as NazwaWlasciwosci,
				Operator as Operator,
				Value as Wartosc,
				AggregateFunction as FunkcjaAgregujaca,
				AggregateEntity as EncjaAgregowana,
				@user_id as IdUzytkownik	
		from @filter )
		except
		(select * from Predykat where IdUzytkownik = @user_id)

		);
end
go

alter procedure define_attribute (@attr_name varchar(100), @group_name varchar(100), @entity varchar(20), @is_enum bit, @plugin_desc varchar(100), @type varchar(20), @unit varchar(10), @result int OUTPUT )
as
begin
	declare @group_id int;
	declare @storage_type varchar(20);
	set @result = 0;
	
	if exists ( select * from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
				where a.Nazwa = @attr_name and ga.Nazwa = @group_name and ga.Opisywana_encja = @entity )
	begin
		set @result = 1;
		return;
	end;
	select @group_id = IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = @group_name and Opisywana_encja = @entity;
	if ( @group_id is null )
	begin
		set @result = 2;
		return;
	end;
	if @type = 'float'
		set @storage_type = 'float';
	else if @type in ('int', 'decimal', 'nonNegativeInteger', 'nonNegativeDecimal') set @storage_type = 'integer';
	else if @type in ( 'shortString', 'longString', 'dateTime', 'date', 'TIMECODE' ) set @storage_type = 'string';
	else
		begin
			set @result = 3;
			return;
		end;
	insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Plugin, Podtyp_danych, Jednostka)
				values ( @group_id, @attr_name, @storage_type, @is_enum, @plugin_desc, @type, @unit );
	
end
go