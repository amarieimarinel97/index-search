package com.tuiasi.controller;

import com.tuiasi.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public List<String> booleanSearch(@RequestParam String query, @RequestParam(required = false) Optional<Boolean> relevance) {
        return searchService.booleanSearch(query, relevance.orElse(true));
    }

    @GetMapping("/tfidf")
    @ResponseBody
    public Double tfidfSearch(@RequestParam String word, @RequestParam String doc){
        return searchService.tfidfSearch(word,doc);
    }
}
