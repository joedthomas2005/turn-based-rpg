package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import globals.PATHS;

public class TileMap {

    private ArrayList<Actor> tiles = new ArrayList<Actor>();

    public TileMap(String path, ActorManager actorManager){
        String fullPath = PATHS.TileMapDir + path;

        try {
            List<String> tileMapData = Files.readAllLines(Path.of(fullPath));
            float tileWidth = Integer.parseInt(tileMapData.get(0));
            float tileHeight = Integer.parseInt(tileMapData.get(1));
            int tileMapWidth = Integer.parseInt(tileMapData.get(2));
            int tileMapHeight = Integer.parseInt(tileMapData.get(3));
            String tileTexture = tileMapData.get(4);

            for(int x = 0; x < tileMapWidth; x++){
                for(int y = 0; y < tileMapHeight; y++){
                    tiles.add(actorManager.create(tileWidth * x, tileHeight * y, 0, tileWidth, tileHeight, tileTexture));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Could not read tileMap data.");
            e.printStackTrace();
        }

    }

    public void draw(){
        for(Actor tile : tiles){
            tile.draw();
        }
    }
}
