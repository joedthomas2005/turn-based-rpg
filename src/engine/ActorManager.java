package engine;

import java.util.ArrayList;

public class ActorManager {
    
    private ShaderController shaders;
    private DrawableCreator creator; 
    private final Animator noAnimations = new Animator(new SpriteSheetParser(1,1), new int[]{0,0});
    private final ArrayList<Actor> actors = new ArrayList<Actor>();
    
    public ActorManager(DrawableCreator creator, ShaderController shaders){

        this.shaders = shaders;
        this.creator = creator;
        
    }  
    
    public Actor create(float x, float y, float rotation, float xScale, float yScale, String texture, Animator animator) {
    	Actor newActor = new Actor(x, y, rotation, xScale, yScale, texture, animator, creator);
    	actors.add(newActor);
    	return newActor;
    }
    
    public Actor create(float x, float y, float rotation, float xScale, float yScale, String texture) {
    	return create(x, y, rotation, xScale, yScale, texture, noAnimations);
    }
    
    public void drawAll() {
    	for(Actor actor : actors) {
    		actor.draw(shaders);
    	}
    }
    
    public void animateAll() {
    	for(Actor actor : actors) {
    		actor.animate();
    	}
    }
   
    
}
