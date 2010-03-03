-- select * from Uzytkownik

-- insert into Uzytkownik (Login, Haslo, Imie, Nazwisko) values ( 'habela', 'password', 'Piotr', 'Habela')

-- select * from Performer

-- insert into Performer (Imie, Nazwisko) values ( 'Jan', 'Kowalski')
-- insert into Performer (Imie, Nazwisko) values ( 'Anna', 'Nowak')
-- select * from Laboratorium
-- insert into Laboratorium (Nazwa) values ('PJWSTK')
-- select * from Rodzaj_ruchu
-- insert into Rodzaj_ruchu (Nazwa) values ('walk')
-- insert into Rodzaj_ruchu (Nazwa) values ('run')
-- select * from Sesja
 --insert into Sesja (IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, IdPerformer, Data, Opis_sesji) values ( 
	--(select IdUzytkownik from Uzytkownik where Nazwisko='habela'), 
	--(select IdLaboratorium from Laboratorium where Nazwa='PJWSTK'), 
	--(select IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa='walk'), 
	--(select IdPerformer from Performer where Nazwisko='Kowalski'), 
	--GETDATE(), 
	--'sesja testowa nr 1' )
--insert into Sesja (IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, IdPerformer, Data, Opis_sesji) values ( 
--	(select IdUzytkownik from Uzytkownik where Nazwisko='habela'), 
--	(select IdLaboratorium from Laboratorium where Nazwa='PJWSTK'), 
--	(select IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa='run'), 
--	(select IdPerformer from Performer where Nazwisko='Kowalski'), 
--	GETDATE(), 
--	'sesja testowa nr 2' )
select * from Sesja

use Motion

 insert into Sesja (IdUzytkownik, IdLaboratorium, IdRodzaj_ruchu, IdPerformer, Data, Opis_sesji) values ( 
	(select IdUzytkownik from Uzytkownik where Nazwisko='habela'), 
	(select IdLaboratorium from Laboratorium where Nazwa='PJWSTK'), 
	(select IdRodzaj_ruchu from Rodzaj_ruchu where Nazwa='walk'), 
	(select IdPerformer from Performer where Nazwisko='Kowalski'), 
	GETDATE(), 
	'sesja testowa nr 3' )