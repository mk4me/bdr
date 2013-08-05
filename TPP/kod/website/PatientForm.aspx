<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PatientForm.aspx.cs" Inherits="PatientForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>Numer pacjenta:</td>
            <td>
                <asp:TextBox ID="textPatientNumber" runat="server"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
                    ErrorMessage="Proszę podać numer." ControlToValidate="textPatientNumber"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td>Rok urodzenia:</td>
            <td>
                <asp:DropDownList ID="dropYear" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Miesiąc urodzenia:</td>
            <td>
                <asp:DropDownList ID="dropMonth" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Płeć:</td>
            <td>
                <asp:RadioButton ID="radioWoman" runat="server" Text="Kobieta" 
                    GroupName="Sex" Checked="True" />
                <asp:RadioButton ID="radioMan" runat="server" Text="Mężczyzna" 
                    GroupName="Sex" />
            </td>
        </tr>
        <tr>
            <td>Lokalizacja:</td>
            <td>
                <asp:DropDownList ID="dropLocation" runat="server">
                    <asp:ListItem>STN</asp:ListItem>
                    <asp:ListItem>Gpi</asp:ListItem>
                    <asp:ListItem>Vim</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Liczba elektrod:</td>
            <td>
                <asp:DropDownList ID="dropElectrodes" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <%--
        <tr>
            <td>Rodzinność:</td>
            <td>
                <asp:DropDownList ID="DropDownList4" runat="server">
                    <asp:ListItem>Tak</asp:ListItem>
                    <asp:ListItem>Nie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wykształcenei:</td>
            <td>
                <asp:DropDownList ID="DropDownList5" runat="server">
                </asp:DropDownList>
            </td>
        </tr>--%>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonOK" runat="server" Text="Zatwierdź" 
        onclick="buttonOK_Click" />
    <asp:Button ID="buttonCancel" runat="server" Text="Anuluj" 
        CausesValidation="False" onclick="buttonCancel_Click" />
</asp:Content>

