package engine.exceptions;


@SuppressWarnings("serial")
public class ShaderLinkException extends ShaderException{
	public ShaderLinkException(String log) {
		super("Shader could not be linked. Error log is as follows: \n\n" + log);
	}
}
