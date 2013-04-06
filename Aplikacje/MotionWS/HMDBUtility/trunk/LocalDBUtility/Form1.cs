using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
//-------------------------
using System.IO;
using System.Xml;
using System.Xml.Serialization;
using System.Data.SqlClient;
using XmlFeeds;

namespace LocalDBUtility
{
    public partial class Form1 : Form
    {

        // database stuff - start
        DatabaseAccess dacc = new DatabaseAccess();

        static string connStr = DatabaseAccess.GetConnectionString();
        SqlDataAdapter daGroups = new SqlDataAdapter("select IdGrupa_sesji as GroupID, Nazwa as GroupName from Grupa_sesji order by Nazwa", connStr);
        SqlDataAdapter daSessions = new SqlDataAdapter("select IdSesja as SessionID, Nazwa as SessionName, dbo.groups_assigned(IdSesja) as GroupsAssgnd, dbo.has_antro(IdSesja) as Antro, dbo.exercises_assigned(IdSesja) as Exercises from Sesja order by IdSesja", connStr);
        
        // database stuff - end

        string xfName = null;
        string csName = null;
        string sdName = null;
        session s = null;

        void ProcessSessionData(session sd)
        {
            string sName = sd.name+"-";


            foreach (sessionTrial st in sd.trial)
            {
                try
                {
                    dacc.SetExerciseNo(sName + st.name, st.ExerciseNo, true);
                }
                catch (UpdateException ue)
                {
                    MessageBox.Show(ue.IssueKind + " error ocurred: " + ue.Message);
                    return;
                }
            }
            lXFilePath.Text = "Exercises for session " + sName + " written!";
        }


        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            if (ofdXmlFile.ShowDialog() == DialogResult.OK)
            {
                xfName = ofdXmlFile.FileName.ToString();
                tbXFilePath.Text = xfName;

            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            lXFilePath.Text = "write attempt...";

            if (xfName != null)
            {
                XmlSerializer xs = new XmlSerializer(typeof(XmlFeeds.session));
                StreamReader sr = new StreamReader(xfName);
                s = (session)xs.Deserialize(sr);

                ProcessSessionData(s);

                SessionListRefresh();
            }

        }

        void SessionListRefresh(){
            dsSessions.Reset();
            daSessions.Fill(dsSessions, "Sesja");
            dgSessions.DataSource = dsSessions.Tables["Sesja"];
            dgSessions.Refresh();

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        
            ofdXmlFile.Title = "Select the XML file";
            ofdXmlFile.Filter = "XML Files|*.xml|All Files|*.*";
            ofdCSFile.Title = "Select the CSV file";
            ofdCSFile.Filter = "CSV Files|*.csv|All Files|*.*";
            ofdXmlFile.InitialDirectory = @"F:\"; // Change it to F: for production!!!
            ofdCSFile.InitialDirectory = @"F:\";
            fbdSessionDir.RootFolder = System.Environment.SpecialFolder.MyComputer;

            try
            {
                daGroups.Fill(dsGroups, "Grupa_sesji");
                dgGroups.DataSource = dsGroups.Tables["Grupa_sesji"];
            }
            catch (Exception ex)
            {
                MessageBox.Show("Session group list refersh error ocurred: " + ex.Message);
            }

            try
            {
                daSessions.Fill(dsSessions, "Sesja");
                dgSessions.DataSource = dsSessions.Tables["Sesja"];
            }
            catch (Exception ex)
            {
                MessageBox.Show("Session list refersh error ocurred: " + ex.Message);
            }



        }

        private void btAssignGrp_Click(object sender, EventArgs e)
        {
            if (dgSessions.SelectedRows.Count != 1)
            {
                MessageBox.Show(dgSessions.SelectedRows.Count + "Please select exactly one Session!");
                return;
            }
            if (dgGroups.SelectedRows.Count != 1)
            {
                MessageBox.Show("Please select exactly one Session group!");
                return;
            }
            dacc.AssignSessionToGroup(int.Parse(dgSessions.SelectedRows[0].Cells[0].Value.ToString()), int.Parse(dgGroups.SelectedRows[0].Cells[0].Value.ToString()));
            SessionListRefresh();
        }

        private void bOpenCSV_Click(object sender, EventArgs e)
        {

            if (ofdCSFile.ShowDialog() == DialogResult.OK)
            {
                csName = ofdCSFile.FileName.ToString();
                tbCSFilePath.Text = csName;

            }
        }

        private void bProcessCSV_Click(object sender, EventArgs e)
        {
            lCFileStatus.Text = "CSV feed attempt...";

            string[] entries = null;

            int counter = 0;

            if (csName != null)
            {

                entries = System.IO.File.ReadAllLines(csName);
                foreach (string sessionAData in entries)
                {
                    try
                    {
                        if (sessionAData.Length > 20 && sessionAData.StartsWith("20"))
                        {
                            dacc.FeedAnthropometricData(sessionAData);
                            counter++;
                        }
                    }
                    catch (UpdateException ue)
                    {
                        MessageBox.Show(ue.IssueKind + " error ocurred: " + ue.Details);
                        return;
                    }



                    //MessageBox.Show( ""+int.Parse(sessionAData.Substring(12, 4))+sessionAData.Substring(17, 3) );
                }
                lCFileStatus.Text = "Antropometric data written for " + counter + " sessions";

                SessionListRefresh();
            }
        }


        private void btSelectSessDir_Click(object sender, EventArgs e)
        {
            if (fbdSessionDir.ShowDialog() == DialogResult.OK)
            {
                sdName = fbdSessionDir.SelectedPath.ToString();
                tbSessDir.Text = sdName;

            }
        }

        private void btProcessSessionDir_Click(object sender, EventArgs e)
        {
            
            string[] entries = null;

            int id = -1;

            if (sdName != null && sdName != "")
            {

                
                    try
                    {
                        id = dacc.CreateSessionFromFiles(sdName);
                    }
                    catch (FileAccessServiceException ue)
                    {
                        MessageBox.Show(ue.IssueKind + " error ocurred: " + ue.Details);
                        return;
                    }



                MessageBox.Show("Session written. Session ID is "+id);

                SessionListRefresh();


            }
        }

        private void dgSessions_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void tbSessDir_TextChanged(object sender, EventArgs e)
        {

        }

        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
