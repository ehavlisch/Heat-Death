package filesystem;

import java.io.File;

public class FileManager {

	public void fileManagerListSaves() {
		
	}
	
	public void fileManagerLoadSave(String name) {
		
	}
	
	public void fileManagerClean() {
		System.out.println("Cleaning up files in save");
		File f = new File("save");
		deleteRecursively(f);
		System.out.println("Cleaning done.");
	}
	
	private void deleteRecursively(File file) {
		try {
			for(File inner : file.listFiles()) {
				deleteRecursively(inner);
			}
		} catch(Exception e) {
			
		}
		file.delete();
	}
}
