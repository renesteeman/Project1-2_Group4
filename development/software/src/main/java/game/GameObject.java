package game;

import java.awt.Graphics;

public class GameObject {
	public Vector2d coords2;
	public Vector3d coords3;

	public GameObject(Vector2d coords) { 
		this.coords2 = new Vector2d(coords);
		this.coords3 = new Vector3d(coords);
	}

	public GameObject(Vector3d coords) {
		this.coords2 = new Vector2d(coords);
		this.coords3 = new Vector3d(coords);
	}

	public void render(Graphics g) { 

	}

	public void setCoords2(Vector2d coords) {
		coords2 = coords;
	}

	public void setCoords3(Vector3d coords) {
		coords3 = coords;
	}

	public Vector2d getCoords2() {
		return coords2;
	}

	public Vector3d getCoords3() {
		return coords3;
	}
}
