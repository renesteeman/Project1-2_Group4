package GUIElements;

import GUI.GUITexture;
import GUIElements.Buttons.InterfaceButton;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import org.joml.Vector2f;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Slider implements InterfaceButton, UIElement {
    protected GUITexture backgroundTexture;
    protected GUITexture sliderTexture;
    private final Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public Slider(Loader loader, String backgroundTexture, String sliderTexture, Vector2f position, Vector2f scale){
        this.backgroundTexture = new GUITexture(loader.loadTexture(backgroundTexture), position, scale);
        this.sliderTexture = new GUITexture(loader.loadTexture(sliderTexture), position, scale);
        originalScale = scale;
    }

    public void update(){
        if(!isHidden){
            Vector2f location = backgroundTexture.getPosition();
            Vector2f scale = backgroundTexture.getScale();

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
            guiTextureList.add(backgroundTexture);
            guiTextureList.add(sliderTexture);
            isHidden = false;
        }
    }

    public void hide(List<GUITexture> guiTextureList){
        if(!isHidden){
            guiTextureList.remove(backgroundTexture);
            guiTextureList.remove(sliderTexture);
            isHidden = true;
        }
    }

    public void resetScale(){
        backgroundTexture.setScale(originalScale);
        sliderTexture.setScale(originalScale);
    }

    public void playHoverAnimation(float scaleFactor){
        backgroundTexture.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
        sliderTexture.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
    }

    public GUITexture getBackgroundTexture() {
        return backgroundTexture;
    }

    public GUITexture getSliderTexture() {
        return sliderTexture;
    }

    public void setBackgroundTexture(GUITexture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setSliderTexture(GUITexture sliderTexture) {
        this.sliderTexture = sliderTexture;
    }
}
