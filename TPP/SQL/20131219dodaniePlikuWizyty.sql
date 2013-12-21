use TPP;
go

alter table Plik alter column IdBadanie int null;
go

alter table Plik add IdWizyta int null;
go

CREATE INDEX X2Plik ON Plik (
	IdWizyta
)
go

alter table Plik
	add foreign key ( IdWizyta )
		references Wizyta;
go