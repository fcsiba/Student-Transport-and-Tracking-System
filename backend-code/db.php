<?php
 $conn = new mysqli('localhost', 'root', '', 'bus_locator');
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
 /* $conn = new mysqli('saalameri30892.domaincommysql.com', 'umar_farooq', 'Umar#12#', 'bus_locator');
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}  */
?>