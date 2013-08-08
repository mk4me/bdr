<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartEForm.aspx.cs" Inherits="PartEForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <table ID="Table1" runat="server">
        <tr>
            <td>Wydzielanie śliny:</td>
            <td>
                <asp:DropDownList ID="dropRLS" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dysfagia:</td>
            <td>
                <asp:DropDownList ID="DropDownList1" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Dysfagia częstotliwość:</td>
            <td>
                <asp:DropDownList ID="DropDownList2" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nudności:</td>
            <td>
                <asp:DropDownList ID="DropDownList3" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaparcia:</td>
            <td>
                <asp:DropDownList ID="DropDownList4" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Trudności w oddawaniu moczu:</td>
            <td>
                <asp:DropDownList ID="DropDownList5" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Potrzeba nagłego oddania moczu:</td>
            <td>
                <asp:DropDownList ID="DropDownList6" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Niekompletne opróżnienie pęcherza:</td>
            <td>
                <asp:DropDownList ID="DropDownList7" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Słaby strumień moczu:</td>
            <td>
                <asp:DropDownList ID="DropDownList8" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Częstotliowość oddawania moczu:</td>
            <td>
                <asp:DropDownList ID="DropDownList9" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nykturia:</td>
            <td>
                <asp:DropDownList ID="DropDownList10" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Niekontrolowane oddawanie moczu:</td>
            <td>
                <asp:DropDownList ID="DropDownList11" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Omdlenia:</td>
            <td>
                <asp:DropDownList ID="DropDownList12" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zaburzenia rytmu serca:</td>
            <td>
                <asp:DropDownList ID="DropDownList13" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Próba pionizacyjna:</td>
            <td>
                <asp:DropDownList ID="DropDownList14" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości twarzy i karku:</td>
            <td>
                <asp:DropDownList ID="DropDownList15" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości ramion i dłoni:</td>
            <td>
                <asp:DropDownList ID="DropDownList16" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Wzrost potliwości kończyn dolnych i stóp:</td>
            <td>
                <asp:DropDownList ID="DropDownList17" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości twarzy i karku:</td>
            <td>
                <asp:DropDownList ID="DropDownList18" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości ramion i dłoni:</td>
            <td>
                <asp:DropDownList ID="DropDownList19" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek potliwości kończyn dolnych i stóp:</td>
            <td>
                <asp:DropDownList ID="DropDownList20" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nietolerancja wysokich temperatur:</td>
            <td>
                <asp:DropDownList ID="DropDownList21" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Nietolerancja niskich temperatur:</td>
            <td>
                <asp:DropDownList ID="DropDownList22" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Łojotok:</td>
            <td>
                <asp:DropDownList ID="DropDownList23" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Spadek libido:</td>
            <td>
                <asp:DropDownList ID="DropDownList24" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kłopoty osiągnięcia erekcji:</td>
            <td>
                <asp:DropDownList ID="DropDownList25" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kłopoty utrzymania erekcji:</td>
            <td>
                <asp:DropDownList ID="DropDownList26" runat="server">
                    <asp:ListItem Value="0">Norma</asp:ListItem>
                    <asp:ListItem Value="1">Niewielkie zaburzenia</asp:ListItem>
                    <asp:ListItem Value="2">Umiarkowane</asp:ListItem>
                    <asp:ListItem Value="3">Poważne</asp:ListItem>
                    <asp:ListItem Value="4">Ciężkie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
    </table>
</asp:Content>

