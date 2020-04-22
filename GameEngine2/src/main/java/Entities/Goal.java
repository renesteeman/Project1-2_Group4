package Entities;

import MainGame.MainGameLoop;
import Physics.Vector2d;
import Physics.Vector3d;
import java.awt.Graphics;
import java.awt.Color;
import Models.TexturedModel;
import org.joml.Vector3f;

public class Goal extends Entity{
    float radius;

    public Goal(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public Goal(Vector3d position) {
        this.position = Vector3d.convertF(position);
    }

    public void setPosition(int x, int y, int z){
        this.setPosition(new Vector3f(x, y, z));
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    //TODO remove after testing
    @Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(400 + (int)position.x - 15, 600 - (int)position.y - 15, 30, 30);
	}
}
