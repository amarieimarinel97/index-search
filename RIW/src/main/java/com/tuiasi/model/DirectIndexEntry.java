package com.tuiasi.model;

import lombok.Builder;
import lombok.Data;
import com.tuiasi.model.utils.WordAppearancesPair;

import java.util.List;

@Builder
@Data
public class DirectIndexEntry {
    String _id;
    String document;
    List<WordAppearancesPair> words;
}
