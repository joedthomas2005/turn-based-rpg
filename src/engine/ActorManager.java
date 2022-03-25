package engine;

import java.util.ArrayList;

/**
 * Actor Manager is class which handles creating actors. It has a predefined 1 texture animator for non-animated sprites and a 
 * DrawableCreator stored to pass to the objects it creates.
 */
public class ActorManager {
    
    private final DrawableCreator creator; 
    private final Animator noAnimations = new Animator(new SpriteSheetParser(1,1), new int[]{0,1});
    private final ArrayList<Actor> actors = new ArrayList<Actor>();
    private final Camera camera;
    
    public ActorManager(DrawableCreator creator, Camera camera){
        this.creator = creator;
        this.camera = camera;
    }  
    
    /**
     * Returns a list of all actors added to the generic actor list.
     * @return an arraylist of Actors
     */
    public ArrayList<Actor> getActors(){
        return actors;
    }

    /**
     * Returns a new instance of actor created with the stored DrawableCreator and a given Animator.
     * @param x the x coordinates
     * @param y the y coordinates
     * @param rotation the roll rotation
     * @param xScale the scale in the x axis
     * @param yScale the scale in the y axis
     * @param texture the path for the spritesheet to use
     * @param animator an Animator object to pass to the actor to animate it
     * @return a new instance of Actor
     */
    public Actor create(float x, float y, float rotation, float xScale, float yScale, String texture, Animator animator) {
    	return new Actor(x, y, rotation, xScale, yScale, texture, animator, creator);
    }
    
    /**
     * Returns a new instance of actor created with the stored DrawableCreator and the default 1 state animator
     * @param x the x coordinates
     * @param y the y coordinates
     * @param rotation the roll rotation
     * @param xScale the scale in the x axis
     * @param yScale the scale in the y axis
     * @param texture the path for the texture to use
     * @return a new instance of Actor
     */
    public Actor create(float x, float y, float rotation, float xScale, float yScale, String texture) {
    	return create(x, y, rotation, xScale, yScale, texture, noAnimations);
    }
    
    /**
     * Add an actor to the stored actor list (primarily for Actors which can be drawn in any order all in one go).
     * @param actor the actor to add
     */
    public void add(Actor actor){
        actors.add(actor);
    }

    /**
     * Draw the actor if it is visible in the stored camera's viewport.
     * @param actor the actor to draw.
     */
    public void draw(Actor actor) {
    	if(camera.isRectVisible(actor.x - actor.getXScale()/2, actor.x + actor.getXScale()/2, actor.y - actor.getYScale()/2, actor.y + actor.getYScale()/2)) {	
    		actor.draw();
    	}
    }
    
    /**
     * Draw all actors in the stored actor list.
     */
    public void drawAll() {
    	for(Actor actor : actors) {
    		actor.draw();
    	}
    }
    
    /**
     * Animate all actors in the stored actor list (switch them to their current animation state's next frame).
     */
    public void animateAll() {
    	for(Actor actor : actors) {
    		actor.animate();
    	}
    }

    /**
     * Remove an actor from the stored actor list (doesn't delete the actor unless it isn't refered to anywhere else).
     * @param actor the actor to remove
     */
    public void delete(Actor actor){
        actors.remove(actor);
    }
   
    
}
