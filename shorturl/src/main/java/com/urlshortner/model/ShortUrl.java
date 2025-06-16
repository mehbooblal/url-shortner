package com.urlshortner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The ShortUrl class represents a URL shortening entity that maps a long original URL
 * to a shorter_url, unique code. This entity is stored in the "short_urls" table and includes
 * relevant metadata such as creation timestamp.
 *
 */

@Entity
@Table(name = "short_urls")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Setter
    @Getter
    @Column(name="short_url", nullable = false, unique = true)
    private String shortUrl;

    private LocalDateTime  createdAt = LocalDateTime.now();


    // Getters and setters
}
