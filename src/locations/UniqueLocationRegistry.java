package locations;

import java.util.ArrayList;
import java.util.List;

import main.Randomizer;

import childrenLocations.OldEarth;

public class UniqueLocationRegistry {
	private List<Location> locations;
	private int locationCount;
	
	public UniqueLocationRegistry() {
		this.locations = new ArrayList<Location>();
		//TODO try to fetch modded unique locations from a file or something
		addLocation(new OldEarth());
		
		locationCount = locations.size();
	}

	public List<Location> getLocations() {
		return locations;
	}
	
	public void addLocation(Location loc) {
		locations.add(loc);
	}

	public List<Location> getSomeLocations(int worldSize) {
		if(locations.size() > 0) {
			List<Location> partialLocations = new ArrayList<Location>();
			// Ideally return list<location> that contains 1/worldSize of the elements in the unique locations
			for(int i = 0; i < locationCount/worldSize + 1; i++) {
				int rand = Randomizer.random(locations.size());
				partialLocations.add(locations.get(rand));
				locations.remove(rand);
			}
			
			return partialLocations;
		} else {
			return null;
		}
	}
}
