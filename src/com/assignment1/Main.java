package com.assignment1;


import com.assignment1.algorithms.SearchResult;
import com.assignment1.algorithms.SearchType;
import com.assignment1.exception.UnkownSearchAlgorithmExeption;

import javax.sound.midi.SysexMessage;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        if(args.length >= 1) {
            // Handle file
            Map map = FileHandler.readFile(args[0]);

            /** Handle command line arguments **/

            //No command line parameters
            if (args.length == 1) {
                // Launch UI
                map.launch();
            } else {
                SearchType type = SearchType.valueOf(args[1].toUpperCase());
                if(type == null) {
                    throw new UnkownSearchAlgorithmExeption("Unknown Search Algorithm: "+ args[1]);
                }
                SearchResult result = map.launch(type);
                System.out.println(args[0] +" | " + args[1] + " | " + (result.isSolutionFound() ? result.getVistedNodes() : "No solution found"));
                if(result.isSolutionFound()){
                    System.out.println(result.getPath());
                }
            }
        } else {
            System.out.println("Please enter the name of the map file. e.g. map.txt");
        }

    }
}
