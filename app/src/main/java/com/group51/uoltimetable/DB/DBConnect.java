package com.group51.uoltimetable.DB;

import java.sql.*;

public class DBConnect {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String URL = "51.89.139.204:3306/UOLTimetable";
    private String user = "admin";
    private String password = "9Xiba0B9";
    private String misc = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public DBConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + URL + misc, user, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // function takes an SQL query string as an argument and returns a result set
    // with the result of the query
    // to use result set, use a while loop while(resultSet.next())
    // reference column from from current row in result set by using
    // resultSet.getString("column name") if type is string, getInt if type is int etc.
    public ResultSet getData(String SQLQuery) {
        try {
            resultSet = statement.executeQuery(SQLQuery);
            return resultSet;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void setData(String SQLQuery) {
        try {
            statement.executeUpdate(SQLQuery);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}