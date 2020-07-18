<?php
include('db.php');
$response=array();
$response['status']='error';
$response['message']='Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['islogin']))&&(isset($_POST['type']))&&($_POST['islogin']==true)&&($_POST['type']==1)&&(isset($_POST['s_id']))&&(isset($_POST['b_id']))&&(isset($_POST['pickup_stop_id'])))
{
	$s_id 			= $_POST['s_id'];//student id
	$b_id 			= $_POST['b_id'];
	$pickup_stop_id = $_POST['pickup_stop_id'];
	$sql			="SELECT * FROM buses where id='$b_id'";
	$result 		= mysqli_query($conn, $sql);
	if (mysqli_num_rows($result) > 0) 
	{
		while($row = mysqli_fetch_assoc($result)) 
		{
			$bus_no 			= $row['bus_no'];
			$total_seats 		= $row['total_seats'];
			$available_seats	= $row['available_seats'];
			$status				= $row["status"];
			$current_stop		= $row['current_stop'];
			$next_stop			= $row['next_stop'];
			$type				= $row['type'];
			$stop_lat=$stop_lng = 0;
			$current_stop 		= $row['current_stop'];
			$sql_a = "SELECT * FROM `stops` where id='$current_stop'";
			$result_a = mysqli_query($conn, $sql_a);
			if (mysqli_num_rows($result_a) > 0) 
			{
				while($row_a = mysqli_fetch_assoc($result_a)) 
				{
					$bus_stop_lat  = $row_a["stop_lat"];
					$bus_stop_lng  = $row_a["stop_lng"];
					$bus_stop_name = $row_a["stop_name"];
				}
			}
			if($pickup_stop_id<$current_stop)
			{
				$row['available_seats']++;
			}
		}
	}
	
	if($pickup_stop_id<$current_stop)
	{
		$response['status']						= 'error';
		$response['message']					= 'You miss it! Bus has gone when you checked.';
		echo json_encode($response);
	}
	else if($pickup_stop_id==$current_stop)
	{
		$response['status']						= 'success';
		$response['message']					= "Your bus no '$bus_no' is at your stop i.e. '$bus_stop_name' <br> Grab your bus please ";
		echo json_encode($response);
	}
	else
	{
		
		$sql_b = "SELECT * FROM `stops` where id='$pickup_stop_id'";
		$result_b = mysqli_query($conn, $sql_b);
		if (mysqli_num_rows($result_b) > 0) 
		{
			while($row_b = mysqli_fetch_assoc($result_b)) 
			{
				$pickup_stop_name = $row_b["stop_name"];
			}
		}
		
		$response['message'] = " Your seat has been reserved.<br> Your bus no $bus_no is at $bus_stop_name <br> Your bus next stop is $next_stop.<br> Your pickup stop is $pickup_stop_name";
		echo json_encode($response);
	}
}
else
{
	echo json_encode($response);
}
?>