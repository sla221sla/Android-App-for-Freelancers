<?php
require_once __DIR__.'/connection_new.php';

$free = $_POST['freelancer'];
$comp = $_POST['company'];
$project = $_POST['projname'];
$date = $_POST['date'];
$hours = $_POST['hrs'];
$info = $_POST['details'];

	$result = mysqli_query($con , "INSERT INTO freelancer(FreeName, CompName, ProjectName, Date, Hours, TaskInfo) VALUES('$free', '$comp', '$project', '$date', '$hours', '$info')");


echo mysqli_error($con);
mysqli_close($con);

?>