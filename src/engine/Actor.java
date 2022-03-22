package engine;

public class Actor extends GameObject{
    
    private DrawableGameObject sprite;
    private Animator animator;

    public Actor(float x, float y, float rotation, float xScale, float yScale, String texture, Animator animator, DrawableCreator spriteCreator){
        
        super(x,y,0,0,0,rotation, xScale, yScale);
        this.sprite = spriteCreator.create(x,y,rotation,xScale,yScale, texture); 
        this.animator = animator;

    }

    public void draw(Camera camera, ShaderController shaders){
        
        sprite.setX(x);
        sprite.setY(y);
        sprite.setRoll(roll);
        sprite.setXScale(xScale);
        sprite.setYScale(yScale);

        sprite.draw(shaders, camera, animator.getFrame());
    }

    public void animate(){
        animator.nextFrame();
    }

    public void setAnimationState(int state){
        animator.setState(state);
    }


}
