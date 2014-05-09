package ship;

import items.Item;

import java.util.ArrayList;
import java.util.List;

import childrenItems.BasicFuelContainer;

import combat.CombatDamageResult;

import shipUpgrades.*;

import main.IOHandler;

public class Ship {
	protected int fuel;
	protected int maxFuel;

	protected int efficiency;

	protected int hull;
	protected int maxHull;

	protected int scrap;

	protected ArrayList<Item> inventory;
	protected int load;
	protected int capacity;
	protected int overloaded;

	protected int maxGenericU;
	protected int maxEngineU;
	protected int maxShieldingU;
	protected int maxHullU;
	protected int maxWeaponU;
	protected int maxOtherU;
	protected List<Upgrade> genericUpgrades;
	protected List<Upgrade> engineUpgrades;
	protected List<Upgrade> shieldingUpgrades;
	protected List<Upgrade> hullUpgrades;
	protected List<Weapon> weaponUpgrades;
	protected List<Upgrade> otherUpgrades;

	protected int speed;

	protected String name;
	protected String className;

	public String toString() {
		String str = "Ship Details\n";
		str += "Fuel: " + fuel + "/" + maxFuel + "\n";
		str += "Hull: " + hull + "/" + maxHull + "\n";
		str += "Load: " + load + "/" + capacity + "\n";
		str += "Scrap: " + scrap + "\n";
		str += "Efficiency: " + efficiency + "\n";
		str += "Speed: " + speed + "\n";
		str += "Overloaded: ";
		if (overloaded == 1) {
			str += "Yes\n";
		} else {
			str += "No\n";
		}

		// inventory
		if (inventory != null && inventory.size() > 0) {
			str += "Cargo Hold:\n";
			for (Item i : inventory) {
				str += i.toString();
			}
		}

		// upgrades
		if (genericUpgrades != null && genericUpgrades.size() > 0) {
			str += "Generic Upgrades:\n";
			for (Upgrade u : genericUpgrades) {
				str += u.toString();
			}
		}
		if (engineUpgrades != null && engineUpgrades.size() > 0) {
			str += "Engine Upgrades:\n";
			for (Upgrade u : engineUpgrades) {
				str += u.toString();
			}
		}
		if (shieldingUpgrades != null && shieldingUpgrades.size() > 0) {
			str += "Shielding Upgrades:\n";
			for (Upgrade u : shieldingUpgrades) {
				str += u.toString();
			}
		}
		if (weaponUpgrades != null && weaponUpgrades.size() > 0) {
			str += "Weapon Upgrades:\n";
			for (Upgrade u : weaponUpgrades) {
				str += u.toString();
			}
		}
		if (hullUpgrades != null && hullUpgrades.size() > 0) {
			str += "Hull Upgrades:\n";
			for (Upgrade u : hullUpgrades) {
				str += u.toString();
			}
		}
		if (otherUpgrades != null && otherUpgrades.size() > 0) {
			str += "Other Upgrades:\n";
			for (Upgrade u : otherUpgrades) {
				str += u.toString();
			}
		}

		return str;
	}

	public boolean hasWeapons() {
		return !(weaponUpgrades == null || weaponUpgrades.size() == 0);
	}

	public ShipUpdate firingCost() {
		ShipUpdate su = new ShipUpdate();

		for (Upgrade u : weaponUpgrades) {
			su.addFuel(-((Weapon) u).getCost());
		}

		return su;
	}

	public String listInventory(boolean bool) {
		String str = "";
		int index = 0;
		if (inventory != null && inventory.size() > 0) {
			for (Item i : inventory) {
				if (bool) {
					str += index + " -- ";
					index++;
				}
				str += i.getName() + "\n";
			}
			return str;
		} else {
			return "Inventory empty\n";
		}
	}

