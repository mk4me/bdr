<%@ Page Title="Pacjent" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PatientForm.aspx.cs" Inherits="PatientForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>
        Pacjent
    </h2>
    <br />
    <table ID="Table1" runat="server">
        <tr>
            <td>Grupa:</td>
            <td>
                <asp:DropDownList ID="dropGroup" runat="server" AutoPostBack="True" 
                    onselectedindexchanged="dropGroup_SelectedIndexChanged">
                </asp:DropDownList>
            </td>
        </tr>
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
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonOK" runat="server" Text="Zatwierdź" 
        onclick="buttonOK_Click" />
    <asp:Button ID="buttonCancel" runat="server" Text="Anuluj" 
        CausesValidation="False" onclick="buttonCancel_Click" />
</asp:Content>

