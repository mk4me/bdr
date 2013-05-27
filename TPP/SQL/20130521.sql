
alter table Pacjent
add NumerPacjenta int not null unique;
go

insert into Pacjent ( NumerPacjenta ) values ( 1001 );
go



	insert into Plik  ( IdBadanie, NazwaPliku, OpisPliku, Plik )
	select 1, 'koncepcjaWitrynHumanMotion.png', 'Plik testowy nr 1', binary_data 
	from openrowset ( bulk 'S:\tmp\koncepcjaWitrynHumanMotion.png', SINGLE_BLOB ) as F(binary_data )

	insert into Plik  ( IdBadanie, NazwaPliku, OpisPliku, Plik )
	select 1, 'stopka_PJ_POIG.jpg', 'Plik testowy nr 1', binary_data 
	from openrowset ( bulk 'S:\tmp\stopka_PJ_POIG.jpg', SINGLE_BLOB ) as F(binary_data )