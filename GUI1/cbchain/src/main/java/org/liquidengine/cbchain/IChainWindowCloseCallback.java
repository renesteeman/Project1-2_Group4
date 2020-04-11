package org.liquidengine.cbchain;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

/**
 * Instances of this interface may be passed to the {@link GLFW#glfwSetWindowCloseCallback SetWindowCloseCallback} method.
 */
public interface IChainWindowCloseCallback extends IChainCallback<GLFWWindowCloseCallbackI>, GLFWWindowCloseCallbackI {
}
