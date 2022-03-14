package engine.exceptions;

import engine.GameObject;

public class DrawElementsException extends DrawException{
	private static final long serialVersionUID = -8947430576946690243L;

	public DrawElementsException(GameObject object, int err) {
		super("Could not draw object with ID " + object.getID() +
				" due to error on drawElements call. Error code is " + Integer.toString(err));
		
	}
}
