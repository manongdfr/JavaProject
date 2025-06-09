/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hr.algebra.movieapp;
import hr.algebra.movieapp.config.Database;
import hr.algebra.movieapp.model.Movie;
import hr.algebra.movieapp.repository.MovieRepository;
import hr.algebra.movieapp.util.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author manongodefroy
 */
public class MovieApp {

    public static void main(String[] args) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            System.out.println("Connexion à SQL Server réussie !");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //SqlUtils.runSqlScript("init.sql");
        SqlUtils.runSqlScript("clear.sql");
        //SqlUtils.runSqlScript("clear.sql"); // à activer si besoin
        
        // Create
        Movie m = new Movie("Matrix", "Sci-fi", 1999, 1, 1, "matrix.jpg");
        Movie created = MovieRepository.createMovie(m);
        System.out.println("New movie id = " + created.getId());

        // Read
        List<Movie> movies = MovieRepository.findAllMovies();
        movies.forEach(System.out::println);

        // Update
        created.setTitle("Matrix Reloaded");
        MovieRepository.updateMovie(created);
        System.out.println("After update:");
        MovieRepository.findAllMovies().forEach(System.out::println);

        // Delete
        MovieRepository.deleteMovie(created.getId());
        System.out.println("After delete:");
        MovieRepository.findAllMovies().forEach(System.out::println);        
    }
}
