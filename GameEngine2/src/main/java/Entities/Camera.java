package Entities;

import RenderEngine.DisplayManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static RenderEngine.DisplayManager.getDeltaTime;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    //Up-down rotation
    private float pitch;
    //Left-right rotation
    private float yaw;
    //Tilted
    private float roll;

    public Camera(){

    }

    public void move(){
        if(GLFW.glfwGetMouseButton(DisplayManager.getWindow(), GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS){
            position.z -= getDeltaTime() * 0.02f;
        }

        if(GLFW.glfwGetMouseButton(DisplayManager.getWindow(), GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS){
            position.z -= getDeltaTime() * -0.02f;
        }

        if(GLFW.glfwGetMouseButton(DisplayManager.getWindow(), GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS){
            position.x -= getDeltaTime() * 0.02f;
        }

        if(GLFW.glfwGetMouseButton(DisplayManager.getWindow(), GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS){
            position.x -= getDeltaTime() * -0.02f;
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
