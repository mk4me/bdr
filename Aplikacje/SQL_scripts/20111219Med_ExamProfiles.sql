use Motion_Med;
go


create table Kontekst_badania (
        IdKontekst_badania    int IDENTITY,
        Nazwa					varchar(100) NOT NULL
 )
go

create table Badanie_kontekst_badania (
	IdKontekst_badania	int not null,
	IdBadanie	int not null,
	Rola	varchar(50)
)
go


alter table Kontekst_badania
	add primary key (IdKontekst_badania)
go

CREATE INDEX X1Badanie_kontekst_badania ON Badanie_kontekst_badania
 (
        IdKontekst_badania
 )
go

CREATE INDEX X2Badanie_kontekst_badania ON Badanie_kontekst_badania
 (
        IdBadanie
 )
go

ALTER TABLE Badanie_kontekst_badania
        ADD FOREIGN KEY (IdKontekst_badania)
                              REFERENCES Kontekst_badania;
go
 
 
 ALTER TABLE Badanie_kontekst_badania
        ADD FOREIGN KEY (IdBadanie)
                              REFERENCES Badanie on delete cascade;
go

