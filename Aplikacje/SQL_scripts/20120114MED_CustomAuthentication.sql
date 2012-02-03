use Motion_Med;
go

alter table Uzytkownik add Haslo varbinary(100) default 0x00 not null;
go

CREATE TABLE Grupa_uzytkownikow (
        IdGrupa_uzytkownikow    int IDENTITY,
        Nazwa					varchar(100) NOT NULL
 )
go

create table Uzytkownik_grupa_uzytkownikow (
	IdGrupa_uzytkownikow	int not null,
	IdUzytkownik	int not null
)
go

alter table Grupa_uzytkownikow
	add primary key (IdGrupa_uzytkownikow)
go

CREATE INDEX X1Uzytkownik_grupa_uzytkownikow ON Uzytkownik_grupa_uzytkownikow
 (
        IdUzytkownik
 )
go

CREATE INDEX X2Uzytkownik_grupa_uzytkownikow ON Uzytkownik_grupa_uzytkownikow
 (
        IdGrupa_uzytkownikow
 )
go

ALTER TABLE Uzytkownik_grupa_uzytkownikow
        ADD FOREIGN KEY (IdUzytkownik)
                              REFERENCES Uzytkownik;
go
 
 
 ALTER TABLE Uzytkownik_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_uzytkownikow)
                              REFERENCES Grupa_uzytkownikow on delete cascade;
go


create table Grupa_badan_grupa_uzytkownikow (
	IdGrupa_uzytkownikow	int not null,
	IdGrupa_badan	int not null
)
go

CREATE INDEX X1Grupa_badan_grupa_uzytkownikow ON Grupa_badan_grupa_uzytkownikow
 (
        IdGrupa_badan
 )
go

CREATE INDEX X2Grupa_badan_grupa_uzytkownikow ON Grupa_badan_grupa_uzytkownikow
 (
        IdGrupa_uzytkownikow
 )
go

ALTER TABLE Grupa_badan_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_badan)
                              REFERENCES Grupa_badan;
go
 
 
 ALTER TABLE Grupa_badan_grupa_uzytkownikow
        ADD FOREIGN KEY (IdGrupa_uzytkownikow)
                              REFERENCES Grupa_uzytkownikow on delete cascade;
go



