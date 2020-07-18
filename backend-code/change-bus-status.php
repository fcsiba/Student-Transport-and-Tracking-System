<?php
include('db.php');
$response				=	array();
$response['status']		=	'error';
$response['message']	=	'Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['islogin']))&&(isset($_POST['type']))&&($_POST['islogin']==true)&&($_POST['type']==2)&&(isset($_POST['driverid']))&&(isset($_POST['b_id']))&&(isset($_POST['status'])))
{
	$driverid 			= $_POST['driverid'];//driver id
	$b_id 			= $_POST['b_id'];
	$status 		= $_POST['status'];
	$sql111 = "Update `buses` SET `dr_id`='$driverid',  `status`='$status' where id='$bid' ";
	$conn->query($sql111);
	$response['status']		= 'success';
	$response['message']	= 'Driver with id'.$driverid.' drive bus id '.$b_id ;
	echo json_encode($response);
}
else
{
	echo json_encode($response);
}
?>