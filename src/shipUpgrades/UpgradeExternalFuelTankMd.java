package shipUpgrades;

public class UpgradeExternalFuelTankMd extends Upgrade {

	public UpgradeExternalFuelTankMd() {
		name = "External Fuel Tank Medium";
		maxFuel = 200;
		efficiency = 2;
		cost = -300;
		family = UpgradeFamily.Generic;
		mass = 40;
	}

}
