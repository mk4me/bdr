<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartBForm.aspx.cs" Inherits="PartBForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Objawy i epidemiologia</h2>
    <br />
    <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    <table ID="Table1" runat="server">
        <tr>
            <td>Przebyte leczenie operacyjne:</td>
            <td>
                <asp:DropDownList ID="dropPrzebyteLeczenieOperacyjne" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Papierosy:</td>
            <td>
                <asp:DropDownList ID="dropCigarettes" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kawa:</td>
            <td>
                <asp:DropDownList ID="dropCoffee" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zielona herbata:</td>
            <td>
                <asp:DropDownList ID="dropGreenTea" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Alkohol:</td>
            <td>
                <asp:DropDownList ID="dropAlcohol" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Liczba zabiegów w znieczuleniu<br />ogólnym przed rozpoznaniem PD:</td>
            <td>
                <asp:TextBox ID="textTreatmentNumber" runat="server"></asp:TextBox>
                <%--
                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" 
                    ErrorMessage="*" ControlToValidate="textTreatmentNumber"></asp:RequiredFieldValidator>
                <asp:RegularExpressionValidator Runat="server" ID="RegularExpressionValidator4" 
                    ControlToValidate="textTreatmentNumber"
                    ErrorMessage="Proszę podać liczbę od 0 do 255."
                    ValidationExpression="^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$"></asp:RegularExpressionValidator>
                --%>
            </td>
        </tr>
        <tr>
            <td>Zamieszkanie:</td>
            <td>
                <asp:DropDownList ID="dropZamieszkanie" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Narażenie na toksyczność:</td>
            <td>
                <asp:CheckBoxList ID="checkListToxic" runat="server">
                </asp:CheckBoxList>
            </td>
        </tr>
        <tr>
            <td>Uwagi:</td>
            <td>
                <asp:TextBox ID="textNotes" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Nadciśnienie:</td>
            <td>
                <asp:DropDownList ID="dropNadcisnienie" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Blokery kanału wapniowego:</td>
            <td>
                <asp:DropDownList ID="dropBlokery" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
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
            <td>Objawy autonomiczne:</td>
            <td>
                <asp:CheckBoxList ID="checkListObjawyAutonomiczne" runat="server">
                </asp:CheckBoxList>
            </td>
        </tr>
        <tr>
            <td>RLS:</td>
            <td>
                <asp:DropDownList ID="dropRLS" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy psychotyczne:</td>
            <td>
                <asp:DropDownList ID="dropPsychotyczne" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Depresja:</td>
            <td>
                <asp:DropDownList ID="dropDepresja" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Otępienie:</td>
            <td>
                <asp:DropDownList ID="dropOtepienie" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dyzartia:</td>
            <td>
                <asp:DropDownList ID="dropDyzartia" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dysfagia objaw:</td>
            <td>
                <asp:DropDownList ID="dropDysfagiaObjaw" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>RBD:</td>
            <td>
                <asp:DropDownList ID="dropRBD" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia ruchomości gałek ocznych:</td>
            <td>
                <asp:DropDownList ID="dropZaburzeniaGalek" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Apraksja:</td>
            <td>
                <asp:DropDownList ID="dropApraksja" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Test klaskania:</td>
            <td>
                <asp:DropDownList ID="dropKlaskanie" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="1">prawidłowy</asp:ListItem>
                    <asp:ListItem Value="0">nieprawidłowy</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia węchowe:</td>
            <td>
                <asp:DropDownList ID="dropZaburzeniaWechowe" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
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

