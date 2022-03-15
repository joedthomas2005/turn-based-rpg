package engine.exceptions;

public class CustomException extends Exception{
	private static final long serialVersionUID = -6794596399895219527L;

	protected static String titleCase(String string) {
		StringBuilder stringBuilder = new StringBuilder(string);
		stringBuilder.setCharAt(0, Character.toUpperCase(string.charAt(0)));
		return stringBuilder.toString();
	}
	
	public CustomException(String exception) {
		super(exception);
	}
}
