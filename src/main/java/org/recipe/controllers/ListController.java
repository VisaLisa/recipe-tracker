package org.recipe.controllers;

import org.recipe.models.RecipeFieldType;
import org.recipe.models.data.RecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "list")
public class ListController extends AbstractController {

    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "")
    public String list(Model model, HttpServletRequest request) {
        RecipeFieldType[] fields = RecipeFieldType.values();
        model.addAttribute("fields", fields);
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        return "list";
    }

    @RequestMapping(value = "username")
    public String listUser(Model model, HttpServletRequest request) {

        model.addAttribute("title", "All Users");
        model.addAttribute("users", userDao.findAll());
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        return "list-users";
    }

    @RequestMapping(value = "all")
    public String listAllRecipes(Model model, HttpServletRequest request) {

        model.addAttribute("title", "All Recipes");
        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        model.addAttribute("sessionUser", getUserFromSession(request.getSession()));
        model.addAttribute("favoritesOff", true);
        return "list-recipes";
    }

}
