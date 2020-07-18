<style>
	.alert {
		padding: 20px;
		background-color: #f44336;
		color: white;
		opacity: 1;
		transition: opacity 0.6s;
		margin-bottom: 15px;
	}
	.alert.success {
    background-color: #4CAF50;
  }
  .alert.warning {
    background-color: #ff0000;
  }
	.closebtn {
		margin-left: 15px;
		color: white;
		font-weight: bold;
		float: right;
		font-size: 22px;
		line-height: 20px;
		cursor: pointer;
		transition: 0.3s;
	}
	.closebtn:hover {
		color: black;
	}
</style>
<?php
session_start();
include '../../db.php';

include 'header.php';
$check=0;
$addcheck=0;
if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	
	if (isset($_POST['edit'])) 
	{
		$sid 	= $_POST['sid'];
		
		$sql9 = "Select * from `stops` where id='$sid'";
		$result1 	= $conn->query($sql9);
		if ($result1->num_rows > 0) 
		{
			while($row1 = $result1->fetch_assoc()) 
			{
				$sid 			= $row1['id'];
				$stop_name 		= $row1['stop_name'];
				$stop_lat 		= $row1['stop_lat'];
				$stop_lng 		= $row1['stop_lng'];
			}
		}
	}
	if (isset($_POST['editstop'])) 
	{
		$sid 			= $_POST['sid'];
		$stop_name 		= $_POST['stop_name'];
		$stop_lat 		= $_POST['stop_lat'];
		$stop_lng 		= $_POST['stop_lng'];
		
		$sql111 = "Update `stops` SET `stop_name`='$stop_name', `stop_lat`='$stop_lat', `stop_lng`='$stop_lng' where id='$sid' ";
		$conn->query($sql111);
		$addcheck=1;	
	}
}

?>

 <div class="content-wrapper">
    <section class="content">
      <div class="container-fluid">
		<div class="card card-primary">
			<div class="card-header" style="background-color: black;">
				<h3 class="card-title"><b> Edit stop </b></h3>
			</div>
			<div class="row">
				<div class="col-md-12" style="margin-top:2rem;">
					  <?php if($addcheck==1){ ?>
				<div class="alert success">
					<span class="closebtn">&times;</span>  
					Conguradulation Your Stop is updated successfully in Database.
				</div>
				<script>
					var close = document.getElementsByClassName("closebtn");
					var i;
					for (i = 0; i < close.length; i++) 
					{
						close[i].onclick = function()
						{
							var div = this.parentElement;
							div.style.opacity = "0";
							setTimeout(function(){ div.style.display = "none"; }, 600);
						}
					}
				</script>
			  <?php } ?>
					<form role="form"   action="edit-stop.php"   method="POST">
						<div class="card-body">
							<div style="display: grid;grid-template-columns: 47% 47%;grid-column-gap: 6%;">
								<div class="form-group">
									<label>Stop Name</label>
									<input type="text" class="form-control" name="stop_name" value="<?=$stop_name?>" placeholder="Enter Name" required  />
								</div>
								<input type="hidden" name="sid" value="<?=$sid?>"/>
								<div class="form-group">
									<label>Stop latitude</label>
									<input type="text" class="form-control" name="stop_lat" value="<?=$stop_lat?>" placeholder="Enter latitude" required  />
								</div>
								<div class="form-group">
									<label>Stop longitude</label>
									<input type="text" class="form-control" name="stop_lng" placeholder="Enter longitude " value="<?=$stop_lng?>" required  />
								</div>
							</div>
						</div>
						<div class="card-footer">
							<button type="submit" name="editstop" class="btn btn-primary">Edit Stop Record</button>
						</div>
					</form>
				</div>
			</div>
        </div>
		
		<div class="row">
		  <div class="col-md-12" style="margin-top:2rem;">
            <div class="card card-primary">
              <div class="card-header" style="background-color: black;">
                <h3 class="card-title"><b>Stop List </b></h3>
			  </div> 
			  
			  <?php if($check==1){ ?>
				<div class="alert success">
					<span class="closebtn">&times;</span>  
					Conguradulation Your Stop is deleted from Database.
				</div>
				<script>
					var close = document.getElementsByClassName("closebtn");
					var i;
					for (i = 0; i < close.length; i++) 
					{
						close[i].onclick = function()
						{
							var div = this.parentElement;
							div.style.opacity = "0";
							setTimeout(function(){ div.style.display = "none"; }, 600);
						}
					}
				</script>
			  <?php } ?>
			    
             <div class="card-body table-responsive p-0" >
                <table class="table table-head-fixed  table-bordered">
                  	<thead>
                    	<tr>
							<th>S/No</th>
							<th>Id</th>
							<th>Stop Name</th>
							<th>Stop Latitude</th>
							<th>Stop Longitude</th>
							<th>Edit</th>
							<th style="color:red;">Delete</th>
						</tr>
					</thead>
				  	<?php 
					$i=0;
					$sql="select * from stops";
				  	if ($result = mysqli_query($conn, $sql))
					{
						while ($row = mysqli_fetch_assoc($result))
						{
							$i++;
						
						?>
                  <tbody>
                    <tr>
						<td><?= $i ?></td>
						<td><?= $row['id'] ?></td>
						<td><?= $row['stop_name'] ?></td>
						<td><?= $row['stop_lat'] ?></td>
						<td><?= $row['stop_lng'] ?></td>
						
						<td>
							<form  action="view-stop-list.php" method="post">
								<input type="hidden" name="sid" class="form-control" value=<?= $row['id'] ?> >
								<input type="hidden" name="type" class="form-control" value="driver">
								<button type="submit" name="edit" class="btn btn-info btn-fill pull-right" >Edit</button>
							</form>
						</td>
						<td>
							<form  action="view-stop-list.php" method="post">
								<input type="hidden" name="sid" class="form-control" value=<?= $row['id'] ?> >
								<button type="submit" name="delete" class="btn btn-info btn-fill pull-right" >Delete</button>
							</form>
						</td>
                    </tr>
                  </tbody>
						<?php 
						}
					}?>
                </table>
              </div>
			 
            </div>
			</div>
        </div>
		
      </div>
	</section>
   </div>

<?php 
include 'footer.php';
?>
