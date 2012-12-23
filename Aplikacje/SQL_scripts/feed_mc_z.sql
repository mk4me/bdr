
        use Motion;
        go
        declare @res int;
        
         exec feed_measurement_conf '2010-10-03-B0036-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2010-10-03-B0036-S01', 'LB_1';

        
         exec feed_measurement_conf '2010-10-03-B0036-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2010-10-03-B0036-S02', 'UB_1';

        
         exec feed_measurement_conf '2010-10-06-B0036-S03', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2010-10-06-B0036-S03', 'LB_1';

        
         exec feed_measurement_conf '2010-12-16-B0035-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2010-12-16-B0035-S01', 'LB_1';

        
         exec feed_measurement_conf '2010-12-16-B0035-S02', 'emg', '', @res OUTPUT;
            select @res, '2010-12-16-B0035-S02', '';

        
         exec feed_measurement_conf '2010-12-29-B0037-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2010-12-29-B0037-S01', 'LB_1';

        
         exec feed_measurement_conf '2010-12-29-B0037-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2010-12-29-B0037-S02', 'UB_1';

        
         exec feed_measurement_conf '2011-01-04-B0036-S04', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2011-01-04-B0036-S04', 'LB_1';

        
         exec feed_measurement_conf '2011-02-03-B0038-S01', 'emg', 'LB_2', @res OUTPUT;
            select @res, '2011-02-03-B0038-S01', 'LB_2';

        
         exec feed_measurement_conf '2011-02-03-B0038-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2011-02-03-B0038-S02', 'UB_1';

        
         exec feed_measurement_conf '2011-02-18-B0039-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2011-02-18-B0039-S01', 'LB_1';

        
         exec feed_measurement_conf '2011-02-18-B0039-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2011-02-18-B0039-S02', 'UB_1';

        
         exec feed_measurement_conf '2011-02-22-B0040-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2011-02-22-B0040-S01', 'LB_1';

        
         exec feed_measurement_conf '2011-02-22-B0040-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2011-02-22-B0040-S02', 'UB_1';

        
         exec feed_measurement_conf '2011-02-22-B0041-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2011-02-22-B0041-S01', 'LB_1';

        
         exec feed_measurement_conf '2011-02-22-B0041-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2011-02-22-B0041-S02', 'UB_1';

        
         exec feed_measurement_conf '2011-03-02-B0042-S01', 'emg', 'LB_3', @res OUTPUT;
            select @res, '2011-03-02-B0042-S01', 'LB_3';

        
         exec feed_measurement_conf '2011-03-02-B0042-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2011-03-02-B0042-S02', 'UB_1';

        
         exec feed_measurement_conf '2011-03-02-B0043-S01', 'emg', 'LB_1', @res OUTPUT;
            select @res, '2011-03-02-B0043-S01', 'LB_1';

        
         exec feed_measurement_conf '2011-03-02-B0043-S02', 'emg', 'UB_1', @res OUTPUT;
            select @res, '2011-03-02-B0043-S02', 'UB_1';

        