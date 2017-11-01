package org.recipe.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    @ManyToMany
    private List<Recipe> recipes;

    //Constructors
    public Menu() {}
    public Menu(String name){ this.name = name; }
    public void addItem(Recipe item) {recipes.add(item); }
    public void removeItem(Recipe item) {recipes.remove(item); }

    //Getters/Setters
    public void setId(int id) { this.id = id; }
    public int getId(){ return id; }
    public void setName(String name){ this.name = name; }
    public String getName() { return name; }
    public List<Recipe> getRecipes() {return recipes;}
}


