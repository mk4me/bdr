use Motion

/*

Ponadto wgrano poprzez wywolana WS:

perf 2, sesja 3, trial 1, segment 1

session file:	perf_id = ??, file_id = 3, 29

	attribute: file_type / "XML_1.0"

performer file:	perf_id = ??, file_id = 3, 29

	attribute: file_type / "XML_1.0"

trial file:	trial_id = 1, file_id = ??

	attribute: file_type / "XML_1.0"

*/

select * from Uzytkownik

insert into Uzytkownik ( Login, Imie, Nazwisko ) values ( 'PJWSTK\habela', 'Piotr', 'Habela')
go
insert into Uzytkownik ( Login, Imie, Nazwisko ) values ( 'PJWSTK\kulbacki', 'Marek', 'Kulbacki')
go
insert into Uzytkownik ( Login, Imie, Nazwisko ) values ( 'PJWSTK\kaczmarski', 'Krzysztof', 'Kaczmarski')
go
insert into Uzytkownik ( Login, Imie, Nazwisko ) values ( 'PJWSTK\wiktor', 'Wiktor', 'Filipowicz')
go
insert into Uzytkownik ( Login, Imie, Nazwisko ) values ( 'DB-BDR\applet_user', 'Uzytkownik', 'Testowy')
go

update Uzytkownik   set  Login = 'DB-BDR\applet_user'  where Login = 'applet_user'


insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'General_session_attributes', 'session' )
go

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)

values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'marker_set', 'string')
go

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'number_of_trials', 'integer')    
go
-- delete from Grupa_atrybutow where IdGrupa_atrybutow = 2
      


insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_tekst)
values (
(select IdAtrybut from Atrybut where Nazwa = 'marker_set'),
2,
'PlugInGait.mkr'
)
go
insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_liczba)
values (
(select IdAtrybut from Atrybut where Nazwa = 'number_of_trials'),
2,
4
)
go

select * from Sesja
select * from Wartosc_atrybutu_sesji

insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ( 'General_performer_attributes', 'performer' )
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'date_of_birth', 'string')
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'diagnosis', 'string')    
go

select * from Wartosc_atrybutu_performera
go
--delete from Grupa_atrybutow where IdGrupa_atrybutow = 2
--go    

/* To be provided through WS when testing:

insert into Wartosc_atrybutu_performera (IdAtrybut, IdPerformer, Wartosc_tekst)
values (
(select IdAtrybut from Atrybut where Nazwa = 'date_of_birth'),
1,
'2000-01-01'
)

insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_tekst)
values (
(select IdAtrybut from Atrybut where Nazwa = 'diagnosis'),
1,
'Healthy'
)

*/

-- insert into Grupa_sesji (Nazwa) values ('Medical')
-- insert into Grupa_sesji (Nazwa) values ('Test')


insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ('General_file_attributes', 'file')
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_file_attributes'), 'file_type', 'string')    
go

insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ('General_segment_attributes', 'segment')
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_segment_attributes'), 'purpose', 'string')  
go
update Atrybut set Wyliczeniowy=1 where Nazwa = 'purpose';
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'purpose'), 'diagnosis')  
go
insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
values ( (select IdAtrybut from Atrybut where Nazwa = 'purpose'), 'entertainment') 
go
insert into Grupa_atrybutow (Nazwa, Opisywana_encja) values ('General_trial_attributes', 'trial')
go
insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_trial_attributes'), 'relevance', 'integer')  
go





