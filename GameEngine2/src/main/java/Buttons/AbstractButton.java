package Buttons;

import EngineTester.MainGameLoop;
import FontMeshCreator.GUIText;
import GUI.GUITexture;
import MouseHandler.MouseHandler;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import org.joml.Vector2f;
import java.util.List;
import static org.lwjgl.glfw.GLFW.*;

public class AbstractButton extends GUITexture implements InterfaceButton {

    private Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public AbstractButton(int texture, int transparentTexture, Vector2f position, Vector2f scale) {
        super(texture, transparentTexture, position, scale);
        this.originalScale = scale;
    }

    public void update(){
        if(!isHidden){
            Vector2f mouseCoordinates = DisplayManager.getNormalizedMouseCoordinates();

            if(position.y + scale.y > -mouseCoordinates.y &&
                position.y - scale.y < -mouseCoordinates.y &&
                position.x + scale.x > mouseCoordinates.x &&
                position.x - scale.x < mouseCoordinates.x) {

                whileHovering(this);

                if (!isHovering) {
                    isHovering = true;
                    onStartHover(this);
                }

                if (glfwGetMouseButton(DisplayManager.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
                    onClick(this);
                }

            } else{

                if(isHovering) {
                    isHovering = false;
                    onStopHover(this);
                }

            }
        }
    }

    public void resetScale(){
        setScale(originalScale);
    }

    @Override
    public void onClick(InterfaceButton button) {

    }

    @Override
    public void onStartHover(InterfaceButton button) {

    }

    @Override
    public void onStopHover(InterfaceButton button) {

    }

    @Override
    public void whileHovering(InterfaceButton button) {

    }

    @Override
    public void show(List<GUITexture> guiTextureList) {
        texture = originalTexture;
        isHidden = false;
    }

    @Override
    public void hide(List<GUITexture> guiTextureList) {
        texture = transparentTexture;
        isHidden = true;
    }

    public void playHoverAnimation(float scaleFactor){
        setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
    }

}
