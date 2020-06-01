<?php

require_once __DIR__.'/connection_new.php';

$comp = $_POST['compname'];
$result = mysqli_query($con, "SELECT DISTINCT ProjectName FROM freelancer WHERE CompName LIKE '$comp'");
$result2 = mysqli_query($con, "SELECT COUNT(DISTINCT ProjectName) FROM freelancer WHERE CompName LIKE '$comp'");
$boo = null;
$res2 = mysqli_fetch_row($result2);
if ($res2[0] > 0) 
{
    while($row = mysqli_fetch_assoc($result)) 
    {
        $boo = $boo.$row["ProjectName"]."$$";
    }
    echo $boo;
} else {
    echo "";
}

mysqli_close($con);

?>