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
    public static byte APPOINTMENT_0 = 0;
    public static byte APPOINTMENT_6 = 6;
    public static byte APPOINTMENT_12 = 12;
    public static byte APPOINTMENT_18 = 18;
    public static byte APPOINTMENT_24 = 24;
    public static byte APPOINTMENT_30 = 30;
    public static byte APPOINTMENT_36 = 36;
    public static byte APPOINTMENT_42 = 42;
    public static byte APPOINTMENT_48 = 48;
    public static byte APPOINTMENT_54 = 54;
    public static byte APPOINTMENT_60 = 60;
    
    public static string APPOINTMENT_0_text = "przedoperacyjna";

	public Consts()
	{
		//
		// TODO: Add constructor logic here
		//
	}
}