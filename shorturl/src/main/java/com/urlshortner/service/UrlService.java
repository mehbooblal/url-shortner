package com.urlshortner.service;

import com.urlshortner.model.ShortUrl;
import com.urlshortner.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    private final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private UrlRepository repository;

    public ShortUrl createShortUrl(String originalUrl, String customAlias) {


        if(!isValidUrl(originalUrl)) {
            throw new RuntimeException("Invalid URL format");
        }
        if (customAlias != null && repository.findByShortUrl(customAlias).isPresent()) {
            throw new RuntimeException("Alias already exists");
        }

        String code = (customAlias != null && !customAlias.isBlank()) ?
                customAlias : UUID.randomUUID().toString().substring(0, 6);

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortUrl(code);
        return repository.save(shortUrl);
    }

    public ShortUrl getByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .orElse(null);
    }

    public boolean deleteByShortUrl(String shortUrl) {

        Optional<ShortUrl> url = repository.findByShortUrl(shortUrl);
        if (url.isEmpty()) {
            return false;
        }

        repository.deleteByShortUrl(shortUrl);
        return true;
    }

    public List<ShortUrl> getAll() {
        return repository.findAll();
    }

    public boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();  // Ensures valid URL format
            return true;
        } catch (MalformedURLException | IllegalArgumentException | URISyntaxException e) {
            logger.error("Invalid URL format: {}", e.getMessage());
            return false;
        }
    }

}
