package shipUpgrades;

import ship.ShipUpdate;

public abstract class Weapon extends Upgrade {
	protected int damage;
	protected int firingCost;
	//TODO Accuracy
	protected int accuracy;
	
	public abstract ShipUpdate Fire();
	public abstract ShipUpdate Target();
	
	public String toString() {
		
		String str = super.toString();
		
		if(damage != 0) {
			str += "\tDamage: " + damage + "\n";
		}
		if(firingCost != 0) {
			str += "\tDamage: " + firingCost + "\n";
		}
		
		return str;
	}
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getFiringCost() {
		return firingCost;
	}
	public void setFiringCost(int firingCost) {
		this.firingCost = firingCost;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
}
