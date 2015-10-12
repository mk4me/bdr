use TPP;
go

alter table Plik
	add PodRodzajPliku varchar(2);
go	
/*
insert into Plik (IdBadanie, OpisPliku, Plik, NazwaPliku) values ( 28, '1', 0x35, 'UPDRS4.txt');
select top 100 * from Plik
select OpisPliku, UPPER(SUBSTRING(OpisPliku,7,1)) from Plik where OpisPliku like '%Video-%' order by OpisPliku 
update Plik set PodRodzajPliku = UPPER(SUBSTRING(OpisPliku,7,1)) from Plik where OpisPliku like '%Video-%'
*/

alter table Plik
	add NazwaEksportowaPliku varchar(50);
go
	
update Plik set NazwaEksportowaPliku = dbo.generate_file_name(IdPlik);
go

create trigger tr_Plik_Update on Plik
for update, insert
as
begin
	update Plik
	set NazwaEksportowaPliku = dbo.generate_file_name(p.IdPlik)
	from inserted i join Plik p on i.IdPlik = p.IdPlik
end
go

-- updated: 2015-10-12
alter procedure get_database_and_file_overview
as
select 
	   P.[NumerPacjenta]
      ,W.[RodzajWizyty]
      ,max(PBDC.IdPlik) as BMT_DBS_Coord
     , STUFF((SELECT N';' + CAST(p.IdPlik AS VARCHAR(MAX)) +':' + p.PodRodzajPliku
				FROM Plik p join Badanie x on p.IdBadanie = x.IdBadanie and x.BMT = 1 and x.DBS = 1 and p.OpisPliku like '%Video%'
				WHERE x.IdWizyta = W.IdWizyta
				FOR XML PATH(N'')), 1, 1, N'') as BMT_DBS_Video
      ,max(PBDTE.IdPlik) as BMT_DBS_ET_Excel
      ,max(PBDTG.IdPlik) as BMT_DBS_ET_Graph
      
      ,max(PBC.IdPlik) as BMT_O_Coord
      , STUFF((SELECT N';' + CAST(p.IdPlik AS VARCHAR(MAX)) +':' + p.PodRodzajPliku
				FROM Plik p join Badanie x on p.IdBadanie = x.IdBadanie and x.BMT = 1 and x.DBS = 0 and p.OpisPliku like '%Video%'
				WHERE x.IdWizyta = W.IdWizyta
				FOR XML PATH(N'')), 1, 1, N'') as BMT_O_Video
      ,max(PBTE.IdPlik) as BMT_O_ET_Excel
      ,max(PBTG.IdPlik) as BMT_O_ET_Graph

      ,max(PDC.IdPlik) as O_DBS_Coord
      , STUFF((SELECT N';' + CAST(p.IdPlik AS VARCHAR(MAX)) +':' + p.PodRodzajPliku
				FROM Plik p join Badanie x on p.IdBadanie = x.IdBadanie and x.BMT = 0 and x.DBS = 1 and p.OpisPliku like '%Video%'
				WHERE x.IdWizyta = W.IdWizyta
				FOR XML PATH(N'')), 1, 1, N'') as O_DBS_Video
      ,max(PDTE.IdPlik) as O_DBS_ET_Excel
      ,max(PDTG.IdPlik) as O_DBS_ET_Graph

      ,max(PC.IdPlik) as O_O_Coord
      , STUFF((SELECT N';' + CAST(p.IdPlik AS VARCHAR(MAX)) +':' + p.PodRodzajPliku
				FROM Plik p join Badanie x on p.IdBadanie = x.IdBadanie and x.BMT = 0 and x.DBS = 0 and p.OpisPliku like '%Video%'
				WHERE x.IdWizyta = W.IdWizyta
				FOR XML PATH(N'')), 1, 1, N'') as O_O_Video
      ,max(PTE.IdPlik) as O_O_ET_Excel
      ,max(PTG.IdPlik) as O_O_ET_Graph

      
  from
  Pacjent P join Wizyta w on P.IdPacjent = W.IdPacjent join Badanie B on B.IdWizyta = W.IdWizyta
  
  left join Plik PBDC on B.IdBadanie = PBDC.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDC.OpisPliku = 'Coordinates'
  left join Plik PBDV on B.IdBadanie = PBDV.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDV.OpisPliku = 'Video' --
  left join Plik PBDTE on B.IdBadanie = PBDTE.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PBDTG on B.IdBadanie = PBDTG.IdBadanie and B.BMT = 1 and B.DBS = 1 and PBDTG.OpisPliku = 'EyeTrackingGraph'
  
  left join Plik PBC on B.IdBadanie = PBC.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBC.OpisPliku = 'Coordinates'
  left join Plik PBV on B.IdBadanie = PBV.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBV.OpisPliku = 'Video'
  left join Plik PBTE on B.IdBadanie = PBTE.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PBTG on B.IdBadanie = PBTG.IdBadanie and B.BMT = 1 and B.DBS = 0 and PBTG.OpisPliku = 'EyeTrackingGraph'
  
  left join Plik PDC on B.IdBadanie = PDC.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDC.OpisPliku = 'Coordinates'
  left join Plik PDV on B.IdBadanie = PDV.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDV.OpisPliku = 'Video'
  left join Plik PDTE on B.IdBadanie = PDTE.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PDTG on B.IdBadanie = PDTG.IdBadanie and B.BMT = 0 and B.DBS = 1 and PDTG.OpisPliku = 'EyeTrackingGraph'
  
  left join Plik PC on B.IdBadanie = PC.IdBadanie and B.BMT = 0 and B.DBS = 0 and PC.OpisPliku = 'Coordinates'
  left join Plik PV on B.IdBadanie = PV.IdBadanie and B.BMT = 0 and B.DBS = 0 and PV.OpisPliku = 'Video' 
  left join Plik PTE on B.IdBadanie = PTE.IdBadanie and B.BMT = 0 and B.DBS = 0 and PTE.OpisPliku = 'EyeTrackingExcel'
  left join Plik PTG on B.IdBadanie = PTG.IdBadanie and B.BMT = 0 and B.DBS = 0 and PTG.OpisPliku = 'EyeTrackingGraph' 		
  group by P.NumerPacjenta, W.RodzajWizyty, W.IdWizyta	
  order by P.NumerPacjenta, W.RodzajWizyty
  go
  

