package ai;

import ship.Ship;
import combat.CombatAction;
import combat.CombatShipContainer;

public abstract class SuperAI implements AI {
	
	protected Ship ship;
	protected int factionId;
	
	public abstract CombatAction getAction(CombatShipContainer csc);
	
	public SuperAI(Ship ship) {
		this.ship = ship;
	}

	public int getFactionId() {
		return factionId;
	}

	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}
}
