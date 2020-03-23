package com.mygdx.game;

public class Tree implements GameObject{
    Vector2d location;

    @Override
    public void updateLocation(Vector2d location) {
        this.location = location;
    }
}
