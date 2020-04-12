package com.tuiasi.controller;

import com.tuiasi.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public List<String> search(@RequestParam String query) {
        return searchService.search(query);
    }
}
