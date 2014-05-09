package childrenOptions;

import java.util.ArrayList;

import options.Option;

import player.PlayerUpdate;

import locations.Location;
import main.IOHandler;
import main.RNG;
import main.Update;

import ship.ShipUpdate;
import shipUpgrades.*;

public class BuyUpgrade extends Option {
	
	private ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	
	public BuyUpgrade()
	{		
		name = "Shop for Upgrades";
		description = "A chipper voice breaks the radio silence, 'Welcome to our lonely station, do you need any upgrades? We have a few for sale!'\n";
		repeatable = true;
		
		int tier = RNG.random(3);
		int random = RNG.random(100);
		
		if(tier < 1) {
			if (random < 40) {
				upgrades.add(new UpgradeLightPlating());				
			} else if(random < 80) {
				upgrades.add(new UpgradeExternalFuelTankSm());
			} else {
				upgrades.add(new UpgradeFusionRamjetV0());
			}
		} else if (tier < 2) {
			if (random < 40) {
				upgrades.add(new UpgradeLightPlating());
				upgrades.add(new UpgradeMediumPlating());				
			} else if(random < 80) {
				upgrades.add(new UpgradeFusionRamjetV0());
				upgrades.add(new UpgradeExternalFuelTankSm());
			} else {
				upgrades.add(new UpgradeFusionRamjetV1());
				upgrades.add(new UpgradeLightPlating());
			}
		} else if (tier < 3) {
			if (random < 40) {
				upgrades.add(new UpgradeLightPlating());
				upgrades.add(new UpgradeMediumPlating());
				upgrades.add(new UpgradeHeavyPlating());
			} else if(random < 80) {
				upgrades.add(new UpgradeFusionRamjetV0());
				upgrades.add(new UpgradeFusionRamjetV1());
				upgrades.add(new UpgradeExternalFuelTankSm());
			} else {
				upgrades.add(new UpgradeExternalFuelTankMd());
				upgrades.add(new UpgradeFusionRamjetV1());
				upgrades.add(new UpgradeHeavyPlating());
			}
		} else {
			if (random < 40) {
				upgrades.add(new UpgradeLightPlating());
				upgrades.add(new UpgradeMediumPlating());
				upgrades.add(new UpgradeExternalFuelTankSm());
				upgrades.add(new UpgradeExternalFuelTankMd());
			} else if(random < 80) {
				upgrades.add(new UpgradeFusionRamjetV0());
				upgrades.add(new UpgradeFusionRamjetV1());
				upgrades.add(new UpgradeLightPlating());
				upgrades.add(new UpgradeMediumPlating());
			} else {
				upgrades.add(new UpgradeFusionRamjetV0());
				upgrades.add(new UpgradeFusionRamjetV1());
				upgrades.add(new UpgradeHeavyPlating());
				upgrades.add(new UpgradeExternalFuelTankMd());
			}
		}
	}
	
	public void execute(Location loc)
	{
		update = null;
		if(upgrades.size() > 0) {
			System.out.println("You dock and a list of upgrades begins to filter to your ship.");
			
			listUpgrades();
			int in = -1;
			try {
				in = Integer.parseInt(IOHandler.getInput());
			} catch(NumberFormatException e) {
				in = -1;
			}
			
			if(in >= 0 && in < upgrades.size()) {
				Upgrade u = upgrades.get(in);
				upgrades.remove(u);
				ShipUpdate su = new ShipUpdate(u);
				PlayerUpdate pu = new PlayerUpdate(u.getCost(), 0);
				update = new Update();
				update.setSu(su);
				update.setPu(pu);
			} else {
				System.out.println("That is not a valid choice.");
			}
		} else {
			System.out.println("As you move near the ship, you receive another transmission, 'We're all out of stock, sorry!'");
			loc.removeOption(this);
			description = "The station sold a few upgrades and is now out of stock.\n";
		}
	}
	
	private void listUpgrades() {
		for(int i = 0; i < upgrades.size(); i ++) {
			System.out.println(i + " -- " + upgrades.get(i).getName() + " -- " + Math.abs(upgrades.get(i).getCost()));
		}
	}
	
	public void returnUpgrade(Upgrade upgrade) {
		upgrades.add(upgrade);
		//addedUpgrades = null;
		if(disabled) {
			disabled = false;
		}
	}
	
	public void removeUpgrade() {
		//addedUpgrades = null;
		if(upgrades.size() <= 0) {
			disabled = true;
		}
	}
	
}
