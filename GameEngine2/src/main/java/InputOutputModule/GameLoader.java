package InputOutputModule;

import EngineTester.MainGameLoop;

public class GameLoader {
    //TODO load the game info when this function is called (goal location, ball location, terrain, etc)
    public static void loadGameFile(String fullPath){


        String terrainInfo = "";
        MainGameLoop.terrain.loadFromString(terrainInfo);
    }
}
