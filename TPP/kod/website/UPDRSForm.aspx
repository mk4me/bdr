<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="UPDRSForm.aspx.cs" Inherits="UPDRSForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Table ID="tableUPDRS" runat="server">
        <asp:TableHeaderRow>
            <asp:TableHeaderCell>DBS</asp:TableHeaderCell>
            <asp:TableHeaderCell ColumnSpan="2">OFF</asp:TableHeaderCell>
            <asp:TableHeaderCell ColumnSpan="2">ON-LP</asp:TableHeaderCell>
            <asp:TableHeaderCell ColumnSpan="2">ON-L</asp:TableHeaderCell>
            <asp:TableHeaderCell ColumnSpan="2">ON-P</asp:TableHeaderCell>
        </asp:TableHeaderRow>
        <asp:TableHeaderRow>
            <asp:TableHeaderCell>BMT</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON</asp:TableHeaderCell>
            <asp:TableHeaderCell>OFF</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON</asp:TableHeaderCell>
            <asp:TableHeaderCell>OFF</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON</asp:TableHeaderCell>
            <asp:TableHeaderCell>OFF</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON</asp:TableHeaderCell>
            <asp:TableHeaderCell>OFF</asp:TableHeaderCell>
        </asp:TableHeaderRow>
    </asp:Table>

    <asp:Button ID="buttonCalculate" runat="server" Text="Podlicz" 
                    onclick="buttonCalculate_Click" />

    <asp:Table ID="tableUPDRSExtra" runat="server">
    </asp:Table>

    <asp:Button ID="buttonSavePart1" runat="server" Text="Zatwierdź" 
                    onclick="buttonCalculate_Click" />
    
    <asp:Table ID="tablePart2" runat="server">
    </asp:Table>

    <asp:Button ID="buttonSavePart2" runat="server" Text="Zatwierdź" 
                    onclick="buttonCalculate_Click" />
    
    <asp:Table ID="tablePart3" runat="server">
    </asp:Table>

    <asp:Button ID="buttonSavePart3" runat="server" Text="Zatwierdź" 
                    onclick="buttonCalculate_Click" />
</asp:Content>

