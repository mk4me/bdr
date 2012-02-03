use Motion;
go

alter Table Grupa_sesji_grupa_uzytkownikow add Zapis bit default 0 not null;
go
alter Table Grupa_sesji_grupa_uzytkownikow add Wlasciciel bit default 0 not null;
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

-- last rev: 2012-01-14
create function user_group_writable_session_ids( @user_id int )
returns table
as
return
select sgs.IdSesja from Uzytkownik_grupa_uzytkownikow ugu
join Grupa_uzytkownikow gu on ugu.IdGrupa_uzytkownikow = gu.IdGrupa_uzytkownikow
join Grupa_sesji_grupa_uzytkownikow gsgu on gu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
join Grupa_sesji gs on gsgu.IdGrupa_sesji = gs.IdGrupa_sesji
join Sesja_grupa_sesji sgs on gs.IdGrupa_Sesji = sgs.IdGrupa_sesji
where ugu.IdUzytkownik = @user_id and gsgu.Zapis =1 ;
go

-- last rev: 2012-01-14
create function user_group_owned_session_ids( @user_id int )
returns table
as
return
select sgs.IdSesja from Uzytkownik_grupa_uzytkownikow ugu
join Grupa_uzytkownikow gu on ugu.IdGrupa_uzytkownikow = gu.IdGrupa_uzytkownikow
join Grupa_sesji_grupa_uzytkownikow gsgu on gu.IdGrupa_uzytkownikow = gsgu.IdGrupa_uzytkownikow
join Grupa_sesji gs on gsgu.IdGrupa_sesji = gs.IdGrupa_sesji
join Sesja_grupa_sesji sgs on gs.IdGrupa_Sesji = sgs.IdGrupa_sesji
where ugu.IdUzytkownik = @user_id and gsgu.Wlasciciel =1 ;
go




-- last rev: 2012-01-14 ----
alter function user_accessible_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana from Sesja s where s.Publiczna = 1 or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana from Sesja s 
 where s.IdSesja in ( select * from user_group_assigned_session_ids( @user_id) ) )  
go

-- last rev. 2011-01-14
alter function user_updateable_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where (s.Publiczna = 1 and s.PublicznaZapis = 1) or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id and us.Zapis = 1 )
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s 
 where s.IdSesja in ( select * from user_group_writable_session_ids( @user_id) ) )  
go

-- last rev. 2011-01-14
create function user_owned_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis from Sesja s 
 where s.IdSesja in ( select * from user_group_owned_session_ids( @user_id) ) )  
go


-- last rev: 2010-10-24
create function user_accessible_sessions_by_login( @user_login varchar(30) )
returns table
as
return
select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis, s.Ostatnia_zmiana from user_accessible_sessions( dbo.identify_user( @user_login )) s
go


-- last rev. 2012-01-14
alter procedure create_session_from_file_list ( @user_login as varchar(30), @files as FileNameListUdt readonly, @result int output )
as
	set @result = 0;
	declare @fileStoreList table(fname varchar(100), resid int, entity varchar(20) );
	declare @sessionName varchar(30);
	declare @sessionDate DateTime;
	declare @trialNames table(tname varchar(30));
	declare @trialName varchar(30);
	
	declare @sessionId int;
	declare @trialId int;
	declare @labId int;
	declare @res int;
	
	declare @perf_id int;
	
	declare @trialsToCreate int;
	set @sessionId = 0;

	 select top(1) @labId=IdLaboratorium from Laboratorium;
	

	-- Czy lista nie jest pusta?
	if ( (select COUNT(*) from @files where CHARINDEX ('-T', Name ) > 0) = 0 )
		begin
			set @result = 1;
			return;
		end;

	  -- Ustalam nazwe sesji
	  select top 1 @sessionName = SUBSTRING (Name, 1, CHARINDEX ('-T', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  if( ISDATE ( SUBSTRING(@sessionName,1,10))<>1)
		begin
			set @result = 1;
			return;
		end;
	  set @sessionDate = CAST ( SUBSTRING(@sessionName,1,10) as Date);
	  
	  -- Okreslam numer performera
	  set @perf_id = CAST ( SUBSTRING(@sessionName,13,4) as int );
	  
	  -- Czy nie ma plikow o niezgodnych nazwach?
	  if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
		begin
			set @result = 1;
			return;
		end;
	  if exists( select * from Sesja where Nazwa = @sessionName )
		begin
			set @result = 1;
			return;
		end;
	  -- Kompletuje liste triali
	  insert @trialNames  select distinct SUBSTRING (Name, 1, CHARINDEX ('.', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  if exists(select * from @files where CHARINDEX ('.avi', Name ) > 0) and exists( select * from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)		
		)
		begin
			set @result = 1;
			return;
		end;

	  if exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1		
		)
		begin
			set @result = 1;
			return;
		end;
	
		if not exists ( select * from Performer where IdPerformer = @perf_id )
		begin
			insert into Performer ( IdPerformer ) values ( @perf_id );
		end;
	
	exec create_session  @user_login, 1, 'walk', @sessionDate, @sessionName, '', '', @sessionId OUTPUT, @res OUTPUT; 
	
	if (@res<>0) 
		begin
			set @result = 1;
			return;
		end;
		
	insert into Konfiguracja_performera ( IdPerformer, IdSesja ) values (@perf_id, @sessionId );
	-- po przetestowaniu zamien wykomentowania gora-dol
	-- set @sessionId = 1;
									
	insert @fileStoreList ( fname, entity, resid ) select Name, 'session', @sessionId from @files f where ( CHARINDEX ('-T', f.Name)=0 );
	
	select @trialsToCreate = COUNT(*) from @trialNames;
	
	while @trialsToCreate > 0
		begin
			select top(1) @trialName = tn.tname from @trialNames tn where not exists ( select * from @fileStoreList fsl where ( CHARINDEX(tn.tname,fsl.fname)>0  ));

			
			insert into Proba ( IdSesja, Opis_proby, Nazwa) values (@sessionId, '', @trialName ) set @trialId = SCOPE_IDENTITY();
			-- po przetestowaniu zamien wykomentowania gora-dol
			-- set @trialId = @trialsToCreate;
            insert @fileStoreList ( fname, entity, resid ) select Name, 'trial', @trialId from @files f where ( CHARINDEX (@trialName, f.Name)>0 );
			set @trialsToCreate = @trialsToCreate-1;						
		end;

	select * from @fileStoreList order by entity;				
go


