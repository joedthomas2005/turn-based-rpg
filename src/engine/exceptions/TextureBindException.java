package engine.exceptions;

import engine.GameObject;

public class TextureBindException extends TextureException{
	private static final long serialVersionUID = 9122528452366245979L;

	public TextureBindException(GameObject object) {
		super("Texture number " + object.getTextureID() + " loaded from " + object.getTexturePath()
		+ " failed to bind for object");
	}
}
