<?php

require_once __DIR__.'/connection_new.php';
$comp = $_POST['compname'];
$pro = $_POST['projname'];
$result = mysqli_query($con, "SELECT ProjectDesc, AllotedTo, StartDate, EndDate FROM project WHERE CompName LIKE '$comp' AND ProjectName LIKE '$pro' ");
$result2 = mysqli_query($con, "SELECT COUNT(*) FROM project WHERE CompName LIKE '$comp' AND ProjectName LIKE '$pro' ");
$res = mysqli_fetch_row($result2);
if ($res[0] > 0) 
{
    while($row = mysqli_fetch_assoc($result)) {
        echo $row["ProjectDesc"]."$$".$row["AllotedTo"].$row["StartDate"]."$$".$row["EndDate"];
    }
} else {
    echo "0 $$ results $$ present";
}

mysqli_close($con);

?>