package engine;

import java.util.ArrayList;

import engine.exceptions.DrawElementsException;
import engine.exceptions.TextureBindException;
import engine.exceptions.TextureException;

import static org.lwjgl.opengl.GL33.*;

public class Square extends GameObject{
	
	private static int startIndex;
	private static int numIndices;

	private static ArrayList<Float> vertices = new ArrayList<Float>();
	private static ArrayList<Integer> indices = new ArrayList<Integer>();	
	public Square(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, String texture, TextureController textureManager) throws TextureException{
		super(x,y,z,pitch,yaw,roll,xScale,yScale, texture, textureManager);
				
	}
		
	public void draw(ShaderController controller, Camera camera, ArrayList<GameObject> otherObjects) throws TextureBindException, DrawElementsException {

		if(camera.getMoved()) {
			this.visible = this.checkVisible(camera, controller ,otherObjects);
		}
		
		if(visible) {
		
			this.genTransformMatrix();
		
			glBindTexture(GL_TEXTURE_2D, this.textureID);
			int err = glGetError();
			if(err != 0) {
				throw new TextureBindException(this);
			}
		
			controller.setMat4f("transform", this.trans);
		
			glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, startIndex * Integer.BYTES);
			err = glGetError();
			if(err != 0) {
				throw new DrawElementsException(this, err);
			}
		}
	}
	
	public static void initialise(BufferController buffers) {
		
		for(int w = 1; w >= -1; w -= 2) {
			for (int h = 1; h >= -1; h -=2) {
				vertices.add(w * 0.25f);
				vertices.add(h * 0.25f);
				vertices.add(0f);

				if(w < 0) {
					vertices.add(0f);
				}
				else {
					vertices.add(1f);
				}
				if(h < 0f) {
					vertices.add(0f);
				}
				else {
					vertices.add(1f);
				}
			}
		}
		for(int i : new int[]{2,3,1,2,1,0}){
			indices.add(i);
		}
		int[] data = buffers.AddItem(vertices, indices);
		startIndex = data[0];
		numIndices = data[1];
		System.out.println("Square shape initialised. startIndex = " + startIndex + ", numIndices = " + numIndices);
	}
	
	public static int getStartIndex() {
		return startIndex;
	}
	
	public static int getNumIndices() {
		return numIndices;
	}
	
	public static ArrayList<Float> getVertices(){
		return vertices;
	}
	
	public static ArrayList<Integer> getIndices(){
		return indices;
	}

}
