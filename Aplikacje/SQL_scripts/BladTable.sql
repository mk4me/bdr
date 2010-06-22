

BEGIN TRY
    -- Generate a divide-by-zero error.
    SELECT 1/0;
END TRY
BEGIN CATCH
    insert into Blad ( NrBledu, Dotkliwosc, Stan, Procedura, Linia, Komunikat )
    values ( ERROR_NUMBER() , ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE() )
END CATCH;
GO



BEGIN TRY
    -- Generate a divide-by-zero error.
    SELECT 1/0;
END TRY
BEGIN CATCH
    insert into Blad ( TekstBledu ) 
    values (' Message: '+ERROR_MESSAGE() )
END CATCH;


use Motion;

drop table Blad

 CREATE TABLE Blad (
        IdBlad         int IDENTITY,
        NrBledu		int,
        Dotkliwosc	int,
        Stan		int,
        Procedura	nvarchar(100),
        Linia		int,
        Komunikat  nvarchar(500)
 )
 
 select * from Blad