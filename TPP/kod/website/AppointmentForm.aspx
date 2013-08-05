<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="AppointmentForm.aspx.cs" Inherits="AppointmentForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/i18n/jquery.ui.datepicker-pl.min.js" type="text/javascript"></script>
        <script type="text/javascript" language="javascript">
            $(document).ready(function () {
                $("#inputDateIn").datepicker({
                    showOn: 'button',
                    buttonImage: 'img/calendar_datepicker.png',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true,
                    showAnim: 'slideDown',
                    duration: 'fast'
                });
            });
        </script>
        <script type="text/javascript" language="javascript">
            $(document).ready(function () {
                $("#inputDateOut").datepicker({
                    showOn: 'button',
                    buttonImage: 'img/calendar_datepicker.png',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true,
                    showAnim: 'slideDown',
                    duration: 'fast'
                });
            });
        </script>
    </asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <asp:Label ID="labelPatientNumber" runat="server" Text="labelPatientNumber"></asp:Label>
    <table ID="Table1" runat="server">
        <tr>
            <td>Typ wizyty:</td>
            <td>
                <asp:DropDownList ID="dropAppointmentType" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Data przyjęcia:</td>
            <td>
                <input type="text" id="inputDateIn" />
            </td>
        </tr>
        <tr>
            <td>Data wypisu:</td>
            <td>
                <input type="text" id="inputDateOut" />
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

