package MouseHandler;

import RenderEngine.DisplayManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {
    private static double prevX = 0;
    private static double prevY = 0;

    private static double newX = 0;
    private static double newY = 0;

    private static double deltaX = 0;
    private static double deltaY = 0;

    public static void handleMouseEvents(){
        //Handle mouse movements
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(DisplayManager.getWindow(), x, y);
        x.rewind();
        y.rewind();

        newX = x.get();
        newY = y.get();

        deltaX = newX - prevX;
        deltaY = newY - prevY;

        prevX = newX;
        prevY = newY;
    }

    public static double getDeltaX() {
        return deltaX;
    }

    public static double getDeltaY() {
        return deltaY;
    }

}
