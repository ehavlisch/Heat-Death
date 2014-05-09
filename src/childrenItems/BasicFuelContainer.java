package childrenItems;

import items.Item;
import ship.ShipUpdate;

public class BasicFuelContainer extends Item {

	public BasicFuelContainer()
	{
		name = "Fuel Container Full";
		su = new ShipUpdate(50, 0, 10);
		mass = 60;
	}
	
	public BasicFuelContainer(int amount)
	{
		su = new ShipUpdate(amount, 0, 10);
		mass = 10 + amount;
		if(amount == 50) {
			name = "Fuel Container Full";
		} else {
			name = "Fuel Container";
		}
	}
	
	public String toString()
	{
		return "Basic Fuel Container " + su.getFuel() + "/50 Mass: " + mass + "\n";
	}
	
	public int fill(int amount)
	{
		int overflow = su.addFuel(amount, 50);
		
		if(overflow >= 0) {
			name = "Fuel Container Full";
			mass = 60;
			return overflow;
		} else {
			name = "Fuel Container";
			mass = 10 + su.getFuel();
			return 0;
		}
	}
	
	public int drain(int amount)
	{
		int newFuel = su.subFuel(amount);
		if(newFuel >= 0) {
			name = "Fuel Container";
		} else if(newFuel == 50) {
			name = "Fuel Container Full";
		}
		mass = 10 + su.getFuel();
		return newFuel;
	}
	
	public ShipUpdate interact() {
		System.out.println("Fuel Container interact");
		return new ShipUpdate();
	}
	
}
