<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="UserAccountCreation.aspx.cs" Inherits="MotionMedDBWebServices.UserAccountCreation" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>HMDB Web Service user account creation page</title>
</head>
<body>
<p style="text-align: center; font-family: sans-serif;"><img
 src="web/logo.png" height="35" alt="Motion project logo" />&nbsp;</p>
 <table
 style="text-align: left; width: 800px; margin-right: auto; background-color: rgb(255, 255, 255); margin-left: auto;"
 border="0" cellpadding="20" cellspacing="2">
  <tbody>

    <tr>
      <td rowspan="1" style="vertical-align: top;">

<div style="font-weight: bold; color: rgb(204, 0, 0);">
<h1>Human Motion Database Medical Module
    <br />
    Web Service user account creation page</h1></div>   <form id="form1" runat="server">
    <div>
    <p> Use this form to register by providing your particulars and choosing login and password.</p>
    
    <p>
        <asp:Label ID="Label1" runat="server" Text="Login"></asp:Label>
        <asp:TextBox ID="tbLogin" runat="server"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" 
            ControlToValidate="tbLogin" 
            ErrorMessage="At least 4 at most 20 alphanumeric characters required for  login. The first char must be a letter." 
            ValidationExpression="[A-Za-z][A-Za-z0-9]{3,19}"></asp:RegularExpressionValidator>
    </p>
    <p>
        <asp:Label ID="Label2" runat="server" Text="Password"></asp:Label>
        <asp:TextBox ID="tbPassword" runat="server" TextMode="Password"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" 
            ControlToValidate="tbPassword" 
            ErrorMessage="Password needs to be 6 to 20 chars long and include uppercase, lowercase and a digit." 
            ValidationExpression="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}"></asp:RegularExpressionValidator>
    </p>
    <p>
        <asp:Label ID="Label3" runat="server" Text="Email"></asp:Label>
        <asp:TextBox ID="tbEmail" runat="server"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator3" runat="server" 
            ControlToValidate="tbEmail" ErrorMessage="Email syntax invalid." 
            ValidationExpression="\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b"></asp:RegularExpressionValidator>
    </p>
    <p>
        <asp:Label ID="Label4" runat="server" Text="First Name"></asp:Label>
        <asp:TextBox ID="tbFirstName" runat="server"></asp:TextBox>
    </p>
    <p>
        <asp:Label ID="Label5" runat="server" Text="Last Name"></asp:Label>
        <asp:TextBox ID="tbLastName" runat="server"></asp:TextBox>
    </p>
        <p>
            <asp:CheckBox ID="cbHMDBAlso" runat="server" Checked="True" 
                Text="Also attempt HMDB account creation" />
    </p>
    <p>
        <asp:Button ID="btnCreateUserAccount" runat="server" 
            Text="Create User Account" onclick="btnCreateUserAccount_Click" />
        </p>
        <asp:ValidationSummary ID="ValidationSummary1" runat="server" />
        <p>
            <asp:Label ID="lblStatus" runat="server" Text="-- awaiting action --"></asp:Label>
        </p>
    <hr style="margin-top: 9px" />
    <p>After receiving the actvation code through your email, visit this page again to input it and confirm your login activation. Note that at this step providing login, password and email is obligatory as well.</p>
    <p>
        <asp:Label ID="Label6" runat="server" Text="Activation code"></asp:Label>
        <asp:TextBox ID="tbActivationCode" runat="server" Enabled="False"></asp:TextBox>
        <asp:CheckBox ID="cbHasCode" runat="server" AutoPostBack="True" 
            oncheckedchanged="cbHasCode_CheckedChanged" 
            Text="I already have the activation code" />
    </p>
        <p>
            <asp:CheckBox ID="cbHMDBAlsoA" runat="server" Checked="True" 
                Text="Also attempt HMDB account activation" />
    </p>
    <p>
        <asp:Button ID="btnActivateUserAccount" runat="server" 
            Text="Activate User Account" onclick="btnActivateUserAccount_Click" />
    </p>
        </div>
    </form>
    </td></tr>
    </tbody></table>
<br clear="all" />

<div style="text-align: center; font-family: sans-serif;">
    <img alt=""
 src="web/bottom.png" /><br />
</div>


</body>
</html>