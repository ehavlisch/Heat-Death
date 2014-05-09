package ship;

import items.Item;

import java.util.ArrayList;

import shipUpgrades.Upgrade;
import shipUpgrades.UpgradeFamily;

public class ShipUpdateResult {
	
	private int dHull = 0;
	private int nHull;
	private boolean hullFlag = true;

	private int dFuel = 0;
	private int nFuel;
	private boolean fuelFlag = true;

	private int dScrap = 0;
	private int nScrap;
	private boolean scrapFlag = true;
	
	private ArrayList<Item> addedItems;
	private ArrayList<Item> removedItems;
	
	/*
	 * Ship Upgrade Flags
	 *  1 -- Added/Removed Upgrade
	 *  0 -- No Upgrade
	 * -1 -- Failed to add (not for removeUpgrade)
	 */
	private int addUpgradeFlag = 0;
	private Upgrade added = null;
	UpgradeFamily addUpgradeFamily;
	private int removeUpgradeFlag = 0;
	private Upgrade removed = null;
	UpgradeFamily removeUpgradeFamily;
	
	public boolean isSuccess() {
		//TODO add items and upgrade flags too
		return hullFlag && fuelFlag && scrapFlag;
	}

	public int getdHull() {
		return dHull;
	}

	public void setdHull(int dHull) {
		this.dHull = dHull;
	}

	public int getnHull() {
		return nHull;
	}

	public void setnHull(int nHull) {
		this.nHull = nHull;
	}

	public int getdFuel() {
		return dFuel;
	}

	public void setdFuel(int dFuel) {
		this.dFuel = dFuel;
	}

	public int getnFuel() {
		return nFuel;
	}

	public void setnFuel(int nFuel) {
		this.nFuel = nFuel;
	}

	public int getdScrap() {
		return dScrap;
	}

	public void setdScrap(int dScrap) {
		this.dScrap = dScrap;
	}

	public int getnScrap() {
		return nScrap;
	}

	public void setnScrap(int nScrap) {
		this.nScrap = nScrap;
	}
	
	public String getResult() {
		if(isSuccess()) {
			String str = "";
			if(dFuel > 0) {
				str += "Gained " + dFuel + " fuel. Now at " + nFuel + ".\n";
			} else if(dFuel < 0) {
				str += "Lost " + Math.abs(dFuel) + " fuel. Now at " + nFuel + ".\n";
			}
			
			if(dHull > 0) {
				str += "Hull integrity increased by " + dHull + " to " + nHull + ".\n";
			} else if(dHull < 0) {
				str += "Hull integrity decreased by " + Math.abs(dHull) + " to " + nHull + ".\n";
			}
			
			if(dScrap > 0) {
				str += "Gained " + dScrap + " scrap. Now at " + nScrap + ".\n";
			} else if(dScrap < 0) {
				str += "Lost " + Math.abs(dScrap) + " scrap. Now at " + nScrap + ".\n";
			}
			
			if(added != null) {
				str += "Added " + added.getName() + " to the ship.\n";
			}
			
			if(removed != null) {
				str += "Removed " + removed.getName() + " from the ship.\n";
			}
			
			if(addedItems != null && addedItems.size() > 0) {
				for(Item i : addedItems) {
					str += "Added " + i.getName() + " to cargo.\n";
				}
				
			}
			
			if(removedItems != null && removedItems.size() > 0) {
				for(Item i : removedItems) {
					str += "Lost " + i.getName() + " from cargo hold.\n";
				}
			}
			
			if(str.endsWith("\n")) {
				return str.substring(0, str.length()-2);
			}
			
			return str;
		} else {
			String str = "";
			if(!hullFlag) {
				str += "Performing action will destroy ship. Not enough hull integrity.\n";
			}
			
			if(!fuelFlag) {
				str += "Not enough fuel to perform action.\n";
			}
			
			if(!scrapFlag) {
				str += "Not enough scrap to perform action.\n";
			}
			
			if(addUpgradeFlag == -1) {
				str += "Unable to add upgrade. Not enough room.\n";
			}
			
			if(this.removeUpgradeFlag == -1) {
				str += "Unable to remove upgrade, could not find upgrade. Note: This is probably a bug!!!\n";
			}
			
			
			
			if(str.endsWith("\n")) {
				return str.substring(0, str.length()-2);
			}
						
			return str;
		}
	}

	public Upgrade getAdded() {
		return added;
	}

	public void setAdded(Upgrade added) {
		this.addUpgradeFlag = 1;
		this.added = added;
	}

	public Upgrade getRemoved() {
		return removed;
	}

	public void setRemoved(Upgrade removed) {
		this.addUpgradeFlag = 1;
		this.removed = removed;
	}

	public int getAddUpgradeFlag() {
		return addUpgradeFlag;
	}

	public void setAddUpgradeFlag(int addUpgradeFlag) {
		this.addUpgradeFlag = addUpgradeFlag;
	}

	public int getRemoveUpgradeFlag() {
		return removeUpgradeFlag;
	}

	public void setRemoveUpgradeFlag(int removeUpgradeFlag) {
		this.removeUpgradeFlag = removeUpgradeFlag;
	}

	public UpgradeFamily getAddUpgradeFamily() {
		return addUpgradeFamily;
	}

	public void setAddUpgradeFamily(UpgradeFamily addUpgradeFamily) {
		this.addUpgradeFamily = addUpgradeFamily;
	}

	public UpgradeFamily getRemoveUpgradeFamily() {
		return removeUpgradeFamily;
	}

	public void setRemoveUpgradeFamily(UpgradeFamily removeUpgradeFamily) {
		this.removeUpgradeFamily = removeUpgradeFamily;
	}	
	
	public void addRemovedItem(Item i) {
		if(removedItems == null) {
			removedItems = new ArrayList<Item>();
		}
		removedItems.add(i);
	}
	
	public void addItem(Item i) {
		if(addedItems == null) {
			addedItems = new ArrayList<Item>();
		}
		addedItems.add(i);
	}

	public boolean isHullFlag() {
		return hullFlag;
	}

	public void setHullFlag(boolean hullFlag) {
		this.hullFlag = hullFlag;
	}

	public boolean isFuelFlag() {
		return fuelFlag;
	}

	public void setFuelFlag(boolean fuelFlag) {
		this.fuelFlag = fuelFlag;
	}

	public boolean isScrapFlag() {
		return scrapFlag;
	}

	public void setScrapFlag(boolean scrapFlag) {
		this.scrapFlag = scrapFlag;
	}
}
