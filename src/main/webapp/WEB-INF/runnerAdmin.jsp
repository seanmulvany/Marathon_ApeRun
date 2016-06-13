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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

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
</head>

<body>
    <div>
        <div style="float: left">
            Create a new runner:
            <form:form name="login" commandName="runnerCredentials" action="runnerAdmin">
                <form:errors class="error" />
                <p>
                    Name
                    <form:input path="id" />
                    <form:errors path="id" class="error" />
                </p>
                <input type="submit" value="Create" />
                <p>
                    Existing runners:<br />
                    <form:select style="width: 300px" path="" size="10">
                        <c:forEach items="${runners}" var="runner">
                            <option title="${runner.pin}">
                                <c:out value="id: ${runner.id}, pin: ${runner.pin}" />
                            </option>
                        </c:forEach>
                    </form:select>
                </p>
            </form:form>
        </div>
    </div>
</body>
</html>
