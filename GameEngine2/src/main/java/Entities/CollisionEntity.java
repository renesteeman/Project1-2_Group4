package Entities;

import Models.CollisionModel;
import Models.RawModel;
import Models.TexturedModel;
import OBJConverter.ModelData;
import OBJConverter.OBJFileLoader;
import Textures.ModelTexture;
import Toolbox.Maths;
import org.joml.Vector3f;

public class CollisionEntity extends Entity{
    private double collisionRadiusFullScale;
    private CollisionModel collisionModel;

    public CollisionEntity(CollisionModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model.getTexturedModel(), position, rotX, rotY, rotZ, scale);
        this.collisionModel = model;
        calculateCollisionRadius();
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

    public double getCollisionRadius() {
        return collisionRadiusFullScale*scale;
    }

    public CollisionModel getCollisionModel() {
        return collisionModel;
    }
}
