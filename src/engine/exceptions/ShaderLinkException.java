package engine.exceptions;


public class ShaderLinkException extends ShaderException{

	private static final long serialVersionUID = 6650328211567436029L;

	public ShaderLinkException(String log) {
		super("Shader could not be linked. Error log is as follows: \n\n" + log);
	}
}
