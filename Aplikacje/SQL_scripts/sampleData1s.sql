use Motion;
go




declare @res int;
--declare @id int;
--exec create_session 'habela', 1, 'run', '2011-01-01', '2011-01-10-B0001-S01', 'tagi', 'opis', @id OUTPUT, @res OUTPUT;
--select @res as result, @id as ID



declare @files FileNameListUdt;
declare @files1 FileNameListUdt;

--insert into @files values ('2011-03-04-B0005-S02-T04.53875336.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T04.56339527.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T04.59461898.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T04.60981847.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T04.amc') 
--insert into @files values ('2011-03-04-B0005-S02-T04.c3d') 
--insert into @files values ('2011-03-04-B0005-S02-T04.system') 
--insert into @files values ('2011-03-04-B0005-S02-T04.xcp') 
--insert into @files values ('2011-03-04-B0005-S02-T07.53875336.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T07.56339527.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T07.59461898.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T07.60981847.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T07.amc') 
--insert into @files values ('2011-03-04-B0005-S02-T07.c3d') 
--insert into @files values ('2011-03-04-B0005-S02-T07.system') 
--insert into @files values ('2011-03-04-B0005-S02-T07.xcp') 
--insert into @files values ('2011-03-04-B0005-S02-T10.53875336.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T10.56339527.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T10.59461898.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T10.60981847.avi') 
--insert into @files values ('2011-03-04-B0005-S02-T10.amc') 
--insert into @files values ('2011-03-04-B0005-S02-T10.c3d') 
--insert into @files values ('2011-03-04-B0005-S02-T10.system') 
--insert into @files values ('2011-03-04-B0005-S02-T10.xcp') 
--insert into @files values ('2011-03-04-B0005-S02.asf') 
--insert into @files values ('2011-03-04-B0005-S02.mp') 
--insert into @files values ('2011-03-04-B0005-S02.vsk') 
--insert into @files values ('2011-03-04-B0005-S02.zip') 
--insert into @files values ('AcclaimPluginGait.mod') 
--insert into @files values ('PlugInGait.mkr') 

insert into @files values ('2011-02-24-B0001-S01-T01.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T01.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T01.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T01.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T01.amc') 
insert into @files values ('2011-02-24-B0001-S01-T01.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T01.Trial02.enf') 
insert into @files values ('2011-02-24-B0001-S01-T02.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T02.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T02.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T02.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T02.amc') 
insert into @files values ('2011-02-24-B0001-S01-T02.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T02.Trial03.enf') 
insert into @files values ('2011-02-24-B0001-S01-T03.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T03.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T03.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T03.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T03.amc') 
insert into @files values ('2011-02-24-B0001-S01-T03.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T03.Trial04.enf') 
insert into @files values ('2011-02-24-B0001-S01-T04.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T04.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T04.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T04.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T04.amc') 
insert into @files values ('2011-02-24-B0001-S01-T04.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T04.Trial05.enf') 
insert into @files values ('2011-02-24-B0001-S01-T05.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T05.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T05.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T05.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T05.amc') 
insert into @files values ('2011-02-24-B0001-S01-T05.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T05.Trial06.enf') 
insert into @files values ('2011-02-24-B0001-S01-T06.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T06.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T06.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T06.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T06.amc') 
insert into @files values ('2011-02-24-B0001-S01-T06.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T06.Trial07.enf') 
insert into @files values ('2011-02-24-B0001-S01-T07.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T07.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T07.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T07.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T07.amc') 
insert into @files values ('2011-02-24-B0001-S01-T07.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T07.Trial08.enf') 
insert into @files values ('2011-02-24-B0001-S01-T08.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T08.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T08.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T08.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T08.amc') 
insert into @files values ('2011-02-24-B0001-S01-T08.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T08.Trial09.enf') 
insert into @files values ('2011-02-24-B0001-S01-T09.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T09.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T09.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T09.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T09.amc') 
insert into @files values ('2011-02-24-B0001-S01-T09.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T09.Trial10.enf') 
insert into @files values ('2011-02-24-B0001-S01-T10.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T10.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T10.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T10.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T10.amc') 
insert into @files values ('2011-02-24-B0001-S01-T10.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T10.Trial11.enf') 
insert into @files values ('2011-02-24-B0001-S01-T11.53875336.avi') 
insert into @files values ('2011-02-24-B0001-S01-T11.56339527.avi') 
insert into @files values ('2011-02-24-B0001-S01-T11.59461898.avi') 
insert into @files values ('2011-02-24-B0001-S01-T11.60981847.avi') 
insert into @files values ('2011-02-24-B0001-S01-T11.amc') 
insert into @files values ('2011-02-24-B0001-S01-T11.c3d') 
insert into @files values ('2011-02-24-B0001-S01-T11.Trial12.enf') 
insert into @files values ('2011-02-24-B0001-S01.asf') 
insert into @files values ('2011-02-24-B0001-S01.mp') 
insert into @files values ('2011-02-24-B0001-S01.Session 1.enf') 
insert into @files values ('2011-02-24-B0001-S01.vsk') 
insert into @files values ('2011-02-24-B0001-S01.zip') 


insert into @files1 select Name from @files where ( CHARINDEX ('.avi', Name )>0 or CHARINDEX ('.amc', Name )>0 or CHARINDEX ('.asf', Name )>0 or CHARINDEX ('.c3d', Name )>0 or CHARINDEX ('.zip', Name )>0 or CHARINDEX ('.mp', Name )>0 or CHARINDEX ('.vsk', Name )>0 or CHARINDEX ('.zip', Name )>0 )

-- exec create_session_from_file_list 'habela', @files1, @res OUTPUT

