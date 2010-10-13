use Motion;
/* Definicje atrybutow statycznych */

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'trial');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'TrialID', 'integer', 0, 'ID', NULL) 
-- Czy klucze obce powinnismy uwzgledniac?
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'SessionID', 'integer', 0, 'ID', NULL) 
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

insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'Medical_performer_data', 'performer' )
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Medical_performer_data'), 'Diagnosis', 'string', 0, 'shortString', NULL)
go

insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'Anthropometric_data', 'performer_conf' )
go
-- waga [kg]					// BodyMass
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'BodyMass', 'integer', 0, 'nonNegativeInteger', 'kg')    
go
-- wzrost [mm]				// Height 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'Height', 'integer', 0, 'nonNegativeInteger', 'mm')    
go


-- opis/notatki				// Notes
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Notes', 'string', 0, 'longString', NULL)    
go

-- iloœæ markerów				// MarkerCount
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'MarkerCount', 'integer', 0, 'nonNegativeInteger', NULL)    
go
-- d³ugoœæ lewej nogi [mm]	// LeftLegLength
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftLegLength', 'integer', 0, 'nonNegativeInteger', 'mm')    
go 
-- d³ugoœæ prawej nogi [mm]	// RightLegLength 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightLegLength', 'integer', 0, 'nonNegativeInteger', 'mm')    
go
-- szerokoœæ nasad kolanowych dla lewej nogi [mm]	// LeftKneeWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftKneeWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
go
-- szerokoœæ nasad kolanowych dla prawej nogi [mm]	// RightKneeWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightKneeWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
go
-- odleg³oœæ kostki bocznej od przyœrodkowej dla lewej nogi [mm]	// LeftAnkleWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftAnkleWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
go
-- odleg³oœæ kostki bocznej od przyœrodkowej dla prawej nogi [mm]  // RightAnkleWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightAnkleWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
go
insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'General_session_attributes', 'session' )
go

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'SessionName', 'string', 0, 'shortString', NULL)    
go
-- iloœæ aktorów // PerformerCount
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'PerformerCount', 'integer', 0, 'nonNegativeInteger', NULL)    
go
-- czas trwania (frames, timecode) // Duration ???
--  FTP // ???
-- opis (jest)
-- notatki // Notes
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'Notes', 'string', 0, 'longString', NULL)    
go
-- zakresy ruchowe (run, jump, hop, sit, trot, dance, ...) -> IdRodzajRuchu
insert into Rodzaj_ruchu ( Nazwa ) values ('run')
go
insert into Rodzaj_ruchu ( Nazwa ) values ('walk') 
insert into Rodzaj_ruchu ( Nazwa ) values ('jump')
go
insert into Rodzaj_ruchu ( Nazwa ) values ('hop')
go
insert into Rodzaj_ruchu ( Nazwa ) values ('sit')
go
insert into Rodzaj_ruchu ( Nazwa ) values ('trot')
go
insert into Rodzaj_ruchu ( Nazwa ) values ('dance')
go

select * from Rodzaj_ruchu

-- temperatura otoczenia
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'Temperature', 'integer', 0, 'nonNegativeInteger', 'C')    
go


-- ------------------------------------------------------------------  powyzsze Wgrane 2010-09-11

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('TestingPC', 'performer_conf');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'TestingPC' and Opisywana_encja='performer_conf'), 
'Weight', 'integer', 0, 'nonNegativeInteger', NULL) 

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('TestingMeasurement', 'measurement');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'TestingMeasurement' and Opisywana_encja='measurement'), 
'Relevance', 'integer', 0, 'nonNegativeInteger', NULL) 

select * from Pomiar

perf = 1, sesja = 1 atr = 45 kp = 1

insert into Wartosc_atrybutu_konfiguracji_performera (IdKonfiguracja_performera, IdAtrybut, Wartosc_liczba ) values (1, 45, 80 )
insert into Wartosc_atrybutu_pomiaru ( IdPomiar, IdAtrybut, Wartosc_liczba ) values ( 1, 46, 5 )
       
       select * from Wartosc_atrybutu_konfiguracji_performera
       
       exec get_performer_configuration_by_id_xml 1
       exec get_session_by_id_xml 'habela', 3
       
       exec list_session_performer_configurations_attributes_xml 'habela', 1
       
       select * from Pomiar
       
       exec list_measurement_conf_measurements_attributes_xml 'habela', 2
       
       exec list_measurement_conf_sessions_attributes_xml 'habela', 2
       
       select * from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow
       declare @res int;
       exec set_session_attribute 1, 'SessionName','Pierwsza',1,@res