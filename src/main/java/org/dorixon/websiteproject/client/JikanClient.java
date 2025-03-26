package org.dorixon.websiteproject.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dorixon.websiteproject.exceptions.ApiException;
import org.dorixon.websiteproject.repo.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Component
public class JikanClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String URL = "https://api.jikan.moe/v4/";

    private long lastRequestTime = 0;
    private static final long MIN_REQUEST_INTERVAL = 1000;


    @Autowired
    public JikanClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Manga> getPopularMangas(int limit)
    {
        try{
            rateLimit();
            String url = URL + "/top/manga?limit=" + limit;
            String response = restTemplate.getForObject(url, String.class);

            return parseMangaListFromJson(response);
        } catch (Exception e)
        {
            throw new ApiException("Failed to get popular mangas", e);
        }
    }

    public List<Manga> searchMangasByName(String name)
    {
        try{
            rateLimit();
            String url = URL + "manga/search?q=" + name;
            String response = restTemplate.getForObject(url, String.class);
            return parseMangaListFromJson(response);
        } catch (Exception e)
        {
            throw new ApiException("Failed to search mangas by name", e);
        }
    }


    public Manga getMangaById(Long id)
    {
        try {
            rateLimit();
            String url = URL + "/manga/" + id;
            String response = restTemplate.getForObject(url, String.class);

            JsonNode root = objectMapper.readTree(response);
            if (root.has("data")) {
                return parseMangaFromJson(root.get("data"));
            }
            return null;
        } catch (Exception e) {
            System.err.println("Błąd podczas pobierania mangi: " + e.getMessage());
            return null;
        }
    }



    private List<Manga> parseMangaListFromJson(String response) throws Exception {
        List<Manga> mangas = new ArrayList<>();
        JsonNode root = objectMapper.readTree(response);
        if(root.has("data") && root.get("data").isArray()){
            for(JsonNode mangaNode : root.get("data")){
                mangas.add(parseMangaFromJson(mangaNode));
            }
        }
        return mangas;
    }

    private Manga parseMangaFromJson(JsonNode node) {
        String imageUrl = node.path("images").path("jpg").path("image_url").asText("");

        // Pobieranie autorów
        List<String> authors = new ArrayList<>();
        JsonNode authorsNode = node.path("authors");
        if (authorsNode.isArray()) {
            for (JsonNode author : authorsNode) {
                authors.add(author.path("name").asText());
            }
        }

        // Pobieranie gatunków
        List<String> genres = new ArrayList<>();
        JsonNode genresNode = node.path("genres");
        if (genresNode.isArray()) {
            for (JsonNode genre : genresNode) {
                genres.add(genre.path("name").asText());
            }
        }

        return new Manga(
                node.path("mal_id").asLong(),
                node.path("title").asText(),
                node.path("title_english").asText(""),
                imageUrl,
                node.path("synopsis").asText(""),
                node.path("score").asDouble(0.0),
                authors,
                genres,
                node.path("status").asText(""),
                node.path("chapters").asInt(0),
                node.path("volumes").asInt(0)
        );
    }




    private void rateLimit(){
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRequest = currentTime - lastRequestTime;
        if(timeSinceLastRequest < MIN_REQUEST_INTERVAL){
            try {
                TimeUnit.MILLISECONDS.sleep(MIN_REQUEST_INTERVAL - timeSinceLastRequest);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }




}
