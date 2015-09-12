<%@ Page Title="UPDRS i skale oceny klinicznej" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartFForm.aspx.cs" Inherits="PartFForm" %>

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
    <h2>UPDRS i skale oceny klinicznej</h2>
    <p>
        <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
    </p>
    <asp:Table ID="tableUPDRS" runat="server">
    </asp:Table>
    <br />
    <asp:Button ID="buttonCalculate" runat="server" Text="Podlicz" 
                    onclick="buttonCalculate_Click" />
    <br />
    <br />
    <asp:Table ID="tableUPDRSExtra" runat="server">
    </asp:Table>
    <br />
    <asp:Button ID="buttonSavePartA" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartA_Click" />
    <asp:Label ID="labelSavePartA" runat="server" Text=""></asp:Label>
    <br />
    <br />
    <asp:Table ID="tableFiles" runat="server">
    </asp:Table>
    <br />
    <asp:Button runat="server" ID="buttonSaveFiles" Text="Wgraj pliki" onclick="buttonSaveFiles_Click" />
    <asp:Label ID="labelSavedFiles" runat="server" Text=""></asp:Label>
    <br />
    <br />
    <asp:Table ID="tablePart2" runat="server">
    </asp:Table>
    <br />
    <asp:Button ID="buttonSavePartB" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartB_Click" />
    <asp:Label ID="labelSavePartB" runat="server" Text=""></asp:Label>
    <br />
    <br />
    <asp:Table ID="tablePart5" runat="server">
    </asp:Table>
    <br />
    <asp:Button ID="buttonSavePartB0" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartB0_Click" />
    <asp:Label ID="labelSavePartB0" runat="server" Text=""></asp:Label>
    <br />
    <br />
    <asp:Table ID="tablePart4" runat="server">
    </asp:Table>
    <br />
    <asp:Button ID="buttonSavePartB1" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartB1_Click" />
    <asp:Label ID="labelSavePartB1" runat="server" Text=""></asp:Label>
    <br />
    <br />
    <asp:Table ID="tablePart3" runat="server">
    </asp:Table>
    <br />
    <asp:Button ID="buttonSavePartC" runat="server" Text="Zatwierdź" 
                    onclick="buttonSavePartC_Click" />
    <asp:Label ID="labelSavePartC" runat="server" Text=""></asp:Label>
    
    <br />
    <br />
    <table ID="Table2" runat="server">
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

