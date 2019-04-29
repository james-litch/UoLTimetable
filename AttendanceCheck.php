<?php
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

$stmt = "select ModuleCode, attendance from Attendance where studentID = '".$_POST['id']."';";

$pstmt = $pdo->prepare($stmt);

$pstmt->execute();
$result = $pstmt->fetchAll();
echo json_encode($result);

} catch (PDOException $e) {
    exit("PDO Error: ".$e->getMessage()."<br>");
}

$pdo = NULL;

?>