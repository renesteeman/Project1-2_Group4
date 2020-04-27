package GUIElements;

import GUI.GUITexture;
import GUIElements.Buttons.InterfaceButton;
import RenderEngine.Loader;
import org.joml.Vector2f;

import java.util.List;

public class Image implements InterfaceButton, UIElement {
    private GUITexture guiTexture;
    private Vector2f originalScale;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public Image(Loader loader, String texture, Vector2f position, Vector2f scale){
        guiTexture = new GUITexture(loader.loadTexture(texture), position, scale);
        originalScale = scale;
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
    public void show(List<GUITexture> guiTextureList){
        if(isHidden){
            guiTextureList.add(guiTexture);
            isHidden = false;
        }
    }

    @Override
    public void hide(List<GUITexture> guiTextureList){
        if(!isHidden){
            guiTextureList.remove(guiTexture);
            isHidden = true;
        }
    }

    @Override
    public void playHoverAnimation(float scaleFactor) {

    }

    @Override
    public void resetScale() {

    }

    @Override
    public void update() {

    }

    @Override
    public GUITexture getGUITexture() {
        return guiTexture;
    }
}
