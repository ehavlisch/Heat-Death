package player;

import java.io.Serializable;

public class PlayerUpdate implements Serializable {
	protected int money;
	protected int energy;
	
	public PlayerUpdate() {
		money = 0;
		energy = 0;
	}
	
	public PlayerUpdate(int e) {
		money = 0;
		energy = e;
	}
	
	public PlayerUpdate(int m, int e) {
		money = m;
		energy = e;
	}
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public String getMessage() {
		String str = "";
		if (energy > 0) {
			str += "Player gained " + energy + " energy.\n";
		} else if (energy < 0) {
			str += "Player lost " + Math.abs(energy) + " energy.\n";
		}
		if(money > 0) {
			str += "Player gained " + money + " money.\n";
		} else if(money < 0) {
			str += "Player lost " + money + " money.\n";
		}
		return str;
	}
}
