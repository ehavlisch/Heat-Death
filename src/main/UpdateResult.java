package main;

import locations.LocationUpdateResult;
import player.PlayerUpdateResult;
import ship.ShipUpdateResult;

public class UpdateResult {
	private PlayerUpdateResult pur;
	private ShipUpdateResult sur;
	private LocationUpdateResult lur;
	
	private boolean success;
	
	public UpdateResult() {
		
	}
	
	public UpdateResult(PlayerUpdateResult pur) {
		this.pur = pur;
		
		this.success = pur.isSuccess();
	}
	
	public UpdateResult(PlayerUpdateResult pur, ShipUpdateResult sur) {
		this.pur = pur;
		this.sur = sur;
		
		this.success = pur.isSuccess() && sur.isSuccess();
	}
	
	public UpdateResult(PlayerUpdateResult pur, ShipUpdateResult sur, LocationUpdateResult lur) {
		this.pur = pur;
		this.sur = sur;
		this.lur = lur;
		
		this.success = pur.isSuccess() && sur.isSuccess() && lur.isSuccess();
	}
	
	public String toString() {
		return pur + " " + sur + " " + lur + ".";
	}
	
	public PlayerUpdateResult getPur() {
		return pur;
	}
	public void setPur(PlayerUpdateResult pur) {
		this.pur = pur;
	}
	public ShipUpdateResult getSur() {
		return sur;
	}
	public void setSur(ShipUpdateResult sur) {
		this.sur = sur;
	}
	public LocationUpdateResult getLur() {
		return lur;
	}
	public void setLur(LocationUpdateResult lur) {
		this.lur = lur;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
