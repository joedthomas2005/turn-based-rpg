package game;
import engine.*;
import engine.exceptions.ShaderException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.IOException;

import matrices.Vector;
public final class Game implements Runnable{

	GameContext gameContext;

    public Game() throws ShaderException, IOException{
		this.gameContext = new GameContext();
	};


	@Override
	public void run(){
		
		try {
			gameContext.create(1920, false, "Turn Based RPG", "animated_cursor.png");
		} 
		
		catch (ShaderException | IOException e) {
			e.printStackTrace();
		}
		
		gameContext.loadTilemap("test.tlm");
		gameContext.enableCursor();
		
		while(!gameContext.getWindow().shouldClose()) {
			update();
		}
		gameContext.getWindow().destroy();
	}


	public final void update() {
		
		gameContext.startFrame();
		gameContext.endFrame();
	}
}
