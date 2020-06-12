package Entities;

import Collision.CollisionBox;
import Models.CollisionModel;
import org.joml.Vector3f;

public class CollisionEntity extends Entity{
    private CollisionModel collisionModel;
    private CollisionBox collisionBox;

    public CollisionEntity(CollisionModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model.getTexturedModel(), position, rotX, rotY, rotZ, scale);
        this.collisionModel = model;
        calculateCollisionBox();
    }

    //Option to create blank entity (for testing)
    public CollisionEntity() { }

    //Calculate the points that would make up the smallest possible box that can still contain the whole object and then get the middle of this box
    private void calculateCollisionBox(){
        Vector3f[] vertices = collisionModel.getVertices();

        float left = 0;
        float front = 0;
        float back = 0;
        float right = 0;
        float bottom = 0;
        float top = 0;

        //Add all vertices to the center vector
        for(Vector3f vertex : vertices){
            if(vertex.x < left) left = vertex.x;
            if(vertex.x > right) right = vertex.x;
            if(vertex.z < bottom) bottom = vertex.z;
            if(vertex.z > top) top = vertex.z;
            if(vertex.y < front) front = vertex.y;
            if(vertex.y > back) back = vertex.y;
        }

        this.collisionBox = new CollisionBox(left, front, back, right, bottom, top);
    }

    public CollisionModel getCollisionModel() {
        return collisionModel;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
