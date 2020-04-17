package com.tuiasi.controller;

import com.tuiasi.index.IndexUtils;
import com.tuiasi.model.DirectIndexEntry;
import com.tuiasi.service.DirectIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/di")
public class DirectIndexController {

    @Autowired
    private DirectIndexService service;

    @PostMapping
    public void add(@RequestBody DirectIndexEntry di) {
        service.add(di);
    }

    @DeleteMapping
    public void delete(@RequestParam String doc) {
        service.delete(doc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String get(@RequestParam String doc) {
        return service.get(doc);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAll() {
        return service.getAll();
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        service.deleteAll();
    }



}
