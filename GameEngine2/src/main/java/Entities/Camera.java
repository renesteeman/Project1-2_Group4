package Entities;

import MouseHandler.MouseHandler;
import RenderEngine.DisplayManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static RenderEngine.DisplayManager.getDeltaTime;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private float distanceFromBall = 50;
    private float angleAroundBall = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    //Up-down rotation
    private float pitch;
    //Left-right rotation
    private float yaw;
    //Tilted
    private float roll;

    private final float MOVEMENT_SPEED = 20f;
    private final float ROTATION_SPEED = 25f;
    private final float MOUSE_SPEED = 0.5f;

    private Ball ball;

    public Camera(Ball ball, Vector3f position){
        this.ball = ball;
        this.position = position;
    }

    public void move(){
        //third person camera
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (ball.getRotY() + angleAroundBall);

        //Controls for free moving camera
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_W) == GLFW.GLFW_PRESS){
//            position.z -= getDeltaTime() * MOVEMENT_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_S) == GLFW.GLFW_PRESS){
//            position.z += getDeltaTime() * MOVEMENT_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_A) == GLFW.GLFW_PRESS){
//            position.x -= getDeltaTime() * MOVEMENT_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_D) == GLFW.GLFW_PRESS){
//            position.x += getDeltaTime() * MOVEMENT_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_SPACE) == GLFW.GLFW_PRESS){
//            position.y += getDeltaTime() * MOVEMENT_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS){
//            position.y -= getDeltaTime() * MOVEMENT_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_Q) == GLFW.GLFW_PRESS){
//            yaw -= getDeltaTime() * ROTATION_SPEED;
//        }
//
//        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_E) == GLFW.GLFW_PRESS){
//            yaw += getDeltaTime() * ROTATION_SPEED;
//        }
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = ball.getRotY() + angleAroundBall;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = ball.getPosition().x - offsetX;
        position.z = ball.getPosition().z - offsetZ;
        position.y = ball.getPosition().y + verticalDistance;

    }

    private float calculateHorizontalDistance(){
        float horizontalDistance = (float) (distanceFromBall * Math.cos(Math.toRadians(pitch)));

        return horizontalDistance;
    }

    private float calculateVerticalDistance(){
        float verticalDistance = (float) (distanceFromBall * Math.sin(Math.toRadians(pitch)));

        //Prevent awkward rotations
        if(pitch<0){
            pitch = 0;
        } else if(pitch>180){
            pitch=180;
        }

        return verticalDistance;
    }

    private void calculateZoom(){
//        float zoomLevel = Mouse.getDWheel() * MOUSE_SPEED;
//        distanceFromBall -= zoomLevel;
    }

    private void calculatePitch(){
        //Right mouse
        if(glfwGetMouseButton(DisplayManager.getWindow(), GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS){
            float pitchChange = (float) (MouseHandler.getDeltaY() * MOUSE_SPEED);
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer(){
        //Left mouse
        if(glfwGetMouseButton(DisplayManager.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS){
            float angleChange = (float) (MouseHandler.getDeltaX() * MOUSE_SPEED);
            angleAroundBall -= angleChange;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
