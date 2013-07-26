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
                <asp:ListBox ID="listPatients" runat="server" Rows="10"></asp:ListBox>
            
            </td>
        </tr>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
</asp:Content>

