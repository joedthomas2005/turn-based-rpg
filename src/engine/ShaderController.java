package engine;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import engine.exceptions.*;

public class ShaderController {
	
	static String SHADER_DIRECTORY = "resources/shaders/";
	
	private int vShader;
	private int fShader;
	private int ID;
	
	public ShaderController(String vertShaderPath, String fragShaderPath, Window window) throws ShaderException, IOException{
		
		String vertexShaderSource = Files.readString(Path.of(SHADER_DIRECTORY + vertShaderPath));
		String fragShaderSource = Files.readString(Path.of(SHADER_DIRECTORY + fragShaderPath));
		
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
		
		glAttachShader(ID, vShader);
		glAttachShader(ID, fShader);
		glLinkProgram(ID);
		if (glGetProgrami(ID, GL_LINK_STATUS) == 0) {
			throw new ShaderLinkException(glGetShaderInfoLog(ID, 1024));
		}
		
		glDeleteShader(vShader);
		glDeleteShader(fShader);
	}
	
	public void use() {
		glUseProgram(ID);
	}
	
	public void setVec3f(String name, float x, float y, float z) {
		glUniform3f(glGetUniformLocation(ID, name), x, y, z);
	}
	
}
