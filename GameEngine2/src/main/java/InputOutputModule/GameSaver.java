package InputOutputModule;

import EngineTester.MainGameLoop;

import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
    //TODO save the game info when this function is called (goal location, ball location, terrain, etc)
    /*
   TODO make the file path relative, load the goal location, starting location, 'score radius', terrain width, terrain height (check project manual) and
   set these values for the objects themselves (ie set location of items)
    */
    public static void saveGameFile(String fullPath){
        String terrainInfo = MainGameLoop.terrain.getTerrainInfoAsString();
        String treeInfo = MainGameLoop.trees.getTreeInfoAsString();
        String ballInfo = MainGameLoop.ball.getBallInfoAsString();
        String goalInfo = MainGameLoop.goal.getGoalInfoAsString();

        try {
            String separationSign = "|";
            FileWriter writer = new FileWriter("terrainSaveFile.txt");

            writer.write(ballInfo);
            writer.write(separationSign);
            writer.write(goalInfo);
            writer.write(separationSign);
            writer.write(terrainInfo);
            writer.write(separationSign);
            writer.write(treeInfo);

            writer.close();
            System.out.println("Saved");
        } catch (IOException e) {
            System.out.println("Something went wrong with saving the file");
            e.printStackTrace();
        }
    }
}

/**
 * To paste in the file:
 *
 * Starting point of the ball (location of the ball):	|
 * Location of the goal:	|
 * Radius of a 'goal': 	|
 *
 */
