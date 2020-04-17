package com.tuiasi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuiasi.model.InverseIndexEntry;
import com.tuiasi.service.InverseIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ii")
public class InverseIndexController {

    @Autowired
    private InverseIndexService service;

    @PostMapping
    public void add(@RequestBody InverseIndexEntry ii) {
        service.add(ii);
    }

    @DeleteMapping
    public void delete(@RequestParam String word) {
        service.delete(word);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String get(@RequestParam String word) {
        return service.get(word);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAll() {
        return service.getAll();
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        service.deleteAll();
    }


}
