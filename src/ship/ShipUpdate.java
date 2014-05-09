package ship;

import items.Item;

import java.io.Serializable;
import java.util.ArrayList;

import shipUpgrades.Upgrade;

public class ShipUpdate implements Serializable {
	protected int fuel = 0;
	protected int hull = 0;
	protected int scrap = 0;
	
	protected int maxFuel = 0;
	protected int maxHull = 0;
	
	protected Upgrade upgrade = null;
	protected Upgrade removedUpgrade = null;
	
	protected ArrayList<Item> addedItems;
	protected ArrayList<Item> removedItems;
	
	public ShipUpdate() {
		
	}
	
	public ShipUpdate(Upgrade add, Upgrade remove) {
		upgrade = add;
		removedUpgrade = remove;
	}
	
	public ShipUpdate(Upgrade u) {
		upgrade = u;
	}
		
	public ShipUpdate(int fuel, int hull, int scrap) {
		this.fuel = fuel;
		this.hull = hull;
		this.scrap = scrap;
	}
	
	public ShipUpdate(int maxFuel, int maxHull) {
		this.maxFuel = maxFuel;
		this.maxHull= maxHull;
	}
	
	public void addItem(Item i) {
		if(addedItems == null) {
			addedItems = new ArrayList<Item>();
		}
		addedItems.add(i);
	}
	
	public void addRemovedItem(Item i) {
		if(removedItems == null) {
			removedItems = new ArrayList<Item>();
		}
		removedItems.add(i);
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getHull() {
		return hull;
	}

	public void setHull(int hull) {
		this.hull = hull;
	}

	public int getScrap() {
		return scrap;
	}

	public void setScrap(int scrap) {
		this.scrap = scrap;
	}

	public int getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(int maxFuel) {
		this.maxFuel = maxFuel;
	}

	public int getMaxHull() {
		return maxHull;
	}

	public void setMaxHull(int maxHull) {
		this.maxHull = maxHull;
	}
	
	public String getMessage(Ship ship) {
		return "";
	}

	public Upgrade getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Upgrade upgrade) {
		this.upgrade = upgrade;
	}

	public Upgrade getRemovedUpgrade() {
		return removedUpgrade;
	}

	public void setRemovedUpgrade(Upgrade removedUpgrade) {
		this.removedUpgrade = removedUpgrade;
	}
	
	public int addFuel(int amount, int max) {
		fuel += amount;
		
		if(fuel > max) {
			amount = fuel - max;
			fuel = max;
			return amount;
		} else {
			return 0;
		}
	}
	
	public void addFuel(int amount) {
		this.fuel += amount;
	}
	
	public int subFuel(int amount) {
		if(fuel - amount < 0) {
			return -1;
		} else {
			fuel -= amount;
			return fuel;
		}
	}
	
	public void addHull(int amount) {
		this.hull += amount;
	}

	public ArrayList<Item> getAddedItems() {
		return addedItems;
	}

	public ArrayList<Item> getRemovedItems() {
		return removedItems;
	}

	public void addScrap(int scrap) {
		this.scrap += scrap;
	}
	
}
