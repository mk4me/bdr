<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartHForm.aspx.cs" Inherits="PartHForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>Holter:</td>
            <td>
                <asp:DropDownList ID="DropDownList1" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>pH-metria przełyku:</td>
            <td>
                <asp:DropDownList ID="DropDownList2" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>SPECT:</td>
            <td>
                <asp:DropDownList ID="DropDownList3" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>MRI:</td>
            <td>
                <asp:DropDownList ID="DropDownList4" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>MRI wynik:</td>
            <td>
                <asp:TextBox ID="TextBox2" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>USG śródmózgowia:</td>
            <td>
                <asp:DropDownList ID="DropDownList5" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                    <asp:ListItem Value="2">Nie wiadomo</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>USG wynik:</td>
            <td>
                <asp:DropDownList ID="DropDownList6" runat="server">
                    <asp:ListItem Value="0">Brak okna</asp:ListItem>
                    <asp:ListItem Value="1">Brak hyperechogeniczności</asp:ListItem>
                    <asp:ListItem Value="2">Hyperechgeniczność</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kwas moczowy:</td>
            <td>
                <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Genetyka:</td>
            <td>
                <asp:DropDownList ID="DropDownList7" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Genetyka wynik:</td>
            <td>
                <asp:TextBox ID="TextBox3" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Surowica:</td>
            <td>
                <asp:DropDownList ID="DropDownList8" runat="server">
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Surowica pozostało:</td>
            <td>
                <asp:TextBox ID="TextBox4" runat="server"></asp:TextBox>
            </td>
        </tr>
    </table>
</asp:Content>

