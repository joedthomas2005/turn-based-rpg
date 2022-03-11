package engine;

import java.util.Arrays;

public class Square extends GameObject{
	
	private static int startIndex;
	private static int endIndex;
	private static ArrayList<Float> vertices = new ArrayList<Float>(Arrays.asList(null));
	public Square(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale) {
		super(x,y,z,pitch,yaw,roll,xScale,yScale);
		
	}
	
	public static void initialise(BufferController buffers) {
		
	}
}
