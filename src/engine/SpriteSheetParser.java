package engine;
import java.util.ArrayList;

import matrices.Matrix;


public final class SpriteSheetParser {

    private final ArrayList<Matrix> frames = new ArrayList<Matrix>();
    
    public SpriteSheetParser(int frames, int numRows){
        
        int numPerRow = frames/numRows;
        float frameWidth = 1.0f/numPerRow;
        float frameHeight = 1.0f/numRows;

        for(int row = 0; row < numRows; row++){
            for(int frame = 0; frame < numPerRow; frame++){
                Matrix textureTranslate = Matrix.TranslationMatrix(frame * frameWidth, row * frameHeight);
                Matrix textureScale = Matrix.ScaleMatrix(frameWidth, frameHeight);
                Matrix textureCoordTransform = textureTranslate.multiply(textureScale);
                this.frames.add(textureCoordTransform);
            }
        }
    }

    public Matrix getFrame(int frame){
        int index = frame;
        while(index >= frames.size()){
            index -= frames.size();
        }

        return frames.get(index);
    }
}
