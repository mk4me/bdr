use Motion_Med;
go

alter table Badanie alter column Data	datetime NOT NULL 
go

select * from Typ_badania

insert into Typ_badania (Nazwa) values ( 'Goczałkowice-Endoproteza' )
insert into Typ_badania (Nazwa) values ( 'Goczałkowice-Udar' )
insert into Typ_badania (Nazwa) values ( 'Goczałkowice-Kręgosłup' )
insert into Typ_badania (Nazwa) values ( 'Parkinson' )



