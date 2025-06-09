/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author manongodefroy
 */

package hr.algebra.godefroyproject.repository;

import hr.algebra.godefroyproject.config.Database;
import hr.algebra.godefroyproject.model.Genre;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class GenreRepository {

    public static List<Genre> findAllGenres() throws Exception {
        List<Genre> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{ call sp_GetAllGenres() }");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                list.add(new Genre(
                    rs.getInt("Id"),
                    rs.getString("Name")
                ));
            }
        }
        return list;
    }

    public static Genre createGenre(Genre g) throws Exception {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{ call sp_CreateGenre(?,?) }")) {
            cs.setString(1, g.getName());
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();
            g.setId(cs.getInt(2));
            return g;
        }
    }

    public static void updateGenre(Genre g) throws Exception {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{ call sp_UpdateGenre(?,?) }")) {
            cs.setInt(1, g.getId());
            cs.setString(2, g.getName());
            cs.execute();
        }
    }

    public static void deleteGenre(int id) throws Exception {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{ call sp_DeleteGenre(?) }")) {
            cs.setInt(1, id);
            cs.execute();
        }
    }
}

