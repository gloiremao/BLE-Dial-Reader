<?php
$reading =  $_POST["reading"];
$unit =  $_POST["unit"];
$triggerFlag =  $_POST["triggerFlag"];
$angles =  $_POST["angles"];
$batteryVoltage =  $_POST["batteryVoltage"];
$versionFlag =  $_POST["versionFlag"];
$timeInterval =  $_POST["timeInterval"];

$servername = "localhost";
$username = "itriApp";
$password = "fRMDX6Qaf5yNJMrr";
$dbname = "itri_app";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO sensor (reading, unit, triggerFlag, angles, batteryVoltage, versionFlag, timeInterval)
VALUES ('". $reading ."','". $unit ."','". $triggerFlag ."','". $angles ."','". $batteryVoltage ."','". $versionFlag ."','". $timeInterval ."')";

if ($conn->query($sql) === TRUE) {
    echo "Successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();

?>
