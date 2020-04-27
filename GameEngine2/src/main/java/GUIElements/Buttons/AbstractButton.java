package GUIElements.Buttons;

import FontMeshCreator.GUIText;
import GUI.GUITexture;
import GUIElements.UIElement;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import org.joml.Vector2f;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public abstract class AbstractButton implements InterfaceButton, UIElement {
    protected GUITexture guiTexture;
    private Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;
    static int oldLeftMouseButtonState = GLFW_RELEASE;

    public AbstractButton(Loader loader, String texture, Vector2f position, Vector2f scale){
        guiTexture = new GUITexture(loader.loadTexture(texture), position, scale);
        originalScale = scale;
import java.util.List;
import static org.lwjgl.glfw.GLFW.*;

public abstract class AbstractButton implements InterfaceButton, UIElement {
    protected GUITexture guiTexture;
public class AbstractButton extends GUITexture implements InterfaceButton {

    private Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public AbstractButton(Loader loader, String texture, Vector2f position, Vector2f scale){
        guiTexture = new GUITexture(loader.loadTexture(texture), position, scale);
        originalScale = scale;
    public AbstractButton(int texture, int transparentTexture, Vector2f position, Vector2f scale) {
        super(texture, transparentTexture, position, scale);
        this.originalScale = scale;
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

    public void onClick(){
        int newLeftMouseButtonState = glfwGetMouseButton(DisplayManager.getWindow(), GLFW_MOUSE_BUTTON_LEFT);
        if (newLeftMouseButtonState == GLFW_RELEASE && oldLeftMouseButtonState == GLFW_PRESS) {
        }
        if (newLeftMouseButtonState == GLFW_PRESS){
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

    public GUITexture getGUITexture() {
        return this.guiTexture;
    }

}
