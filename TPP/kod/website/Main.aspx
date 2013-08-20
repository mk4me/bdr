<%@ Page Title="Strona główna" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Main.aspx.cs" Inherits="Main" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        Strona główna
    </h2>
    <p>
        Pacjenci.
    </p>
    <table ID="Table1" runat="server">
        <tr>
            <td>
                <asp:ListBox ID="listPatients" runat="server" Rows="10" Width="200px" 
                    AutoPostBack="True" OnSelectedIndexChanged="listPatients_SelectedIndexChanged"></asp:ListBox>
            
            </td>
            <td>
                <table ID="Table2" runat="server">
                    <tr>
                        <td>
                            <asp:Button ID="buttonShowAppointments" runat="server" Text="Wyświetl listę wizyt" 
                                Width="200px" onclick="buttonShowAppointments_Click" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonNewPatient" runat="server" Text="Wprowadź nowego pacjenta" 
                                onclick="buttonNewPatient_Click" Width="200px" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonEditPatient" runat="server" Text="Edytuj dane pacjenta" 
                                Width="200px" onclick="buttonEditPatient_Click" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonDeletePatient" runat="server" Text="Usuń dane pacjenta" 
                                Width="200px" onclick="buttonDeletePatient_Click" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
</asp:Content>

