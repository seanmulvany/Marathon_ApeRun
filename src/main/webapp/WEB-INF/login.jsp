<%--
    Copyright (C) 2012 apeworks

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="CACHE-CONTROL" CONTENT="NO-CACHE" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" href="/css/map.css" type="text/css" />
<link href='http://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Sigmar+One' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="/js/jquery.js" charset="utf-8"></script>
<script type="text/javascript" src="/js/jquery.corners.js" charset="utf-8"></script>

<script language="javascript">
var emailRegex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

function checkEmail() {
    $('#success-message').html('');
    $('.error').html('');
    lowerCase('#register-email');
    lowerCase('#confirm-email');
    
    if ($('#register-email').val() != $('#confirm-email').val()) {
        $('#email-error').html("Emails don't match");
        return false;
    }
    
    if (!emailRegex.test($('#register-email').val())) {
        $('#email-error').html("Please enter a valid email address");
        return false;
    }
}

function lowerCase(field) {
    $(field).val($(field).val().toLowerCase());
}

function main() {
    $(".rounded").corners();
}
</script>
</head>

<body onload="main();">
    <center>
        <div class="centre-panel">
            <div>
                <div class="panel left-login-panel rounded">
                    <div class="banner-text">Register</div>
                    <div>${registrationMessage}</div>
                    <form:form name="register" commandName="donor" action="register" acceptCharset="utf8" onsubmit="return checkEmail();">
                        <p>
                        <div class="form-label">name</div>
                        <form:input path="name" class="form-input" />
                        <form:errors path="name" class="error" />
                        </p>
                        <p>
                        <div class="form-label">email</div>
                        <form:input id="register-email" path="email" class="form-input" />
                        <form:errors path="email" class="error" />
                        </p>
                        <p>
                        <div class="form-label">confirm email</div>
                        <input id="confirm-email" class="form-input" type="text" />
                        <div id="email-error" class="error"></div>
                        </p>
                        <input type="submit" ${registrationDisabledAttribute} value="Register" />
                        <br />
                        <div id="success-message" class="success">${successMessage}</div>
                    </form:form>
                </div>

                <div class="panel right-login-panel rounded">
                    <div class="banner-text">Login</div>
                    <form:form name="login" commandName="donorCredentials" action="login">
                        <form:errors class="error" />
                        <p>
                        <div class="form-label">email</div>
                        <form:input id="login-email" path="email" class="form-input" />
                        <form:errors path="email" class="error" />
                        </p>
                        <p>
                        <div class="form-label">PIN</div>
                        <form:input path="pin" class="form-input" />
                        <form:errors path="pin" class="error" />
                        </p>
                        <p>
                            <input type="submit" value="Login" onclick="lowerCase($('#login-email'));" />
                        </p>
                    </form:form>
                </div>
            </div>
        </div>
    </center>
</body>
</html>
