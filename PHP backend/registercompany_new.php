<?php

require_once __DIR__.'/connection_new.php';

$username1 = $_POST['username'];
$password1 = $_POST['password'];

	$result = mysqli_query($con , "INSERT INTO signup(Username, Password, Type, Active) VALUES('$username1', '$password1', 'Company', 'No')");


echo $result;
mysqli_close($con);

?>

