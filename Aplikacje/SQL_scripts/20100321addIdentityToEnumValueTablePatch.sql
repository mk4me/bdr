USE Motion


DROP TABLE Wartosc_wyliczeniowa 
go

 
 CREATE TABLE Wartosc_wyliczeniowa (
        IdWartosc_wyliczeniowa int IDENTITY,
        IdAtrybut            int NOT NULL,
        Wartosc_wyliczeniowa varchar(100) NOT NULL
 )
go

 
 
 ALTER TABLE Wartosc_wyliczeniowa
        ADD FOREIGN KEY (IdAtrybut)
                              REFERENCES Atrybut
go
 
 