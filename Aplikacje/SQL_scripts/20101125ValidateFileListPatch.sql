use Motion;
go

alter table Sesja add Nazwa varchar(20) null;
alter table Obserwacja add Nazwa varchar(20) null;

select * from Obserwacja


-- alter: 'dbo.user_accessible_sessions'
-- alter: 'dbo.user_accessible_sessions_by_login'
-- alter: 'dbo.user_updateable_sessions'




create type FileNameListUdt as table
(
	Name varchar(100)
)
go


alter function session_label( @user_login varchar(30), @sess_id int )
returns TABLE as
return
select l.Nazwa+':'+s.Nazwa as SessionLabel
from user_accessible_sessions_by_login(@user_login) s inner join Laboratorium l on s.IdLaboratorium = l.IdLaboratorium where s.IdSesja = @sess_id
go	


declare @sfn varchar(30);
set @sfn = '2010-10-10-P12-S01-T03.c3d';
select SUBSTRING (@sfn, 1, CHARINDEX ('-S', @sfn )-1 )


create procedure validate_file_list_xml (  @files as FileNameListUdt readonly )
as
	declare @errorList table(err varchar(200) );
	declare @sessionName varchar(30);
	set @sessionName = 'Not determined';
	declare @trialNames table(tname varchar(30));
	
/*
	-- Czy lista nie jest pusta?
	if (COUNT(select * from @files where CHARINDEX ('-T', Name ) > 0) = 0 )
		insert @errorList values ( 'No files provided at all or files with trial -T** naming convention not found');
	else
	begin
	  -- Ustalam nazwe zmiennej
	  select top 1 @sessionName = SUBSTRING (Name, 1, CHARINDEX ('-T', Name )-1 ) from @files where CHARINDEX ('-T', Name ) > 0;
	  -- Czy nie ma plikow o niezgodnych nazwach?
	  if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
		begin
			insert into @errorList ( select Name+' name does not start with '+@sessionName from @files where CHARINDEX (@sessionName , Name)=0 )
		end;
	   else
		begin
			if exists( select * from @files where CHARINDEX (@sessionName , Name)=0 )
			insert into @trialNames ( select unique SUBSTRING (Name, 1, PATINDEX ('%.%', Name )-1 ) )
		
		end;

	end;
	

	-- czy dla kazdego trial-a sa po 4 pliki .avi
	-- czy dla kazdego trial-a jest 1 plik .c3d
	-- czy kazdy z pozostalych plikow jest plikiem nazwa-sesji.zip
	-- czy nie ma juz sesji o zadanej nazwie
	*/
	insert into @errorList values ('VALIDATOR NOT YET IMPLEMENTED');
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
		
			select
					0 "SessionDetailsWithAttributes/SessionID",
					0 "SessionDetailsWithAttributes/UserID",
					0 "SessionDetailsWithAttributes/LabID",
					'' "SessionDetailsWithAttributes/MotionKind",
					'2000-01-01 00:00:00.000' "SessionDetailsWithAttributes/SessionDate",
					'' "SessionDetailsWithAttributes/SessionDescription",
					'' "SessionDetailsWithAttributes/SessionLabel",
					'name' "SessionDetailsWithAttributes/Attributes/SessionName"
					,
				(	select 0 "@FileID", 'tu bedzie nazwa' "@FileName", '...' "@FileDescription", '' "@SubdirPath",
					--(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
					from @files p
					for XML PATH('FileDetailsWithAttributes'), TYPE
				) as FileWithAttributesList,
				( 
				   select 
					0 "TrialDetailsWithAttributes/TrialID",
					'...' "TrialDetailsWithAttributes/TrialDescription",
					(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) "TrialDetailsWithAttributes/Attributes",
					(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
						(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
						from Plik p 
						where 
						p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes'), TYPE
					) as FileWithAttributesList
				from @trialNames TrialContent FOR XML PATH('TrialContent'), ELEMENTS, TYPE 
				) as TrialContentList
				for XML Path('SessionContent'), ELEMENTS
go




