<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartCForm.aspx.cs" Inherits="PartCForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textCholinotyk" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Leki inne:</td>
            <td>
                <asp:DropDownList ID="dropLekiInne" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textLekiInne" runat="server"></asp:TextBox>
            </td>
        </tr>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonOK" runat="server" Text="Zatwierdź" 
        onclick="buttonOK_Click" />
    <asp:Button ID="buttonCancel" runat="server" Text="Anuluj" 
        CausesValidation="False" onclick="buttonCancel_Click" />
</asp:Content>

