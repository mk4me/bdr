<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeFile="Default.aspx.cs" Inherits="_Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        Strona Powitalna
    </h2>
    <p>
        Pacjenci <a href="Main.aspx" title="Main.aspx">Main.aspx</a>.
    </p>
    <p>
        Nowa wizyta <a href="AppointmentForm.aspx"
            title="AppointmentForm.aspx">AppointmentForm.aspx</a>.
    </p>
    <p>
        Nowa wizyta - wywiad <a href="ExaminationForm.aspx"
            title="ExaminationForm.aspx">ExaminationForm.aspx</a>.
    </p>
    <p>
        Leki <a href="MedicineForm.aspx"
            title="MedicineForm.aspx">MedicineForm.aspx</a>.
    </p>
</asp:Content>
