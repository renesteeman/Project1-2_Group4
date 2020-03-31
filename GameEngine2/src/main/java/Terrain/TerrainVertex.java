package Terrain;

import Toolbox.Color;

public class TerrainVertex {

    //0 = grass, 1 = sand
    private int type;

    public TerrainVertex(int type) {
        this.type = type;
    }

    public void updateType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
