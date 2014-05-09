package combat;

import locations.Location;
import main.Update;
import player.PlayerUpdate;
import ship.ShipUpdate;

public class CombatUpdate extends Update {
	CombatResult cr;
	Location retreatLocation;
		
	public CombatUpdate() {
		su = null;
		pu = null;
		cr = null;
	}
	
	public CombatUpdate(CombatResult cr, ShipUpdate su, PlayerUpdate pu) {
		this.cr = cr;
		this.su = su;
		this.pu = pu;
	}

	public CombatResult getCr() {
		return cr;
	}

	public void setCr(CombatResult cr) {
		this.cr = cr;
	}

	public Location getRetreatLocation() {
		return retreatLocation;
	}

	public void setRetreatLocation(Location retreatLocation) {
		this.retreatLocation = retreatLocation;
	}
}
