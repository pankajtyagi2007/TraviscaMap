<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- META SECTION -->
<title>TaviscaMapSearch</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- END META SECTION -->
<!--  BEGIN STYLE -->
<style>
table, th, td {
	border: 1px solid black;
	padding: 1px;
}
</style>
<!--  END STYLE -->

</head>
<body>
	<form action="#" method="GET">
		City:<br> <input type="text" name="city" value="Agra"> <br> 
		Category:<br> <input type="text" name="category" value="Gym"> <br>
		<br> <input type="submit" value="Submit">
	</form>

	<div>
		<h2>Search Results</h2>
		<h4>${success}</h4>
		<table>
			<thead>
				<tr>
					<th>Name</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Category</th>
					<th>latitude/longitude</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${inf}" var="temp">
					<tr>
						<c:forEach items="${temp}" var="val">
							<td>${val}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</form>
</body>
</html>
