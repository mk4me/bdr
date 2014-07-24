<%@ Page Title="Przegląd i pobieranie" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Overview.aspx.cs" Inherits="Overview" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Table ID="tableOverviews" runat="server" GridLines="Both">
    </asp:Table>
    <br />
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <asp:Button ID="buttonBack" runat="server" Text="Powrót" onclick="buttonBack_Click" />
</asp:Content>

