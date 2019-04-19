import java.sql.*;

public class QueryHandler {

	public void changeStudentPassword(String studentID, String password){
		Hash cipher = new Hash(password);
		String ciphertext = cipher.getHash();
		DBConnect query = new DBConnect();
		query.setData("update Student set password = '" + ciphertext + "' where studentID = '" + studentID + "'");
	}
	
	public void changeLecturerPassword(int lecturerID, String password){
	   Hash cipher = new Hash(password);
	   String ciphertext = cipher.getHash();
	   DBConnect query = new DBConnect();
	   query.setData("update Lecturer set password = '" + ciphertext + "' where lecturerID = '" + lecturerID + "'");
	}
	
	public ResultSet checkModuleLecturer(String moduleCode){
	    DBConnect query = new DBConnect();
		ResultSet lecturermodule = query.getData("select firstName, surname from Lecturer natural join Module where moduleCode = '" + moduleCode + "'");
		return lecturermodule;
	}
	
	public ResultSet checkAvailableRooms(String dateTime){
	   DBConnect query = new DBConnect();
	   ResultSet availablerooms = query.getData("SELECT roomID, RoomName FROM Room WHERE roomID NOT IN (SELECT roomID FROM Lecture WHERE dayAndTime = '" + dateTime + "')");
	   return availablerooms;
	}
   
	public void findAttendance(String studentID, String moduleCode) { //maybe change this delete
	   
	   DBConnect findattendance = new DBConnect();
	   ResultSet attendance = findattendance.getData("select * from Attendance where studentID = '" + studentID + "' and moduleCode = '" + moduleCode + "'");
		
	   DBConnect countlectures = new DBConnect();
	   ResultSet numLectures = countlectures.getData("select count(*) as NumLectures from Lecture where ModuleCode = '" + moduleCode + "' AND dayAndTime < NOW()");
		
	   try {
		   while (attendance.next() && numLectures.next()) {
			   System.out.println(attendance.getString("studentID") + ", " + attendance.getString("moduleCode") + ", "  + (double) attendance.getInt("Attendance")/numLectures.getInt("NumLectures") * 100 + "%");
		   }
	   } 
	   catch (SQLException e) {
		   e.printStackTrace();
	   }

	}
   
	public Boolean checkStudentPassword(String studentID, String passwordAttempt) {
	   Hash cipher = new Hash(passwordAttempt);
	   String ciphertext = cipher.getHash();
	   DBConnect query = new DBConnect();
	   ResultSet passResult = query.getData("select password from Student where studentID = '" + studentID + "'");
	   
	   try {
		  passResult.next();
		  String actualPassword = passResult.getString(1);
		  if (actualPassword.equals(ciphertext)) {
			  return true;
		  }
		  else {
			  return false;
		  }
	   }
	   catch (SQLException e) {
		   return false;
	   }
	   
	}
   
	public Boolean checkLecturerPassword(String username, String passwordAttempt) {
	   Hash cipher = new Hash(passwordAttempt);
	   String ciphertext = cipher.getHash();
	   DBConnect query = new DBConnect();
	   ResultSet passResult = query.getData("select password from Lecturer where username = '" + username + "'");
	   
	   try {
		  passResult.next();
		  String actualPassword = passResult.getString(1);
		  if (actualPassword.equals(ciphertext)) {
			  return true;
		  }
		  else {
			  return false;
		  }
	   }
	   catch (SQLException e) {
		   return false;
	   }
	   
	}
	
	public ResultSet getLecturesForDate (Date date) { //use Date.valueof to parse date eg getLecturesForDate(Date.valueOf("2019-01-29"))
		DBConnect query = new DBConnect();
		ResultSet lecturesForDate = query.getData("select * from Lecture where dayAndTime LIKE '" + date + "%' order by dayAndTime");
		return lecturesForDate;
	}
}
