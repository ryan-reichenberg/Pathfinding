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
                long start = System.nanoTime();
                SearchResult result = map.launch(type);
                long end = System.nanoTime();
                System.out.println(args[0] +" | " + args[1] + " | " + (result.isSolutionFound() ? result.getVistedNodes() +" | " : "No solution found "
                        + (type == SearchType.IDDFS ? " at max depth of: "+ result.getDepth() : "") +" | ")
                        + (args.length == 3 && args[2].equals("-verbose")? result.getIterations() +" | " +result.getFrontierSize() + " | " : ""));
                if(result.isSolutionFound()){
                    System.out.println(type == SearchType.IDDFS ? "At depth: "+ result.getDepth() : "");
                    System.out.println(result.getPath());
                }
                System.out.println("Finished in: "+ (end - start) / 1000000+" ms");

            }
        } else {
            System.out.println("Please enter the name of the map file. e.g. map.txt");
        }

    }
}
