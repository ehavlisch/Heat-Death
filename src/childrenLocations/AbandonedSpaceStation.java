package childrenLocations;

import childrenInteriors.AbandonedSpaceStationInterior;
import locations.Location;
import main.RNG;


public class AbandonedSpaceStation extends Location {

	public AbandonedSpaceStation(String id) {
		desc = "Abandoned Space Station";
		detailed = "There is a large space station without any signs of life.";
		
		name = "Abandoned Station " + id;
		
		int rand = RNG.random(100);
		if(rand < 30) {
			detailed += " The structure is massive, but ancient.\n" +
					"The materials used are grossly out of date and probably couldn't be used in modern ships.\n" +
					"You could still search around anyway for something that may have been left behind.\n" +
					"Given it's age and the nature of things, it's probably been picked clean by now.\n";
		} else if(rand < 60) {
			detailed += " The station looks relatively modern.\n" +
					"It has modern looking engines, that probably don't work, but there is a a large amount of \n" +
					"scrap available for salvaging. As you approach, a message fizzles into your radio.\n" +
					"'Warning! Do not approach without proper protection for deadly viral outbreak!\n" +
					"Approach at your own risk!' The message repeats and you turn it off.\n";
		} else if(rand < 90) {
			detailed += " It is a grisly scene of space combat.\n" +
					"There are bodies floating around and the station appears relatively undisturbed.\n" +
					"It looks as if it has become a memorial to the death rather than another salvage station.\n";
		} else {
			detailed += " The station is utterly unremarkable.\n" +
					"It looks just like you would expect a space station to look like and has all the signs of\n" +
					"once possessing all the features you would expect. Unfortunately, time hasn't been nice.\n";
		}
		addInteriorConnection(new AbandonedSpaceStationInterior(name, rand, this));
	}
}
