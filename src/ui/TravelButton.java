package ui;

import javax.swing.JButton;

import locations.Location;

@SuppressWarnings("serial")
public class TravelButton extends JButton {
	private Location location;
	
	public TravelButton(String name, Location location) {
		super(name);
		this.location = location;
	}

	public Location getLoc() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
