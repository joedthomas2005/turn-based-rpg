package engine;
import static org.lwjgl.glfw.GLFW.*;

public class CameraController {
    private Camera camera;
    private ShaderController shaderController;
    private InputController inputController;

    public CameraController(Camera camera, ShaderController shaderController, InputController inputController){
        this.camera = camera;
        this.shaderController = shaderController;
        this.inputController = inputController;
    }

    public void update(){
        
        if(inputController.isKeyDown(GLFW_KEY_A)){
            camera.move(-0.01f, 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_D)){
            camera.move(0.01f, 0, 0);
        }
        if(inputController.isKeyDown(GLFW_KEY_S)){
            camera.move(0,-0.01f,0);
        }
        if(inputController.isKeyDown(GLFW_KEY_W)){
            camera.move(0,0.01f,0);
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
