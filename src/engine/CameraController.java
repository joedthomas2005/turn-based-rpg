package engine;
import static org.lwjgl.glfw.GLFW.*;

public class CameraController {
    private final Camera camera;
    private final ShaderController shaderController;
    private final InputController inputController;
    private float camSpeed;
    public CameraController(Camera camera, ShaderController shaderController, InputController inputController, float camSpeed){
        this.camera = camera;
        this.camSpeed = camSpeed;
        this.shaderController = shaderController;
        this.inputController = inputController;
    }

    public void update(double deltaTime){
        
        if(inputController.isKeyDown(GLFW_KEY_A)){
            camera.move(-camSpeed * deltaTime, 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_D)){
            camera.move(camSpeed * deltaTime, 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_S)){
            camera.move(0,-camSpeed * deltaTime,0);
        }
        if(inputController.isKeyDown(GLFW_KEY_W)){
            camera.move(0,camSpeed * deltaTime,0);
        }
        shaderController.setView(camera.getView());
    }

    public Camera getCamera(){
        return camera;
    }

    public void setCamSpeed(float camSpeed){
        this.camSpeed = camSpeed;
    }
    
    public ShaderController getShaderController(){
        return shaderController;
    }

    public InputController getInputController(){
        return inputController;
    }

}
