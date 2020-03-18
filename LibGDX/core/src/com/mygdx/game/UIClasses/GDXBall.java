package com.mygdx.game.UIClasses;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Ball;
import com.mygdx.game.Function2d;
import com.mygdx.game.Main;
import com.mygdx.game.Vector2d;

public class GDXBall extends Ball {
    private Function2d height;
    private Vector3 location3, velocity3;

    public GDXBall() {}

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

    public void setVelocity3(Vector3 velocity3) {
        this.velocity3 = velocity3;
    }

    public Vector3 getLocation3() {
        return location3;
    }

    public Vector3 getVelocity3() {
        return velocity3;
    }

    @Override
    public void draw(){
        Main.renderBall(location.x, location.y, location.z);
    }
}
