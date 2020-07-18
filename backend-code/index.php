<!DOCTYPE html>
<html>
<head>
<title>STATS Admin Panel</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Custom Theme files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- //Custom Theme files -->
<!-- web font -->
<link href="https://fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,700,700i" rel="stylesheet">
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- //web font -->
</head>
<?php 
	session_start();
	include "db.php";
	$_SESSION['error']=2;
	if($_SERVER['REQUEST_METHOD'] == 'POST') 
	{
		if (isset($_POST['login'])) 
		{
			$email = $_POST['email'];
			$password = $_POST['password'];
			$result = $conn->query("SELECT * FROM admin WHERE email='$email'");
			if ( $result->num_rows == 0 )
			{
				$_SESSION['message'] 	= "Admin with that email doesn't exist!";
				$_SESSION['error'] 		= 1;
			}
			else 
			{ 
				$user = $result->fetch_assoc();
				if ($password== base64_decode($user['password'])) 
				{
					$_SESSION['id'] = $user['id'];
					$_SESSION['email'] = $user['email'];
					$_SESSION['name'] = $user['name'];
					$_SESSION['logged_in'] = true;
					$_SESSION['message'] 	= "Successfull";
					$_SESSION['error'] 		= 0;
					header("location: Admin-panel/admin/dashboard.php");
				}
				else 
				{
					$_SESSION['error'] 		= 1;
					$_SESSION['message'] = "You have entered wrong password, try again!";
				}
			}
		}
	}
?>
<body>
	<!-- main -->
	<div class="main-w3layouts wrapper">
		
		<h3 class="h3-sign">STATS Administrator Sign In</h3>
		
		<div class="main-agileinfo">
			<div class="agileits-top">
			<?php
			if((isset($_SESSION['error']))&&($_SESSION['error']==1))
			{
				?>
				 <div class="alert alert-danger fade in">
				 <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Error!</strong> <?=$_SESSION['message']?>
				 </div>
				<?php
			}
		
			?>
				<!-- Login Form Investor -->
				<form action="index.php" method="post">
					<input class="text email" type="email" name="email" placeholder="Email" required="">
					<input class="text" type="password" name="password" placeholder="Password" required="">
					<input type="submit" name="login" value="LOG IN">
				</form>
				
			</div>
		</div>
		<!-- copyright -->
		<div class="colorlibcopy-agile">
			<p>© 2020 STATS. All rights reserved </p>
		</div>
		<!-- //copyright -->
		
	</div>
	<!-- //main -->
</body>
</html>