use Motion;
select * from Sesja s join Rodzaj_ruchu r on s.IdRodzaj_ruchu = r.IdRodzaj_ruchu

 select * from Uzytkownik
go
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


insert into Grupa_atrybutow ( Nazwa, Opisywana_encja ) values ('General_performer_attributes', 'performer');
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Wyliczeniowy)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'Sex', 'string', 1)    
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'M') 
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'Sex'), 'F') 

exec list_group_sessions_attributes_xml 'applet_user', 1

select * from Grupa_sesji

select * from Pomiar_performer

delete from Konfiguracja_pomiarowa

select * from Pomiar

select * from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow