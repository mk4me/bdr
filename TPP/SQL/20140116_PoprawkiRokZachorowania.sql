/*
select 
IdWizyta, IdPacjent, RodzajWizyty,
RokZachorowania, 
(select RokZachorowania 
 from Wizyta x where x.IdPacjent = w.IdPacjent 
	and x.RodzajWizyty = (select min(RodzajWizyty) from Wizyta y where y.IdPacjent = w.IdPacjent )) as RokZachorowaniaCalc,
(select dbo.disorder_begin_year(IdWizyta) ) as RokZachorowaniaCalc2 
from Wizyta w order by IdPacjent

update Wizyta set RokZachorowania = dbo.disorder_begin_year(IdWizyta) where RokZachorowania <> dbo.disorder_begin_year(IdWizyta)

select * from Wizyta where RokZachorowania <> dbo.disorder_begin_year(IdWizyta) order by IdPacjent


select * from Wizyta w join Pacjent p on p.IdPacjent = w.IdPacjent where w.IdPacjent in (33, 34, 41)
*/

use TPP;
go



create function disorder_begin_year( @IdWizyta int )
returns smallint
as
begin
	declare @patient_id int;
	select @patient_id = IdPacjent from Wizyta where IdWizyta = @IdWizyta;
	return
		(select RokZachorowania 
		 from Wizyta x where x.IdPacjent = @patient_id 
		and x.RodzajWizyty = (select min(RodzajWizyty) from Wizyta y where y.IdPacjent = @patient_id ))
end
go