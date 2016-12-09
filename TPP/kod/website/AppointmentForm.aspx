<%@ Page Title="Dane demograficzne i objawy ruchowe" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="AppointmentForm.aspx.cs" Inherits="AppointmentForm" %>

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
                $("#<%= textDateSurgery.ClientID %>").datepicker({
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
    <h2>Dane demograficzne i objawy ruchowe</h2>
    <br />
    <table ID="Table1" runat="server">
        <tr>
            <td>
                <table ID="Table2" runat="server">
                    <tr>
                        <td><asp:Label ID="labelPatientNumber" runat="server" Text=""></asp:Label></td>
                    </tr>
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
                        <td>
                            <asp:Label ID="labelDateSurgery" runat="server" Text="Data operacji:"></asp:Label>
                        </td>
                        <td>
                            <asp:TextBox ID="textDateSurgery" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td>Masa ciała:</td>
                        <td>
                            <asp:TextBox ID="textMasaCiala" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelEducation" runat="server" Text="Wykształcenie:"></asp:Label>
                        </td>
                        <td>
                            <asp:DropDownList ID="dropEducation" runat="server">
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelFamily" runat="server" Text="Rodzinność:"></asp:Label>
                        </td>
                        <td>
                            <asp:DropDownList ID="dropFamily" runat="server">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelRokZachorowania" runat="server" Text="Rok zachorowania:"></asp:Label>
                        </td>
                        <td>
                            <asp:DropDownList ID="dropRokZachorowania" runat="server">
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelCzasTrwaniaChoroby" runat="server" Text="Czas trwania choroby:"></asp:Label>
                        </td>
                        <td>
                            <asp:Label ID="labelCzasTrwaniaChorobyText" runat="server" Text=""></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelSymptom" runat="server" Text="Pierwszy objaw:"></asp:Label>
                        </td>
                        <td>
                            <asp:DropDownList ID="dropSymptom" runat="server">
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>Drżenie:</td>
                        <td>
                            <asp:DropDownList ID="dropDrzenie" runat="server">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>Sztywność:</td>
                        <td>
                            <asp:DropDownList ID="dropSztywnosc" runat="server">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>Spowolnienie:</td>
                        <td>
                            <asp:DropDownList ID="dropSpowolnienie" runat="server">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>Objawy inne:</td>
                        <td>
                            <asp:DropDownList ID="dropObjawy" runat="server">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>Objawy inne. Jakie?:</td>
                        <td>
                            <asp:TextBox ID="textObjawy" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelTimeSymptom" runat="server" Text="Czas od począku objawów do<br />włączenia LDopy [mies.]:"></asp:Label>
                        </td>
                        <td>
                            <asp:TextBox ID="textTimeSymptom" runat="server"></asp:TextBox>
                            <%--
                            <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
                                ErrorMessage="*" ControlToValidate="textTimeSymptom"></asp:RequiredFieldValidator>
                            <asp:RegularExpressionValidator Runat="server" ID="RegularExpressionValidator1" 
                                ControlToValidate="textTimeSymptom"
                                ErrorMessage="Proszę podać liczbę od 0 do 255."
                                ValidationExpression="^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$"></asp:RegularExpressionValidator>
                             --%>
                        </td>
                    </tr>
                    <tr>
                        <td>Dyskinezy obecnie:</td>
                        <td>
                            <asp:DropDownList ID="dropDiskinesia" runat="server" 
                                onselectedindexchanged="dropDiskinesia_SelectedIndexChanged" 
                                AutoPostBack="True">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelTimeDiskinesia" runat="server" Text="Dyskinezy od lat:"></asp:Label>
                        </td>
                        <td>
                            <asp:TextBox ID="textTimeDiskinesia" runat="server"></asp:TextBox>
                            <%--
                            <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" 
                                ErrorMessage="*" ControlToValidate="textTimeDiskinesia"></asp:RequiredFieldValidator>
                            <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" 
                                ErrorMessage="Proszę podać liczbę od 0 do 99.9."
                                ValidationExpression="^[0-9]{1,2}(?:\.[0-9]{1})?$"
                                ControlToValidate="textTimeDiskinesia"></asp:RegularExpressionValidator>
                            --%>
                        </td>
                    </tr>
                    <tr>
                        <td>Fluktuacje obecnie:</td>
                        <td>
                            <asp:DropDownList ID="dropFluctuations" runat="server"
                                onselectedindexchanged="dropFluctuations_SelectedIndexChanged" 
                                AutoPostBack="True">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Label ID="labelYearsFluctuations" runat="server" Text="Fluktuacje od lat:"></asp:Label>
                        </td>
                        <td>
                            <asp:TextBox ID="textYearsFluctuations" runat="server"></asp:TextBox>
                            <%--
                            <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" 
                                ErrorMessage="*" ControlToValidate="textYearsFluctuations"></asp:RequiredFieldValidator>
                            <asp:RegularExpressionValidator ID="RegularExpressionValidator3" runat="server" 
                                ErrorMessage="Proszę podać liczbę od 0 do 99.9."
                                ValidationExpression="^[0-9]{1,2}(?:\.[0-9]{1})?$"
                                ControlToValidate="textYearsFluctuations"></asp:RegularExpressionValidator>
                            --%>
                        </td>
                    </tr>
                    <tr>
                        <td>Czas dyskinez:</td>
                        <td>
                            <asp:TextBox ID="textCzasDyskinez" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td>Czas OFF:</td>
                        <td>
                            <asp:TextBox ID="textCzasOFF" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td>Poprawa po LDopie:</td>
                        <td>
                            <asp:DropDownList ID="dropPoprawa" runat="server">
                                <asp:ListItem Value="2" Text=""></asp:ListItem>
                                <asp:ListItem Value="0">nie</asp:ListItem>
                                <asp:ListItem Value="1">tak</asp:ListItem>
                            </asp:DropDownList>
                        </td>
                    </tr>
                    <tr>
                        <td>Lateralizacja:</td>
                        <td>
                            <asp:DropDownList ID="dropLateralizacja" runat="server">
                            </asp:DropDownList>
                        </td>
                    </tr>
                </table>
            </td>
            <td align="center" valign="top" style="padding-left: 100px;">
                <table ID="Table3" runat="server">
                    <tr>
                        <td>
                            <asp:Label ID="labelAppointment" runat="server" Text=""></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonPartB" runat="server" Text="Objawy i epidemiologia" 
                                Width="265px" onclick="buttonPartB_Click" CausesValidation="false" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonPartC" runat="server" Text="Przyjmowane leki" 
                                Width="265px" onclick="buttonPartC_Click" CausesValidation="false" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonPartE" runat="server" Text="Kwestionariusz zaburzeń autonomicznych" 
                                Width="265px" onclick="buttonPartE_Click" CausesValidation="false" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonPartF" runat="server" Text="UPDRS i skale oceny klinicznej" 
                                Width="265px" onclick="buttonPartF_Click" CausesValidation="false" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonPartG" runat="server" Text="Badania i testy psychologiczne" 
                                Width="265px" onclick="buttonPartG_Click" CausesValidation="false" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <asp:Button ID="buttonPartH" runat="server" Text="Badania dodatkowe" 
                                Width="265px" onclick="buttonPartH_Click" CausesValidation="false" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <asp:Label ID="labelMessage" runat="server" Text=""></asp:Label>
    <br />
    <asp:Button ID="buttonOK" runat="server" Text="Zatwierdź" 
        onclick="buttonOK_Click" />
    <asp:Button ID="buttonCancel" runat="server" Text="Powrót" 
        CausesValidation="False" onclick="buttonCancel_Click" />
    <asp:Label ID="labelSaved" runat="server" Text=""></asp:Label>
</asp:Content>

