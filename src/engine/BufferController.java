package engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.system.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;

public class BufferController {
	
	private ArrayList<Float> vertices;
	private ArrayList<Integer> indices;

	
	public BufferController() {
		this.vertices = new ArrayList<Float>();
		this.indices = new ArrayList<Integer>();
	}
	
	public int[] AddItem(ArrayList<Float> vertices, ArrayList<Integer> indices){

		int startIndex = this.indices.size();
		int numIndices = indices.size();
		int EBOvertexOffset = 0;
		if(this.indices.size() > 0) {
			EBOvertexOffset = Collections.max(this.indices);
		}
		System.out.println("Vertices offset by" + EBOvertexOffset);
		for(float vertex : vertices) {
			this.vertices.add(vertex);
		}
		for(int index : indices) {
			this.indices.add(index + EBOvertexOffset);
		}
		
		return new int[]{startIndex, numIndices};    
	}
	
	public void bind() {
		
		try( MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer vao = stack.mallocInt(1);
			IntBuffer vbo = stack.mallocInt(1);
			IntBuffer ebo = stack.mallocInt(1);
		
			glGenVertexArrays(vao);
			glGenBuffers(ebo);
			glGenBuffers(vbo);
		
			System.out.println("Buffers Generated");
		
			glBindVertexArray(vao.get(0));
			glBindBuffer(GL_ARRAY_BUFFER, vbo.get(0));
		
			float[] vertexArray = new float[vertices.size()];
			
			for (int i = 0; i < vertices.size(); i++) {
				vertexArray[i] = (float)vertices.get(i);
				System.out.println(vertexArray[i]);
			}
		
			glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo.get(0));
		
			int[] indexArray = new int[indices.size()];
			for (int i = 0; i < indices.size(); i++) {
				indexArray[i] = (int)indices.get(i);
				System.out.println(indexArray[i]);
			}
		
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexArray, GL_STATIC_DRAW);
		
			glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 5, 0);
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 5, Float.BYTES * 3);
			glEnableVertexAttribArray(1);
			//System.out.println("Vao character 0: " + vao.get(0));
			System.out.println("Vao = " + vao.get(0));
			glBindVertexArray(vao.get(0));
		}
	}
}
