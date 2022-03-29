package engine;
import static org.lwjgl.glfw.GLFW.*;

import matrices.exceptions.MatrixSizeMismatchException;

public class CameraController {
    private final Camera camera;
    private final InputController inputController;
    private float camSpeed;
    
    public CameraController(Camera camera, InputController inputController, float camSpeed){
        this.camera = camera;
        this.camSpeed = camSpeed;
        this.inputController = inputController;
    }

    public void update(double deltaTime) throws MatrixSizeMismatchException{
        
        if(inputController.isKeyDown(GLFW_KEY_A)){
            camera.move((float) (-camSpeed * deltaTime), 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_D)){
            camera.move((float) (camSpeed * deltaTime), 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_S)){
            camera.move(0,(float) (-camSpeed * deltaTime),0);
        }
        if(inputController.isKeyDown(GLFW_KEY_W)){
            camera.move(0,(float) (camSpeed * deltaTime),0);
        }
    }
    

    public void setCamSpeed(float camSpeed){
        this.camSpeed = camSpeed;
    }

}
