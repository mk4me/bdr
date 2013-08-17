<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="ExaminationList.aspx.cs" Inherits="ExaminationList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Label ID="labelAppointmentId" runat="server" Text="labelPatientNumber"></asp:Label>
    <asp:Table ID="tableExaminations" runat="server">
        <asp:TableHeaderRow>
        </asp:TableHeaderRow>
    </asp:Table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonNewUPDRS" runat="server" Text="Dodaj nowe badanie - UPDRS" 
        onclick="buttonNewUPDRS_Click" />
    <asp:Button ID="buttonBack" runat="server" Text="Powrót" onclick="buttonBack_Click" />
</asp:Content>

