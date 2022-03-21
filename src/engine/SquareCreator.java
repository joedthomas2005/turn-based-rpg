package engine;

import java.util.ArrayList;

public class SquareCreator implements DrawableCreator{
	
	private int startIndex;
	private int numIndices;
	private TextureController textures;

	public SquareCreator(TextureController textures){
		this.textures = textures;
		this.startIndex = 0;
		this.numIndices = 0;
				
	}


		

	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getNumIndices() {
		return numIndices;
	}
	

}
