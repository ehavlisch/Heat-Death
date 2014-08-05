package childrenLocations;

import childrenOptions.StrandedShipSmall;
import locations.Location;
import main.RNG;
import main.Randomizer;
import options.Option;

public class Planet extends Location {
	public Planet(String id) {
		//desc = "A planetoid.";
		//detailed = "A dark planet that appears to be empty.\n";
		name = "Planet " + id;

		Randomizer.randomPlanet(this);
		
		int rand = RNG.random(100);
		if (rand < 90) {
			Option option = new StrandedShipSmall();
			this.addOption(option);
		} else {
			detailed += "\nThere is nothing on the scanner near the desolate planet.\n";
		}
	}
}
