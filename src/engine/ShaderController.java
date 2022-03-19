package engine;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import engine.exceptions.*;
import globals.PATHS;
import matrices.Matrix;

public class ShaderController {
	
	private final int ID;
	private final Matrix projection;
	private final int viewID;
	private final int projectionID;
	private final int transformID;
	
	public ShaderController(String vertShaderPath, String fragShaderPath, Window window) throws ShaderException, IOException{
		
		String vertexShaderSource = Files.readString(Path.of(PATHS.ShaderDir + vertShaderPath));
		String fragShaderSource = Files.readString(Path.of(PATHS.ShaderDir + fragShaderPath));
		
		int vShader = glCreateShader(GL_VERTEX_SHADER);
		int fShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(vShader, vertexShaderSource);
		glShaderSource(fShader, fragShaderSource);
		
		glCompileShader(vShader);
		if (glGetShaderi(vShader, GL_COMPILE_STATUS) == 0) {
			throw new VertexShaderCompilationException(glGetShaderInfoLog(vShader,1024));
		}
		
		glCompileShader(fShader);
		if(glGetShaderi(fShader, GL_COMPILE_STATUS) == 0) {
			throw new FragmentShaderCompilationException(glGetShaderInfoLog(fShader, 1024));
		}
		
		this.ID = glCreateProgram();
		System.out.println("Shader ID = " + this.ID);
		glAttachShader(ID, vShader);
		glAttachShader(ID, fShader);
		glLinkProgram(ID);
		if (glGetProgrami(ID, GL_LINK_STATUS) == 0) {
			throw new ShaderLinkException(glGetShaderInfoLog(ID, 1024));
		}
		
		glDeleteShader(vShader);
		glDeleteShader(fShader);
		this.projection = Matrix.OrthographicMatrix(0, window.getWidth(), 0, window.getHeight(), 100, -100);
		System.out.println(this.projection.toString());
		glUseProgram(ID);
		viewID = glGetUniformLocation(ID, "view");
		projectionID = glGetUniformLocation(ID, "projection");
		transformID = glGetUniformLocation(ID, "transform");
	}
	

	public void setView(Matrix view) {
		setMat4f("view", view);
		setMat4f("projection", projection);
	}
	
	public void setVec3f(String name, float x, float y, float z) {
		glUniform3f(glGetUniformLocation(ID, name), x, y, z);
	}

	public void setMat4f(String name, Matrix matrix) {
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); 

		for(float[] row : matrix.data){
			for(float column: row){
                matrixBuffer.put(column);
            }
		}
    
		switch(name) {
			
		case "view":
			glUniformMatrix4fv(viewID, false, matrixBuffer);
			break;
		case "projection":
			glUniformMatrix4fv(projectionID, false, matrixBuffer);
			break;
		case "transform":
			glUniformMatrix4fv(transformID, false, matrixBuffer);
			break;
		default:
			System.err.println("Unknown Uniform Name.");
			break;
		
		}
		matrixBuffer.clear();
	}
	
	public Matrix getProjection(){
		return this.projection;
	}
	
}
