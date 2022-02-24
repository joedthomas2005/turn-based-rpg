package engine;

public class GameObject {
	
	private float x, y, z, pitch, yaw, roll, xScale, yScale, zScale;
	
	void move(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
}
