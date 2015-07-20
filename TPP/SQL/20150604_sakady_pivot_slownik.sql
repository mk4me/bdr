use TPP;
go


insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	66,	'5 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	72,	'6 lat po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	78,	'6 i pół roku po DBS' );
insert into SlownikInt ( Tabela, Atrybut, Klucz, Definicja ) values ( 'Wizyta',	'RodzajWizyty',	84,	'7 lat po DBS' );



sp_rename 'Badanie.Latencymeter', 'Saccades', 'Column';
go
sp_rename 'Badanie.LatencymeterLatencyLEFT', 'SaccadesLatencyMeanLEFT', 'Column';
go
sp_rename 'Badanie.LatencymeterLatencyRIGHT', 'SaccadesLatencyMeanRIGHT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterDurationLEFT', 'SaccadesDurationLEFT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterDurationRIGHT', 'SaccadesDurationRIGHT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterAmplitudeLEFT', 'SaccadesAmplitudeLEFT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterAmplitudeRIGHT', 'SaccadesAmplitudeRIGHT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterPeakVelocityLEFT', 'SaccadesPeakVelocityLEFT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterPeakVelocityRIGHT', 'SaccadesPeakVelocityRIGHT', 'Column'; 
go
sp_rename 'Badanie.LatencymeterLatencyALL', 'SaccadesLatencyMeanALL', 'Column'; 
go
sp_rename 'Badanie.LatencymeterDurationALL', 'SaccadesDurationALL', 'Column'; 
go
sp_rename 'Badanie.LatencymeterAmplitudeALL', 'SaccadesAmplitudeALL', 'Column'; 
go
sp_rename 'Badanie.LatencymeterPeakVelocityALL', 'SaccadesPeakVelocityALL', 'Column'; 
go




create table Kolumna (
	IdKolumna	int	identity,
	PozycjaDomyslna int,
	Encja	varchar(30),
	Nazwa	varchar(50)
)
go


CREATE INDEX X1Kolumna ON Kolumna (
     Nazwa
 )
 go

CREATE INDEX X2Kolumna ON Kolumna (
     Encja, Nazwa
 )
 go



insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (1, 'P', 'NumerPacjenta');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (2, 'P', 'RokUrodzenia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (3, 'P', 'MiesiacUrodzenia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (4, 'P', 'Plec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (5, 'P', 'Lokalizacja');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (6, 'P', 'LiczbaElektrod');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (7, 'P', 'NazwaGrupy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (8, 'W', 'RodzajWizyty');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (9, 'W', 'DataPrzyjecia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (10, 'W', 'DataWypisu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (11, 'W', 'MasaCiala');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (12, 'W', 'DataOperacji');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (13, 'W', 'Wyksztalcenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (14, 'W', 'Rodzinnosc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (15, 'W', 'RokZachorowania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (16, 'W', 'CzasTrwaniaChoroby');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (17, 'W', 'PierwszyObjaw');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (18, 'W', 'Drzenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (19, 'W', 'Sztywnosc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (20, 'W', 'Spowolnienie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (21, 'W', 'ObjawyInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (22, 'W', 'ObjawyInneJakie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (23, 'W', 'CzasOdPoczObjDoWlLDopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (24, 'W', 'DyskinezyObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (25, 'W', 'DyskinezyOdLat');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (26, 'W', 'FluktuacjeObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (27, 'W', 'FluktuacjeOdLat');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (28, 'W', 'CzasDyskinez');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (29, 'W', 'CzasOFF');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (30, 'W', 'PoprawaPoLDopie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (31, 'W', 'PrzebyteLeczenieOperacyjnePD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (32, 'W', 'Papierosy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (33, 'W', 'Kawa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (34, 'W', 'ZielonaHerbata');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (35, 'W', 'Alkohol');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (36, 'W', 'ZabiegowWZnieczOgPrzedRozpoznaniemPD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (37, 'W', 'Zamieszkanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (38, 'W', 'NarazeniaNaToks');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (39, 'W', 'Uwagi');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (40, 'W', 'Nadcisnienie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (41, 'W', 'BlokeryKanWapn');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (42, 'W', 'DominujacyObjawObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (43, 'W', 'DominujacyObjawUwagi');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (44, 'W', 'ObjawyAutonomiczne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (45, 'W', 'RLS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (46, 'W', 'ObjawyPsychotyczne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (47, 'W', 'Depresja');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (48, 'W', 'Otepienie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (49, 'W', 'Dyzartria');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (50, 'W', 'DysfagiaObjaw');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (51, 'W', 'RBD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (52, 'W', 'ZaburzenieRuchomosciGalekOcznych');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (53, 'W', 'Apraksja');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (54, 'W', 'TestKlaskania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (55, 'W', 'ZaburzeniaWechowe');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (56, 'W', 'Ldopa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (57, 'W', 'LDopaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (58, 'W', 'Agonista');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (59, 'W', 'AgonistaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (60, 'W', 'Amantadyna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (61, 'W', 'AmantadynaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (62, 'W', 'MAOBinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (63, 'W', 'MAOBinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (64, 'W', 'COMTinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (65, 'W', 'COMTinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (66, 'W', 'Cholinolityk');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (67, 'W', 'CholinolitykObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (68, 'W', 'LekiInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (69, 'W', 'LekiInneJakie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (70, 'W', 'L_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (71, 'W', 'L_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (72, 'W', 'L_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (73, 'W', 'L_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (74, 'W', 'R_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (75, 'W', 'R_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (76, 'W', 'R_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (77, 'W', 'R_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (78, 'W', 'Wypis_Ldopa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (79, 'W', 'Wypis_LDopaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (80, 'W', 'Wypis_Agonista');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (81, 'W', 'Wypis_AgonistaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (82, 'W', 'Wypis_Amantadyna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (83, 'W', 'Wypis_AmantadynaObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (84, 'W', 'Wypis_MAOBinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (85, 'W', 'Wypis_MAOBinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (86, 'W', 'Wypis_COMTinh');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (87, 'W', 'Wypis_COMTinhObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (88, 'W', 'Wypis_Cholinolityk');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (89, 'W', 'Wypis_CholinolitykObecnie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (90, 'W', 'Wypis_LekiInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (91, 'W', 'Wypis_LekiInneJakie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (92, 'W', 'Wypis_L_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (93, 'W', 'Wypis_L_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (94, 'W', 'Wypis_L_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (95, 'W', 'Wypis_L_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (96, 'W', 'Wypis_R_STIMOpis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (97, 'W', 'Wypis_R_STIMAmplitude');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (98, 'W', 'Wypis_R_STIMDuration');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (99, 'W', 'Wypis_R_STIMFrequency');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (100, 'W', 'WydzielanieSliny');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (101, 'W', 'Dysfagia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (102, 'W', 'DysfagiaCzestotliwosc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (103, 'W', 'Nudnosci');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (104, 'W', 'Zaparcia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (105, 'W', 'TrudnosciWOddawaniuMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (106, 'W', 'PotrzebaNaglegoOddaniaMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (107, 'W', 'NiekompletneOproznieniePecherza');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (108, 'W', 'SlabyStrumienMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (109, 'W', 'CzestotliwowscOddawaniaMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (110, 'W', 'Nykturia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (111, 'W', 'NiekontrolowaneOddawanieMoczu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (112, 'W', 'Omdlenia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (113, 'W', 'ZaburzeniaRytmuSerca');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (114, 'W', 'ProbaPionizacyjna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (115, 'W', 'WzrostPodtliwosciTwarzKark');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (116, 'W', 'WzrostPotliwosciRamionaDlonie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (117, 'W', 'WzrostPotliwosciBrzuchPlecy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (118, 'W', 'WzrostPotliwosciKonczynyDolneStopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (119, 'W', 'SpadekPodtliwosciTwarzKark');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (120, 'W', 'SpadekPotliwosciRamionaDlonie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (121, 'W', 'SpadekPotliwosciBrzuchPlecy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (122, 'W', 'SpadekPotliwosciKonczynyDolneStopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (123, 'W', 'NietolerancjaWysokichTemp');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (124, 'W', 'NietolerancjaNiskichTemp');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (125, 'W', 'Lojotok');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (126, 'W', 'SpadekLibido');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (127, 'W', 'KlopotyOsiagnieciaErekcji');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (128, 'W', 'KlopotyUtrzymaniaErekcji');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (129, 'B', 'DBS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (130, 'B', 'BMT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (131, 'B', 'UPDRS_I');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (132, 'B', 'UPDRS_II');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (133, 'B', 'UPDRS_18');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (134, 'B', 'UPDRS_19');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (135, 'B', 'UPDRS_20_FaceLipsChin');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (136, 'B', 'UPDRS_20_RHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (137, 'B', 'UPDRS_20_LHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (138, 'B', 'UPDRS_20_RFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (139, 'B', 'UPDRS_20_LFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (140, 'B', 'UPDRS_21_RHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (141, 'B', 'UPDRS_21_LHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (142, 'B', 'UPDRS_22_Neck');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (143, 'B', 'UPDRS_22_RHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (144, 'B', 'UPDRS_22_LHand');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (145, 'B', 'UPDRS_22_RFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (146, 'B', 'UPDRS_22_LFoot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (147, 'B', 'UPDRS_23_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (148, 'B', 'UPDRS_23_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (149, 'B', 'UPDRS_24_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (150, 'B', 'UPDRS_24_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (151, 'B', 'UPDRS_25_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (152, 'B', 'UPDRS_25_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (153, 'B', 'UPDRS_26_R');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (154, 'B', 'UPDRS_26_L');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (155, 'B', 'UPDRS_27');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (156, 'B', 'UPDRS_28');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (157, 'B', 'UPDRS_29');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (158, 'B', 'UPDRS_30');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (159, 'B', 'UPDRS_31');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (160, 'B', 'UPDRS_III');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (161, 'B', 'UPDRS_IV');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (162, 'B', 'UPDRS_TOTAL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (163, 'B', 'HYscale');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (164, 'B', 'SchwabEnglandScale');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (165, 'B', 'JazzNovo');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (166, 'B', 'Wideookulograf');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (167, 'B', 'Saccades');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (168, 'B', 'SaccadesLatencyMeanLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (169, 'B', 'SaccadesLatencyMeanRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (170, 'B', 'SaccadesDurationLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (171, 'B', 'SaccadesDurationRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (172, 'B', 'SaccadesAmplitudeLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (173, 'B', 'SaccadesAmplitudeRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (174, 'B', 'SaccadesPeakVelocityLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (175, 'B', 'SaccadesPeakVelocityRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (176, 'B', 'SaccadesLatencyMeanALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (177, 'B', 'SaccadesDurationALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (178, 'B', 'SaccadesAmplitudeALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (179, 'B', 'SaccadesPeakVelocityALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (180, 'B', 'Antisaccades');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (181, 'B', 'AntisaccadesPercentOfCorrectLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (182, 'B', 'AntisaccadesPercentOfCorrectRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (183, 'B', 'AntisaccadesLatencyMeanLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (184, 'B', 'AntisaccadesLatencyMeanRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (185, 'B', 'AntisaccadesDurationLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (186, 'B', 'AntisaccadesDurationRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (187, 'B', 'AntisaccadesAmplitudeLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (188, 'B', 'AntisaccadesAmplitudeRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (189, 'B', 'AntisaccadesPeakVelocityLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (190, 'B', 'AntisaccadesPeakVelocityRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (191, 'B', 'AntisaccadesPercentOfCorrectALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (192, 'B', 'AntisaccadesLatencyMeanALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (193, 'B', 'AntisaccadesDurationALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (194, 'B', 'AntisaccadesAmplitudeALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (195, 'B', 'AntisaccadesPeakVelocityALL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (196, 'B', 'POM_Gain_SlowSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (197, 'B', 'POM_StDev_SlowSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (198, 'B', 'POM_Gain_MediumSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (199, 'B', 'POM_StDev_MediumSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (200, 'B', 'POM_Gain_FastSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (201, 'B', 'POM_StDev_FastSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (202, 'B', 'POM_Accuracy_SlowSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (203, 'B', 'POM_Accuracy_MediumSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (204, 'B', 'POM_Accuracy_FastSinus');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (205, 'B', 'Tremorometria');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (206, 'B', 'TremorometriaLEFT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (207, 'B', 'TremorometriaRIGHT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (208, 'B', 'TremorometriaLEFT_0_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (209, 'B', 'TremorometriaLEFT_1_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (210, 'B', 'TremorometriaLEFT_2_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (211, 'B', 'TremorometriaLEFT_3_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (212, 'B', 'TremorometriaLEFT_4_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (213, 'B', 'TremorometriaLEFT_5_6');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (214, 'B', 'TremorometriaLEFT_6_7');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (215, 'B', 'TremorometriaLEFT_7_8');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (216, 'B', 'TremorometriaLEFT_8_9');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (217, 'B', 'TremorometriaLEFT_9_10');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (218, 'B', 'TremorometriaLEFT_23_24');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (219, 'B', 'TremorometriaRIGHT_0_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (220, 'B', 'TremorometriaRIGHT_1_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (221, 'B', 'TremorometriaRIGHT_2_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (222, 'B', 'TremorometriaRIGHT_3_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (223, 'B', 'TremorometriaRIGHT_4_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (224, 'B', 'TremorometriaRIGHT_5_6');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (225, 'B', 'TremorometriaRIGHT_6_7');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (226, 'B', 'TremorometriaRIGHT_7_8');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (227, 'B', 'TremorometriaRIGHT_8_9');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (228, 'B', 'TremorometriaRIGHT_9_10');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (229, 'B', 'TremorometriaRIGHT_23_24');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (230, 'B', 'TestSchodkowy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (231, 'B', 'TestSchodkowyWDol');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (232, 'B', 'TestSchodkowyWGore');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (233, 'B', 'TestMarszu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (234, 'B', 'TestMarszuCzas1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (235, 'B', 'TestMarszuCzas2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (236, 'B', 'Posturografia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (237, 'B', 'MotionAnalysis');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (238, 'B', 'Otwarte_Srednia_C_o_P_X');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (239, 'B', 'Otwarte_Srednia_C_o_P_Y');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (240, 'B', 'Otwarte_Srednia_P_T_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (241, 'B', 'Otwarte_Srednia_P_B_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (242, 'B', 'Otwarte_Perimeter_mm');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (243, 'B', 'Otwarte_PoleElipsy_mm2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (244, 'B', 'Zamkniete_Srednia_C_o_P_X');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (245, 'B', 'Zamkniete_Srednia_C_o_P_Y');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (246, 'B', 'Zamkniete_Srednia_P_T_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (247, 'B', 'Zamkniete_Srednia_P_B_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (248, 'B', 'Zamkniete_Perimeter_mm');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (249, 'B', 'Zamkniete_PoleElipsy_mm2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (250, 'B', 'WspolczynnikPerymetru_E_C_E_O_obie_stopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (251, 'B', 'WspolczynnikPowierzchni_E_C_E_O_obie_stopy');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (252, 'B', 'Biofeedback_Srednia_C_o_P_X');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (253, 'B', 'Biofeedback_Srednia_C_o_P_Y');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (254, 'B', 'Biofeedback_Srednia_P_T_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (255, 'B', 'Biofeedback_Srednia_P_B_Predkosc_mm_sec');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (256, 'B', 'Biofeedback_Perimeter_mm');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (257, 'B', 'Biofeedback_PoleElipsy_mm2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (258, 'B', 'UpAndGo');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (259, 'B', 'UpAndGoLiczby');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (260, 'B', 'UpAndGoKubekPrawa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (261, 'B', 'UpAndGoKubekLewa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (262, 'B', 'TST');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (263, 'B', 'TandemPivot');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (264, 'B', 'WTT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (265, 'B', 'WprowadzilasWariantZapisal');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (266, 'B', 'ZmodyfikowalasWariantModyfikowal');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (267, 'B', 'OstatniaZmianaasOstatniaEdycjaWariantu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (268, 'W', 'PDQ39');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (269, 'W', 'AIMS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (270, 'W', 'Epworth');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (271, 'W', 'CGI');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (272, 'W', 'FSS');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (273, 'W', 'TestZegara');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (274, 'W', 'MMSE');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (275, 'W', 'CLOX1_Rysunek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (276, 'W', 'CLOX2_Kopia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (277, 'W', 'AVLT_proba_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (278, 'W', 'AVLT_proba_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (279, 'W', 'AVLT_proba_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (280, 'W', 'AVLT_proba_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (281, 'W', 'AVLT_proba_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (282, 'W', 'AVLT_Suma');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (283, 'W', 'AVLT_Srednia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (284, 'W', 'AVLT_KrotkieOdroczenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (285, 'W', 'AVLT_Odroczony20min');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (286, 'W', 'AVLT_Rozpoznawanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (287, 'W', 'AVLT_BledyRozpoznania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (288, 'W', 'TestAVLTSrednia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (289, 'W', 'TestAVLTOdroczony');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (290, 'W', 'TestAVLTPo20min');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (291, 'W', 'TestAVLTRozpoznawanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (292, 'W', 'CVLT_proba_1');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (293, 'W', 'CVLT_proba_2');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (294, 'W', 'CVLT_proba_3');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (295, 'W', 'CVLT_proba_4');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (296, 'W', 'CVLT_proba_5');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (297, 'W', 'CVLT_Suma');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (298, 'W', 'CVLT_OSKO_krotkie_odroczenie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (299, 'W', 'CVLT_OPKO_krotkie_odroczenie_i_pomoc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (300, 'W', 'CVLT_OSDO_po20min');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (301, 'W', 'CVLT_OPDO_po20min_i_pomoc');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (302, 'W', 'CVLT_perseweracje');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (303, 'W', 'CVLT_WtraceniaOdtwarzanieSwobodne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (304, 'W', 'CVLT_wtraceniaOdtwarzanieZPomoca');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (305, 'W', 'CVLT_Rozpoznawanie');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (306, 'W', 'CVLT_BledyRozpoznania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (307, 'W', 'Benton_JOL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (308, 'W', 'WAIS_R_Wiadomosci');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (309, 'W', 'WAIS_R_PowtarzanieCyfr');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (310, 'W', 'WAIS_R_Podobienstwa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (311, 'W', 'BostonskiTestNazywaniaBMT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (312, 'W', 'BMT_SredniCzasReakcji_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (313, 'W', 'SkalaDepresjiBecka');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (314, 'W', 'SkalaDepresjiBeckaII');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (315, 'W', 'TestFluencjiK');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (316, 'W', 'TestFluencjiP');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (317, 'W', 'TestFluencjiZwierzeta');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (318, 'W', 'TestFluencjiOwoceWarzywa');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (319, 'W', 'TestFluencjiOstre');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (320, 'W', 'TestLaczeniaPunktowA');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (321, 'W', 'TestLaczeniaPunktowB');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (322, 'W', 'ToL_SumaRuchow');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (323, 'W', 'ToL_LiczbaPrawidlowych');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (324, 'W', 'ToL_CzasInicjowania_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (325, 'W', 'ToL_CzasWykonania_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (326, 'W', 'ToL_CzasCalkowity_sek');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (327, 'W', 'ToL_CzasPrzekroczony');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (328, 'W', 'ToL_LiczbaPrzekroczenZasad');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (329, 'W', 'ToL_ReakcjeUkierunkowane');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (330, 'W', 'InnePsychologiczne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (331, 'W', 'OpisBadania');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (332, 'W', 'Wnioski');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (333, 'W', 'Holter');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (334, 'W', 'BadanieWechu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (335, 'W', 'WynikWechu');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (336, 'W', 'LimitDysfagii');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (337, 'W', 'pH_metriaPrzełyku');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (338, 'W', 'SPECT');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (339, 'W', 'SPECTWyniki');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (340, 'W', 'MRI');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (341, 'W', 'MRIwynik');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (342, 'W', 'USGsrodmozgowia');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (343, 'W', 'USGWynik');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (344, 'W', 'Genetyka');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (345, 'W', 'GenetykaWynik');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (346, 'W', 'Surowica');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (347, 'W', 'SurowicaPozostało');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (348, 'W', 'Ferrytyna');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (349, 'W', 'CRP');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (350, 'W', 'NTproCNP');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (351, 'W', 'URCA');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (352, 'W', 'WitD');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (353, 'W', 'CHOL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (354, 'W', 'TGI');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (355, 'W', 'HDL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (356, 'W', 'LDL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (357, 'W', 'olLDL');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (358, 'W', 'LaboratoryjneInne');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (359, 'W', 'WprowadzilasWizyteWprowadzil');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (360, 'W', 'ZmodyfikowalasWizyteEdytowal');
insert into Kolumna ( PozycjaDomyslna, Encja, Nazwa) values (361, 'W', 'OstatniaZmianaasOstatniaModyfikacja');




alter table Pacjent add
	ZakonczenieUdzialu varchar(255)
go

alter table Badanie add
	Antisaccades bit,
	AntisaccadesPercentOfCorrectLEFT decimal(8,5),
	AntisaccadesPercentOfCorrectRIGHT decimal(8,5),
	AntisaccadesLatencyMeanLEFT decimal(8,5),
	AntisaccadesLatencyMeanRIGHT decimal(8,5),
	AntisaccadesDurationLEFT decimal(8,5),
	AntisaccadesDurationRIGHT decimal(8,5),
	AntisaccadesAmplitudeLEFT decimal(8,5),
	AntisaccadesAmplitudeRIGHT decimal(8,5),
	AntisaccadesPeakVelocityLEFT decimal(8,5),
	AntisaccadesPeakVelocityRIGHT decimal(8,5),
	AntisaccadesPercentOfCorrectALL decimal(8,5),
	AntisaccadesLatencyMeanALL decimal(8,5),
	AntisaccadesDurationALL decimal(8,5),
	AntisaccadesAmplitudeALL decimal(8,5),
	AntisaccadesPeakVelocityALL decimal(8,5),
	POM_Gain_SlowSinus decimal(8,5),
	POM_StDev_SlowSinus decimal(8,5),
	POM_Gain_MediumSinus decimal(8,5),
	POM_StDev_MediumSinus decimal(8,5),
	POM_Gain_FastSinus decimal(8,5),
	POM_StDev_FastSinus decimal(8,5),
	POM_Accuracy_SlowSinus decimal(8,5),
	POM_Accuracy_MediumSinus decimal(8,5),
	POM_Accuracy_FastSinus decimal(8,5)
go




-- last rev. 2015-06-05
-- @result codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message
alter procedure update_patient  (	@NumerPacjenta	varchar(20), @NazwaGrupy varchar(3), @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @ZakonczenieUdzialu varchar(255), @allow_update_existing bit, @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @update bit;
	set @result = 0;
	set @update = 0;
	set @message = '';
	if(( select count(*) from Pacjent where NumerPacjenta = @NumerPacjenta )>0 )
	begin
		if ( @allow_update_existing = 0 )
		begin
			set @result = 1;
			return;
		end
		else set @update = 1;
	end;

	if( dbo.validate_input_varchar('Pacjent', 'NazwaGrupy', @NazwaGrupy) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Nazwa grupy - invalid enum value.';
		return;
	end;

	if( dbo.validate_ext_bit( @Plec) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Plec - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_year_month( @RokUrodzenia, @MiesiacUrodzenia) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'MiesiacUrodzenia / RokUrodzenia - invalid value.';
		return;
	end;

	if(@NazwaGrupy <> 'BMT')
	begin
		if( dbo.validate_input_varchar('Pacjent', 'Lokalizacja', @Lokalizacja) = 0 )
		begin
			set @result = 2;
			set @message = @message + 'Lokalizacja - invalid enum value.';
			return;
		end;
	end;

	if(@update = 0)
		insert into Pacjent (NumerPacjenta, NazwaGrupy, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod, ZakonczenieUdzialu )
					values (@NumerPacjenta, @NazwaGrupy, @RokUrodzenia, @MiesiacUrodzenia, @Plec, @Lokalizacja, @LiczbaElektrod, @ZakonczenieUdzialu	 );
	else
		update Pacjent
		set RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod, ZakonczenieUdzialu = @ZakonczenieUdzialu
		where NumerPacjenta = @NumerPacjenta;		
end;
go



-- last rev. 2015-06-05
-- @result codes: 0 = OK, 1 = patient exists while update existing not allowed, 2 = validation failed - see message, 3 = login user not found
alter procedure update_patient_l  (	@NumerPacjenta	varchar(20), @NazwaGrupy varchar(3),  @RokUrodzenia smallint, @MiesiacUrodzenia tinyint, @Plec tinyint, @Lokalizacja varchar(10), @LiczbaElektrod tinyint, @ZakonczenieUdzialu varchar(255), @allow_update_existing bit, @actor_login varchar(50), @result int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @update bit;
	set @result = 0;
	set @update = 0;
	declare @userid int;
	set @userid = 0;
	set @message = '';
	if(( select count(*) from Pacjent where NumerPacjenta = @NumerPacjenta )>0 )
	begin
		if ( @allow_update_existing = 0 )
		begin
			set @result = 1;
			return;
		end
		else set @update = 1;
	end;

	if( dbo.validate_input_varchar('Pacjent', 'NazwaGrupy', @NazwaGrupy) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Nazwa grupy - invalid enum value.';
		return;
	end;

	select @userid = dbo.identify_user(@actor_login);

	if ( @userid = 0 )
	begin
		set @result = 3;
		set @message = 'Unknown login '+ @actor_login ;
		return;
	end;

	if( dbo.validate_ext_bit( @Plec) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'Plec - invalid value. Allowed: 0, 1, 2.';
		return;
	end;

	if( dbo.validate_year_month( @RokUrodzenia, @MiesiacUrodzenia) = 0 )
	begin
		set @result = 2;
		set @message = @message + 'MiesiacUrodzenia / RokUrodzenia - invalid value.';
		return;
	end;

	if(@NazwaGrupy <> 'BMT')
	begin
		if( dbo.validate_input_varchar('Pacjent', 'Lokalizacja', @Lokalizacja) = 0 )
		begin
			set @result = 2;
			set @message = @message + 'Lokalizacja - invalid enum value.';
			return;
		end;
	end;

	if(@update = 0)
		insert into Pacjent (NumerPacjenta, NazwaGrupy, RokUrodzenia, MiesiacUrodzenia, Plec, Lokalizacja, LiczbaElektrod, ZakonczenieUdzialu, OstatniaZmiana, Wprowadzil, Zmodyfikowal )
					values (@NumerPacjenta, @NazwaGrupy, @RokUrodzenia, @MiesiacUrodzenia, @Plec, @Lokalizacja, @LiczbaElektrod, @ZakonczenieUdzialu, getdate(), @userid, @userid	 );
	else
		update Pacjent
		set RokUrodzenia = @RokUrodzenia, MiesiacUrodzenia = @MiesiacUrodzenia, Plec = @Plec, Lokalizacja = @Lokalizacja, 
		LiczbaElektrod = @LiczbaElektrod,
		ZakonczenieUdzialu = @ZakonczenieUdzialu,
		Zmodyfikowal = @userid,
		OstatniaZmiana = getdate()
		where NumerPacjenta = @NumerPacjenta;		
end;
go



-- @result codes: 0 = OK, 1 = variant already exists while run in no-update mode,  exist 2 = validation failed - see message, 3 = visit of this ID not found, 4 = user login unknown
alter procedure update_variant_examination_data_partA  (@IdWizyta int, @DBS tinyint, @BMT bit,
	@UPDRS_I	tinyint,
	@UPDRS_II	tinyint,
	@UPDRS_18	tinyint,
	@UPDRS_19 	tinyint,
	@UPDRS_20_FaceLipsChin	tinyint,
	@UPDRS_20_RHand	tinyint,
	@UPDRS_20_LHand	tinyint,
	@UPDRS_20_RFoot	tinyint,
	@UPDRS_20_LFoot	tinyint,
	@UPDRS_21_RHand	tinyint,
	@UPDRS_21_LHand	tinyint,
	@UPDRS_22_Neck	tinyint,
	@UPDRS_22_RHand	tinyint,
	@UPDRS_22_LHand	tinyint,
	@UPDRS_22_RFoot	tinyint,
	@UPDRS_22_LFoot	tinyint,
	@UPDRS_23_R	tinyint,
	@UPDRS_23_L	tinyint,
	@UPDRS_24_R	tinyint,
	@UPDRS_24_L	tinyint,
	@UPDRS_25_R	tinyint,
	@UPDRS_25_L	tinyint,
	@UPDRS_26_R	tinyint,
	@UPDRS_26_L	tinyint,
	@UPDRS_27	tinyint,
	@UPDRS_28	tinyint,
	@UPDRS_29	tinyint,
	@UPDRS_30	tinyint,
	@UPDRS_31	tinyint,
	@UPDRS_III	tinyint,
	@UPDRS_IV	tinyint,
	@UPDRS_TOTAL	tinyint,
	@HYscale	decimal(2,1),
	@SchwabEnglandScale	tinyint,
	@JazzNovo bit,
	@Wideookulograf bit,
	@Saccades bit,
	@SaccadesDurationLEFT decimal(6,2),
	@SaccadesLatencyMeanLEFT decimal(6,2),
	@SaccadesAmplitudeLEFT decimal(6,2),
	@SaccadesPeakVelocityLEFT decimal(6,2),
	@SaccadesDurationRIGHT decimal(6,2),
	@SaccadesLatencyMeanRIGHT decimal(6,2),
	@SaccadesAmplitudeRIGHT decimal(6,2),
	@SaccadesPeakVelocityRIGHT decimal(6,2),
	@SaccadesDurationALL decimal(6,2),
	@SaccadesLatencyMeanALL decimal(6,2),
	@SaccadesAmplitudeALL decimal(6,2),
	@SaccadesPeakVelocityALL decimal(6,2),
	@Antisaccades bit,
	@AntisaccadesPercentOfCorrectLEFT decimal(8,5),
	@AntisaccadesPercentOfCorrectRIGHT decimal(8,5),
	@AntisaccadesLatencyMeanLEFT decimal(8,5),
	@AntisaccadesLatencyMeanRIGHT decimal(8,5),
	@AntisaccadesDurationLEFT decimal(8,5),
	@AntisaccadesDurationRIGHT decimal(8,5),
	@AntisaccadesAmplitudeLEFT decimal(8,5),
	@AntisaccadesAmplitudeRIGHT decimal(8,5),
	@AntisaccadesPeakVelocityLEFT decimal(8,5),
	@AntisaccadesPeakVelocityRIGHT decimal(8,5),
	@AntisaccadesPercentOfCorrectALL decimal(8,5),
	@AntisaccadesLatencyMeanALL decimal(8,5),
	@AntisaccadesDurationALL decimal(8,5),
	@AntisaccadesAmplitudeALL decimal(8,5),
	@AntisaccadesPeakVelocityALL decimal(8,5),
	@POM_Gain_SlowSinus decimal(8,5),
	@POM_StDev_SlowSinus decimal(8,5),
	@POM_Gain_MediumSinus decimal(8,5),
	@POM_StDev_MediumSinus decimal(8,5),
	@POM_Gain_FastSinus decimal(8,5),
	@POM_StDev_FastSinus decimal(8,5),
	@POM_Accuracy_SlowSinus decimal(8,5),
	@POM_Accuracy_MediumSinus decimal(8,5),
	@POM_Accuracy_FastSinus decimal(8,5),
	@allow_update_existing bit,
	@actor_login varchar(50),
	@result int OUTPUT, @variant_id int OUTPUT, @message varchar(200) OUTPUT )
as
begin
	declare @user_id int;
	declare @update int;
	set @result = 0;
	set @message = '';
	set @update = 0;

	
	if( not exists (select * from Wizyta where IdWizyta = @IdWizyta) )
	begin
		set @result = 3;
		set @message = 'visit of this number not found';
		return;
	end;
	select @user_id = dbo.identify_user(@actor_login);
	if(@user_id = 0)
	begin
		set @result = 4;
		set @message = 'user of this login not found';
		return;
	end;


	if (select count(*) from Badanie where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT ) > 0 
	begin
		if (@allow_update_existing = 0)
			begin
				set @result = 1;
				return;
			end;
		set @update = 1;
		select @variant_id = IdBadanie from Badanie where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT;
	end;

	-- validations

	-- TODO!
	if dbo.validate_input_int('Badanie', 'DBS', @DBS) = 0	begin	set @result = 2;	set @message = 'Invalid value for attribute DBS';	return;	end;

	if dbo.validate_input_int('Badanie', 'UPDRS_I', @UPDRS_I) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_I'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_II', @UPDRS_II) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_II'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_18', @UPDRS_18) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_18'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_19', @UPDRS_19) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_19'; return; end; 
	if dbo.validate_input_int('Badanie', 'UPDRS_20_FaceLipsChin', @UPDRS_20_FaceLipsChin) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_FaceLipsChin'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_RHand', @UPDRS_20_RHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_RHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_LHand', @UPDRS_20_LHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_LHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_RFoot', @UPDRS_20_RFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_RFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_20_LFoot', @UPDRS_20_LFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_20_LFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_21_RHand', @UPDRS_21_RHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_21_RHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_21_LHand', @UPDRS_21_LHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_21_LHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_Neck', @UPDRS_22_Neck) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_Neck'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_RHand', @UPDRS_22_RHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_RHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_LHand', @UPDRS_22_LHand) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_LHand'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_RFoot', @UPDRS_22_RFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_RFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_22_LFoot', @UPDRS_22_LFoot) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_22_LFoot'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_23_R', @UPDRS_23_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_23_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_23_L', @UPDRS_23_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_23_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_24_R', @UPDRS_24_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_24_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_24_L', @UPDRS_24_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_24_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_25_R', @UPDRS_25_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_25_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_25_L', @UPDRS_25_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_25_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_26_R', @UPDRS_26_R) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_26_R'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_26_L', @UPDRS_26_L) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_26_L'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_27', @UPDRS_27) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_27'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_28', @UPDRS_28) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_28'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_29', @UPDRS_29) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_29'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_30', @UPDRS_30) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_30'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_31', @UPDRS_31) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_31'; return; end;
	if dbo.validate_input_int('Badanie', 'UPDRS_IV', @UPDRS_IV) = 0 begin set @result = 2; set @message = 'Invalid value for attribute UPDRS_IV'; return; end;

	if dbo.validate_input_int('Badanie', 'SchwabEnglandScale', @SchwabEnglandScale) = 0 begin set @result = 2; set @message = 'Invalid value for attribute SchwabEnglandScale'; return; end;
	if dbo.validate_input_decimal('Badanie', 'HYscale', @HYscale) = 0 begin set @result = 2; set @message = 'Invalid value for attribute HYscale'; return; end;

	-- /validations
	-- depending on update
	if(@update = 0)
		begin
		insert into Badanie (
			IdWizyta,
			DBS,
			BMT,
			UPDRS_I,
			UPDRS_II,
			UPDRS_18,
			UPDRS_19 ,
			UPDRS_20_FaceLipsChin,
			UPDRS_20_RHand,
			UPDRS_20_LHand,
			UPDRS_20_RFoot,
			UPDRS_20_LFoot,
			UPDRS_21_RHand,
			UPDRS_21_LHand,
			UPDRS_22_Neck,
			UPDRS_22_RHand,
			UPDRS_22_LHand,
			UPDRS_22_RFoot,
			UPDRS_22_LFoot,
			UPDRS_23_R,
			UPDRS_23_L,
			UPDRS_24_R,
			UPDRS_24_L,
			UPDRS_25_R,
			UPDRS_25_L,
			UPDRS_26_R,
			UPDRS_26_L,
			UPDRS_27,
			UPDRS_28,
			UPDRS_29,
			UPDRS_30,
			UPDRS_31,
			UPDRS_III,
			UPDRS_IV,
			UPDRS_TOTAL,
			HYscale,
			SchwabEnglandScale,
			JazzNovo,
			Wideookulograf,
			Saccades,
			SaccadesLatencyMeanLEFT,
			SaccadesLatencyMeanRIGHT,
			SaccadesDurationLEFT,
			SaccadesDurationRIGHT,
			SaccadesAmplitudeLEFT,
			SaccadesAmplitudeRIGHT,
			SaccadesPeakVelocityLEFT,
			SaccadesPeakVelocityRIGHT,
			SaccadesLatencyMeanALL,
			SaccadesDurationALL,
			SaccadesAmplitudeALL,
			SaccadesPeakVelocityALL,
			Antisaccades,
			AntisaccadesPercentOfCorrectLEFT,
			AntisaccadesPercentOfCorrectRIGHT,
			AntisaccadesLatencyMeanLEFT,
			AntisaccadesLatencyMeanRIGHT,
			AntisaccadesDurationLEFT,
			AntisaccadesDurationRIGHT,
			AntisaccadesAmplitudeLEFT,
			AntisaccadesAmplitudeRIGHT,
			AntisaccadesPeakVelocityLEFT,
			AntisaccadesPeakVelocityRIGHT,
			AntisaccadesPercentOfCorrectALL,
			AntisaccadesLatencyMeanALL,
			AntisaccadesDurationALL,
			AntisaccadesAmplitudeALL,
			AntisaccadesPeakVelocityALL,
			POM_Gain_SlowSinus,
			POM_StDev_SlowSinus,
			POM_Gain_MediumSinus,
			POM_StDev_MediumSinus,
			POM_Gain_FastSinus,
			POM_StDev_FastSinus,
			POM_Accuracy_SlowSinus,
			POM_Accuracy_MediumSinus,
			POM_Accuracy_FastSinus,
			Wprowadzil,
			Zmodyfikowal,
			OstatniaZmiana
		 )
		values (
			@IdWizyta,
			@DBS,
			@BMT,
			@UPDRS_I,
			@UPDRS_II,
			@UPDRS_18,
			@UPDRS_19 ,
			@UPDRS_20_FaceLipsChin,
			@UPDRS_20_RHand,
			@UPDRS_20_LHand,
			@UPDRS_20_RFoot,
			@UPDRS_20_LFoot,
			@UPDRS_21_RHand,
			@UPDRS_21_LHand,
			@UPDRS_22_Neck,
			@UPDRS_22_RHand,
			@UPDRS_22_LHand,
			@UPDRS_22_RFoot,
			@UPDRS_22_LFoot,
			@UPDRS_23_R,
			@UPDRS_23_L,
			@UPDRS_24_R,
			@UPDRS_24_L,
			@UPDRS_25_R,
			@UPDRS_25_L,
			@UPDRS_26_R,
			@UPDRS_26_L,
			@UPDRS_27,
			@UPDRS_28,
			@UPDRS_29,
			@UPDRS_30,
			@UPDRS_31,
			@UPDRS_III,
			@UPDRS_IV,
			@UPDRS_TOTAL,
			@HYscale,
			@SchwabEnglandScale,
			@JazzNovo,
			@Wideookulograf,
			@Saccades,
			@SaccadesLatencyMeanLEFT,
			@SaccadesLatencyMeanRIGHT,
			@SaccadesDurationLEFT,
			@SaccadesDurationRIGHT,
			@SaccadesAmplitudeLEFT,
			@SaccadesAmplitudeRIGHT,
			@SaccadesPeakVelocityLEFT,
			@SaccadesPeakVelocityRIGHT,
			@SaccadesLatencyMeanALL,
			@SaccadesDurationALL,
			@SaccadesAmplitudeALL,
			@SaccadesPeakVelocityALL,
			@Antisaccades,
			@AntisaccadesPercentOfCorrectLEFT,
			@AntisaccadesPercentOfCorrectRIGHT,
			@AntisaccadesLatencyMeanLEFT,
			@AntisaccadesLatencyMeanRIGHT,
			@AntisaccadesDurationLEFT,
			@AntisaccadesDurationRIGHT,
			@AntisaccadesAmplitudeLEFT,
			@AntisaccadesAmplitudeRIGHT,
			@AntisaccadesPeakVelocityLEFT,
			@AntisaccadesPeakVelocityRIGHT,
			@AntisaccadesPercentOfCorrectALL,
			@AntisaccadesLatencyMeanALL,
			@AntisaccadesDurationALL,
			@AntisaccadesAmplitudeALL,
			@AntisaccadesPeakVelocityALL,
			@POM_Gain_SlowSinus,
			@POM_StDev_SlowSinus,
			@POM_Gain_MediumSinus,
			@POM_StDev_MediumSinus,
			@POM_Gain_FastSinus,
			@POM_StDev_FastSinus,
			@POM_Accuracy_SlowSinus,
			@POM_Accuracy_MediumSinus,
			@POM_Accuracy_FastSinus,
			@user_id,
			@user_id, 
			getdate() 		
		)  set @variant_id = SCOPE_IDENTITY();
		end;
	else
		begin
			update Badanie
			set 
				UPDRS_I	=	@UPDRS_I,
				UPDRS_II	=	@UPDRS_II,
				UPDRS_18	=	@UPDRS_18,
				UPDRS_19 	=	@UPDRS_19,
				UPDRS_20_FaceLipsChin	=	@UPDRS_20_FaceLipsChin,
				UPDRS_20_RHand	=	@UPDRS_20_RHand,
				UPDRS_20_LHand	=	@UPDRS_20_LHand,
				UPDRS_20_RFoot	=	@UPDRS_20_RFoot,
				UPDRS_20_LFoot	=	@UPDRS_20_LFoot,
				UPDRS_21_RHand	=	@UPDRS_21_RHand,
				UPDRS_21_LHand	=	@UPDRS_21_LHand,
				UPDRS_22_Neck	=	@UPDRS_22_Neck,
				UPDRS_22_RHand	=	@UPDRS_22_RHand,
				UPDRS_22_LHand	=	@UPDRS_22_LHand,
				UPDRS_22_RFoot	=	@UPDRS_22_RFoot,
				UPDRS_22_LFoot	=	@UPDRS_22_LFoot,
				UPDRS_23_R	=	@UPDRS_23_R,
				UPDRS_23_L	=	@UPDRS_23_L,
				UPDRS_24_R	=	@UPDRS_24_R,
				UPDRS_24_L	=	@UPDRS_24_L,
				UPDRS_25_R	=	@UPDRS_25_R,
				UPDRS_25_L	=	@UPDRS_25_L,
				UPDRS_26_R	=	@UPDRS_26_R,
				UPDRS_26_L	=	@UPDRS_26_L,
				UPDRS_27	=	@UPDRS_27,
				UPDRS_28	=	@UPDRS_28,
				UPDRS_29	=	@UPDRS_29,
				UPDRS_30	=	@UPDRS_30,
				UPDRS_31	=	@UPDRS_31,
				UPDRS_III	=	@UPDRS_III,
				UPDRS_IV	=	@UPDRS_IV,
				UPDRS_TOTAL	=	@UPDRS_TOTAL,
				HYscale	=	@HYscale,
				SchwabEnglandScale	=	@SchwabEnglandScale,
				JazzNovo = @JazzNovo,
				Wideookulograf = @Wideookulograf,
				Saccades	=	@Saccades,
				SaccadesLatencyMeanLEFT	=	@SaccadesLatencyMeanLEFT,
				SaccadesLatencyMeanRIGHT	=	@SaccadesLatencyMeanRIGHT,
				SaccadesDurationLEFT	=	@SaccadesDurationLEFT,
				SaccadesDurationRIGHT	=	@SaccadesDurationRIGHT,
				SaccadesAmplitudeLEFT	=	@SaccadesAmplitudeLEFT,
				SaccadesAmplitudeRIGHT	=	@SaccadesAmplitudeRIGHT,
				SaccadesPeakVelocityLEFT	=	@SaccadesPeakVelocityLEFT,
				SaccadesPeakVelocityRIGHT	=	@SaccadesPeakVelocityRIGHT,
				SaccadesLatencyMeanALL	=	@SaccadesLatencyMeanALL,
				SaccadesDurationALL	=	@SaccadesDurationALL,
				SaccadesAmplitudeALL	=	@SaccadesAmplitudeALL,
				SaccadesPeakVelocityALL	=	@SaccadesPeakVelocityALL,
				Antisaccades	=	@Antisaccades,
				AntisaccadesPercentOfCorrectLEFT	=	@AntisaccadesPercentOfCorrectLEFT,
				AntisaccadesPercentOfCorrectRIGHT	=	@AntisaccadesPercentOfCorrectRIGHT,
				AntisaccadesLatencyMeanLEFT	=	@AntisaccadesLatencyMeanLEFT,
				AntisaccadesLatencyMeanRIGHT	=	@AntisaccadesLatencyMeanRIGHT,
				AntisaccadesDurationLEFT	=	@AntisaccadesDurationLEFT,
				AntisaccadesDurationRIGHT	=	@AntisaccadesDurationRIGHT,
				AntisaccadesAmplitudeLEFT	=	@AntisaccadesAmplitudeLEFT,
				AntisaccadesAmplitudeRIGHT	=	@AntisaccadesAmplitudeRIGHT,
				AntisaccadesPeakVelocityLEFT	=	@AntisaccadesPeakVelocityLEFT,
				AntisaccadesPeakVelocityRIGHT	=	@AntisaccadesPeakVelocityRIGHT,
				AntisaccadesPercentOfCorrectALL	=	@AntisaccadesPercentOfCorrectALL,
				AntisaccadesLatencyMeanALL	=	@AntisaccadesLatencyMeanALL,
				AntisaccadesDurationALL	=	@AntisaccadesDurationALL,
				AntisaccadesAmplitudeALL	=	@AntisaccadesAmplitudeALL,
				AntisaccadesPeakVelocityALL	=	@AntisaccadesPeakVelocityALL,
				POM_Gain_SlowSinus	=	@POM_Gain_SlowSinus,
				POM_StDev_SlowSinus	=	@POM_StDev_SlowSinus,
				POM_Gain_MediumSinus	=	@POM_Gain_MediumSinus,
				POM_StDev_MediumSinus	=	@POM_StDev_MediumSinus,
				POM_Gain_FastSinus	=	@POM_Gain_FastSinus,
				POM_StDev_FastSinus	=	@POM_StDev_FastSinus,
				POM_Accuracy_SlowSinus	=	@POM_Accuracy_SlowSinus,
				POM_Accuracy_MediumSinus	=	@POM_Accuracy_MediumSinus,
				POM_Accuracy_FastSinus	=	@POM_Accuracy_FastSinus,
				Zmodyfikowal = @user_id, 
				OstatniaZmiana = getdate() 
			where IdWizyta = @IdWizyta and DBS = @DBS and BMT = @BMT ;
		end;
	return;
end;
go


-- altered: 2015-06-05
alter procedure get_database_copy
as
SELECT 
/* PACJENT */
	   P.[NumerPacjenta]
      ,P.[RokUrodzenia]
      ,P.[MiesiacUrodzenia]
      ,P.[Plec]
      ,P.[Lokalizacja]
      ,P.[LiczbaElektrod]
	  ,P.[NazwaGrupy]
/*    ,P.[Wprowadzil] as WprowadzilDanePacjenta
      ,P.[Zmodyfikowal] as KorygowalDanePacjenta
      ,P.[OstatniaZmiana] as OstatniaKorektaDanychPacjenta */
/* WIZYTA */
      ,W.[RodzajWizyty]
      ,W.[DataPrzyjecia]
      ,W.[DataWypisu]
      ,W.[MasaCiala]
      ,W.[DataOperacji]
      ,W.[Wyksztalcenie]
      ,W.[Rodzinnosc]
      ,W.[RokZachorowania]
	  ,CAST(datediff(day, CAST( CAST((select max(RokZachorowania) from Wizyta x where x.IdPacjent = p.IdPacjent) as varchar)+'-'+ CAST(1 as varchar)+'-'+ CAST(1 as varchar) as datetime), W.DataPrzyjecia )/365.0 as decimal(4,2)) as CzasTrwaniaChoroby
      ,W.[PierwszyObjaw]
      ,W.[Drzenie]
      ,W.[Sztywnosc]
      ,W.[Spowolnienie]
      ,W.[ObjawyInne]
      ,REPLACE(W.[ObjawyInneJakie],';','. ') ObjawyInneJakie
      ,W.[CzasOdPoczObjDoWlLDopy]
      ,W.[DyskinezyObecnie]
      ,W.[DyskinezyOdLat]
      ,W.[FluktuacjeObecnie]
      ,W.[FluktuacjeOdLat]
      ,W.[CzasDyskinez]
      ,W.[CzasOFF]
      ,W.[PoprawaPoLDopie]
      ,W.[PrzebyteLeczenieOperacyjnePD]
      ,W.[Papierosy]
      ,W.[Kawa]
      ,W.[ZielonaHerbata]
      ,W.[Alkohol]
      ,W.[ZabiegowWZnieczOgPrzedRozpoznaniemPD]
      ,W.[Zamieszkanie]
	  ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'NarazenieNaToks' and IdWizyta = W.IdWizyta) as NarazeniaNaToks
      ,REPLACE(W.[Uwagi],';','. ') Uwagi
      ,W.[Nadcisnienie]
      ,W.[BlokeryKanWapn]
      ,W.[DominujacyObjawObecnie]
      ,REPLACE(W.[DominujacyObjawUwagi],';','. ') DominujacyObjawUwagi
	  ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'ObjawyAutonomiczne' and IdWizyta = W.IdWizyta) as ObjawyAutonomiczne
      ,W.[RLS]
      ,W.[ObjawyPsychotyczne]
      ,W.[Depresja]
      ,W.[Otepienie]
      ,W.[Dyzartria]
      ,W.[DysfagiaObjaw]
      ,W.[RBD]
      ,W.[ZaburzenieRuchomosciGalekOcznych]
      ,W.[Apraksja]
      ,W.[TestKlaskania]
      ,W.[ZaburzeniaWechowe]
      ,W.[Ldopa]
      ,W.[LDopaObecnie]
      ,W.[Agonista]
      ,W.[AgonistaObecnie]
      ,W.[Amantadyna]
      ,W.[AmantadynaObecnie]
      ,W.[MAOBinh]
      ,W.[MAOBinhObecnie]
      ,W.[COMTinh]
      ,W.[COMTinhObecnie]
      ,W.[Cholinolityk]
      ,W.[CholinolitykObecnie]
      ,W.[LekiInne]
      ,REPLACE(W.[LekiInneJakie],';','. ') LekiInneJakie
      ,REPLACE(W.[L_STIMOpis],';','. ') L_STIMOpis
      ,W.[L_STIMAmplitude]
      ,W.[L_STIMDuration]
      ,W.[L_STIMFrequency]
      ,REPLACE(W.[R_STIMOpis],';','. ') R_STIMOpis
      ,W.[R_STIMAmplitude]
      ,W.[R_STIMDuration]
      ,W.[R_STIMFrequency]
      ,W.[Wypis_Ldopa]
      ,W.[Wypis_LDopaObecnie]
      ,W.[Wypis_Agonista]
      ,W.[Wypis_AgonistaObecnie]
      ,W.[Wypis_Amantadyna]
      ,W.[Wypis_AmantadynaObecnie]
      ,W.[Wypis_MAOBinh]
      ,W.[Wypis_MAOBinhObecnie]
      ,W.[Wypis_COMTinh]
      ,W.[Wypis_COMTinhObecnie]
      ,W.[Wypis_Cholinolityk]
      ,W.[Wypis_CholinolitykObecnie]
      ,W.[Wypis_LekiInne]
      ,W.[Wypis_LekiInneJakie]
      ,REPLACE(W.[Wypis_L_STIMOpis],';','. ') Wypis_L_STIMOpis
      ,W.[Wypis_L_STIMAmplitude]
      ,W.[Wypis_L_STIMDuration]
      ,W.[Wypis_L_STIMFrequency]
      ,REPLACE(W.[Wypis_R_STIMOpis],';','. ') Wypis_R_STIMOpis
      ,W.[Wypis_R_STIMAmplitude]
      ,W.[Wypis_R_STIMDuration]
      ,W.[Wypis_R_STIMFrequency]
      ,W.[WydzielanieSliny]
      ,W.[Dysfagia]
      ,W.[DysfagiaCzestotliwosc]
      ,W.[Nudnosci]
      ,W.[Zaparcia]
      ,W.[TrudnosciWOddawaniuMoczu]
      ,W.[PotrzebaNaglegoOddaniaMoczu]
      ,W.[NiekompletneOproznieniePecherza]
      ,W.[SlabyStrumienMoczu]
      ,W.[CzestotliwowscOddawaniaMoczu]
      ,W.[Nykturia]
      ,W.[NiekontrolowaneOddawanieMoczu]
      ,W.[Omdlenia]
      ,W.[ZaburzeniaRytmuSerca]
      ,W.[ProbaPionizacyjna]
      ,W.[WzrostPodtliwosciTwarzKark]
      ,W.[WzrostPotliwosciRamionaDlonie]
	  ,W.[WzrostPotliwosciBrzuchPlecy]
      ,W.[WzrostPotliwosciKonczynyDolneStopy]
      ,W.[SpadekPodtliwosciTwarzKark]
      ,W.[SpadekPotliwosciRamionaDlonie]
	  ,W.[SpadekPotliwosciBrzuchPlecy]
      ,W.[SpadekPotliwosciKonczynyDolneStopy]
      ,W.[NietolerancjaWysokichTemp]
      ,W.[NietolerancjaNiskichTemp]
      ,W.[Lojotok]
      ,W.[SpadekLibido]
      ,W.[KlopotyOsiagnieciaErekcji]
      ,W.[KlopotyUtrzymaniaErekcji]
	/* BADANIE (wariantowe) */
      ,B.[DBS]
      ,B.[BMT]
      ,B.[UPDRS_I]
      ,B.[UPDRS_II]
      ,B.[UPDRS_18]
      ,B.[UPDRS_19]
      ,B.[UPDRS_20_FaceLipsChin]
      ,B.[UPDRS_20_RHand]
      ,B.[UPDRS_20_LHand]
      ,B.[UPDRS_20_RFoot]
      ,B.[UPDRS_20_LFoot]
      ,B.[UPDRS_21_RHand]
      ,B.[UPDRS_21_LHand]
      ,B.[UPDRS_22_Neck]
      ,B.[UPDRS_22_RHand]
      ,B.[UPDRS_22_LHand]
      ,B.[UPDRS_22_RFoot]
      ,B.[UPDRS_22_LFoot]
      ,B.[UPDRS_23_R]
      ,B.[UPDRS_23_L]
      ,B.[UPDRS_24_R]
      ,B.[UPDRS_24_L]
      ,B.[UPDRS_25_R]
      ,B.[UPDRS_25_L]
      ,B.[UPDRS_26_R]
      ,B.[UPDRS_26_L]
      ,B.[UPDRS_27]
      ,B.[UPDRS_28]
      ,B.[UPDRS_29]
      ,B.[UPDRS_30]
      ,B.[UPDRS_31]
      ,B.[UPDRS_III]
      ,B.[UPDRS_IV]
      ,B.[UPDRS_TOTAL]
      ,B.[HYscale]
      ,B.[SchwabEnglandScale]
      ,B.[JazzNovo]
      ,B.[Wideookulograf]
	,B.[Saccades]
	,B.[SaccadesLatencyMeanLEFT]
	,B.[SaccadesLatencyMeanRIGHT]
	,B.[SaccadesDurationLEFT]
	,B.[SaccadesDurationRIGHT]
	,B.[SaccadesAmplitudeLEFT]
	,B.[SaccadesAmplitudeRIGHT]
	,B.[SaccadesPeakVelocityLEFT]
	,B.[SaccadesPeakVelocityRIGHT]
	,B.[SaccadesLatencyMeanALL]
	,B.[SaccadesDurationALL]
	,B.[SaccadesAmplitudeALL]
	,B.[SaccadesPeakVelocityALL]
	,B.[Antisaccades]
	,B.[AntisaccadesPercentOfCorrectLEFT]
	,B.[AntisaccadesPercentOfCorrectRIGHT]
	,B.[AntisaccadesLatencyMeanLEFT]
	,B.[AntisaccadesLatencyMeanRIGHT]
	,B.[AntisaccadesDurationLEFT]
	,B.[AntisaccadesDurationRIGHT]
	,B.[AntisaccadesAmplitudeLEFT]
	,B.[AntisaccadesAmplitudeRIGHT]
	,B.[AntisaccadesPeakVelocityLEFT]
	,B.[AntisaccadesPeakVelocityRIGHT]
	,B.[AntisaccadesPercentOfCorrectALL]
	,B.[AntisaccadesLatencyMeanALL]
	,B.[AntisaccadesDurationALL]
	,B.[AntisaccadesAmplitudeALL]
	,B.[AntisaccadesPeakVelocityALL]
	,B.[POM_Gain_SlowSinus]
	,B.[POM_StDev_SlowSinus]
	,B.[POM_Gain_MediumSinus]
	,B.[POM_StDev_MediumSinus]
	,B.[POM_Gain_FastSinus]
	,B.[POM_StDev_FastSinus]
	,B.[POM_Accuracy_SlowSinus]
	,B.[POM_Accuracy_MediumSinus]
	,B.[POM_Accuracy_FastSinus]
      ,B.[Tremorometria]
      ,B.[TremorometriaLEFT]
      ,B.[TremorometriaRIGHT]
      ,B.[TremorometriaLEFT_0_1]
      ,B.[TremorometriaLEFT_1_2]
      ,B.[TremorometriaLEFT_2_3]
      ,B.[TremorometriaLEFT_3_4]
      ,B.[TremorometriaLEFT_4_5]
      ,B.[TremorometriaLEFT_5_6]
      ,B.[TremorometriaLEFT_6_7]
      ,B.[TremorometriaLEFT_7_8]
      ,B.[TremorometriaLEFT_8_9]
      ,B.[TremorometriaLEFT_9_10]
      ,B.[TremorometriaLEFT_23_24]
      ,B.[TremorometriaRIGHT_0_1]
      ,B.[TremorometriaRIGHT_1_2]
      ,B.[TremorometriaRIGHT_2_3]
      ,B.[TremorometriaRIGHT_3_4]
      ,B.[TremorometriaRIGHT_4_5]
      ,B.[TremorometriaRIGHT_5_6]
      ,B.[TremorometriaRIGHT_6_7]
      ,B.[TremorometriaRIGHT_7_8]
      ,B.[TremorometriaRIGHT_8_9]
      ,B.[TremorometriaRIGHT_9_10]
      ,B.[TremorometriaRIGHT_23_24]
      ,B.[TestSchodkowy]
      ,B.[TestSchodkowyWDol]
      ,B.[TestSchodkowyWGore]
      ,B.[TestMarszu]
      ,B.[TestMarszuCzas1]
      ,B.[TestMarszuCzas2]
      ,B.[Posturografia]
      ,B.[MotionAnalysis]
      ,B.[Otwarte_Srednia_C_o_P_X]
      ,B.[Otwarte_Srednia_C_o_P_Y]
      ,B.[Otwarte_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Otwarte_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Otwarte_Perimeter_mm]
      ,B.[Otwarte_PoleElipsy_mm2]
      ,B.[Zamkniete_Srednia_C_o_P_X]
      ,B.[Zamkniete_Srednia_C_o_P_Y]
      ,B.[Zamkniete_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Zamkniete_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Zamkniete_Perimeter_mm]
      ,B.[Zamkniete_PoleElipsy_mm2]
      ,B.[WspolczynnikPerymetru_E_C_E_O_obie_stopy]
      ,B.[WspolczynnikPowierzchni_E_C_E_O_obie_stopy]
      ,B.[Biofeedback_Srednia_C_o_P_X]
      ,B.[Biofeedback_Srednia_C_o_P_Y]
      ,B.[Biofeedback_Srednia_P_T_Predkosc_mm_sec]
      ,B.[Biofeedback_Srednia_P_B_Predkosc_mm_sec]
      ,B.[Biofeedback_Perimeter_mm]
      ,B.[Biofeedback_PoleElipsy_mm2]				
      ,B.[UpAndGo]
      ,B.[UpAndGoLiczby]
      ,B.[UpAndGoKubekPrawa]
      ,B.[UpAndGoKubekLewa]
      ,B.[TST]
      ,B.[TandemPivot]
      ,B.[WTT]
      ,B.[Wprowadzil] as WariantZapisal
      ,B.[Zmodyfikowal] as WariantModyfikowal
      ,B.[OstatniaZmiana] as OstatniaEdycjaWariantu
/* WIZYTA - c.d. */
      ,W.[PDQ39]
      ,W.[AIMS]
      ,W.[Epworth]
      ,W.[CGI]
      ,W.[FSS]
	,REPLACE(W.[TestZegara],';','. ') TestZegara
	,REPLACE(W.[MMSE],';','. ') MMSE
	,REPLACE(W.[CLOX1_Rysunek],';','. ') CLOX1_Rysunek
	,REPLACE(W.[CLOX2_Kopia],';','. ') CLOX2_Kopia
	,REPLACE(W.[AVLT_proba_1],';','. ') AVLT_proba_1
	,REPLACE(W.[AVLT_proba_2],';','. ') AVLT_proba_2
	,REPLACE(W.[AVLT_proba_3],';','. ') AVLT_proba_3
	,REPLACE(W.[AVLT_proba_4],';','. ') AVLT_proba_4
	,REPLACE(W.[AVLT_proba_5],';','. ') AVLT_proba_5
	,REPLACE(W.[AVLT_Suma],';','. ') AVLT_Suma
	,REPLACE(W.[AVLT_Srednia],';','. ') AVLT_Srednia
	,REPLACE(W.[AVLT_KrotkieOdroczenie],';','. ') AVLT_KrotkieOdroczenie
	,REPLACE(W.[AVLT_Odroczony20min],';','. ') AVLT_Odroczony20min
	,REPLACE(W.[AVLT_Rozpoznawanie],';','. ') AVLT_Rozpoznawanie
	,REPLACE(W.[AVLT_BledyRozpoznania],';','. ') AVLT_BledyRozpoznania

	,REPLACE(W.[TestAVLTSrednia],';','. ') TestAVLTSrednia
	,REPLACE(W.[TestAVLTOdroczony],';','. ') TestAVLTOdroczony
	,REPLACE(W.[TestAVLTPo20min],';','. ') TestAVLTPo20min
	,REPLACE(W.[TestAVLTRozpoznawanie],';','. ') TestAVLTRozpoznawanie

	,REPLACE(W.[CVLT_proba_1],';','. ') CVLT_proba_1
	,REPLACE(W.[CVLT_proba_2],';','. ') CVLT_proba_2
	,REPLACE(W.[CVLT_proba_3],';','. ') CVLT_proba_3
	,REPLACE(W.[CVLT_proba_4],';','. ') CVLT_proba_4
	,REPLACE(W.[CVLT_proba_5],';','. ') CVLT_proba_5
	,REPLACE(W.[CVLT_Suma],';','. ') CVLT_Suma
	,REPLACE(W.[CVLT_OSKO_krotkie_odroczenie],';','. ') CVLT_OSKO_krotkie_odroczenie
	,REPLACE(W.[CVLT_OPKO_krotkie_odroczenie_i_pomoc],';','. ') CVLT_OPKO_krotkie_odroczenie_i_pomoc
	,REPLACE(W.[CVLT_OSDO_po20min],';','. ') CVLT_OSDO_po20min
	,REPLACE(W.[CVLT_OPDO_po20min_i_pomoc],';','. ') CVLT_OPDO_po20min_i_pomoc
	,REPLACE(W.[CVLT_perseweracje],';','. ') CVLT_perseweracje
	,REPLACE(W.[CVLT_WtraceniaOdtwarzanieSwobodne],';','. ') CVLT_WtraceniaOdtwarzanieSwobodne
	,REPLACE(W.[CVLT_wtraceniaOdtwarzanieZPomoca],';','. ') CVLT_wtraceniaOdtwarzanieZPomoca
	,REPLACE(W.[CVLT_Rozpoznawanie],';','. ') CVLT_Rozpoznawanie
	,REPLACE(W.[CVLT_BledyRozpoznania],';','. ') CVLT_BledyRozpoznania
	,REPLACE(W.[Benton_JOL],';','. ') Benton_JOL
	,REPLACE(W.[WAIS_R_Wiadomosci],';','. ') WAIS_R_Wiadomosci
	,REPLACE(W.[WAIS_R_PowtarzanieCyfr],';','. ') WAIS_R_PowtarzanieCyfr
	,REPLACE(W.[WAIS_R_Podobienstwa],';','. ') WAIS_R_Podobienstwa
	,REPLACE(W.[BostonskiTestNazywaniaBMT],';','. ') BostonskiTestNazywaniaBMT
	,REPLACE(W.[BMT_SredniCzasReakcji_sek],';','. ') BMT_SredniCzasReakcji_sek
	,W.[SkalaDepresjiBecka]
	,W.[SkalaDepresjiBeckaII]
	,REPLACE(W.[TestFluencjiK],';','. ') TestFluencjiK
	,REPLACE(W.[TestFluencjiP],';','. ') TestFluencjiP
	,REPLACE(W.[TestFluencjiZwierzeta],';','. ') TestFluencjiZwierzeta
	,REPLACE(W.[TestFluencjiOwoceWarzywa],';','. ') TestFluencjiOwoceWarzywa
	,REPLACE(W.[TestFluencjiOstre],';','. ') TestFluencjiOstre
	,REPLACE(W.[TestLaczeniaPunktowA],';','. ') TestLaczeniaPunktowA
	,REPLACE(W.[TestLaczeniaPunktowB],';','. ') TestLaczeniaPunktowB
	,REPLACE(W.[ToL_SumaRuchow],';','. ') ToL_SumaRuchow
	,REPLACE(W.[ToL_LiczbaPrawidlowych],';','. ') ToL_LiczbaPrawidlowych
	,REPLACE(W.[ToL_CzasInicjowania_sek],';','. ') ToL_CzasInicjowania_sek
	,REPLACE(W.[ToL_CzasWykonania_sek],';','. ') ToL_CzasWykonania_sek
	,REPLACE(W.[ToL_CzasCalkowity_sek],';','. ') ToL_CzasCalkowity_sek
	,REPLACE(W.[ToL_CzasPrzekroczony],';','. ') ToL_CzasPrzekroczony
	,REPLACE(W.[ToL_LiczbaPrzekroczenZasad],';','. ') ToL_LiczbaPrzekroczenZasad
	,REPLACE(W.[ToL_ReakcjeUkierunkowane],';','. ') ToL_ReakcjeUkierunkowane
	,REPLACE(W.[InnePsychologiczne],';','. ') InnePsychologiczne
	,REPLACE(W.[OpisBadania],';','. ') OpisBadania
	,REPLACE(W.[Wnioski],';','. ') Wnioski
      ,W.[Holter]
      ,W.[BadanieWechu]
      ,W.[WynikWechu]
      ,W.[LimitDysfagii]
      ,W.[pH_metriaPrzełyku]
      ,W.[SPECT]
	   ,(select Wartosci from AtrybutyWielowartoscioweWizyty where NazwaAtrybutu = 'SPECTWynik' and IdWizyta = W.IdWizyta) as SPECTWyniki
      ,W.[MRI]
      ,REPLACE(W.[MRIwynik],';','. ') MRIwynik
      ,W.[USGsrodmozgowia]
      ,W.[USGWynik]
      ,W.[Genetyka]
      ,REPLACE(W.[GenetykaWynik],';','. ') GenetykaWynik
      ,W.[Surowica]
      ,REPLACE(W.[SurowicaPozostało],';','. ')
      ,W.[Ferrytyna]
      ,W.[CRP]
      ,W.[NTproCNP]
      ,W.[URCA]
      ,W.[WitD]
      ,W.[CHOL]
      ,W.[TGI]
      ,W.[HDL]
      ,W.[LDL]
      ,W.[olLDL]
      ,REPLACE(W.[LaboratoryjneInne],';','. ') LaboratoryjneInne
      ,W.[Wprowadzil] as WizyteWprowadzil
      ,W.[Zmodyfikowal] as WizyteEdytowal
      ,W.[OstatniaZmiana] as OstatniaModyfikacja

  FROM Pacjent P left join Wizyta w on P.IdPacjent = W.IdPacjent left join Badanie B on B.IdWizyta = W.IdWizyta
  order by P.NumerPacjenta, W.RodzajWizyty, B.BMT, B.DBS
  go

create type KolumnyUdt as table
(
	Pozycja int,
	KolumnaID int
)
go


create procedure get_transformed_copy(@column_filter as KolumnyUdt readonly, @timeline_filter int)
as
begin
	declare @sql as nvarchar(max);
	set @sql = 'select ''wynik'' as Kolumna1, ''testowy'' as Kolumna2';
		-- select @sql;
		exec sp_executesql @statement = @sql;
		
end
go

declare @filtr as KolumnyUdt;
insert into @filtr (Pozycja, KolumnaID) values ( 1, 1 );
insert into @filtr (Pozycja, KolumnaID)values ( 2, 2 );

exec get_transformed_copy @filtr, 20;


/*
ZMIANY OBEJMUJĄ:

(cyt.)

W związku z porządkowaniem części związanej z sakadami, w miarę Państwa możliwości:

1) bardzo prosiłbym o zmianę nazw odpowiednio na:

LatencymeterDurationLEFT      -> Saccades: Duration - Left                                                          

LatencymeterLatencyLEFT        -> Saccades: Latency mean - Left                                  

LatencymeterAmplitudeLEFT     -> Saccades: Amplitude - Left                  

LatencymeterPeakVelocityLEFT -> Saccades: Peak Velocity - Left                                         

LatencymeterDurationRIGHT     -> Saccades: Duration - Right                                   

LatencymeterLatencyRIGHT      -> Saccades: Latency mean - Right                                        

LatencymeterAmplitudeRIGHT   -> Saccades: Amplitude - Right                                              

LatencymeterPeakVelocityRIGHT  -> Saccades: Peak Velocity - Right                                    

LatencymeterDurationALL      -> Saccades: Duration - All                                                

LatencymeterLatencyALL       -> Saccades: Latency mean - All                                                      

LatencymeterAmplitudeALL     -> Saccades: Amplitude - All                                                         

LatencymeterPeakVelocityALL   -> Saccades: Peak Velocity – All

 

2) Następnie biorąc pod uwagę zmiany w nowym oprogramowaniu Obera i wiążące się z tym trudności (po zmianie kolejności poszczególnych parametrów) w przepisywaniu kolejnych danych, bardzo zależałoby mi na zmianie kolejności, odpowiednio na docelową (tak aby również przy pobieraniu tabeli excela były one uszeregowane w ten sposób):

Saccades: Latency mean - Left 

Saccades: Latency mean - Right              

Saccades: Duration - Left                                                           

Saccades: Duration - Right         

Saccades: Amplitude - Left                        

Saccades: Amplitude - Right      

Saccades: Peak Velocity - Left                                                 

Saccades: Peak Velocity - Right                               

Saccades: Latency mean - All

Saccades: Duration - All                                                              

Saccades: Amplitude - All                                                          

Saccades: Peak Velocity – All

 

3) Następnie prosiłbym o dodanie antysakad:

Antisaccades: Percent of correct – Left

Antisaccades: Percent of correct – Right

Antisaccades: Latency mean - Left         

Antisaccades: Latency mean - Right      

Antisaccades: Duration - Left                                                   

Antisaccades: Duration - Right 

Antisaccades: Amplitude - Left                

Antisaccades: Amplitude - Right             

Antisaccades: Peak Velocity - Left                                                         

Antisaccades: Peak Velocity - Right                                       

Antisaccades: Percent of correct – All

Antisaccades: Latency mean - All

Antisaccades: Duration - All                                                      

Antisaccades: Amplitude - All                                                  

Antisaccades: Peak Velocity – All

 

4) oraz prosiłbym o dodanie ruchów nadążnych:

POM: Gain for slow sinus           

POM:  Standard deviation for  slow sinus           

POM: Gain for medium sinus   

POM: Standard deviation for medium sinus           

POM: Gain for fast sinus               

POM: Standard deviation for fast sinus               

POM: Accuracy for slow sinus      

POM: Accuracy for medium sinus              

POM: Accuracy for fast sinus

====

Ponadto: poszerzenie słownika terminów wizyt dla najdłużej współpracujących pacjentów oraz dodanie kolumny informującej o zakończeniu udziału w badaniu.

====
Ponadto - zaczatek procedury do zdalnego generowania "przestawnych" zapytan - jako alternatywnej dla get_database_copy.

*/


