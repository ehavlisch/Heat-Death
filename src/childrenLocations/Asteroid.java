package childrenLocations;

import java.util.ArrayList;

import options.Option;

import childrenOptions.GenericSearch;
import childrenOptions.SearchOption;
import childrenOptions.SearchOptionContainer;
import childrenOptions.UnstableAsteroid;

import locations.Location;
import main.RNG;
import player.PlayerUpdate;
import ship.ShipUpdate;

public class Asteroid extends Location {
	
	public Asteroid(String id) 
	{
		desc = "A dark asteroid";
		detailed = "An asteroid with signs of mineral mining. It shows no current signs of life.\n";
		name = "Asteroid " + id;
		
		ArrayList<SearchOption> searchOptions = new ArrayList<SearchOption>(2);
		searchOptions.add(new SearchOption(new PlayerUpdate(-5), new ShipUpdate(RNG.random(30) + 1, 0, 0), 65, "You found a vein of minerals you can use as fuel."));
		searchOptions.add(new SearchOption(new PlayerUpdate(-5), new ShipUpdate(RNG.random(3) + 1, 0, 0), 50, "You found a few old fashion tools laying around and managed to siphon a few fuel from them."));
		searchOptions.add(new SearchOption(new PlayerUpdate(-5), new ShipUpdate(0, 0, RNG.random(40) + 1), 40, "There are the fragments of a ship and tools about that you gather as scrap."));
		searchOptions.add(new SearchOption(new PlayerUpdate(-5), null, 0, "You found nothing."));


		SearchOptionContainer soc = new SearchOptionContainer(searchOptions);
		
		this.addOption(new GenericSearch("Search the asteroid", soc, true));
		int rand = RNG.random(100);
		if(rand < 120) {
			Option option = new UnstableAsteroid();
			this.addOption(option);
		}
	}
}
