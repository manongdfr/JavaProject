/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.repository;

import hr.algebra.godefroyproject.config.Database;
import hr.algebra.godefroyproject.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author manongodefroy
 */
public class MovieRepository {

public static List<Movie> findAllMovies() throws Exception {
        String sql = "{ call sp_GetAllMovies }";
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                movies.add(new Movie(
                    rs.getInt("Id"),
                    rs.getString("Title"),
                    rs.getString("Description"),      // can be null
                    rs.getInt("ReleaseYear"),
                    rs.getInt("GenreId"),
                    rs.getInt("DirectorId"),
                    rs.getString("ImagePath")         // can be null
                ));
            }
        }
        return movies;
    }

    public static Movie createMovie(Movie m) throws Exception {
        String sql = "{ call sp_CreateMovie(?,?,?,?,?,?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, m.getTitle());
            cs.setString(2, m.getDescription());
            cs.setInt   (3, m.getReleaseYear());
            cs.setInt   (4, m.getGenreId());
            cs.setInt   (5, m.getDirectorId());
            cs.setString(6, m.getImagePath());
            cs.registerOutParameter(7, java.sql.Types.INTEGER);

            cs.execute();
            m.setId(cs.getInt(7));
            return m;
        }
    }


    public static void updateMovie(Movie m) throws Exception {
        String sql = "{ call sp_UpdateMovie(?,?,?,?,?,?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt   (1, m.getId());
            cs.setString(2, m.getTitle());
            cs.setString(3, m.getDescription());
            cs.setInt   (4, m.getReleaseYear());
            cs.setInt   (5, m.getGenreId());
            cs.setInt   (6, m.getDirectorId());
            cs.setString(7, m.getImagePath());
            cs.execute();
        }
    }

    public static void deleteMovie(int id) throws Exception {
        String sql = "{ call sp_DeleteMovie(?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.execute();
        }
    }
}
