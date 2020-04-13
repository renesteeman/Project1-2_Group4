package Buttons;

import EngineTester.MainGameLoop;
import FontMeshCreator.GUIText;
import GUI.GUITexture;
import GUIElements.UIElement;
import MouseHandler.MouseHandler;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import org.joml.Vector2f;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public abstract class AbstractButton implements InterfaceButton, UIElement {

    private GUITexture guiTexture;
    private Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public AbstractButton(Loader loader, String texture, Vector2f position, Vector2f scale){
        guiTexture = new GUITexture(loader.loadTexture(texture), position, scale);
        originalScale = scale;
    }

    public void update(){
        if(!isHidden){
            Vector2f location = guiTexture.getPosition();
            Vector2f scale = guiTexture.getScale();

            Vector2f mouseCoordinates = DisplayManager.getNormalizedMouseCoordinates();

            if(location.y + scale.y > -mouseCoordinates.y &&
                location.y - scale.y < -mouseCoordinates.y &&
                location.x + scale.x > mouseCoordinates.x &&
                location.x - scale.x < mouseCoordinates.x) {
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

    public void show(List<GUITexture> guiTextureList){
        if(isHidden){
            guiTextureList.add(guiTexture);
            isHidden = false;
        }
    }

    public void hide(List<GUITexture> guiTextureList){
        if(!isHidden){
            guiTextureList.remove(guiTexture);
            isHidden = true;
        }
    }

    public void resetScale(){
        guiTexture.setScale(originalScale);
    }

    public void playHoverAnimation(float scaleFactor){
        guiTexture.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
    }

}