	// Removed - use the IOHandler optiosnList instead
	/*
	public String listUpgrades(boolean displayIndexes, UpgradeFamily family) {
		String str = "";
		if (family == UpgradeFamily.Generic && genericUpgrades != null) {
			for (int i = 0; i < genericUpgrades.size(); i++) {
				if (displayIndexes) {
					str += i + " -- " + genericUpgrades.get(i).getName();
				} else {
					str += genericUpgrades.get(i).getName();
				}
			}
		} else if (family == UpgradeFamily.Engine && engineUpgrades != null) {
			for (int i = 0; i < engineUpgrades.size(); i++) {
				if (displayIndexes) {
					str += i + " -- " + engineUpgrades.get(i).getName();
				} else {
					str += engineUpgrades.get(i).getName();
				}
			}
		} else if (family == UpgradeFamily.Hull && hullUpgrades != null) {
			for (int i = 0; i < hullUpgrades.size(); i++) {
				if (displayIndexes) {
					str += i + " -- " + hullUpgrades.get(i).getName();
				} else {
					str += hullUpgrades.get(i).getName();
				}
			}
		} else if (family == UpgradeFamily.Other && otherUpgrades != null) {
			for (int i = 0; i < otherUpgrades.size(); i++) {
				if (displayIndexes) {
					str += i + " -- " + otherUpgrades.get(i).getName();
				} else {
					str += otherUpgrades.get(i).getName();
				}
			}
		} else if (family == UpgradeFamily.Shielding && shieldingUpgrades != null) {
			for (int i = 0; i < shieldingUpgrades.size(); i++) {
				if (displayIndexes) {
					str += i + " -- " + shieldingUpgrades.get(i).getName();
				} else {
					str += shieldingUpgrades.get(i).getName();
				}
			}
		} else if (family == UpgradeFamily.Weapon && weaponUpgrades != null) {
			for (int i = 0; i < weaponUpgrades.size(); i++) {
				if (displayIndexes) {
					str += i + " -- " + weaponUpgrades.get(i).getName();
				} else {
					str += weaponUpgrades.get(i).getName();
				}
			}
		} else {
			str = null;
		}

		return str;
	}
	*/
	
	public ShipUpdateResult refuel() {
		ShipUpdateResult sur = new ShipUpdateResult();
		int toFull = maxFuel - fuel;
		int amount = 0;

		if (toFull == 0) {
			return sur;
		} else {
			Item temp;

			int recoveredScrap = 0;

			int fullTanks = (int) Math.floor(toFull / 50);
			int remainder = toFull % 50;

			// Attempt to completely empty full tanks
			for (int i = 0; i < inventory.size() && fullTanks > 0; i++) {
				temp = inventory.get(i);
				if (temp.getName().equalsIgnoreCase("Fuel Container Full")) {
					amount += temp.getSu().getFuel();
					recoveredScrap += temp.getSu().getScrap();
					removeItem(i);
					fullTanks--;
					sur.addRemovedItem(temp);
				}
			}

			if (fullTanks > 0) {
				remainder += 50 * fullTanks;
			}

			// Attempt to completely empty/drain partially full tanks
			for (int i = 0; i < inventory.size() && remainder > 0; i++) {
				temp = inventory.get(i);
				if (temp.getName().equalsIgnoreCase("Fuel Container")) {
					if (temp.getSu().getFuel() > remainder) {
						amount += remainder;
						((BasicFuelContainer) temp).drain(remainder);
						remainder = 0;
					} else {
						remainder -= temp.getSu().getFuel();
						amount += temp.getSu().getFuel();
						recoveredScrap += temp.getSu().getScrap();
						removeItem(temp);
						sur.addRemovedItem(temp);
					}
				}
			}

			// Attempt to drain from full tanks
			for (int i = 0; i < inventory.size() && remainder > 0; i++) {
				temp = inventory.get(i);
				if (temp.getName().equalsIgnoreCase("Fuel Container Full")) {
					System.out.println("found a full fuel container");
					if (temp.getSu().getFuel() > remainder) {
						System.out.println("More fuel in tank than needed");
						amount += remainder;
						((BasicFuelContainer) temp).drain(remainder);
						remainder = 0;
					} else {
						System.out.println("More fuel needed than in tank, draining it");
						remainder -= temp.getSu().getFuel();
						amount += temp.getSu().getFuel();
						recoveredScrap += temp.getSu().getScrap();
						removeItem(temp);
					}
				}
			}
			scrap += recoveredScrap;
			sur.setdScrap(recoveredScrap);
			sur.setnScrap(scrap);
		}
		fuel += amount;
		sur.setdFuel(amount);
		sur.setnFuel(fuel);
		return sur;
	}

