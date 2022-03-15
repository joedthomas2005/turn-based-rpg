package engine.exceptions;

public class VertexShaderCompilationException extends ShaderCompilationException{
	private static final long serialVersionUID = 1605463012009882997L;

	public VertexShaderCompilationException(String log) {
		super("vertex", log);
	}
}
