package com.tuiasi.index;

import com.tuiasi.model.DirectIndexEntry;
import com.tuiasi.model.InverseIndexEntry;
import com.tuiasi.model.utils.DocumentAppearancesPair;
import com.tuiasi.model.utils.WordAppearancesPair;
import com.tuiasi.repository.DirectIndexRepository;
import com.tuiasi.repository.InverseIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TFIDFSearch {

    @Autowired
    private DirectIndexRepository directIndexRepository;

    @Autowired
    private InverseIndexRepository inverseIndexRepository;

    @Autowired
    private IndexUtils indexUtils;

    private List<WordAppearancesPair> getDocumentWords(String document) {
        String docName = new File(document).getName();
        List<DirectIndexEntry> entries = directIndexRepository.getAll();
        for (DirectIndexEntry entry : entries)
            if (entry.getDocument().endsWith(docName))
                return entry.getWords();
        System.out.println("No document found with name " + docName);
        return new ArrayList<>();
    }

    private double getTermFrequency(String word, String document) {
        List<WordAppearancesPair> wordsFromDocument = this.getDocumentWords(document);
        for (WordAppearancesPair wordAppearancesPair : wordsFromDocument) {
            if (wordAppearancesPair.word.equals(word) || wordAppearancesPair.word.equals(indexUtils.getBaseForm(word)))
                return wordAppearancesPair.counter / wordsFromDocument.size();
        }
        return 0.0;
    }

    private double getInverseDocumentFrequency(String word, String document) {
        InverseIndexEntry wordAppearances = inverseIndexRepository.get(indexUtils.getBaseForm(word));
        int noOfDocuments = directIndexRepository.getAll().size();
        return Math.log((double) noOfDocuments / wordAppearances.documents.size());
    }

    public double getTFIDF(String word, String document) {
        return getTermFrequency(word, document) * getInverseDocumentFrequency(word, document);
    }

    public void sortByRelevance(String query, List<String> documents) {
        List<String> words = new ArrayList<>();
        char[] chars = query.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char c : chars)
            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                sb.append(c);
            } else {
                if (sb.length() > 0) {
                    words.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        words.add(sb.toString());

        Map<String, Double> documentsWithRelevanceCoeff = new TreeMap<>();
        for (String document : documents) {
            double sumOfRelevanceCoeffs = 0.0;
            for (String word : words)
                sumOfRelevanceCoeffs += this.getTFIDF(word, document);
            documentsWithRelevanceCoeff.put(document, sumOfRelevanceCoeffs);
        }

        documents.sort((doc1, doc2) -> {
            if (documentsWithRelevanceCoeff.get(doc1) > documentsWithRelevanceCoeff.get(doc2))
                return -1;
            else
                return 1;
        });
    }

}
