import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Buffers {
	
	ArrayList<Float> vertices;
	ArrayList<Integer> indices;
	IntBuffer vao;
	IntBuffer ebo;
	IntBuffer vbo;
	
	public Buffers(ArrayList<Float> vertices, ArrayList<Integer> indices) {
		this.vertices = vertices;
		this.indices = indices;
		
		MemoryStack stack = MemoryStack.stackPush();
		this.vao = stack.mallocInt(1);
		this.vbo = stack.mallocInt(1);
		this.ebo = stack.mallocInt(1);
		
		glGenVertexArrays(vao);
		glGenBuffers(ebo);
		glGenBuffers(vbo);
		
		System.out.println("Buffers Generated");
		
		glBindVertexArray(vao.get(0));
		glBindBuffer(GL_ARRAY_BUFFER, vbo.get(0));
		float[] vertexArray = new float[vertices.size()];

		glBufferData(GL_ARRAY_BUFFER, vertices.toArray(), GL_DYNAMIC_DRAW);
		
	}
}
