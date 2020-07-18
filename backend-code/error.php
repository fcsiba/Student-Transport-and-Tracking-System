<?php
session_start();
?>

<section class="content">
    <div class="error-page">
        <h1>Error...</h1>
    
        <div class="error-content">
          <h3><i class="fas fa-exclamation-triangle text-warning"></i> Oops! Something went wrong</h3>

          <p>
            <?php 
				if( isset($_SESSION['message']) AND !empty($_SESSION['message']) ): 
					echo $_SESSION['message'];    
				else:
					header( "location: customer-login.php" );
				endif;
			?>
		  </p>
		  
		  <a href="customer-login.php"><button class="button button-block"/>Home</button></a>
        </div>
    </div>
</section>