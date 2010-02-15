use Motion

insert into Grupa_atrybutow (Nazwa) values ( 'General_session_attributes' )

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'marker_set', 'string', 'session')

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_session_attributes'), 'number_of_trials', 'integer', 'session')    

-- delete from Grupa_atrybutow where IdGrupa_atrybutow = 2
      
select * from Sesja

insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_tekst)
values (
(select IdAtrybut from Atrybut where Nazwa = 'marker_set'),
2,
'PlugInGait.mkr'
)

insert into Wartosc_atrybutu_sesji (IdAtrybut, IdSesja, Wartosc_liczba)
values (
(select IdAtrybut from Atrybut where Nazwa = 'number_of_trials'),
2,
4
)


select * from Sesja
select * from Wartosc_atrybutu_sesji

insert into Grupa_atrybutow (Nazwa) values ( 'General_performer_attributes' )

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'date_of_birth', 'string', 'performer')

insert into Atrybut ( IdGrupa_atrybutow, Nazwa, Typ_danych, Opisywana_encja)
values ( (select IdGrupa_atrybutow from Grupa_atrybutow where Nazwa = 'General_performer_attributes'), 'diagnosis', 'string', 'performer')    

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


/*
select pvt.IdSesja, [marker_set] as Marker_set
from
(select s.IdSesja, a.Nazwa, was.Wartosc_tekst from Sesja s inner join Wartosc_atrybutu_sesji was on s.IdSesja = was.IdSesja inner join Atrybut a on was.IdAtrybut = a.IdAtrybut) sav
pivot
(
count(Wartosc_tekst)
for Nazwa in ( [marker_set] ) 
) as pvt

select s.IdSesja, a.Nazwa, was.Wartosc_tekst from Sesja s inner join Wartosc_atrybutu_sesji was on s.IdSesja = was.IdSesja inner join Atrybut a on was.IdAtrybut = a.IdAtrybut where s.IdSesja = 2
*/


