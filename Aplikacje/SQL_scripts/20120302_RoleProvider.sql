use Motion;
go
alter procedure get_user_roles @login varchar(30)
as
select gu.Nazwa from Uzytkownik u join Uzytkownik_grupa_uzytkownikow ugu on u.IdUzytkownik = ugu.IdUzytkownik join Grupa_uzytkownikow gu on ugu.IdGrupa_uzytkownikow = gu.IdGrupa_uzytkownikow
where u.Login = @login
go

insert into Grupa_uzytkownikow ( Nazwa ) values ( 'administrators' )

insert into Uzytkownik_grupa_uzytkownikow ( IdGrupa_uzytkownikow, IdUzytkownik ) select gu.IdGrupa_uzytkownikow, u.IdUzytkownik from Uzytkownik u, Grupa_uzytkownikow gu where u.Login = 'habela' and gu.Nazwa = 'administrators'

update Grupa_uzytkownikow set Nazwa = 'operators' where Nazwa = 'Operators'

exec get_user_roles 'applet_user'

select * from Uzytkownik_grupa_uzytkownikow