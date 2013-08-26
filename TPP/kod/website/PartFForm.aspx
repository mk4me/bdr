<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartFForm.aspx.cs" Inherits="PartFForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <script type = "text/javascript">
        window.onload = function () {
            var scrollY = parseInt('<%=Request.Form["scrollY"] %>');
            if (!isNaN(scrollY)) {
                window.scrollTo(0, scrollY);
            }
        };
        window.onscroll = function () {
            var scrollY = document.body.scrollTop;
            if (scrollY == 0) {
                if (window.pageYOffset) {
                    scrollY = window.pageYOffset;
                }
                else {
                    scrollY = (document.body.parentElement) ? document.body.parentElement.scrollTop : 0;
                }
            }
            if (scrollY > 0) {
                var input = document.getElementById("scrollY");
                if (input == null) {
                    input = document.createElement("input");
                    input.setAttribute("type", "hidden");
                    input.setAttribute("id", "scrollY");
                    input.setAttribute("name", "scrollY");
                    document.forms[0].appendChild(input);
                }
                input.value = scrollY;
            }
        };
    </script>
    <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    <asp:Table ID="tableUPDRS" runat="server">
        <asp:TableHeaderRow>
            <asp:TableHeaderCell>BMT</asp:TableHeaderCell>
            <asp:TableHeaderCell ColumnSpan="4">ON</asp:TableHeaderCell>
            <asp:TableHeaderCell ColumnSpan="4">OFF</asp:TableHeaderCell>
        </asp:TableHeaderRow>
        <asp:TableHeaderRow>
            <asp:TableHeaderCell>DBS</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON-LP</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON-L</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON-P</asp:TableHeaderCell>
            <asp:TableHeaderCell>OFF</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON-LP</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON-L</asp:TableHeaderCell>
            <asp:TableHeaderCell>ON-P</asp:TableHeaderCell>
            <asp:TableHeaderCell>OFF</asp:TableHeaderCell>
        </asp:TableHeaderRow>
    </asp:Table>

    <asp:Button ID="buttonCalculate" runat="server" Text="Podlicz" 
                    onclick="buttonCalculate_Click" />
    <br />
    <br />
    <asp:Table ID="tableUPDRSExtra" runat="server">
    </asp:Table>

    <asp:Button ID="buttonSavePartA" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartA_Click" />
    <br />
    <br />
    <asp:Table ID="tablePart2" runat="server">
    </asp:Table>

    <asp:Button ID="buttonSavePartB" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartB_Click" />
    <br />
    <br />
    <asp:Table ID="tablePart3" runat="server">
    </asp:Table>

    <asp:Button ID="buttonSavePartC" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartC_Click" />
    <br />
    <br />
    <table ID="Table1" runat="server">
        <tr>
            <td>PDQ39:</td>
            <td>
                <asp:TextBox ID="textPDQ39" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>AIMS:</td>
            <td>
                <asp:TextBox ID="textAIMS" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Epworth:</td>
            <td>
                <asp:TextBox ID="textEpworth" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>CGI:</td>
            <td>
                <asp:TextBox ID="textCGI" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>FSS:</td>
            <td>
                <asp:TextBox ID="textFSS" runat="server"></asp:TextBox>
            </td>
        </tr>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonOK" runat="server" Text="Zatwierdź" 
        onclick="buttonOK_Click" />
    <asp:Button ID="buttonCancel" runat="server" Text="Powrót" 
        CausesValidation="False" onclick="buttonCancel_Click" />
</asp:Content>

