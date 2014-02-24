use Motion_test;
go



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




create table Adnotacja (
	IdUzytkownik int NOT NULL,
	IdProba int NOT NULL,
	Status tinyint NOT NULL default 1,
	Komentarz varchar(200),
	Uwagi varchar(500),
	IdOceniajacy int
)

 alter table Adnotacja
        ADD FOREIGN KEY (IdUzytkownik)
                   REFERENCES Uzytkownik on delete cascade;
go

 alter table Adnotacja
        ADD FOREIGN KEY (IdProba)
                   REFERENCES Proba on delete cascade;
go


 alter table Adnotacja
        add primary key (IdUzytkownik, IdProba)
 go
 

 
 
 alter table Grupa_sesji_Grupa_uzytkownikow
	add
	Adnotuje bit NOT NULL default 0,
	Weryfikuje_adnotacje bit NOT NULL default 0;

go


/*
Annotation statuses:
	0 - rejected. requires corrections
	1 - in construction (initial state)
	2 - ready for review
	3 - in review
	4 - approved
	
Procedure error codes:
	0	-	OK
	1	-	trial not found
	2	-	status change not allowed
	3	-	invalid status code ( only 1-2 switching is allowed)
	11	-	user not found
	
*/


select * from Proba

declare @r int;
exec set_my_annotation_status 1, 2, 'Comment', 'habela', @r OUTPUT;
select @r;
	

select * from Uzytkownik u join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdUzytkownik = u.IdUzytkownik
join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_uzytkownikow = ugu.IdGrupa_uzytkownikow
join Sesja_grupa_sesji sgs on sgs.IdGrupa_sesji = gsgu.IdGrupa_sesji

select * from Grupa_sesji_grupa_uzytkownikow
update Grupa_sesji_grupa_uzytkownikow set Weryfikuje_adnotacje = 1


create procedure set_my_annotation_status ( @trial_id int, @status tinyint, @comment varchar(200), @user_login varchar(30), @result int OUTPUT)
as
begin
	declare @user_id int;
	
	set @result = 0;
	
	declare @previous_status tinyint;
	
	set @result = 0;
	
	if ( @status <> 1 and @status <> 2)
	begin
		set @result = 3;
		return;
	end;
	
	
	set @user_id = dbo.identify_user( @user_login );
	
	if(@user_id is NULL)
	begin
		set @result = 11;
		return;
	end;
	
	if( not exists(select IdProba from Proba where IdProba = @trial_id ))
	begin
		set @result = 1;
		return;
	end;
	
	
	select @previous_status = Status from Adnotacja where IdProba = @trial_id and IdUzytkownik = @user_id ;
	
	if ( @previous_status is null )
	begin
		insert into Adnotacja ( IdProba, IdUzytkownik, Status, Komentarz ) values (@trial_id, @user_id, @status, @comment );	
	end
	else
	begin
		if ( @previous_status <> 0 and @previous_status <> 1)
		begin
			set @result =2;
			return;	
		end;
	
		update Adnotacja set  Status = @status, Komentarz = @comment where IdProba = @trial_id and IdUzytkownik = @user_id;
	end;
	
end;
go

delete from Adnotacja

declare @r int;
exec review_annotation 1, 1, 3, 'Comment', 'dpisko', @r OUTPUT;
select @r;

/*
Annotation statuses:
	0 - rejected. requires corrections
	1 - in construction (initial state)
	2 - ready for review
	3 - in review
	4 - approved
	
Procedure error codes:
	0	-	OK
	1	-	annotation not found
	2	-	status change not allowed
	3	-	invalid status code ( only 0, 3, 4 values are allowed)
	11	-	reviewer not found
	12	-	user not found
	
*/
create procedure review_annotation ( @trial_id int, @user_id int, @status tinyint, @note varchar(500), @reviewer_login varchar(30), @result int OUTPUT)
as
begin
	declare @reviewer_id int;
	set @result = 0;
	
	declare @previous_status tinyint;
	
	set @result = 0;
	
	if ( @status <> 0 and @status <> 3 and @status <> 4)
	begin
		set @result = 3;
		return;
	end;
	
	
	set @reviewer_id = dbo.identify_user( @reviewer_login );
	
	if(@reviewer_id is NULL)
	begin
		set @result = 11;
		return;
	end;
		
	select @previous_status = Status from Adnotacja where IdProba = @trial_id and IdUzytkownik = @user_id ;
	
	if ( @previous_status is null )
	begin
		set @result = 1;
		return;
	end
	else
	begin
		if ( @previous_status = 2 )
		begin
			if( @status = 3 and exists ( select * from dbo.user_reviewable_annotations (@reviewer_id) where IdProba = @trial_id and IdUzytkownik = @user_id ))
			begin
				update Adnotacja set IdOceniajacy = @reviewer_id, Status = 3, Uwagi = @note where IdUzytkownik = @user_id and IdProba = @trial_id;
			end
			else
			begin
				set @result = 2;
				return;	
			end;
		end
		else
		if ( @previous_status = 3 )
		begin
			if(@status <> 4 and @status <> 0)
			begin
				set @result = 2;
				return;	
			end
			else update Adnotacja set IdOceniajacy = NULL, Status = @status, Uwagi = @note where IdUzytkownik = @user_id and IdProba = @trial_id;
		end
		else
		begin
			set @result = 2;
			return;	
		end		
	end;
	
	
end;
go



-- created 2014-02-24
create procedure list_authors_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdProba as TrialID,
	IdUzytkownik as UserID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from Adnotacja Annotation
	where IdUzytkownik = dbo.identify_user(@user_login)
    for XML AUTO, ELEMENTS, root ('UserAnnotations')
go

-- created 2014-02-24
alter function user_reviewable_annotations( @user_id int )
returns TABLE as
return 
select a.IdUzytkownik, a.IdProba, a.Status, a.Komentarz, a.Uwagi from Adnotacja a 
	join Proba p on a.IdProba = p.IdProba join Sesja s on s.IdSesja = p.IdSesja join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
	join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow where ugu.IdUzytkownik = @user_id and a.Status = 2 and gsgu.Weryfikuje_adnotacje = 1;
go



-- created 2014-02-24
create procedure list_awaiting_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from dbo.user_reviewable_annotations( dbo.identify_user (@user_login)) Annotation
	where Annotation.status = 3
    for XML AUTO, ELEMENTS, root ('AwaitingAnnotations')
go


-- created 2014-02-24
create procedure list_reviewers_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from Adnotacja Annotation
	where IdOceniajacy = dbo.identify_user(@user_login) and Status = 3
    for XML AUTO, ELEMENTS, root ('UserAnnotations')
go





/*
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


*/

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