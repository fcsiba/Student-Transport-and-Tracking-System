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
if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$check=1;
	if (isset($_POST['update-status'])) 
	{
		$status = $_POST['status-value'];
		$uid 	= $_POST['uid'];
		$value=0;
		if($status==0){$value=1;};
		$sql = "UPDATE users SET status='$value' where id='$uid'";
		$conn->query($sql);
	}
}

?>

 <div class="content-wrapper">
    <section class="content">
      <div class="container-fluid">
        <div class="row">
		  <div class="col-md-12" style="margin-top:2rem;">
            <div class="card card-primary">
              <div class="card-header" style="background-color: black;">
                <h3 class="card-title"><b>Driver List </b></h3>
			  </div> 
			  
             <div class="card-body table-responsive p-0" >
                <table class="table table-head-fixed  table-bordered">
                  	<thead>
                    	<tr>
							<th>S/No</th>
							<th>Id</th>
							<th>Name</th>
							<th>CNIC</th>
							<th>Password</th>
							<th>Latitude</th>
							<th>Longitude</th>
							<th>Contact No</th>
							<th>Status</th>
							<th>Action</th>
							<th>Edit</th>
						</tr>
					</thead>
				  	<?php 
					$i=0;
					$sql="select * from users where type=2 ";
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
						<td><?= $row['name'] ?></td>
						<td><?= $row['cnic'] ?></td>
						<td><?= $row['password'] ?></td>
						<td><?= $row['latitude'] ?></td>
						<td><?= $row['longitude'] ?></td>
						<td><?= $row['contact_no'] ?></td>
						<td><?php if ($row['status']==1) echo "Approved"; else if($row['status']==0) echo "Rejected";?></td>
						
						<td>
							<form  action="view-driver-list.php" method="post">
								<input type="hidden" name="uid" class="form-control" value=<?= $row['id'] ?> >
								<input type="hidden" name="status-value" class="form-control" value=<?= $row['status'] ?> >
								<button type="submit" style="background-color:#eef8f9;color:<?php if($row['status']==1) echo "red";else echo "green";?>" name="update-status" class="btn btn-info btn-fill pull-right" ><?php if($row['status']==1) echo "Deactive";else echo "active";?></button>
							</form>
						</td>
						<td>
							<form  action="edit-driver-profile.php" method="post">
								<input type="hidden" name="uid" class="form-control" value=<?= $row['id'] ?> >
								<input type="hidden" name="type" class="form-control" value="driver">
								<button type="submit" name="edit" class="btn btn-info btn-fill pull-right" >Edit</button>
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
