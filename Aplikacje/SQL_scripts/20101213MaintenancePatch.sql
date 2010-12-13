use Motion;
go


alter procedure remove_basket ( @user_login as varchar(30), @basket_name as varchar(30), @result int OUTPUT)
as
begin
	declare @user_id int;
	declare @basket_id int;
	set @user_id = dbo.identify_user(@user_login);

	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	select @basket_id = IdKoszyk from Koszyk where IdUzytkownik = @user_id and Nazwa = @basket_name;
	if @basket_id is NULL
	begin
		set @result = 2;
		return;
	end;
	
	delete from Performer_Koszyk where IdKoszyk = @basket_id;
	delete from Sesja_Koszyk where IdKoszyk = @basket_id;
	delete from Obserwacja_Koszyk where IdKoszyk = @basket_id;
	delete from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	set @result = 0;
end
go

alter procedure add_entity_to_basket( @user_login as varchar(30), @basket_name as varchar(30), @entity as varchar(20), @res_id as int, @result int OUTPUT )
as
begin
	declare @user_id int;
	declare @basket_id int;
	set @user_id = dbo.identify_user(@user_login);

	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	select @basket_id = IdKoszyk from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	
	if (@basket_id is NULL )
	begin 
		set @result = 2;
		return;
	end;	
	if @entity = 'performer'
		begin
			if not exists ( select * from Performer_Koszyk where IdPerformer = @res_id and IdKoszyk = @basket_id )
			insert into Performer_Koszyk ( IdKoszyk, IdPerformer ) values ( @basket_id, @res_id );
		end;
	else if @entity = 'session'
		begin
			if @res_id not in (select IdSesja from dbo.user_accessible_sessions(@user_id))
				begin
					set @result = 3;
					return;
				end
			if not exists ( select * from Sesja_Koszyk where IdSesja = @res_id and IdKoszyk = @basket_id )
			insert into Sesja_Koszyk ( IdKoszyk, IdSesja ) values ( @basket_id, @res_id );
		end;
	else if @entity = 'trial'
		begin

			if (select top 1 IdSesja from Obserwacja where IdObserwacja = @res_id) not in (select IdSesja from dbo.user_accessible_sessions(@user_id))
				begin
					set @result = 3;
					return;
				end
			if not exists ( select * from Obserwacja_Koszyk where IdObserwacja = @res_id and IdKoszyk = @basket_id )
			insert into Obserwacja_Koszyk ( IdKoszyk, IdObserwacja ) values ( @basket_id, @res_id );
		end;
	else
		begin
			set @result = 7;
			return;
		end;
	set @result = 0;
end
go

alter procedure remove_entity_from_basket( @user_login as varchar(30), @basket_name as varchar(30), @entity as varchar(20), @res_id as int, @result int OUTPUT )
as
begin
	declare @user_id int;
	declare @basket_id int;
	
	set @user_id = dbo.identify_user(@user_login);
	if @user_id is NULL 
	begin 
		set @result = 1;
		return;
	end;
	
	select @basket_id = IdKoszyk from Koszyk where Nazwa = @basket_name and IdUzytkownik = @user_id;
	
	if (@basket_id is NULL )
	begin 
		set @result = 2;
		return;
	end;	
	if @entity = 'performer'
		delete from Performer_Koszyk where IdKoszyk = @basket_id and IdPerformer = @res_id;
	else if @entity = 'session'
		delete from Sesja_Koszyk where IdKoszyk = @basket_id and IdSesja = @res_id;
	else if @entity = 'trial'
		delete from Obserwacja_Koszyk where IdKoszyk = @basket_id and IdObserwacja = @res_id;
	else
		begin
			set @result = 7;
			return;
		end;
	set @result = 0;
end
go