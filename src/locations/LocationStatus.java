package locations;

public enum LocationStatus {
	Unvisited("Unvisited"), Unused("Unused"), Visited("Visited");
	
	private String ls;
	
	private LocationStatus(String ls) {
		this.ls = ls;
	}

	public String getLs() {
		return ls;
	}

	public void setLs(String ls) {
		this.ls = ls;
	}
	
	
}
