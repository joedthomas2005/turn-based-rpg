package engine.exceptions;

public class ShaderCompilationException extends ShaderException{
	
	private static final long serialVersionUID = 5940992046301563514L;

		public ShaderCompilationException(String type, String log) {
			
			super(titleCase(type) + " shader failed to compile. Error log is as follows: \n\n" + log);
			
		}
}
