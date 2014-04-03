use Motion;
go

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

