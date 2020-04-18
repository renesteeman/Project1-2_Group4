package InputOutputModule;

import EngineTester.MainGameLoop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameLoader {
    //TODO load the game info when this function is called (goal location, ball location, terrain, etc)
    public static void loadGameFile(String fullPath){
        StringBuilder fileContent = new StringBuilder();

        try {
            /*
            TODO make the file path relative, load the goal location, starting location, 'score radius', terrain width, terrain height (check project manual) and
            set these values for the objects themselves (ie set location of items)
            */
            File myObj = new File("C:/Users/steem/Desktop/TMP/ProjectDebug/terrain.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                fileContent.append(line);
            }
            myReader.close();

            //Use the read in info
            String[] infoCategories = fileContent.toString().split("\\|");
            String terrainInfo = infoCategories[0];
            String treeInfo = infoCategories[1];

            MainGameLoop.terrain.loadFromString(terrainInfo);
            MainGameLoop.trees.loadFromString(treeInfo);

        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong with reading the file");
            e.printStackTrace();
        }

    }
}
