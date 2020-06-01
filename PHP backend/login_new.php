<?php

require_once __DIR__.'/connection_new.php';

$username1 = $_POST['username'];
$password1 = $_POST['password'];
$type1 = $_POST['type'];

	$result =  mysqli_query($con , "SELECT COUNT(*)  FROM signup WHERE Username LIKE '$username1' AND Password LIKE '$password1' AND Type LIKE '$type1' " );
$res = mysqli_fetch_row($result);

if( $res[0] == 1)
    echo "success";
else
    echo "unsuccessful";
    
mysqli_close($con);

?>