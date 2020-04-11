package org.liquidengine.cbchain.impl;

import org.liquidengine.cbchain.AbstractChainCallback;
import org.liquidengine.cbchain.IChainMouseButtonCallback;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

/**
 * Chain callback implementation based on {@link AbstractChainCallback}.
 * <p>
 * Instances of this interface may be passed to the {@link GLFW#glfwSetMouseButtonCallback SetMouseButtonCallback} method.
 */
public class ChainMouseButtonCallback extends AbstractChainCallback<GLFWMouseButtonCallbackI> implements IChainMouseButtonCallback {
    @Override
    public void invoke(long window, int button, int action, int mods) {
        callbackChain.forEach(c -> c.invoke(window, button, action, mods));
    }
}
