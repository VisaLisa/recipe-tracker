package org.recipe.controllers;

import org.recipe.models.Recipe;
import org.recipe.models.User;
import org.recipe.models.data.RecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "favorite")
public class FavoriteController extends AbstractController{
    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addFavorite(Model model, int recipeId, HttpServletRequest request) {
        Recipe recipe = recipeDao.findOne(recipeId);
        User user = getUserFromSession(request.getSession());
        user.addFavorite(recipe);
        userDao.save(user);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
