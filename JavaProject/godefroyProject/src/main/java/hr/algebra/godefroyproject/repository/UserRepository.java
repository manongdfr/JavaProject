/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.repository;

import hr.algebra.godefroyproject.config.Database;
import hr.algebra.godefroyproject.model.AppUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author manongodefroy
 */

/**
 * 
 * AppUser => authentication 
 */
public class UserRepository {

public static AppUser authenticate(String username, String password) throws Exception {
    String sql = "{ call sp_AuthenticateUser(?,?) }";
    try (Connection conn = Database.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setString(1, username);
        cs.setString(2, password);

        try (ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                AppUser u = new AppUser();
                u.setId       (rs.getInt   ("Id"));
                u.setUsername (rs.getString("Username"));
                u.setPassword (rs.getString("Password"));  
                u.setRole     (rs.getString("Role"));
                return u;
            }
        }
    }
    return null;
}


    public static AppUser createUser(AppUser u) throws Exception {
        String sql = "{ call sp_CreateUser(?,?,?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, u.getUsername());
            cs.setString(2, u.getPassword());
            cs.setString(3, u.getRole());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            cs.execute();
            u.setId(cs.getInt(4));
            return u;
        }
    }
}

