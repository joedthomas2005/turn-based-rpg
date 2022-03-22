package engine;

import matrices.Matrix;

public class Animator {

    private SpriteSheetParser frames;
    private int[][] animationRanges;
    private int currentFrame;
    private int currentAnimation;

    public Animator(SpriteSheetParser parser, int[]... animationRanges){
        
        this.animationRanges = animationRanges;
        this.frames = parser;
        this.currentFrame = 0;

    }

    public void setState(int animation){
        this.currentAnimation = animation;
    }

    public Matrix getFrame(){

        int frame = currentFrame;
        while(frame < animationRanges[currentAnimation][0]){
            frame += animationRanges[currentAnimation][1] - animationRanges[currentAnimation][0];
        }
        while(frame > animationRanges[currentAnimation][1]){
            frame -= animationRanges[currentAnimation][1] - animationRanges[currentAnimation][1];
        }
        return frames.getFrame(frame);

    }

    public void nextFrame(){
        this.currentFrame++;
    }

}
