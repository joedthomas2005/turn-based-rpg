package engine;
//import static com.sun.jna.platform.win32.WinUser.*;
//import static com.sun.jna.platform.win32.User32.INSTANCE;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

public class InputController {
	
	private Boolean[] keys = new Boolean[GLFW_KEY_LAST];
	private Boolean firstMouse = true;
	private double lastX;
	private double lastY;
	private double xScreenCoords;
	private double yScreenCoords;
	private boolean lmbDown;
	private boolean rmbDown;
	private float pitch;
	private float yaw;
	private float sensitivity;
	
	public InputController(Window window, float sensitivity) {
		
		this.sensitivity = sensitivity;
		this.lmbDown = false;
		this.rmbDown = false;
		this.lastX = window.getWidth() / 2;
		this.lastY = window.getHeight() / 2;
		
		//glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		glfwSetKeyCallback(window.getWindow(), GLFWKeyCallback.create((event_window, key, scancode, action, mods) -> 
		{
			keys[key] = action == GLFW_PRESS ? true : action == GLFW_RELEASE ? false : keys[key];
		}));
		
	
		glfwSetCursorPosCallback(window.getWindow(), GLFWCursorPosCallback.create((event_window, x, y) -> {
			
			xScreenCoords = x;
			yScreenCoords = window.getHeight() - y;
			
			if(xScreenCoords > window.getWidth()) {
				xScreenCoords = window.getWidth();
			}
			
			if(yScreenCoords > window.getHeight()) {
				yScreenCoords = window.getHeight();
			}
			
			if(xScreenCoords < 0) {
				xScreenCoords = 0;
			}
			
			if(yScreenCoords < 0) {
				yScreenCoords = 0;
			}
			
			if (firstMouse) {
				lastX = x;
				lastY = y;
				firstMouse = false;
			}
			
		//	INSTANCE.SetCursorPos(0,0);
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
		
		glfwSetMouseButtonCallback(window.getWindow(), GLFWMouseButtonCallback.create((event_window, button, action, mods) -> {
			
			if(button == GLFW_MOUSE_BUTTON_LEFT) {
				
				if(action == GLFW_PRESS) {
					lmbDown = true;
				}
				else if(action == GLFW_RELEASE) {
					lmbDown = false;
				}
			}
			
			else if(button == GLFW_MOUSE_BUTTON_RIGHT) {
				
				if(action == GLFW_PRESS) {
					rmbDown = true;
				}
				else if(action == GLFW_RELEASE) {
					rmbDown = false;
				}
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
	
	public double getMouseX() {
		return xScreenCoords;
	}
	
	public double getMouseY() {
		return yScreenCoords;
	}
	
	public Boolean leftMouseDown() {
		return lmbDown;
	}
	
	public Boolean rightMouseDown() {
		return rmbDown;
	}
	
}
