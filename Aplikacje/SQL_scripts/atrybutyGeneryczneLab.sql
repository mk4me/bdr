use Motion
go
update Atrybut set Nazwa = 'DateOfBirth' where Nazwa = 'date_of_birth'
go

--drop INDEX XIF46Wartosc_wyliczeniowa ON Wartosc_wyliczeniowa
--go

--drop table Wartosc_wyliczeniowa
--go


-- CREATE TABLE Wartosc_wyliczeniowa (
--        IdWartosc_wyliczeniowa int IDENTITY,
--        IdAtrybut            int NOT NULL,
--        Wartosc_wyliczeniowa varchar(100) NOT NULL
-- )
--go
-- ALTER TABLE Wartosc_wyliczeniowa
--        ADD PRIMARY KEY (IdWartosc_wyliczeniowa)
--go
--CREATE INDEX XIF46Wartosc_wyliczeniowa ON Wartosc_wyliczeniowa
-- (
--        IdAtrybut
-- )
--go
-- ALTER TABLE Wartosc_wyliczeniowa
--        ADD FOREIGN KEY (IdAtrybut)
--                              REFERENCES Atrybut
--go

-- 5. Nasze atrybuty:
  -- a) dla aktora

-- -data urodzenia [rrrr-mm-dd]	// DateOfBirth
-- p³eæ [kobieta/mê¿czyzna]	// sex

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Sex', 'string')    
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'M') 
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'F') 
go

-- Anthropometric data

insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'Anthropometric_data', 'performer' )
go
-- waga [kg]					// BodyMass
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'BodyMass', 'integer')    
go
-- wzrost [mm]				// Height 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'Height', 'integer')    
go
-- opis/notatki				// Notes
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Notes', 'string')    
go

-- iloœæ markerów				// MarkerCount
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'MarkerCount', 'integer')    
go
-- d³ugoœæ lewej nogi [mm]	// LeftLegLength
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftLegLength', 'integer')    
go 
-- d³ugoœæ prawej nogi [mm]	// RightLegLength 
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightLegLength', 'integer')    
go
-- szerokoœæ nasad kolanowych dla lewej nogi [mm]	// LeftKneeWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftKneeWidth', 'integer')    
go
-- szerokoœæ nasad kolanowych dla prawej nogi [mm]	// RightKneeWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightKneeWidth', 'integer')    
go
-- odleg³oœæ kostki bocznej od przyœrodkowej dla lewej nogi [mm]	// LeftAnkleWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'LeftAnkleWidth', 'integer')    
go
-- odleg³oœæ kostki bocznej od przyœrodkowej dla prawej nogi [mm]  // RightAnkleWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'RightAnkleWidth', 'integer')    
go
-- nazwa pliku kalibracyjnego dla aktora // CalibrationFileName
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Anthropometric_data'), 'CalibrationFileName', 'string')    
go


-- b) Dla sesji:
-- nazwa sesji  // SessionName
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'SessionName', 'string')    
go
-- iloœæ aktorów // PerformerCount
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'PerformerCount', 'integer')    
go
-- czas trwania (frames, timecode) // Duration ???
--  FTP // ???
-- opis (jest)
-- notatki // Notes
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'Notes', 'string')    
go
-- zakresy ruchowe (run, jump, hop, sit, trot, dance, ...) -> IdRodzajRuchu
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

-- typ sesji: [kalibracyjna/nagraniowa] // SessionType ( calibration | recording ) Purpose -> SessionType
update Atrybut set Nazwa = 'SessionType' where Nazwa = 'purpose'
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'SessionType'), 'calibration') 
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'SessionType'), 'recording') 
go
delete from Wartosc_wyliczeniowa where Wartosc_wyliczeniowa = 'entertainment'
go
delete from Wartosc_wyliczeniowa where Wartosc_wyliczeniowa = 'diagnosis'
go
-- temperatura otoczenia
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'Temperature', 'integer')    
go


