package Entities;

import Models.CollisionModel;
import Models.TexturedModel;
import org.joml.Vector3f;

public class CollisionEntity extends Entity{
    private double collisionRadius;
    private CollisionModel collisionModel;

    public CollisionEntity(CollisionModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, double collisionRadius) {
        super(model.getTexturedModel(), position, rotX, rotY, rotZ, scale);
        this.collisionModel = model;
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

    public CollisionModel getCollisionModel() {
        return collisionModel;
    }
}
