package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Random Jokes from a Joke API")
@Slf4j
@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    JokeQueryService jokeQueryService;

    @Operation(summary = "Get random jokes", description = "Fetches a specified number of random jokes from an external joke API. Categories can include 'knock-knock', 'dad', 'general', etc.")
    @GetMapping("/get")
    public ResponseEntity<String> getRandomJokes(
        @Parameter(name = "category", description = "Category of the joke", example = "dad")
        @RequestParam(required = false) String category,
        @Parameter(name = "numJokes", description = "Number of jokes to fetch", example = "3")
        @RequestParam(required = false, defaultValue = "1") int numJokes
    ) throws JsonProcessingException {
        log.info("getRandomJokes: category={}, numJokes={}", category, numJokes);
        String result = jokeQueryService.getJSON(category, numJokes);
        return ResponseEntity.ok().body(result);
    }
}



