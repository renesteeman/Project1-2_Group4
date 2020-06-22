package Entities;

import Models.CollisionModel;
import org.joml.Vector3f;

//A ball that can be used for previews off ball movement
public class IndicationBall extends Ball{
    public IndicationBall(CollisionModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void hide(){
        this.setPosition(0, -10000, 0);
    }
}
