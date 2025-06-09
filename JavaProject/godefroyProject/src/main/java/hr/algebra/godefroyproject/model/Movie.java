/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.model;

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

    // Construteur full
    public Movie(int id, String title, String description,
                 int releaseYear, int genreId, int directorId, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.genreId = genreId;
        this.directorId = directorId;
        this.imagePath = imagePath;
    }

    public Movie(String title,
                 String description,
                 int releaseYear,
                 int genreId,
                 int directorId,
                 String imagePath) {
        this(0,title,description,releaseYear,genreId,directorId,imagePath);
    }

    public int    getId()          { return id; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public int    getReleaseYear() { return releaseYear; }
    public int    getGenreId()     { return genreId; }
    public int    getDirectorId()  { return directorId; }
    public String getImagePath()   { return imagePath; }

    public void setId(int i)                { this.id = i; }
    public void setTitle(String t)          { this.title = t; }
    public void setDescription(String d)    { this.description = d; }
    public void setReleaseYear(int y)       { this.releaseYear = y; }
    public void setGenreId(int g)           { this.genreId = g; }
    public void setDirectorId(int d)        { this.directorId = d; }
    public void setImagePath(String path)   { this.imagePath = path; }

    @Override
    public String toString() {
        return title + " (" + releaseYear + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + this.releaseYear;
        hash = 47 * hash + this.genreId;
        hash = 47 * hash + this.directorId;
        hash = 47 * hash + Objects.hashCode(this.imagePath);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.releaseYear != other.releaseYear) {
            return false;
        }
        if (this.genreId != other.genreId) {
            return false;
        }
        if (this.directorId != other.directorId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.imagePath, other.imagePath);
    }
}
