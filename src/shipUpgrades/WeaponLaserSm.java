package shipUpgrades;

import ship.ShipUpdate;

public class WeaponLaserSm extends Weapon {
	public WeaponLaserSm() {
		name = "Small Laser";
		cost = -100;
		family = UpgradeFamily.Weapon;
		mass = 100;
		damage = 100;
	}
	
	public ShipUpdate Fire() {
		ShipUpdate su = new ShipUpdate();		
		su.setFuel(fuelCost);
		return su;
	}
	
	public ShipUpdate Target() {
		ShipUpdate su = new ShipUpdate();
		su.setHull(damage);
		return su;
	}
}
