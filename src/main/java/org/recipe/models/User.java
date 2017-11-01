package org.recipe.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Pattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_-]{4,11}", message = "Usernames must be between 4 and 11 characters, and may contain only letters and numbers")
    private String username;

    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToMany
    @JoinColumn(name = "author_id")
    private List<Recipe> recipes;

    @ManyToMany
    private List<Recipe> favorites;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = hashPassword(password);
    }

    @NotNull
    @Column(name = "pwhash")
    public String getPwHash() {
        return pwHash;
    }

    @SuppressWarnings("unused")
    private void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    @NotNull
    @Column(name = "username", unique = true)
    public String getUsername() {
        return username;
    }

    private static String hashPassword(String password) {
        return encoder.encode(password);
    }

    @SuppressWarnings("unused")
    private void setUsername(String username) {
        this.username = username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(Recipe recipe) {
        favorites.add(recipe);
    }
}
