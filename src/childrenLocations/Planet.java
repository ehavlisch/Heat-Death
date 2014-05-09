package childrenLocations;

import childrenOptions.StrandedShipSmall;
import locations.Location;
import main.RNG;
import options.Option;

public class Planet extends Location {
	public Planet(String id) {
		desc = "The remains of a planet";
		detailed = "A dark planet that appears to be empty. The planet's atmosphere is intolerable.\n";
		name = "Planet " + id;

		int rand = RNG.random(100);
		if (rand < 90) {
			Option option = new StrandedShipSmall();
			this.addOption(option);
		} else {
			detailed += "\nThere is nothing on the scanner near the desolate planet.\n";
		}
	}
}
