use Motion_test;



create table Plik_usuniety (
        IdPlik            int NOT NULL,
        IdUzytkownik	int NOT NULL,
        DataUsuniecia	datetime not null
 )
go

create index X1Plik_usuniety on Plik_usuniety
 (
        IdPlik
 )
go

create index X2Plik_usuniety on Plik_usuniety
 (
        IdUzytkownik
 )
go

create index X3Plik_usuniety on Plik_usuniety
 (
        DataUsuniecia
 )
go

select * from Plik

insert into Plik ( IdSesja, Nazwa_pliku, Opis_pliku, Plik) values (2, 'Hello', 'opis', 0xFE)

select * from Plik_Usuniety

select * from Sesja s join Proba p on p.IdSesja = s.IdSesja

select * from Uzytkownik

select * from Sesja_grupa_sesji

select * from Grupa_sesji

insert into Sesja_grupa_sesji (IdGrupa_sesji, IdSesja) values (1, 2)

insert into  Grupa_sesji_grupa_uzytkownikow ( IdGrupa_sesji, IdGrupa_uzytkownikow ) values ( 1, 1)

select * from Grupa_uzytkownikow

select * from Uzytkownik_grupa_uzytkownikow ugu join Grupa_uzytkownikow	gu on gu.IdGrupa_uzytkownikow = ugu.IdGrupa_uzytkownikow

insert into Uzytkownik_grupa_uzytkownikow (IdGrupa_uzytkownikow, IdUzytkownik ) values (1, 2)

delete from Plik where IdPlik > 10

declare @file_id int;
set @file_id = 8;
select @file_id, s.IdUzytkownik, getdate() from Plik p 
	join Proba pr on pr.IdProba = p.IdProba join Sesja s on s.IdSesja = pr.IdSesja where p.IdPlik = @file_id or s.Publiczna = 1
union
select @file_id, s.IdUzytkownik, getdate() from Plik p join Sesja s on s.IdSesja = p.IdSesja where p.IdPlik = @file_id or s.Publiczna = 1
union
select @file_id, u.IdUzytkownik, getdate() from Plik p join Proba pr on pr.IdProba = p.IdProba join Sesja s on s.IdSesja = pr.IdSesja 
join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik
where p.IdPlik = @file_id
union
select @file_id, u.IdUzytkownik, getdate() from Plik p join Sesja s on s.IdSesja = p.IdSesja 
join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik
where p.IdPlik = @file_id

	declare @date datetime;
	set @date = getdate();
declare @file_id int;
set @file_id = 8;
	insert into PlikUsuniety ( IdPlik, IdUzytkownik, DataUsuniecia)
	select @file_id, s.IdUzytkownik, @date from Plik p join Proba pr on pr.IdProba = p.IdProba join Sesja s on s.IdSesja = pr.IdSesja where p.IdPlik = @file_id or s.Publiczna = 1
	union
	select @file_id, s.IdUzytkownik, @date from Plik p join Sesja s on s.IdSesja = p.IdSesja where p.IdPlik = @file_id or s.Publiczna = 1
	union
	select @file_id, u.IdUzytkownik, @date from Plik p join Proba pr on pr.IdProba = p.IdProba join Sesja s on s.IdSesja = pr.IdSesja 
	join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
	join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
	join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
	join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik
	where p.IdPlik = @file_id
	union
	select @file_id, u.IdUzytkownik, @date from Plik p join Sesja s on s.IdSesja = p.IdSesja 
	join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
	join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
	join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
	join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik
	where p.IdPlik = @file_id	
go;

alter trigger tr_Plik_Delete on Plik
for delete
as
begin
	declare @date datetime;
	set @date = getdate();

	insert into Plik_usuniety ( IdPlik, IdUzytkownik, DataUsuniecia)
	(
	select p.IdPlik, s.IdUzytkownik, @date from deleted p 
		join Proba pr on pr.IdProba = p.IdProba join Sesja s on s.IdSesja = pr.IdSesja  -- plik Triala z sesji autorstwa danego Uzytkownika
	union
	select p.IdPlik, u.IdUzytkownik, @date from deleted p  -- plik Triala z sesji publiczniej => wszyscy uzytkownicy
		join Proba pr on pr.IdProba = p.IdProba join Sesja s on s.IdSesja = pr.IdSesja, Uzytkownik u where s.Publiczna = 1 
	union
	select p.IdPlik, s.IdUzytkownik, @date from deleted p -- plik Sesji z sesji autorstwa danego Uzytkownika
		join Sesja s on s.IdSesja = p.IdSesja
	union
	select p.IdPlik, u.IdUzytkownik, @date from deleted p  -- plik Sesji z sesji publiczniej => wszyscy uzytkownicy
		join Sesja s on s.IdSesja = p.IdSesja, Uzytkownik u where s.Publiczna = 1   
	union
	select p.IdPlik, u.IdUzytkownik, @date from deleted p -- plik Sesji z uzytkownikami uprawnionymi poprzez powiazania uprawniajace swoich GrupUzytkownikow z GrupamiSesji
		join Sesja s on s.IdSesja = p.IdSesja
		join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
		join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
		join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
		join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik
	union
	select p.IdPlik, u.IdUzytkownik, @date from deleted p -- plik Triala z uzytkownikami uprawnionymi poprzez powiazania uprawniajace swoich GrupUzytkownikow z GrupamiSesji
		join Proba pr on pr.IdProba = p.IdProba
		join Sesja s on s.IdSesja = pr.IdSesja 
		join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
		join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
		join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
		join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik	
	)
end
go



-- last rev: 2012-01-14
create function user_group_assigned_session_ids( @user_id int )
returns table
as
return
select sgs.IdSesja from Uzytkownik_grupa_uzytkownikow ugu
join Grupa_uzytkownikow gu on ugu.IdGrupa_uzytkownikow = gu.IdGrupa_uzytkownikow
join Grupa_sesji_grupa_uzytkownikow gsgu on gu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
join Grupa_sesji gs on gsgu.IdGrupa_sesji = gs.IdGrupa_sesji
join Sesja_grupa_sesji sgs on gs.IdGrupa_Sesji = sgs.IdGrupa_sesji
where ugu.IdUzytkownik = @user_id;
go

create function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s where s.Publiczna = 1 or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana, s.Utworzono from Sesja s 
 where s.IdSesja in ( select * from user_group_assigned_session_ids( @user_id) ) )  
go




/*


	tryger on delete Plik
	
Tabela ProbaUsunieta
	IdUzytkownik, IdProba, DateTime

	tryger on delete Proba

Tabela SesjaUsunieta
	IdUzytkownik, IdSesja, DateTime

	tryger on delete 

Tabela	PrzyznaniePrawa
	IdUzytkownik, IdSesja, DateTime

Tabela	OdebraniePrawa
	IdUzytkownik, IdSesja, DateTime

	tryger on delete GrupaSesji GrupaUzytkownikow

	tryger on delete Uzytkownik GrupaUzytkownikow

	tryger on delete Sesja GrupaSesji

	tryger on --> sprawdziæ inne kryteria


*/