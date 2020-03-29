package model;

import lombok.Builder;
import lombok.Data;
import model.utils.DocumentAppearancesPair;

import java.util.List;

@Data
@Builder
public class InverseIndexEntry {
    public String _id;
    public String word;
    public List<DocumentAppearancesPair> documents;
}
