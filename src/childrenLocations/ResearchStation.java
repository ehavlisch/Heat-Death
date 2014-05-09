package childrenLocations;

import childrenOptions.BuyUpgrade;
import locations.Location;
import main.RNG;
import options.Option;

public class ResearchStation extends Location {

	public ResearchStation(String id) {
		desc = "Space Station";
		detailed = "The small station appears to be tailored for research. It seems active and inhabited. They are cautious and heavily armed.\n"
				+ "Despite the small size of the station, it's heavily shielded and could dole out a beating if attacked.\n";

		name = "Research Station " + id;

		int rand = RNG.random(100);
		if (rand < 90) {
			Option option = new BuyUpgrade();
			this.addOption(option);
		} else {
			detailed += "A voice breaks the radio silence, 'What do you want? If you don't need anything, please leave quickly.'\n";
		}
	}
}
