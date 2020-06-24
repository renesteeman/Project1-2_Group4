package Water;

//A water tile is an area with water
public class WaterTile {
	private float height;
	private float x;
	private float z;
	private float width;
	
	public WaterTile(float centerX, float centerZ, float height, float size){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
		this.width = size;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public float getWidth() {
		return width;
	}
}
