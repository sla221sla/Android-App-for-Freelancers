<?php
require_once __DIR__.'/connection_new.php';

$freename1 = $_POST['freename'];
$projname1 = $_POST['projname'];
$compname1 = $_POST['compname'];

$sql = "SELECT Date, Hours, TaskInfo FROM freelancer WHERE FreeName='$freename1' AND CompName='$compname1' AND ProjectName='$projname1'";
$result = $con->query($sql);

if ($result->num_rows > 0)
{
    while($row = $result->fetch_assoc()) {
        echo "Date: " . $row["Date"];
        echo "Hours: " . $row["Hours"];
        echo "TaskInfo: " . $row["TaskInfo"];
    }
}
else {
    echo "DB yil Vellathum add cheyyada patti.. xD";
}

?>