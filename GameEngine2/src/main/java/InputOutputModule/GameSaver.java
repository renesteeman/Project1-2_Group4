package InputOutputModule;

import EngineTester.MainGameLoop;

import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
    //TODO save the game info when this function is called (goal location, ball location, terrain, etc)
    public static void saveGameFile(String fullPath){
        String terrainInfo = MainGameLoop.terrain.getTerrainInfoAsString();

        try {
            FileWriter myWriter = new FileWriter("C:/Users/steem/Desktop/TMP/ProjectDebug/terrain.txt");
            myWriter.write(terrainInfo);
            myWriter.close();
            System.out.println("Saved");
        } catch (IOException e) {
            System.out.println("Something went wrong with saving the file");
            e.printStackTrace();
        }
    }
}
