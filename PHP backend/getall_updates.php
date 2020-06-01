<?php

require_once __DIR__.'/connection_new.php';

$projname = $_POST['projname'];
$freename = $_POST['freename'];
$comp = $_POST['compname'];

$result = mysqli_query($con, "SELECT TaskInfo FROM freelancer WHERE FreeName LIKE '$freename' AND ProjectName LIKE '$projname' AND CompName LIKE '$comp'");
$res2 = mysqli_fetch_row($result);

$cnt = mysqli_query($con, "SELECT COUNT(*) FROM freelancer WHERE FreeName LIKE '$freename' AND CompName LIKE '$comp' AND ProjectName LIKE '$projname' " );
$cnt2 = mysqli_fetch_row($cnt);

if($cnt2[0]>0){
    echo "Result Obtained";
}else{
    echo "failed";
}

?>