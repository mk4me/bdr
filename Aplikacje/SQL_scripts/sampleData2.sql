use Motion

/*

Ponadto wgrano poprzez wywolana WS:

perf 2, sesja 3, trial 1, segment 1

performer file:	perf_id = 1, file_id = 77

	attribute: file_type / "XML_1.0"

trial file:	trial_id = 1, file_id = 78

	attribute: file_type / "XML_1.0"

*/


--insert into Grupa_atrybutow (Nazwa) values ( 'General_session_attributes' )

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'marker_set', 'string', 'session')

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'number_of_trials', 'integer', 'session')    

---- delete from Grupa_atrybutow where IdGrupa_atrybutow = 2
      
--select * from Sesja

--insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_tekst)
--values (
--(select IdAtrybut from Atrybut where Nazwa = 'marker_set'),
--2,
--'PlugInGait.mkr'
--)

--insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_liczba)
--values (
--(select IdAtrybut from Atrybut where Nazwa = 'number_of_trials'),
--2,
--4
--)


--select * from Sesja
--select * from Wartosc_atrybutu_sesji

--insert into Grupa_atrybutow (Nazwa) values ( 'General_performer_attributes' )

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'date_of_birth', 'string', 'performer')

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'diagnosis', 'string', 'performer')    


--select * from Wartosc_atrybutu_performera


-- delete from Grupa_atrybutow where IdGrupa_atrybutow = 2
      
select * from Performer
select * from Wartosc_atrybutu_sesji

/* To be provided through WS when testing:

insert into Wartosc_atrybutu_performera (IdAtrybut, IdSesja, Wartosc_tekst)
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


--insert into Grupa_atrybutow (Nazwa) values ('General_file_attributes')

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_file_attributes'), 'file_type', 'string', 'file')    


--insert into Grupa_atrybutow (Nazwa) values ('General_segment_attributes')

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_segment_attributes'), 'purpose', 'string', 'segment')  

--update Atrybut set Wyliczeniowy=1 where Opisywana_encja = 'segment'

--insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
--values ( (select IdAtrybut from Atrybut where Nazwa = 'purpose' and Opisywana_encja='segment'), 'diagnosis')  

--insert into Wartosc_wyliczeniowa ( IdAtrybut, Wartosc_wyliczeniowa)
--values ( (select IdAtrybut from Atrybut where Nazwa = 'purpose' and Opisywana_encja='segment'), 'entertainment') 

--insert into Grupa_atrybutow (Nazwa) values ('General_trial_attributes')

--insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
--values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_trial_attributes'), 'relevance', 'integer', 'trial')  






