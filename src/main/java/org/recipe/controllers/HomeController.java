package org.recipe.controllers;

import org.recipe.models.User;
import org.recipe.models.data.RecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController extends AbstractController {

    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "/all")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("title", "All Recipes");
        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        model.addAttribute("favoritesOff", true);
        return "index";
    }

    @RequestMapping(value = "/home")
    public String userIndex(Model model, HttpServletRequest request) {
        User user = getUserFromSession(request.getSession());
        model.addAttribute("title", user.getUsername() + "'s Recipes");
        model.addAttribute("recipes", recipeDao.findByAuthor(user));
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        model.addAttribute("favorites", user.getFavorites());
        model.addAttribute("faveTitle", "My Favorites");
        return "index";
    }

    @RequestMapping(value = "/about")
    public String aboutIndex (Model model,HttpServletRequest request){
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));

        return "about";
    }

    @RequestMapping(value = "/map")
    public String mapIndex (Model model, HttpServletRequest request){
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        return "map";
    }
    // TODO own recipe

}
