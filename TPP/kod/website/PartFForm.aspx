<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="PartFForm.aspx.cs" Inherits="PartFForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
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
</asp:Content>

