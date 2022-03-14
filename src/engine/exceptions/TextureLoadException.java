package engine.exceptions;

public class TextureLoadException extends TextureException{
	private static final long serialVersionUID = -4803640143208315083L;

	public TextureLoadException(String path, String exception) {
		super("Exception while loading texture at " + path + ": " + exception);
	}
}
