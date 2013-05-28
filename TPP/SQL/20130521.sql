use TPP;
go


alter table Pacjent
add NumerPacjenta int not null unique;
go

declare @res int;
declare @kom varchar(200);


exec zapisz_dane_badania 1, 1, 1, 34, 2, 31, 2, 0, 1, 0, 1, 1, 1, 1, 2, 1, 1, 10, null, 0, @kom OUTPUT, @res OUTPUT 

select @res, @kom;

insert into Pacjent ( NumerPacjenta ) values ( 1001 );
go



	insert into Plik  ( IdBadanie, NazwaPliku, OpisPliku, Plik )
	select 1, 'koncepcjaWitrynHumanMotion.png', 'Plik testowy nr 1', binary_data 
	from openrowset ( bulk 'S:\tmp\koncepcjaWitrynHumanMotion.png', SINGLE_BLOB ) as F(binary_data )

	insert into Plik  ( IdBadanie, NazwaPliku, OpisPliku, Plik )
	select 1, 'stopka_PJ_POIG.jpg', 'Plik testowy nr 1', binary_data 
	from openrowset ( bulk 'S:\tmp\stopka_PJ_POIG.jpg', SINGLE_BLOB ) as F(binary_data )