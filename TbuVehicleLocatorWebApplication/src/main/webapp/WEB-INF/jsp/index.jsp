<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>Tata Elxsi - Engineering creativity</title>
<link rel="stylesheet" href="/vehiclelocator/static/reset.css">

<link rel='stylesheet prefetch'
	href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
<link rel='stylesheet prefetch'
	href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
<link rel="stylesheet" href="/vehiclelocator/static/style.css">
<script type="text/javascript">
	window.history.forward();
	function noBack() { window.history.forward(); }
</script>
</head>
<body onload="noBack();" 
	onpageshow="if (event.persisted) noBack();" onunload="">
	<br>
	<br>
	<!--  <img src="../WEB-INF/logo-tata.png"> -->
	  <p align="center">
		<b> <font size="6" color="#33b5e5">&nbsp&nbspTata Elxsi - Engineering Creativity</font></b>
	</p> 
	
	<br>
	<hr size="8" color="#33b5e5">
	<br>
	<br>
	<br>
	<p align="center">
		<b> <font size="5" color="#33b5e5">&nbspRemote Vehicle
				Location WebApp</font></b>
	</p>
	<!-- Form Mixin-->
	<!-- Input Mixin-->
	<!-- Button Mixin-->
	<!-- Pen Title-->
 <!-- <div class="pen-title">
  <h1>Flat Login Form</h1><span>Pen <i class='fa fa-paint-brush'></i> + <i class='fa fa-code'>
  </i> by <a href='http://andytran.me'>Andy Tran</a></span>
</div>  -->
	<br>
	<br>
	<br>
	<!-- Form Module-->
	 <div class="module form-module">
		 <div class="toggle">
		  <i class="fa fa-times fa-pencil"></i>
	 <!-- <div class="tooltip">Click Me</div> -->
		</div>
		<div class="form">
			<h2>Login to your Account</h2>
			<div><font color="red" >${message}</font></div>
			
			<form action="login" method="post">
				<input type="text" placeholder="Username" name="username"/> 
				<input type="password" placeholder="Password" name="password"/>
				<button>Login</button>
			</form>
		</div>
		   <div class="form">
    <h2>Create an account</h2>
    <form>
      <input type="text" placeholder="Username"/>
      <input type="password" placeholder="Password"/>
      <input type="email" placeholder="Email Address"/>
      <input type="tel" placeholder="Phone Number"/>
      <button>Register</button>
    </form>
  </div>
		 <!-- <div class="cta"><a href="http://andytran.me">Forgot your password?</a></div> -->
</div> 
		<!--  <script
			src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
		 <script src='http://codepen.io/andytran/pen/vLmRVp.js'></script>
	 <script src="js/index.js"></script> -->
</body>

</html>
