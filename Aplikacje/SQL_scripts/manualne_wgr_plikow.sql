use Motion;
go

select * from Plik where Nazwa_pliku like '%xml%'

/*
declare @statement varchar(50);
declare @statement2 varchar(355);
declare @trialname varchar(40);
declare @medusa varchar(20);
declare @filename varchar(60);
declare @sql as nvarchar(2500);

set @medusa = 'usg-medusa22.xml';
set @trialname = '2014-01-03-S0022-T0010';
set @filename = @trialname+'.'+@medusa;

set @statement = quotename('F:/pliki/'+@filename,'''');

set @statement2 = 'insert into Plik ( Nazwa_pliku, Plik, IdProba, Opis_pliku ) select '''+@filename+''', binary_data, IdProba, '''' from openrowset ( bulk '+@statement+', SINGLE_BLOB ) as F(binary_data ), Proba where Nazwa = '''+@trialname+'''';

-- select @statement2;


set @sql = N''+@statement2;

exec sp_executesql @statement = @sql;

select * from Plik where Nazwa_pliku like '%medusa22%'

select * from Proba where Nazwa like '%2013-12-10%'

*/


/*
declare @statement varchar(150);
declare @statement2 varchar(355);
declare @sessname varchar(40);
declare @medusa varchar(20);
declare @filename varchar(60);
declare @sql as nvarchar(2500);

set @sessname = '2014-04-04-S0033';
set @filename = @sessname+'.xml';

set @statement = quotename('F:/pliki/xml/drop/'+@sessname+'/'+@filename,'''');

set @statement2 = 'update Plik set Plik = (select binary_data from openrowset ( bulk '+@statement+', SINGLE_BLOB ) as F(binary_data )) where Nazwa_pliku = '''+@filename+'''';



set @sql = N''+@statement2;

exec sp_executesql @statement = @sql;

select Nazwa_pliku, Ostatnia_zmiana from Plik where Nazwa_pliku = @filename



*/


select IdPlik from Plik where Nazwa_pliku = '2013-09-27-S0010.xml'

-- update Plik set Plik = (select binary_data from openrowset ( bulk 'F:/pliki/2013-09-27-S0010.xml', SINGLE_BLOB ) as F(binary_data )) where Nazwa_pliku = '2013-09-27-S0010.xml';

-- update Plik set Plik = (select binary_data from openrowset ( bulk 'F:/pliki/2014-01-09-S0024.xml', SINGLE_BLOB ) as F(binary_data )) where Nazwa_pliku = '2014-01-09-S0024.xml';

-- select * from dbo.user_accessible_sessions_by_login('usg-medusa01')

-- select * from Performer p join Konfiguracja_performera kp on kp.IdPerformer = p.IdPerformer join Sesja s on s.IdSesja = kp.IdSesja where p.IdPerformer = 9001 order by s.IdSesja

-- insert into Konfiguracja_performera (IdPerformer, IdSesja)  select 9001, IdSesja from Sesja where Nazwa like '%S00%' and IdSesja > 539 order by IdSesja;

