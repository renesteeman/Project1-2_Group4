package InputOutputModule;

import MainGame.MainGame;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Load map in-game
public class GameLoader {
    static ArrayList<Vector3f> treeLocations;

    public static void loadGameFile(String fullPath, MainGame game){

        /*
        String gravitationalConstant = "";
        String massOfBall = "";
        String frictionCoefficient = "";
        String vMax = "";
        String goalRadius = "";
        String startCoordinates2D = "";
        String goalCoordinates2D = "";
        String heightFunction = "";
        */

        try {
            File myObj = new File(fullPath);
            Scanner myReader = new Scanner(myObj);
            String remaining = "";

            int i=0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                switch(i){
                    case 0:
//                        gravitationalConstant = extractValue(line);
                        break;
                    case 1:
//                        massOfBall = extractValue(line);
                        break;
                    case 2:
//                        frictionCoefficient = extractValue(line);
                        break;
                    case 4:
//                        vMax = extractValue(line);
                        break;
                    case 5:
//                        goalRadius = extractValue(line);
                        break;
                    case 7:
//                        startCoordinates2D = extractValue(line);
                        break;
                    case 8:
//                        goalCoordinates2D = extractValue(line);
                        break;
                    case 10:
//                        heightFunction = extractValue(line);
                        break;
                    default:
                        if(i!=3 && i!=6 && i!=9){
                            remaining += line;
                        }
                }

                i++;
            }
            myReader.close();

            //Process additional details that are optional
            if(remaining.length()>0){
                processRemaining(remaining, game);
            }

//            MainGameLoop.terrain.loadFromString(terrainInfo);
//            MainGameLoop.trees.loadFromString(treeInfo);
//            MainGameLoop.ball.loadFromString(ballInfo);
//            MainGameLoop.goal.loadFromString(goalInfo);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();

        } catch (Exception e){
            System.out.println("Something went wrong with loading the file");
            e.printStackTrace();
        }
    }

    private static void processRemaining(String remaining, MainGame game){
        String[] parts = remaining.split(";");
        String treesInfo = parts[0].split("=")[1];
        String terrainInfo = parts[1];

        processTrees(treesInfo, game);
        game.getTerrain().loadFromString(terrainInfo);
    }

    private static void processTrees(String treesInfo, MainGame game){
        treeLocations = new ArrayList<Vector3f>();

        String[] trees = treesInfo.split("\\)");

        for(String tree : trees){
            tree = tree.replace(", (", "");
            tree = tree.replace("(", "");
            String[] treeInfo = tree.split(",");

            treeLocations.add(new Vector3f(Float.parseFloat(treeInfo[0]), Float.parseFloat(treeInfo[1]), Float.parseFloat(treeInfo[2])));
        }

        game.getTrees().loadFromString(treeLocations, game.getLoader());
    }

    private static String extractValue(String inputString){
        String result = inputString.substring(inputString.indexOf("="));
        result = result.substring(0, result.indexOf(";"));
        result = result.substring(1);
        result = result.trim();
        return result;
    }
}


