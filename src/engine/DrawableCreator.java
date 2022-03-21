package engine;

public interface DrawableCreator {

    public void initialise(BufferController buffers);
    public DrawableGameObject create(float x, float y, float rotation, float xScale, float yScale, String texture, int numFrames, int numRows);

}
