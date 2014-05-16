package uiElements;

import java.awt.BorderLayout;

import javax.swing.JTextArea;

import locations.Location;

@SuppressWarnings("serial")
public class LocationPanel extends ParentPanel {
	
	public LocationPanel(Location loc) {
		super();
		this.setLayout(new BorderLayout(0, 0));

		JTextArea locationTextArea = new JTextArea(loc.toString());
		locationTextArea.setLineWrap(true);
		
		this.add(locationTextArea);
	}
}
