package game;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Ball extends GameObject{
	public Vector2d velocity2;
	public Vector3d velocity3;

	public Ball(Vector2d coords, Vector2d velocity) {
		super(coords);
		this.velocity2 = new Vector2d(velocity);
		this.velocity3 = new Vector3d(velocity);
	}

	public Ball(Vector3d coords, Vector3d velocity) {
		super(coords);
		this.velocity2 = new Vector2d(velocity);
		this.velocity3 = new Vector3d(velocity);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(400 + (int)coords3.x - 10, 600 - (int)coords3.z - 10, 20, 20);
	}

	public void setVelocity2(Vector2d velocity) {
		velocity2 = velocity;
	}

	public void setVelocity3(Vector3d velocity) {
		velocity3 = velocity;
	}

	public Vector2d getVelocity2() {
		return velocity2;
	}

	public Vector3d getVelocity3() {
		return velocity3;
	}
}
