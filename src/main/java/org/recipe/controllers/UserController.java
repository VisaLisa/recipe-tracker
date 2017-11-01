package org.recipe.controllers;

import org.recipe.models.User;
import org.recipe.models.data.RecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractController {

    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userPage(Model model,  int id, HttpServletRequest request) {
        User user = userDao.findOne(id);
        model.addAttribute("title", user.getUsername() + "'s Recipes");
        model.addAttribute("recipes", recipeDao.findByAuthor(user));
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        model.addAttribute("isAuthor",
                getUserFromSession(request.getSession()) == user);
        model.addAttribute("sessionUser", getUserFromSession(request.getSession()));
        model.addAttribute("faveTitle", user.getUsername() + "'s Favorites");
        model.addAttribute("favorites", user.getFavorites());
        return "list-recipes";
    }

}
