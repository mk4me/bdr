delete from Plik where IdBadanie in 
(select b.IdBadanie from Pacjent p join Wizyta w on w.IdPacjent = p.IdPacjent join Badanie b on b.IdWizyta = w.IdWizyta
where p.NumerPacjenta in ('15/PD/DBS/2010','27/PD/DBS/2014','34/PD/DBS/2012','37/PD/DBS/2012','41/PD/DBS/2012','42/PD/DBS/2012') )


delete from Badanie where IdWizyta in 
(select w.IdWizyta from Pacjent p join Wizyta w on w.IdPacjent = p.IdPacjent join Badanie b on b.IdWizyta = w.IdWizyta
where p.NumerPacjenta in ('15/PD/DBS/2010','27/PD/DBS/2014','34/PD/DBS/2012','37/PD/DBS/2012','41/PD/DBS/2012','42/PD/DBS/2012') )

delete from Pacjent
where NumerPacjenta in ('15/PD/DBS/2010','27/PD/DBS/2014','34/PD/DBS/2012','37/PD/DBS/2012','41/PD/DBS/2012','42/PD/DBS/2012')

