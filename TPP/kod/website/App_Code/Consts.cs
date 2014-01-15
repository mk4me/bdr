using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for Consts
/// </summary>
public class Consts
{
    // Patient grups
    public static string PATIENT_BMT = "BMT";
    public static string PATIENT_DBS = "DBS";
    public static string PATIENT_POP = "POP";

    // Appointment types (decimal type matches database. ToString() representation depends on server's language setting: . vs ,)
    public static decimal APPOINTMENT_0_0 = 0.0m;
    public static decimal APPOINTMENT_0_5 = 0.5m;
    public static decimal APPOINTMENT_1_0 = 1.0m;
    public static decimal APPOINTMENT_2_0 = 2.0m;
    public static decimal APPOINTMENT_3_0 = 3.0m;
    public static decimal APPOINTMENT_4_0 = 4.0m;
    public static decimal APPOINTMENT_5_0 = 5.0m;

    public static string APPOINTMENT_0_0_text = "przedoperacyjna";

	public Consts()
	{
		//
		// TODO: Add constructor logic here
		//
	}
}