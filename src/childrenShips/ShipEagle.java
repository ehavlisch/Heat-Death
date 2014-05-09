package childrenShips;

import java.util.ArrayList;

import ship.Ship;
import shipUpgrades.Upgrade;
import shipUpgrades.Weapon;

import items.Item;

public class ShipEagle extends Ship {

	public ShipEagle(String name) {
		setDefaults();
		this.name = name;
		fuel = 300;
		hull = 1000;
		scrap = 100;
		load = fuel + scrap;
		updateLoad();
	}
	
	public ShipEagle(int fuel, int hull, int scrap, ArrayList<Item> inventory) {
		setDefaults();
		this.fuel = fuel;
		this.hull = hull;
		this.scrap = scrap;
		load = fuel + scrap;
		
		this.inventory = inventory;
		for(int i = 0; i < inventory.size(); i++) {
			load += inventory.get(i).getMass();
		}
		
		checkOverflows();
		updateLoad();
	}

	private void setDefaults() {
		className = "Eagle Class";
		inventory = new ArrayList<Item>();
		maxGenericU = 10;
		maxEngineU = 2;
		maxShieldingU = 4;
		maxHullU = 4;
		maxWeaponU = 6;
		maxOtherU = 4;
		shieldingUpgrades = new ArrayList<Upgrade>(4);
		genericUpgrades = new ArrayList<Upgrade>(10);
		engineUpgrades = new ArrayList<Upgrade>(2);
		hullUpgrades = new ArrayList<Upgrade>(4);
		weaponUpgrades = new ArrayList<Weapon>(6);		
		otherUpgrades = new ArrayList<Upgrade>(4);
		maxFuel = 300;
		maxHull = 1000;

		efficiency = 10;

		capacity = 4000;

		speed = 50;
		
	}
}
