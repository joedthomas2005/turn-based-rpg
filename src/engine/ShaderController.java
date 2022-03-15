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

public class ShaderController {
	
	private int vShader;
	private int fShader;
	private int ID;
	private Matrix4f projection;
	
	public ShaderController(String vertShaderPath, String fragShaderPath, Window window) throws ShaderException, IOException{
		
		String vertexShaderSource = Files.readString(Path.of(PATHS.ShaderDir + vertShaderPath));
		String fragShaderSource = Files.readString(Path.of(PATHS.ShaderDir + fragShaderPath));
		
		vShader = glCreateShader(GL_VERTEX_SHADER);
		fShader = glCreateShader(GL_FRAGMENT_SHADER);
		
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
		
		this.projection = new Matrix4f().perspective((float)Math.toRadians(45), window.getWidth()/window.getHeight(), 0.1f, -2 * window.getWidth());
	}
	
	public void use(Matrix4f view) {
		glUseProgram(ID);
		setMat4f("view", view);
		setMat4f("projection", projection);
	}
	
	public void setVec3f(String name, float x, float y, float z) {
		glUniform3f(glGetUniformLocation(ID, name), x, y, z);
	}

	public void setMat4f(String name, Matrix4f matrix) {
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		matrix.get(matrixBuffer);
	
		glUniformMatrix4fv(glGetUniformLocation(ID, name), false, matrixBuffer);
	}
	
	
}
