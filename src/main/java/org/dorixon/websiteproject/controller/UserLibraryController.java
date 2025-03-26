package org.dorixon.websiteproject.controller;


import org.dorixon.websiteproject.model.UserLibrary;
import org.dorixon.websiteproject.service.UserLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/library")
public class UserLibraryController {

    private final UserLibraryService libraryService;

    @Autowired
    public UserLibraryController(UserLibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String getUserLibrary(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<UserLibrary> userLibrary = libraryService.getUserLibrary(userDetails.getUsername());
        model.addAttribute("library", userLibrary);
        return "library/index";
    }

    @PostMapping("/add/{mangaId}")
    public String addToLibrary(@PathVariable Long mangaId, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            libraryService.addToLibrary(userDetails.getUsername(), mangaId);
            return "redirect:/manga" + mangaId + "?success=Manga została dodana do biblioteki";
        } catch (Exception e) {
            return "redirect:/manga" + mangaId + "?error=" + e.getMessage();
        }
    }


    @PostMapping("/update/{mangaId}")
    public String updateReadStatus(@PathVariable Long mangaId, @RequestParam UserLibrary.ReadStatus status, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            libraryService.updateReadStatus(userDetails.getUsername(), mangaId, status);
            return "redirect:/library?success=Status czytania został zaktualizowany";
        } catch (Exception e) {
            return "redirect:/library?error=" + e.getMessage();
        }
    }

    @PostMapping("/remove/{mangaId}")
    public String removeFromLibrary(@PathVariable Long mangaId, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            libraryService.removeFromLibrary(userDetails.getUsername(), mangaId);
            return "redirect:/library?success=Manga została usunięta z biblioteki";
        } catch (Exception e)
        {
            return "redirect:/library?error=" + e.getMessage();
        }
    }


}
