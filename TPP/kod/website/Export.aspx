<%@ Page Title="Selektywny eksport" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Export.aspx.cs" Inherits="Export" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:ScriptManager runat="server" ID="Script1"></asp:ScriptManager>
    <asp:UpdatePanel runat="Server" ID="UpdatePanel1">
        <ContentTemplate>
            <table ID="Table1" runat="server">
                <tr>
                    <th>Wybierz atrybuty:</th>
                    <th></th>
                    <th>Wybrane:</th>
                    <th></th>
                </tr>
                <tr>
                    <td valign="top">
                        <asp:ListBox ID="listBoxWszystkie" runat="server" Rows="15" 
                            SelectionMode="Multiple" Width="350px"></asp:ListBox>
                    </td>
                    <td>
                        <asp:Button ID="buttonToRight" runat="server" Text=">" onClick="buttonToRight_Click" Width="40px" />
                        <br />
                        <br />
                        <asp:Button ID="buttonToLeft" runat="server" Text="<" onClick="buttonToLeft_Click" Width="40px" />
                    </td>
                    <td valign="top">
                        <asp:ListBox ID="listBoxWybrane" runat="server" Rows="15" 
                            SelectionMode="Multiple" Width="350px"></asp:ListBox>
                    </td>
                    <td>
                        <asp:Button ID="buttonUp" runat="server" Text="^" 
                            onclick="buttonUp_Click" Width="40px" />
                        <br />
                        <br />
                        <asp:Button ID="buttonDown" runat="server" Text="  v  " 
                            onclick="buttonDown_Click" Width="40px" />
                    </td>
                </tr>
            </table>
        </ContentTemplate>
    </asp:UpdatePanel>
    <br />
    <table ID="Table2" runat="server">
        <tr>
            <th>Rodzaje wizyt:</th>
            <th>Warianty badań:</th>
        </tr>
        <tr>
            <td valign="top">
                <asp:CheckBoxList ID="checkListRodzajWizyty" runat="server">
                </asp:CheckBoxList>
            </td>
            <td valign="top">
                <asp:CheckBoxList ID="checkListBMTDBS" runat="server">
                    <asp:ListItem Text="BMT ON"></asp:ListItem>
                    <asp:ListItem Text="BMT OFF"></asp:ListItem>
                    <asp:ListItem Text="DBS ON"></asp:ListItem>
                    <asp:ListItem Text="DBS OFF"></asp:ListItem>
                </asp:CheckBoxList>
            </td>
        </tr>
        
    </table>
    <br />
    <asp:Button ID="buttonExportCSV" runat="server" Text="Pobierz CSV" 
        onclick="buttonExportCSV_Click" />
    <br />
    <br />
    <br />
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <asp:Button ID="buttonBack" runat="server" Text="Powrót" onclick="buttonBack_Click" />
    <asp:Button ID="buttonReset" runat="server" Text="Reset" onclick="buttonReset_Click" />
</asp:Content>

