package childrenLocations;

import childrenOptions.WormholeTravel;
import locations.Location;

public class Wormhole extends Location {
	
	private int wormholeId;
	private String connectionName;
	private int connectingSector;
	
	public Wormhole(String id, int wormholeId) {
		this.wormholeId = wormholeId;
		name = id + " Wormhole";
		desc = "A wormhole to a far off sector.";
		detailed = "A tear in space that allows instant travel across billions of miles. You are not sure where it leads.\n";
		
		detailed += "DEBUG: wormholeId: " + wormholeId + " connectionName: " + connectionName + " connectingSector: " + connectingSector + ".\n";
		
		wormhole = true;		
	}

	public int getWormholeId() {
		return wormholeId;
	}

	public void setWormholeId(int wormholeId) {
		this.wormholeId = wormholeId;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public int getConnectingSector() {
		return connectingSector;
	}

	public void setConnectingSector(int connectingSector) {
		this.connectingSector = connectingSector;
		this.addOption(new WormholeTravel(connectingSector, wormholeId));
	}
}
