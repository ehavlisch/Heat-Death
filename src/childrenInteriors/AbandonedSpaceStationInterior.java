package childrenInteriors;

import interiors.Interior;
import locations.Location;


public class AbandonedSpaceStationInterior extends Interior {

	public AbandonedSpaceStationInterior(String name, int rand, Location parent) {
		desc = "";
		detailed = "";
		this.name = name + " Interior";
		this.setEnergyCost(5);
		this.addLocation(parent);
		
		if(rand < 30 ) {
			detailed += "Once inside the station, you see a vast assortment of old technology and outdated objects.\n";
		} else if(rand < 60) {
			detailed += "You ignore the warning and enter anyway. There are medical quarantine tarps installed all over.\n" +
					"You immediately begin rethinking your decision after you stumble onto a pile of body bags.\n" +
					"You have accidentally entered the makeshift morgue and you're right next to the triage station.\n" +
					"Bodies are still open on the table and rotting you and the smell is nauseating.\n";
		} else if(rand < 90) {
			detailed += "The oxygen containment systems have all failed and you must keep your suit on to enter.\n" +
					"There are bodies and other gore floating all around and traversing the area is quite unpleasant.\n";
		} else {
			detailed += "You're inside the space station.\n";
		}
	}
}
