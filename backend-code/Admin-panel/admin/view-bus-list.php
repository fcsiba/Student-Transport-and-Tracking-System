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
$type ="";
$bid =0;
$bus_no=$total_seat=$dr_id="";
if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	
	if (isset($_POST['delete'])) 
	{
		$bid 	= $_POST['bid'];
		$sql = "Delete from buses where id='$bid'";
		$conn->query($sql);
		$check=1;
	}
	if (isset($_POST['edit'])) 
	{
		$bid 	= $_POST['bid'];
		$type 	= $_POST['type'];	
	}
	if (isset($_POST['addbus'])) 
	{
		$bus_no 		= $_POST['bus_no'];
		$total_seat 	= $_POST['total_seat'];
		$dr_id 			= $_POST['dr_id'];
		
		$sql1 = "INSERT INTO `buses`(`bus_no`, `total_seats`,available_seats, `dr_id`) VALUES ('$bus_no','$total_seat','$total_seat','$dr_id') ";
		$conn->query($sql1);
		$addcheck=1;	
	}
	if (isset($_POST['editbus'])) 
	{
		$bus_no 		= $_POST['bus_no'];
		$total_seat 	= $_POST['total_seat'];
		$dr_id 			= $_POST['dr_id'];
		$busid 			= $_POST['busid'];
		$sql111 = "Update `buses` SET `bus_no`='$bus_no', `total_seats`='$total_seat', `dr_id`='$dr_id' where id='$busid' ";
		$conn->query($sql111);
		$addcheck=1;	
		$bus_no ="";
		$total_seat ="";
		$dr_id ="";
	}
}

?>

 <div class="content-wrapper">
    <section class="content">
      <div class="container-fluid">
		<div class="card card-primary">
			<div class="card-header" style="background-color: black;">
				<h3 class="card-title"><b> 
				<?php 
				if($type=="bus")
				{
					$sql5="select * from buses where id='$bid'";
					if ($result5 = mysqli_query($conn, $sql5))
					{
						while ($row5 = mysqli_fetch_assoc($result5))
						{
							$bus_no		=$row5['bus_no'] ;
							$total_seat	=$row5['total_seats'] ;
							$dr_id		=$row5['dr_id'] ;
						}
					}
				echo "Edit ";
				}
				else echo "Add ";
				?>buses </b></h3>
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
					<form role="form"   action="view-bus-list.php"   method="POST">
						<div class="card-body">
							<div style="display: grid;grid-template-columns: 47% 47%;grid-column-gap: 6%;">
								<div class="form-group">
									<label>Bus No</label>
									<input type="text" class="form-control" name="bus_no" value="<?=$bus_no?>" placeholder="Enter Bus No" required  />
								</div>
							
								<div class="form-group">
									<label>Total Seat</label>
									<input type="text" class="form-control" name="total_seat"  value="<?=$total_seat?>" placeholder="Enter Total Seat" required  />
								</div>
								<input type="hidden" value="<?=$bid;?>" name="busid"/>
								<div class="form-group">
								  <label for="sel1">Select driver:</label>
								  <select class="form-control" name="dr_id">
									<?php
									$sql10="select * from users where type=2";
									if ($result = mysqli_query($conn, $sql10))
									{
										while ($row = mysqli_fetch_assoc($result))
										{
									?>
									<option value="<?=$row['id'] ?>" ><?=$row['name'] ?></option>
									<?php
										}
									}
									?>
								  </select>
								</div>
							</div>
						</div>
						<div class="card-footer">
							<?php 
							if($type=="bus")
							{?><button type="submit" name="editbus" class="btn btn-primary">Edit Bus Record</button>
							<?php } else {?>
							
							<button type="submit" name="addbus" class="btn btn-primary">Add Bus Record</button>
							<?php } ?>

						</div>
					</form>
				</div>
			</div>
        </div>
		
		<div class="row">
		  <div class="col-md-12" style="margin-top:2rem;">
            <div class="card card-primary">
              <div class="card-header" style="background-color: black;">
                <h3 class="card-title"><b>Bus List </b></h3>
			  </div> 
			  
			  <?php if($check==1){ ?>
				<div class="alert success">
					<span class="closebtn">&times;</span>  
					Conguradulation Your Bus is deleted from Database.
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
							<th>Bus No</th>
							<th>Total Seat</th>
							<th>Driver Name</th>
							<th>Edit</th>
							<th style="color:red;">Delete</th>
						</tr>
					</thead>
				  	<?php 
					$i=0;
					$sql="select * from buses";
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
						<td><?= $row['bus_no'] ?></td>
						<td><?= $row['total_seats'] ?></td>
						<?php
						$did=$row['dr_id'];
						$sql10="select * from users where type=2 and id='$did'";
						if ($result1 = mysqli_query($conn, $sql10))
						{
							while ($row1 = mysqli_fetch_assoc($result1))
							{
								$dname = $row1['name'];
							}
						}
						?>
						<td><?= $dname ?></td>
						
						<td>
							<form  action="view-bus-list.php" method="post">
								<input type="hidden" name="bid" class="form-control" value=<?= $row['id'] ?> >
								<input type="hidden" name="type" class="form-control" value="bus">
								<button type="submit" name="edit" class="btn btn-info btn-fill pull-right" >Edit</button>
							</form>
						</td>
						<td>
							<form  action="view-bus-list.php" method="post">
								<input type="hidden" name="bid" class="form-control" value=<?= $row['id'] ?> >
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
