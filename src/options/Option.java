package options;

import items.Item;

import java.io.Serializable;
import java.util.ArrayList;

import shipUpgrades.Upgrade;
import locations.Location;
import main.Update;

@SuppressWarnings("serial")
public abstract class Option implements Serializable {
	
	protected Update update;
	
	protected boolean disabled = false;
	
	protected String name;
	protected String description;
	
	protected ArrayList<Option> addedOptions;
	protected ArrayList<Option> removedOptions;
	
	protected ArrayList<Item> addedItems;
	protected ArrayList<Item> removedItems;
	
	protected ArrayList<Upgrade> addedUpgrades;
	protected ArrayList<Upgrade> removedUpgrades;
	
	protected boolean repeatable = false;
	protected int repeated;
	
	public abstract void execute(Location loc);
	
	public void addUpgrade(Upgrade u) {
		if(addedUpgrades == null) {
			addedUpgrades = new ArrayList<Upgrade>();
		}
		addedUpgrades.add(u);
	}
	
	public void removeUpgrade(Upgrade u) {
		if(addedUpgrades != null) {
			for(int i = 0; i < addedUpgrades.size(); i++) {
				if(addedUpgrades.get(i).getName().equalsIgnoreCase(u.getName())) {
					addedUpgrades.remove(i);
				}
			}
		}
	}
	
	public void addRemovedUpgrade(Upgrade u) {
		if(removedUpgrades == null) {
			removedUpgrades = new ArrayList<Upgrade>();
		}
		removedUpgrades.add(u);
	}
	
	public void delRemovedUpgrade(Upgrade u) {
		if(removedUpgrades != null) {
			for(int i = 0; i < removedUpgrades.size(); i++) {
				if(removedUpgrades.get(i).getName().equalsIgnoreCase(u.getName())) {
					removedUpgrades.remove(i);
				}
			}
		}
	}
	
	public void addOption(Option o) {
		if(addedOptions == null) {
			addedOptions = new ArrayList<Option>();
		}
		addedOptions.add(o);
	}
	
	public void removeOption(Option o) {
		if(addedOptions == null) {
			return;
		}
		for(int i = 0; i < addedOptions.size(); i++) {
			if(o.getName().equalsIgnoreCase(addedOptions.get(i).getName())) {
				addedOptions.remove(i);
			}
		}
	}
	
	public void removeOption(int index) {
		if(addedOptions != null) {
			addedOptions.remove(index);
		}
	}
	
	public void addRemovedOption(Option o) {
		if(removedOptions == null) {
			removedOptions = new ArrayList<Option>();
		}
		removedOptions.add(o);
	}
	public void delRemovedOption(Option o) {
		for(int i = 0; i < removedOptions.size(); i++) {
			if(removedOptions.get(i).getName().equalsIgnoreCase(o.getName())) {
				removedOptions.remove(i);
			}
		}
	}
	
	public void delRemovedOption(int index) {
		if(removedOptions != null) {
			removedOptions.remove(index);
		}
	}
	

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public boolean isRepeatable() {
		return repeatable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public int getRepeated() {
		return repeated;
	}

	public void setRepeated(int repeated) {
		this.repeated = repeated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}
}
