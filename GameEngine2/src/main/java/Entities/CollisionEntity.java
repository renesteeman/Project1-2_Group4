package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

public class CollisionEntity extends Entity{
    private double collisionRadius;

    public CollisionEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, double collisionRadius) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.collisionRadius = collisionRadius;
    }

    //Option to create blank entity (for testing)
    public CollisionEntity() { }

    public double getCollisionRadius() {
        return collisionRadius;
    }

    public void setCollisionRadius(double collisionRadius) {
        this.collisionRadius = collisionRadius;
    }
}
