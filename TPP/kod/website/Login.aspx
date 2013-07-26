<%@ Page Title="Zaloguj do TPP" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Login.aspx.cs" Inherits="Login" %>

<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <asp:Label ID="lblMessage" runat="server" Text=""></asp:Label>
    <div style="text-align: center;">
        <div style="width: 400px; margin-left: auto; margin-right:auto;">
            <asp:Login ID="AppLogin" runat="server"
                        DestinationPageUrl="~/Main.aspx"
                            
                TitleText="Proszę podać nazwę użytkownika i hasło." 
                onauthenticate="AppLogin_Authenticate" DisplayRememberMe="False" 
                UserNameLabelText="Użytkownik:" PasswordLabelText="Hasło:" 
                LoginButtonText="Zaloguj" 
                FailureText="Próba zalogowania nie powiodła się. <br />Proszę spróbować ponownie.">
            </asp:Login>
        </div>
    </div>
</asp:Content>
