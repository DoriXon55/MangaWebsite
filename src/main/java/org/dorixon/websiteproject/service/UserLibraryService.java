package org.dorixon.websiteproject.service;

import org.dorixon.websiteproject.client.JikanClient;
import org.dorixon.websiteproject.model.UserLibrary;
import org.dorixon.websiteproject.repo.Manga;
import org.dorixon.websiteproject.repo.UserLibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final JikanClient jikanClient;


    @Autowired
    public UserLibraryService(UserLibraryRepository userLibraryRepository, JikanClient jikanClient) {
        this.userLibraryRepository = userLibraryRepository;
        this.jikanClient = jikanClient;
    }


    public List<UserLibrary> getUserLibrary(String username) {
        return userLibraryRepository.findByUsername(username);
    }
    public UserLibrary addToLibrary(String username, Long mangaId)
    {
        if (userLibraryRepository.existsByUsernameAndMangaId(username, mangaId))
        {
            throw new IllegalStateException("Manga już istnieje w Twojej bibliotece");
        }
        Manga manga = jikanClient.getMangaById(mangaId);
        if(manga == null)
        {
            throw new IllegalStateException("Nie znaleziono mangi o podanym id");
        }
        UserLibrary libraryEntry = new UserLibrary();
        libraryEntry.setUsername(username);
        libraryEntry.setMangaId(mangaId);
        libraryEntry.setMangaId(mangaId);
        libraryEntry.setMangaTitle(manga.title());
        libraryEntry.setMangaImageUrl(manga.imageUrl());
        libraryEntry.setAddedAt(LocalDateTime.now());
        return userLibraryRepository.save(libraryEntry);
    }

    public UserLibrary updateReadStatus(String username, Long mangaId, UserLibrary.ReadStatus status)
    {
        Optional<UserLibrary> existingEntry = userLibraryRepository.findByUsernameAndMangaId(username, mangaId);
        if (existingEntry.isEmpty())
        {
            throw new IllegalStateException("Nie znaleziono mangi w Twojej bibliotece");
        }
        UserLibrary entry = existingEntry.get();
        entry.setReadStatus(status);
        return userLibraryRepository.save(entry);
    }

    public void removeFromLibrary(String username, Long mangaId) {
        Optional<UserLibrary> existingEntry = userLibraryRepository.findByUsernameAndMangaId(username, mangaId);

        if (existingEntry.isEmpty()) {
            throw new IllegalArgumentException("Nie znaleziono mangi w Twojej bibliotece");
        }

        userLibraryRepository.delete(existingEntry.get());
    }



}
