package hr.algebra.godefroyproject.model;

import java.util.Objects;

/**
 * User in the app
 */
public class AppUser {
    private int    id;
    private String username;
    private String password;
    private String role;

    public AppUser() { }

    public AppUser(int id, String username, String password, String role) {
        this.id       = id;
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    public int    getId()       { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }

    public void setId(int id)             { this.id = id; }
    public void setUsername(String u)     { this.username = u; }
    public void setPassword(String p)     { this.password = p; }
    public void setRole(String r)         { this.role = r; }

    /**
     * if admin : return true
     */
    public boolean isAdmin() {
        return role != null && role.equalsIgnoreCase("Admin");
    }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AppUser)) return false;
        AppUser other = (AppUser) obj;
        return id == other.id
            && Objects.equals(username, other.username)
            && Objects.equals(password, other.password)
            && Objects.equals(role, other.role);
    }
}
