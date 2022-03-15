package engine;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;

import org.joml.*;

import engine.exceptions.DrawElementsException;
import engine.exceptions.TextureBindException;
import engine.exceptions.TextureLoadException;
import globals.ID_MANAGER;
import globals.PATHS;

public abstract class GameObject {

	protected float x, y, z, pitch, yaw, roll, xScale, yScale;
	protected Matrix4f trans = new Matrix4f();
	public abstract void draw(ShaderController controller) throws TextureBindException, DrawElementsException;
	private int ID;
	protected String texturePath;
	protected int textureID;
	
	private int[] texWidth = new int[1];
	private int[] texHeight = new int[1];
	private int[] nrChannels = new int[1];
	
	protected GameObject(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, String texture) throws TextureLoadException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		this.xScale = xScale;
		this.yScale = yScale;
		this.ID = ID_MANAGER.RequestID();
		ID_MANAGER.NewObject(this);
		
		glGetError();
		
		this.texturePath = PATHS.TextureDir + texture;
		ByteBuffer texData = stbi_load(texturePath, texWidth, texHeight, nrChannels, 3);
		texData.flip();
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texWidth[0], texHeight[0], 0, GL_RGB, GL_UNSIGNED_BYTE, texData);
		System.out.println(texData);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		int err = glGetError();
		if(err != 0) {
			throw new TextureLoadException(texturePath, Integer.toString(err));
		}
		
		stbi_image_free(texData);
	
	}
	
	public int getID() {
		return ID;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public String getTexturePath() {
		return texturePath;
	}
	
	public void move(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	
	public void rotate(float pitch, float yaw, float roll) {
		this.pitch += pitch;
		this.yaw += yaw;
		this.roll += roll;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	public void scale(float x, float y) {
		this.xScale *= x;
		this.yScale *= y;
	}
	
	public void setXScale(float x) {
		this.xScale = x;
	}
	
	public void setYScale(float y) {
		this.yScale = y;
	}
	
	protected void genTransformMatrix() {
		this.trans = new Matrix4f().translate(x,y,z).rotateXYZ(pitch,yaw,roll).scale(xScale,yScale,1);
	}
	
	
}
