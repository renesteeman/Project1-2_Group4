package Entities;

import Models.RawModel;
import Physics.Vector2d;
import Physics.Vector3d;
import java.awt.Graphics;
import Models.TexturedModel;
import org.joml.Vector3f;

public class Entity {
    protected TexturedModel model;
    protected Vector3f position;
    protected float rotX, rotY, rotZ;
    protected float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    //Option to create blank entity (for testing)
    public Entity() { }

    //Overridden in extensions
    public void render(Graphics g) { }

    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz){
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3d getPosition3() {
        return new Vector3d(position.x, position.y, position.z);
    }

    public Vector2d getPosition2() {
        return new Vector2d(position.x, position.z);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(Vector3d position) {
        this.position = Vector3d.convertF(position);
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
