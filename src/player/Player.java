package player;

import childrenShips.ShipEagle;
import combat.FactionTable;

import ship.*;

public class Player {
	private String name;
	private int money;
	private int energy;
	private int maxEnergy;
	
	private FactionTable factionTable;
	
	public Ship ship;
	
	public Player(String n)
	{
		name = n;
		money = 100;
		energy = 300;
		maxEnergy = energy;
		factionTable = new FactionTable();
		String shipName = "Alpha";
		ship = new ShipEagle(shipName);
	}
	
	public Player(String name, String ship) {
		this.name = name;
		money = 100;
		energy = 300;
		maxEnergy = energy;
		factionTable = new FactionTable();
		this.ship = new ShipEagle(ship);
	}

	public String toString()
	{
		String str = "Player information\n";
		str += "Name: " + name + "\n";
		str += "Money: " + money + "\n";
		str += "Energy: " + energy + "\n";		
		return str;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void subMoney(int money) {
		this.money -= money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void subEnergy(int energy) {
		this.energy -= energy;
	}
	
	public void addEnergy(int energy) {
		this.energy += energy;
	}
	
	public PlayerUpdateResult update(PlayerUpdate pu) {
		if(pu != null) {
			
			PlayerUpdateResult pur = new PlayerUpdateResult();
			
			if(energy <= pu.getEnergy()) {
				pur.setEnergyFlag(false);
			} else {
				pur.setdEnergy(pu.getEnergy());
				pur.setnEnergy(energy + pu.getEnergy());
			}
			
			if(money + pu.getMoney() < 0) {
				pur.setMoneyFlag(false);
			} else {
				pur.setdMoney(pu.getMoney());
				pur.setnMoney(money + pu.getMoney());
			}
			return pur;
		} else {
			return new PlayerUpdateResult();
		}
	}
	
	public void applyUpdate(PlayerUpdate pu) {
		if(pu != null) {
			energy += pu.getEnergy();
			money += pu.getMoney();
		}
	}
	
	//TODO Depreciate
	public void undoUpdate(PlayerUpdateResult pur) {
		energy -= pur.getdEnergy();
		money -= pur.getdMoney();
	}

	public FactionTable getFactionTable() {
		return factionTable;
	}

	public void setFactionTable(FactionTable factionTable) {
		this.factionTable = factionTable;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}
}
