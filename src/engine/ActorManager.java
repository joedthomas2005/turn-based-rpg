package engine;

public class ActorManager {
    
    private Camera camera;
    private ShaderController shaders;
    private DrawableCreator creator; 
    public ActorManager(DrawableCreator creator, Camera camera, ShaderController shaders){
        this.camera = camera;
        this.shaders = shaders;
        this.creator = creator;
    }  
}
