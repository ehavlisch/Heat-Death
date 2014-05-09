package combat;

public enum CombatAction {

    Retreat("Retreat"), Attack("Attack"), Evade("Evade"), Manouver("Manouver"), Disengage("Disengage"), Surrender("Surrender");
    /*
     * Retreat
     * 		attempt to flee from the battle
     * Attack 
     * 		attack the enemy using all weapons
     * Evade 
     * 		increase dodge chance for a few turns
     * Maneuver
     * 		increase damage on next attack
     * Disengage
     * 		no other targets to attack, disengage
     * Surrender
     * 		beg for your life
     * 
     */
    private String ca;
    
    private CombatAction(String string) {
    	this.setCa(string);
    }

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

}
