use Motion;


create table Predykat
(
	IdPredykat int not null,
	IdRodzicPredykat int not null,
	EncjaKontekst varchar(20) not null,
	IdPoprzedniPredykat int not null,
	NastepnyOperator varchar(5) not null,
	NazwaWlasciwosci varchar(100) not null,
	Operator varchar(5) not null,
	Wartosc varchar(100) not null,
	FunkcjaAgregujaca varchar(10) not null,
	EncjaAgregowana varchar(20) not null,
	Nazwa varchar(20) not null,
	IdUzytkownik int not null
)
go

 ALTER TABLE Predykat
        ADD FOREIGN KEY (IdUzytkownik) REFERENCES Uzytkownik
go

ALTER TABLE Predykat
		ADD PRIMARY KEY (IdUzytkownik, IdPredykat)
go

-- drop index XIFPredykat on Predykat
-- go

 CREATE INDEX XIFPredykat ON Predykat
 (
        IdUzytkownik
 )
go