package locations;

public class LocationUpdateResult {
	private boolean success;
	
	private boolean newSector;
	private String sectorName;
	
	private boolean newLocation;
	private Location loc;
	
	public LocationUpdateResult() {
		this.success = true;
		this.newSector = false;
		this.newLocation = false;
	}
	
	public LocationUpdateResult(boolean success) {
		this.success = success;
		this.newSector = false;
		this.newLocation = false;
	}
	
	public LocationUpdateResult(Location loc) {
		this.loc = loc;
		this.success = true;
		this.newLocation = true;
		this.newSector = false;
	}
	
	public LocationUpdateResult(Location loc, String sectorName) {
		this.loc = loc;
		this.sectorName = sectorName;
		this.success = true;
		this.newLocation = true;
		this.newSector = true;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isNewSector() {
		return newSector;
	}

	public boolean isNewLocation() {
		return newLocation;
	}

	public String getSectorName() {
		return sectorName;
	}

	public Location getLoc() {
		return loc;
	}

	public void setSectorName(String sectorName) {
		this.newSector = true;
		this.sectorName = sectorName;
	}

	public void setLoc(Location loc) {
		this.newLocation = true;
		this.loc = loc;
	}

	public String getResult() {
		StringBuilder sb = new StringBuilder();
		if(newSector && sectorName != null) {
			sb.append("You travel to sector: ");
			sb.append(sectorName);
			sb.append(". ");
		}
		
		if(newLocation && loc != null) {
			sb.append("You travel to a new location: ");
			sb.append(loc.getName());
			sb.append(". ");
		}
		
		return sb.toString();
	}	
}
