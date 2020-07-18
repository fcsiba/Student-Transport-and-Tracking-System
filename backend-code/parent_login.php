<?php
include('db.php');
$response=array();
$response['status']='error';
$response['message']='Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['cnic']))&&(isset($_POST['password']))&&($_POST['cnic']!='')&&($_POST['password']==$_POST['cnic'])&&(isset($_POST['type']))&&($_POST['type']==3))
{
	$cnic	 		= $_POST['cnic'];
	$password	 	= $_POST['password'];
	
	$sql="SELECT * FROM users where cnic='$cnic'";
	$result = mysqli_query($conn, $sql);
	if($password==$cnic)
	{
		if (mysqli_num_rows($result) > 0) 
		{
			while($row = mysqli_fetch_assoc($result)) 
			{
				$id 			= $row["id"];
				$name	 		= $row['name'];
				$password	 	= $row['password'];
				$father_name 	= $row['father_name'];
				$longitude 		= $row['longitude'];
				$latitude 		= $row['latitude'];
				$cnic 			= $row['cnic'];
				$contact_no 	= $row['contact_no'];
				$address 		= $row['address'];
				$f_contact_no 	= $row['father_contact_no'];
				$type 			= $row['type'];
				$reg_no 		= $row['reg_no'];
				$status 		= $row['status'];
			}
			$sql1="UPDATE users SET status=1 WHERE id='$id'";
			mysqli_query($conn, $sql1);
			
			$response['status']						= 'success';
			$response['message']					= 'Record is successfully inserted into the database';
			$response['data']['id']					= $id;
			$response['data']['name']				= $name;
			$response['data']['cnic']				= $cnic;
			
			$response['data']['status']				= $status;
			echo json_encode($response);
		}
		else
		{
			$response['message']='cnic you entered is incorrect';
			echo json_encode($response);
		}
	}
	else
	{
		$response['message']='password you entered is incorrect';
		echo json_encode($response);
	}
}
else
{
	echo json_encode($response);
}
?>