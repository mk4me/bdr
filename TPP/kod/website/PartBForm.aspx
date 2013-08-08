<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartBForm.aspx.cs" Inherits="PhsychologyForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>RLS:</td>
            <td>
                <asp:DropDownList ID="dropRLS" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy psychotyczne:</td>
            <td>
                <asp:DropDownList ID="DropDownList1" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Depresja:</td>
            <td>
                <asp:DropDownList ID="DropDownList2" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Otępienie:</td>
            <td>
                <asp:DropDownList ID="DropDownList3" runat="server">
                    <asp:ListItem Value="2">Brak danych</asp:ListItem>
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="0.5">Częściowo</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dyzartia:</td>
            <td>
                <asp:DropDownList ID="DropDownList4" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>RBD:</td>
            <td>
                <asp:DropDownList ID="DropDownList5" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia ruchomości gałek ocznych:</td>
            <td>
                <asp:DropDownList ID="DropDownList6" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Apraksja:</td>
            <td>
                <asp:DropDownList ID="DropDownList7" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Test klaskania:</td>
            <td>
                <asp:DropDownList ID="DropDownList8" runat="server">
                    <asp:ListItem Value="1">Prawidłowy</asp:ListItem>
                    <asp:ListItem Value="0">Nieprawidłowy</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia węchowe:</td>
            <td>
                <asp:DropDownList ID="DropDownList9" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Masa ciała:</td>
            <td>
                <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Drżenie:</td>
            <td>
                <asp:DropDownList ID="DropDownList10" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Sztywność:</td>
            <td>
                <asp:DropDownList ID="DropDownList11" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spowolnienie:</td>
            <td>
                <asp:DropDownList ID="DropDownList12" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy inne:</td>
            <td>
                <asp:DropDownList ID="DropDownList13" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy inne. Jakie?:</td>
            <td>
                <asp:TextBox ID="TextBox2" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Czas OFF:</td>
            <td>
                <asp:TextBox ID="TextBox3" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Poprawa po LDopie:</td>
            <td>
                <asp:DropDownList ID="DropDownList14" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
    </table>
</asp:Content>

