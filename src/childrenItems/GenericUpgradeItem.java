package childrenItems;

import items.Item;
import main.IOHandler;
import ship.ShipUpdate;
import shipUpgrades.Upgrade;

public class GenericUpgradeItem extends Item {
	
	private Upgrade upgrade;
	
	public GenericUpgradeItem(Upgrade u) {
		name = u.getName() + " - Uninstalled";
		mass = u.getMass();
		setUpgrade(u);
	}

	public Upgrade getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Upgrade upgrade) {
		this.upgrade = upgrade;
	}

	@Override
	public ShipUpdate interact() {
		System.out.println("What do you want to do with " + name + "?");
		System.out.println("0 -- Install");
		System.out.println("1 -- Dump");
		
		String in = IOHandler.getInput();
		int index = -1;
		try {
			index = Integer.parseInt(in);
		} catch(Exception e) {
			index = -1;
		}
		
		if(index == 0 || in.equalsIgnoreCase("install")) {			
			ShipUpdate su = new ShipUpdate();
			su.setUpgrade(upgrade);
			su.addRemovedItem(this);			
			return su;
		} else if(index == 1 || in.equalsIgnoreCase("dump")) {
			ShipUpdate su = new ShipUpdate();
			su.addRemovedItem(this);
			return su;
		} else {
			return new ShipUpdate();
		}
	}
	
	
}