-- c) Inne:
-- Grupa atrybutow dla plikow: Video

insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'Video_file_properties', 'file' )
go

-- u¿yty kodek video	// Video -> Codec
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'Codec', 'string')    
go

-- próbkowanie bitowe [Kbps] // Video -> BitRate
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'BitRate', 'integer')    
go
-- szerokoœæ obrazu [px] // Video -> ImageWidth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'ImageWidth', 'integer')    
go
-- wysokoœæ obrazu [px] // Video -> ImageHeight
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'ImageHeight', 'integer')    
go
-- g³êbia obrazu [bits]	// Video -> ImageDepth
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'ImageDepth', 'integer')    
go
-- proporcje obrazu [4:3, 16:9] Video -> PictureFormat
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'PictureFormat', 'string')    
go
-- liczba klatek na sekundê [ftp] Video -> FPS
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'FPS', 'integer')    
go
-- tryb: rgb/B&W	// Video -> ColorMode ( RGB | B&W )
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'ColorMode', 'string')    
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'ColorMode'), 'RGB') 
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'ColorMode'), 'B&W') 
go
-- dostêpne widoki z kamer Video -> CameraView [front/back/left/right]
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'Video_file_properties'), 'CameraView', 'string')    
go

insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'CameraView'), 'front') 
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'CameraView'), 'back') 
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'CameraView'), 'left') 
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'CameraView'), 'right') 
go

update dbo.Atrybut set Wyliczeniowy = 1 where exists( select * from Wartosc_wyliczeniowa ww where ww.IdAtrybut = dbo.Atrybut.IdAtrybut) 
/* Zdefiniowane podtypy atrybutów:
integer:	int
		decimal
		nonNegativeInteger
		nonNegativeDecimal
string:		dateTime
		date
		shortString
		longString
		TIMECODE */

update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'marker_set'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'number_of_trials'
go
update dbo.Atrybut set Podtyp_danych = 'date' where Nazwa = 'DateOfBirth'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'diagnosis'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'file_type'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'SessionType'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'relevance'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'Sex'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'BodyMass'
go
update dbo.Atrybut set Jednostka = 'kg' where Nazwa = 'BodyMass'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'Height'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'Height'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'MarkerCount'
go

update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'LeftLegLength'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'LeftLegLength'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'RightLegLength'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'RightLegLength'
go

update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'LeftKneeWidth'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'LeftKneeWidth'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'RightKneeWidth'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'RightKneeWidth'
go

update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'LeftAnkleWidth'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'LeftAnkleWidth'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'RightAnkleWidth'
go
update dbo.Atrybut set Jednostka = 'mm' where Nazwa = 'RightAnkleWidth'
go

update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'CalibrationFileName'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'SessionName'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'PerformerCount'
go
update dbo.Atrybut set Podtyp_danych = 'longString' where Nazwa = 'Notes'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'Temperature'
go
update dbo.Atrybut set Jednostka = 'C' where Nazwa = 'Temperature'
go

update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'Codec'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'BitRate'
go
update dbo.Atrybut set Jednostka = 'Kbps' where Nazwa = 'BitRate'
go

update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'ImageWidth'
go
update dbo.Atrybut set Jednostka = 'px' where Nazwa = 'ImageWidth'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'ImageHeight'
go
update dbo.Atrybut set Jednostka = 'px' where Nazwa = 'ImageHeight'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'ImageDepth'
go
update dbo.Atrybut set Jednostka = 'bits' where Nazwa = 'ImageDepth'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'PictureFormat'
go
update dbo.Atrybut set Podtyp_danych = 'nonNegativeInteger' where Nazwa = 'FPS'
go
update dbo.Atrybut set Jednostka = 'fps' where Nazwa = 'FPS'
go

update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'ColorMode'
go
update dbo.Atrybut set Podtyp_danych = 'shortString' where Nazwa = 'CameraView'
go

select * from Atrybut order by Nazwa