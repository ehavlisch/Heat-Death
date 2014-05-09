package locations;

import java.io.Serializable;

public class LocationUpdate implements Serializable {
	
	private boolean wormholeTravel = false;
	
	private Integer sector;
	private Integer locationId;
	private Integer wormholeId;
	
	private Location location;
	
	public LocationUpdate(Integer sector, Integer wormholeId) {
		this.sector = sector;
		this.wormholeId = wormholeId;
		this.wormholeTravel = true;
	}

	public LocationUpdate(Location target) {
		this.location = target;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer location) {
		this.locationId = location;
	}

	public Integer getWormholeId() {
		return wormholeId;
	}

	public void setWormholeId(Integer wormholeId) {
		this.wormholeId = wormholeId;
	}

	public boolean isWormholeTravel() {
		return wormholeTravel;
	}

	public void setWormholeTravel(boolean wormholeTravel) {
		this.wormholeTravel = wormholeTravel;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

}
