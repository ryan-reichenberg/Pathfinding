package com.assignment1;

import com.assignment1.datastructures.Node;
import com.assignment1.exception.InvalidMapConfigurationException;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileHandler {

    public static Map readFile(String fileName) {
        try {

            // TODO: Needs major clean up
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+fileName));
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
                        String[] split = line.split("\\|");
                        split[0] = split[0].substring(1, split[0].length() - 1);
                        split[1] = split[1].substring(1, split[1].length() - 1);

                        String[] split1 = split[0].split(",");
                        String[] split2 = split[1].split(",");
//                        System.out.println(split1[0]);
//                        System.out.println(split1[1]);
//                        System.out.println(split2[0]);
//                        System.out.println(split2[1]);
                        builder.addGoalNode(new Node<>(new Location(Integer.valueOf(split1[0]), Integer.valueOf(split1[1]))));
                        builder.addGoalNode(new Node<>(new Location(Integer.valueOf(split2[0]), Integer.valueOf(split2[1]))));
                        continue;
                    }
                    line = line.substring(1, line.length() - 1);
                    String[] split  = line.split(",");
                    if(split.length == 2){
                        // Start node
                        builder.addStartNode(new Node<>(new Location(Integer.valueOf(split[0]), Integer.valueOf(split[1]))));
                    }else if (split.length == 4){
                        //wall
                        builder.addWall(new Node<>(new Wall(Integer.valueOf(split[0]), Integer.valueOf(split[1]),
                                Integer.valueOf(split[2]), Integer.valueOf(split[3]))));
                    } else {
                        throw new InvalidMapConfigurationException("Unknown Node/Location configuration");

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
}
