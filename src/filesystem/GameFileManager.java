package filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import locations.Sector;
import locations.World;
import player.Player;

public class GameFileManager extends FileManager {
	
	private Player p;
	
	private String saveFileVersion(String str) {

		str = str.replace("\\s+","_");
		return str;
	}
	
	@SuppressWarnings("unused")
	private String displayVersion(String str) {

		str = str.replace("_","\\s+");
		return str;
	}
	
	public void fileManagerMakeGameFiles() {
		if(p!= null) {
			File f = new File("save/" + saveFileVersion(p.getName()));
			if(!f.mkdirs()) {
				System.out.println("ERROR: Failed to create save directory");
			}
		}
	}
	
	public static void fileManagerSaveGame(World w) {
		
	}
	
	public void fileManagerSaveSector(Sector s) {
		try {
			if(p!=null) {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save/" + saveFileVersion(p.getName()) + "/" + saveFileVersion(s.getName()) +".sec"));
				
				out.writeObject(s);
				out.close();				
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: Unable to save sector " + s.getName());
			e.printStackTrace();
			System.exit(-1);
		}		
	}
	
	public Sector loadSector(String name) {
		try {
			if(p!=null) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("save/" + saveFileVersion(p.getName()) + "/" + saveFileVersion(name) + ".sec"));
				Sector s = (Sector) in.readObject();
				in.close();
				return s;
			}
		} catch(Exception e) {
			System.out.println("ERROR: Unable to load sector " + name);
			e.printStackTrace();
			System.exit(-1);
		} 
		//Should never get here
		return null;
	}
	
	public void saveGameOptions(GameOptions gameOptions) {
		

		ObjectOutputStream out;
		
		try {
			out = new ObjectOutputStream(new FileOutputStream("options/gameOptions"));

			
			out.writeObject(gameOptions);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Warn: Unable to find options directory, creating now");
			File f = new File("options");
			if(!f.mkdir()) {
				System.out.println("Error: Failed to create options directory");
				return;
			} else {
				saveGameOptions(gameOptions);
			}
		} catch (IOException e) {
			System.out.println("Error: Uncaught IO Exception saving game options");
			e.printStackTrace();
		}
	}
	
	public GameOptions loadGameOptions() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("options/gameOptions"));
			GameOptions opts = (GameOptions) in.readObject();
			in.close();
			return opts;
		} catch(Exception e) {
			System.out.println("Warn: Unable to load game options");
		} 
		return null;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}
	
}
