<?php
require_once __DIR__.'/connection_new.php';

$company = $_POST['cname'];
$proj = $_POST['projname'];
$task = $_POST['taskid'];
$start = $_POST['startdate'] ;
$end = $_POST['enddate'];
$est = (int)$_POST['esthours'];
$to = $_POST['allotto'];
$det = $_POST['details'];

	$result = mysqli_query($con , "INSERT INTO project(CompName, ProjectName, TaskID, ProjectDesc, StartDate, EndDate, EstimatedHours, AllotedTo) VALUES('$company', '$proj', '$task', '$det', STR_TO_DATE('$start','%Y/%m/%d') ,STR_TO_DATE( '$end','%Y/%m/%d'), '$est', '$to')");
	
	echo mysqli_error($con);


echo $result;
mysqli_close($con);

?>