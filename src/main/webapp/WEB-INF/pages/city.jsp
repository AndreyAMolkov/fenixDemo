<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cities</title>
</head>
<body>
	<div align="center">
		<h1>cities</h1>
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
			<input name="id" placeholder="input id city">
			<input type="hidden" name="nameClazz" value="city">
			<input type="submit" value="delete" form="delete" id="delete"
				style="width: 85px;">
		</form:form>
		<form:form id="deleteAllCities" action="/Fenix/remove/dataOfOneEntity"
			method="post">
			<input type="hidden" name="nameClazz" value="city">
			<input type="submit" value="delete all cities" form="deleteAllCities"
				id="deleteAllCities" style="width: 160px;">
		</form:form>
		<form:form id="deleteAllDataInDb" action="/Fenix/remove/dataAllInDb"
			method="post">
			<input type="submit" value="delete all datas in db"
				form="deleteAllDataInDb" id="deleteAllDataInDb"
				style="width: 160px;">
		</form:form>
		<table border="1" style="width: 768px;">

			<tr>
				<td style="width: 30px;">Id</td>
				<td style="width: 40px;">name</td>
				<td style="width: 40px;">Store</td>
				<td style="width: 20px;">Number of stores</td>
				<td style="width: 30px;">Coach</td>
				<td style="width: 20px;">Number of coaches</td>
			</tr>
			<c:forEach var="city" items="${listCities}" varStatus="i">
				<tr>
					<td style="width: 40px;">${city.getId()}</td>
					<td style="width: 40px;">${city.getName()}</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="store" items="${city.getStoreListAll()}"
									varStatus="i">
									<option value=i>"${store.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td style="width: 20px;">${city.getStoreListAll().size()}</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="coach" items="${city.getCoachListAll()}"
									varStatus="i">
									<option value=i>"${coach.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td style="width: 20px;">${city.getCoachListAll().size()}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
