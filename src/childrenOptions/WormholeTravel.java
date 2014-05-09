package childrenOptions;

import childrenLocations.Wormhole;
import options.Option;
import ship.ShipUpdate;
import locations.Location;
import locations.LocationUpdate;
import main.Update;

public class WormholeTravel extends Option {
	
	public WormholeTravel(int targetSectorId, int wormholeId) {
		super();
		name = "Travel through the wormhole";
		repeatable = true;
		LocationUpdate lu = new LocationUpdate(targetSectorId, wormholeId);
		this.update = new Update(null, null, lu);

	}
	
	public void execute(Location loc) {
		
		Wormhole wormhole = (Wormhole) loc;
		
		LocationUpdate lu = new LocationUpdate(wormhole.getConnectingSector(), wormhole.getWormholeId());
		
		ShipUpdate su = new ShipUpdate();
		su.setFuel(-50);
		
		update = new Update();
		update.setLu(lu);
		update.setSu(su);
		System.out.println("You initate travel through the wormhole, gravitiational stabilizers to maximum. You put yourself into a deep sleep for the trip.");
	}

}
