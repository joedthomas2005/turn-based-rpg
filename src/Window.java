import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.*;

import java.nio.*;
import static org.lwjgl.opengl.GL11.*;


public class Window {
	
	private int vsync;
	private long window;
	private long monitor;
	private IntBuffer pWidth;
	private IntBuffer pHeight;
	
	private CharSequence title;
	
	public Window(int width, int height, CharSequence title, int vsync, Boolean fullScreen) {
		
		this.title = title;
		this.vsync = vsync;
		
		MemoryStack stack = MemoryStack.stackPush();
		pWidth = stack.mallocInt(1);
		pHeight = stack.mallocInt(1);
		
		this.monitor = 0;
		this.window = 0;
		
		if(fullScreen) {
			this.monitor = glfwGetPrimaryMonitor();
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		this.window = glfwCreateWindow(width, height, this.title, monitor, 0);
		
		if(this.window == 0) {
			System.err.println("GLFW FAILED LOADING WINDOW");
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(this.vsync);
		glfwGetFramebufferSize(window, pWidth, pHeight);
		
		glfwSetFramebufferSizeCallback(window, GLFWFramebufferSizeCallback.create((windowPointer, newWidth, newHeight) -> {	//Pogger.
			this.pWidth.clear();
			this.pWidth.put(newWidth);
			this.pHeight.clear();
			this.pHeight.put(newHeight);
			glViewport(0,0,newWidth,newHeight);
			}));
		
		GL.createCapabilities();
	}
	
	public void initViewport() {
		glViewport(0, 0, this.pWidth.get(0), this.pHeight.get(0));
	}
	
	public void setColor(float r, float g, float b, float a) {
		glClearColor(r,g,b,a);
	}
	
	public Boolean shouldClose() {
		return glfwWindowShouldClose(this.window);
	}
	
	public long getWindow() {
		return this.window;	
	}
	
	public void update() {
		glfwSwapBuffers(this.window);
		glfwPollEvents();
	}
	
	public void destroy() {
		glfwDestroyWindow(this.window);
		glfwTerminate();
		
	}
	
	public float getWidth() {
		return pWidth.get(0);
	}
	
	public float getHeight() {
		return pHeight.get(0);
	}
	
}
