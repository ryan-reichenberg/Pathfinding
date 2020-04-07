package com.assignment1;

import com.assignment1.datastructures.Tree;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileHandler {

    public static Map readFile(String fileName) {
        try {

            // TODO: Needs major clean up
            BufferedReader reader = new BufferedReader(new FileReader("resources/"+fileName));
            MapBuilder builder = new MapBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[")) {
                    // remove brackets
                    line = line.substring(1, line.length() - 1);
                    String[] split = line.split(",");
                    builder.setHeight(Integer.parseInt(split[0]));
                    builder.setWidth(Integer.parseInt(split[1]));
                }
                builder.addStartNode(new Node());
                if(line.contains("|")){

                }
            }
            reader.close();
            return builder.build();
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", fileName);
            e.printStackTrace();
            return null;
        }
    }


    private static void buildMap(){}

    private static void populateTree(){}
}
