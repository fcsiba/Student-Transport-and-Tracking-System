<?php
$id=$_SESSION['id'];
$name=$_SESSION['name'];
?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Administrator | Dashboard</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../plugins/fontawesome-free/css/all.min.css">
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <link rel="stylesheet" href="../plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <link rel="stylesheet" href="../plugins/jqvmap/jqvmap.min.css">
  <link rel="stylesheet" href="../dist/css/adminlte.min.css"><!-- styles -->
  <link rel="stylesheet" href="../plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <link rel="stylesheet" href="../plugins/daterangepicker/daterangepicker.css">
  <link rel="stylesheet" href="../plugins/summernote/summernote-bs4.css">
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  <style>
	.customer-statement td{padding:0.3rem;}
	.customer-statement th{padding:0.3rem;}
  </style>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <a href="" class="nav-link">Home</a>
      </li>
   </ul>
</nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="customer-panel.php" class="brand-link">
      
      <span class="brand-text font-weight-light">STATS</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
          <img src="../dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block"><?= $name ?></a>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item">
            <a href="dashboard.php" class="nav-link">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Dashboard
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="edit-profile.php" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                Edit Profile
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="add-user-profile.php" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
               Add User
              </p>
            </a>
          </li>
		  
          <li class="nav-item">
            <a href="view-driver-list.php" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
               Driver List
              </p>
            </a>
          </li>  
		  
          <li class="nav-item">
            <a href="add-driver-profile.php" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
             Add Driver 
              </p>
            </a>
          </li>
         
		 
          <li class="nav-item">
            <a href="view-stop-list.php" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
              Stops
              </p>
            </a>
          </li>  
		 
          <li class="nav-item">
            <a href="view-bus-list.php" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
              Bus
              </p>
            </a>
          </li>  
		 
		  <li class="nav-item has-treeview">
            <a href="logout.php" class="nav-link">
              <i class="nav-icon fas fa-chart-pie"></i>
              <p>
                Logout
              </p>
            </a>
          </li>
			
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>