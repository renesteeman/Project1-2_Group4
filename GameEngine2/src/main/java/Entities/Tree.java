package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

public class Tree extends CollisionEntity {

    public Tree(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        //CollisionRadius is pre-calculated
        super(model, position, rotX, rotY, rotZ, scale, 0);
    }

    public void setPosition(int x, int y, int z){
        this.setPosition(new Vector3f(x, y, z));
    }
}
