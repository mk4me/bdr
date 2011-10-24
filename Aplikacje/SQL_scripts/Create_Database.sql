CREATE DATABASE Motion 
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'f:\MotionDB\Motion.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'f:\MotionDB\filestream')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'f:\MotionDB\Motionlog.ldf')
GO

CREATE DATABASE Motion_Med
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'f:\MotionDB\Motion_Med.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'f:\MotionDB\filestreamMed')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'f:\MotionDB\Motion_Medlog.ldf')
GO