-- updated: 2015-09-11
alter function generate_file_name( @file_id int )
returns varchar(80)
as
begin
declare @file_name varchar(80);
declare @bmt bit;
declare @dbs bit;
declare @step varchar(3);
declare @patient_no varchar(14);
declare @file_type varchar(100);

select 
	@file_name = p.NazwaPliku, 
	@bmt = b.BMT, 
	@dbs = b.DBS, 
	@step = w.RodzajWizyty, 
	@patient_no = pa.NumerPacjenta, 
	@file_type = p.OpisPliku
	from Plik p join Badanie b on b.IdBadanie = p.IdBadanie join Wizyta w on w.IdWizyta = b.IdWizyta join Pacjent pa on pa.IdPacjent = w.IdPacjent
	where p.IdPlik = @file_id;
	
	set @file_name = rtrim(right(@file_name, charindex('.', reverse(@file_name))));
	
	if @file_type = 'Coordinates' set @file_name = '_C'+@file_name;
	if @file_type like '%Video%' 
		begin 
			if @file_type like '%desktop%' set @file_name = '_D'+@file_name;
			if @file_type like '%laptop%' set @file_name = '_L'+@file_name;
			if @file_type like '%tablet%' set @file_name = '_T'+@file_name;
			if @file_type like '%jazznovo%' set @file_name = '_J'+@file_name;
			set @file_name = '_V'+@file_name;
		end;
	if @file_type = 'EyeTrackingExcel' set @file_name = '_ETe'+@file_name;
	if @file_type = 'EyeTrackingGraph' set @file_name = '_ETg'+@file_name;
	
	if @dbs = 1 set @file_name = '-DBS'+@file_name; else set @file_name = '-O'+@file_name;
	if @bmt = 1 set @file_name = '-BMT'+@file_name; else set @file_name = '-O'+@file_name;
	
	set @file_name = '-' + @step + @file_name;
	
	set @patient_no = replace( @patient_no, '/', '_');
	set @file_name = @patient_no+@file_name;
	return @file_name;
end
go 