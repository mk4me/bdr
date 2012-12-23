use Motion;
go

update Obserwacja set Nazwa = 
(select wao.Wartosc_tekst from Wartosc_atrybutu_obserwacji wao join Atrybut a on wao.IdAtrybut = a.IdAtrybut and a.Nazwa = 'TrialName' and wao.IdObserwacja = Obserwacja.IdObserwacja)
where IdObserwacja in (select wao.IdObserwacja from Wartosc_atrybutu_obserwacji wao join Atrybut a on wao.IdAtrybut = a.IdAtrybut and a.Nazwa = 'TrialName')
go
delete from Wartosc_atrybutu_obserwacji where IdAtrybut in (select IdAtrybut from Atrybut where Nazwa='TrialName')
go

