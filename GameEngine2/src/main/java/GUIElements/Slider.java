package GUIElements;

import FontMeshCreator.GUIText;
import GUI.GUITexture;
import GUIElements.Buttons.InterfaceButton;
import Physics.Vector3d;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import Toolbox.Maths;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Slider implements InterfaceButton, UIElement {
    protected GUITexture backgroundTexture;
    protected GUITexture sliderTexture;
    private final Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;
    private double value;

    public Slider(Loader loader, String backgroundTexture, String sliderTexture, Vector2f position, Vector2f scale){
        this.backgroundTexture = new GUITexture(loader.loadTexture(backgroundTexture), position, scale);
        this.sliderTexture = new GUITexture(loader.loadTexture(sliderTexture), position, scale);
        originalScale = scale;
        setupValue();
    }

    public void update(){
        if(!isHidden){
            Vector2f location = backgroundTexture.getPosition();
            Vector2f scale = backgroundTexture.getScale();

            Vector2f mouseCoordinates = DisplayManager.getNormalizedMouseCoordinates();

            if(location.y + scale.y > mouseCoordinates.y &&
                    location.y - scale.y < mouseCoordinates.y &&
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

    private void setupValue(){
        //Calculate value of the slider between 0 and 1 (but not including 0)
        //getBackgroundTexture().getXPosition() returns the middle coordinate of the bar in screen coordinates ([-1, 1]), similarly for the button
        double barCenterPos = Maths.screenCoordinateToPixelX(getBackgroundTexture().getXPosition());
        double knobCenterPos = Maths.screenCoordinateToPixelX(getSliderTexture().getXPosition());
        //600 is a random number that works, don't question the gods
        double barWidth = 600*getBackgroundTexture().getScale().x;
        //Math.min and Math.max ensure the value is always between 0 and 1 (including the edges)
        setValue(Math.min(Math.max((1+((knobCenterPos-barCenterPos)/barWidth))/2, 0.0000001), 1));
    }

    public void show(){
        isHidden = false;
        backgroundTexture.setScale(originalScale);
        sliderTexture.setScale(originalScale);
    }

    public void hide(){
        isHidden = false;
        backgroundTexture.setScale(new Vector2f(0, 0));
        sliderTexture.setScale(new Vector2f(0, 0));
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

    public ArrayList<GUITexture> getGUITextures(){
        ArrayList<GUITexture> textures = new ArrayList<>();

        textures.add(backgroundTexture);
        textures.add(sliderTexture);

        return textures;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
