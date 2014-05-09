package locations;

public enum LocationRegistry {
	//Name(uniqueId, Name shown when creating (plural), Class Name, maxPerSector, chanceOfGeneration)
	NeutronStar(0, true, "Neutron Stars", "NeutronStar", 5, 99, 100), 
	Asteroid(1, true, "Asteroids", "Asteroid", 30, 100, 100), 
	Planet(2, true, "Planets", "Planet", 18, 100, 100), 
	JunkTrader(3, false, "Junk Traders", "JunkTrader", 6, 90, 100), 
	ResearchStation(4, false, "Research Stations", "ResearchStation", 3, 50, 100), 
	AbanondedSpaceStation(5, false, "Abandoned Space Stations", "AbandonedSpaceStation", 3, 50, 100), 
	Nebula(6, true, "Nebulas", "Nebula", 2, 80, 100);



	private int id;
	private String pluralName;
	private boolean nameNumber;	
	private String className;
	private int maxPerSector;
	private int genChance;
	private int baseChance;
	
	private LocationRegistry(int id, boolean nameNumber, String pluralName, String className, int maxPerSector, int genChance, int baseChance) {
		this.id = id;
		this.nameNumber = nameNumber;
		this.pluralName = pluralName;
		this.className = className;
		this.maxPerSector = maxPerSector;
		this.genChance = genChance;
		this.baseChance = baseChance;
	}

	public boolean isNameNumber() {
		return nameNumber;
	}

	public void setNameNumber(boolean nameNumber) {
		this.nameNumber = nameNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	public int getMaxPerSector() {
		return maxPerSector;
	}

	public void setMaxPerSector(int maxPerSector) {
		this.maxPerSector = maxPerSector;
	}

	public int getGenChance() {
		return genChance;
	}

	public void setGenChance(int genChance) {
		this.genChance = genChance;
	}

	public int getBaseChance() {
		return baseChance;
	}

	public void setBaseChance(int baseChance) {
		this.baseChance = baseChance;
	}

}
