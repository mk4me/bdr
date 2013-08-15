<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="ExaminationList.aspx.cs" Inherits="ExaminationList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Label ID="labelPatientNumber" runat="server" Text="labelPatientNumber"></asp:Label>
    <table ID="Table2" runat="server">
        <tr>
            <td>
                <asp:Button ID="buttonNewUPDRS" runat="server" Text="Dodaj nowe badanie - UPDRS" 
                    onclick="buttonNewUPDRS_Click" />
            </td>
        </tr>
        <tr>
            <td>
                <asp:Button ID="buttonNewOculography" runat="server" Text="Dodaj nowe badanie - okulografia i testy" 
                    onclick="buttonNewOculography_Click" />
            </td>
        </tr>
    </table>
</asp:Content>

