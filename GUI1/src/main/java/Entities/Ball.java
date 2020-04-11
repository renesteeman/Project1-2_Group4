package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

public class Ball extends Entity {

    public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void setPosition(int x, int y, int z){
        this.setPosition(new Vector3f(x, y, z));
    }
}
