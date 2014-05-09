package combat;

import ship.ShipVisualStatus;

public class CombatDamageResult {
	//TODO use accuracy and evade to apply a potential damage reduction
	private int damageReduction = 0;
	
	private boolean hit = true;
	private int damageInflicted = 0;
	private boolean targetDestroyed = false;
	private ShipVisualStatus targetVisualStatus;
	
	public int getDamageReduction() {
		return damageReduction;
	}
	
	public void setDamageReduction(int damageReduction) {
		this.damageReduction = damageReduction;
	}
	
	public int getDamageInflicted() {
		return damageInflicted;
	}
	
	public void setDamageInflicted(int damageInflicted) {
		this.damageInflicted = damageInflicted;
	}

	public boolean isTargetDestroyed() {
		return targetDestroyed;
	}

	public void setTargetDestroyed(boolean targetDestroyed) {
		this.targetDestroyed = targetDestroyed;
	}

	public ShipVisualStatus getTargetVisualStatus() {
		return targetVisualStatus;
	}

	public void setTargetVisualStatus(ShipVisualStatus targetVisualStatus) {
		this.targetVisualStatus = targetVisualStatus;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	
}
