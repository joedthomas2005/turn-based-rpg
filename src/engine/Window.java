package engine;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.*;

import java.nio.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * This window class is effectively a wrapper for the openGL window handle with a few
 * getters and setters for different display properties and an update function to hide the 
 * GL calls.
 */
public class Window {
	
	private int swapInterval;
	private long window;
	private int width;
	private int height;
	
	/**
	 * Create a new window with the given width and a 16:9 aspect ratio.
	 * @param width the width of the window to be created
	 * @param title the window title (only visible in non-fullscreen mode)
	 * @param swapInterval the value to pass to glfwSwapInterval.
	 * 0 = No FPS Cap, 1 = Monitor refresh rate, 2 = Half of monitor refresh rate
	 * @param fullScreen whether the window should be made fullscreen
	 */
	public Window(int width, CharSequence title, int swapInterval, boolean fullScreen) {
		
		this.swapInterval = swapInterval;
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
		
			long monitor = 0;
			this.window = 0;
		
			if(fullScreen) {
				monitor = glfwGetPrimaryMonitor();
			}
		
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
			glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
			System.out.println("create window now");
            this.window = glfwCreateWindow(width, width * 9/16, title, monitor, 0);
	        System.out.println("window created");	
			if(this.window == 0) {
				System.err.println("GLFW FAILED LOADING WINDOW");
			}
		
			glfwMakeContextCurrent(window);
			glfwSwapInterval(this.swapInterval);
			glfwGetFramebufferSize(window, pWidth, pHeight);
			this.width = pWidth.get();
			this.height = pHeight.get();
			System.out.println(height);
			glfwSetFramebufferSizeCallback(window, GLFWFramebufferSizeCallback.create((windowPointer, newWidth, newHeight) -> {
				this.width = newWidth;
				this.height = newHeight;
				glViewport(0,0,newWidth,newHeight);
				}));
		}
		GL.createCapabilities();
	}
	
	/**
	 * Sets the window color in the GL_COLOR_BUFFER_BIT
	 * @param r the red value
	 * @param g the green value
	 * @param b the blue value
	 * @param a the alpha (transparency) value
	 */
	public void setColor(float r, float g, float b, float a) {
		glClearColor(r,g,b,a);
	}
	
	/**
	 * Set the glfwSwapInterval value.
	 * @param swapInterval the swap interval to use (0 = no fps cap, 
	 * 1 = monitor refresh rate cap, 2 = half monitor refresh rate cap)
	 */
	public void setSwapInterval(int swapInterval){
		this.swapInterval = swapInterval;
		glfwSwapInterval(swapInterval);
	}

	/**
	 * Return whether the glfwWindowShouldClose flag is set for this window
	 * @return a boolean of whether the window should close
	 */
	public Boolean shouldClose() {
		return glfwWindowShouldClose(this.window);
	}
	
	/**
	 * Return the glfw handle to this window (its ID).
	 * @return the long which holds this windows handle
	 */
	public long getWindow() {
		return this.window;	
	}

	/**
	 * Swap the buffers and poll events for this window. 
	 * (If this isn't the last call in your render loop something is probably wrong).
	 */
	public void update() {
		
		glfwSwapBuffers(this.window);
		glfwPollEvents();
	}
	
	/**
	 * Destroy this window and terminate glfw.
	 * Call this at the end of the program.
	 */
	public void destroy() {
		glfwDestroyWindow(this.window);
		glfwTerminate();
		
	}
	
	/**
	 * Return the width in pixels of this window.
	 * @return the int width of the window.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returh the height in pixels of this window.
	 * @return the int height of the window.
	 */
	public int getHeight() {
		return height;
	}
	
}
