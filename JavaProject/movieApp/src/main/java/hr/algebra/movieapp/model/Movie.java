/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.movieapp.model;
import java.util.Objects;

/**
 *
 * @author manongodefroy
 */

public class Movie {

    private int id;
    private String title;
    private String description;
    private int releaseYear;
    private int genreId;
    private int directorId;
    private String imagePath;

    // Constructeur vide
    public Movie() {}

    // Constructeur sans id (pour insertion)
    public Movie(String title, String description, int releaseYear,
                 int genreId, int directorId, String imagePath) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.genreId = genreId;
        this.directorId = directorId;
        this.imagePath = imagePath;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }
    public int getDirectorId() { return directorId; }
    public void setDirectorId(int directorId) { this.directorId = directorId; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie m = (Movie) o;
        return id == m.id &&
               releaseYear == m.releaseYear &&
               genreId == m.genreId &&
               directorId == m.directorId &&
               Objects.equals(title, m.title) &&
               Objects.equals(description, m.description) &&
               Objects.equals(imagePath, m.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseYear,
                            genreId, directorId, imagePath);
    }

    @Override
    public String toString() {
        return "Movie{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", releaseYear=" + releaseYear +
               '}';
    }
}
