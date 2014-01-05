<%@ Page Title="Lista wizyt" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="AppointmentList.aspx.cs" Inherits="AppointmentList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>
        Lista wizyt
    </h2>
    <br />
    <asp:Label ID="labelPatientNumber" runat="server" Text="labelPatientNumber"></asp:Label>
    <br />
    <br />
    <asp:Table ID="tableAppointments" runat="server" Width="450px">
        <asp:TableHeaderRow>
        </asp:TableHeaderRow>
    </asp:Table>
    <br />
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <asp:Button ID="buttonBack" runat="server" Text="Powrót" onclick="buttonBack_Click" />
</asp:Content>

