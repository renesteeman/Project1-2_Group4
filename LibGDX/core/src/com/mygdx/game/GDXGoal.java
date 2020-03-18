package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

public class GDXGoal extends Goal {
    private Function2d height;
    private Vector3 location3;

    public GDXGoal() {}

    @Override 
    public void setlocation(Vector2d location) {
        super.location = location;
        location3.x = location.x;
        location3.y = location.y;
        location3.z = height.evaluate(new Vector2d(location.x, location.y));
    
        //ANY SHIFTS?
    }

    public void setLocation3(Vector3 location3) {
        this.location3 = location3;
    }

    public Vector3 getLocation3() {
        return location3;
    }

    @Override
    public void draw(){
        Main.renderGoal(location.x, location.y, location.z);
    }
}
