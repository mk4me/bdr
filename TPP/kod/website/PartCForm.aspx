<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartCForm.aspx.cs" Inherits="PartCForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Przyjmowane leki</h2>
    <br />
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
                <asp:DropDownList ID="dropLdopa" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
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
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
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
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
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
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
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
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
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
                <asp:DropDownList ID="dropCholinolityk" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textCholinolityk" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Leki inne:</td>
            <td>
                <asp:DropDownList ID="dropLekiInne" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textLekiInne" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMOpis:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textL_STIMOpis" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMAmplitude:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textL_STIMAmplitude" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMDuration:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textL_STIMDuration" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMFrequency:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textL_STIMFrequency" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMOpis:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textR_STIMOpis" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMAmplitude:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textR_STIMAmplitude" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMDuration:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textR_STIMDuration" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMFrequency:</td>
            <td></td>
            <td>
                <asp:TextBox ID="textR_STIMFrequency" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>LDopa (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_Ldopa" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_LDopa" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Agonista (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_Agonista" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_Agonista" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Amantadyna (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_Amantadyna" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_Amantadyna" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>MAOBinh (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_MAOBinh" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_MAOBinh" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>COMTinh (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_COMTinh" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_COMTinh" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Cholinolityk (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_Cholinolityk" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_Cholinolityk" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>LekiInne (wypis):</td>
            <td>
                <asp:DropDownList ID="dropWypis_LekiInne" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
            <td>
                <asp:TextBox ID="textWypis_LekiInne" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMOpis (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_L_STIMOpis" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMAmplitude (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_L_STIMAmplitude" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMDuration (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_L_STIMDuration" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>L_STIMFrequency (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_L_STIMFrequency" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMOpis (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_R_STIMOpis" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMAmplitude (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_R_STIMAmplitude" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMDuration (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_R_STIMDuration" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>R_STIMFrequenc (wypis):</td>
            <td></td>
            <td>
                <asp:TextBox ID="textWypis_R_STIMFrequency" runat="server"></asp:TextBox>
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

