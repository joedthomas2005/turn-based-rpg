package globals;

import java.util.Hashtable;

import engine.GameObject;

public class ID_MANAGER {
	private static int maxKey;
	private ID_MANAGER() {
	}
	
	private static Hashtable<Integer, GameObject> GameObjects = new Hashtable<Integer, GameObject>();
	
	public static void NewObject(GameObject object) {
		GameObjects.put(object.getID(), object);
		System.out.println("NEW OBJECT ID " + object.getID());
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
		System.out.println("NEW ID REQUESTED. GIVING " + (maxKey + 1));
		return maxKey + 1;
	}
}
