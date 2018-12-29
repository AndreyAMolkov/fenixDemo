<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee</title>
</head>
<body>
	<div align="center">
		<h1>Employee</h1>
		<!--  <a href="<c:url value="/logout?logout=true" />">Logout</a>-->

		<form action="/Fenix/entity" method="POST">
            <p>Check pages</p>
            <select name="nameEntity">
              <option value="coach">go to coach</option>
                <option value="employee">go to employee</option>
                <option value="city">go to city</option>
                <option value="store">go to store</option>
                <option value="day">go to day</option>
                <option value="month">go to month</option>
                <option value="quarter">go to quarter</option>
                <option value="halfYear">go to halfYear</option>
                <option value="year">go to year</option>
            </select> <input type="submit" value="go" style="width: 85px;">
        </form>
		<c:if test="${message != null}">
			<div style="color: red; font-style: italic">${message.getCause()}</div>
		</c:if>
		<form id="download" action="/Fenix/prepare/populateDB" method="post">
			<input name="id" placeholder="id of client"> <input
				type="submit" value="download" form="download" id="download"
				style="width: 85px;">
		</form>

		<!--  <td>number of line for output</td>-->
		<form:form id="delete" action="/Fenix/remove/entity" method="post">
			<input name="id" placeholder="input id identity of employee">
			<input type="hidden" name="nameClazz" value="employee">
			<input type="submit" value="delete" form="delete" id="delete"
				style="width: 85px;">
		</form:form>
		<form:form id="deleteAllEmployees"
			action="/Fenix/remove/dataOfOneEntity" method="post">
			<input type="hidden" name="nameClazz" value="employee">
			<input type="submit" value="delete all employees"
				form="deleteAllEmployees" id="deleteAllEmployees"
				style="width: 160px;">
		</form:form>
		<form:form id="deleteAllDataInDb" action="/Fenix/remove/dataAllInDb"
			method="post">
			<input type="submit" value="delete all datas in db"
				form="deleteAllDataInDb" id="deleteAllDataInDb"
				style="width: 160px;">
		</form:form>
		<table border="1" style="width: 1900px;">

			<tr>
				<td style="width: 30px;">Id Identity</td>
				<td style="width: 40px;">Id form file</td>
				<td style="width: 40px;">name</td>
				<td style="width: 40px;">City</td>
				<td style="width: 40px;">Store</td>
				<td style="width: 30px;">Coach</td>
				<td style="width: 30px;">Date dismissial</td>
				<td style="width: 30px;">Period of work</td>
				<td style="width: 30px;">number of day</td>
				<td style="width: 900px;">days</td>
				<td style="width: 900px;">days id</td>
			</tr>
			<c:forEach var="empl" items="${listEmployees}" varStatus="i">
				<tr>
					<td style="width: 30px;">${empl.getId()}</td>
					<td style="width: 40px;">${empl.getIdFromFile()}</td>
					<td style="width: 40px;">${empl.getName()}</td>
					<td style="width: 40px;">${empl.getCityName()}</td>
					<td style="width: 40px;">${empl.getStoreName()}</td>
					<td style="width: 40px;">${empl.getCoachName()}</td>
					<td style="width: 40px;">${empl.getDataDismissal()}</td>
					<td style="width: 40px;">${empl.getDayOfwork()}</td>
					<td style="width: 40px;">${empl.getDayListAll().size()}</td>
					<td style="width: 900px;">${empl.printDay()}</td>
					<td style="width: 900px;">${empl.printDayID()}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
