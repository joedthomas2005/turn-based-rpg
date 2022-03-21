package engine;

import java.util.ArrayList;

public class SquareCreator implements DrawableCreator{

    private int startIndex;
    private int numIndices;
    private final TextureController textures;

    public SquareCreator(TextureController textures){
        this.textures = textures;
    }

    @Override
    public void initialise(BufferController buffers) {

        ArrayList<Float> vertices = new ArrayList<Float>();
        ArrayList<Integer> indices = new ArrayList<Integer>();

        for(int w = 1; w >= -1; w -= 2) {
            for (int h = 1; h >= -1; h -=2) {
                vertices.add(w * 1f);
                vertices.add(h * 1f);
                vertices.add(0f);

                if(w < 0) {
                    vertices.add(0f);
                }
                else {
                    vertices.add(1f);
                }
                if(h < 0f) {
                    vertices.add(0f);
                }
                else {
                    vertices.add(1f);
                }
            }
        }
        for(int i : new int[]{2,3,1,2,1,0}){
            indices.add(i);
        }
        int[] data = buffers.AddItem(vertices, indices);
        startIndex = data[0];
        numIndices = data[1];
        System.out.println("Square shape initialised. startIndex = " + startIndex + ", numIndices = " + numIndices);
    }

    @Override
    public DrawableGameObject create(float x, float y, float rotation, float xScale, float yScale, String texture, int numFrames, int numRows){
        return new DrawableGameObject(x, y, 0, 0, 0, rotation, xScale, yScale, textures, texture, startIndex, numIndices, numFrames, numRows);
    }
}
