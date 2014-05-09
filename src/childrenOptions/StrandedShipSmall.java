package childrenOptions;

import options.Option;
import locations.Location;
import main.RNG;

public class StrandedShipSmall extends Option {
	
	private boolean life = false;
	
	public StrandedShipSmall() 
	{
		name = "Investigate ship";
		description = "There is another ship that has been picked up on the scanner.";
		
		if(RNG.random(100) < 50) {
			life = true;
			description += " There are signs of life onboard.\n";
		} else {
			description += "\n";
		}
	}
	
	public void execute(Location loc)
	{
		loc.removeOption(this);
		
		int rand = RNG.random(100);
		if(life) {
			if(rand < 10) {
				System.out.println("You send a message to the ship. There's no response.\n");
				description = "There is a ship nearby with signs of life. Your messages recieved no response.\n";
				Option opt = new BoardShip(life);
				addOption(opt);
			} else {
				System.out.println("You attempt to send a message to the ship, but it quickly departs.\n");
				
			}
		} else {
			if(rand < 90) {
				System.out.println("You scan the ship. There are no signs of life onboard.");
				System.out.println("It looks intact and you could dock with the other ship and look around.");
				description += "You have scanned the ship and there are no signs of life aboard.";
				Option opt = new BoardShip(life);

				addOption(opt);
			} else {
				System.out.println("You scan the ship. There is not much left of the ship.");
				System.out.println("As you move in for visual inspection, the ship has been completely looted already.");
			}
		}
	}
}
