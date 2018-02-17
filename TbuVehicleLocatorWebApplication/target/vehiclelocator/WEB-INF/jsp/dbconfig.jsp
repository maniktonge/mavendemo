<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/vehiclelocator/static/style.css">
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
	<br>
	<p align="center">
		<b> <font size="5" color="#33b5e5">WelCome DataBase
				Configuration</font></b>
	</p><br> <br>
	<div align="center" color="#33b5e5">
		<div align="center">
			<font color="red">${message}</font>
		</div><br>
			<p align="center">
		<i><b></b> <font size="2" color="red">&nbsp&nbsp&nbsp&nbspNote :</font></i><b></b>
		<i><b></b> <font size="2" color="red">For Localhost DataBase Configuration,Please Use 127.0.0.1 IP Address </font></i><b></b> 
		
	</p>
		<form action="dbcreation" method="post">				
			 <input placeholder=" Admin Username " name="username" size="20" />
			 <br> <br>
			 <input type="password" placeholder=" Admin Password " name="password" size="20"/> 
			 <br> <br> <input placeholder=" 255.255.255.255 " name="serverip" size="20" /> 
			 <br> <br> <input  placeholder="3306" name="port" size="20" />
			  <br> <br> <input placeholder=" DataBase Name" name="databasename" size="20" />
	          <br> <br>
			<button>Create DataBase</button>
		</form>
	</div>


</body>
</html>