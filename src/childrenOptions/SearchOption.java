package childrenOptions;

import java.io.Serializable;

import player.PlayerUpdate;
import ship.ShipUpdate;

public class SearchOption implements Serializable {

	private PlayerUpdate pu;
	private ShipUpdate su;
	private int randomThreshold;
	private String result;
	
	public SearchOption() {
		this.randomThreshold = 0;
		this.result = "Nothing Interesting Happened. Default Search Option Result";
	}
	
	public String toString() {
		return this.result;
	}
	
	public SearchOption(PlayerUpdate pu, ShipUpdate su, int randomThreshold, String result) {
		this.randomThreshold = randomThreshold;
		this.result = result;
		
		if(pu == null) {
			this.pu = new PlayerUpdate();
		} else {
			this.pu = pu;
		}
		
		if(su == null) {
			this.su = new ShipUpdate();
		} else {
			this.su = su;
		}
		
	}
	
	public PlayerUpdate getPu() {
		return pu;
	}
	
	public void setPu(PlayerUpdate pu) {
		this.pu = pu;
	}
	
	public ShipUpdate getSu() {
		return su;
	}
	
	public void setSu(ShipUpdate su) {
		this.su = su;
	}
	
	public int getRandomThreshold() {
		return randomThreshold;
	}
	
	public void setRandomThreshold(int randomThreshold) {
		this.randomThreshold = randomThreshold;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
