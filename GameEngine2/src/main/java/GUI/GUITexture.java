package GUI;

import org.joml.Vector2f;

public class GUITexture {

    protected int originalTexture;
    protected int texture;
    protected int transparentTexture;
    protected Vector2f position;
    protected Vector2f scale;

    public GUITexture(int texture, int transparentTexture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.originalTexture = texture;
        this.transparentTexture = transparentTexture;
        this.position = position;
        this.scale = scale;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale){
        this.scale = scale;
    }
}
