use Motion_Med;
go

alter table Badanie alter column Data	datetime NOT NULL 
go

select * from Typ_badania

insert into Typ_badania (Nazwa) values ( 'Gocza�kowice-Endoproteza' )
insert into Typ_badania (Nazwa) values ( 'Gocza�kowice-Udar' )
insert into Typ_badania (Nazwa) values ( 'Gocza�kowice-Kr�gos�up' )
insert into Typ_badania (Nazwa) values ( 'Parkinson' )



