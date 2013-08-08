<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartDForm.aspx.cs" Inherits="PartDForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>Przebyte leczenie operacyjne:</td>
            <td>
                <asp:DropDownList ID="dropRLS" runat="server">
                    <asp:ListItem Value="1">Brak</asp:ListItem>
                    <asp:ListItem Value="2">Palidotomia</asp:ListItem>
                    <asp:ListItem Value="3">Talamotomia</asp:ListItem>
                    <asp:ListItem Value="4">DBS STN</asp:ListItem>
                    <asp:ListItem Value="5">DBS Gpi</asp:ListItem>
                    <asp:ListItem Value="6">DBS Vim</asp:ListItem>
                    <asp:ListItem Value="7">DBS PPN</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nadciśnienie:</td>
            <td>
                <asp:DropDownList ID="DropDownList1" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Blokery kanału wapniowego:</td>
            <td>
                <asp:DropDownList ID="DropDownList2" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dominujący objaw obecnie:</td>
            <td>
                <asp:DropDownList ID="DropDownList3" runat="server">
                    <asp:ListItem Value="1">Zaburzenia równowagi</asp:ListItem>
                    <asp:ListItem Value="2">Spowolnienie</asp:ListItem>
                    <asp:ListItem Value="3">Sztywność</asp:ListItem>
                    <asp:ListItem Value="4">Drżenie</asp:ListItem>
                    <asp:ListItem Value="5">Otępienie</asp:ListItem>
                    <asp:ListItem Value="6">Dyskinezy i fluktuacje</asp:ListItem>
                    <asp:ListItem Value="7">Objawy autonomiczne</asp:ListItem>
                    <asp:ListItem Value="8">Inne</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dominujący objaw uwagi:</td>
            <td>
                <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Badanie węchu:</td>
            <td>
                <asp:DropDownList ID="DropDownList4" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wynik węchu:</td>
            <td>
                <asp:TextBox ID="TextBox2" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Limit dysfagii:</td>
            <td>
                <asp:TextBox ID="TextBox3" runat="server"></asp:TextBox>
            </td>
        </tr>
    </table>
</asp:Content>

