package game;
import engine.*;
import engine.exceptions.ShaderException;
import matrices.Vector;

import java.io.IOException;

public final class Game implements Runnable{

	GameContext gameContext;

    public Game() throws ShaderException, IOException{
		this.gameContext = new GameContext();
	};


	@Override
	public void run(){
		
		try {
			gameContext.create(1366, true, "Turn Based RPG", "animated_cursor.png");
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
		
		Cursor cursor = gameContext.getCursor();
		InputController input = gameContext.getInputController();

		float x = cursor.getActor().getX();
		float y = cursor.getActor().getY();

		if(input.leftMouseClicked()){
		
			gameContext.addActor(
				gameContext.createRect(x, y, 0, 100, 100, "placeholder.png")
			);

		}
		
		gameContext.endFrame();
	}
}
