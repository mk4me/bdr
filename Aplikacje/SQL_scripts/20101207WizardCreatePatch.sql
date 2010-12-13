use Motion;
go

update Obserwacja set Nazwa = '' where Nazwa is null
go

alter table Obserwacja alter column Nazwa varchar(30) not null
go



-- to do: recompile all procs and funcs dealing with Obserwacja

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
	  if exists( select * from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)		
		)
		insert into @errorList select ('Trial '+tname+' does not meet the requirement of having 4 .avi files AND 1 .c3d file') 
		from @trialNames tn where 
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.avi', Name ) > 0)<>4)
		or
		((select COUNT (*) from @files where CHARINDEX (tn.tname, Name ) > 0 and CHARINDEX ('.c3d', Name ) > 0)<>1)	

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


