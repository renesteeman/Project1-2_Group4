package Entities;

import MainGame.MainGameLoop;
import Models.TexturedModel;
import org.joml.Vector3f;
import Physics.Vector2d;
import Physics.Vector3d;

import java.awt.*;

public class Ball extends Entity {

	Vector3f velocity;

    public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public Ball(Vector3d position, Vector3d velocity) {
        this.position = Vector3d.convertF(position);
        this.velocity = Vector3d.convertF(velocity);
    }

    //TODO remove after testing
    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(400 + (int)position.x - 10, 600 - (int)position.y - 10, 20, 20);
    }

    public void setPosition(int x, int y, int z){
        this.setPosition(new Vector3f(x, y, z));
    }

    public String getBallInfoAsString(){

        Vector3f pos = this.getPosition();
        String info = "("+pos.x+" "+pos.y+" "+pos.z+")";

        return info.toString();
    }

    public void loadFromString(String ballInfo) {

        String[] ballCoordinates = ballInfo.split(" ");
        float xPos = Float.parseFloat(ballCoordinates[0]);
        float yPos = Float.parseFloat(ballCoordinates[1]);
        float zPos = Float.parseFloat(ballCoordinates[2]);

        MainGameLoop.ball.setPosition(new Vector3f(xPos, yPos, zPos));
    }

    public void setVelocity(Vector3f velocity) {
    	this.velocity = velocity;
    }

    public Vector3f getVelocity() {
    	return velocity;
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = Vector3d.convertF(velocity);
    }

    public Vector3d getVelocity3() {
        return new Vector3d(velocity.x, velocity.y, velocity.z);
    }

    public Vector2d getVelocity2() {
        return new Vector2d(velocity.x, velocity.z);
    }
}
