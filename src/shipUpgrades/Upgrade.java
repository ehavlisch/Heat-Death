package shipUpgrades;

import java.io.Serializable;

import childrenItems.GenericUpgradeItem;


public class Upgrade implements Serializable {
	protected String name = "Default upgrade name";
	
	protected int maxHull = 0;
	protected int maxFuel = 0;
	protected int efficiency = 0;
	protected int capacity = 0;
	
	protected int cost = 0;
	protected int fuelCost = 0;
	protected int energyCost = 0;
	protected int scrapCost = 0;
	
	protected int mass;
	
	protected UpgradeFamily family = UpgradeFamily.Generic;
	
	public String toString() {
		
		String str = "";
		
		str += name + "\n";
		str += "\tMass: " + mass + "\n";
		if(maxHull != 0) {
			str += "\tHull: " + maxHull + "\n";
		}
		if(maxFuel != 0) {
			str += "\tFuel: " + maxFuel + "\n";
		}
		if(efficiency != 0) {
			str += "\tEfficiency: " + efficiency + "\n";
		}
		if(capacity != 0) {
			str += "\tCapacity: " + capacity + "\n";
		}
		
		return str;
	}
	
	public GenericUpgradeItem toUpgrade() {
		return new GenericUpgradeItem(this);
	}
	
	public int getMaxHull() {
		return maxHull;
	}

	public void setMaxHull(int maxHull) {
		this.maxHull = maxHull;
	}

	public int getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(int maxFuel) {
		this.maxFuel = maxFuel;
	}

	public int getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getFuelCost() {
		return fuelCost;
	}

	public void setFuelCost(int fuelCost) {
		this.fuelCost = fuelCost;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}

	public int getScrapCost() {
		return scrapCost;
	}

	public void setScrapCost(int scrapCost) {
		this.scrapCost = scrapCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UpgradeFamily getFamily() {
		return family;
	}

	public void setFamily(UpgradeFamily family) {
		this.family = family;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public Weapon convertToWeapon() {
		return (Weapon) this;
	}
}
