package ship;

public enum ShipVisualStatus {
	Perfect("The ship is in mint condition", 1), // > 80
	Dented("The ship has taken minor damage", 2), // > 60
	Damaged("The ship is at half mast", 3), // > 40
	Failing("The ship is barely limping", 4), // > 20
	Burning("The ship is on the verge of destruction", 5),
	Destroyed("The ship is rubble in space", 6); // <= 0
	
	private String status;
	private int targetValue;
	
	private ShipVisualStatus(String status, int targetValue) {
		this.setStatus(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(int targetValue) {
		this.targetValue = targetValue;
	}
}
