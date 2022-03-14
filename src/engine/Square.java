package engine;

import java.util.ArrayList;
import java.util.Arrays;

import engine.exceptions.DrawElementsException;
import engine.exceptions.TextureBindException;
import engine.exceptions.TextureException;

import static org.lwjgl.opengl.GL33.*;

public class Square extends GameObject{
	
	private static int startIndex;
	private static int numIndices;

	private static ArrayList<Float> vertices = new ArrayList<Float>();
	private static ArrayList<Integer> indices = new ArrayList<Integer>();
	
	public Square(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, String texture) throws TextureException{
		super(x,y,z,pitch,yaw,roll,xScale,yScale, texture);
				
	}
		
	public void draw(ShaderController controller) throws TextureBindException, DrawElementsException {
		this.genTransformMatrix();
		//System.out.println("Texture id = " + this.textureID);
		glBindTexture(GL_TEXTURE_2D, this.textureID);
		int err = glGetError();
		if(err != 0) {
			throw new TextureBindException(this);
		}
		//System.out.println("Texture bound successfully, drawing.");
		controller.setMat4f("transform", this.trans);
		//System.out.println("Draw call: GL_TRIANGLES, " + numIndices + ", GL_UNSIGNED_INT, " + startIndex*Integer.BYTES);
		glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, startIndex * Integer.BYTES);
		err = glGetError();
		if(err != 0) {
			throw new DrawElementsException(this, err);
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
		indices = new ArrayList<Integer>(Arrays.asList(2,3,1,2,1,0));
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
