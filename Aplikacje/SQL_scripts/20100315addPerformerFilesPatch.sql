 -- zdezaktualizowane
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