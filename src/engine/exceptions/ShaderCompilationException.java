package engine.exceptions;

@SuppressWarnings("serial")
public class ShaderCompilationException extends ShaderException{
	
		public ShaderCompilationException(String type, String log) {
			
			super(titleCase(type) + " shader failed to compile. Error log is as follows: \n\n" + log);
			
		}
}
