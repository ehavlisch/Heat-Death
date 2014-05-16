package uiElements;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import options.Option;

import player.Player;
import player.PlayerUpdate;

import ship.ShipUpdate;
import ui.HeatDeathUI;

import locations.Location;
import locations.LocationUpdate;
import main.Update;
import main.UpdateResult;

@SuppressWarnings("serial")
public class OptionsPanel extends ParentPanel {
	
	private Player player;
	private Location loc;
	
	public OptionsPanel(Location location, Player p, HeatDeathUI parent) {
		super(parent);
		this.setLayout(new GridLayout(0,1));
		this.player = p;
		this.loc = location;
		
		if(loc == null) System.out.println("ERROR: loc location null");
		if(loc.getConnections() == null) System.out.println("ERROR: getConnections null");
		
		for(Location connection : loc.getConnections()) {
			TravelButton newButton = new TravelButton(connection.getName(), connection);
			newButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TravelButton sourceButton = (TravelButton) e.getSource();
					Location target = sourceButton.getLoc();
					Update update = new Update();
					
					LocationUpdate lu = new LocationUpdate(target);
					update.setLu(lu);
					
					//TODO deal with interiors again
//					if(loc.isInterior()) {
//						int energyCost = ((Interior) loc).getEnergyCost();
//						if(player.getEnergy() - energyCost <= 0) {
//							System.out.println("Cannot get to " + target.getName() + ". You do not have enough energy.");
//						} else {
//							player.subEnergy(((Interior) loc).getEnergyCost());
//							loc = target;
//							System.out.println("Updated target");
//						}
//					}
					
					int fuelConsumption = player.ship.getEfficiency();
					fuelConsumption *= 1 + player.ship.overloaded();
					
					ShipUpdate su = new ShipUpdate();
					su.setFuel(-fuelConsumption);
					update.setSu(su);
					
					PlayerUpdate pu = new PlayerUpdate();
					pu.setEnergy(-5);
					update.setPu(pu);
					
					heatDeathUI.update(update);
					
//					if (player.ship.getFuel() >= fuelConsumption) {
//						loc = target;
//						player.ship.subFuel(fuelConsumption);
//						
//						// If the world hasn't been visited, add some connections
//						if (loc.getUsed().equals(LocationStatus.Unvisited)) {
//							sector.addConnection(loc, worldSize, worldDensity);
//							sector.addUsedConnection(loc, worldSize, worldDensity);
//							loc.setUsed(LocationStatus.Visited);
//						}
//
//						if (player.getEnergy() > 5) {
//							// Enough energy to survive trip
//							//printLocation(loc);
//							player.subEnergy(5);
//							initLocationOptionsPanel();
//
//							Map<UiZone, JPanel> toDisplay = new HashMap<UiZone, JPanel>();
//							toDisplay.put(UiZone.West, locationOptionsPanel);
//							initLocationPanel();
//							toDisplay.put(UiZone.Center, locationPanel);
//							initShipPanel();
//							toDisplay.put(UiZone.East, shipPanel);
//							updateDisplay(toDisplay);
//							
//							if(initWarningsPanel()) {
//								updateSinglePanel(UiZone.South, warningPanel);
//							}
//						} else {
//							System.out.println("You collapse before arriving. Game Over");
//							//TODO handle exiting better, init game over screen, display game over screen
//							System.exit(1);
//						}
//					} else {
//						initWarningsPanel();
//						updateSinglePanel(UiZone.South, warningPanel);
//					}
				}
			});
			this.add(newButton);
		}
		List<Option> optionsList = loc.getOptionsList();
		if(optionsList != null) {
			for(Option opt : optionsList) {
				OptionButton newButton = new OptionButton(opt.getName(), opt);
				newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OptionButton sourceButton = (OptionButton) e.getSource();
						//System.out.println(sourceButton);
						Option option = sourceButton.getOption();
						option.execute(loc);
						Update update = option.getUpdate();
						
						if(update != null) {
							UpdateResult ur = heatDeathUI.update(update);
							System.out.println(ur.getPur().getResult());
							System.out.println(ur.getSur().getResult());
							System.out.println(ur.getLur().getResult());
						} else {
							System.out.println("Update null");
						}
					}
				});
				this.add(newButton);
			}
		}
		
		this.setVisible(true);
	}
}
