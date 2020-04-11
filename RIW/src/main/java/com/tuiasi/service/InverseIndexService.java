package com.tuiasi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuiasi.model.InverseIndexEntry;
import com.tuiasi.repository.InverseIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InverseIndexService {
    @Autowired
    private InverseIndexRepository inverseIndexRepository;

    private ObjectMapper mapper = new ObjectMapper();

    public String getAll() {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inverseIndexRepository.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error: Couldn't map object to json.";
        }
    }

    public void delete(String word) {
        inverseIndexRepository.delete(word);
    }

    public void add(InverseIndexEntry ii) {
        inverseIndexRepository.add(ii);
    }

    public String get(String word) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inverseIndexRepository.get(word));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error: Couldn't map object to json.";
        }
    }

    public void deleteAll() {
        inverseIndexRepository.deleteAll();
    }

}
