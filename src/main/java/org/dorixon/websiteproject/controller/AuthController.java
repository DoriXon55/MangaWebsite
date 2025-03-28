package org.dorixon.websiteproject.controller;

import org.dorixon.websiteproject.model.AppUser;
import org.dorixon.websiteproject.service.RecaptchaService;
import org.dorixon.websiteproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;
    private final RecaptchaService recaptchaService;

    @Autowired
    public AuthController(UserService userService, RecaptchaService recaptchaService) {
        this.userService = userService;
        this.recaptchaService = recaptchaService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/perform_register")
    public String registerUser(
            @ModelAttribute("user") AppUser user,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            RedirectAttributes redirectAttributes) {

        // Weryfikacja CAPTCHA
        if (!recaptchaService.verify(recaptchaResponse)) {
            redirectAttributes.addFlashAttribute("error", "Weryfikacja CAPTCHA nie powiodła się");
            return "redirect:/register";
        }

        try {
            // Rejestracja użytkownika
            userService.registerNewUser(user);
            redirectAttributes.addFlashAttribute("success", "Rejestracja udana! Sprawdź swój email, aby zweryfikować konto.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}