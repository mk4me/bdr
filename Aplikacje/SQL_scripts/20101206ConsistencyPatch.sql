use Motion;
go

-- last rev. 2010-12-06
alter function user_updateable_sessions( @user_id int )
returns table
as
return
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where (s.Publiczna = 1 and s.PublicznaZapis = 1) or dbo.is_superuser(@user_id)=1)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s where s.IdUzytkownik = @user_id)
union
(select s.IdSesja, s.IdUzytkownik, s.IdLaboratorium, s.IdRodzaj_ruchu, s.Data, s.Nazwa, s.Tagi, s.Opis_sesji, s.Publiczna, s.PublicznaZapis  from Sesja s join Uprawnienia_sesja us on s.IdSesja = us.IdSesja where us.IdUzytkownik = @user_id and us.Zapis = 1 )
go

create procedure if_can_update( @user_login varchar(30), @res_id int, @entity varchar(20), @result int OUTPUT )
as
begin
	if( @entity = 'session')
		set @result = (select count(*) from user_updateable_sessions( dbo.identify_user(@user_login) ) s where s.IdSesja = @res_id);
	else
		set @result = 1;
end;
go


create procedure get_file_data_by_id_xml ( @res_id int )
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select
				IdPlik as FileID,
				Nazwa_pliku as FileName,
				Opis_pliku as FileDescription,
				Sciezka SubdirPath,
				(select * from list_file_attributes ( @res_id ) Attribute FOR XML AUTO, TYPE) as Attributes
			from Plik FileDetailsWithAttributes where IdPlik = @res_id
			for XML AUTO, ELEMENTS
go
