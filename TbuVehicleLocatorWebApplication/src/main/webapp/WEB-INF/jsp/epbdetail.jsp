<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tata Elxsi - Engineering creativity</title>
<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</script>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();"
	onunload="">
	<br>
	<br>
	<p align="center">
		<b> <font size="6" color="#33b5e5">&nbsp&nbspTata Elxsi -
				Engineering Creativity</font></b>
	</p>
	<br>
	<hr size="8" color="#33b5e5">
	<br>
	<div align="right">
		<a href="logout" class="btn btn-info btn-lg"> <span
			class="glyphicon glyphicon-off"></span>Logout
		</a>
	</div>
	<div align="left">
		<a href="home" class="btn btn-info btn-lg"> <span
			class="glyphicon glyphicon-off"></span>Home
		</a>
	</div>
	<!-- <p align="left">
		<b> <font size="5" color="grey">&nbsp&nbspVehicle Locator App</font></b>
	</p>  -->
	<br>
	<div align="center">
		<p align="center">
			<b> <font size="5" color="grey">Registered Head Unit
					Details</font></b>
		</p>
		<div>
			<font color="red">${message}</font>
		</div>
		<table border="1">
		<!-- <from action="deleteUser" method="post"> -->
			<th bgcolor="#5D7B9D"><font color="#fff">EPB Id</th>
			<th bgcolor="#5D7B9D"><font color="#fff">User Id</th>
			<th bgcolor="#5D7B9D"><font color="#fff">Car Name</th>
			<th bgcolor="#5D7B9D"><font color="#fff">HDUUID</th>
			<th bgcolor="#5D7B9D"><font color="#fff">AppVersion</th>
			<!-- <th bgcolor="#5D7B9D"><font color="#fff">GCM Reg Key</th> -->
			<th bgcolor="#5D7B9D"><font color="#fff">Created Date</th>
			<th bgcolor="#5D7B9D"><font color="#fff">Action</th>
			<c:forEach var="hdunit" items="${listepb}">
				<tr>
					<td>${hdunit.epb_detail_id}</td>
					<td>${hdunit.header}</td>
					<td>${hdunit.MT}</td>
					<td>${hdunit.IMEI}</td>
					<td>${hdunit.PT}</td>
					<%-- <td>${hdunit.gcmregistartionkey}</td> --%>
					<td>${hdunit.date}</td>
						<td>${hdunit.gpsvalidity }</td>
							<td>${hdunit.Lattitude}</td>
							<td>${hdunit.Longitude}</td>
							<td>${hdunit.Latdir}</td>
							<td>${hdunit.Longdir}</td>
							<td>${hdunit.alt}</td>
							<td>${hdunit.spedd}</td>
							<td>${hdunit.VRN}</td>
							<td>${hdunit.provider}</td>
				</tr>
			</c:forEach>
			</from>
		</table>
	</div>


</body>