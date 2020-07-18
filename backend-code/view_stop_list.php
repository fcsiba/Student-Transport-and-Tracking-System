<?php
include('db.php');
$response=array();
$response['status']='error';
$response['message']='Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['islogin']))&&(isset($_POST['type']))&&($_POST['islogin']==true)&&($_POST['type']==1))
{
	$sql="SELECT * FROM stops";
	$result = mysqli_query($conn, $sql);
	if (mysqli_num_rows($result) > 0) 
	{
		$i=1;
		while($row = mysqli_fetch_assoc($result)) 
		{
			$response['data'][$i]['id']				= $row["id"];
			$response['data'][$i]['name']	 		= $row['stop_name'];
			$response['data'][$i]['password']	 	= $row['stop_lat'];
			$response['data'][$i]['father_name']	= $row['stop_lng'];
			
			$i++;
		}
		
		$response['status']						= 'success';
		$response['message']					= 'Successfully get data';
		echo json_encode($response);
	}
	else
	{
		$response['message']='No stop exist in database';
		echo json_encode($response);
	}
}
else
{
	echo json_encode($response);
}
?>