	public int overloaded() {
		return overloaded;
	}

	// TODO replace repair return methods with ShipUpdateResults
	public void repair() {
		int damage = maxHull - hull;
		if (damage == 0) {
			System.out.println("Ship is already fully repaired. " + hull + "/" + maxHull + " integrity.");
			return;
		} else if (scrap >= damage) {
			scrap -= damage;
			load -= damage;
			hull = maxHull;
			updateLoad();
			System.out.println("Repaired " + damage + " points to " + hull + "/" + maxHull + " integrity.");
		} else if (scrap > 0 && scrap < damage) {
			hull += scrap;
			load -= scrap;
			System.out.println("Repaired " + scrap + " points to " + hull + "/" + maxHull + " integrity.");
			scrap = 0;
			updateLoad();
		} else {
			System.out.println("No scrap for repairs.");
		}
	}

	// TODO replace repair return methods with ShipUpdateResults
	public void repair(int amount) {
		if (scrap >= amount) {
			int damage = maxHull - hull;
			if (damage == 0) {
				System.out.println("Ship is already fully repaired. Hull: " + maxHull);
				return;
			} else if (amount >= damage) {
				// repairing more than maxHull
				scrap -= damage;
				load -= damage;
				hull = maxHull;
				updateLoad();
				System.out.println("Full repaired " + damage + " hull points");
			} else {
				// repairing less than maxHull
				scrap -= amount;
				load -= amount;
				hull += amount;
				updateLoad();
				System.out.println("Repaired " + amount + ". Now at " + hull + " hull points");
			}
		} else {
			System.out.println("You only have " + scrap + " scrap!");
		}
	}

	// TODO replace repair return methods with ShipUpdateResults
	public int repairWith(int amount) {
		int damage = maxHull - hull;

		if (amount > damage) {
			hull = maxHull;
			amount -= damage;
			return amount;
		} else {
			hull += amount;
			return 0;
		}
	}

	// TODO replace dump Inventory return method with ShipUpdateResults
	public void dumpInventory() {
		for (int i = 0; i < inventory.size(); i++) {
			load -= inventory.get(i).getMass();
			inventory.remove(i);
		}
		updateLoad();
	}

	public void addInventory(Item item) {
		inventory.add(item);
		load += item.getMass();
		updateLoad();
	}

	public void removeItem(int index) {
		Item temp = inventory.get(index);
		inventory.remove(index);
		load -= temp.getMass();
		updateLoad();
	}

	public boolean removeItem(Item item) {
		if (inventory.remove(item)) {
			load -= item.getMass();
			updateLoad();
			return true;
		}
		return false;
	}

	protected void updateLoad() {
		if (load <= capacity) {
			overloaded = 0;
		} else if (load <= capacity * 2) {
			overloaded = 1;
		} else {
			overloaded = 2;
		}
	}

	public int getEfficiency() {
		return efficiency;
	}

	public int getFuel() {
		return fuel;
	}

	public int getMaxFuel() {
		return maxFuel;
	}

	public void setFuel(int fuel) {
		int previous = this.fuel;
		load -= previous;
		this.fuel = fuel;
		load += fuel;
		updateLoad();
	}

