<%@ Page Title="Kwestionariusz zaburzeń autonomicznych" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartEForm.aspx.cs" Inherits="PartEForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Kwestionariusz zaburzeń autonomicznych</h2>
    <p>
        <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    </p>
    <table ID="Table1" runat="server">
        <tr>
            <td>Wydzielanie śliny:</td>
            <td>
                <asp:DropDownList ID="dropWydzielanieSliny" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dysfagia:</td>
            <td>
                <asp:DropDownList ID="dropDysfagia" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dysfagia częstotliwość:</td>
            <td>
                <asp:DropDownList ID="dropDysfagiaCzestotliwosc" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nudności:</td>
            <td>
                <asp:DropDownList ID="dropNudnosci" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaparcia:</td>
            <td>
                <asp:DropDownList ID="dropZaparcia" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Trudności w oddawaniu moczu:</td>
            <td>
                <asp:DropDownList ID="dropTrudnosciWOddawaniuMoczu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Potrzeba nagłego oddania moczu:</td>
            <td>
                <asp:DropDownList ID="dropPotrzebaNaglegoOddaniaMoczu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Niekompletne opróżnienie pęcherza:</td>
            <td>
                <asp:DropDownList ID="dropNiekompletneOproznieniePecherza" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Słaby strumień moczu:</td>
            <td>
                <asp:DropDownList ID="dropSlabyStrumienMoczu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Częstotliwość oddawania moczu:</td>
            <td>
                <asp:DropDownList ID="dropCzestotliwowscOddawaniaMoczu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nykturia:</td>
            <td>
                <asp:DropDownList ID="dropNykturia" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Niekontrolowane oddawanie moczu:</td>
            <td>
                <asp:DropDownList ID="dropNiekontrolowaneOddawanieMoczu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Omdlenia:</td>
            <td>
                <asp:DropDownList ID="dropOmdlenia" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia rytmu serca:</td>
            <td>
                <asp:DropDownList ID="dropZaburzeniaRytmuSerca" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Próba pionizacyjna:</td>
            <td>
                <asp:DropDownList ID="dropProbaPionizacyjna" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości twarzy i karku:</td>
            <td>
                <asp:DropDownList ID="dropWzrostPodtliwosciTwarzKark" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości ramion i dłoni:</td>
            <td>
                <asp:DropDownList ID="dropWzrostPotliwosciRamionaDlonie" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości brzucha i pleców:</td>
            <td>
                <asp:DropDownList ID="dropWzrostPotliwosciBrzuchPlecy" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości kończyn dolnych i stóp:</td>
            <td>
                <asp:DropDownList ID="dropWzrostPotliwosciKonczynyDolneStopy" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości twarzy i karku:</td>
            <td>
                <asp:DropDownList ID="dropSpadekPodtliwosciTwarzKark" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości ramion i dłoni:</td>
            <td>
                <asp:DropDownList ID="dropSpadekPotliwosciRamionaDlonie" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości brzucha i pleców:</td>
            <td>
                <asp:DropDownList ID="dropSpadekPotliwosciBrzuchPlecy" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości kończyn dolnych i stóp:</td>
            <td>
                <asp:DropDownList ID="dropSpadekPotliwosciKonczynyDolneStopy" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nietolerancja wysokich temperatur:</td>
            <td>
                <asp:DropDownList ID="dropNietolerancjaWysokichTemp" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nietolerancja niskich temperatur:</td>
            <td>
                <asp:DropDownList ID="dropNietolerancjaNiskichTemp" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Łojotok:</td>
            <td>
                <asp:DropDownList ID="dropLojotok" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek libido:</td>
            <td>
                <asp:DropDownList ID="dropSpadekLibido" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kłopoty osiągnięcia erekcji:</td>
            <td>
                <asp:DropDownList ID="dropKlopotyOsiagnieciaErekcji" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kłopoty utrzymania erekcji:</td>
            <td>
                <asp:DropDownList ID="dropKlopotyUtrzymaniaErekcji" runat="server">
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

