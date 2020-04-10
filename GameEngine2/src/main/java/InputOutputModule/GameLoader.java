package InputOutputModule;

import EngineTester.MainGameLoop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameLoader {
    //TODO load the game info when this function is called (goal location, ball location, terrain, etc)
    public static void loadGameFile(String fullPath){
        StringBuilder terrainInfo = new StringBuilder();

        try {
            File myObj = new File("C:/Users/steem/Desktop/TMP/ProjectDebug/terrain.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                //Use the read in info
                terrainInfo.append(line);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong with reading the file");
            e.printStackTrace();
        }

        MainGameLoop.terrain.loadFromString(terrainInfo.toString());


    }
}
