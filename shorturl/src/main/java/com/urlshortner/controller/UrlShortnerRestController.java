package com.urlshortner.controller;

import com.urlshortner.model.ShortUrl;
import com.urlshortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.InvalidUrlException;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlShortnerRestController {

    @Autowired
    private UrlService service;

    @PostMapping
    public ResponseEntity<?> createUrl(@RequestParam String url, @RequestParam(required = false) String alias) {
        try {
            ShortUrl shortUrl = service.createShortUrl(url, alias);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", shortUrl.getOriginalUrl())
                    .body(shortUrl);
        } catch (InvalidUrlException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid URL format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Alias already in use");
        }
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable String shortUrl) {
        ShortUrl url = service.getByShortUrl(shortUrl);

        if (ObjectUtils.isEmpty(url)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<!doctype html><title>404 Not Found</title><h1 style=\"text-align: center\">404 Not Found</h1>");
        }

        // Redirect 302
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", url.getOriginalUrl()).build();
    }

    @GetMapping
    public List<ShortUrl> list() {
        return service.getAll();
    }

    @Transactional
    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortUrl) {
        boolean deleted = service.deleteByShortUrl(shortUrl);
        System.out.println("In deleteUrl, deleted = " + deleted + " for shortUrl = " + shortUrl);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 if URL not found
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 for successful deletion
    }
}
