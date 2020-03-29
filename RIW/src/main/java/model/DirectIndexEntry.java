package model;

import lombok.Builder;
import lombok.Data;
import model.utils.WordAppearancesPair;

import java.util.List;

@Builder
@Data
public class DirectIndexEntry {
    String _id;
    String document;
    List<WordAppearancesPair> words;
}
