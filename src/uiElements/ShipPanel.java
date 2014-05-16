package uiElements;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import player.Player;

import ui.HeatDeathUI;
import ui.UiZone;

import main.Lang;

@SuppressWarnings("serial")
public class ShipPanel extends ParentPanel {

	public ShipPanel(HeatDeathUI parent) {
		super(parent);
		
		this.setLayout(new GridLayout(0,1));
		
		Player p = heatDeathUI.getPlayer();
		
		JLabel playerNameLabel = new JLabel(p.getName());
		this.add(playerNameLabel);
		
		addStatusField(Lang.playerEnergyLabel, heatDeathUI.getPlayer().getEnergy(), p.getMaxEnergy());
		addStatusField(Lang.playerMoneyLabel, heatDeathUI.getPlayer().getMoney(), null);
		
		JLabel shipNameLabel = new JLabel(p.ship.getName());
		this.add(shipNameLabel);
		
		addStatusField(Lang.shipHullLabel, p.ship.getHull(), p.ship.getMaxHull());
		addStatusField(Lang.shipFuelLabel, p.ship.getFuel(), p.ship.getMaxFuel());
		addStatusField(Lang.shipLoadLabel, p.ship.getLoad(), p.ship.getCapacity());
		addStatusField(Lang.shipScrapLabel, p.ship.getScrap(), null);
		addStatusField(Lang.shipEfficiencyLabel, p.ship.getEfficiency(), null);

		JButton manageShipButton = new JButton(Lang.manageShipButton);
		manageShipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				heatDeathUI.initManageShipPanel();
				//heatDeathUI.updateSinglePanel(UiZone.East, manageShipPanel);
			}
		});
		
		this.add(manageShipButton);
		
		JButton manageCargoButton = new JButton(Lang.manageCargoButton);
		manageCargoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				heatDeathUI.initManageCargoPanel();
				//heatDeathUI.updateSinglePanel(UiZone.East, manageCargoPanel);
			}
		});
		this.add(manageCargoButton);
		
		JButton manageUpgradesButton = new JButton(Lang.manageUpgradesButton);
		manageUpgradesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				heatDeathUI.initManageUpgradesPanel();
				//heatDeathUI.updateSinglePanel(UiZone.East, manageUpgradesPanel);
			}
		});
		this.add(manageUpgradesButton);
		
		this.setVisible(true);
	}
	
	private void addStatusField(String label, Integer value, Integer max) {
		if(max != null) {
			JLabel l = new JLabel(label + value + "/" + max);
			this.add(l);
		} else {
			JLabel l = new JLabel(label + value);
			this.add(l);
		}

	}
}
