﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Web.Configuration;
using System.Data;

public partial class Overview : System.Web.UI.Page
{
    private List<string> fileColumns = new List<string> {
        "BMT_DBS_Coord", "BMT_DBS_Video", "BMT_O_Coord", "BMT_O_Video", "O_DBS_Coord", "O_DBS_Video", "O_O_Coord", "O_O_Video"
    };

    protected void Page_Load(object sender, EventArgs e)
    {
        //if (!IsPostBack)
        {
            Dictionary<byte, string> appointmentTypes = DatabaseProcedures.getEnumerationByte("Wizyta", "RodzajWizyty");
            List<OverviewSelection> overviewList = getOverviews(appointmentTypes);

            TableHeaderRow header = new TableHeaderRow();
            TableHeaderCell headerCell1 = new TableHeaderCell();
            TableHeaderCell headerCell2 = new TableHeaderCell();
            header.Cells.Add(headerCell1);
            header.Cells.Add(headerCell2);
            
            tableOverviews.Rows.Add(header);
            headerCell1.Text = "Numer pacjenta";
            headerCell2.Text = "Typ wizyty";

            foreach (string column in fileColumns)
            {
                TableHeaderCell headerCellFile = new TableHeaderCell();
                header.Cells.Add(headerCellFile);
                headerCellFile.Text = column;
            }
            
            foreach (OverviewSelection overview in overviewList)
            {
                TableRow row = new TableRow();
                TableCell cell1 = new TableCell();
                TableCell cell2 = new TableCell();
                row.Cells.Add(cell1);
                row.Cells.Add(cell2);
                cell1.Controls.Add(overview.patientNumber);
                cell2.Controls.Add(overview.labelType);

                foreach (string column in fileColumns)
                {
                    TableCell cellFile = new TableCell();
                    cellFile.HorizontalAlign = HorizontalAlign.Center;
                    row.Cells.Add(cellFile);
                    FileLink fileLink = overview.getFileLink(column);
                    if (fileLink != null)
                    {
                        cellFile.Controls.Add(fileLink.link);
                    }
                }

                Utils.colorRow(tableOverviews, row);
                tableOverviews.Rows.Add(row);
            }
        }
    }

    private List<OverviewSelection> getOverviews(Dictionary<byte, string> appointmentTypes)
    {
        SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
        SqlCommand cmd = new SqlCommand();
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.CommandText = "[dbo].[get_database_and_file_overview]";
        cmd.Connection = con;

        List<OverviewSelection> list = new List<OverviewSelection>();

        try
        {
            con.Open();
            SqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                byte typeKey = (byte)rdr["RodzajWizyty"];
                string typeValue = "";
                appointmentTypes.TryGetValue(typeKey, out typeValue);

                List<FileLink> fileLinks = new List<FileLink>();
                foreach (string column in fileColumns)
                {
                    object value = rdr[column];
                    if (value != DBNull.Value)
                    {
                        fileLinks.Add(new FileLink((int)value, column));
                    }
                }
                OverviewSelection overview = new OverviewSelection(
                    (string)rdr["NumerPacjenta"],
                    typeValue,
                    typeKey,
                    fileLinks
                );

                list.Add(overview);
            }
        }
        catch (SqlException ex)
        {
            labelMessage.Text = ex.Message;
        }
        finally
        {
            cmd.Dispose();
            if (con != null)
            {
                con.Close();
            }
        }

        return list;
    }

    private class OverviewSelection
    {
        public Label patientNumber = new Label();
        public decimal typeKey;
        public Label labelType = new Label();
        public List<FileLink> linkList;

        public OverviewSelection(string patientNumber, string typeValue, decimal typeKey, List<FileLink> linkList)
        {
            this.patientNumber.Text = patientNumber;
            labelType.Text = typeValue;
            this.typeKey = typeKey;
            this.linkList = linkList;
        }

        public FileLink getFileLink(string column)
        {
            foreach (FileLink fileLink in linkList)
            {
                if (fileLink.column.Equals(column))
                {
                    return fileLink;
                }
            }

            return null;
        }
    }

    private class FileLink
    {
        public int fileId;
        public string column;
        public LinkButton link = new LinkButton();

        public FileLink(int fileId, string column)
        {
            this.fileId = fileId;
            this.column = column;
            link.Text = "pobierz";
            link.Click += new System.EventHandler(fileLink_Click);
        }

        protected void fileLink_Click(object sender, EventArgs e)
        {
            DataTable file = getFile(fileId);
            DataRow row = file.Rows[0];
            Byte[] data = (Byte[])row["Plik"];
            HttpContext.Current.Response.AddHeader("Content-type", "text/plain");
            HttpContext.Current.Response.AddHeader("Content-Disposition", "attachment; filename=" + getFileName(fileId));
            HttpContext.Current.Response.BinaryWrite(data);
            HttpContext.Current.Response.Flush();
            HttpContext.Current.Response.End();
        }

        private string getFileName(int fileId)
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.Text;
            cmd.CommandText = "select dbo.generate_file_name(@file_id)";
            cmd.Parameters.Add("@file_id", SqlDbType.Int).Value = fileId;
            cmd.Connection = con;

            String fileName = "";

            try
            {
                con.Open();
                object result = cmd.ExecuteScalar();
                if (result != null)
                {
                    fileName = (string)result;
                }

            }
            catch (SqlException ex)
            {
                //labelMessage.Text = ex.Message;
            }
            finally
            {
                cmd.Dispose();
                if (con != null)
                {
                    con.Close();
                }
            }

            return fileName;
        }

        private DataTable getFile(int fileId)
        {
            SqlConnection con = new SqlConnection(WebConfigurationManager.ConnectionStrings[DatabaseProcedures.SERVER].ToString());
            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.Text;
            cmd.CommandText = "select Plik from Plik where IdPlik = @fileId";
            cmd.Parameters.Add("@fileId", SqlDbType.Int).Value = fileId;
            cmd.Connection = con;

            DataTable file = new DataTable();

            try
            {
                con.Open();
                SqlDataAdapter adapter = new SqlDataAdapter();
                adapter.SelectCommand = cmd;
                adapter.Fill(file);
            }
            catch (SqlException ex)
            {
                //labelMessage.Text = ex.Message;
            }
            finally
            {
                cmd.Dispose();
                if (con != null)
                {
                    con.Close();
                }
            }

            return file;
        }
    }

    protected void buttonBack_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/Default.aspx");
    }
}