package childrenItems;

import items.Item;
import player.PlayerUpdate;
import ship.ShipUpdate;

public class SimpleRation extends Item {

	public SimpleRation()
	{
		name = "Simple Ration";
		
		pu = new PlayerUpdate(100);
		mass = 1;
	}
	
	public ShipUpdate interact() {
		System.out.println("Simple Ration interact");
		return new ShipUpdate();
	}
}
