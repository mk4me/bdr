 use Motion;
 
 ALTER TABLE Plik ADD IdPerformer int NULL;
 go
 
  ALTER TABLE Plik
        ADD FOREIGN KEY (IdPerformer)
                              REFERENCES Performer
go 
 
 CREATE INDEX XIF57Plik ON Plik
 (
        IdPerformer
 )
go 

 ALTER TABLE Atrybut
        ADD Plugin varchar(100) NULL;
go