	public String addFuel(int fuel) {
		int overflow = this.fuel + fuel - maxFuel;
		String str = "";
		if (overflow > 0) {
			while (overflow > 0) {
				int s = subScrap(10);
				if (overflow >= 50 && s > 0) {
					addInventory(new BasicFuelContainer());
					overflow -= 50;
					str += "Converted Excess Fuel & 10 Scrap into a Fuel Container Full.";
				} else if (overflow < 50 && s > 0) {
					addInventory(new BasicFuelContainer(overflow));
					str += "Converted Excess Fuel & 10 Scrap into a Fuel Container " + overflow + "/50";
					overflow = 0;
				} else if (overflow > 0 && s < 0) {
					str += "Not enough scrap to convert excess fuel into Fuel Containers";
					handleOverflow(overflow, "fuel");
					return str;
				} else {
					str += "Something else happened here";
				}
			}

			// need to handle the load changing as fuel might go up
			load += maxFuel - this.fuel;
			this.fuel = maxFuel;
			updateLoad();
		} else {
			this.fuel += fuel;
			load += fuel;
			updateLoad();
		}
		return str;
	}

	public void subFuel(int fuel) {
		this.fuel -= fuel;
		load -= fuel;
	}

	public int getMaxHull() {
		return maxHull;
	}

	public int getHull() {
		return hull;
	}

	public void subHull(int hull) {
		this.hull -= hull;
	}

	public void addScrap(int scrap) {
		this.scrap += scrap;
		load += scrap;
		updateLoad();
	}

	public int subScrap(int scrap) {
		if (this.scrap < scrap) {
			return -1;
		}
		load -= scrap;
		updateLoad();
		this.scrap -= scrap;
		return 1;
	}

	public int indexOfItem(String name) {
		for (Item temp : inventory) {
			if (temp.getName().equalsIgnoreCase(name)) {
				return inventory.indexOf(temp);
			}
		}
		return -1;
	}

	public ShipUpdateResult handleOverflow(int amount, String type) {
		System.out.println("Detected an overflow of " + amount + " " + type + ".");

		if (type == "fuel") {
			return handleFuelOverflow(amount);
		} else if (type == "scrap") {
			return handleScrapOverflow(amount);
		} else if (type == "hull") {
			return handleHullOverflow(amount);
		} else {
			System.out.println("Unknown type of overflow.");
		}
		return null;
	}

