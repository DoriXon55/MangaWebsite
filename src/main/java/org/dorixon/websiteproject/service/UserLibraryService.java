package org.dorixon.websiteproject.service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dorixon.websiteproject.model.UserLibrary;
import org.dorixon.websiteproject.repo.UserLibraryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;

    public List<UserLibrary> getUserLibrary(String username) {
        return userLibraryRepository.findByUsername(username);
    }




    @Transactional
    public UserLibrary addMangaToLibrary(String username, Long mangaId, String mangaTitle,
                                         String mangaImageUrl, UserLibrary.ReadStatus readStatus) {
        Optional<UserLibrary> existingEntry = userLibraryRepository.findByUsernameAndMangaId(username, mangaId);

        if (existingEntry.isPresent()) {
            UserLibrary entry = existingEntry.get();
            entry.setReadStatus(readStatus);
            return userLibraryRepository.save(entry);
        } else {
            UserLibrary newEntry = new UserLibrary();
            newEntry.setUsername(username);
            newEntry.setMangaId(mangaId);
            newEntry.setMangaTitle(mangaTitle);
            newEntry.setMangaImageUrl(mangaImageUrl);
            newEntry.setReadStatus(readStatus);
            newEntry.setAddedAt(LocalDateTime.now());
            return userLibraryRepository.save(newEntry);
        }
    }

    @Transactional
    public void updateReadStatus(String username, Long mangaId, UserLibrary.ReadStatus readStatus) {
        Optional<UserLibrary> entry = userLibraryRepository.findByUsernameAndMangaId(username, mangaId);
        entry.ifPresent(userLibrary -> {
            userLibrary.setReadStatus(readStatus);
            userLibraryRepository.save(userLibrary);
        });
    }

    @Transactional
    public void removeMangaFromLibrary(String username, Long mangaId) {
        userLibraryRepository.deleteByUsernameAndMangaId(username, mangaId);
    }




}
