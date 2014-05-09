package childrenOptions;

import options.Option;
import ship.ShipUpdate;
import locations.Location;
import main.RNG;
import main.Update;

public class UnstableAsteroid extends Option {

	public UnstableAsteroid()
	{
		name = "Destabilize the asteroid";
		description = "The asteroid appears to be unstable. A well aimed engine burst might shatter the asteroid.\n";
	}
	
	public void execute(Location loc)
	{
		int rand = RNG.random(100);
		if(rand < 50) {
			System.out.println("You angle your ship and fire your engines. The asteroid glows brightly then shatters!");
			System.out.println("You are able to gather some scrap from the debris.");
			description = "The asteroid has been shattered by an engine burst. There are fragments floating all around.\n";
			update = new Update(new ShipUpdate(-1, 0, RNG.random(60) + 1));
		} else if(rand < 90) {
			System.out.println("You angle your ship and fire your engines. The asteroid shatters quickly and violently!");
			System.out.println("Your hull takes some damage, but there was a large vein of fuel in the asteroid.");
			description = "The asteroid has been violently shattered by an engine burst. There are fragments floating all around.\n";
			update = new Update(new ShipUpdate(RNG.random(40) + 20, -(RNG.random(10) + 5), 0));
		} else {
			System.out.println("You angle your ship and fire your engines. The asteroid doesn't shatter as you expected.");
			description = "There are scorch marks on the asteroid from where you tried to shatter the asteroid.\n";
			update = new Update( new ShipUpdate(-1, 0, 0));
		}
		this.setDisabled(true);
	}
}
