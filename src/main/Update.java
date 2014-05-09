package main;

import java.io.Serializable;

import locations.LocationUpdate;
import player.PlayerUpdate;
import ship.ShipUpdate;

public class Update implements Serializable {
	protected PlayerUpdate pu;
	protected ShipUpdate su;
	protected LocationUpdate lu;
	
	public Update() {
		
	}
	
	public Update(ShipUpdate su) {
		this.su = su;
	}
	
	public Update(ShipUpdate su, PlayerUpdate pu) {
		this.su = su;
		this.pu = pu;
	}
	
	public Update(ShipUpdate su, PlayerUpdate pu, LocationUpdate lu) {
		this.su = su;
		this.pu = pu;
		this.lu = lu;
	}

	public ShipUpdate getSu() {
		return su;
	}

	public void setSu(ShipUpdate su) {
		this.su = su;
	}

	public PlayerUpdate getPu() {
		return pu;
	}

	public void setPu(PlayerUpdate pu) {
		this.pu = pu;
	}

	public LocationUpdate getLu() {
		return lu;
	}

	public void setLu(LocationUpdate lu) {
		this.lu = lu;
	}
}
