<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tata Elxsi - Engineering creativity</title>
<script type="text/javascript">
	window.history.forward();
	function noBack() { window.history.forward(); }
</script>
</head>
<body onload="noBack();" 
	onpageshow="if (event.persisted) noBack();" onunload="">

	<br>
	<br>
	<p align="center">
		<b> <font size="6" color="#33b5e5">&nbsp&nbspTata Elxsi -
				Engineering Creativity</font></b>
	</p>
	<br>
	<hr size="8" color="#33b5e5">
	<br>
	<!-- <p align="left">
		<b> <font size="5" color="grey">&nbsp&nbspVehicle Locator App</font></b>
	</p>  -->
	<div align="right"><a href="logout" class="btn btn-info btn-lg">
          <span class="glyphicon glyphicon-off"></span>Logout</a></div>
   <div align="left"><a href="home" class="btn btn-info btn-lg">
          <span class="glyphicon glyphicon-off"></span>Home</a></div>
	<br>
	<div align="center">
		<p align="center">
			<b> <font size="5" color="grey">Registered User	Details</font></b>
		</p>
		<div><font color="red" >${message}</font></div>
		<table border="1">
		<!-- <from action="deleteUser" method="post"> -->
			<th bgcolor="#5D7B9D"><font color="#fff">User Id</th>
			<th bgcolor="#5D7B9D"><font color="#fff">User Name</th>
			<th bgcolor="#5D7B9D"><font color="#fff">Email Id</th>
			<th bgcolor="#5D7B9D"><font color="#fff">Mobile No</th>
			<th bgcolor="#5D7B9D"><font color="#fff">UUID</th>
			<th bgcolor="#5D7B9D"><font color="#fff">AppVersion</th>
			<!-- <th bgcolor="#5D7B9D"><font color="#fff">GCM Reg Key</th> -->
			<th bgcolor="#5D7B9D"><font color="#fff">Created Date</th>
			<th bgcolor="#5D7B9D"><font color="#fff">Action</th>

			<c:forEach var="user" items="${listUser}" >
			
				<tr>
					<td>${user.userid}</td>
					<td>${user.username}</td>
					<td>${user.emailid}</td>
					<td>${user.mobileno}</td>
					<td>${user.uuid}</td>
					<td>${user.appversion}</td>
					<%-- <td>${user.gcmregistartionkey}</td> --%>
					<td>${user.createddate}</td>
			    	 <td><a href="deleteUser?id=${user.userid}">Delete</a></td>
					<%-- <td>
					<button type="submit" name="id" value="${user.userid}" class="btn-link">Delete</button>
					</td> --%>
				</tr>
			</c:forEach>
			</from>
		</table>
	</div>


</body>