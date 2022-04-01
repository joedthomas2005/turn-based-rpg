package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import globals.PATHS;
import matrices.Vector;

public class TileMap {

    private ArrayList<Actor> tiles = new ArrayList<Actor>();
    private ActorManager actorManager;
    
    public TileMap(String path, ActorManager actorManager){
    	this.actorManager = actorManager;
        String fullPath = PATHS.TileMapDir + path;

        try {
            
            List<String> tileMapData = Files.readAllLines(Path.of(fullPath));
            float tileWidth = Integer.parseInt(tileMapData.get(0));
            float tileHeight = Integer.parseInt(tileMapData.get(1));
            int tileMapWidth = Integer.parseInt(tileMapData.get(2));
            int tileMapHeight = Integer.parseInt(tileMapData.get(3));
            String defaultTexture = tileMapData.get(4);
            HashMap<String, String> nonDefaults = new HashMap<String, String>();
            
            for(int i = 5; i < tileMapData.size(); i++){
                
                String line = tileMapData.get(i);
                System.out.println("parsing " + line);
                String coords =  line.split(":")[0].trim();
                String texture = line.split(":")[1].trim();
                String xRange = coords.split(",")[0].trim();
                String yRange = coords.split(",")[1].trim();

                ArrayList<Integer> xInRange = new ArrayList<Integer>();
                ArrayList<Integer> yInRange = new ArrayList<Integer>();

                if(xRange.contains("-")){
                    int xStart = Integer.parseInt(xRange.split("-")[0]);
                    int xEnd = Integer.parseInt(xRange.split("-")[1]);
                    for(int x = xStart; x < xEnd; x++){
                        xInRange.add(x);
                    }
                }
                else{
                    xInRange.add(Integer.parseInt(xRange));
                }

                if(yRange.contains("-")){
                    int yStart = Integer.parseInt(yRange.split("-")[0]);
                    int yEnd = Integer.parseInt(yRange.split("-")[1]);
                    for(int y = yStart; y < yEnd; y++){
                        yInRange.add(y);
                    }
                }

                else{
                    yInRange.add(Integer.parseInt(yRange));
                }
                
                for(int x : xInRange){
                    for(int y : yInRange){
                        nonDefaults.put(Integer.toString(x)+","+Integer.toString(y), texture);
                    }
                }
            }

            for(int x = 0; x < tileMapWidth; x++){
                for(int y = 0; y < tileMapHeight; y++){
                	
                	String texture = "";
                	
                	if(nonDefaults.containsKey(Integer.toString(x)+","+Integer.toString(y))) {
                		texture = nonDefaults.get(Integer.toString(x)+","+Integer.toString(y));
                	}
                	else {
                		texture = defaultTexture;
                	}
                	
                    tiles.add(actorManager.create(tileWidth * x, tileHeight * y, 0, tileWidth, tileHeight, texture));

                }
            }
            
        }
        
        catch (IOException e) {
            System.err.println("Could not open tilemap file.");
        }
        catch(NumberFormatException e) {
        	System.err.println("Could not parse tilemap data. Likely syntax error.");
        	
        }

    }

    public void draw(){
        for(Actor tile : tiles){
            actorManager.draw(tile);
        }
    }
}
