use Motion;
go

-- last rev. 2013-10-04
alter procedure validate_file_list_xml (  @files as FileNameListUdt readonly )
as
	declare @errorList table(err varchar(200) );
	declare @sessionName varchar(30);
	set @sessionName = 'Not determined';
	declare @trialNames table(tname varchar(30));
	

	-- Czy lista nie jest pusta?
	if ( (select COUNT(*) from @files where CHARINDEX ('-T', Name ) > 0) = 0 )
		insert @errorList values ( 'No files provided at all or files with trial -T** naming convention not found');
	else
	begin
	  -- Ustalam nazwe sesji
	  select top 1 @sessionName = SUBSTRING (Name, 1, CHARINDEX ('-T', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  -- Czy nie ma plikow o niezgodnych nazwach?
	  if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
			insert @errorList select (Name+' name does not start with '+@sessionName) from @files where CHARINDEX (@sessionName , Name)=0 
	  if exists( select * from Sesja where Nazwa = @sessionName )
			insert into @errorList values ('Session with name '+@sessionName+' already exists in the database!');
	  -- Kompletuje liste triali
	  insert @trialNames  select distinct SUBSTRING (Name, 1, CHARINDEX ('.', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  -- jesli jakakolwiek proba posiada pliki .avi - wymagamy po 4 takowe dla kazdej z nich
	  if exists(select * from @files where CHARINDEX ('.avi', Name ) > 0)
	   and exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)>4
		)
		insert into @errorList select ('Trial '+tname+' does not meet the requirement of having at most 4 .avi files') 
		from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4

      if exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and ( CHARINDEX ('.c3d', Name ) > 0 or CHARINDEX ('.xml', Name ) > 0 ))<>1)		
		insert into @errorList select ('Trial '+tname+' does not meet the requirement of having 1 .c3d file or 1 .xml file') 
		from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and ( CHARINDEX ('.c3d', Name ) > 0 or CHARINDEX ('.xml', Name ) > 0 ))<>1	

	end;
	
/*
	-- czy dla kazdego trial-a sa po 4 pliki .avi
	-- czy dla kazdego trial-a jest 1 plik .c3d
	-- czy kazdy z pozostalych plikow jest plikiem nazwa-sesji.zip
	-- czy nie ma juz sesji o zadanej nazwie
	*/
	-- insert into @errorList values ('VALIDATOR NOT YET IMPLEMENTED' from @trialNames));
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
		select
			(select
					0 "SessionDetailsWithAttributes/SessionID",
					@sessionName "SessionDetailsWithAttributes/SessionName",
					0 "SessionDetailsWithAttributes/UserID",
					0 "SessionDetailsWithAttributes/LabID",
					'' "SessionDetailsWithAttributes/MotionKind",
					'2000-01-01 00:00:00.000' "SessionDetailsWithAttributes/SessionDate",
					'' "SessionDetailsWithAttributes/SessionDescription",
					'' "SessionDetailsWithAttributes/SessionLabel",
					(	
						select	
							a.Nazwa "Name", 
							'' "Value",
							a.Typ_danych "Type",
							ga.Nazwa "AttributeGroup",
							'session' "Entity"
						from Atrybut a 
							inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
						where ga.Opisywana_encja = 'session' and ga.Nazwa <> '_static'				
						FOR XML PATH('Attribute'), TYPE 
					) "SessionDetailsWithAttributes/Attributes",
					(	
						select 
							0 "@FileID", 
							p.Name "@FileName", 
							'...' "@FileDescription", 
							'' "@SubdirPath",
							(
								select
									a.Nazwa "Name", 
									'' "Value",
									a.Typ_danych "Type",
									ga.Nazwa "AttributeGroup",
									'file' "Entity"
								from Atrybut a 
									inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
								where ga.Opisywana_encja = 'file' and ga.Nazwa <> '_static'
								FOR XML PATH('Attribute'), TYPE 
							) "Attributes"
							from @files p where CHARINDEX ('-T', Name ) = 0
							for XML PATH('FileDetailsWithAttributes'), TYPE
					) "FileWithAttributesList",
					( 
						select 
							0 "TrialDetailsWithAttributes/TrialID",
							tname "TrialDetailsWithAttributes/TrialName",
							'...' "TrialDetailsWithAttributes/TrialDescription",
							(
									select
											a.Nazwa "Name", 
											'' "Value",
											a.Typ_danych "Type",
											ga.Nazwa "AttributeGroup",
											'file' "Entity"
										from Atrybut a 
											inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
										where ga.Opisywana_encja = 'trial' and ga.Nazwa <> '_static'
										FOR XML PATH('Attribute'), TYPE 
							) "TrialDetailsWithAttributes/Attributes",
							(	
								select 
									0 "@FileID", 
									p.Name "@FileName", 
									'' "@FileDescription", 
									'' "@SubdirPath",
									(
										select
											a.Nazwa "Name", 
											'' "Value",
											a.Typ_danych "Type",
											ga.Nazwa "AttributeGroup",
											'file' "Entity"
										from Atrybut a 
											inner join Grupa_atrybutow ga on ga.IdGrupa_atrybutow=a.IdGrupa_atrybutow
										where ga.Opisywana_encja = 'file' and ga.Nazwa <> '_static'
										FOR XML PATH('Attribute'), TYPE 
									) "Attributes"
						from @files p where CHARINDEX (tname, Name ) > 0 
						for XML PATH('FileDetailsWithAttributes'), TYPE
					) as FileWithAttributesList
				from @trialNames tc FOR XML PATH('TrialContent'), ELEMENTS, TYPE 
				) "TrialContentList"
				for XML Path('SessionContent'), TYPE, ELEMENTS),
				(select err as Error from @errorList ErrorList FOR XML AUTO, TYPE, ELEMENTS ) 
				for XML PATH('FileSetValidationResult'), ELEMENTS
				
