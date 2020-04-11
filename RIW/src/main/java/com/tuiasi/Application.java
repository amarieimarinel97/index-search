package com.tuiasi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
//        System.out.println(printHeader("LAB01"));
//        HTMLUtils.displayHTMLInfo();

//        List<String> exceptions = FileUtils.splitByNewLine(FileUtils.readFromFile("exceptions.txt"));
//        List<String> stopWords = FileUtils.splitByNewLine(FileUtils.readFromFile("stopwords.txt"));
//
//        FileUtils.clearDirectory(FileUtils.INVERSE_INTERMEDIATE_PATH);
//        FileUtils.clearDirectory(FileUtils.DIRECT_INTERMEDIATE_PATH);
//        FileUtils.writeToFile(FileUtils.DIRECT_INDEX_PATH, "", false);
//
//
//        long start = System.currentTimeMillis();
//        Map<String, Integer> dictionary = IndexUtils.getDictionaryWords(
//                IndexUtils.getTextFilesFromDirectory(FileUtils.WORKING_DIRECTORY_PATH),
//                exceptions, stopWords);
//        float elapsedTimeSec = (System.currentTimeMillis() - start) / 1000F;
//        System.out.println(printHeader("Getting dictionary - Time elapsed: " + elapsedTimeSec + "s"));
//
//
//        System.out.println(printHeader("LAB02"));
//        System.out.println(printHeader("Read files: "));
//        IndexUtils.getTextFilesFromDirectory(FileUtils.WORKING_DIRECTORY_PATH).forEach(f -> System.out.println(f.toString()));
//
//        System.out.println(printHeader("Dictionary length"));
//        System.out.println(dictionary.keySet().size());
//
//        System.out.println(printHeader("LAB03"));
//        Map<String, Map<String, Integer>> directIndex = getDirectIndex(FileUtils.DIRECT_INDEX_PATH);
//        writeIntermediateFilesDirectIndex(FileUtils.DIRECT_INDEX_PATH);
//        System.out.println(printHeader("Direct com.tuiasi.index intermediate files written in " + FileUtils.DIRECT_INTERMEDIATE_PATH));
//        writeIntermediateFilesInverseIndex(directIndex);
//        System.out.println(printHeader("Inverse com.tuiasi.index intermediate files written in " + FileUtils.INVERSE_INTERMEDIATE_PATH));
//        writeInverseIndex(directIndex);
//        System.out.println(printHeader("Inverse com.tuiasi.index written in "+FileUtils.INVERSE_INDEX_PATH));
//
//        System.out.println(printHeader("LAB04"));
//        Map<String, Map<String, Integer>> inverseIndex = readInverseIndex(FileUtils.INVERSE_INDEX_PATH);
//        System.out.println(printHeader("Read com.tuiasi.index length: "+inverseIndex.keySet().size()));
//        System.out.println(inverseIndex.keySet());
//        BooleanSearch.setInverseIndex(inverseIndex);
//        String query = "accident & accordance | accountable ! aberration";
//        System.out.println("Query: "+query);
//        BooleanSearch.evaluateQuery(query).forEach(System.out::println);
//
//        System.out.println(printHeader("MONGODB"));
//        DirectIndexRepository.deleteAll();
//        InverseIndexRepository.deleteAll();
//
//        int randomElement = new Random().nextInt()%(inverseIndex.keySet().size());
//        String randomWord = inverseIndex.keySet().toArray()[randomElement].toString();
//        InverseIndexRepository.add(InverseIndexEntry.builder()
//                .word(randomWord)
//                .documents(DocumentAppearancesPair.fromMapToList(directIndex.get(randomWord)))
//        .build());
//        System.out.println("Inserted word: "+randomWord);
//        System.out.println("Retrieving word: "+randomWord);
//        System.out.println(InverseIndexRepository.get(randomWord).toString());

     }


}
