select a.Nazwa from Atrybut a join Grupa_atrybutow ga on a.IdGrupa_atrybutow = ga.IdGrupa_atrybutow where ga.Opisywana_encja = 'performer_conf' 

use Motion;
go

create procedure feed_anthropometric_data(
	@pid int,
	@sname varchar(4),
	@BodyMass decimal (5, 2),
	@Height int,
	@InterAsisDistance int,
	@LeftLegLength int,
	@RightLegLenght int,
	@LeftKneeWidth int,
	@RightKneeWidth int,
	@LeftAnkleWidth int,
	@RightAnkleWidth int,
	@LeftCircuitThigh int,
	@RightCircuitThight int,
	@LeftCircuitShank int,
	@RightCircuitShank int,
	@LeftShoulderOffset int,
	@RightShoulderOffset int,
	@LeftElbowWidth int,
	@RightElbowWidth int,
	@LeftWristWidth int,
	@RightWristWidth int,
	@LeftWristThickness int,
	@RightWristThickness int,
	@LeftHandWidth int,
	@RightHandWidth int,
	@LeftHandThickness int,
	@RightHandThickness int,
	@result int OUTPUT )
as
begin
	/* Error codes:
		1 = attribute of this name not applicable here
		3 = attribute owning instance not found
		5 = value exists while update has not been allowed
		6 = value type casting error
		7 = file-valued attribute: invalid file ID
		11 = invalid format of the session label = S99 pattern expected
	*/	
	declare @pc_id int;
	declare @sid int;
	set @result = 0;
	
	  if( ISNUMERIC ( SUBSTRING(@sname,2,2))<>1)
		begin
			set @result = 11;
			return;
		end;
	set @sid = CAST ( SUBSTRING(@sname,2,2) as int);
	
	select @pc_id = kp.IdKonfiguracja_performera from
	 Konfiguracja_performera kp join Sesja s on kp.IdSesja = s.IdSesja where kp.IdPerformer = @pid and CHARINDEX (@sname, s.Nazwa ) > 0;
	exec set_performer_conf_attribute @pc_id, 'BodyMass', @BodyMass, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'Height', @Height, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'InterAsisDistance', @InterAsisDistance, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftLegLength', @LeftLegLength, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightLegLenght', @RightLegLenght, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftKneeWidth', @LeftKneeWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightKneeWidth', @RightKneeWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftAnkleWidth', @LeftAnkleWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightAnkleWidth', @RightAnkleWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftCircuitThigh', @LeftCircuitThigh, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightCircuitThight', @RightCircuitThight, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftCircuitShank', @LeftCircuitShank, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightCircuitShank', @RightCircuitShank, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftShoulderOffset', @LeftShoulderOffset, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightShoulderOffset', @RightShoulderOffset, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftElbowWidth', @LeftElbowWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightElbowWidth', @RightElbowWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftWristWidth', @LeftWristWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightWristWidth', @RightWristWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftWristThickness', @LeftWristThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightWristThickness', @RightWristThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftHandWidth', @LeftHandWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightHandWidth', @RightHandWidth, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'LeftHandThickness', @LeftHandThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
	exec set_performer_conf_attribute @pc_id, 'RightHandThickness', @RightHandThickness, 0, @result OUTPUT;
	if( @result <> 0 ) return;
end;

-- ==============

--declare @res int;

--exec feed_anthropometric_data 1 , 'S01' , 84.84 , 1580 , 260 , 810 , 810 , 130 , 130 , 85 , 85 , 0 , 0 , 0 , 0 , 35 , 35 , 76 , 76 , 60 , 60 , 45 , 45 , 84 , 84 , 30 , 30, @res OUTPUT;   

--select @res;

--select * from Konfiguracja_performera where IdPerformer = 1 and IdSesja = 23;
--select IdKonfiguracja_performera from Konfiguracja_performera where IdPerformer = 5 and IdSesja = (select top 1 IdSesja from Sesja where CHARINDEX ('S01', Nazwa ) > 0 );

--select * from Wartosc_atrybutu_konfiguracji_performera

