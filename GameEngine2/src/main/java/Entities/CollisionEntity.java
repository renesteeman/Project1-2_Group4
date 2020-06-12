package Entities;

import Models.CollisionModel;
import Toolbox.Maths;
import org.joml.Vector3f;

public class CollisionEntity extends Entity{
    private double collisionRadiusFullScale;
    private Vector3f centerPointModel;
    private CollisionModel collisionModel;

    public CollisionEntity(CollisionModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model.getTexturedModel(), position, rotX, rotY, rotZ, scale);
        this.collisionModel = model;
        calculateCollisionRadius();
        calculateObjectCenter();
    }

    //Option to create blank entity (for testing)
    public CollisionEntity() { }

    private void calculateCollisionRadius(){
        Vector3f[] vertices = collisionModel.getVertices();
        double maxVertexDistance = 0;
        for(Vector3f vertexOne : vertices){
            for(Vector3f vertexTwo : vertices){
                double distance = Maths.getDistance(vertexOne, vertexTwo);
                if(distance>maxVertexDistance){
                    maxVertexDistance = distance;
                }
            }
        }

        this.collisionRadiusFullScale = maxVertexDistance;
    }

    private void calculateObjectCenter(){
        Vector3f[] vertices = collisionModel.getVertices();
        Vector3f center = new Vector3f(0,0,0);

        //Add all vertices to the center vector
        for(Vector3f vertex : vertices){
            center = center.add(vertex);
        }

        //Divide by the amount of vertices you added to get the average
        center = center.div(vertices.length);

        this.centerPointModel = center;
    }

    public double getCollisionRadius() {
        return collisionRadiusFullScale*scale;
    }

    public CollisionModel getCollisionModel() {
        return collisionModel;
    }

    public double getCollisionRadiusFullScale() {
        return collisionRadiusFullScale;
    }

    public Vector3f getCenterPointModel() {
        return centerPointModel;
    }

    public Vector3f getCenterPoint(){
        return centerPointModel.add(position);
    }
}
