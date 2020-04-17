package com.tuiasi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuiasi.model.DirectIndexEntry;
import com.tuiasi.repository.DirectIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectIndexService {
    @Autowired
    private DirectIndexRepository directIndexRepository;

    private ObjectMapper mapper = new ObjectMapper();

    public String getAll() {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(directIndexRepository.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error: Couldn't map object to json.";
        }
    }

    public void delete(String word) {
        directIndexRepository.delete(word);
    }

    public void add(DirectIndexEntry ii) {
        directIndexRepository.add(ii);
    }

    public String get(String word) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(directIndexRepository.get(word));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error: Couldn't map object to json.";
        }
    }

    public void deleteAll() {
        directIndexRepository.deleteAll();
    }
}
