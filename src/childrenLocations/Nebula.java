package childrenLocations;

import java.util.ArrayList;

import childrenOptions.GenericSearch;
import childrenOptions.SearchOption;
import childrenOptions.SearchOptionContainer;

import locations.Location;
import main.RNG;

import ship.ShipUpdate;

public class Nebula extends Location {

		public Nebula(String id) {
			desc = "The birthplace of the heavens";
			detailed = "A massive cloud of various elements and particles. With time they will form into larger constructs.\n";
			if(RNG.random(100) > 50) {
				name = id + " nebula";
			} else {
				name = "Nebula " + id;
			}
			
			ArrayList<SearchOption> searchOptions = new ArrayList<SearchOption>(2);
			int scrap = RNG.random(15) + 5;
			searchOptions.add(new SearchOption(null, new ShipUpdate(-1, 0, scrap), 20, "You gathered enough materials to form " + scrap + " scrap."));
			searchOptions.add(new SearchOption(null, new ShipUpdate(-1 + scrap, 0, 0), 0, "You gathered enough materials to form " + scrap + " fuel."));

			SearchOptionContainer soc = new SearchOptionContainer(searchOptions);
			this.addOption(new GenericSearch("Gather particles", soc));
		}
}
