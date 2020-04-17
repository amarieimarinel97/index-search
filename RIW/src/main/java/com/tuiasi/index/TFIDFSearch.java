package com.tuiasi.index;

import com.tuiasi.model.InverseIndexEntry;
import com.tuiasi.repository.DirectIndexRepository;
import com.tuiasi.repository.InverseIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TFIDFSearch {

    @Autowired
    private DirectIndexRepository directIndexRepository;

    @Autowired
    private InverseIndexRepository inverseIndexRepository;

    private double getTermFrequency(String word){
        return 0.0;
    }

    private double getInverseDocumentFrequency(String word, String document){
        return 0.0;
    }
}
