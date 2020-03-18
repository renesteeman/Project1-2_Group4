package com.mygdx.game;

public interface Drawable {
    Vector2d location = null;
    
    void draw();
    void updateLocation(Vector2d location);
}
