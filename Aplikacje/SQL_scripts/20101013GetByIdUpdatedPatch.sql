use Motion;
go

  CREATE TABLE Wartosc_atrybutu_konfiguracji_performera (
        IdKonfiguracja_performera         int NOT NULL,
        IdAtrybut            int NOT NULL,
        Wartosc_tekst        varchar(100) NULL,
        Wartosc_liczba       numeric(10,2) NULL,
        Wartosc_zmiennoprzecinkowa float NULL,
        Wartosc_Id int NULL
 )
go
 
 CREATE INDEX X1Wartosc_atrybutu_konfiguracji_performera ON Wartosc_atrybutu_konfiguracji_performera
 (
        IdKonfiguracja_performera
 )
go
 
 CREATE INDEX X2Wartosc_atrybutu_konfiguracji_performera ON Wartosc_atrybutu_konfiguracji_performera
 (
        IdAtrybut
 )
go

 CREATE INDEX X3Wartosc_atrybutu_konfiguracji_performera ON Wartosc_atrybutu_konfiguracji_performera
 (
        Wartosc_Id
 )
go 
 
 ALTER TABLE Wartosc_atrybutu_konfiguracji_performera
        ADD PRIMARY KEY (IdKonfiguracja_performera, IdAtrybut)
go
 
 
  ALTER TABLE Wartosc_atrybutu_konfiguracji_performera
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut on delete cascade;
go
 
 
 ALTER TABLE Wartosc_atrybutu_konfiguracji_performera
        ADD FOREIGN KEY (IdKonfiguracja_performera)
                              REFERENCES Konfiguracja_performera on delete cascade;
go
ALTER TABLE Wartosc_Atrybutu_konfiguracji_performera
        ADD FOREIGN KEY (Wartosc_Id) REFERENCES Plik
go


-- WPROWADZIC DO PLIKU GLOWNEGO !!!


create function list_performer_configuration_attributes ( @pc_id int )
returns TABLE as
return 
select 
	a.Nazwa as Name, 
	(case a.Typ_danych 
		when 'string' then cast ( wakp.Wartosc_tekst as SQL_VARIANT )
		when 'integer' then cast ( wakp.Wartosc_liczba as SQL_VARIANT )
		else cast ( wakp.Wartosc_zmiennoprzecinkowa as SQL_VARIANT) end ) as Value,
		a.Typ_danych as Type,
		ga.Nazwa as AttributeGroup,
		'performer_conf' as Entity
from Atrybut a 
inner join Wartosc_atrybutu_konfiguracji_performera wakp on a.IdAtrybut=wakp.IdAtrybut
inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
where wakp.IdKonfiguracja_performera = @pc_id and a.Typ_danych <> 'file'
go


alter procedure get_measurement_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdPomiar as MeasurementID,
				(select * from list_measurement_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Pomiar MeasurementDetailsWithAttributes where IdPomiar=@res_id
			for XML AUTO, ELEMENTS
go

alter procedure get_measurement_configuration_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdKonfiguracja_pomiarowa as MeasurementConfID,
				Nazwa as MeasurementConfName,
				Rodzaj as MeasurementConfKind,
				Opis as MeasurementConfDescription,
				(select * from list_measurement_configuration_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Konfiguracja_pomiarowa MeasurementConfDetailsWithAttributes where IdKonfiguracja_pomiarowa=@res_id
			for XML AUTO, ELEMENTS
go


create procedure get_performer_configuration_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				IdKonfiguracja_performera as PerformerConfID,
				IdSesja as SessionID,
				IdPerformer as PerformerID,
				(select * from list_performer_configuration_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE ) as Attributes 
			from Konfiguracja_performera PerformerConfDetailsWithAttributes where IdKonfiguracja_performera=@res_id
			for XML AUTO, ELEMENTS
go

create procedure list_session_performer_configurations_attributes_xml(@user_login varchar(30), @sess_id int)
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select 
	IdKonfiguracja_performera as PerformerConfID, 
	IdSesja as SessionID, 
	IdPerformer as PerformerID, 
	(select * from list_performer_configuration_attributes ( IdKonfiguracja_performera ) Attribute FOR XML AUTO, TYPE ) as Attributes 
from Konfiguracja_performera PerformerConfDetailsWithAttributes where (IdSesja in (select s.IdSesja from user_accessible_sessions_by_login(@user_login) s)) and IdSesja=@sess_id
    for XML AUTO, ELEMENTS, root ('SessionPerformerConfWithAttributesList')
go