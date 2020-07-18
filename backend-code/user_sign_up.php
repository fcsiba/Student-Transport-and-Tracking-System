<?php
include('db.php');
$response=array();
if((isset($_POST['name']))&&(isset($_POST['cnic']))&&(isset($_POST['contact_no']))&&(isset($_POST['address']))&&(isset($_POST['father_name']))&&(isset($_POST['father_contact_no']))&&(isset($_POST['reg_no']))&&(isset($_POST['type']))&&(isset($_POST['password']))&&(isset($_POST['longitude']))&&(isset($_POST['latitude'])))
{
	$name	 		= $_POST['name'];
	$password	 	= $_POST['password'];
	$father_name 	= $_POST['father_name'];
	$longitude 		= $_POST['longitude'];
	$latitude 		= $_POST['latitude'];
	$cnic 			= $_POST['cnic'];
	$contact_no 	= $_POST['contact_no'];
	$address 		= $_POST['address'];
	$f_contact_no 	= $_POST['father_contact_no'];
	$type 			= $_POST['type'];
	$reg_no 		= $_POST['reg_no'];
	$sql="INSERT INTO users(name, cnic, password, latitude, longitude, contact_no, address, father_name, father_contact_no, reg_no, type, status) VALUES ('$name', '$cnic', '$password', '$latitude', '$longitude', '$contact_no', '$address', '$father_name', '$f_contact_no', '$reg_no', '$type', '0')";
	mysqli_query($conn, $sql);
	$response['status']='success';
	$response['message']='Record is successfully inserted into the database';
	echo json_encode($response);
}
else
{
	$response['status']='error';
	$response['message']='Opps Something went wrong. Please fill all feilds.';
	echo json_encode($response);
}
?>