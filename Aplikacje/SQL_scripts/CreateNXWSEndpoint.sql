DROP ENDPOINT basic_queries

GO

CREATE ENDPOINT basic_queries
 STATE = STARTED 
AS HTTP
( PATH = '/NXWS/BasicQueries',
 AUTHENTICATION = (NTLM ),
 PORTS = ( CLEAR ),
 SITE = 'dbpawell',
 CLEAR_PORT = 2000 )
FOR SOAP 
( WEBMETHOD 'ListPerformerSessions' (name='Motion.dbo.list_sessions', SCHEMA=STANDARD ),
 WSDL = DEFAULT,
 SCHEMA = STANDARD,
 DATABASE = 'Motion',
 NAMESPACE = 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/NXWS' );
 
 GO
 
 -- nizej starszy przyklad od Pawla:
 
 DROP ENDPOINT test_ws

GO
 
CREATE ENDPOINT test_ws
STATE = STARTED
AS HTTP
(
SITE = 'dbpawell',
PATH = '/test',
AUTHENTICATION = ( INTEGRATED ),
PORTS = ( CLEAR )
)
FOR SOAP
(
WEBMETHOD 'test_wm'
( NAME = '[motion].[dbo].[test_ws]',
SCHEMA = STANDARD
),
WSDL = DEFAULT,
BATCHES = DISABLED,
DATABASE = 'Motion'
);
GO
