package com.urlshortner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShorterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateShortUrl() throws Exception {
        mockMvc.perform(post("/api/urls")
                        .param("url", "https://www.bbc.com/news"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalUrl").value("https://www.bbc.com/news"));

    }


    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/api/urls"))
                .andExpect(status().isOk());
    }
    @Test
    void testNotFound() throws Exception {
        mockMvc.perform(get("/api/urls/google"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInvalidUrl() throws Exception {
        mockMvc.perform(post("/api/urls")
                        .param("url", "invalidurl"))
                .andExpect(status().isBadRequest());
    }



}



