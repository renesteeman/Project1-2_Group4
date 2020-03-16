package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

public class Goal {
    Vector3 location;
    float radius;

    public Vector3 getLocation() {
        return location;
    }

    public float getRadius() {
        return radius;
    }

    public void setLocation(Vector3 location) {
        this.location = location;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void updateGoalModel(){
        Main.renderGoal(location.x, location.y, location.z);
    }
}
