package org.dorixon.websiteproject.repo;

import org.dorixon.websiteproject.model.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLibraryRepository extends JpaRepository<UserLibrary, Long> {
    List<UserLibrary> findByUsername(String username);
    Optional<UserLibrary> findByUsernameAndMangaId(String username, Long mangaId);
    void deleteByUsernameAndMangaId(String username, Long mangaId);
}
