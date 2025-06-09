/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.repository;

import hr.algebra.godefroyproject.config.Database;
import hr.algebra.godefroyproject.model.Director;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author manongodefroy
 */

public class DirectorRepository {

    public static List<Director> findAllDirectors() throws Exception {
        List<Director> list = new ArrayList<>();
        String sql = "{ call sp_GetAllDirectors() }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                // find the name and split
                String fullName = rs.getString("Name");
                String[] parts  = fullName.split(" ", 2);
                String firstName = parts[0];
                String lastName  = parts.length > 1 ? parts[1] : "";
                list.add(new Director(
                    rs.getInt("Id"),
                    firstName,
                    lastName
                ));
            }
        }
        return list;
    }

    public static Director createDirector(Director d) throws Exception {
        String sql = "{ call sp_CreateDirector(?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            // create the name by first name and adding the last name
            String fullName = d.getFirstName()
                            + (d.getLastName().isEmpty() ? "" : " " + d.getLastName());
            cs.setString(1, fullName);        
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();

            d.setId(cs.getInt(2));
            return d;
        }
    }

    public static void updateDirector(Director d) throws Exception {
        String sql = "{ call sp_UpdateDirector(?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, d.getId());
            // i full name in 1 parameter
            String fullName = d.getFirstName()
                            + (d.getLastName().isEmpty() ? "" : " " + d.getLastName());
            cs.setString(2, fullName);
            cs.execute();
        }
    }

    public static void deleteDirector(int id) throws Exception {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{ call sp_DeleteDirector(?) }")) {
            cs.setInt(1, id);
            cs.execute();
        }
    }
}
