package globals;

import java.util.ArrayList;
import java.util.Hashtable;

import engine.GameObject;

public class ID_MANAGER {
	private static int maxKey;
	private ID_MANAGER() {
	}
	
	private static Hashtable<Integer, GameObject> GameObjects = new Hashtable<Integer, GameObject>();
	
	public static void NewObject(GameObject object) {
		GameObjects.put(object.getID(), object);
	}
	
	public static GameObject GetObjectById(int id) {
		return GameObjects.get(id);
	}
	
	public static Hashtable<Integer, GameObject> GetGameObjects(){
		return GameObjects;
	}
	
	public static int RequestID() {
		GameObjects.keys().asIterator().forEachRemaining((key)->{
			if(key > maxKey){
				maxKey = key;
			}
		});
		return maxKey + 1;
	}
}
