package Entities;

import Models.CollisionModel;
import Physics.Vector2d;
import Physics.Vector3d;
import Toolbox.Maths;
import org.joml.Vector3f;

import java.awt.*;

public class Ball extends CollisionEntity {
	Vector3f velocity;
	float massOfBall;
	float collisionRadiusFullScale;

    public Ball(CollisionModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        calculateCollisionRadius();
    }

    //BOT/TESTING ONLY
    public Ball(Vector3d position, Vector3d velocity) {
        this.position = Vector3d.convertF(position);
        this.velocity = Vector3d.convertF(velocity);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(400 + (int)position.x - 10, 600 - (int)position.y - 10, 20, 20);
    }

    public void setPosition(int x, int y, int z){
        this.setPosition(new Vector3f(x, y, z));
    }

    public Vector3f getVelocity() {
    	return new Vector3f(velocity);
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = Vector3d.convertF(velocity);
    }

    public Vector3d getVelocity3D() {
        return new Vector3d(velocity.x, velocity.y, velocity.z);
    }

    public Vector2d getVelocity2D() {
        return new Vector2d(velocity.x, velocity.z);
    }

    public float getMassOfBall() {
        return massOfBall;
    }

    public void setMassOfBall(float massOfBall) {
        this.massOfBall = massOfBall;
    }

    private void calculateCollisionRadius(){
        Vector3f[] vertices = collisionModel.getVertices();
        float maxVertexDistance = 0;
        for(Vector3f vertexOne : vertices){
            for(Vector3f vertexTwo : vertices){
                double distance = Maths.getDistance(vertexOne, vertexTwo);
                if(distance>maxVertexDistance){
                    maxVertexDistance = (float) distance;
                }
            }
        }

        this.collisionRadiusFullScale = maxVertexDistance;
    }

    public float getCollisionRadiusFullScale() {
        return collisionRadiusFullScale;
    }

    public double getCollisionRadiusScaled() {
        return collisionRadiusFullScale*scale;
    }
}
