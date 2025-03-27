package org.dorixon.websiteproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.dorixon.websiteproject.model.UserLibrary;
import org.dorixon.websiteproject.service.UserLibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final UserLibraryService userLibraryService;

    @GetMapping
    public String getUserLibrary(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("mangaList", userLibraryService.getUserLibrary(username));
            model.addAttribute("readStatuses", UserLibrary.ReadStatus.values());
            return "library";
        }
        return "redirect:/login";
    }

    @PostMapping("/add")
    public String addToLibrary(
            Authentication authentication,
            @RequestParam Long mangaId,
            @RequestParam String mangaTitle,
            @RequestParam String mangaImageUrl,
            @RequestParam(required = false) UserLibrary.ReadStatus readStatus,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            UserLibrary.ReadStatus status = readStatus != null ? readStatus : UserLibrary.ReadStatus.PLAN_TO_READ;
            userLibraryService.addMangaToLibrary(username, mangaId, mangaTitle, mangaImageUrl, status);
            redirectAttributes.addFlashAttribute("success", "Manga dodana do biblioteki");
            return "redirect:/manga/" + mangaId;
        }
        return "redirect:/login";
    }

    @PostMapping("/update-status")
    public String updateReadStatus(
            Authentication authentication,
            @RequestParam Long mangaId,
            @RequestParam UserLibrary.ReadStatus readStatus,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            userLibraryService.updateReadStatus(username, mangaId, readStatus);
            redirectAttributes.addFlashAttribute("success", "Status czytania zaktualizowany");
            return "redirect:/library";
        }
        return "redirect:/login";
    }

    @PostMapping("/remove")
    public String removeFromLibrary(
            Authentication authentication,
            @RequestParam Long mangaId,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            userLibraryService.removeMangaFromLibrary(username, mangaId);
            redirectAttributes.addFlashAttribute("success", "Manga usunięta z biblioteki");
            return "redirect:/library";
        }
        return "redirect:/login";
    }
}