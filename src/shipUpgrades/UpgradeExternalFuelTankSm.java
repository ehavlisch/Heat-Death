package shipUpgrades;

public class UpgradeExternalFuelTankSm extends Upgrade {

	public UpgradeExternalFuelTankSm() {
		name = "External Fuel Tank Small";
		maxFuel = 100;
		efficiency = 1;
		cost = -100;
		family = UpgradeFamily.Generic;
		mass = 20;
	}

}
