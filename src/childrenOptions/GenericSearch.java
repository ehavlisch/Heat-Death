package childrenOptions;

import options.Option;
import player.PlayerUpdate;
import ship.ShipUpdate;
import locations.Location;
import main.RNG;
import main.Update;

public class GenericSearch extends Option {

	private SearchOptionContainer soc;
		
	public GenericSearch(String name, SearchOptionContainer searchOptionContainer) {
		this.name = name;
		soc = searchOptionContainer;
	}
	
	public GenericSearch(String name, SearchOptionContainer soc, boolean repeatable) {
		this.name = name;
		this.soc = soc;
		this.repeatable = repeatable;
	}

	public void execute(Location loc) {
		int random = RNG.random(100);
		
		String result = null;
		
		for(SearchOption option : soc.getOptions()) {
			if(random > option.getRandomThreshold()) {
				result = option.getResult();
				PlayerUpdate pu = option.getPu();
				ShipUpdate su = option.getSu();
				update = new Update();
				update.setPu(pu);
				update.setSu(su);
				break;
			}
		}
		if(result == null) {
			result = "Nothing Interesting happened.";
			update = new Update();
		}
		
		if(!repeatable) {
			loc.removeOption(this);
		} else {
			repeated += repeated + 1;
			if(repeated > 100) {
				result += " There is nothing left to find.";
				loc.removeOption(this);
			}
		}
				
		System.out.println(result);
	}
}
