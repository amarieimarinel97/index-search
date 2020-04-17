package com.tuiasi.service;

import com.tuiasi.index.BooleanSearch;
import com.tuiasi.index.TFIDFSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private BooleanSearch booleanSearch;

    @Autowired
    private TFIDFSearch tfidfSearch;

    public List<String> booleanSearch(String query, boolean sortByRelevance) {
        List<String> result = new ArrayList<String>(booleanSearch.evaluateQuery(query));
        if(sortByRelevance)
            tfidfSearch.sortByRelevance(query, result);
        return result;
    }

    public double tfidfSearch(String word, String document){
        return tfidfSearch.getTFIDF(word,document);
    }


}
