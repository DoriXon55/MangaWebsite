package org.dorixon.websiteproject.service;


import org.dorixon.websiteproject.client.JikanClient;
import org.dorixon.websiteproject.repo.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaService {

    private final JikanClient jikanClient;

    @Autowired
    public MangaService(JikanClient jikanClient ) {
        this.jikanClient = jikanClient;
    }

    @Cacheable("popularMangas")
    public List<Manga> getPopularMangas(int limit)
    {
        return jikanClient.getPopularMangas(limit);
    }

    public List<Manga> searchMangasByName(String name)
    {
        return jikanClient.searchMangasByName(name);
    }

    @Cacheable(value = "mangaDetails", key = "#id")
    public Manga getMangaByID(Long id)
    {
        return jikanClient.getMangaById(id);
    }



}
