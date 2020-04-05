import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Goal extends GameObject{
	public Goal(Vector2d coords) {
		super(coords);
	}

	public Goal(Vector3d coords) {
		super(coords);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(400 + (int)coords3.x - 15, 600 - (int)coords3.z - 15, 30, 30);
	}
}