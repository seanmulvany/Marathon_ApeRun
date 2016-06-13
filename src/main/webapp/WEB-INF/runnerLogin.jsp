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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width = device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=no;">
<link rel="stylesheet" href="/css/map.css" type="text/css" />

<style type="text/css">
.error {
    color: red
}

;
.success {
    color: green
}
;
</style>
<script language="javascript">
function lowerCase(field) {
    field.value = field.value.toLowerCase();
}
</script>
</head>

<body>
    <div>
        Login
        <form:form name="login" commandName="runnerCredentials" action="runnerLogin">
            <form:errors class="error" />
            <p>
                Name
                <form:input path="id" />
                <form:errors path="id" class="error" />
            </p>
            <p>
                PIN
                <form:input type="tel" path="pin" />
                <form:errors path="pin" class="error" />
            </p>
            <input type="submit" value="Login" />
        </form:form>
    </div>
</body>
</html>
