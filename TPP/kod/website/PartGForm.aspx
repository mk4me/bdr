<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartGForm.aspx.cs" Inherits="PartGForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Badania i testy psychologiczne</h2>
    <br />
    <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    <table ID="Table1" runat="server">
        <tr>
            <td>Test zegara:</td>
            <td>
                <asp:DropDownList ID="dropTestZegara" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>MMSE:</td>
            <td>
                <asp:TextBox ID="textMMSE" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>WAIS-R - wiadomości:</td>
            <td>
                <asp:TextBox ID="textWAISR_Wiadomosci" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>WAIS-R - powtarzanie cyfr:</td>
            <td>
                <asp:TextBox ID="textWAISR_PowtarzanieCyfr" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Skala depresji Becka:</td>
            <td>
                <asp:TextBox ID="textSkalaDepresjiBecka" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji zwierzęta:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiZwierzeta" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji ostre:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiOstre" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji K:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiK" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test łączenia punktów A:</td>
            <td>
                <asp:TextBox ID="textTestLaczeniaPunktowA" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test łączenia punktów B:</td>
            <td>
                <asp:TextBox ID="textTestLaczeniaPunktowB" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test uczenia słowno-słuchowego:</td>
            <td>
                <asp:TextBox ID="textTestUczeniaSlownoSluchowego" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test Stroopa:</td>
            <td>
                <asp:TextBox ID="textTestStroopa" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test Minnesota:</td>
            <td>
                <asp:TextBox ID="textTestMinnesota" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Inne psychologiczne:</td>
            <td>
                <asp:TextBox ID="textInnePsychologiczne" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Opis badania:</td>
            <td>
                <asp:TextBox ID="textOpisBadania" runat="server" Rows="5" TextMode="MultiLine"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Wnioski:</td>
            <td>
                <asp:TextBox ID="textWnioski" runat="server" Rows="5" TextMode="MultiLine"></asp:TextBox>
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

