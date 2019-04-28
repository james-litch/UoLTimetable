<?php
$table;
$id;
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
	$table = "Student";
	$id = "StudentID";
} else {
	$table = "Lecturer";
	$id = "LecturerID";
}

$stmt = "SELECT Password FROM ".$table." WHERE ".$id."=".$_POST['id'];

$pstmt = $pdo->prepare($stmt);

$pstmt->execute();
$result = $pstmt->fetch();
$pwattempt = crypt($_POST['pword'], "university");

if ($pwattempt == $pwattempt) {
	echo 'success';
}

} catch (PDOException $e) {
    exit("PDO Error: ".$e->getMessage()."<br>");
}

$pdo = NULL;

?>