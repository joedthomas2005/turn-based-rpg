package engine;

import java.nio.ByteBuffer;
import java.util.Hashtable;

import engine.exceptions.TextureLoadException;
import globals.PATHS;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.opengl.GL30.*;

public class TextureController {
	private Hashtable<String, Integer> textureNames = new Hashtable<String, Integer>();
	public TextureController(String... paths) throws TextureLoadException {
		for(String path: paths) {
			
			if(!textureNames.containsKey(path)) {
				
				int[] texWidth = new int[1];
				int[] texHeight = new int[1];
				int[] nrChannels = new int[1];
				ByteBuffer texData = stbi_load(PATHS.TextureDir + path, texWidth, texHeight, nrChannels, 3);
				texData.flip();
				int textureID = glGenTextures();
				glBindTexture(GL_TEXTURE_2D, textureID);
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texWidth[0], texHeight[0], 0, GL_RGB, GL_UNSIGNED_BYTE, texData);
				glGenerateMipmap(GL_TEXTURE_2D);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				
				int err = glGetError();
				if(err != 0) {
					throw new TextureLoadException(path, Integer.toString(err));
				}
				
				stbi_image_free(texData);
				texData.clear();
				textureNames.put(path, textureID);
				
			}
		}
	}
	
	public int getTexture(String path) {
		return textureNames.get(path);
	}
}
