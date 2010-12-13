use Motion;
go


-- last rev: 2010-12-08
alter procedure list_session_contents_xml (@user_login varchar(30), @page_size int, @page_no int)
as
	with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
	select
		-- top @page_size ...
		IdSesja as SessionID,
		IdUzytkownik as UserID,
		IdLaboratorium as LabID,
		dbo.motion_kind_name(IdRodzaj_ruchu) as MotionKind,
		Data as SessionDate,
		Nazwa as SessionName,
		Tagi as Tags, 
		Opis_sesji as SessionDescription,
		(select * from session_label(@user_login, IdSesja)) as SessionLabel,
		(select * from list_session_attributes ( IdSesja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(	select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
	(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
	from Plik p where p.IdSesja=SessionContent.IdSesja
	for XML PATH('FileDetailsWithAttributes'), TYPE) as FileWithAttributesList,
	(select 
		IdObserwacja as TrialID,
		Nazwa as TrialName,
		Opis_obserwacji as TrialDescription,
		(select * from list_trial_attributes ( IdObserwacja ) Attribute FOR XML AUTO, TYPE ) as Attributes,
		(select p.IdPlik "@FileID", p.Nazwa_pliku "@FileName", p.Opis_pliku "@FileDescription", p.Sciezka "@SubdirPath",
		(select * from list_file_attributes ( IdPlik ) Attribute FOR XML AUTO, TYPE ) as Attributes
		from Plik p 
		where 
		p.IdObserwacja=TrialContent.IdObserwacja for XML PATH('FileDetailsWithAttributes'), TYPE) as FileWithAttributesList
	from Obserwacja TrialContent where TrialContent.IdSesja = SessionContent.IdSesja FOR XML AUTO, ELEMENTS, TYPE ) as TrialContentList
	from user_accessible_sessions_by_login(@user_login) SessionContent
      for XML AUTO, ELEMENTS, root ('SessionContentList')
go

-- last rev: 2010-12-08
alter procedure set_file_attribute (@file_id int, @attr_name varchar(100), @attr_value varchar(100), @update bit, @result int OUTPUT )
as
begin
	declare @attr_id as int, @attr_type as varchar(100), @attr_enum as bit;
	declare @integer_value numeric(10,2), @float_value float, @id_value int;
	declare @value_tuple_found as bit = 0;	

	/*
	Current static non-id attributes:
		'FileName', 'string', 0, 'shortString'
		'FileDescription', 'string', 0, 'longString'
		'SubdirPath', 'string', 0, 'shortString'
	*/

	set @result = 6; -- result 3 = type casting error

	if(@attr_name = 'FileName' or @attr_name = 'FileDescription' or @attr_name = 'SubdirPath' )
	begin
		if(@update = 0)
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;	
		if(@attr_name = 'FileName')
			update Plik set Nazwa_pliku  = @attr_value where IdPlik = @file_id;
		else if(@attr_name = 'Sciezka')
			update Plik set Sciezka  =  @attr_value where IdPlik = @file_id;
		else if(@attr_name = 'SubdirPath')
			update Plik set Opis_pliku  = @attr_value where IdPlik = @file_id;
		set @result = 0;
		return;
	end;	
	select top(1) @attr_id = IdAtrybut, @attr_type = Typ_danych, @attr_enum = Wyliczeniowy 
		from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow=ga.IdGrupa_atrybutow where a.Nazwa = @attr_name and ga.Opisywana_encja =  'file';
	if @@rowcount = 0 
	begin
		set @result = 1 -- result 1 = attribute of this name not applicable here
		return;
	end;
	if not exists ( select * from Plik where IdPlik = @file_id )
		begin
			set @result = 3;
			return;
		end;
	else
		begin
			if exists ( select * from Wartosc_atrybutu_pliku where IdAtrybut = @attr_id and IdPlik = @file_id ) set @value_tuple_found = 1;
			if @update = 0 and @value_tuple_found = 1
				begin
					set @result = 5; -- result 5 = value exists while update has not been allowed
					return;
				end;
			if(@attr_enum = 1) 
			begin
				select top(1) Wartosc_wyliczeniowa from Wartosc_wyliczeniowa where Wartosc_wyliczeniowa=@attr_value ;
				if @@rowcount = 0 begin set @result = 2; return; end; -- result 2 = illegal enum attribute value
			end;
			if @attr_type = 'integer'
				begin
					set @integer_value = cast ( @attr_value as numeric(10,2) );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pliku set Wartosc_liczba  = @integer_value where IdAtrybut = @attr_id and IdPlik = @file_id ;
					else
					insert into Wartosc_atrybutu_pliku (IdAtrybut, IdPlik, Wartosc_liczba) values (@attr_id, @file_id, @integer_value);
				end;
			
			else if @attr_type = 'float'
				begin
					set @float_value = cast ( @attr_value as float );
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pliku set Wartosc_zmiennoprzecinkowa  = @float_value where IdAtrybut = @attr_id and IdPlik = @file_id ;
					else
					insert into Wartosc_atrybutu_pliku (IdAtrybut, IdPlik, Wartosc_zmiennoprzecinkowa) values (@attr_id, @file_id, @float_value);
				end;
			else
				begin
					if ( @value_tuple_found = 1 )
					update Wartosc_atrybutu_pliku set Wartosc_tekst  = @attr_value where IdAtrybut = @attr_id and IdPlik = @file_id ;
					else
					insert into Wartosc_atrybutu_pliku (IdAtrybut, IdPlik, Wartosc_tekst) values (@attr_id, @file_id, @attr_value);
				end;
			set @result = 0;
		end;
end;
go





