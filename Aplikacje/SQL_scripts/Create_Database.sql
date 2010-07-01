CREATE DATABASE Motion 
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'c:\MotionDB\Motion.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'c:\MotionDB\filestream')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'c:\MotionDB\Motionlog.ldf')
GO
