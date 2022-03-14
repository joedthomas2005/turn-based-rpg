package engine.exceptions;

public class VertexShaderCompilationException extends ShaderCompilationException{
	public VertexShaderCompilationException(String log) {
		super("vertex", log);
	}
}
