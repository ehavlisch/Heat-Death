package interiors;

import locations.Location;

public class Interior extends Location {
	protected int energyCost;
	
	//Override
	public boolean isInterior() {
		return true;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}
}
