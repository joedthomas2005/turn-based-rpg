package engine.exceptions;
@SuppressWarnings("serial")
public class FragmentShaderCompilationException extends ShaderCompilationException{
	public FragmentShaderCompilationException(String log) {
		super("fragment", log);
	}
}
