package shipUpgrades;

public class UpgradeMediumPlating extends Upgrade {
	public UpgradeMediumPlating() {
		name = "Medium Hull Plating";
		this.cost = -100;
		this.maxHull = 600;
		this.efficiency = 2;
		family = UpgradeFamily.Hull;
		mass = 200;
	}
}
