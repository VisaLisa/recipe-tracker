package org.recipe.controllers;


import org.recipe.models.Recipe;
import org.recipe.models.RecipeFieldType;
import org.recipe.models.User;
import org.recipe.models.data.RecipeDao;
import org.recipe.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@Controller
@RequestMapping("search")
public class SearchController extends AbstractController {

    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "")
    public String search(Model model, HttpServletRequest request) {
        model.addAttribute(new SearchForm());
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        return "search";
    }

    @RequestMapping(value = "results")
    public String search(Model model,
                         @ModelAttribute SearchForm searchForm, HttpServletRequest request) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Iterable<Recipe> recipeList = recipeDao.findAll();
        Iterable<User> userList = userDao.findAll();
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        model.addAttribute("sessionUser", getUserFromSession(request.getSession()));
        String keyword = searchForm.getKeyword().toLowerCase();

        if (searchForm.getSearchField().equals(RecipeFieldType.ALL)) {
            for (Recipe recipe : recipeList) {
                if ((recipe.getName() + recipe.getIngredient()).toLowerCase().contains(keyword)) {
                    recipes.add(recipe);
                }
            }
            model.addAttribute("recipes", recipes);
        } else if (searchForm.getSearchField().equals(RecipeFieldType.INGREDIENT)) {
            for (Recipe recipe : recipeList) {
                if (recipe.getIngredient().toLowerCase().contains(keyword)) {
                    recipes.add(recipe);
                }
            }
            model.addAttribute("recipes", recipes);

        } else if (searchForm.getSearchField().equals(RecipeFieldType.NAME)) {
            for (Recipe recipe : recipeList) {
                if (recipe.getName().toLowerCase().contains(keyword)) {
                    recipes.add(recipe);

                }
            }
            model.addAttribute("recipes", recipes);
        } else if (searchForm.getSearchField().equals(RecipeFieldType.USERNAME)) {
            for (User user : userList) {
                if (user.getUsername().toLowerCase().contains(keyword)) {
                    users.add(user);
                }
            }
            model.addAttribute("users", users);
        }
        return "search";
    }

}
