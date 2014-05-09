package combat;

public enum CombatResult {
	Destroyed("Destroyed"), Victory("Victory"), Retreat("Retreat"), Disengage("Disengage");
	
	/*
	 * Destroy
	 *     Player ship was destroyed
	 * Victory
	 *     All hostile ships eliminated
	 * Retreat
	 *     Player retreated
	 * Disengage
	 *     Canceled combat against neutral enemies?
	 */
	
	private String cr;
	
	private CombatResult(String str) {
		setCr(str);
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}
}
