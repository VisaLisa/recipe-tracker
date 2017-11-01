package org.recipe.controllers;

import org.recipe.models.Menu;
import org.recipe.models.Recipe;
import org.recipe.models.data.MenuDao;
import org.recipe.models.data.RecipeDao;
import org.recipe.models.forms.MenuForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController extends AbstractController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpServletRequest request){

        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayMenuForm(Model model, HttpServletRequest request){

        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processMenuForm(Model model, @ModelAttribute @Valid
            Menu menu, Errors errors, HttpServletRequest request) {

        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        menuDao.save(menu);

        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId, HttpServletRequest request) {

        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        Menu menu = menuDao.findOne(menuId);
        model.addAttribute("menu", menu);

        return "menu/view";
    }

    @RequestMapping(value = "edit-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId, HttpServletRequest request) {

        Menu menu = menuDao.findOne(menuId);
        MenuForm form = new MenuForm(
                recipeDao.findAll(), menu);
//        MenuForm removeForm = new MenuForm(
//                recipes.menu, menu);
        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        model.addAttribute("title", "Edit recipes for: " + menu.getName());
        model.addAttribute("addItem", "Add Recipe");
        model.addAttribute("removeItem", "Remove Recipe");
        model.addAttribute("form", form);

        return "menu/edit-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model,
                          @ModelAttribute @Valid MenuForm addForm,
                          Errors errors, HttpServletRequest request) {

        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        if (errors.hasErrors()) {
            model.addAttribute("form", addForm);
            return "menu/edit-item";
        }

        Recipe theRecipe = recipeDao.findOne(addForm.getRecipeId());
        Menu theMenu = menuDao.findOne(addForm.getMenuId());
        theMenu.addItem(theRecipe);
        menuDao.save(theMenu);

        return "redirect:view/" + theMenu.getId();
    }

    @RequestMapping(value = "remove-item", method = RequestMethod.POST)
    public String removeItem(Model model,
                          @ModelAttribute @Valid MenuForm removeForm,
                          Errors errors, HttpServletRequest request) {

        model.addAttribute("sessionOn", isSessionActive(request.getSession()));
        if (errors.hasErrors()) {
            model.addAttribute("form", removeForm);
            return "menu/edit-item";
        }

        Recipe theRecipe = recipeDao.findOne(removeForm.getRecipeId());
        Menu theMenu = menuDao.findOne(removeForm.getMenuId());
        theMenu.removeItem(theRecipe);
        menuDao.save(theMenu);

        return "redirect:view/" + theMenu.getId();
    }


}
