package RenderEngine;

import MouseHandler.MouseHandler;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class DisplayManager {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static String title = "Crazy Putting";
    private static long window;
    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay(){
        if (!GLFW.glfwInit()) {
            System.err.println("Couln't initialize GLFW");
            System.exit(-1);
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        //SMAA
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 0);
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, title, 0, 0);

        if (window == 0) {
            System.err.println("Window couldn't be created");
            System.exit(-1);
        }

        GLFW.glfwSetWindowPos(window, (WIDTH/2), (HEIGHT/2));

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);

        lastFrameTime = getCurrentTimeMilis();
    }

    public static void updateDisplay(){
        GLFW.glfwPollEvents();
        long currentFrameTime = getCurrentTimeMilis();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }

    public static boolean closed(){
        return GLFW.glfwWindowShouldClose(window);
    }

    private static long getCurrentTimeMilis() {
        return (long)(GLFW.glfwGetTime() * 1000);
    }

    public static void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static long getWindow() {
        return window;
    }

    public static float getDeltaTime() {
        return delta;
    }

    public static Vector2f getNormalizedMouseCoordinates(){
        float normalizedX = -1.0f + 2.0f * (float) MouseHandler.getCurrentX() / (float) DisplayManager.getWidth();
        float normalizedY = 1.0f - 2.0f * (float) MouseHandler.getCurrentY() / (float) DisplayManager.getHeight();

        return new Vector2f(normalizedX, normalizedY);
    }
}
