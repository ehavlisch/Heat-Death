package childrenOptions;

import options.Option;
import ship.ShipUpdate;
import locations.Location;
import main.IOHandler;
import main.RNG;
import main.Update;

public class BoardShip extends Option {
	
	private boolean life = false;
	
	public BoardShip(boolean life)
	{
		name = "Board the ship";
		
		this.life = life;
		
		if(life) {
			description = "You decided to board the small ship and investigate the signs of life.\n";
		} else {
			description = "You decided to board the small abandoned ship.\n";
		}
	}
	
	public void execute(Location loc)
	{
		int rand = RNG.random(100);
		
		if(life) {
			if(rand < 90) {
				System.out.println("You open the hatch and the ship has only the guide lights on and the oxygen levels are dangerously low.");
				System.out.println("You see a men in the living quarters barely alive, his thermoelectric suit no longer sustaining him.");
				System.out.println("The man manages to turn and look at you and his mouth opens up as if to say something ... ");
				System.out.println("You watch the light fade from his eyes and the last source of power for the ship fades.");
				
				String in = IOHandler.getInput();
				System.out.println("Do you want to comadeer the ship? (yes/no)");
				if(in.equalsIgnoreCase("yes") || in.equalsIgnoreCase("y")) {
					
				} else {
					
				}
				
				System.out.println("Do you want to loot the ship? (yes/no)");
				in = IOHandler.getInput();
				if(in.equalsIgnoreCase("yes")  || in.equalsIgnoreCase("y")) {
					ShipUpdate su = new ShipUpdate();
					su.setFuel(RNG.random(60));
					su.setScrap(RNG.random(300) + 400);
					update = new Update();
					update.setSu(su);
				} else {
					
				}
				
				System.out.println("Do you want to take the body? (yes/no)");
				in = IOHandler.getInput();
				if(in.equalsIgnoreCase("yes") || in.equalsIgnoreCase("y")) {
					// Add body to cargo hold
				} else {
					System.out.println("You dump the body into space.");
				}
				
			} else {
				System.out.println("You board the ship - TODO");
			}
		} else {
			if(rand < 90) {
				System.out.println("You board the ship - TODO");
			} else {
				System.out.println("You board the ship - TODO");
			}
		}
		//return originalDesc + "\n" + description;
	}
}
