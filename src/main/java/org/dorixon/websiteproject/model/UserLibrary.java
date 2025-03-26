package org.dorixon.websiteproject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_library")
public class UserLibrary {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long mangaId;

    @Column(nullable = false)
    private String mangaTitle;

    private String mangaImageUrl;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus = ReadStatus.PLAN_TO_READ;
    public enum ReadStatus {
        READING, COMPLETED, ON_HOLD, DROPPED, PLAN_TO_READ
    }

}
