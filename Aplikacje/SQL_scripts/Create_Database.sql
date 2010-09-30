CREATE DATABASE Motion 
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'f:\MotionDB\Motion.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'f:\MotionDB\filestream')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'f:\MotionDB\Motionlog.ldf')
GO
