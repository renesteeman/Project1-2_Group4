package GUIElements.Buttons;

import GUI.GUITexture;

import java.util.List;

public interface InterfaceButton {
    void onClick(InterfaceButton button);

    void onStartHover(InterfaceButton button);

    void onStopHover(InterfaceButton button);

    void whileHovering(InterfaceButton button);

    void show(List<GUITexture> guiTextureList);

    void hide(List<GUITexture> guiTextureList);

    void playHoverAnimation(float scaleFactor);

    void resetScale();

    void update();
}
