package com.tuiasi.controller;

import com.tuiasi.model.DirectIndexEntry;
import com.tuiasi.service.DirectIndexService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    @ResponseBody
    public String get(@RequestParam String doc) {
        return service.get(doc);
    }

    @GetMapping("/all")
    @ResponseBody
    public String getAll() {
        return service.getAll();
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        service.deleteAll();
    }
}
