use Motion_Med;
go

alter table Badanie alter column Data	datetime NOT NULL 
go

select * from Typ_badania

insert into Typ_badania (Nazwa) values ( 'Gocza³kowice-Endoproteza' )
insert into Typ_badania (Nazwa) values ( 'Gocza³kowice-Udar' )
insert into Typ_badania (Nazwa) values ( 'Gocza³kowice-Krêgos³up' )
insert into Typ_badania (Nazwa) values ( 'Parkinson' )



