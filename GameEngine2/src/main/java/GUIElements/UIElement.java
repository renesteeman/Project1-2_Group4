package GUIElements;

import GUI.GUITexture;

import java.util.ArrayList;

public interface UIElement {
    ArrayList<GUITexture> getGUITextures();
    void update();
}
