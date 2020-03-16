package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

public class Ball {
    Vector3 location;
    float speed;

    public Vector3 getLocation() {
        return location;
    }

    public float getSpeed() {
        return speed;
    }

    public void setLocation(Vector3 location) {
        this.location = location;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void updateBallModel(){
        Main.renderBall(location.x, location.y, location.z);
    }
}
