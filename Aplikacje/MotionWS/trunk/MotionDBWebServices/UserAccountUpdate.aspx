<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="UserAccountUpdate.aspx.cs" Inherits="MotionDBWebServices.UserAccountUpdate" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Human Motion Database user account update page</title>
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
<h1>Human Motion Database user account update page</h1></div>   <form id="form1" runat="server">
    <div>
    
    <p>
        <asp:Label ID="Label1" runat="server" Text="Login"></asp:Label>
        <asp:TextBox ID="tbLogin" runat="server"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" 
            ControlToValidate="tbLogin" 
            ErrorMessage="At least 4 at most 20 alphanumeric characters required for  login. The first char must be a letter." 
            ValidationExpression="[A-Za-z][A-Za-z0-9]{3,19}"></asp:RegularExpressionValidator>
    </p>
    <p>
        Current Password<asp:TextBox ID="tbOldPassword" runat="server" 
            TextMode="Password"></asp:TextBox>
        <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
            ControlToValidate="tbOldPassword" 
            ErrorMessage="Current password cannot be empty"></asp:RequiredFieldValidator>
    </p>
        <p>
            <asp:CheckBox ID="cbChangePassword" runat="server" AutoPostBack="True" 
                oncheckedchanged="CheckBox1_CheckedChanged" 
                Text="I want to change my password" />
    </p>
        <p>
            New
        <asp:Label ID="Label2" runat="server" Text="Password"></asp:Label>
        <asp:TextBox ID="tbPassword" runat="server" Enabled="False" TextMode="Password"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" 
            ControlToValidate="tbPassword" 
            ErrorMessage="Password needs to be 6 to 20 chars long and include uppercase, lowercase and a digit." 
            ValidationExpression="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}"></asp:RegularExpressionValidator>
    </p>
        <p>
            <asp:Label ID="Label6" runat="server" Text="Retype New Password"></asp:Label>
            <asp:TextBox ID="tbRetypePassword" runat="server" Enabled="False" 
                TextMode="Password"></asp:TextBox>
            <asp:CompareValidator ID="CompareValidator1" runat="server" 
                ControlToCompare="tbPassword" ControlToValidate="tbRetypePassword" 
                ErrorMessage="CompareValidator"></asp:CompareValidator>
    </p>
        <p>
            <asp:CheckBox ID="cbChangeDetails" runat="server" AutoPostBack="True" 
                oncheckedchanged="cbChangeDetails_CheckedChanged" 
                Text="I want to change my email and name data" />
    </p>
    <p>
        <asp:Label ID="Label3" runat="server" Text="Email"></asp:Label>
        <asp:TextBox ID="tbEmail" runat="server" Enabled="False"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator3" runat="server" 
            ControlToValidate="tbEmail" ErrorMessage="Email syntax invalid." 
            ValidationExpression="\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b"></asp:RegularExpressionValidator>
    </p>
    <p>
        <asp:Label ID="Label4" runat="server" Text="First Name"></asp:Label>
        <asp:TextBox ID="tbFirstName" runat="server" Enabled="False"></asp:TextBox>
    </p>
    <p>
        <asp:Label ID="Label5" runat="server" Text="Last Name"></asp:Label>
        <asp:TextBox ID="tbLastName" runat="server" Enabled="False"></asp:TextBox>
    </p>
    <p>
        <asp:Button ID="btnUpdateUserAccount" runat="server" 
            Text="Update User Account" onclick="btnCreateUserAccount_Click" />
        &nbsp;
        <input id="Reset1" type="reset" value="Clear" /></p>
        <asp:ValidationSummary ID="ValidationSummary1" runat="server" />
        <p>
            <asp:Label ID="lblStatus" runat="server" Text="-- awaiting action --"></asp:Label>
        </p>
    <hr style="margin-top: 9px" />
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
