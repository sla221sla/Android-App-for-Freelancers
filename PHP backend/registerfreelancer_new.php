<?php

require_once __DIR__.'/connection_new.php';


$username1 = $_POST['username'];
$password1 = $_POST['password'];

	
	$result1 = mysqli_query($con , "INSERT INTO employee(CompName, FreeName, FreePass, Active) VALUES('comp', '$username1', '$password1', 'No')");
	$result2 = mysqli_query($con , "INSERT INTO signup VALUES('$username1', '$password1', 'Freelancer' , 'No')");
	
	
echo mysqli_error($con);

echo $result1;
echo $result2;
mysqli_close($con);


?>