package Entities;

import RenderEngine.DisplayManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static RenderEngine.DisplayManager.getDeltaTime;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    //Up-down rotation
    private float pitch;
    //Left-right rotation
    private float yaw;
    //Tilted
    private float roll;

    private final float MOVEMENT_SPEED = 20f;
    private final float ROTATION_SPEED = 25f;

    public Camera(){}

    public Camera(Vector3f position){
        this.position = position;
    }

    public void move(){
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