	public ShipUpdateResult testUpgrade(Upgrade upgrade, ShipUpdateResult sur) {
		UpgradeFamily fam = upgrade.getFamily();
		sur.setAdded(upgrade);

		if (fam.equals(UpgradeFamily.Generic)) {
			if (genericUpgrades.size() >= maxGenericU) {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Engine)) {
			if (engineUpgrades.size() >= maxEngineU) {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Hull)) {
			if (hullUpgrades.size() >= maxHullU) {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Shielding)) {
			if (shieldingUpgrades.size() >= maxShieldingU) {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Weapon)) {
			if (weaponUpgrades.size() >= maxWeaponU) {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else {
			if (otherUpgrades.size() >= maxOtherU) {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		}
		return sur;
	}

	public ShipUpdateResult applyUpgrade(Upgrade upgrade, ShipUpdateResult sur) {

		UpgradeFamily fam = upgrade.getFamily();
		sur.setAdded(upgrade);

		if (fam.equals(UpgradeFamily.Generic)) {
			sur.setAddUpgradeFamily(UpgradeFamily.Generic);
			if (genericUpgrades.size() < maxGenericU) {
				genericUpgrades.add(upgrade);
			} else {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Engine)) {
			sur.setAddUpgradeFamily(UpgradeFamily.Engine);
			if (engineUpgrades.size() < maxEngineU) {
				engineUpgrades.add(upgrade);
			} else {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Hull)) {
			sur.setAddUpgradeFamily(UpgradeFamily.Hull);
			if (hullUpgrades.size() < maxHullU) {
				hullUpgrades.add(upgrade);
			} else {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Shielding)) {
			sur.setAddUpgradeFamily(UpgradeFamily.Shielding);
			if (shieldingUpgrades.size() < maxShieldingU) {
				shieldingUpgrades.add(upgrade);
			} else {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else if (fam.equals(UpgradeFamily.Weapon)) {
			sur.setAdded(upgrade);
			sur.setAddUpgradeFamily(UpgradeFamily.Weapon);
			if (weaponUpgrades.size() < maxWeaponU) {
				weaponUpgrades.add(upgrade.convertToWeapon());
				// weaponUpgrades.add(upgrade);
			} else {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		} else {
			if (otherUpgrades.size() < maxOtherU) {
				otherUpgrades.add(upgrade);
				sur.setAddUpgradeFamily(UpgradeFamily.Other);
			} else {
				sur.setAddUpgradeFlag(-1);
				//sur.setSuccess(false);
				return sur;
			}
		}
		maxHull += upgrade.getMaxHull();
		maxFuel += upgrade.getMaxFuel();

		if (hull > maxHull) {
			int overflow = hull - maxHull;
			load -= overflow;
			handleOverflow(overflow, "hull");
			hull = maxHull;
		}
		if (fuel > maxFuel) {
			int overflow = fuel - maxFuel;
			load -= overflow;
			this.handleOverflow(overflow, "fuel");
			fuel = maxFuel;
		}

		capacity += upgrade.getCapacity();
		efficiency += upgrade.getEfficiency();
		load += upgrade.getMass();
		updateLoad();
		return sur;
	}

	public Upgrade removeUpgrade(int index, UpgradeFamily fam) {
		Upgrade u = null;
		if (fam.equals(UpgradeFamily.Generic)) {
			u = genericUpgrades.get(index);
			genericUpgrades.remove(index);
		} else if (fam.equals(UpgradeFamily.Engine)) {
			u = engineUpgrades.get(index);
			engineUpgrades.remove(index);
		} else if (fam.equals(UpgradeFamily.Hull)) {
			u = hullUpgrades.get(index);
			hullUpgrades.remove(index);
		} else if (fam.equals(UpgradeFamily.Shielding)) {
			u = shieldingUpgrades.get(index);
			shieldingUpgrades.remove(index);
		} else if (fam.equals(UpgradeFamily.Weapon)) {
			u = weaponUpgrades.get(index);
			weaponUpgrades.remove(index);
		} else {
			u = otherUpgrades.get(index);
			otherUpgrades.remove(index);
		}
		if (u != null) {
			maxHull -= u.getMaxHull();
			if (hull > maxHull) {
				int overflow = hull - maxHull;
				load -= overflow;
				handleOverflow(overflow, "hull");
				hull = maxHull;
			}
			maxFuel -= u.getMaxFuel();
			if (fuel > maxFuel) {
				int overflow = fuel - maxFuel;
				load -= overflow;
				this.handleOverflow(overflow, "fuel");
				fuel = maxFuel;
			}
			capacity -= u.getCapacity();
			efficiency -= u.getEfficiency();
			load -= u.getMass();
			updateLoad();
		}

		return u;
	}

	public ShipUpdateResult testRemoveUpgrade(Upgrade u, ShipUpdateResult sur) {
		if (u.getFamily().equals(UpgradeFamily.Generic)) {
			for (int i = 0; i < genericUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(genericUpgrades.get(i).getName())) {
					return sur;
				}
			}
			//sur.setSuccess(false);
			return sur;
		} else if (u.getFamily().equals(UpgradeFamily.Engine)) {
			for (int i = 0; i < engineUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(engineUpgrades.get(i).getName())) {
					return sur;
				}
			}
			//sur.setSuccess(false);
			return sur;
		} else if (u.getFamily().equals(UpgradeFamily.Hull)) {
			for (int i = 0; i < hullUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(hullUpgrades.get(i).getName())) {
					return sur;
				}
			}
			//sur.setSuccess(false);
			return sur;
		} else if (u.getFamily().equals(UpgradeFamily.Shielding)) {
			for (int i = 0; i < shieldingUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(shieldingUpgrades.get(i).getName())) {
					return sur;
				}
			}
			//sur.setSuccess(false);
			return sur;
		} else if (u.getFamily().equals(UpgradeFamily.Weapon)) {
			for (int i = 0; i < weaponUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(weaponUpgrades.get(i).getName())) {
					return sur;
				}
			}
			//sur.setSuccess(false);
			return sur;
		} else {
			for (int i = 0; i < otherUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(otherUpgrades.get(i).getName())) {
					return sur;
				}
			}
			//sur.setSuccess(false);
			return sur;
		}
	}

