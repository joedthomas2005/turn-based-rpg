package engine;

public abstract class DrawableGameObject extends GameObject implements Drawable {

    protected int textureID;
    protected String texturePath;

    public DrawableGameObject(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, TextureController textureController, String texture){
        super(x,y,z,pitch,yaw,roll,xScale,yScale);
        this.textureID = textureController.getTexture(texture);
    };
    public abstract void draw(ShaderController shaderController, Camera camera);
}
