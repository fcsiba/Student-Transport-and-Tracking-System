<?php
include('db.php');
$response				=	array();
$response['status']		=	'error';
$response['message']	=	'Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['islogin']))&&(isset($_POST['type']))&&($_POST['islogin']==true)&&($_POST['type']==2)&&(isset($_POST['driverid']))&&(isset($_POST['b_id']))&&(isset($_POST['status']))&&(isset($_POST['current_stop']))&&(isset($_POST['previous_stop']))&&(isset($_POST['next_stop']))
)
{
	$driverid 		= $_POST['driverid'];//driver id
	$b_id 			= $_POST['b_id'];
	$current_stop 	= $_POST['current_stop'];
	$previous_stop 	= $_POST['previous_stop'];
	$next_stop 		= $_POST['next_stop'];
	$sql111 		= "Update `buses` SET `current_stop`='$current_stop',  `previous_stop`='$previous_stop',  `next_stop`='$next_stop' where id='$bid' ";
	$conn->query($sql111);
	$response['status']		= 'success';
	$response['message']	= 'Bus stops updated.' ;
	echo json_encode($response);
}
else
{
	echo json_encode($response);
}
?>