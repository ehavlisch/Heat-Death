package childrenAIs;

import ai.SuperAI;
import main.RNG;
import ship.Ship;
import combat.CombatAction;
import combat.CombatShipContainer;

public class AgressiveAI extends SuperAI {

	// Default AI for Pirate vessels
	public AgressiveAI(Ship ship) {
		super(ship);
		this.factionId = 2;
	}

	public CombatAction getAction(CombatShipContainer csc) {
		int random = RNG.random(100);
		
		if(!ship.hasWeapons() || csc.isRetreating() || ship.getHull() < (1/3) * ship.getMaxHull()) {
			return CombatAction.Retreat;
		}
		
		if(random < 80) {
			return CombatAction.Attack;
		} else {
			return CombatAction.Manouver;
		} 
	}

}
