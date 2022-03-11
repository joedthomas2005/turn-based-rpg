package engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.system.*;

import java.nio.IntBuffer;
import java.util.ArrayList;

public class BufferController {
	
	ArrayList<Float> vertices;
	ArrayList<Integer> indices;
	IntBuffer vao;
	IntBuffer ebo;
	IntBuffer vbo;
	
	public BufferController() {
		this.vertices = new ArrayList<Float>();
		this.indices = new ArrayList<Integer>();
	}
	
	public int[] AddItem(ArrayList<Float> vertices, ArrayList<Integer> indices){
		int startIndex = this.vertices.size();
		object.
	}
	
	public void bind() {
		
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
		for (int i = 0; i < vertices.size(); i++) {
			vertexArray[i] = (float)vertices.get(i);
			
		}
		
		glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo.get(0));
		
		int[] indexArray = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			indexArray[i] = (int)indices.get(i);
		}
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexArray, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 8, 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, 3, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 3);
		glEnableVertexAttribArray(1);
		
		glVertexAttribPointer(2, 2, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 6);
		glEnableVertexAttribArray(2);
		
		glBindVertexArray(vao.get(0));
	}
}
