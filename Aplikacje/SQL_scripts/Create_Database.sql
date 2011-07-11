CREATE DATABASE Motion 
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'e:\MotionDB\Motion.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'e:\MotionDB\filestream')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'e:\MotionDB\Motionlog.ldf')
GO

CREATE DATABASE Motion_Med
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'e:\MotionDB\Motion_Med.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'e:\MotionDB\filestreamMed')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'e:\MotionDB\Motion_Medlog.ldf')
GO
