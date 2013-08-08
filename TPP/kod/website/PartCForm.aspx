<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartCForm.aspx.cs" Inherits="MedicineForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <th>Lek</th>
            <th>Przyjmowanie</th>
            <th>Dawka</th>
        </tr>
        <tr>
            <td>LDopa:</td>
            <td>
                <asp:DropDownList ID="dropLDopa" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textLDopa" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Agonista:</td>
            <td>
                <asp:DropDownList ID="dropAgonista" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textAgonista" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Amantadyna:</td>
            <td>
                <asp:DropDownList ID="dropAmantadyna" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textAmantadyna" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>MAOBinh:</td>
            <td>
                <asp:DropDownList ID="dropMAOBinh" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textMAOBinh" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>COMTinh</td>
            <td>
                <asp:DropDownList ID="dropCOMTinh" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textCOMTinh" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Cholinolityk:</td>
            <td>
                <asp:DropDownList ID="dropCholinotyk" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textCholinotyk" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Leki inne:</td>
            <td>
                <asp:DropDownList ID="DropDownList1" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
            </td>
        </tr>
    </table>
</asp:Content>

