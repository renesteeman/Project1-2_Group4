package Entities;

import MouseHandler.MouseHandler;
import RenderEngine.DisplayManager;
import Terrain.Terrain;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import static RenderEngine.DisplayManager.getDeltaTime;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private float distanceFromBall = 50;
    private float angleAroundBall = 0;

    private Vector3f position;
    //Up-down rotation
    private float pitch;
    //Left-right rotation
    private float yaw;
    //Tilted
//    private float roll;

    private boolean preventTerrainClipping;

//    private final float MOVEMENT_SPEED = 20f;
//    private final float ROTATION_SPEED = 25f;
    private final float MOUSE_SPEED = 0.5f;
    private final float MOUSE_SCROLL_SPEED = 2f;

    //Handle mouse scrolling
    private static float mouseWheelVelocity = 0;

    private Ball ball;

    public Camera(Ball ball, Vector3f position){
        this(ball, position, true);
    }

    public Camera(Ball ball, Vector3f position, boolean preventTerrainClipping){
        this.ball = ball;
        this.position = position;

        GLFW.glfwSetScrollCallback(DisplayManager.getWindow(), new GLFWScrollCallback() {
            @Override public void invoke (long win, double dx, double dy) {
                mouseWheelVelocity = (float) dy;
                calculateZoom();
            }
        });

        this.preventTerrainClipping = preventTerrainClipping;
    }

    //Needs the terrain to prevent the camera from clipping trough it
    public void move(Terrain terrain){
        //third person camera
        calculateAngleAroundPlayerAndPitch();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance, terrain);
        this.yaw = 180 - (ball.getRotY() + angleAroundBall);

        /*
        Controls for free moving camera
        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_W) == GLFW.GLFW_PRESS){
            position.z -= getDeltaTime() * MOVEMENT_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_S) == GLFW.GLFW_PRESS){
            position.z += getDeltaTime() * MOVEMENT_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_A) == GLFW.GLFW_PRESS){
            position.x -= getDeltaTime() * MOVEMENT_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_D) == GLFW.GLFW_PRESS){
            position.x += getDeltaTime() * MOVEMENT_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_SPACE) == GLFW.GLFW_PRESS){
            position.y += getDeltaTime() * MOVEMENT_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS){
            position.y -= getDeltaTime() * MOVEMENT_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_Q) == GLFW.GLFW_PRESS){
            yaw -= getDeltaTime() * ROTATION_SPEED;
        }

        if(glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_E) == GLFW.GLFW_PRESS){
            yaw += getDeltaTime() * ROTATION_SPEED;
        }
*/
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance, Terrain terrain){
        float theta = ball.getRotY() + angleAroundBall;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));

        position.x = ball.getPosition().x - offsetX;
        position.z = ball.getPosition().z - offsetZ;

        //Prevent clipping trough terrain
        if(preventTerrainClipping){
            float terrainHeight = terrain.getHeightOfTerrain(position.x, position.z);
            if(ball.getPosition().y + verticalDistance > terrainHeight){
                position.y = ball.getPosition().y + verticalDistance;
            } else {
                position.y = terrainHeight+1f;
            }
        } else {
            position.y = ball.getPosition().y + verticalDistance;
        }
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromBall * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromBall * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        float zoomLevel = mouseWheelVelocity * MOUSE_SCROLL_SPEED;
        distanceFromBall -= zoomLevel;
    }

    private void calculateAngleAroundPlayerAndPitch(){
        //Left mouse
        if(glfwGetMouseButton(DisplayManager.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS){
            float pitchChange = (float) (MouseHandler.getDeltaY() * MOUSE_SPEED);
            pitch += pitchChange;
            float angleChange = (float) (MouseHandler.getDeltaX() * MOUSE_SPEED);
            angleAroundBall -= angleChange;
        }
    }

    public void invertPitch(){
        this.pitch = -pitch;
    }

    public boolean isPreventTerrainClipping() {
        return preventTerrainClipping;
    }

    public void setPreventTerrainClipping(boolean preventTerrainClipping) {
        this.preventTerrainClipping = preventTerrainClipping;
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

//    public float getRoll() {
//        return roll;
//    }

}
