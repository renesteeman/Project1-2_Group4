package GUI;

import org.joml.Vector2f;

public class GUITexture {

    private int texture;
    private Vector2f position;
    private Vector2f scale;

    public GUITexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f placement) {
        Vector2f newPosition = new Vector2f(placement.x, this.position.y);
        this.position = newPosition;
    }

    public double getXPosition(){
        return position.x;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale){
        this.scale = scale;
    }
}
