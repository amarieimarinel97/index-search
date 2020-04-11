package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static final String INVERSE_INTERMEDIATE_PATH = "mapreduce/inverse_intermediate/";
    public static final String INVERSE_INDEX_PATH = "mapreduce/inverse_index.txt";
    public static final String DIRECT_INTERMEDIATE_PATH = "mapreduce/direct_intermediate/";
    public static final String DIRECT_INDEX_PATH = "mapreduce/direct_index.txt";
    public static final String WORKING_DIRECTORY_PATH = "E:\\Facultate\\Materiale an 4\\Rezolvari Marinel\\Sem2\\RIW\\WD";
    public static final String ABSOLUTE_PATH_PREFIX = "E:\\Facultate\\Materiale an 4\\Rezolvari Marinel\\Sem2\\RIW";

    public static String readFromFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                sb.append(data).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String printHeader(String message) {
        return "------------------------------\n[" + message.toUpperCase() + "]\n------------------------------\n";
    }

    public static List<String> splitByNewLine(String input) {
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();

        char[] chars = input.toCharArray();
        for (char c : chars) {
            if (c == '\n') {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        return result;
    }

    public static void writeToFile(String fileName, String text, boolean append) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, append));
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearDirectory(String path) {
        File directory = new File(path);
        for (File file : directory.listFiles())
            file.delete();
    }

}
