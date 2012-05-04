
use Motion;
go


create table Konfiguracja (
		Klucz		varchar(20) NOT NULL UNIQUE,
		Wartosc		varchar(100) NOT NULL,
		Szczegoly	varchar(500)
)
go

CREATE INDEX X1Konfiguracja ON Konfiguracja
 (
         Klucz
 )
go




create procedure get_current_client_version_info 
as
			with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
			select 
				Wartosc as Version,
				Szczegoly as Details
			from Konfiguracja ClientVersionInfo where Klucz='curr_client_ver'
			for XML AUTO
go 


create procedure check_for_newer_client  ( @version varchar(10), @result bit OUTPUT )
as
begin
	set @result = 1;
	if exists ( select Wartosc from Konfiguracja where Klucz = 'curr_client_ver' and Wartosc = @version ) set @result = 0;
	return;
end


exec get_current_client_version_info 
declare @r bit;
exec check_for_newer_client '0.0', @r OUTPUT;
select @r;

insert into Konfiguracja ( Klucz, Wartosc, Szczegoly ) values ( 'curr_client_ver', '0.1', 'Experimental version' )