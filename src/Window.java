import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;

public class Window {
	
	private int width;
	private int height;
	private int vsync;
	private long window;
	private long monitor;
	private String title;
	
	public void window(int width, int height, String title, int vsync, Boolean fullScreen) {
		
		this.width = width;
		this.height = height;
		this.title = title;
		this.vsync = vsync;
		
		ByteBuffer title_as_bytestring = ByteBuffer.wrap(title.getBytes());
		
		this.monitor = 0;
		this.window = 0;
		
		if(fullScreen) {
			this.monitor = glfwGetPrimaryMonitor();
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		this.window = glfwCreateWindow(width, height, title_as_bytestring, monitor, 0);
		
		if(this.window == 0) {
			System.err.println("GLFW FAILED LOADING WINDOW");
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(vsync);
		glfwGetFramebufferSize()
	}
}
