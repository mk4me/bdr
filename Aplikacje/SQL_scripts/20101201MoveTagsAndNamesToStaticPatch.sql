use Motion;
go

update Sesja set Nazwa = 
(select was.Wartosc_tekst from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'SessionName' and was.IdSesja = Sesja.IdSesja)
where IdSesja in (select was.IdSesja from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'SessionName')
update Sesja set Nazwa = '-' where Nazwa is null;
go
delete was from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'SessionName'
go

update Sesja set Tagi = 
(select was.Wartosc_tekst from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'Tags' and was.IdSesja = Sesja.IdSesja)
where IdSesja in (select was.IdSesja from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'Tags')
update Sesja set Tagi = '' where Tagi is null;
go
delete was from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'Tags'
go

update Obserwacja set Nazwa = 
(select wao.Wartosc_tekst from Wartosc_atrybutu_obserwacji wao join Atrybut a on wao.IdAtrybut = a.IdAtrybut and a.Nazwa = 'TrialName' and wao.IdObserwacja = Obserwacja.IdObserwacja)
where IdSesja in (select was.IdSesja from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'Tags')
update Sesja set Tagi = '' where Tagi is null;
go
delete was from Wartosc_atrybutu_sesji was join Atrybut a on was.IdAtrybut = a.IdAtrybut and a.Nazwa = 'Tags'
go


