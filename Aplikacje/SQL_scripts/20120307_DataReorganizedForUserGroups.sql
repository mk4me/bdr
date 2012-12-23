use Motion;
go


select * from Grupa_sesji gs join Sesja_grupa_sesji sgs on gs.IdGrupa_sesji = sgs.IdGrupa_sesji

insert into Grupa_uzytkownikow values ( 'motion_project')

insert into Grupa_sesji_grupa_uzytkownikow ( IdGrupa_sesji, IdGrupa_uzytkownikow, Wlasciciel, Zapis) values ( 1, 3, 0, 0 )

update Sesja set Publiczna = 0






