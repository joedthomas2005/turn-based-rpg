package engine;

import matrices.Matrix;

public class Animator {

    private final SpriteSheetParser frames;
    private final int[][] animationRanges;
    private int currentFrame;
    private int currentAnimation;

    public Animator(SpriteSheetParser parser, int[]... animationRanges){
        
        this.animationRanges = animationRanges;
        this.frames = parser;
        this.currentFrame = 0;

    }

    public boolean isFinished(){
        
        return this.currentFrame == animationRanges[currentAnimation][1];
    }

    public void setState(int animation){
        int animationNumber = animation;
        if(animationNumber >= animationRanges.length){
            animationNumber -= animationRanges.length;
        }
        this.currentAnimation = animationNumber;
    }

    public int getState(){
        return this.currentAnimation;
    }

    public Matrix getFrame(){
        return frames.getFrame(currentFrame);

    }

    public void nextFrame(){
        this.currentFrame++;
        if(this.currentFrame > animationRanges[currentAnimation][1]){
            this.currentFrame = animationRanges[currentAnimation][0];
        }
    }

}
