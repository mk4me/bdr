use Motion;
/* Definicje atrybutow statycznych */

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'trial');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'TrialID', 'integer', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'TrialDescription', 'string', 0, 'longString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'Duration', 'int', 0, 'nonNegativeInteger', 'second') 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'performer');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer'), 'PerformerID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer'), 'FirstName', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer'), 'LastName', 'string', 0, 'shortString', NULL) 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'performer_conf');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer_conf'), 'PerformerConfID', 'int', 0, 'ID', NULL) 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'measurement_conf');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='measurement_conf'), 'MeasurementConfID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='measurement_conf'), 'MeasurementConfName', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='measurement_conf'), 'MeasurementConfKind', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='measurement_conf'), 'MeasurementConfDescription', 'string', 0, 'longString', NULL) 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'session');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'SessionID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'UserID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'LabID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'MotionKindID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'SessionDate', 'string', 0, 'dateTime', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'SessionDescription', 'string', 0, 'longString', NULL) 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'measurement');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='measurement'), 'MeasurementID', 'int', 0, 'ID', NULL) 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'file');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='file'), 'FileID', 'int', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='file'), 'FileName', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='file'), 'FileDescription', 'string', 0, 'longString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='file'), 'SubdirPath', 'string', 0, 'shortString', NULL) 
go

/* Uzytkownicy */

 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'habela', 'Piotr', 'Habela')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'kaczmarski', 'Krzysztof', 'Kaczmarski')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'applet_user', 'Uzytkownik', 'Testowy')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'wiktor', 'Wiktor', 'Filipowicz')
go
insert into Rodzaj_ruchu (Nazwa) values ('walk')
go
insert into Rodzaj_ruchu (Nazwa) values ('run')
go
insert into Performer (Imie, Nazwisko) values ( 'Jan', 'Kowalski')
go
insert into Performer (Imie, Nazwisko) values ( 'Anna', 'Nowak')
go
insert into Performer (Imie, Nazwisko) values ( 'Magdalena', 'Tabaka')
go
insert into Performer (Imie, Nazwisko) values ( 'Dawid', 'Pisko')
go
insert into Laboratorium (Nazwa) values ('PJWSTK')
go

insert into Grupa_sesji ( Nazwa ) values ('SesjeTestowe')
go
insert into Grupa_sesji ( Nazwa ) values ('SesjeFaktyczne')
go

/* Wybrane atrybuty generyczne */

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('General_performer_attributes', 'performer');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Sex', 'string', 1, 'shortString', NULL)    
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'M') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'F') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'DateOfBirth', 'string', 0, 'date', NULL)    
go


exec list_group_sessions_attributes_xml 'applet_user', 1

select * from Grupa_sesji

select * from Pomiar_performer

delete from Konfiguracja_pomiarowa

select * from Pomiar

select * from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow