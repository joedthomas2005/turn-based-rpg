package engine.exceptions;

public class FragmentShaderCompilationException extends ShaderCompilationException{

	private static final long serialVersionUID = 4804304024739333659L;

	public FragmentShaderCompilationException(String log) {
		super("fragment", log);
	}
}
