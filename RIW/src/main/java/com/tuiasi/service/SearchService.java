package com.tuiasi.service;

import com.tuiasi.index.BooleanSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private BooleanSearch booleanSearch;

    public List<String> search(String query) {
        return new ArrayList<String>(booleanSearch.evaluateQuery(query));
    }
}
