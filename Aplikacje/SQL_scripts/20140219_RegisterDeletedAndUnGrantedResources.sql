use Motion;
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

select * from Proba
	

select * from Uzytkownik u join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdUzytkownik = u.IdUzytkownik
join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_uzytkownikow = ugu.IdGrupa_uzytkownikow
join Sesja_grupa_sesji sgs on sgs.IdGrupa_sesji = gsgu.IdGrupa_sesji


update Grupa_sesji_grupa_uzytkownikow set Adnotuje = 1 where IdGrupa_sesji = 7
update Grupa_sesji_grupa_uzytkownikow set Weryfikuje_adnotacje = 1 

!!! to do: dodac i uprawnic grupe dla recenzetow adnotacji


*/



select * from Grupa_sesji gs join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = gs.IdGrupa_sesji join Grupa_uzytkownikow gu on gu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
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
-- last rev. 2014-02-27
create procedure set_my_annotation_status ( @trial_id int, @status tinyint, @comment varchar(200), @user_login varchar(30), @result int OUTPUT)
as
begin
	declare @user_id int;
	
	set @result = 0;
	
	declare @previous_status tinyint;
	
	set @result = 0;
	
	if ( @status <> 1 and @status <> 2)	-- annotator can only change the status from 1 (in construction) and 0 - rejected.
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
		if ( @previous_status <> 0 and @previous_status <> 1)	-- annotator may change the status only if its current value is 0 - rejected or 1 - in construction
		begin
			set @result =2;
			return;	
		end;
	
		update Adnotacja set  Status = @status, Komentarz = CASE @comment WHEN '' THEN Komentarz ELSE @comment END where IdProba = @trial_id and IdUzytkownik = @user_id;
	end;
	
end;
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
	1	-	annotation not found
	2	-	status change not allowed
	3	-	invalid status code ( only 0, 3, 4 values are allowed)
	4	-	decision provided while annotation not in the "in review" state
	11	-	reviewer not found
	12	-	user not found
	
*/
create procedure review_annotation ( @trial_id int, @user_id int, @status tinyint, @note varchar(500), @reviewer_login varchar(30), @result int OUTPUT)
as
begin
	declare @reviewer_id int;
	declare @annotation_locker_id int;
	set @result = 0;
	
	declare @previous_status tinyint;
	
	set @result = 0;
	
	if ( @status <> 0 and @status <> 3 and @status <> 4) -- reviewer may only switch state to 3 (in review) or into one of the review results (0 or 4)
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
		
	select @previous_status = Status, @annotation_locker_id = IdOceniajacy from Adnotacja where IdProba = @trial_id and IdUzytkownik = @user_id ;	-- check the curent status of the annotation
	
	if ( @previous_status is null )	-- annotation not found
	begin
		set @result = 1;
		return;
	end
	else
	begin
		if ( @previous_status = 2 )
		begin
			if( @status = 3 and exists ( select * from dbo.user_reviewable_annotations (@reviewer_id) where IdProba = @trial_id and IdUzytkownik = @user_id )) -- annotation ready to review and reviewer has necessary privileges
			begin
				update Adnotacja set IdOceniajacy = @reviewer_id, Status = 3, Uwagi = CASE @note WHEN '' THEN Uwagi ELSE @note END where IdUzytkownik = @user_id and IdProba = @trial_id;
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
			if( (@annotation_locker_id is null) or (@annotation_locker_id <> @reviewer_id) )
			begin
				set @result = 4;
				return;
			end;
			if(@status <> 4 and @status <> 0)  -- review result expected - is the new status a valid grade (0 or 4)?
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
create function user_reviewable_annotations( @user_id int )
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
    for XML AUTO, ELEMENTS, root ('ReviewedAnnotations')
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


create trigger tr_Plik_Delete on Plik
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



create table Proba_usunieta (
        IdProba            int NOT NULL,
        IdUzytkownik	int NOT NULL,
        DataUsuniecia	datetime not null
 )
go

create index X1Proba_usunieta on Proba_usunieta
 (
        IdProba
 )
go

create index X2Proba_usunieta on Proba_usunieta
 (
        IdUzytkownik
 )
go

create index X3Proba_usunieta on Proba_usunieta
 (
        DataUsuniecia
 )
go




create trigger tr_Proba_Delete on Proba
for delete
as
begin
	declare @date datetime;
	set @date = getdate();

	insert into Proba_usunieta ( IdProba, IdUzytkownik, DataUsuniecia)
	(
	select p.IdProba, s.IdUzytkownik, @date from deleted p 
		join Sesja s on s.IdSesja = p.IdSesja  --  Trial z sesji autorstwa danego Uzytkownika
	union
	select p.IdProba, u.IdUzytkownik, @date from deleted p  -- Trial z sesji publiczniej => wszyscy uzytkownicy
		join Sesja s on s.IdSesja = p.IdSesja, Uzytkownik u where s.Publiczna = 1 
	union
	select p.IdProba, u.IdUzytkownik, @date from deleted p -- Triala z uzytkownikami uprawnionymi poprzez powiazania uprawniajace swoich GrupUzytkownikow z GrupamiSesji
		join Sesja s on s.IdSesja = p.IdSesja 
		join Sesja_grupa_sesji sgs on sgs.IdSesja = s.IdSesja
		join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
		join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
		join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik	
	)
end
go





create table Sesja_usunieta (
        IdSesja            int NOT NULL,
        IdUzytkownik	int NOT NULL,
        DataUsuniecia	datetime not null
 )
go

create index X1Sesja_usunieta on Sesja_usunieta
 (
        IdSesja
 )
go

create index X2Sesja_usunieta on Sesja_usunieta
 (
        IdUzytkownik
 )
go

create index X3Sesja_usunieta on Sesja_usunieta
 (
        DataUsuniecia
 )
go



create trigger tr_Sesja_Delete on Sesja
for delete
as
begin
	declare @date datetime;
	set @date = getdate();

	insert into Sesja_usunieta ( IdSesja, IdUzytkownik, DataUsuniecia)
	(
	select p.IdSesja, p.IdUzytkownik, @date from deleted p 
		--  Sesja autorstwa danego Uzytkownika
	union
	select p.IdSesja, u.IdUzytkownik, @date from deleted p  -- Sesja publiczna => wszyscy uzytkownicy
		, Uzytkownik u where p.Publiczna = 1 
	union
	select p.IdSesja, u.IdUzytkownik, @date from deleted p -- Sesja z uzytkownikami uprawnionymi poprzez powiazania uprawniajace swoich GrupUzytkownikow z GrupamiSesji
		join Sesja_grupa_sesji sgs on sgs.IdSesja = p.IdSesja
		join Grupa_sesji_grupa_uzytkownikow gsgu on gsgu.IdGrupa_sesji = sgs.IdGrupa_sesji
		join Uzytkownik_grupa_uzytkownikow ugu on ugu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
		join Uzytkownik u on u.IdUzytkownik = ugu.IdUzytkownik	
	)
end
go


/*


Tabela	OdebraniePrawa
	IdUzytkownik, IdSesja, DateTime

	tryger on delete GrupaSesji GrupaUzytkownikow

	tryger on delete Uzytkownik GrupaUzytkownikow

	tryger on delete Sesja GrupaSesji

	tryger on --> sprawdziæ inne kryteria


*/