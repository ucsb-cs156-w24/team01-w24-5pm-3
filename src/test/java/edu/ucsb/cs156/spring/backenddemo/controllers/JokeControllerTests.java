
package edu.ucsb.cs156.spring.backenddemo.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = JokeController.class)
public class JokeControllerTests {
  
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JokeQueryService mockJokeQueryService;

    @Test
    public void test_getRandomJoke() throws Exception {
    
        String fakeJsonResult = "{ \"joke\" : \"Why don't scientists trust atoms? Because they make up everything!\" }";
        String category = "program";
        int numJokes = 1; // Assuming you want 1 joke
        when(mockJokeQueryService.getJSON(eq(category), eq(numJokes))).thenReturn(fakeJsonResult);

        String url = String.format("/api/jokes/get?category=%s&numJokes=%d", category, numJokes);

        MvcResult response = mockMvc
            .perform(get(url).contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

        String responseString = response.getResponse().getContentAsString();

        assertEquals(fakeJsonResult, responseString);
    }

    @Test
    public void test_getRandomJoke_noCategory() throws Exception {
    
        String fakeJsonResult = "{ \"joke\" : \"Parallel lines have so much in common. It’s a shame they’ll never meet.\" }";
        String category = null; // No category specified
        int numJokes = 1; // Assuming you want 1 joke
        when(mockJokeQueryService.getJSON(eq(category), eq(numJokes))).thenReturn(fakeJsonResult);

        String url = String.format("/api/jokes/get?numJokes=%d", numJokes);

        MvcResult response = mockMvc
            .perform(get(url).contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

        String responseString = response.getResponse().getContentAsString();

        assertEquals(fakeJsonResult, responseString);
    }
}
