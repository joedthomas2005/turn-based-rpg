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
		this.gameContext = new gameContext(1920);
	};


	@Override
	public void run(){
		time = glfwGetTime();
		lastTime = glfwGetTime();
		deltaTime = 0;
		
		curFrame = 0;
		while(!window.shouldClose()){
			update();
		}

		window.destroy();		
	}


	public final void update() {
		
		curFrame++;
		time = glfwGetTime();
		deltaTime = (time - lastTime) * 100;
		
		glClear(GL_COLOR_BUFFER_BIT);
		camera.bindView(shaderController);
		//Draw loop
		tileMap.draw();
		cursor.draw();

		if(inputController.leftMouseClicked()){
			cursor.click();
		}

		if(inputController.isKeyDown(GLFW_KEY_ESCAPE)){
			glfwSetWindowShouldClose(window.getWindow(), true);
		}

		if(curFrame % 10 == 0){
			cursor.cursor.animate();
		}
	
		cameraController.update((float)deltaTime);
		inputController.reset();
		window.update();
		lastTime = time;
	}
}
