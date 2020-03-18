package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

public interface Drawable {
    Vector3d location = null;
    
    void draw();
    void setLocation(Vector3 location);
    void remove();
}
