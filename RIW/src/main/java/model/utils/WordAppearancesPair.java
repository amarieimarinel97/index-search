package model.utils;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
public  class WordAppearancesPair{
    public String word;
    public double counter;


    public static List<WordAppearancesPair> fromMapToList(Map<String, Integer> input){
        List<WordAppearancesPair> result = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : input.entrySet())
            result.add(WordAppearancesPair.builder()
                    .word(entry.getKey())
                    .counter(entry.getValue())
                    .build());
        return result;
    }

    public static  Map<String, Integer> fromListToMap(List<WordAppearancesPair> input){
        Map<String, Integer> result = new TreeMap<>();
        for(WordAppearancesPair d : input)
            result.put(d.word, (int) d.counter);
        return result;
    }
}
