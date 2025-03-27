package org.dorixon.websiteproject.controller;

import org.dorixon.websiteproject.repo.Manga;
import org.dorixon.websiteproject.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/manga")
public class MangaController {


    private final MangaService mangaService;

    @Autowired
    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping({"", "/"})
    public String home(Model model) {
        List<Manga> popularMangas = mangaService.getPopularMangas(12);
        model.addAttribute("mangas", popularMangas);
        return "home";
    }

    @GetMapping("/search")
    public String searchMangas(@RequestParam String query, Model model)
    {
        List<Manga> mangas = mangaService.searchMangasByName(query);
        model.addAttribute("mangas", mangas);
        model.addAttribute("query", query);
        return "search-results";
    }

    @GetMapping("/{id}")
    public String mangaDetails(@PathVariable Long id, Model model)
    {
        Manga manga = mangaService.getMangaByID(id);
        if (manga == null) return "redirect:/manga?error=Manga nie została znaleziona";
        model.addAttribute("manga", manga);
        return "details";

    }
}
