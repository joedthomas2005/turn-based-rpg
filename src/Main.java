import java.io.IOException;

import javax.crypto.AEADBadTagException;

import engine.SpriteSheetParser;
import matrices.Matrix;
import matrices.Vector;

public class Main {
	
	public static void main(String[] args) throws IOException {

		Game game = new Game();
		game.run();

	}
}