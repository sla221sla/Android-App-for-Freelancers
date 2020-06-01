<?php
$host = "localhost";
$db = "id6438040_collabcloud";
$username = "siri";
$password = "boo";

$con = mysqli_connect($host , $username , $password , $db );

if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}

?>