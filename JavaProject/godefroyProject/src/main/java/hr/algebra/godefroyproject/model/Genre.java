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

public class Genre {
    private int    id;
    private String name;

    public Genre() { }

    public Genre(int id, String name) {
        this.id   = id;
        this.name = name;
    }
        public Genre(String name) {
        this.name = name;
    }

    public int    getId()   { return id; }
    public String getName() { return name; }

    public void setId(int i)       { this.id = i; }
    public void setName(String n)  { this.name = n; }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final Genre other = (Genre) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
}
