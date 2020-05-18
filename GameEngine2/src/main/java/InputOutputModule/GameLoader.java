package InputOutputModule;

import FeatureTester.FeatureTester;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// QUESTION is this file supposed to just extract the basic course data? Like, read the file that was sampled in the manual? If yes, than it is already
// implemented inside the puttingcourse and this file can be deleted.

// TODO rewrite for the MainGame 
// The easiest way to do so is not drag the Ball from PuttingCourse cause it'll likely break a lot of stuff but to provide MainGame class
// (or whatever GameLoader is logically supposed to interact with) with methods that are gonna send data to the puttingcourse's object.

public class GameLoader {
    //TODO load the game info when this function is called (goal location, ball location, terrain, etc)
    public static void loadGameFile(String fullPath){

        String gravitationalConstant = "";
        String massOfBall = "";
        String frictionCoefficient = "";
        String vMax = "";
        String goalRadius = "";
        String startCoordinates2D = "";
        String goalCoordinates2D = "";
        String heightFunction = "";

        try {
            File myObj = new File("terrainSaveFile.txt");
            Scanner myReader = new Scanner(myObj);
            int i=0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                switch(i){
                    case 0:
                        gravitationalConstant = extractValue(line);
                        break;
                    case 1:
                        massOfBall = extractValue(line);
                        break;
                    case 2:
                        frictionCoefficient = extractValue(line);
                        break;
                    case 4:
                        vMax = extractValue(line);
                        break;
                    case 5:
                        goalRadius = extractValue(line);
                        break;
                    case 7:
                        startCoordinates2D = extractValue(line);
                        break;
                    case 8:
                        goalCoordinates2D = extractValue(line);
                        break;
                    case 10:
                        heightFunction = extractValue(line);
                        break;
                }

                i++;
            }
            myReader.close();

            //TODO link to physics and game objects
            System.out.println(gravitationalConstant);
            System.out.println(massOfBall);
            FeatureTester.ball.setMassOfBall(Float.parseFloat(massOfBall));
            System.out.println(frictionCoefficient);
            System.out.println(vMax);
            System.out.println(goalRadius);
            FeatureTester.goal.setRadius(Float.parseFloat(goalRadius));
            System.out.println(startCoordinates2D);
            System.out.println(goalCoordinates2D);
            System.out.println(heightFunction);

//            String ballInfo = infoCategories[0];
//            String goalInfo = infoCategories[1];
//            String terrainInfo = infoCategories[0];
//            String treeInfo = infoCategories[1];
//            //Same for the two others 1(goal location), 2(radius), 4(treeInfo), 3(terrainInfo)
//
//            MainGameLoop.terrain.loadFromString(terrainInfo);
//            MainGameLoop.trees.loadFromString(treeInfo);
//            MainGameLoop.ball.loadFromString(ballInfo);
//            MainGameLoop.goal.loadFromString(goalInfo);

        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong with reading the file");
            e.printStackTrace();

        } catch (Exception e){
            System.out.println("Something went wrong with loading the file");
            e.printStackTrace();
        }
    }

    private static String extractValue(String inputString){
        String result = inputString.substring(inputString.indexOf("="));
        result = result.substring(0, result.indexOf(";"));
        result = result.substring(1);
        result = result.trim();
        return result;
    }
}


