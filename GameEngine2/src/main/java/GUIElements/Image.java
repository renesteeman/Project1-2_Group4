package GUIElements;

import GUI.GUITexture;
import GUIElements.Buttons.InterfaceButton;
import RenderEngine.Loader;
import org.joml.Vector2f;

import java.util.ArrayList;
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
        isHovering = true;
    }

    @Override
    public void onStopHover(InterfaceButton button) {
        isHovering = false;
    }

    @Override
    public void whileHovering(InterfaceButton button) {

    }

    @Override
    public void show(){
        guiTexture.setScale(originalScale);
        isHidden = false;
    }

    @Override
    public void hide(){
        guiTexture.setScale(new Vector2f(0, 0));
        isHidden = true;
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

    public ArrayList<GUITexture> getGUITextures(){
        ArrayList<GUITexture> textures = new ArrayList<>();
        textures.add(guiTexture);

        return textures;
    }
}
