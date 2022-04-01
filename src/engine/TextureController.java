package engine;
import globals.PATHS;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.ByteBuffer;
import java.util.Hashtable;

import engine.exceptions.TextureLoadException;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;
import static org.lwjgl.opengl.GL30.*;

/**
 * The TextureController class contains a hashtable of all the loaded texture names and their 
 * internal GL handles. Pass all texture names to be used into the constructor (just the filename,
 * paths are defined in globals/PATHS.java) and they will be loaded 
 * and their ids stored in the hashtable.
 */
public class TextureController {
	
	private final Hashtable<String, Integer> textureNames = new Hashtable<String, Integer>();
	public TextureController() throws TextureLoadException {

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		stbi_set_flip_vertically_on_load(true);
		
		File[] textures = new File(globals.PATHS.TextureDir).listFiles(new FilenameFilter() {
			
			public boolean accept(File f, String name) {
				return name.endsWith(".png") || name.endsWith(".jpg");
			}
			
		});
		
		String[] paths = new String[textures.length];
		
		for(int tex = 0; tex < textures.length; tex++) {
			paths[tex] = textures[tex].getName();
		}
		
		
		for(String path: paths) {
			System.out.println(path);
			if(!textureNames.containsKey(path)) {
				
				int[] texWidth = new int[1];
				int[] texHeight = new int[1];
				int[] nrChannels = new int[1];
				ByteBuffer texData = stbi_load(PATHS.TextureDir + path, texWidth, texHeight, nrChannels, 4);
				texData.flip();
				int textureID = glGenTextures();
				glBindTexture(GL_TEXTURE_2D, textureID);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texWidth[0], texHeight[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
				glGenerateMipmap(GL_TEXTURE_2D);

				
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

	/**
	 * Return the handle/ID of this texture. Use the same filename you passed to the constructor.
	 * @param path the filename of the texture (not the full path)
	 * @return the int ID of the given texure name
	 */
	public int getTexture(String path) {
		System.out.println("Getting textureID for " + path);
		return textureNames.get(path);
	}
}
