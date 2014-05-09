package shipUpgrades;

public enum UpgradeFamily {

    Generic("Generic", 0), Shielding("Shielding", 1), Hull("Hull", 2),
    Engine("Engine", 3), Weapon("Weapon", 4), Other("Other", 5);
    
    private String family;
    private int id;
    
    private UpgradeFamily(String family, int id) {
        this.family = family;
        this.id = id;
    }
    
    public String toString() {
        return family;
    }

	public static UpgradeFamily lookup(int index) {
		
		switch(index) {
			case 0: return UpgradeFamily.Generic;
			case 1: return UpgradeFamily.Shielding;
			case 2: return UpgradeFamily.Hull;
			case 3: return UpgradeFamily.Engine;
			case 4: return UpgradeFamily.Weapon;
			default: return UpgradeFamily.Other;		
		}
		
	}

	public int getId() {
		return id;
	}
}
