package childrenLocations;

import locations.Location;
import childrenOptions.JunkTraderFuelForScrap;
import childrenOptions.JunkTraderHullForFuel;
import childrenOptions.JunkTraderScrapForFuel;

public class JunkTrader extends Location {
	public JunkTrader(String id) 
	{
		desc = "A floating mass of parts.";
		detailed = "A junk trader's horde of random parts and relics. You could probably trade for fuel, parts, or food for high prices.\n" +
				"These traders make their living by ripping off the few people that come across them. They thrive on necessity.\n" +
				"Be careful around these type, they would trade their own family for profits.\n";
		name = "Junk Trader " + id;
		
		this.addOption(new JunkTraderScrapForFuel());
		this.addOption(new JunkTraderFuelForScrap());
		this.addOption(new JunkTraderHullForFuel());
	}
}