go


-- last rev. 2013-10-04
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
	
	declare @motion_kind varchar(10);
	set @motion_kind = 'walk';

	declare @perf_id int;
	
	declare @trialsToCreate int;
	set @sessionId = 0;
	set @perf_id = 0;

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
			set @result = 2;
			return;
		end;
	  set @sessionDate = CAST ( SUBSTRING(@sessionName,1,10) as Date);
	  
	  -- wykrycie sesji nieposiadajacej wbudowanego numeru performera
	  if CHARINDEX('-B', @sessionName) = 0 and CHARINDEX('-A', @sessionName) = 0 
	  begin
		set @perf_id = -1;
		set @motion_kind = 'n/a';
	  end;

	  -- Okreslam numer performera
	  if @perf_id = 0 set @perf_id = CAST ( SUBSTRING(@sessionName,13,4) as int );
	  
	  -- Czy nie ma plikow o niezgodnych nazwach?
	  if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
		begin
			set @result = 3;
			return;
		end;
	  if exists( select * from Sesja where Nazwa = @sessionName )
		begin
			set @result = 4;
			return;
		end;
	  -- Kompletuje liste triali
	  insert @trialNames  select distinct SUBSTRING (Name, 1, CHARINDEX ('.', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  if exists(select * from @files where CHARINDEX ('.avi', Name ) > 0) and exists( select * from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and (CHARINDEX ('.xml', Name ) > 0  or CHARINDEX ('.c3d', Name ) > 0) )<>1)		
		)
		begin
			set @result = 5;
			return;
		end;

	  if exists( select * from @trialNames tn where 
		(select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and (CHARINDEX ('.xml', Name ) > 0  or CHARINDEX ('.c3d', Name ) > 0) )<>1		
		)
		begin
			set @result = 6;
			return;
		end;
	
		if @perf_id > 0 and not exists ( select * from Performer where IdPerformer = @perf_id )
		begin
			insert into Performer ( IdPerformer ) values ( @perf_id );
		end;
	
	exec create_session  @user_login, 1, @motion_kind, @sessionDate, @sessionName, '', '', @sessionId OUTPUT, @res OUTPUT; 
	
	if (@res<>0) 
		begin
			set @result = 7;
			return;
		end;
		
	if @perf_id > 0
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

insert into Rodzaj_ruchu ( Nazwa ) values ('n/a')
go