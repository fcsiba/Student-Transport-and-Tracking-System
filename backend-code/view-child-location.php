<?php
include('db.php');
$response=array();
$response['status']='error';
$response['message']='Opps Something went wrong. Please fill all feilds.';

if((isset($_POST['id']))&&($_POST['id']!='')&&($_POST['type']==3))
{
	$id	 		= $_POST['id'];
	
	$sql="SELECT * FROM `reservations` where s_id='$id'";
	$result = mysqli_query($conn, $sql);
	if (mysqli_num_rows($result) > 0) 
	{
		while($row = mysqli_fetch_assoc($result)) 
		{
			$id 			= $row["id"];
			$s_id	 		= $row['s_id'];
			$b_id	 		= $row['b_id'];
			$pickup_stop	= $row['pickup_stop'];
			$sql2="SELECT * FROM `stops` where id='$pickup_stop'";
			$result2 = mysqli_query($conn, $sql2);
			if (mysqli_num_rows($result2) > 0) 
			{
				while($row2 = mysqli_fetch_assoc($result2)) 
				{	
					$pickup_stop_name	 		= $row2['stop_name'];
				}
			}
			
			$sql21="SELECT * FROM `users` where id='$s_id'";
			$result21 = mysqli_query($conn, $sql21);
			if (mysqli_num_rows($result21) > 0) 
			{
				while($row21 = mysqli_fetch_assoc($result21)) 
				{	
					$child_name	 		= $row21['name'];
				}
			}
			
			
			$sql1="SELECT * FROM `buses` where id='$b_id'";
			$result1 = mysqli_query($conn, $sql1);
			if (mysqli_num_rows($result1) > 0) 
			{
				while($row1 = mysqli_fetch_assoc($result1)) 
				{	
					$bus_no	 		= $row1['bus_no'];
					$current_stop	= $row1['current_stop'];
					$sql2="SELECT * FROM `stops` where id='$current_stop'";
					$result2 = mysqli_query($conn, $sql2);
					if (mysqli_num_rows($result2) > 0) 
					{
						while($row2 = mysqli_fetch_assoc($result2)) 
						{	
							$current_stop_name	 		= $row2['stop_name'];
						}
					}	
				}
			}
			$response['status']='success';
			$response['message']="Your child '".$child_name."' is pick our bus having no '".$bus_no."' from stop '".$pickup_stop_name."' and Bus id at '".$current_stop_name."' right now";
			echo json_encode($response);
		}
		
	}
	else
	{
		$response['message']='Your child not pick any bus right now';
		echo json_encode($response);
	}
}
else
{
	echo json_encode($response);
}
?>