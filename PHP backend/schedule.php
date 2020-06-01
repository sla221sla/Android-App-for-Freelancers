<?php

require_once __DIR__.'/connection_new.php';
$comp = $_POST['compname'];

$result = mysqli_query($con, "SELECT F.ProjectName, SUM(F.Hours)/P.EstimatedHours AS Percentage FROM freelancer AS F, project AS P WHERE F.CompName LIKE '$comp' GROUP BY F.ProjectName");

$result2 = mysqli_query($con, "SELECT COUNT(F.ProjectName) FROM freelancer AS F WHERE F.CompName LIKE '$comp' GROUP BY F.ProjectName");
$res = mysqli_fetch_row($result2);

$response = array();
$data = array();
$response["data"] = array();

if ($res[0] > 0) 
{
    while($row = mysqli_fetch_assoc($result)) 
    {
    	$data["ProjectName"] = $row["ProjectName"];
    	$data["Percentage"] = $row["Percentage"];
    	array_push($response["data"], $data);
    }
    echo json_encode($response);
} 
else 
{
    echo "Not found";
}

mysqli_close($con);

?>