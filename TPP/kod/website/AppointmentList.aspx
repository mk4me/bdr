<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="AppointmentList.aspx.cs" Inherits="AppointmentList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>
                <asp:Table ID="tableAppointments" runat="server" Width="250px">
                    <asp:TableHeaderRow>
                    </asp:TableHeaderRow>
                </asp:Table>
            </td>
            <td>
                <table ID="Table3" runat="server">
                    <tr>
                        <td><asp:Button ID="buttonNewAppointment" runat="server" Text="Dodaj nową wizytę" 
                                Width="150px" onclick="buttonNewAppointment_Click" /></td>
                    </tr>
                    <tr>
                        <td><asp:Button ID="buttonEditAppointment" runat="server" Text="Edytuj wizytę" 
                                Width="150px" /></td>
                    </tr>
                    <tr>
                        <td><asp:Button ID="buttonDeleteAppointment" runat="server" Text="Usuń wizytę" 
                                Width="150px" /></td>
                    </tr>
                    <tr>
                        <td><asp:Button ID="buttonBack" runat="server" Text="Powrót" 
                                Width="150px" onclick="buttonBack_Click" /></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
</asp:Content>

