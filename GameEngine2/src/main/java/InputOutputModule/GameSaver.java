package InputOutputModule;

import EngineTester.MainGameLoop;

import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
    //TODO save the game info when this function is called (goal location, ball location, terrain, etc)
    public static void saveGameFile(String fullPath){
        String terrainInfo = MainGameLoop.terrain.getTerrainInfoAsString();
        String treeInfo = MainGameLoop.trees.getTreeInfoAsString();

        try {
            String separationSign = "|";
            FileWriter writer = new FileWriter("C:/Users/steem/Desktop/TMP/ProjectDebug/terrain.txt");

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
