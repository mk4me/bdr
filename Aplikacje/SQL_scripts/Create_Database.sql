/*
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
*/

CREATE DATABASE Motion_test
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'f:\MotionDB\Motion_test.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'f:\MotionDB\filestream_test')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'f:\MotionDB\Motionlog_test.ldf')
GO

CREATE DATABASE Motion_Med_test
ON
PRIMARY ( NAME = Arch1,
    FILENAME = 'f:\MotionDB\Motion_Med_test.mdf'),
FILEGROUP FileStreamGroup1 CONTAINS FILESTREAM( NAME = MotionFS,
    FILENAME = 'f:\MotionDB\filestreamMed_test')
LOG ON  ( NAME = Archlog1,
    FILENAME = 'f:\MotionDB\Motion_Medlog_test.ldf')
GO