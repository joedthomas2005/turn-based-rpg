package engine;
import static org.lwjgl.glfw.GLFW.*;

public class CameraController {
    private Camera camera;
    private ShaderController shaderController;
    private InputController inputController;
    private float camSpeed;
    public CameraController(Camera camera, ShaderController shaderController, InputController inputController, int camSpeed){
        this.camera = camera;
        this.camSpeed = camSpeed;
        this.shaderController = shaderController;
        this.inputController = inputController;
    }

    public void update(){
        
        if(inputController.isKeyDown(GLFW_KEY_A)){
            camera.move(-camSpeed, 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_D)){
            camera.move(camSpeed, 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_S)){
            camera.move(0,-camSpeed,0);
        }
        if(inputController.isKeyDown(GLFW_KEY_W)){
            camera.move(0,camSpeed,0);
        }
        shaderController.setView(camera.getView());
    }

    public Camera getCamera(){
        return camera;
    }

    public ShaderController getShaderController(){
        return shaderController;
    }

    public InputController getInputController(){
        return inputController;
    }

}
