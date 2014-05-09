package items;

import java.io.Serializable;

import main.Update;
import player.PlayerUpdate;
import ship.ShipUpdate;

public abstract class Item implements Serializable {
	protected int mass = 1;
	protected String name = "Default Name";
	
	protected Update u;
	protected ShipUpdate su;
	protected PlayerUpdate pu;
	
	public abstract ShipUpdate interact();
	
	public String toString()
	{
		return "Name: " + name + "\tMass:" + mass + "\n";
	}	
	
	public String getName()
	{
		return name;
	}


	public int getMass() {
		return mass;
	}


	public void setMass(int mass) {
		this.mass = mass;
	}


	public ShipUpdate getSu() {
		return su;
	}


	public PlayerUpdate getPu() {
		return pu;
	}


	public void setName(String name) {
		this.name = name;
	}

	public Update getU() {
		return u;
	}

	public void setU(Update u) {
		this.u = u;
	}

	public void setSu(ShipUpdate su) {
		this.su = su;
	}

	public void setPu(PlayerUpdate pu) {
		this.pu = pu;
	}
}
