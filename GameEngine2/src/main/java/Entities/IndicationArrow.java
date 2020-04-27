package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

public class IndicationArrow extends Entity {
    Ball ball;

    public IndicationArrow(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Ball ball) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.ball = ball;
    }

    public void updateLocation(){
        this.setPosition(ball.getPosition());
    }

    public void updateRotation(float rotX, float rotY, float rotZ){
        setRotX(rotX);
        setRotY(rotY);
        setRotZ(rotZ);
    }

    public void updateLocationAndRotation(float rotX, float rotY, float rotZ){
        updateLocation();
        updateRotation(rotX, rotY, rotZ);
    }

    public void show(){
        updateLocation();
    }

    public void hide(){
        this.setPosition(new Vector3f(0, -10000, 0));
    }

}
