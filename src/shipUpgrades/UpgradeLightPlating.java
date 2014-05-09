package shipUpgrades;

public class UpgradeLightPlating extends Upgrade {
	public UpgradeLightPlating() {
		name = "Light Hull Plating";
		this.cost = -100;
		this.maxHull = 300;
		this.efficiency = 1;
		family = UpgradeFamily.Hull;
		mass = 100;
	}
}
