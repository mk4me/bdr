
use Motion;
go


delete from Wartosc_atrybutu_obserwacji;
go
delete from Wartosc_atrybutu_performera;
go
delete from Wartosc_atrybutu_pliku;
go
delete from Wartosc_atrybutu_segmentu;
go
delete from Wartosc_atrybutu_sesji;
go
delete from Wartosc_wyliczeniowa;
go
delete from Atrybut;
go
delete from Grupa_atrybutow;
go
alter table Atrybut drop column Opisywana_encja;
go
alter table Grupa_atrybutow add Opisywana_encja varchar(20) not null
go