exec validate_file_list_xml @files1

select @res





use Motion;
/* Definicje atrybutow statycznych */

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'trial');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'TrialID', 'integer', 0, 'ID', NULL) 
-- Czy klucze obce powinnismy uwzgledniac?
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'SessionID', 'integer', 0, 'ID', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'TrialName', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'TrialDescription', 'string', 0, 'longString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='trial'), 'Duration', 'int', 0, 'nonNegativeInteger', 'second') 


insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('_static', 'performer');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer'), 'PerformerID', 'int', 0, 'ID', NULL) 
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer'), 'FirstName', 'string', 0, 'shortString', NULL) 
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='performer'), 'LastName', 'string', 0, 'shortString', NULL) 

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
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'MotionKind', 'string', 1, 'shortString', NULL) -- ZMIENIONO
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'SessionDate', 'string', 0, 'date', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'SessionDescription', 'string', 0, 'longString', NULL) 



insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'SessionName', 'string', 0, 'shortString', NULL) 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = '_static' and Opisywana_encja='session'), 'Tags', 'string', 0, 'shortString', NULL) 
go

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

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('General_trial_attributes', 'trial');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_trial_attributes' and Opisywana_encja='trial'), 'SkeletonFile', 'file', 0, 'file', NULL) 
go


/* Uzytkownicy */



 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'habela', 'Piotr', 'Habela')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'kaczmarski', 'Krzysztof', 'Kaczmarski')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'wiktor', 'Wiktor', 'Filipowicz')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'dpisko', 'Dawid', 'Pisko')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'mtabaka', 'Magdalena', 'Tabaka')
go
 insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'kulbacki', 'Marek', 'Kulbacki')
go


insert into Laboratorium (Nazwa) values ('PJWSTK')
go

select * from Konfiguracja_performera

-- zakresy ruchowe (run, jump, hop, sit, trot, dance, ...) -> IdRodzajRuchu

insert into Rodzaj_ruchu ( Nazwa ) values ('walk') 
go
insert into Rodzaj_ruchu ( Nazwa ) values ('run')
go
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

-- select * from Grupa_atrybutow

-- delete from Grupa_atrybutow where Nazwa != '_static' and Opisywana_encja = 'performer_conf'

insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('Anthropometric', 'performer_conf');
go

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'BodyMass', 'integer', 0, 'nonNegativeDecimal', 'kg') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'Height', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'InterAsisDistance', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftLegLength', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightLegLenght', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftKneeWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightKneeWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftAnkleWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightAnkleWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftCircuitThigh', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightCircuitThight', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftCircuitShank', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightCircuitShank', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftShoulderOffset', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightShoulderOffset', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftElbowWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightElbowWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftWristWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightWristWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftWristThickness', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightWristThickness', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftHandWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightHandWidth', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'LeftHandThickness', 'integer', 0, 'nonNegativeInteger', 'mm') 

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric' and Opisywana_encja='performer_conf'), 'RightHandThickness', 'integer', 0, 'nonNegativeInteger', 'mm') 


select Nazwa from Sesja

select IdPerformer from Performer


-- PONIZSZE: TYLKO NA SERWER TESTOWY! --


insert into Uzytkownik (Login, Imie, Nazwisko) values ( 'applet_user', 'Uzytkownik', 'Testowy')
go


insert into Grupa_sesji ( Nazwa ) values ('SesjeTestowe')
go
insert into Grupa_sesji ( Nazwa ) values ('SesjeFaktyczne')
go


declare @res as int;
declare @sid as int;
exec create_session 'habela',1, 'jump', '2010-10-10', '2010-10-10-B0005-S02', 'sample', 'desc', @sid OUTPUT, @res OUTPUT 
select @sid

select * from Sesja

/* Wybrane atrybuty generyczne */

--insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('General_performer_attributes', 'performer');
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Sex', 'string', 1, 'shortString', NULL)    
--insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
--values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'M') 
--insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
--values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'F') 

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'DateOfBirth', 'string', 0, 'date', NULL)    
--go

--insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'Medical_performer_data', 'performer' )
--go
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Medical_performer_data'), 'Diagnosis', 'string', 0, 'shortString', NULL)
--go

--insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'Anthropometric_data', 'performer_conf' )
--go
---- waga [kg]					// BodyMass
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'BodyMass', 'integer', 0, 'nonNegativeInteger', 'kg')    
--go
---- wzrost [mm]				// Height 
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'Height', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go


---- opis/notatki				// Notes
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Notes', 'string', 0, 'longString', NULL)    
--go

---- iloœæ markerów				// MarkerCount
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'MarkerCount', 'integer', 0, 'nonNegativeInteger', NULL)    
--go
---- d³ugoœæ lewej nogi [mm]	// LeftLegLength
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftLegLength', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go 
---- d³ugoœæ prawej nogi [mm]	// RightLegLength 
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightLegLength', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go
---- szerokoœæ nasad kolanowych dla lewej nogi [mm]	// LeftKneeWidth
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftKneeWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go
---- szerokoœæ nasad kolanowych dla prawej nogi [mm]	// RightKneeWidth
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightKneeWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go
---- odleg³oœæ kostki bocznej od przyœrodkowej dla lewej nogi [mm]	// LeftAnkleWidth
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftAnkleWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go
---- odleg³oœæ kostki bocznej od przyœrodkowej dla prawej nogi [mm]  // RightAnkleWidth
--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy, Podtyp_danych, Jednostka)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightAnkleWidth', 'integer', 0, 'nonNegativeInteger', 'mm')    
--go
insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'General_session_attributes', 'session' )
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