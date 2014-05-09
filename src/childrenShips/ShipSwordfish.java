package childrenShips;

import items.Item;

import java.util.ArrayList;

import ship.Ship;
import shipUpgrades.Upgrade;
import shipUpgrades.Weapon;

public class ShipSwordfish extends Ship {

	// For creating a new ship
	public ShipSwordfish(String name) {
		setDefaults();
		this.name = name;
		fuel = 100;
		hull = 400;
		scrap = 100;		
		load = fuel + scrap;
		updateLoad();
		this.inventory = new ArrayList<Item>();
	}
	
	private void setDefaults() {
		className = "Swordfish Class";
		maxGenericU = 4;
		maxEngineU = 1;
		maxShieldingU = 1;
		maxHullU = 1;
		maxWeaponU = 3;
		maxOtherU = 2;
		shieldingUpgrades = new ArrayList<Upgrade>(1);
		genericUpgrades = new ArrayList<Upgrade>(4);
		engineUpgrades = new ArrayList<Upgrade>(1);
		hullUpgrades = new ArrayList<Upgrade>(1);
		weaponUpgrades = new ArrayList<Weapon>(3);
		otherUpgrades = new ArrayList<Upgrade>(2);
		maxFuel = 100;
		maxHull = 400;
		capacity = 1000;
		speed = 40;
	}
}
