<?php
include('db.php');
$response=array();
$response['status']='error';
$response['message']='Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['islogin']))&&(isset($_POST['type']))&&($_POST['islogin']==true)&&($_POST['type']==1))
{
	$sql="SELECT * FROM buses";
	$result = mysqli_query($conn, $sql);
	if (mysqli_num_rows($result) > 0) 
	{
		$i=1;
		while($row = mysqli_fetch_assoc($result)) 
		{
			$response['data'][$i]['id']					= $row["id"];
			$response['data'][$i]['bus_no']	 			= $row['bus_no'];
			$response['data'][$i]['total_seats']		= $row['total_seats'];
			$response['data'][$i]['available_seats']	= $row['available_seats'];
			$response['data'][$i]['status']				= $row["status"];
			$response['data'][$i]['current_stop']		= $row['current_stop'];
			$response['data'][$i]['next_stop']			= $row['next_stop'];
			$response['data'][$i]['type']				= $row['type'];
			$stop_lat=$stop_lng=0;
			$current_stop = $row['current_stop'];
			$sql_a = "SELECT * FROM `stops` where id='$current_stop'";
			$result_a = mysqli_query($conn, $sql_a);
			if (mysqli_num_rows($result_a) > 0) 
			{
				while($row_a = mysqli_fetch_assoc($result_a)) 
				{
					$stop_id	= $row_a["id"];
					$stop_lat	= $row_a["stop_lat"];
					$stop_lng	= $row_a["stop_lng"];
					$stop_name	= $row_a["stop_name"];
				}
			}
			$response['data'][$i]['stop_id']	 		= $stop_id;
			$response['data'][$i]['current_lat']	 	= $stop_lat;
			$response['data'][$i]['current_lng']		= $stop_lng;
			$response['data'][$i]['stop_name']			= $stop_name;
			
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