package childrenOptions;

import options.Option;
import ship.ShipUpdate;
import locations.Location;
import main.IOHandler;
import main.Update;

public class JunkTraderScrapForFuel extends Option {
	public JunkTraderScrapForFuel()
	{
		name = "Trade scrap for fuel";
	}
	
	public void execute(Location loc)
	{
		int input;
		
		System.out.println("How much scrap do you want to trade? 2:1");
		input = Integer.parseInt(IOHandler.getInput());
		if(input < 0) {
			System.out.println("Invalid amount");
			update = new Update();
		} else if (input > 0){
			int scrap = -input;
			int fuel = (int) Math.floor(input/2);
			update = new Update(new ShipUpdate(fuel, 0, scrap));
			System.out.println("You trade " + Math.abs(scrap) + " scrap for " + fuel + " fuel");
		} else {
			System.out.println("Trade canceled");
		}
		
	}
}
