package com.mygdx.game;

public class Vector3d {
    public double x, y, z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(double x, double z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }
}
