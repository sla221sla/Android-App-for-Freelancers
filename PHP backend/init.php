<?php

$host = "localhost";
$db = "id6438040_collabcloud";
$username = "id6438040_collabcloudmain";
$password = "password@123";

$con = mysqli_connect($host , $username , $password , $db );

if(!$con){
	die("Error in Connection : ". mysqli_connect_error());
}

else{
	
	echo "<br><h3>Connection Success</h3>";
}

?>