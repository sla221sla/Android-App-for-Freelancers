<?php

require_once __DIR__.'/connection_new.php';

$freename = $_POST['fname'];

$result = mysqli_query($con, "SELECT ProjectName, ProjectDesc, CompName FROM project WHERE AllotedTo LIKE '$freename'");
$result2 = mysqli_query($con, "SELECT COUNT(*) FROM project WHERE AllotedTo LIKE '$freename'");
$res = mysqli_fetch_row($result2);
if ($res[0] > 0) 
{
    while($row = mysqli_fetch_assoc($result)) {
        echo $row["ProjectName"]."$$$$".$row["ProjectDesc"]."$$$$".$row["CompName"];
    }
} else {
    echo "0 $$$$ results $$$$ present";
}

mysqli_close($con);

?>