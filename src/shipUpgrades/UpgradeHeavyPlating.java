package shipUpgrades;

public class UpgradeHeavyPlating extends Upgrade {
	public UpgradeHeavyPlating() {
		name = "Heavy Hull Plating";
		cost = -100;
		maxHull = 900;
		efficiency = 3;
		family = UpgradeFamily.Hull;
		mass = 300;
	}
}
