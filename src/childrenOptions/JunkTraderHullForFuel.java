package childrenOptions;

import options.Option;
import locations.Location;
import main.IOHandler;
import main.Update;
import ship.ShipUpdate;

public class JunkTraderHullForFuel extends Option {
	public JunkTraderHullForFuel()
	{
		name = "Trade hull for fuel";
		//result = "Uninitiated";
	}
	
	public void execute(Location loc)
	{
		int input;
		
		System.out.println("How much hull do you want to trade? 2:1");
		input = Integer.parseInt(IOHandler.getInput());
		if(input < 0) {
			System.out.println("Invalid amount");

			update = new Update();
		} else if (input > 0){
			int hull = -input;
			int fuel = (int) Math.floor(input/2);
			update = new Update(new ShipUpdate(fuel, hull, 0));
			System.out.println("You trade " + Math.abs(hull) + " hull for " + fuel + " fuel");
		} else {
			System.out.println("Trade canceled");
		}
		
	}
}
