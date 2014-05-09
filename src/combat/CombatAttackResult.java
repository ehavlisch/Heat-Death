package combat;

public class CombatAttackResult {
	private CombatDamageResult cdr;
	
	private CombatShipContainer attacker;
	private CombatShipContainer target;
	
	private boolean wasManouvering;
	private boolean stillManouvering;
	
	
	public String manouveringUpdate() {
		if(wasManouvering) {
			if(stillManouvering) {
				return "You are still manouvering.";
			} else {
				return "You are finished manouvering.";
			}
		} else {
			return null;
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		if(cdr != null) {
			
		}
		return str.toString();
	}

	public CombatDamageResult getCdr() {
		return cdr;
	}

	public void setCdr(CombatDamageResult cdr) {
		this.cdr = cdr;
	}

	public void setWasManouvering(boolean wasManouvering, boolean stillManouvering) {
		this.wasManouvering = wasManouvering;
		this.stillManouvering = stillManouvering;
	}

	public CombatShipContainer getAttacker() {
		return attacker;
	}

	public void setAttacker(CombatShipContainer attacker) {
		this.attacker = attacker;
	}

	public CombatShipContainer getTarget() {
		return target;
	}

	public void setTarget(CombatShipContainer target) {
		this.target = target;
	}
	
}
