/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.movieapp.repository;

import hr.algebra.movieapp.config.Database;
import hr.algebra.movieapp.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manongodefroy
 */

public class MovieRepository {

    // 1) CREATE via procédure sp_CreateMovie
    public static Movie createMovie(Movie movie) throws SQLException {
        String sql = "{ CALL sp_CreateMovie(?,?,?,?,?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, movie.getTitle());
            cs.setString(2, movie.getDescription());
            cs.setInt(3, movie.getReleaseYear());
            cs.setInt(4, movie.getGenreId());
            cs.setInt(5, movie.getDirectorId());
            cs.setString(6, movie.getImagePath());

            // exécute et récupère l'ID généré
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                movie.setId(rs.getInt(1));
            }
            return movie;
        }
    }

    // 2) READ ALL via sp_GetAllMovies
    public static List<Movie> findAllMovies() throws SQLException {
        String sql = "{ CALL sp_GetAllMovies() }";
        List<Movie> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("Id"));
                m.setTitle(rs.getString("Title"));
                m.setDescription(rs.getString("Description"));
                m.setReleaseYear(rs.getInt("ReleaseYear"));
                m.setGenreId(rs.getInt("GenreId"));
                m.setDirectorId(rs.getInt("DirectorId"));
                m.setImagePath(rs.getString("ImagePath"));
                list.add(m);
            }
        }
        return list;
    }

    // 3) UPDATE via sp_UpdateMovie
    public static void updateMovie(Movie movie) throws SQLException {
        String sql = "{ CALL sp_UpdateMovie(?,?,?,?,?,?,?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, movie.getId());
            cs.setString(2, movie.getTitle());
            cs.setString(3, movie.getDescription());
            cs.setInt(4, movie.getReleaseYear());
            cs.setInt(5, movie.getGenreId());
            cs.setInt(6, movie.getDirectorId());
            cs.setString(7, movie.getImagePath());

            cs.execute();
        }
    }

    // 4) DELETE via sp_DeleteMovie
    public static void deleteMovie(int id) throws SQLException {
        String sql = "{ CALL sp_DeleteMovie(?) }";
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        }
    }
}
