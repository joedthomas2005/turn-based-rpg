import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

public class InputController {
	
	private Boolean[] keys = new Boolean[GLFW_KEY_LAST];
	private Boolean firstMouse = true;
	private double lastX;
	private double lastY;
	private float pitch;
	private float yaw;
	private float sensitivity;
	
	public InputController(Window window, float sensitivity) {
		this.sensitivity = sensitivity;
		this.lastX = window.getWidth() / 2;
		this.lastY = window.getHeight() / 2;
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		glfwSetKeyCallback(window.getWindow(), GLFWKeyCallback.create((event_window, key, scancode, action, mods) -> {
			keys[key] = action == GLFW_PRESS ? true : action == GLFW_RELEASE ? false : keys[key];
		}));
		glfwSetCursorPosCallback(window.getWindow(), GLFWCursorPosCallback.create((event_window, x, y) -> {
			
			if (firstMouse) {
				lastX = x;
				lastY = y;
				firstMouse = false;
			}
			
			double xOffset = this.sensitivity * (x - lastX);
			double yOffset = this.sensitivity * (y - lastY);
			lastX = x;
			lastY = y;
			yaw += xOffset;
			pitch += yOffset;
			
			if(pitch > 89.0f) {
				pitch = 89.0f;
			}
			
			if(pitch < -89.0f) {
				pitch = -89.0f;
			}
			
		}));
	}
	
	public Boolean isKeyDown(int key) {
		return keys[key];
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
}
