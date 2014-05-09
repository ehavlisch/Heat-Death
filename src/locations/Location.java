package locations;

import interiors.Interior;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import combat.CombatEnvironment;
import options.Option;

@SuppressWarnings("serial")
public class Location implements Serializable {
	protected String name;
	protected String desc;
	protected String detailed;

	protected ArrayList<Option> options;

	protected int numConnections;
	protected ArrayList<Location> connections;

	protected CombatEnvironment ce;

	protected LocationStatus used = LocationStatus.Unused;

	protected ArrayList<Interior> interiorConnections;
	protected int numInteriorConnections;

	protected boolean wormhole;

	public void addInteriorConnection(Interior interior) {
		if (interiorConnections == null) {
			interiorConnections = new ArrayList<Interior>();
		}
		interiorConnections.add(interior);
	}

	public String printLocation() {
		String str = "";
		str += this.toString();
		if (options != null) {
			for (Option opt : options) {
				if(opt.getDescription() != null) {
					str += opt.getDescription();
				}
			}
		}

		ArrayList<String> actions = getOptions();
		int i = 0;
		for (; i < actions.size(); i++) {
			str += i + " -- " + actions.get(i) + "\n";
		}

		System.out.println(str);
		return str;
	}

	public void addOption(Option o) {
		if (options == null) {
			options = new ArrayList<Option>();
		}
		options.add(o);
	}

	public void removeOption(Option option) {
		if (options != null) {
			for (int i = 0; i < options.size(); i++) {
				if (option.getName().equalsIgnoreCase(options.get(i).getName())) {
					options.remove(i);
				}
			}
		}
	}

	public String toString() {
		String str = "";

		str += "Name: " + name + "\nDescription: " + desc + "\nDetailed: " + detailed;

		return str;
	}

	public ArrayList<String> getOptions() {
		ArrayList<String> actions = new ArrayList<String>();
		for (Location loc : connections) {
			actions.add(loc.getName());
		}
		
		if (options != null && options.size() > 0) {
			for (Option opt : options) {
				if (!opt.isDisabled()) {
					actions.add(opt.getName());
				}
			}
		}
		
		if (interiorConnections != null && interiorConnections.size() > 0) {
			for (Interior interior : interiorConnections) {
				actions.add(interior.getName());
			}
		}

		return actions;
	}

	public int getNumConnections() {
		if (connections == null) {
			connections = new ArrayList<Location>();
		}
		numConnections = connections.size();
		return numConnections;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public void addLocation(Location loc) {
		if (connections == null) {
			connections = new ArrayList<Location>();
		}
		connections.add(loc);
		numConnections++;
	}

	public Location getConnection(int id) {
		if (connections != null && connections.size() > id) {
			return connections.get(id);
		} else {
			return null;
		}
	}

	public int getNumOptions() {
		if (options != null) {
			return options.size();
		} else {
			return 0;
		}
	}

	public Option getOption(int id) {
		return options.get(id);
	}

	public void setDetailed(String str) {
		detailed = str;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public CombatEnvironment getCe() {
		return ce;
	}

	public void setCe(CombatEnvironment ce) {
		this.ce = ce;
	}

	public LocationStatus getUsed() {
		return used;
	}

	public void setUsed(LocationStatus used) {
		this.used = used;
	}

	public ArrayList<Location> getConnections() {
		return connections;
	}

	public ArrayList<Interior> getInteriorConnections() {
		return interiorConnections;
	}

	public Interior getInteriorConnection(int index) {
		if (interiorConnections != null && index < interiorConnections.size()) {
			return interiorConnections.get(index);
		} else {
			return null;
		}
	}

	public boolean isInterior() {
		return false;
	}

	public boolean isWormhole() {
		return wormhole;
	}

	public void setWormhole(boolean wormhole) {
		this.wormhole = wormhole;
	}

	public List<Option> getOptionsList() {
		return options;
	}
}
