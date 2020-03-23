package com.mygdx.game;

public interface GameObject {
    Vector2d location = null;
    void render();
    void updateLocation(Vector2d location);
}
