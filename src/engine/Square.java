package engine;

import settings.PATHS;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import engine.exceptions.TextureException;
import engine.exceptions.TextureLoadException;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Square extends GameObject{
	
	private static int startIndex;
	private static int endIndex;

	private static ArrayList<Float> vertices;
	private static ArrayList<Integer> indices;
	
	private String texturePath;
	private int textureID;
	
	private int[] texWidth = new int[1];
	private int[] texHeight = new int[1];
	private int[] nrChannels = new int[1];
	
	public Square(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, String texture) throws TextureException{
		super(x,y,z,pitch,yaw,roll,xScale,yScale);
		
		this.texturePath = PATHS.TextureDir + texture;
		ByteBuffer texData = stbi_load(texturePath, texWidth, texHeight, nrChannels, 3);
	
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texWidth[0], texHeight[0], 0, GL_RGB, GL_UNSIGNED_BYTE, texData);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		int err = glGetError();
		if(err != 0) {
			throw new TextureLoadException(texturePath, Integer.toString(err));
		}
	}
	
	public void draw(ShaderController controller) {
		this.genTransformMatrix();
		glBindTexture(GL_TEXTURE_2D, this.textureID);
	}
	
	public static void initialise(BufferController buffers) {
		
		int[] data = buffers.AddItem(vertices, indices);
		startIndex = data[0];
		endIndex = data[1];
			
	}
	
	public static int getStartIndex() {
		return startIndex;
	}
	
	public static int getEndIndex() {
		return endIndex;
	}
	
	public static ArrayList<Float> getVertices(){
		return vertices;
	}
	
	public static ArrayList<Integer> getIndices(){
		return indices;
	}

}
