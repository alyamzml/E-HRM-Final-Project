package com.emp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLDatabase {

    private Connection con;
    private static String driver = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://db4free.net:3306/aisyahmaryam";
    static final String DB_USER = "aisymrym";
    static final String DB_PASSWORD = "012345678";

    // to lock the database
    public MySQLDatabase() {
        try {
            Class.forName(MySQLDatabase.driver);
        } catch (Exception e) {System.out.println(e);}
    }

    // to connect to database
    public Connection connect() {
        try {
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");
        } catch (Exception e) {System.out.println(e);}
        return con;
    }


    // to close the connection
    public void close(Connection con) {
        try {
            con.close();
        } catch (Exception e) {System.out.println(e);}
    }
}
