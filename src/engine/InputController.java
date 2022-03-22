package engine;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

public class InputController {
	
	private Boolean[] keys = new Boolean[GLFW_KEY_LAST];
	private Boolean[] firstPressed = new Boolean[GLFW_KEY_LAST];
	private Boolean lmbFirstPressed;
	private boolean rmbFirstPressed;
	private double xScreenCoords;
	private double yScreenCoords;
	private boolean lmbDown;
	private boolean rmbDown;
	
	
	public void reset() {
		for(int i = 0; i < GLFW_KEY_LAST; i++) {
			firstPressed[i] = false;
		}
		this.lmbFirstPressed = false;
		this.rmbFirstPressed = false;
	}
	
	public InputController(Window window) {
		
		this.lmbDown = false;
		this.rmbDown = false;
		this.lmbFirstPressed = false;
		this.rmbFirstPressed = false;
		for(int i = 0; i < GLFW_KEY_LAST; i++){
			keys[i] = false;
			firstPressed[i] = false;
		}
		//glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		glfwSetKeyCallback(window.getWindow(), GLFWKeyCallback.create((event_window, key, scancode, action, mods) -> 
		{
			try {
			keys[key] = action == GLFW_PRESS || action != GLFW_RELEASE;
			firstPressed[key] = action == GLFW_PRESS;
			}
			
			catch(Exception e) {
				System.err.println("Warning: Unknown key pressed");
			}
		}));
		
	
		glfwSetCursorPosCallback(window.getWindow(), GLFWCursorPosCallback.create((event_window, x, y) -> {
			
			xScreenCoords = x;
			yScreenCoords = window.getHeight()-y;
			
		}));
		
		glfwSetMouseButtonCallback(window.getWindow(), GLFWMouseButtonCallback.create((event_window, button, action, mods) -> {
			
			if(button == GLFW_MOUSE_BUTTON_LEFT) {
				
				if(action == GLFW_PRESS) {
					lmbDown = true;
					lmbFirstPressed = true;
				}
				else if(action == GLFW_RELEASE) {
					lmbDown = false;
				}
			}
			
			else if(button == GLFW_MOUSE_BUTTON_RIGHT) {
				
				if(action == GLFW_PRESS) {
					rmbDown = true;
					rmbFirstPressed = true;
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
	
	public double getMouseX() {
		return xScreenCoords;
	}
	
	public double getMouseY() {
		return yScreenCoords;
	}
	
	public Boolean leftMouseDown() {
		return lmbDown;
	}
	
	public Boolean leftMouseClicked() {
		return lmbFirstPressed;
	}
	
	public Boolean rightMouseDown() {
		return rmbDown;
	}
	
	public Boolean rightMouseClicked() {
		return rmbFirstPressed;
	}
	
	public Boolean isKeyFirstPressed(int key) {
		return firstPressed[key];
	}
	
}
