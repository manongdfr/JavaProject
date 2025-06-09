/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.config;

/**
 *
 * @author manongodefroy
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    //Docker Desktop database & Azure Data Studio for connexion

    private static final String URL = 
        "jdbc:sqlserver://localhost:1433;databaseName=java01db;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";           
    private static final String PASSWORD = "Password123!"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
