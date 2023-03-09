package com.example.notesapp;

import java.sql.*;

public class DataBaseHandler {
    private  Connection getConnection() throws  Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javadatabase", "pmaUser", "admin");
        return  con;
    }
    public ResultSet getAllUsers() throws  Exception{
        Connection con = getConnection();
        String query = "SELECT * FROM user";
        Statement stmt = con.createStatement();
        return  stmt.executeQuery(query);
    }

    public void addUser(int id, String name, String password) throws  Exception{
        Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO user VALUES (?,?,?);");
        pstmt.setInt(1,id);
        pstmt.setString(2,name);
        pstmt.setString(3,password);
        pstmt.execute();
    }

    public int getLastId(String tableName) throws Exception{
        Connection con = getConnection();
        String query = "SELECT * FROM " + tableName + " ORDER BY ID DESC LIMIT 1";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }return  0;
    }

    public ResultSet getNotesOfUser(int user_id) throws Exception{
        Connection con = getConnection();
        String query = "SELECT * FROM note WHERE user_id=" + user_id;
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }

    public ResultSet authenticate(String username, String password) throws Exception{
        Connection con = getConnection();
        String query = "SELECT * FROM user where username = '" + username + "' AND password = '" + password + "';";
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }

    public void addNote(int id,int user_id, String title,String content ) throws  Exception{
        Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO note VALUES (?,?,?,?);");
        pstmt.setInt(1,id);
        pstmt.setInt(2,user_id);
        pstmt.setString(3,title);
        pstmt.setString(4,content);
        pstmt.execute();
    }
    public  void deleteUniversal(String table_name, int id) throws  Exception{
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        String query = "DELETE FROM " + table_name + " WHERE id=" + id;
        stmt.execute(query);
    }

//    public static void main(String[] args) throws  Exception {
//        DataBaseHandler dbh = new DataBaseHandler();
//        ResultSet rs = dbh.getAllUsers();
//        while (rs.next()){
//            System.out.println(rs.getString(1));
//        }
//    }
}
