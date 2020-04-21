package Entities;

import MainGame.MainGameLoop;
import Physics.Vector2d;
import Physics.Vector3d;
import java.awt.Graphics;
import java.awt.Color;
import Models.TexturedModel;
import org.joml.Vector3f;

public class Goal extends Entity{

    public Goal(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public Goal(Vector3d position) {
        this.position = Vector3d.convertF(position);
    }

    public void setPosition(int x, int y, int z){
        this.setPosition(new Vector3f(x, y, z));
    }

    public String getGoalInfoAsString(){

        Vector3f pos = this.getPosition();
        String info = "("+pos.x+" "+pos.y+" "+pos.z+")";

        return info.toString();
    }

    public void loadFromString(String ballInfo){

        String[] goalCoordinates = ballInfo.split(" ");
        float xPos = Float.parseFloat(goalCoordinates[0]);
        float yPos = Float.parseFloat(goalCoordinates[1]);
        float zPos = Float.parseFloat(goalCoordinates[2]);

        MainGameLoop.goal.setPosition(new Vector3f(xPos, yPos, zPos));
    }

    //TODO remove after testing
    @Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(400 + (int)position.x - 15, 600 - (int)position.y - 15, 30, 30);
	}
}
