package combat;

public class CombatEnvironment {

	protected int baseDodgeChance;
	protected int damageOverTime;
	protected String name;
	
	public int getBaseDodgeChance() {
		return baseDodgeChance;
	}

	public void setBaseDodgeChance(int baseDodgeChance) {
		this.baseDodgeChance = baseDodgeChance;
	}

	public int getDamageOverTime() {
		return damageOverTime;
	}

	public void setDamageOverTime(int damageOverTime) {
		this.damageOverTime = damageOverTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
