package GUIElements.Buttons;

import FontMeshCreator.GUIText;
import GUI.GUITexture;
import GUIElements.UIElement;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public abstract class AbstractButton implements InterfaceButton, UIElement {
    protected GUITexture guiTexture;
    private final Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public AbstractButton(Loader loader, String texture, Vector2f position, Vector2f scale){
        this.guiTexture = new GUITexture(loader.loadTexture(texture), position, scale);
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

    public void show(){
        isHidden = false;
        guiTexture.setScale(originalScale);
    }

    public void hide(){
        isHidden = true;
        guiTexture.setScale(new Vector2f(0, 0));
    }

    public void resetScale(){
        guiTexture.setScale(originalScale);
    }

    public void playHoverAnimation(float scaleFactor){
        guiTexture.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
    }

    public GUITexture getGUITexture() {
        return this.guiTexture;
    }

    public ArrayList<GUITexture> getGUITextures(){
        ArrayList<GUITexture> textures = new ArrayList<>();
        textures.add(guiTexture);

        return textures;
    }

    public void setGuiTexture(GUITexture guiTexture) {
        this.guiTexture = guiTexture;
    }
}
