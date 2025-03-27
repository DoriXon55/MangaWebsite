package org.dorixon.websiteproject.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("statusCode", statusCode);
        }

        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (exception != null) {
            model.addAttribute("errorMessage", ((Exception) exception).getMessage());
        } else {
            model.addAttribute("errorMessage", "Wystąpił nieznany błąd");
        }

        return "error";
    }
}