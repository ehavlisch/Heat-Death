package childrenOptions;

import java.util.List;

import options.Option;

import combat.Combat;
import combat.CombatUpdate;

import ai.SuperAI;

import locations.Location;

public class GenericShips extends Option {
	
	private List<SuperAI> agents;
	
	public GenericShips(List<SuperAI> agents) {
		this.agents = agents;
		
		if(agents != null) {
			this.name = agents.size() > 1 ? this.name = "Engage the ships" : "Engage " + agents.get(0).getShip().getName();
		} else {
			this.name = "Engage nothing? Check that GenericShips was created properly.";
		}
	}

	public void execute(Location loc) {
		Combat combat = new Combat(loc);
		//combat.initializeCombat(this.agents, this.player);
		CombatUpdate cu = combat.run();
		
		
	}

}
