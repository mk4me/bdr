﻿<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="AppointmentForm.aspx.cs" Inherits="AppointmentForm" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/i18n/jquery.ui.datepicker-pl.min.js" type="text/javascript"></script>
        <script type="text/javascript" language="javascript">
            $(document).ready(function () {
                $("#<%= textDateIn.ClientID %>").datepicker({
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
                $("#<%= textDateOut.ClientID %>").datepicker({
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
            <td>Rodzaj wizyty:</td>
            <td>
                <asp:DropDownList ID="dropAppointmentType" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Data przyjęcia:</td>
            <td>
                <asp:TextBox ID="textDateIn" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Data wypisu:</td>
            <td>
                <asp:TextBox ID="textDateOut" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>Wykształcenie:</td>
            <td>
                <asp:DropDownList ID="dropEducation" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Rodzinność:</td>
            <td>
                <asp:DropDownList ID="dropFamily" runat="server">
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Miesiąc i rok zachorowania:</td>
            <td>
                <asp:DropDownList ID="dropMonth" runat="server">
                </asp:DropDownList>
                <asp:DropDownList ID="dropYear" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Pierwszy objaw:</td>
            <td>
                <asp:DropDownList ID="dropSymptom" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Czas od począku objawów do włączenia LDopy:</td>
            <td>
                <asp:TextBox ID="textTimeSymptom" runat="server"></asp:TextBox>
                <asp:RegularExpressionValidator Runat="server" ID="RegularExpressionValidator1" 
                    ControlToValidate="textTimeSymptom"
                    ErrorMessage="Proszę podać liczbę od 0 do 255."
                    ValidationExpression="^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td>Dyskinezy obecnie:</td>
            <td>
                <asp:DropDownList ID="dropDiskinesia" runat="server" 
                    onselectedindexchanged="dropDiskinesia_SelectedIndexChanged" 
                    AutoPostBack="True">
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Czas dyskinez:</td>
            <td>
                <asp:TextBox ID="textTimeDiskinesia" runat="server"></asp:TextBox>
                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" 
                    ErrorMessage="Proszę podać liczbę od 0 do 99.9."
                    ValidationExpression="^[0-9]{1,2}(?:\.[0-9]{1})?$"
                    ControlToValidate="textTimeDiskinesia"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td>Fluktuacje obecnie:</td>
            <td>
                <asp:DropDownList ID="dropFluctuations" runat="server"
                    onselectedindexchanged="dropFluctuations_SelectedIndexChanged" 
                    AutoPostBack="True">
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Fluktuacje od lat:</td>
            <td>
                <asp:TextBox ID="textYearsFluctuations" runat="server"></asp:TextBox>
                <asp:RegularExpressionValidator ID="RegularExpressionValidator3" runat="server" 
                    ErrorMessage="Proszę podać liczbę od 0 do 99.9."
                    ValidationExpression="^[0-9]{1,2}(?:\.[0-9]{1})?$"
                    ControlToValidate="textYearsFluctuations"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td>Papierosy:</td>
            <td>
                <asp:DropDownList ID="dropCigarettes" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Kawa:</td>
            <td>
                <asp:DropDownList ID="dropCoffee" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Zielona herbata:</td>
            <td>
                <asp:DropDownList ID="dropGreenTea" runat="server">
                    <asp:ListItem Value="1">Tak</asp:ListItem>
                    <asp:ListItem Value="0">Nie</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Alkohol:</td>
            <td>
                <asp:DropDownList ID="dropAlcohol" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Liczba zabiegów w znieczuleniu ogólnym przed rozpoznaniem PD:</td>
            <td>
                <asp:TextBox ID="textTreatmentNumber" runat="server"></asp:TextBox>
                <asp:RegularExpressionValidator Runat="server" ID="RegularExpressionValidator4" 
                    ControlToValidate="textTreatmentNumber"
                    ErrorMessage="Proszę podać liczbę od 0 do 255."
                    ValidationExpression="^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td>Zamieszkanie:</td>
            <td>
                <asp:DropDownList ID="dropPlace" runat="server">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td>Narażenie na toksyczność:</td>
            <td>
                <asp:DropDownList ID="dropToxic" runat="server">
                </asp:DropDownList>
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
