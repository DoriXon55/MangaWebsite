package org.dorixon.websiteproject.repo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Manga(
        Long id,
        String title,
        String titleEnglish,
        String imageUrl,
        String synopsis,
        Double score,
        List<String> authors,
        List<String> genres,
        String status,
        Integer chapters,
        Integer volumes
) {}