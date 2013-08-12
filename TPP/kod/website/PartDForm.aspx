<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartDForm.aspx.cs" Inherits="PartDForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>Przebyte leczenie operacyjne:</td>
            <td>
                <asp:DropDownList ID="dropPrzebyteLeczenieOperacyjne" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nadciśnienie:</td>
            <td>
                <asp:DropDownList ID="dropNadcisnienie" runat="server">
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Blokery kanału wapniowego:</td>
            <td>
                <asp:DropDownList ID="dropBlokery" runat="server">
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dominujący objaw obecnie:</td>
            <td>
                <asp:DropDownList ID="dropDominujacyObjawObecnie" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dominujący objaw uwagi:</td>
            <td>
                <asp:TextBox ID="textDominujacyObjawUwagi" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Badanie węchu:</td>
            <td>
                <asp:DropDownList ID="dropBadanieWechu" runat="server">
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wynik węchu:</td>
            <td>
                <asp:DropDownList ID="dropWynikWechu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Limit dysfagii:</td>
            <td>
                <asp:DropDownList ID="dropLimitDysfagii" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
    </table>
</asp:Content>

