use Motion;
go

-- insert into Konfiguracja_pomiarowa ( Nazwa, Rodzaj, Opis ) values ('Medusa1', 'usg', 'Measurement configuration for Medusa USG data and annotations')

select * from Sesja
select * from Konfiguracja_pomiarowa

insert into Sesja_Konfiguracja_pomiarowa ( IdKonfiguracja_pomiarowa, IdSesja) select 6, IdSesja from Sesja where IdSesja > 345