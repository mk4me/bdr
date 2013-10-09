<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartHForm.aspx.cs" Inherits="PartHForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    <table ID="Table1" runat="server">
        <tr>
            <td>Holter:</td>
            <td>
                <asp:DropDownList ID="dropHolter" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Badanie węchu:</td>
            <td>
                <asp:DropDownList ID="dropBadanieWechu" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wynik węchu:</td>
            <td>
                <asp:DropDownList ID="dropWynikWechu" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Limit dysfagii:</td>
            <td>
                <asp:DropDownList ID="dropLimitDysfagii" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>pH-metria przełyku:</td>
            <td>
                <asp:DropDownList ID="droppHmetriaPrzełyku" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>SPECT:</td>
            <td>
                <asp:DropDownList ID="dropSPECT" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>SPECT wynik:</td>
            <td>
                <asp:CheckBoxList ID="checkListSPECTWynik" runat="server">
                </asp:CheckBoxList>
            </td>
        </tr>
        <tr>
            <td>MRI:</td>
            <td>
                <asp:DropDownList ID="dropMRI" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>MRI wynik:</td>
            <td>
                <asp:TextBox ID="textMRIwynik" runat="server" Rows="5" TextMode="MultiLine"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>USG śródmózgowia:</td>
            <td>
                <asp:DropDownList ID="dropUSGsrodmozgowia" runat="server">
                    <asp:ListItem Value="2" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>USG wynik:</td>
            <td>
                <asp:DropDownList ID="dropUSGWynik" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Genetyka:</td>
            <td>
                <asp:DropDownList ID="dropGenetyka" runat="server">
                    <asp:ListItem Value="-1" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Genetyka wynik:</td>
            <td>
                <asp:TextBox ID="textGenetykaWynik" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Surowica:</td>
            <td>
                <asp:DropDownList ID="dropSurowica" runat="server">
                    <asp:ListItem Value="-1" Text=""></asp:ListItem>
                    <asp:ListItem Value="0">nie</asp:ListItem>
                    <asp:ListItem Value="1">tak</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Surowica pozostało:</td>
            <td>
                <asp:TextBox ID="textSurowicaPozostało" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Ferrytyna:</td>
            <td>
                <asp:TextBox ID="textFerrytyna" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CRP:</td>
            <td>
                <asp:TextBox ID="textCRP" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>NTproCNP:</td>
            <td>
                <asp:TextBox ID="textNTproCNP" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>URCA:</td>
            <td>
                <asp:TextBox ID="textURCA" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>WitD:</td>
            <td>
                <asp:TextBox ID="textWitD" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CHOL:</td>
            <td>
                <asp:TextBox ID="textCHOL" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>TGI:</td>
            <td>
                <asp:TextBox ID="textTGI" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>HDL:</td>
            <td>
                <asp:TextBox ID="textHDL" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>LDL:</td>
            <td>
                <asp:TextBox ID="textLDL" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>olLDL:</td>
            <td>
                <asp:TextBox ID="textolLDL" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Laboratoryjne inne:</td>
            <td>
                <asp:TextBox ID="textLaboratoryjneInne" runat="server" Rows="5" TextMode="MultiLine"></asp:TextBox>
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

