<?php
class lecture {
	public $lectureName;
	public $lecturerName;
	public $location;
	public $latitude;
	public $longitude;
	public $dateTime;
	
	function __construct ($modname, $fname, $sname, $roomname, $buildingname, $lat, $long, $daytime) {
		$this->lectureName = $modname;
		$this->lecturerName = $fname." ".$sname;
		$this->location = $roomname.", ".$buildingname;
		$this->latitude = $lat;
		$this->longitude = $long;
		$this->dateTime = $daytime;
	}
}

$results = array();
$db_hostname = "student.csc.liv.ac.uk";
$db_database = "sgmbray";
$db_username = "sgmbray";
$db_password = "database";
$db_charset = "utf8mb4";
$dsn = "mysql:host=$db_hostname;dbname=$db_database;charset=$db_charset";
$opt = array(
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES => false
);

try { //connect the pdo
    $pdo = new PDO($dsn,$db_username,$db_password,$opt);

if ($_POST['lecturer'] == "false") {
	$stmt = "select ModuleName, FirstName, Surname, RoomName, 
BuildingName, Latitude, Longitude, dayAndTime from Attendance 
NATURAL JOIN Lecture NATURAL JOIN Room NATURAL JOIN Building 
NATURAL JOIN Lecturer NATURAL JOIN Module where dayAndTime LIKE \"".$_POST['date']."%\" AND StudentID = \"".$_POST['id']."\" order by dayAndTime;";

} else {
	$stmt = "select distinct ModuleName, FirstName, Surname, RoomName, 
BuildingName, Latitude, Longitude, dayAndTime from Attendance 
NATURAL JOIN Lecture NATURAL JOIN Room NATURAL JOIN Building 
NATURAL JOIN Lecturer NATURAL JOIN Module where dayAndTime LIKE \"".$_POST['date']."%\" AND LecturerID = \"".$_POST['id']."\" order by dayAndTime;";
}


$pstmt = $pdo->prepare($stmt);
$pstmt->execute();
$index = 0;

while ($row = $pstmt->fetch()) {
	$results[$index] = new lecture($row['ModuleName'],$row['FirstName'],$row['Surname'],$row['RoomName'],$row['BuildingName'],
	$row['Latitude'],$row['Longitude'],$row['dayAndTime']);
	$index++;
}

echo json_encode($results);

} catch (PDOException $e) {
    exit("PDO Error: ".$e->getMessage()."<br>");
}

$pdo = NULL;

?>