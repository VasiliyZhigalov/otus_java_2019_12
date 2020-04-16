package com.vasiliyzhigalov.controllers;

import com.vasiliyzhigalov.domain.User;
import com.vasiliyzhigalov.services.DbServiceUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class AdminController {
    DbServiceUser dbServiceUser;

    public AdminController(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping({"/","/admin"})
    public String userList(Model model, User user) {
        List<User> users = dbServiceUser.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping("/save")
    public RedirectView addUser(@ModelAttribute(value = "user") User user) {
        dbServiceUser.saveUser(user);
        return new RedirectView("/admin", true);
    }
}
