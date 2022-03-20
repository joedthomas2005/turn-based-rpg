package engine.exceptions;

public class TextureBindException extends TextureException{
	private static final long serialVersionUID = 9122528452366245979L;

	public TextureBindException(String path, int ID) {
		super("Texture number " + ID + " loaded from " + path
		+ " failed to bind for object");
	}
}
