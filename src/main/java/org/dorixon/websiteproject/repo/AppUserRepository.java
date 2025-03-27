package org.dorixon.websiteproject.repo;

import org.dorixon.websiteproject.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}