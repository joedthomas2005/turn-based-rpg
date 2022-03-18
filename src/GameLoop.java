import engine.CameraController;
import engine.InputController;
import engine.Window;

public final class GameLoop {
    private GameLoop(){};
	public final static void update(Window window, InputController inputController, CameraController cameraController, double deltaTime){
		cameraController.update((float)deltaTime);
		inputController.reset();
		window.update();
	}
}
