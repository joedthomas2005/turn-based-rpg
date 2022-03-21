package engine;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;

import engine.exceptions.DrawElementsException;
import engine.exceptions.TextureBindException;

public class DrawableGameObject extends GameObject {

    protected int textureID;
    protected String texturePath;
    private int startIndex;
    private int numIndices;
    private int currentFrame;
    private SpriteSheetParser parser;
    public DrawableGameObject(float x, float y, float z,
     float pitch, float yaw, float roll,
      float xScale, float yScale,
       TextureController textureController, String texture,
        int startIndex, int numIndices,
         int numFrames, int numRows){

        super(x,y,z,pitch,yaw,roll,xScale,yScale);
        this.textureID = textureController.getTexture(texture);
        this.startIndex = startIndex;
        this.numIndices = numIndices;
        this.currentFrame = 0;
        this.parser = new SpriteSheetParser(numFrames, numRows);
    };

    public void setFrame(int frame){
        this.currentFrame = frame;
    }

    public int getFrame(){
        return currentFrame;
    }
    public void draw(ShaderController shaderController, Camera camera){
      
        glBindTexture(GL_TEXTURE_2D, this.textureID);
		int err = glGetError();
		if(err != 0) {
			throw new TextureBindException(this.texturePath, this.textureID);
		}
		
        shaderController.setMat4f("texture", this.parser.getFrame(currentFrame));
		shaderController.setMat4f("transform", this.trans);
		
		glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, startIndex * Integer.BYTES);
		err = glGetError();
		if(err != 0) {
			throw new DrawElementsException(this, err);
		}
		
    }
}
