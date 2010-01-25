DROP ENDPOINT basic_queries

GO

CREATE ENDPOINT basic_queries
 STATE = STARTED 
AS HTTP
( PATH = '/BasicQueries',
 AUTHENTICATION = (NTLM ),
 PORTS = ( CLEAR ),
 SITE = 'localhost',
 CLEAR_PORT = 2000 )
FOR SOAP 
( WEBMETHOD 'ListPerformerSessions' (name='Motion.dbo.list_sessions', SCHEMA=STANDARD ),
 WSDL = DEFAULT,
 SCHEMA = STANDARD,
 DATABASE = 'Motion',
 NAMESPACE = 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/NXWS' );
 
 GO