package combat;

public class FactionTable {
	private int numFactions = 5;
	
	private int[][] factionTable;
	
	public FactionTable() {
		this.factionTable = new int[numFactions][numFactions];

		/*
		 * 
		 * Reputation
		 * -100 Hated
		 * -50	Disliked
		 * -10	Upset
		 * 0 	Neutral
		 * 10	Friendly
		 * 50	Supportive
		 * 100	Loyal
		 * 
		 */
		
		/* 
		 * Factions:
		 * 0 -- Default
		 *  used for non initialized factions
		 * 1 -- Player
		 * 2 -- Pirate
		 * 	attack non pirates on sight
		 * 3 -- Vigilante
		 * 	pirate hunters
		 * 4 -- Neutral
		 * 	traders, etc
		 */
		
		// Set pirates to hate everyone except other pirates, especially hate the vigilantes
		for(int i = 1; i < numFactions; i++) {
			factionTable[2][i] = -100;
			factionTable[i][2] = -100;

		}
		factionTable[2][2] = 10;
		factionTable[2][3] *= 2;
		factionTable[3][2] *= 2;
		
		// Vigilantes
		// They will work together to destroy pirates
		factionTable[3][3] = 50;
		
		// Neutral
		// They will only attack pirates unless provoked

	}
	
	public int getRelation(int mine, int theirs) {
		return factionTable[mine][theirs];
	}
	
	public int adjustRelation(int mine, int theirs, int amount) {
		factionTable[mine][theirs] += amount;
		factionTable[theirs][mine] += amount;
		return factionTable[mine][theirs];
	}
	
	public String lookup(int factionId) {
		switch(factionId) {
			case 0: return "Default";
			case 1: return "Player";
			case 2: return "Pirate";
			case 3: return "Vigilante";
			case 4: return "Neutral";
			default: return "ERROR: add the faction to the lookup in FactionTable";
		}
	}
}
