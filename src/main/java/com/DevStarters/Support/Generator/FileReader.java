package com.DevStarters.Support.Generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {
    public static String loadBook(String path) throws FileNotFoundException {
        StringBuilder line = new StringBuilder();
        File file = new File(path);
        try {
            BufferedReader inputData = new BufferedReader(new java.io.FileReader(file.getAbsoluteFile()));
            try {
                String temp;
                while ((temp = inputData.readLine()) != null) {
                    line.append(temp);
                    line.append("\n");
                }
            } finally {
                inputData.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.toString();
    }
}
