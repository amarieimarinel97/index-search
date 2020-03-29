package model.utils;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Builder
@Data
public class DocumentAppearancesPair {
    public String document;
    public double counter;

    public static List<DocumentAppearancesPair> fromMapToList(Map<String, Integer> input){
        List<DocumentAppearancesPair> result = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : input.entrySet())
            result.add(DocumentAppearancesPair.builder()
            .document(entry.getKey())
            .counter(entry.getValue())
            .build());
        return result;
    }

    public static  Map<String, Integer> fromListToMap(List<DocumentAppearancesPair> input){
        Map<String, Integer> result = new TreeMap<>();
        for(DocumentAppearancesPair d : input)
            result.put(d.document, (int) d.counter);
        return result;
    }


}
