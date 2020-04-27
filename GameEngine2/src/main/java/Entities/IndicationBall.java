package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

public class IndicationBall extends Ball{
    public IndicationBall(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void hide(){
        this.setPosition(0, -10000, 0);
    }
}
