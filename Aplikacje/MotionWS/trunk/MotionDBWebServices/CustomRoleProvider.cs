using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Security;
using System.Data;
using System.Data.SqlClient;


namespace MotionDBWebServices
{
    // sample role provider
    class CustomRoleProvider : RoleProvider
    {

        protected SqlConnection conn = null;
        protected SqlCommand cmd = null;

        protected string GetConnectionString()
        {
            return @"server = .; integrated security = true; database = Motion";
        }

        protected void OpenConnection()
        {
            conn = new SqlConnection(GetConnectionString());
            conn.Open();
            cmd = conn.CreateCommand();
        }

        protected void CloseConnection()
        {
            conn.Close();
        }




        public override string[] GetRolesForUser(string username)
        {
            SqlDataReader dr;
            ArrayList alRoles = new ArrayList();

            if (username == null)
            {
                throw new ArgumentNullException();
            }
            try
            {
                OpenConnection();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "get_user_roles";
                cmd.Parameters.Add("@login", SqlDbType.VarChar, 50);

                cmd.Parameters["@login"].Value = username;

                dr = cmd.ExecuteReader();
                while (dr.Read())
                {
                    alRoles.Add( dr[0].ToString() );
                }
                dr.Close();

            }
            catch (SqlException ex)
            {
                throw new Exception("Authentication store access error: "+ex.Message);
            }
            finally
            {
                CloseConnection();
            }

            

            // alRoles.Add("motion_administrators");
            return alRoles.ToArray(typeof(string)) as string[];
        }

        public override bool IsUserInRole(string username, string roleName)
        {
            return GetRolesForUser(username).Contains(roleName);
        }

        #region Not Implemented
        public override void AddUsersToRoles(string[] usernames, string[] roleNames)
        {
            throw new NotImplementedException();
        }

        public override string ApplicationName
        {
            get
            {
                throw new NotImplementedException();
            }
            set
            {
                throw new NotImplementedException();
            }
        }

        public override void CreateRole(string roleName)
        {
            throw new NotImplementedException();
        }

        public override bool DeleteRole(string roleName, bool throwOnPopulatedRole)
        {
            throw new NotImplementedException();
        }

        public override string[] FindUsersInRole(string roleName, string usernameToMatch)
        {
            throw new NotImplementedException();
        }

        public override string[] GetAllRoles()
        {
            throw new NotImplementedException();
        }



        public override string[] GetUsersInRole(string roleName)
        {
            throw new NotImplementedException();
        }



        public override void RemoveUsersFromRoles(string[] usernames, string[] roleNames)
        {
            throw new NotImplementedException();
        }

        public override bool RoleExists(string roleName)
        {
            throw new NotImplementedException();
        }
        #endregion
    }
}