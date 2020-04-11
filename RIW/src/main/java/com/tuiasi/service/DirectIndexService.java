package com.tuiasi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuiasi.index.BooleanSearch;
import com.tuiasi.index.IndexUtils;
import com.tuiasi.model.DirectIndexEntry;
import com.tuiasi.model.InverseIndexEntry;
import com.tuiasi.model.utils.DocumentAppearancesPair;
import com.tuiasi.model.utils.WordAppearancesPair;
import com.tuiasi.repository.DirectIndexRepository;
import com.tuiasi.repository.InverseIndexRepository;
import com.tuiasi.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
