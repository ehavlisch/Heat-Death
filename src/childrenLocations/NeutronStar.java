package childrenLocations;

import locations.Location;

public class NeutronStar extends Location {

	public NeutronStar(String id) {
		desc = "A small, dense planetoid.";
		detailed = "You come across the remains of a star that has gone type II supernova. All that is left behind after the sun collpase\n"
				+ "in on iteslf is a dark, incredibly dense planet with a gravity well strong enough to crush any human. If the star was larger,\n"
				+ "it may have formed a dark hole instead. You might be able to reach the surface by using a large amount of power to dampen the\n"
				+ "effects of gravity.\n";
		name = "Neutron Star " + id;
	}
}
