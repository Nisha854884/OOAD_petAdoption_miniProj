package com.petadoption.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ViewController: GRASP Controller principle — handles all inbound HTTP GET
 * requests and delegates to the appropriate Thymeleaf view.
 * Contains NO business logic; delegates to service layer for any data needs.
 */
@Controller
public class ViewController {

    /** Landing page */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /** Login / signup page */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /** Admin dashboard */
    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    /** Adopter dashboard */
    @GetMapping("/adopter")
    public String adopterDashboard() {
        return "adopter-dashboard";
    }

    /** Staff dashboard */
    @GetMapping("/staff")
    public String staffDashboard() {
        return "staff-dashboard";
    }
}
