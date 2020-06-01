<?php

$host = "localhost";
$db = "id6438040_collabcloud";
$username = "id6438040_collabcloudmain";
$password = "password@123";

$con = mysqli_connect($host , $username , $password , $db );

	$username = $_POST['username'];
    $password = $_POST['password'];
   

if(!$con){
	die("Error in Connection : ". mysqli_connect_error());
}

else{
	
	$result = mysqli_fetch_row( mysqli_query($con , "SELECT COUNT(*) FROM signup WHERE Username LIKE '$username' AND Password LIKE '$password' " ));
	
    
}

echo $result[0];

?>