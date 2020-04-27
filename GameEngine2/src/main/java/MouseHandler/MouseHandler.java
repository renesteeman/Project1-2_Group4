package MouseHandler;

import RenderEngine.DisplayManager;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {
    private static boolean enabled = true;

    private static double prevX = 0;
    private static double prevY = 0;

    private static double currentX = 0;
    private static double currentY = 0;

    private static double deltaX = 0;
    private static double deltaY = 0;

    public static void handleMouseEvents(){
        //Handle mouse movements
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(DisplayManager.getWindow(), x, y);
        x.rewind();
        y.rewind();

        currentX = x.get();
        currentY = y.get();

        if(enabled){
            deltaX = currentX - prevX;
            deltaY = currentY - prevY;
        } else {
            deltaX = 0;
            deltaY = 0;
        }

        prevX = currentX;
        prevY = currentY;
    }

    public static double getDeltaX() {
        return deltaX;
    }

    public static double getDeltaY() {
        return deltaY;
    }

    public static double getCurrentX() {
        return currentX;
    }

    public static double getCurrentY() {
        return currentY;
    }

    public static void enable() {
        MouseHandler.enabled = true;
    }

    public static void disable() {
        MouseHandler.enabled = false;
    }

    public static Vector2f getCurrentPosition() {
        return new Vector2f((float) currentX, (float) currentY);
    }
}
