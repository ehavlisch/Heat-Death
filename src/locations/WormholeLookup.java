package locations;

public class WormholeLookup {
	private int wormholeId;
	private int sectorId;
	private boolean matched = false;
	
	public WormholeLookup(int wormholeId, int sectorId) {
		this.wormholeId = wormholeId;
		this.sectorId = sectorId;
	}
	
	public String toString() {
		return "(" + wormholeId + ", " + sectorId + ", " + matched + ")";
	}
	
	public void matched() {
		this.matched = true;
	}

	public int getWormholeId() {
		return wormholeId;
	}

	public void setWormholeId(int wormholeId) {
		this.wormholeId = wormholeId;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

}
