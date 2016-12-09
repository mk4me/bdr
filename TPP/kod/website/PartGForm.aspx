<%@ Page Title="Badania i testy psychologiczne" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartGForm.aspx.cs" Inherits="PartGForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Badania i testy psychologiczne</h2>
    <p>
        <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    </p>
    <table ID="Table1" runat="server">
        <tr>
            <td>MMSE:</td>
            <td>
                <asp:TextBox ID="textMMSE" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Test zegara:</td>
            <td>
                <asp:DropDownList ID="dropTestZegara" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nieprawidłowy</asp:ListItem>
                    <asp:ListItem Value="1">prawidłowy</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Test zegara ACE-III:</td>
            <td>
                <asp:TextBox ID="textTestZegaraACE_III" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CLOX 1-rysunek:</td>
            <td>
                <asp:TextBox ID="textCLOX1_Rysunek" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CLOX 2-kopia:</td>
            <td>
                <asp:TextBox ID="textCLOX2_Kopia" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>AVLT próba 1:</td>
            <td>
	            <asp:TextBox ID="textAVLT_proba_1" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT próba 2:</td>
            <td>
	            <asp:TextBox ID="textAVLT_proba_2" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT próba 3:</td>
            <td>
	            <asp:TextBox ID="textAVLT_proba_3" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT próba 4:</td>
            <td>
	            <asp:TextBox ID="textAVLT_proba_4" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT próba 5:</td>
            <td>
	            <asp:TextBox ID="textAVLT_proba_5" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT suma:</td>
            <td>
	            <asp:TextBox ID="textAVLT_Suma" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT średnia:</td>
            <td>
	            <asp:TextBox ID="textAVLT_Srednia" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT krótkie odroczenie:</td>
            <td>
	            <asp:TextBox ID="textAVLT_KrotkieOdroczenie" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT odroczony po 20 min:</td>
            <td>
	            <asp:TextBox ID="textAVLT_Odroczony20min" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT rozpoznawanie:</td>
            <td>
	            <asp:TextBox ID="textAVLT_Rozpoznawanie" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AVLT błędy rozpoznania:</td>
            <td>
	            <asp:TextBox ID="textAVLT_BledyRozpoznania" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test AVLT średnia:</td>
            <td>
                <asp:TextBox ID="textTestAVLTSrednia" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test AVLT odroczony:</td>
            <td>
                <asp:TextBox ID="textTestAVLTOdroczony" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test AVLT po 20 minutach:</td>
            <td>
                <asp:TextBox ID="textTestAVLTPo20min" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test AVLT rozpoznawanie:</td>
            <td>
                <asp:TextBox ID="textTestAVLTRozpoznawanie" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>CVLT próba 1:</td>
            <td>
	            <asp:TextBox ID="textCVLT_proba_1" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT próba 2:</td>
            <td>
	            <asp:TextBox ID="textCVLT_proba_2" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT próba 3:</td>
            <td>
	            <asp:TextBox ID="textCVLT_proba_3" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT próba 4:</td>
            <td>
	            <asp:TextBox ID="textCVLT_proba_4" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT próba 5:</td>
            <td>
	            <asp:TextBox ID="textCVLT_proba_5" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT suma:</td>
            <td>
	            <asp:TextBox ID="textCVLT_Suma" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT OSKO<br />krótkie odroczenie:</td>
            <td>
	            <asp:TextBox ID="textCVLT_OSKO_krotkie_odroczenie" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT OPKO<br />krótkie odroczenie i pomoc:</td>
            <td>
	            <asp:TextBox ID="textCVLT_OPKO_krotkie_odroczenie_i_pomoc" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT OSDO po 20 min:</td>
            <td>
	            <asp:TextBox ID="textCVLT_OSDO_po20min" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT OPDO po 20 min i pomoc:</td>
            <td>
	            <asp:TextBox ID="textCVLT_OPDO_po20min_i_pomoc" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT perseweracje:</td>
            <td>
	            <asp:TextBox ID="textCVLT_perseweracje" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT wtrącenia<br />w odtwarzaniu swobodnym:</td>
            <td>
	            <asp:TextBox ID="textCVLT_WtraceniaOdtwarzanieSwobodne" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT wtrącenia<br />w odtwarzaniu z pomocą:</td>
            <td>
	            <asp:TextBox ID="textCVLT_wtraceniaOdtwarzanieZPomoca" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT rozpoznawanie:</td>
            <td>
	            <asp:TextBox ID="textCVLT_Rozpoznawanie" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CVLT błędy rozpoznania:</td>
            <td>
	            <asp:TextBox ID="textCVLT_BledyRozpoznania" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Benton JOL:</td>
            <td>
	            <asp:TextBox ID="textBenton_JOL" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>TFZ Reya lub inny:</td>
            <td>
	            <asp:TextBox ID="textTFZ_ReyaLubInny" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>TFZ Reya lub inny 2:</td>
            <td>
	            <asp:TextBox ID="textTFZ_ReyaLubInny2" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Test fluencji - K:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiK" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji - P:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiP" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji - zwierzęta:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiZwierzeta" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji - owoce i warzywa:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiOwoceWarzywa" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test fluencji ostre:</td>
            <td>
                <asp:TextBox ID="textTestFluencjiOstre" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
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
            <td>Test łączenia punktów A mały:</td>
            <td>
                <asp:TextBox ID="textTestLaczeniaPunktowA_maly" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Test łączenia punktów B mały:</td>
            <td>
                <asp:TextBox ID="textTestLaczeniaPunktowB_maly" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>ToL - suma ruchów:</td>
            <td>
                <asp:TextBox ID="textToL_SumaRuchow" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - liczba prawidłowych:</td>
            <td>
                <asp:TextBox ID="textToL_LiczbaPrawidlowych" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - czas inicjowania [sek]:</td>
            <td>
                <asp:TextBox ID="textToL_CzasInicjowania_sek" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - czas wykonania [sek]:</td>
            <td>
                <asp:TextBox ID="textToL_CzasWykonania_sek" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - czas całkowity [sek]:</td>
            <td>
                <asp:TextBox ID="textToL_CzasCalkowity_sek" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - czas przekroczony :</td>
            <td>
                <asp:TextBox ID="textToL_CzasPrzekroczony" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - liczba przekroczeń zasad:</td>
            <td>
                <asp:TextBox ID="textToL_LiczbaPrzekroczenZasad" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>ToL - reakcje ukierunkowane:</td>
            <td>
                <asp:TextBox ID="textToL_ReakcjeUkierunkowane" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>WAIS-R - wiadomości:</td>
            <td>
                <asp:TextBox ID="textWAIS_R_Wiadomosci" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>WAIS-R - powtarzanie cyfr:</td>
            <td>
                <asp:TextBox ID="textWAIS_R_PowtarzanieCyfr" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>WAIS-R - podobieństwa:</td>
            <td>
                <asp:TextBox ID="textWAIS_R_Podobienstwa" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Bostoński Test Nazywania BNT:</td>
            <td>
                <asp:TextBox ID="textBostonskiTestNazywaniaBNT" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>BNT - średni czas reakcji [sek]:</td>
            <td>
                <asp:TextBox ID="textBNT_SredniCzasReakcji_sek" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Skala depresji Becka:</td>
            <td>
                <asp:TextBox ID="textSkalaDepresjiBecka" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Skala depresji Becka II:</td>
            <td>
                <asp:TextBox ID="textSkalaDepresjiBeckaII" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
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
                <asp:TextBox ID="textOpisBadania" runat="server" Columns="50" Rows="5" TextMode="MultiLine"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Wnioski:</td>
            <td>
                <asp:TextBox ID="textWnioski" runat="server" Columns="50" Rows="5" TextMode="MultiLine"></asp:TextBox>
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

