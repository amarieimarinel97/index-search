package index;

import utils.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class IndexUtils {

    public static Map<String, Integer> countAppearances(String text, List<String> exceptions, List<String> stopWords) {
        Map<String, Integer> result = new TreeMap<>();
        StringBuilder currentWord = new StringBuilder();


        char[] chars = text.toCharArray();
        for (char c : chars) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                currentWord.append(c);
            } else {
                String word = currentWord.toString().toLowerCase();
                currentWord = new StringBuilder();
                if (!word.equals(""))
                    if (exceptions.contains(word))
                        if (!result.containsKey(word))
                            result.put(word, 1);
                        else
                            result.replace(getBaseForm(word), result.get(word) + 1);
                    else if (!stopWords.contains(word)) {
                        if (!result.containsKey(word))
                            result.put(getBaseForm(word), 1);
                        else
                            result.replace(word, result.get(word) + 1);
                    }
            }
        }
        return result;
    }


    public static Map<String, Integer> countAppearancesCharByChar(String path, List<String> exceptions, List<String> stopWords) throws IOException {
        Map<String, Integer> result = new TreeMap<>();
        StringBuilder currentWord = new StringBuilder();

        Charset encoding = Charset.defaultCharset();
        try (InputStream in = new FileInputStream(path);
             Reader reader = new InputStreamReader(in, encoding);

             Reader buffer = new BufferedReader(reader)) {
            int r;
            while ((r = reader.read()) != -1) {
                char c = (char) r;
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    currentWord.append(c);
                } else {
                    String word = currentWord.toString().toLowerCase();
                    currentWord = new StringBuilder();
                    if (!word.equals(""))
                        if (exceptions.contains(word))
                            if (!result.containsKey(word))
                                result.put(word, 1);
                            else
                                result.replace(getBaseForm(word), result.get(word) + 1);
                        else if (!stopWords.contains(word)) {
                            if (!result.containsKey(word))
                                result.put(getBaseForm(word), 1);
                            else
                                result.replace(word, result.get(word) + 1);
                        }
                }
            }
        }

        return result;
    }

    public static Map<String, Integer> getDictionaryWordsCharByChar(List<File> files, List<String> exceptions, List<String> stopWords) throws IOException {
        Map<String, Integer> dictionary = new TreeMap<>();
        for (File file : files)
            dictionary.putAll(countAppearancesCharByChar(file.getPath(), exceptions, stopWords));
        return dictionary;
    }

    public static List<File> getTextFilesFromDirectory(String path) {
        List<File> directories = new ArrayList<>();
        List<File> files = new ArrayList<>();
        directories.add(new File(path));

        while (!directories.isEmpty()) {
            File[] filesInDirectory = directories.remove(0).listFiles();
            if (filesInDirectory != null)
                for (File file : filesInDirectory)
                    if (file.isDirectory())
                        directories.add(file);
                    else if (file.isFile())
                        files.add(file);
        }
        return files;
    }

    public static Map<String, Integer> getDictionaryWords(List<File> files, List<String> exceptions, List<String> stopWords) {
        Map<String, Integer> dictionary = new TreeMap<>();
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb.append(file.getPath()).append("\n{\n");
            Map<String, Integer> intermediateResult = countAppearances(FileUtils.readFromFile(file.getPath()), exceptions, stopWords);
            intermediateResult.forEach((k, v) ->
                    sb.append(k).append(":")
                            .append(v).append("\n")
            );
            sb.append("}\n");
            dictionary.putAll(intermediateResult);
        }
        FileUtils.writeToFile(FileUtils.DIRECT_INDEX_PATH, sb.toString(), true);
        return dictionary;
    }

    private static String getBaseForm(String in) {
        return in;
    }

    public static Map<String, Map<String, Integer>> getDirectIndex(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        Map<String, Map<String, Integer>> index = new TreeMap<>();

        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentFilePath = "";
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if(data.startsWith("}")||data.startsWith("{"))
                if(scanner.hasNextLine())
                    data=scanner.nextLine();
                else
                    break;

            if (data.startsWith("E:\\Facultate\\Materiale an 4\\Rezolvari Marinel\\Sem2\\RIW")) {
                currentFilePath = data;
            } else {
                String[] wordsWithAppearances = splitBySeparator(data, ':');
                stringBuilder = new StringBuilder();
                stringBuilder.append(wordsWithAppearances[0])
                        .append(":")
                        .append(currentFilePath)
                        .append(",")
                        .append(wordsWithAppearances[1])
                        .append("\n");

                if (index.containsKey(wordsWithAppearances[0])) {
                    index.get(wordsWithAppearances[0]).put(currentFilePath, Integer.valueOf(wordsWithAppearances[1]));
                } else {
                    Map<String, Integer> currentWord = new TreeMap<>();
                    currentWord.put(currentFilePath, Integer.valueOf(wordsWithAppearances[1]));
                    index.put(wordsWithAppearances[0], currentWord);
                }
            }
        }
        scanner.close();
        return index;
    }

    public static void writeIntermediateFilesDirectIndex(String path){
        StringBuilder stringBuilder = new StringBuilder();

        File fileInput = new File(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(fileInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentFilePath = null;
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if(data.startsWith("}")||data.startsWith("{"))
                if(scanner.hasNextLine())
                    data=scanner.nextLine();
                else
                    break;
            if (data.startsWith("E:\\Facultate\\Materiale an 4\\Rezolvari Marinel\\Sem2\\RIW")) {
                if(currentFilePath != null){
                    FileUtils.writeToFile(currentFilePath, stringBuilder.toString(), true);
                    stringBuilder= new StringBuilder();
                }
                currentFilePath = FileUtils.DIRECT_INTERMEDIATE_PATH + new File(data).getName();
            } else {
                String[] wordsWithAppearances = splitBySeparator(data,':');
                stringBuilder.append(wordsWithAppearances[0])
                        .append(":")
                        .append(wordsWithAppearances[1])
                        .append("\n");
                }
            }
        scanner.close();
    }

    public static void writeInverseIndex(Map<String, Map<String, Integer>> index){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key :index.keySet()){
            stringBuilder.append(key).append("{");
            for(String directory : index.get(key).keySet()) {
                stringBuilder.append("\n\t")
                        .append(directory)
                        .append("-")
                        .append(index.get(key).get(directory));
            }
            stringBuilder.append("\n}\n");
        }
        FileUtils.writeToFile(FileUtils.INVERSE_INDEX_PATH, stringBuilder.toString(), false);
    }

    public static Map<String, Map<String, Integer>> readInverseIndex(String path){
        Map<String, Map<String, Integer>> inverseIndex = new TreeMap<>();
        StringBuilder stringBuilder = new StringBuilder();

        File fileInput = new File(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(fileInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentWord = null;
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if(data.startsWith("}")||data.startsWith("{"))
                if(scanner.hasNextLine())
                    data=scanner.nextLine();
                else
                    break;
            if (data.startsWith("\tE:\\Facultate\\Materiale an 4\\Rezolvari Marinel\\Sem2\\RIW")) {
                if(currentWord != null && inverseIndex.containsKey(currentWord)){
                    String[] documentWithAppearances = splitBySeparator(data,'-');
                    inverseIndex.get(currentWord).put(documentWithAppearances[0], Integer.parseInt(documentWithAppearances[1]));
                }
            } else {
                currentWord = data.substring(0,data.length()-1);
                inverseIndex.put(currentWord, new TreeMap<>());
            }
        }
        scanner.close();

        return inverseIndex;
    }

    public static void writeIntermediateFilesInverseIndex(Map<String, Map<String, Integer>> index){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key :index.keySet()){
                stringBuilder.append(key);
               for(String directory : index.get(key).keySet()) {
                   stringBuilder.append("\n")
                           .append(directory)
                           .append("-")
                           .append(index.get(key).get(directory));
               }
                stringBuilder.append("\n");
                FileUtils.writeToFile(FileUtils.INVERSE_INTERMEDIATE_PATH + key.charAt(0) + ".txt",
                        stringBuilder.toString(), true);
                stringBuilder = new StringBuilder();
        }
    }

    public static String[] splitBySeparator(String text, Character separator) {
        String[] result = new String[2];
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == separator) {
                result[0] = sb.toString();
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        result[1] = sb.toString();
        return result;
    }


}
