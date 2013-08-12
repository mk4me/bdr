<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartHForm.aspx.cs" Inherits="PartHForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>Holter:</td>
            <td>
                <asp:DropDownList ID="dropHolter" runat="server">
                    <asp:ListItem Value="-1">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>pH-metria przełyku:</td>
            <td>
                <asp:DropDownList ID="droppHmetriaPrzełyku" runat="server">
                    <asp:ListItem Value="-1">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>SPECT:</td>
            <td>
                <asp:DropDownList ID="dropSPECT" runat="server">
                    <asp:ListItem Value="-1">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>MRI:</td>
            <td>
                <asp:DropDownList ID="dropMRI" runat="server">
                    <asp:ListItem Value="-1">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>MRI wynik:</td>
            <td>
                <asp:TextBox ID="textMRIwynik" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>USG śródmózgowia:</td>
            <td>
                <asp:DropDownList ID="dropUSGsrodmozgowia" runat="server">
                    <asp:ListItem Value="2">nie wiadomo</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>USG wynik:</td>
            <td>
                <asp:DropDownList ID="dropUSGWynik" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kwas moczowy:</td>
            <td>
                <asp:TextBox ID="textKwasMoczowy" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Genetyka:</td>
            <td>
                <asp:DropDownList ID="dropGenetyka" runat="server">
                    <asp:ListItem Value="-1">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Genetyka wynik:</td>
            <td>
                <asp:TextBox ID="textGenetykaWynik" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Surowica:</td>
            <td>
                <asp:DropDownList ID="dropSurowica" runat="server">
                    <asp:ListItem Value="-1">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Surowica pozostało:</td>
            <td>
                <asp:TextBox ID="textSurowicaPozostało" runat="server"></asp:TextBox>
            </td>
        </tr>
    </table>
</asp:Content>

