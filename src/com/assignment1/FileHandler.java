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

                if(line.startsWith("(")) {
                    if(line.contains("|")){
                        // Wow.
                        //TODO: For loop this out.
                        line = line.replaceAll(" ", "");
                        System.out.println(line);
                        String[] split = line.split("\\|");
                        System.out.println(split[2]);
                        split[0] = split[0].substring(1, split[0].length() - 1);
                        split[1] = split[1].substring(1, split[1].length() - 1);

                        String[] split1 = split[0].split(",");
                        String[] split2 = split[1].split(",");
                        continue;
                    }
                    line = line.substring(1, line.length() - 1);
                    String[] split  = line.split(",");
                    if(split.length == 2){
                        // Start node
                        System.out.println(split[0]);
                    }else if (split.length == 4){
                        //wall
                        System.out.println(split[0]);
                    } else {
                        // Invalid Node configuration

                    }
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
