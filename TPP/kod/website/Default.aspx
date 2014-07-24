<%@ Page Title="Strona powitalna" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeFile="Default.aspx.cs" Inherits="_Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        Strona Powitalna
    </h2>
    <ul>
        <li>
            <p>
                <a href="Main.aspx" title="Main.aspx">Lista pacjentów</a>
            </p>
        </li>
        <li>
            <p>
                <asp:LinkButton ID="linkButtonExportCSV" runat="server" onclick="buttonExportCSV_Click">Pobierz plik CSV</asp:LinkButton>
            </p>
        </li>
        <li>
            <p>
                <a href="Overview.aspx" title="Overview.aspx">Przegląd i pobieranie</a>
            </p>
        </li>
    </ul>
</asp:Content>
