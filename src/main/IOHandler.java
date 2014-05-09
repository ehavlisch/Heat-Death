package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class IOHandler {
	
	public static String getInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = "";
	
		try {
			str = br.readLine();
		}catch (IOException ioe) {
			System.out.println("IO error");
			System.exit(1);
		}
		return str;
	}
	
	public static int getIndex(String str) {
		int index = -1;
		try { 
			index = Integer.parseInt(str);
		} catch(Exception e) {
			index = -1;
		}
		return index;
	}
	
	public static HashMap<Integer, String> createOptionsTable(List<String> options) {
		int index = 0;
		HashMap<Integer, String> optionsMap = new HashMap<Integer, String>(options.size());
		for(String option : options) {
			optionsMap.put(index, option);
			index++;
		}
		return optionsMap;
	}
	
	public static HashMap<Integer, String> createOptionsTable(String[] options) {
		HashMap<Integer, String> optionsMap = new HashMap<Integer, String>(options.length);
		for(int index = 0; index < options.length; index++) {
			optionsMap.put(index, options[index]);
		}
		return optionsMap;
	}
	
	public static int getIndex(HashMap<Integer, String> options) {
		
		for(Map.Entry<Integer, String> pair : options.entrySet()) {
			System.out.println(pair.getKey() + " -- " + pair.getValue());
			pair.setValue(pair.getValue().toLowerCase());
		}
		
		int index = -1;
		while(index == -1 || index >= options.size()) {
			String inputString = getInput();
			
			try { 
				index = Integer.parseInt(inputString);
			} catch(Exception e) {
				if(inputString != null && !inputString.equalsIgnoreCase("") && inputString.length() > 2) {
					for(Map.Entry<Integer, String> pair : options.entrySet()) {
						if(pair.getValue().contains(inputString.toLowerCase())) {
							return pair.getKey();
						}
					}
				} else {
					System.out.println("Input String must be 3 or more characters");
				}
			}
			if(index >= options.size()) {
				System.out.println("Invalid Integer choice. 0 - " + (options.size()-1) + " is allowed.");
			}
			
		}
		
		return index;
	}
	
	public static int[] getIndexMultiple(HashMap<Integer, String> options, boolean multiple) {
		
		
		for(Map.Entry<Integer, String> pair : options.entrySet()) {
			System.out.println(pair.getKey() + " -- " + pair.getValue());
			pair.setValue(pair.getValue().toLowerCase());
		}
		System.out.println(options.size() + " -- Finished");
		options.put(options.size(), "finished");
		
		HashSet<Integer> selected = new HashSet<Integer>(options.size());
		while(multiple) {
			int index = -1;
			while(index == -1 || index >= options.size()) {
				String inputString = getInput();
				
				try { 
					index = Integer.parseInt(inputString);
				} catch(Exception e) {
					if(inputString != null && !inputString.equalsIgnoreCase("") && inputString.length() > 2) {
						for(Map.Entry<Integer, String> pair : options.entrySet()) {
							if(pair.getValue().contains(inputString.toLowerCase())) {
								if(!selected.add(pair.getKey())) {
									selected.remove(pair.getKey());
									System.out.println("Removed Selection");
								} else {
									System.out.println("Added Selection");
								}
								
							}
						}
						System.out.println("Unable to match string.");
						continue;
					} else {
						System.out.println("Input String must be 3 or more characters");
					}
				}
				if(index >= options.size()) {
					System.out.println("Invalid Integer choice. 0 - " + (options.size()-1) + " is allowed.");
				} else if(index == options.size() - 1) {
					multiple = false;
					break;
				} else {
					if(!selected.add(index)) {
						selected.remove(index);
						System.out.println("Removed Selection");
					} else {
						System.out.println("Added Selection");
					}
				}				
			}
		}	
		
		int[] selectedIndexes = new int[selected.size()];
		int index = 0;
		for(Integer sel : selected) {
			selectedIndexes[index] = sel;
			index++;
		}
		return selectedIndexes;
	}
}
