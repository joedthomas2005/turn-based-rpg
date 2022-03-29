package engine;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;

import engine.exceptions.DrawElementsException;
import engine.exceptions.TextureBindException;
import matrices.Matrix;

public class DrawableGameObject extends GameObject {

    protected final int textureID;
    protected final String texturePath;
    private final int startIndex;
    private final int numIndices;
    private ShaderController shaders;

    public DrawableGameObject(float x, float y, float z,
     float pitch, float yaw, float roll,
      float xScale, float yScale, ShaderController shaders,
       TextureController textureController, String texture,
        int startIndex, int numIndices){

        super(x,y,z,pitch,yaw,roll,xScale,yScale);
        this.textureID = textureController.getTexture(texture);
        this.texturePath = texture;
        this.startIndex = startIndex;
        this.shaders = shaders;
        this.numIndices = numIndices;
    };

    public void draw(Matrix frame){
      
        glBindTexture(GL_TEXTURE_2D, this.textureID);
		int err = glGetError();
		if(err != 0) {
			throw new TextureBindException(this.texturePath, this.textureID);
		}
		
        shaders.setMat4f("texCoordTransform", frame);
		shaders.setMat4f("transform", this.trans);
		
		glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, startIndex * Integer.BYTES);
		err = glGetError();
		if(err != 0) {
			throw new DrawElementsException(this, err);
		}
		
    }
}
