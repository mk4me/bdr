<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartBForm.aspx.cs" Inherits="PartBForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    <table ID="Table1" runat="server">
        <tr>
            <td>RLS:</td>
            <td>
                <asp:DropDownList ID="dropRLS" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy psychotyczne:</td>
            <td>
                <asp:DropDownList ID="dropPsychotyczne" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Depresja:</td>
            <td>
                <asp:DropDownList ID="dropDepresja" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>RBD:</td>
            <td>
                <asp:DropDownList ID="dropRBD" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia ruchomości gałek ocznych:</td>
            <td>
                <asp:DropDownList ID="dropZaburzeniaGalek" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Apraksja:</td>
            <td>
                <asp:DropDownList ID="dropApraksja" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Test klaskania:</td>
            <td>
                <asp:DropDownList ID="dropKlaskanie" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="1">prawidłowy</asp:ListItem>
                    <asp:ListItem Value="0">nieprawidłowy</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia węchowe:</td>
            <td>
                <asp:DropDownList ID="dropZaburzeniaWechowe" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Masa ciała:</td>
            <td>
                <asp:TextBox ID="textMasa" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Drżenie:</td>
            <td>
                <asp:DropDownList ID="dropDrzenie" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Sztywność:</td>
            <td>
                <asp:DropDownList ID="dropSztywnosc" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spowolnienie:</td>
            <td>
                <asp:DropDownList ID="dropSpowolnienie" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy inne:</td>
            <td>
                <asp:DropDownList ID="dropObjawy" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Objawy inne. Jakie?:</td>
            <td>
                <asp:TextBox ID="textObjawy" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Czas OFF:</td>
            <td>
                <asp:TextBox ID="textCzasOFF" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Poprawa po LDopie:</td>
            <td>
                <asp:DropDownList ID="dropPoprawa" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
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
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Blokery kanału wapniowego:</td>
            <td>
                <asp:DropDownList ID="dropBlokery" runat="server">
                    <asp:ListItem Value="2">brak danych</asp:ListItem>
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
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonOK" runat="server" Text="Zatwierdź" 
        onclick="buttonOK_Click" />
    <asp:Button ID="buttonCancel" runat="server" Text="Anuluj" 
        CausesValidation="False" onclick="buttonCancel_Click" />
</asp:Content>