	public ShipUpdateResult removeUpgrade(Upgrade u, ShipUpdateResult sur) {
		if (u.getFamily() == UpgradeFamily.Generic) {
			sur.setRemoveUpgradeFamily(UpgradeFamily.Generic);
			for (int i = 0; i < genericUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(genericUpgrades.get(i).getName())) {
					sur.setRemoved(removeUpgrade(i, UpgradeFamily.Generic));
					return sur;
				}
			}
		} else if (u.getFamily() == UpgradeFamily.Engine) {
			sur.setRemoveUpgradeFamily(UpgradeFamily.Engine);
			for (int i = 0; i < engineUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(engineUpgrades.get(i).getName())) {
					sur.setRemoved(removeUpgrade(i, UpgradeFamily.Engine));
					return sur;
				}
			}
		} else if (u.getFamily() ==UpgradeFamily.Hull) {
			sur.setRemoveUpgradeFamily(UpgradeFamily.Hull);
			for (int i = 0; i < hullUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(hullUpgrades.get(i).getName())) {
					sur.setRemoved(removeUpgrade(i, UpgradeFamily.Hull));
					return sur;
				}
			}
		} else if (u.getFamily() == UpgradeFamily.Shielding) {
			sur.setRemoveUpgradeFamily(UpgradeFamily.Shielding);
			for (int i = 0; i < shieldingUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(shieldingUpgrades.get(i).getName())) {
					sur.setRemoved(removeUpgrade(i, UpgradeFamily.Shielding));
					return sur;
				}
			}
		} else if (u.getFamily() == UpgradeFamily.Weapon) {
			sur.setRemoveUpgradeFamily(UpgradeFamily.Weapon);
			for (int i = 0; i < weaponUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(weaponUpgrades.get(i).getName())) {
					sur.setRemoved(removeUpgrade(i, UpgradeFamily.Weapon));
					return sur;
				}
			}
		} else {
			sur.setRemoveUpgradeFamily(UpgradeFamily.Other);
			for (int i = 0; i < otherUpgrades.size(); i++) {
				if (u.getName().equalsIgnoreCase(otherUpgrades.get(i).getName())) {
					sur.setRemoved(removeUpgrade(i, UpgradeFamily.Other));
					return sur;
				}
			}
		}
		return sur;
	}

	public ShipVisualStatus getVisualStatus() {
		int hullPercent = 100 * hull / maxHull;

		if (hullPercent > 80) {
			return ShipVisualStatus.Perfect;
		} else if (hullPercent > 60) {
			return ShipVisualStatus.Dented;
		} else if (hullPercent > 40) {
			return ShipVisualStatus.Damaged;
		} else if (hullPercent > 20) {
			return ShipVisualStatus.Failing;
		} else if (hullPercent > 0) {
			return ShipVisualStatus.Burning;
		} else {
			return ShipVisualStatus.Destroyed;
		}
	}

	public CombatDamageResult combatUpdate(ShipUpdate su) {

		CombatDamageResult cdr = new CombatDamageResult();

		hull += su.getHull();

		if (hull + su.getHull() <= 0) {
			cdr.setDamageInflicted(hull);
			cdr.setTargetDestroyed(true);
			cdr.setTargetVisualStatus(ShipVisualStatus.Destroyed);
			return cdr;
		} else {
			cdr.setTargetVisualStatus(getVisualStatus());
			cdr.setDamageInflicted(-su.getHull());
		}

		// TODO add fuel/scrap checks to add support for different types of
		// weapons that have draining effects
		return cdr;
	}

	public ShipUpdateResult update(ShipUpdate su) {
		if (su != null) {
			ShipUpdateResult sur = new ShipUpdateResult();

			if (hull + su.getHull() <= 0) {
				sur.setHullFlag(false);
			} else if (su.getHull() != 0) {
				sur.setdHull(su.getHull());
			}

			if (fuel + su.getFuel() < 0) {
				sur.setFuelFlag(false);
			} else if (su.getFuel() != 0) {
				sur.setdFuel(su.getFuel());
			}

			if (scrap + su.getScrap() < 0) {
				sur.setScrapFlag(false);
			} else if (su.getScrap() != 0) {
				sur.setdScrap(su.getScrap());
			}

			if (sur.isSuccess()) {
				Upgrade u = su.getUpgrade();
				if (u != null) {
					sur = testUpgrade(u, sur);
				}

				u = su.getRemovedUpgrade();

				if (u != null) {
					sur = testRemoveUpgrade(u, sur);
				}
			}

			if (sur.isSuccess()) {
				if (sur.isHullFlag()) {
					hull += su.getHull();
					sur.setnHull(hull);
				}

				if (sur.isFuelFlag()) {
					this.addFuel(su.getFuel());
					sur.setnFuel(fuel);
				}

				if (sur.isScrapFlag()) {
					this.addScrap(su.getScrap());
					sur.setnScrap(scrap);
				}

				Upgrade u = su.getUpgrade();

				if (u != null) {
					sur = applyUpgrade(u, sur);
				}

				u = su.getRemovedUpgrade();

				if (u != null) {
					sur = removeUpgrade(u, sur);
				}
			}

			if (sur.isSuccess() && su.getAddedItems() != null && su.getAddedItems().size() > 0) {
				for (Item i : su.getAddedItems()) {
					addInventory(i);
					sur.addItem(i);
				}
			}

			if (sur.isSuccess() && su.getRemovedItems() != null && su.getRemovedItems().size() > 0) {
				for (Item i : su.getRemovedItems()) {
					removeItem(i);
					sur.addRemovedItem(i);
				}
			}

			return sur;
		} else {
			return new ShipUpdateResult();
		}
	}
	
	public void applyUpdate(ShipUpdate su) {
		fuel += su.getFuel();
		scrap += su.getScrap();
		hull += su.getHull();
		
		//TODO add upgrades and items
	}

	// TODO: consider the load, these are only called in the ship constructors
	// may need to change the constructors as well and make this function
	// obsolete
	protected void checkOverflows() {

		if (hull > maxHull) {
			int temp = hull;
			hull = maxHull;
			handleOverflow(temp - maxHull, "hull");
		}

		if (fuel > maxFuel) {
			int temp = fuel;
			fuel = maxFuel;

			handleOverflow(temp - maxFuel, "fuel");
		}
	}

	public ShipUpdateResult handleFuelOverflow(int amount) {
		return handleFuelOverflow(amount, new ShipUpdateResult());
	}

	public ShipUpdateResult handleScrapOverflow(int amount) {
		return handleScrapOverflow(amount, new ShipUpdateResult());
	}

	public ShipUpdateResult handleScrapOverflow(int amount, ShipUpdateResult sur) {
		if (amount > 0) {
			addScrap(amount);
		} else {
			// ask to subtract scrap from the hull
		}
		return null;
	}

	public ShipUpdateResult handleHullOverflow(int amount) {
		return handleHullOverflow(amount, new ShipUpdateResult());
	}

	public ShipUpdateResult handleHullOverflow(int amount, ShipUpdateResult sur) {
		if (amount > 0) {
			addScrap(amount);
		} else {
			// hull damage - can end game, should make action unperformable
			hull -= amount;
		}
		return null;
	}

	public ShipUpdateResult handleFuelOverflow(int amount, ShipUpdateResult sur) {
		if (amount > 0) {

			int index = indexOfItem("Fuel Container");
			while (index != -1) {
				System.out.println("Filling partially filled fuel containers...");
				if (index >= 0) {
					BasicFuelContainer temp = (BasicFuelContainer) inventory.get(index);

					int overflow = temp.fill(amount);
					load += amount;
					load -= overflow;
					updateLoad();
					amount = overflow;

					if (overflow == 0) {
						return sur;
					}
				}
				index = indexOfItem("Fuel Container");
			}

			while (amount > 0 && scrap >= 10) {
				scrap -= 10;
				if (amount >= 50) {
					Item temp = new BasicFuelContainer();
					load += temp.getMass() - 10;
					inventory.add(temp);
					updateLoad();
				} else {
					Item temp = new BasicFuelContainer(amount);
					load += temp.getMass() - 10;
					inventory.add(temp);
					return sur;
				}
			}

			while (amount > 0 && scrap < 10) {
				System.out.println("No remaining scrap to make canisters. How many canisters should we make from the hull?");
				System.out.println("Pick 0 or below to dump the excess fuel. " + amount + " remaining.");
				System.out.println("Hull is currently at " + hull + ".");

				int in = -1;
				try {
					in = Integer.parseInt(IOHandler.getInput());
				} catch (Exception e) {
					in = -1;
				}

				if (in <= 0) {
					System.out.println("Dumping excess " + amount + " fuel.");
					return sur;
				} else {
					while (amount > 50 && in > 0) {
						in--;
						subHull(10);
						amount -= 50;
						addInventory(new BasicFuelContainer());
					}

					if (amount < 50 && in > 0) {
						in--;
						subHull(10);
						addInventory(new BasicFuelContainer(amount));
						amount = 0;
						System.out.println("All excess fuel has been stored in the cargo bay.");
						return sur;
					}
				}
			}
		} else {
			//TODO revisit if needed
			// error cannot perform action
			sur.setdFuel(-1);
			//sur.setSuccess(false);
		}
		return sur;
	}

	public int getSpeed() {
		return speed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScrap() {
		return scrap;
	}

	public List<Upgrade> getGenericUpgrades() {
		return genericUpgrades;
	}

	public List<Upgrade> getEngineUpgrades() {
		return engineUpgrades;
	}

	public List<Upgrade> getShieldingUpgrades() {
		return shieldingUpgrades;
	}

	public List<Upgrade> getHullUpgrades() {
		return hullUpgrades;
	}

	public List<Weapon> getWeaponUpgrades() {
		return weaponUpgrades;
	}

	public List<Upgrade> getOtherUpgrades() {
		return otherUpgrades;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public ShipUpdate fireWeapons(int[] weaponIds) {
		int cost = 0;
		int damage = 0;
		for (int i : weaponIds) {
			Weapon wep = weaponUpgrades.get(i);
			cost += wep.getFiringCost();
			damage -= wep.getDamage();
		}
		ShipUpdate su = new ShipUpdate();

		if (cost <= fuel) {
			subFuel(cost);
			su.setHull(damage);
		}
		return su;
	}

	public List<Upgrade> getUpgrades(UpgradeFamily family) {
		
		switch(family.getId()) {
		case 0: return genericUpgrades;
		case 1: return shieldingUpgrades;
		case 2: return hullUpgrades;
		case 3: return engineUpgrades;
		case 4: 
		{
			ArrayList<Upgrade> castedWeapons = new ArrayList<Upgrade>(weaponUpgrades.size());
			for(Weapon w : weaponUpgrades) {
				castedWeapons.add((Upgrade) w);
			}
			return castedWeapons;
		}
		default: return otherUpgrades;
		}
	}

	public int getLoad() {
		return load;
	}

	public int getCapacity() {
		return capacity;
	}

	public String getClassName() {
		return className;
	}
}
