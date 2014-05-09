package childrenAIs;

import ai.SuperAI;
import main.RNG;
import ship.Ship;
import combat.CombatAction;
import combat.CombatShipContainer;

public class DefaultAI extends SuperAI {
	
	// Default ai for a semi-cautious strategic agent
	public DefaultAI(Ship ship) {
		super(ship);
		this.factionId = 4;
	}

	public CombatAction getAction(CombatShipContainer csc) {
		if(!ship.hasWeapons() || csc.isRetreating() || ship.getHull() < .5 * ship.getMaxHull()) {
			return CombatAction.Retreat;
		}
		int random = RNG.random(100);
		
		if(random < 20) {
			return CombatAction.Evade;
		} else if(random < 40 && !csc.isManouvering()) {
			return CombatAction.Manouver;
		} else {
			return CombatAction.Attack;
		}
	}
}
