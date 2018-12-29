<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Month</title>
</head>
<body>
	<div align="center">
		<h1>Month</h1>
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
		<form id="download" action="/Fenix/prepare/populateMonth" method="post">
			<input type="hidden" name="nameClazz" value="month"> <input
				type="submit" value="populate month data base" form="download"
				id="download" style="width: 180px;">
		</form>

		<!--  <td>number of line for output</td>-->
		<form:form id="delete" action="/Fenix/remove/entity" method="post">
			<input name="id" placeholder="input id monyh">
			<input type="hidden" name="nameClazz" value="month">
			<input type="submit" value="delete" form="delete" id="delete"
				style="width: 85px;">
		</form:form>
		<form:form id="deleteAllMonth" action="/Fenix/remove/dataOfOneEntity"
			method="post">
			<input type="hidden" name="nameClazz" value="month">
			<input type="submit" value="delete all month" form="deleteAllMonth"
				id="deleteAllMonth" style="width: 160px;">
		</form:form>
		<form:form id="deleteAllDataInDb" action="/Fenix/remove/dataAllInDb"
			method="post">
			<input type="submit" value="delete all datas in db"
				form="deleteAllDataInDb" id="deleteAllDataInDb"
				style="width: 160px;">
		</form:form>
		<table border="1" style="width: 1268px;">

			<tr>
				<td style="width: 30px;">Id</td>
				<td style="width: 90px;">name</td>
				<td style="width: 40px;">days</td>
				<td style="width: 40px;">Employee all</td>
				<td style="width: 20px;">Number of employees</td>
				<td style="width: 40px;">Employee current</td>
                <td style="width: 20px;">Number of current employees</td>
                <td style="width: 40px;">Employee out of work</td>
                <td style="width: 20px;">Number of out of work employees</td>
				<td style="width: 30px;">Coach</td>
				<td style="width: 20px;">Number of coaches</td>
				<td style="width: 40px;">City</td>
				<td style="width: 20px;">Number of cities</td>
				<td style="width: 40px;">Store</td>
				<td style="width: 20px;">Number of stores</td>

			</tr>
			<c:forEach var="month" items="${listMonths}" varStatus="i">
				<tr>
					<td style="width: 30px;">${month.getId()}</td>
					<td style="width: 90px;">${month.getName()}</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="day" items="${month.getDayListAll()}"
									varStatus="i">
									<option value=i>"${day.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="empl" items="${month.getEmployeeListAll()}"
									varStatus="i">
									<option value=i>"${empl.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td style="width: 20px;">${month.getEmployeeListAll().size()}</td>
					<td>
                        <form action="" method="POST">
                            <select name="form_select">
                                <c:forEach var="empl" items="${month.getEmployeeCurrentListAll()}"
                                    varStatus="i">
                                    <option value=i>"${empl.getName()}"</option>
                                </c:forEach>

                            </select>
                        </form>
                    </td>
                    <td style="width: 20px;">${month.getEmployeeCurrentListAll().size()}</td>
                    <td>
                        <form action="" method="POST">
                            <select name="form_select">
                                <c:forEach var="empl" items="${month.getEmployeeOutOfWorkListAll()}"
                                    varStatus="i">
                                    <option value=i>"${empl.getName()}"</option>
                                </c:forEach>

                            </select>
                        </form>
                    </td>
                    <td style="width: 20px;">${month.getEmployeeOutOfWorkListAll().size()}</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="coach" items="${month.getCoachListAll()}"
									varStatus="i">
									<option value=i>"${coach.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td style="width: 20px;">${month.getCoachListAll().size()}</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="city" items="${month.getCityListAll()}"
									varStatus="i">
									<option value=i>"${city.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td style="width: 20px;">${month.getCityListAll().size()}</td>
					<td>
						<form action="" method="POST">
							<select name="form_select">
								<c:forEach var="store" items="${month.getStoreListAll()}"
									varStatus="i">
									<option value=i>"${store.getName()}"</option>
								</c:forEach>

							</select>
						</form>
					</td>
					<td style="width: 20px;">${month.getStoreListAll().size()}</td>

				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
