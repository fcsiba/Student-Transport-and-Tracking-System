<?php
include('db.php');
$response=array();
$response['status']='error';
$response['message']='Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['islogin']))&&(isset($_POST['type']))&&($_POST['islogin']==true)&&($_POST['type']==1)&&(isset($_POST['s_id']))&&(isset($_POST['b_id']))&&(isset($_POST['checkin']))&&($_POST['checkin']==true))
{
	$s_id 			= $_POST['s_id'];//student id
	$b_id 			= $_POST['b_id'];
	$sql			= "UPDATE `users` SET ``status`=2 WHERE `id`='$s_id'";
	$result 		= mysqli_query($conn, $sql);
	$response['status']='success';
	$response['message']='Got Bus.';
	echo json_encode($response);
}
else
{
	echo json_encode($response);
}
?>