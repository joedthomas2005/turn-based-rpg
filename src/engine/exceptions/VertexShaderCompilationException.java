package engine.exceptions;

@SuppressWarnings("serial")
public class VertexShaderCompilationException extends ShaderCompilationException{
	public VertexShaderCompilationException(String log) {
		super("vertex", log);
	}
}
