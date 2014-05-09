package locations;

import java.io.Serializable;
import java.util.ArrayList;

import childrenLocations.End;
import childrenLocations.Wormhole;

import main.Randomizer;

public class Sector implements Serializable {

	private String name;
	private int sectorId;

	private ArrayList<Location> allLocations;
	private int used;

	public int size() {
		return allLocations.size();
	}

	public Sector(String name, int id) {
		this.name = name + " Sector";
		this.sectorId = id;
		allLocations = new ArrayList<Location>();
	}
	
	public String toString() {
		return "Name: " + name + " Id: " + sectorId;
	}

	public void initializeSector() {
		for(LocationRegistry lr : LocationRegistry.values()) {
			
			int rand = Randomizer.random(lr.getBaseChance());
			int genChance = lr.getGenChance();
			while(rand <= genChance) {
				try {				
					Object o = null;
					
					if(lr.isNameNumber()) {
						o = Class.forName("childrenLocations." + lr.getClassName()).getConstructor(String.class).newInstance(Randomizer.randomNameNumber());
					} else {
						o = Class.forName("childrenLocations." + lr.getClassName()).getConstructor(String.class).newInstance(Randomizer.randomName());
					}
					allLocations.add((Location) o);
					
				} catch(Exception e) {
					System.out.println("Error dynamically creating instances.");
					e.printStackTrace();
					System.exit(-1);
				}
				
				genChance -= (lr.getBaseChance()/lr.getMaxPerSector());
				rand = Randomizer.random(lr.getBaseChance());
			}
		}
	}

	public Sector() {
		used = 0;
		allLocations = new ArrayList<Location>();

		initializeSector();
		
		// Unique Locations -- MOVE TO WORLD GEN
		//allLocations.add(new OldEarth());

	}
	
	public Location getLocation(Integer id) {
		return(allLocations.get(id));
	}

	public String locationToString(int id) {
		return allLocations.get(id).toString();
	}

	public Location getFirstLocation(int worldSize, int worldDensity) {
		
		if(allLocations.size() == 0) {
			System.out.println("Error: Sector size is 0");
			System.exit(-1);
		}
		Location current;
		
		current = allLocations.get(Randomizer.random(allLocations.size()));
		current.setUsed(LocationStatus.Visited);
		used++;

		worldDensity++;

		while (current.getNumConnections() < worldDensity) {
			Location loc = getUnusedLocation();

			if (loc.getName().equalsIgnoreCase("end")) {
				break;
			} else {
				addMutual(current, loc);
			}
		}
		return current;
	}

	private Location getUnusedLocation() {
		if (used < allLocations.size()) {
			Location loc = allLocations.get(Randomizer.random(allLocations.size()));
			int count = 0;
			while ((loc.getUsed().equals(LocationStatus.Visited) || loc.getUsed().equals(LocationStatus.Unvisited)) && count < 1000) {
				count++;
				loc = allLocations.get(Randomizer.random(allLocations.size()));
			}
			if (count < 1000) {
				loc.setUsed(LocationStatus.Unvisited);
				used++;
				return loc;
			} else {
				return new End();
			}
		} else {
			return new End();
		}

	}

	private Location getUnvisitedLocation(Location loc) {
		if (used < allLocations.size()) {
			ArrayList<Location> suitable = new ArrayList<Location>();
			for (Location l : allLocations) {
				if (l.getUsed().equals(LocationStatus.Unvisited) && l != loc) {
					boolean ok = true;
					for (Location connection : loc.getConnections()) {
						if (l == connection) {
							ok = false;
							break;
						}
					}
					if (ok) {
						suitable.add(l);
					}
				}
			}
			if (suitable.size() > 0) {
				return suitable.get(Randomizer.random(suitable.size()));
			} else {
				return null;
			}
		}
		return null;
	}

	public void addMutual(Location one, Location two) {
		one.addLocation(two);
		two.addLocation(one);
	}

	// TODO fix addConnection and addUsedConnection to better add connections
	// depending on the
	// world size and world Density variables - NOTE: the value passed to random
	// cannot be negative
	public void addConnection(Location loc, int worldSize, int worldDensity) {
		while (loc.getNumConnections() < Randomizer.random(worldDensity + worldSize)) {
			Location newLocation = getUnusedLocation();
			if (!newLocation.getName().equalsIgnoreCase("End")) {
				addMutual(loc, newLocation);
			}
		}
	}

	public void addUsedConnection(Location loc, int worldSize, int worldDensity) {
		if (loc.getNumConnections() < Randomizer.random(worldDensity + worldSize)) {
			Location newLocation = getUnvisitedLocation(loc);
			if (newLocation != null && loc != newLocation) {
				addMutual(loc, newLocation);
			}
		}
	}
	
	public Location getWormholeLocation(int wormholeId) {
		for(Location loc : allLocations) {
			if(loc.isWormhole()) {
				Wormhole wormhole = (Wormhole) loc;
				if(wormholeId == wormhole.getWormholeId()) return loc;
			}
		}
		System.out.println("ERROR: No wormhole with " + wormholeId + " found in this sector.");
		return null;
	}

	public void progress() {
		int unused = 0;
		int unvisited = 0;
		int visited = 0;

		for (int i = 0; i < allLocations.size(); i++) {
			if (allLocations.get(i).getUsed().equals(LocationStatus.Unused))
				unused++;
			if (allLocations.get(i).getUsed().equals(LocationStatus.Unvisited))
				unvisited++;
			if (allLocations.get(i).getUsed().equals(LocationStatus.Visited))
				visited++;
		}

		System.out.println("Unused: " + unused);
		System.out.println("Unvisited: " + unvisited);
		System.out.println("Visited: " + visited);
		System.out.println("World's used (should match unvisited+visited): " + used);
		System.out.println("Total (should be the sum of the above): " + allLocations.size());
	}

	public void dumpWorld() {
		Location temp;
		System.out.println("Name\t\tIsUsed");
		for (int i = 0; i < allLocations.size(); i++) {
			temp = allLocations.get(i);
			System.out.println(temp.getName() + "\t" + temp.getUsed());
		}
	}

	public String getName() {		
		return this.name;
	}

	public void addLocation(Location loc) {
		this.allLocations.add(loc);
	}

	public ArrayList<Location> getAllLocations() {
		return allLocations;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAllLocations(ArrayList<Location> allLocations) {
		this.allLocations = allLocations;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
}
