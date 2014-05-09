package childrenOptions;

import options.Option;
import locations.Location;
import main.IOHandler;
import main.Update;
import ship.ShipUpdate;

public class JunkTraderFuelForScrap extends Option {

	public JunkTraderFuelForScrap()
	{
		name = "Trade fuel for scrap";
	}
	
	public void execute(Location loc)
	{
		int input = -1;
		
		System.out.println("How much fuel do you want to trade? 1:1");
		try {
			input = Integer.parseInt(IOHandler.getInput());
		} catch (Exception e) {
			
		}
		
		if(input < 0) {
			System.out.println("Invalid amount");
			update = new Update();
		} else if (input > 0){
			ShipUpdate su = new ShipUpdate(-input, 0, input);
			update = new Update();
			update.setSu(su);
			System.out.println("You trade " + input + " fuel for " + input + " scrap");
		} else {
			System.out.println("Trade canceled");
		}
		
	}
}
