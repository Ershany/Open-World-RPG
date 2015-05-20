package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileIO {

	//files to write to
	private static File levelFile = new File("leveldata.dat");
	
	//writing
	private static FileOutputStream fos;
	private static ObjectOutputStream oos;
	
	//reading
	private static FileInputStream fis;
	private static ObjectInputStream ois;
	
	//to save the level/xp of the player
	public static void save(LevelData data) {
		try {
			//make sure the file exists
			if(!levelFile.exists()) {
				levelFile.createNewFile();
			}
			
			fos = new FileOutputStream(levelFile);
			oos = new ObjectOutputStream(fos);
			
			//write the level/xp data to the file
			oos.writeObject(data);
			
			oos.flush();
			fos.flush();
			oos.close();
			fos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LevelData loadLevelData() {
		LevelData data = null;
		try {
			//first check that the file exists before, if it does not, return null
			if(!levelFile.exists()) return null;
			
			fis = new FileInputStream(levelFile);
			ois = new ObjectInputStream(fis);
			
			//read the level/xp data from the file
			data = (LevelData)ois.readObject();
			
			fis.close();
			ois.close();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}
