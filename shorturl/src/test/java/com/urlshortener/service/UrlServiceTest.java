package com.urlshortener.service;

import com.urlshortner.model.ShortUrl;
import com.urlshortner.repository.UrlRepository;
import com.urlshortner.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShortUrlWithoutAlias() {
        String originalUrl = "https://example.com";
        when(urlRepository.save(any(ShortUrl.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ShortUrl result = urlService.createShortUrl(originalUrl, null);

        assertEquals(originalUrl, result.getOriginalUrl());
        assertNotNull(result.getShortUrl());
        verify(urlRepository).save(any(ShortUrl.class));
    }

    @Test
    void testCreateShortUrlWithAlias() {
        String originalUrl = "https://example.com";
        String alias = "custom123";

        when(urlRepository.findByShortUrl(alias)).thenReturn(Optional.empty());
        when(urlRepository.save(any(ShortUrl.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ShortUrl result = urlService.createShortUrl(originalUrl, alias);

        assertEquals(originalUrl, result.getOriginalUrl());
        assertEquals(alias, result.getShortUrl());
        verify(urlRepository).save(any(ShortUrl.class));
    }

    @Test
    void testCreateShortUrlWithDuplicateAlias() {
        String originalUrl = "https://example.com";
        String alias = "duplicate";

        when(urlRepository.findByShortUrl(alias)).thenReturn(Optional.of(new ShortUrl()));

        assertThrows(RuntimeException.class, () -> urlService.createShortUrl(originalUrl, alias));
        verify(urlRepository, never()).save(any());
    }

    @Test
    void testGetByShortCodeFound() {
        ShortUrl url = new ShortUrl();
        url.setOriginalUrl("https://www.bbc.com/news");
        url.setShortUrl("bbc-news");

        when(urlRepository.findByShortUrl("bbc-news")).thenReturn(Optional.of(url));

        ShortUrl result = urlService.getByShortUrl("bbc-news");

        assertEquals("https://www.bbc.com/news", result.getOriginalUrl());
    }

    @Test
    void testGetByShortCodeNotFound() {
        when(urlRepository.findByShortUrl("notfound")).thenReturn(Optional.empty());

        assertNull(urlService.getByShortUrl("notfound"));
        assertEquals(0, urlService.getAll().size());
    }

    @Test
    void testGetAll() {
        List<ShortUrl> urls = List.of(
                new ShortUrl() {{ setShortUrl("a1"); setOriginalUrl("https://a.com"); }},
                new ShortUrl() {{ setShortUrl("b2"); setOriginalUrl("https://b.com"); }}
        );
        when(urlRepository.findAll()).thenReturn(urls);

        List<ShortUrl> result = urlService.getAll();

        assertEquals(2, result.size());
        assertEquals("a1", result.get(0).getShortUrl());
    }
}

