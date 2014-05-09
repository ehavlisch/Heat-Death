package ui;

import interiors.Interior;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import options.Option;

import locations.Location;
import locations.LocationStatus;
import locations.LocationUpdate;
import locations.LocationUpdateResult;
import locations.World;
import main.HeatDeath;
import main.L;
import main.Randomizer;
import main.Update;
import main.UpdateResult;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import filesystem.GameFileManager;
import filesystem.GameOptions;
import gameOptions.Resolution;

import player.Player;
import player.PlayerUpdate;
import player.PlayerUpdateResult;
import ship.ShipUpdate;
import ship.ShipUpdateResult;

public class HeatDeathUI extends HeatDeath {
	
	private int frameWidth = 800;
	private int frameHeight = 600;

	private JFrame mainFrame;
	
	private JPanel northPanel;
	
	private JPanel centerPanel;

	private JPanel southPanel;
	
	private JScrollPane leftScrollPane;
	private JPanel leftOptionPanel;

	private JScrollPane rightScrollPane;
	private JPanel rightOptionPanel;
	
	private JMenuBar mainMenuBar;
	private JMenu fileMenu;
	
	private Object displayLock = new Object();
	private Map<UiZone, JPanel> updateDisplay;
	private Map<UiZone, JPanel> previousDisplay;
	private JPanel overlay;
	
	private JPanel newGamePanel;
	private JTextField newGameNameTextField;
	private JTextField newGameShipTextField;
	private JSpinner newGameWorldSizeSpinner;
	private JSpinner newGameWorldDensitySpinner;
	
	private JPanel optionsPanel;
	private JComboBox<Resolution> resolutionComboBox;
	
	private JPanel shipPanel;
	
	private JPanel locationPanel;
	
	private JPanel locationOptionsPanel;
	
	private JPanel manageShipPanel;
	
	private JPanel manageCargoPanel;
	
	private JPanel manageUpgradesPanel;

	private JPanel warningPanel;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeatDeathUI window = new HeatDeathUI();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public HeatDeathUI() {
		fileManager = new GameFileManager();
		GameOptions gameOptions = fileManager.loadGameOptions();
		if(gameOptions != null) {
			frameWidth = gameOptions.getScreenWidth() == null? 800 : gameOptions.getScreenWidth();
			frameHeight = gameOptions.getScreenHeight() == null? 600 : gameOptions.getScreenHeight();
		}
		
		initialize();		
	}
	
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle(L.gameName);
		mainFrame.setBounds(100, 100, frameWidth, frameHeight);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Menu */
		mainMenuBar = new JMenuBar();
		mainFrame.setJMenuBar(mainMenuBar);
		
		fileMenu = new JMenu(L.menuFile);
		mainMenuBar.add(fileMenu);
		
		JMenuItem newGameMenu = new JMenuItem(L.newGameButton);
		newGameMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initNewGamePanel();
				player = null;
				world = null;
				sector = null;
				current = null;
				HashMap<UiZone, JPanel> toDisplay = new HashMap<UiZone, JPanel>();
				toDisplay.put(UiZone.Center, newGamePanel);
				updateDisplay(toDisplay);
			}
		});
		
		fileMenu.add(newGameMenu);
		
		JMenuItem optionsMenu = new JMenuItem(L.menuOptions);
		optionsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initOptionsPanel();
				showOverlay(optionsPanel);
			}
		});
		
		fileMenu.add(optionsMenu);
		
		/* Right Scroll panel */
		rightScrollPane = new JScrollPane();
		mainFrame.getContentPane().add(rightScrollPane, BorderLayout.EAST);
		
		rightScrollPane.setVisible(false);

		/* Right Option Panel */
		rightOptionPanel = new JPanel();
		rightScrollPane.setViewportView(rightOptionPanel);
				
		/* North Panel */
		northPanel = new JPanel();
		mainFrame.getContentPane().add(northPanel, BorderLayout.NORTH);
		
		northPanel.setVisible(false);
		
		/* South Panel */
		southPanel = new JPanel();
		mainFrame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		southPanel.setVisible(false);
		
		/* Center Panel */
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(0, 0));
		mainFrame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		centerPanel.setVisible(false);
		
		/* Left panel */
		leftScrollPane = new JScrollPane();
		mainFrame.getContentPane().add(leftScrollPane, BorderLayout.WEST);

		leftScrollPane.setVisible(false);

		/* Left Option Panel */
		leftOptionPanel = new JPanel();
		leftScrollPane.setViewportView(leftOptionPanel);
	}
	
	public void newGame(String name, String ship, int worldSize, int worldDensity) {

		debug = true;
		
		player = new Player(name, ship);
		
		fileManager.setP(player);
		if(debug) fileManager.fileManagerClean();
		fileManager.fileManagerMakeGameFiles();

		world = new World(worldSize, worldDensity, fileManager);
		sector = world.getActiveSector();

		current = sector.getFirstLocation(worldSize, worldDensity);
		
		initLocationOptionsPanel();
		initShipPanel();
		initLocationPanel();
		
		Map<UiZone, JPanel> toDisplay = new HashMap<UiZone, JPanel>();
		toDisplay.put(UiZone.West, locationOptionsPanel);
		toDisplay.put(UiZone.East, shipPanel);
		toDisplay.put(UiZone.Center, locationPanel);
		updateDisplay(toDisplay);
	}
	
	public void initOptionsPanel() {
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new FormLayout(new ColumnSpec[] {
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
		}, new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
		}));
		
		JLabel optionsLabel = new JLabel(L.optionsLabel);

		optionsPanel.add(optionsLabel, "2, 2");
		
		resolutionComboBox = new JComboBox<Resolution>();
		resolutionComboBox.setModel(new DefaultComboBoxModel<Resolution>(Resolution.values()));
		optionsPanel.add(resolutionComboBox, "2, 4");
		
		JButton optionsSaveButton = new JButton(L.saveButton);
		optionsSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving options");
				
				Resolution res = (Resolution) resolutionComboBox.getSelectedItem();
				frameWidth = res.getWidth();
				
				frameHeight = res.getHeight();
				
				mainFrame.setSize(frameWidth, frameHeight);
				mainFrame.revalidate();
				
				GameOptions gameOptions = new GameOptions(frameWidth, frameHeight);
				
				fileManager.saveGameOptions(gameOptions);
				removeOverlay();
			}
		});
		optionsPanel.add(optionsSaveButton, "2, 14");
	}
	
	public boolean initWarningsPanel() {
		boolean warning = false;
		StringBuilder sb = new StringBuilder();
		if (player.getEnergy() < 10) {
			warning = true;
			sb.append(L.warningPlayerEnergy);
		}
		if(player.ship.getFuel() < player.ship.getEfficiency()) {
			warning = true;
			sb.append(L.warningShipFuelEmpty);
		} else if (player.ship.getFuel() < (player.ship.getEfficiency() * 3) + 1) {
			warning = true;
			sb.append(L.warningShipFuelLow);
		} 
		if (player.ship.overloaded() > 0) {
			warning = true;
			sb.append(L.warningShipOverloaded);
		}
		if(warning) {
			warningPanel = new JPanel();
			
			JLabel warningLabel = new JLabel(sb.toString());
			warningLabel.setForeground(Color.RED);
			warningPanel.add(warningLabel);
			
			JButton closeWarningButton = new JButton(L.closeButton);
			closeWarningButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearSinglePanel(UiZone.South);
				}
			});
			warningPanel.add(closeWarningButton);
		}
		return warning;
	}
	
	public void initLocationOptionsPanel() {
		locationOptionsPanel = new JPanel();
		locationOptionsPanel.setLayout(new GridLayout(0,1));
		
		if(current == null) System.out.println("ERROR: Current location null");
		if(current.getConnections() == null) System.out.println("ERROR: getConnections null");
		
		for(Location loc : current.getConnections()) {
			TravelButton newButton = new TravelButton(loc.getName(), loc);
			newButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TravelButton sourceButton = (TravelButton) e.getSource();
					Location target = sourceButton.getLoc();
					Update update = new Update();
					
					LocationUpdate lu = new LocationUpdate(target);
					update.setLu(lu);
					
					//TODO deal with interiors again
//					if(current.isInterior()) {
//						int energyCost = ((Interior) current).getEnergyCost();
//						if(player.getEnergy() - energyCost <= 0) {
//							System.out.println("Cannot get to " + target.getName() + ". You do not have enough energy.");
//						} else {
//							player.subEnergy(((Interior) current).getEnergyCost());
//							current = target;
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
					
					update(update);
					
//					if (player.ship.getFuel() >= fuelConsumption) {
//						current = target;
//						player.ship.subFuel(fuelConsumption);
//						
//						// If the world hasn't been visited, add some connections
//						if (current.getUsed().equals(LocationStatus.Unvisited)) {
//							sector.addConnection(current, worldSize, worldDensity);
//							sector.addUsedConnection(current, worldSize, worldDensity);
//							current.setUsed(LocationStatus.Visited);
//						}
//
//						if (player.getEnergy() > 5) {
//							// Enough energy to survive trip
//							//printLocation(current);
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
			locationOptionsPanel.add(newButton);
		}
		List<Option> optionsList = current.getOptionsList();
		if(optionsList != null) {
			for(Option opt : optionsList) {
				OptionButton newButton = new OptionButton(opt.getName(), opt);
				newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OptionButton sourceButton = (OptionButton) e.getSource();
						//System.out.println(sourceButton);
						Option option = sourceButton.getOption();
						option.execute(current);
						Update update = option.getUpdate();
						
						if(update != null) {
							UpdateResult ur = update(update);
							System.out.println(ur.getPur().getResult());
							System.out.println(ur.getSur().getResult());
							System.out.println(ur.getLur().getResult());
						} else {
							System.out.println("Update null");
						}
					}
				});
				locationOptionsPanel.add(newButton);
			}
		}
		
		locationOptionsPanel.setVisible(true);
	}
	
	public UpdateResult update(Update update) {
		UpdateResult ur = super.update(update);
		initShipPanel();
		initLocationOptionsPanel();
		initLocationPanel();
		
		Map<UiZone, JPanel> toDisplay = new HashMap<UiZone, JPanel>();
		toDisplay.put(UiZone.West, locationOptionsPanel);
		toDisplay.put(UiZone.East, shipPanel);
		toDisplay.put(UiZone.Center, locationPanel);
		
		updateDisplay(toDisplay);
		
		return ur;
	}
	
	private void addStatusField(JPanel panel, String label, Integer value, Integer max) {
		if(max != null) {
			JLabel l = new JLabel(label + value + "/" + max);
			panel.add(l);
		} else {
			JLabel l = new JLabel(label + value);
			panel.add(l);
		}

	}
	
	public void initShipPanel() {
		shipPanel = new JPanel();
		shipPanel.setLayout(new GridLayout(0,1));
		
		JLabel playerNameLabel = new JLabel(player.getName());
		shipPanel.add(playerNameLabel);
		
		addStatusField(shipPanel, L.playerEnergyLabel, player.getEnergy(), player.getMaxEnergy());
		addStatusField(shipPanel, L.playerMoneyLabel, player.getMoney(), null);
		
		JLabel shipNameLabel = new JLabel(player.ship.getName());
		shipPanel.add(shipNameLabel);
		
		addStatusField(shipPanel, L.shipHullLabel, player.ship.getHull(), player.ship.getMaxHull());
		addStatusField(shipPanel, L.shipFuelLabel, player.ship.getFuel(), player.ship.getMaxFuel());
		addStatusField(shipPanel, L.shipLoadLabel, player.ship.getLoad(), player.ship.getCapacity());
		addStatusField(shipPanel, L.shipScrapLabel, player.ship.getScrap(), null);
		addStatusField(shipPanel, L.shipEfficiencyLabel, player.ship.getEfficiency(), null);

		JButton manageShipButton = new JButton(L.manageShipButton);
		manageShipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initManageShipPanel();
				updateSinglePanel(UiZone.East, manageShipPanel);
			}
		});
		
		shipPanel.add(manageShipButton);
		
		JButton manageCargoButton = new JButton(L.manageCargoButton);
		manageCargoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initManageCargoPanel();
				updateSinglePanel(UiZone.East, manageCargoPanel);
			}
		});
		shipPanel.add(manageCargoButton);
		
		JButton manageUpgradesButton = new JButton(L.manageUpgradesButton);
		manageUpgradesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initManageUpgradesPanel();
				updateSinglePanel(UiZone.East, manageUpgradesPanel);
			}
		});
		shipPanel.add(manageUpgradesButton);
		
		shipPanel.setVisible(true);
	}
	
	public JButton createBackToShipPanelButton() {
		JButton back = new JButton(L.backButton);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initShipPanel();
				updateSinglePanel(UiZone.East, shipPanel);
			}
		});
		
		return back;
	}
	
	public void initManageShipPanel() {
		manageShipPanel = new JPanel();
		manageShipPanel.setLayout(new FormLayout(new ColumnSpec[] {
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
		}, new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
		}));
		
		int currentRow = 2;
		
		JLabel shipNameLabel = new JLabel(player.ship.getName());
		manageShipPanel.add(shipNameLabel, CC.xyw(2,  currentRow, 3)); //"2, " + currentRow + ", 5");
		
		JLabel shipClassLabel = new JLabel(player.ship.getClassName());
		manageShipPanel.add(shipClassLabel, CC.xyw(5, currentRow, 3));
		
		currentRow += 2;
		
		JLabel repairLabel = new JLabel(L.repairShipLabel);
		manageShipPanel.add(repairLabel, "2, " + currentRow);
		
		JLabel currentHullLabel = new JLabel(player.ship.getHull() + "/" + player.ship.getMaxHull());
		manageShipPanel.add(currentHullLabel, "6, " + currentRow);
		
		currentRow += 2;
		
		if(player.ship.getHull() < player.ship.getMaxHull()) {
			JTextField repairTextField = new JTextField();
			manageShipPanel.add(repairTextField, "2, " + currentRow);
			
			JButton repairAmountButton = new JButton(L.repairShipButton);
			manageShipPanel.add(repairAmountButton, "4, " + currentRow);
			
			JButton repairAllButton = new JButton(L.repairAllButton);
			manageShipPanel.add(repairAllButton, "6, " + currentRow);
			
			currentRow += 2;
		}
		
		JTextField salvageHullTextField = new JTextField();
		salvageHullTextField.setToolTipText(L.salvageHullTooltip);
		manageShipPanel.add(salvageHullTextField, "2, " + currentRow);
		
		JButton salvageHullButton = new JButton(L.salvageHullButton);
		salvageHullButton.setToolTipText(L.salvageHullTooltip);
		manageShipPanel.add(salvageHullButton, "4, " + currentRow);
		
		currentRow += 2;
		
		JLabel fuelManageLabel = new JLabel(L.manageFuelLabel);
		manageShipPanel.add(fuelManageLabel, "2, " + currentRow);
		
		currentRow += 2;
		
		JTextField dumpFuelTextField = new JTextField();
		manageShipPanel.add(dumpFuelTextField, "2, " + currentRow);
		
		JButton dumpFuelButton = new JButton(L.dumpFuelButton);
		manageShipPanel.add(dumpFuelButton, "4, " + currentRow);
		
		JButton dumpAllFuelButton = new JButton (L.dumpAllFuelButton);
		manageShipPanel.add(dumpAllFuelButton, "6, " + currentRow);
		
		currentRow += 2;
		
		manageShipPanel.add(createBackToShipPanelButton(), "4, " + currentRow);
	}
	
	public void initManageCargoPanel() {
		manageCargoPanel = new JPanel();
		JLabel testLabel = new JLabel("Test Manage Cargo Panel");
		manageCargoPanel.add(testLabel);
		
		manageCargoPanel.add(createBackToShipPanelButton());
	}
	
	public void initManageUpgradesPanel() {
		manageUpgradesPanel = new JPanel();
		
		JLabel testLabel = new JLabel("Test Manage Upgrades Panel");
		manageUpgradesPanel.add(testLabel);
		
		manageUpgradesPanel.add(createBackToShipPanelButton());
	}
	
	public void initLocationPanel() {
		locationPanel = new JPanel();
		locationPanel.setLayout(new BorderLayout(0, 0));
		
		JTextArea locationTextArea = new JTextArea(current.toString());
		locationTextArea.setLineWrap(true);

		locationPanel.add(locationTextArea, BorderLayout.CENTER);
	}
	
	public void initNewGamePanel() {
		newGamePanel = new JPanel();
		
		newGamePanel.setLayout(
			new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				}));
			
		JLabel newGameNameLabel = new JLabel(L.newGameNameLabel);
		newGamePanel.add(newGameNameLabel, "2, 2, right, default");
		
		newGameNameTextField = new JTextField();
		newGamePanel.add(newGameNameTextField, "4, 2, fill, default");
		newGameNameTextField.setColumns(10);
		
		JLabel newGameShipLabel = new JLabel(L.newGameShipNameLabel);
		newGamePanel.add(newGameShipLabel, "2, 4, right, default");
		
		newGameShipTextField = new JTextField();
		newGamePanel.add(newGameShipTextField, "4, 4, fill, default");
		newGameShipTextField.setColumns(10);
		
		JLabel newGameWorldSizeLabel = new JLabel(L.newGameWorldSizeLabel);
		newGamePanel.add(newGameWorldSizeLabel, "2, 6");
		
		newGameWorldSizeSpinner = new JSpinner();
		newGameWorldSizeSpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		newGamePanel.add(newGameWorldSizeSpinner, "4, 6");
		
		JLabel newGameWorldDensityLabel = new JLabel(L.newGameWorldDensityLabel);
		newGamePanel.add(newGameWorldDensityLabel, "2, 8");
		
		newGameWorldDensitySpinner = new JSpinner();
		newGameWorldDensitySpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		newGamePanel.add(newGameWorldDensitySpinner, "4, 8");
		
		JButton newGameCreateButton = new JButton(L.newGameButton);
		newGameCreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean ok = true;
				String gameName = newGameNameTextField.getText();
				String shipName = newGameShipTextField.getText();
				if(gameName == null || gameName.length() == 0) {
					newGameNameTextField.setText(Randomizer.randomName());
					ok = false;
				} else if(shipName == null || shipName.length() == 0) {
					newGameShipTextField.setText(Randomizer.randomName());
					ok = false;
				}
				if(ok) {
					worldSize = (int) newGameWorldSizeSpinner.getValue();
					worldDensity = (int) newGameWorldDensitySpinner.getValue();
					newGame(gameName, shipName, worldSize, worldDensity);							
				}
			}
		});
		
		newGamePanel.add(newGameCreateButton, "4, 10");

	}
	public void clearSinglePanel(UiZone uiZone) {
		synchronized(displayLock) {			
			if(uiZone == UiZone.Center) {
//				System.out.println("Updating Only Center Display");
				centerPanel.setVisible(false);
				JPanel panelToRemove = previousDisplay.get(uiZone);
				if(panelToRemove != null) {
					centerPanel.remove(panelToRemove);
				}
			} else if(uiZone == UiZone.West) {	
//				System.out.println("Updating Only W Display");
				leftScrollPane.setVisible(false);
			} else if(uiZone == UiZone.East) {	
//				System.out.println("Updating Only E Display");
				rightScrollPane.setVisible(false);
			} else if(uiZone == UiZone.North) {
//				System.out.println("Updating Only N Display");
				northPanel.setVisible(false);
				JPanel panelToRemove = previousDisplay.get(uiZone);
				if(panelToRemove != null) {
					northPanel.remove(panelToRemove);
				}
			} else if(uiZone == UiZone.South) {	
//				System.out.println("Updating Only S Display");
				southPanel.setVisible(false);
				JPanel panelToRemove = previousDisplay.get(uiZone);
				if(panelToRemove != null) {
					southPanel.remove(panelToRemove);
				}
			}
			
			if(previousDisplay != null) {
				previousDisplay.remove(uiZone);
			}
		}
	}
	
	public void updateSinglePanel(UiZone uiZone, JPanel panelToDisplay) {
		synchronized(displayLock) {
			if(previousDisplay == null) {
				previousDisplay = new HashMap<UiZone, JPanel>();
			}
			if(uiZone == UiZone.Center) {
//				System.out.println("Updating Only Center Display");
				centerPanel.setVisible(false);
				JPanel panelToRemove = previousDisplay.get(uiZone);
				if(panelToRemove != null) {
					centerPanel.remove(panelToRemove);
				}
				centerPanel.add(panelToDisplay);
				centerPanel.setVisible(true);
			} else if(uiZone == UiZone.West) {	
//				System.out.println("Updating Only W Display");
				leftScrollPane.setVisible(false);
				leftScrollPane.setViewportView(panelToDisplay);
				leftScrollPane.setVisible(true);
			} else if(uiZone == UiZone.East) {	
//				System.out.println("Updating Only E Display");
				rightScrollPane.setVisible(false);
				rightScrollPane.setViewportView(panelToDisplay);
				rightScrollPane.setVisible(true);
			} else if(uiZone == UiZone.North) {
//				System.out.println("Updating Only N Display");
				northPanel.setVisible(false);
				JPanel panelToRemove = previousDisplay.get(uiZone);
				if(panelToRemove != null) {
					northPanel.remove(panelToRemove);
				}
				northPanel.add(panelToDisplay);
				northPanel.setVisible(true);
			} else if(uiZone == UiZone.South) {	
//				System.out.println("Updating Only S Display");
				southPanel.setVisible(false);
				JPanel panelToRemove = previousDisplay.get(uiZone);
				if(panelToRemove != null) {
					southPanel.remove(panelToRemove);
				}
				southPanel.add(panelToDisplay);
				southPanel.setVisible(true);
			}
			previousDisplay.put(uiZone, panelToDisplay);
			mainFrame.revalidate();
		}
	}
	public void showOverlay(JPanel display) {
		synchronized(displayLock) {
			if(previousDisplay == null) {
				System.out.println("Warn: no Previous display saved");
			} else {
				for(Entry<UiZone, JPanel> e : previousDisplay.entrySet()) {
					if(e.getKey() == UiZone.Center) {
//						System.out.println("Saving Center Display");
						centerPanel.setVisible(false);
						centerPanel.remove(e.getValue());		
					} else if(e.getKey() == UiZone.West) {
//						System.out.println("sav W Display");
						leftScrollPane.setVisible(false);
						leftScrollPane.setViewport(null);
					} else if(e.getKey() == UiZone.East) {
//						System.out.println("sv E Display");
						rightScrollPane.setVisible(false);
						rightScrollPane.setViewport(null);
					} else if(e.getKey() == UiZone.North) {	
//						System.out.println("sv N Display");
						northPanel.setVisible(false);
						northPanel.remove(e.getValue());
					} else if(e.getKey() == UiZone.South) {	
//						System.out.println("sv S Display");
						southPanel.setVisible(false);
						southPanel.remove(e.getValue());
					}
				}
			}
			fileMenu.setVisible(false);
			overlay = display;
			centerPanel.add(display);
			mainFrame.revalidate();
			centerPanel.setVisible(true);
		}
	}
	
	public void removeOverlay() {
		synchronized(displayLock) {
			if(overlay == null) {
				System.out.println("Error: Trying to clear nonexistant overlay");
				return;
			} else {
				centerPanel.setVisible(false);
				centerPanel.remove(overlay);
				overlay = null;
				mainFrame.revalidate();
				fileMenu.setVisible(true);				
			}
		}
		if(previousDisplay != null) {
			updateDisplay(previousDisplay);
		}
	}
	
	public void updateDisplay(Map<UiZone, JPanel> toDisplay) {
		synchronized(displayLock) {
			updateDisplay = toDisplay;
		}
		updateDisplay();
	}

	public void updateDisplay() {
		try {
			synchronized(displayLock) {
				if(previousDisplay != null) {
					System.out.println("Clearing the previous display");
					for(Entry<UiZone, JPanel> e : previousDisplay.entrySet()) {
						if(e.getKey() == UiZone.Center) {
//							System.out.println("Clearing Center Display");
							centerPanel.setVisible(false);
							centerPanel.remove(e.getValue());		
						} else if(e.getKey() == UiZone.West) {
//							System.out.println("Clearing W Display");
							leftScrollPane.setVisible(false);
							leftScrollPane.setViewport(null);
						} else if(e.getKey() == UiZone.East) {
//							System.out.println("Clearing E Display");
							rightScrollPane.setVisible(false);
							rightScrollPane.setViewport(null);
						} else if(e.getKey() == UiZone.North) {	
//							System.out.println("Clearing N Display");
							northPanel.setVisible(false);
							northPanel.remove(e.getValue());
						} else if(e.getKey() == UiZone.South) {	
//							System.out.println("Clearing S Display");
							southPanel.setVisible(false);
							southPanel.remove(e.getValue());
						}
					}
				}
				if(updateDisplay != null) {
					System.out.println("Updating the display");
					for(Entry<UiZone, JPanel> e : updateDisplay.entrySet()) {
						if(e.getKey() == UiZone.Center) {
//							System.out.println("Updating Center Display");
							centerPanel.setVisible(false);
							centerPanel.add(e.getValue());
							centerPanel.setVisible(true);		
						} else if(e.getKey() == UiZone.West) {	
//							System.out.println("Updating W Display");
							leftScrollPane.setVisible(false);
							leftScrollPane.setViewportView(e.getValue());
							leftScrollPane.setVisible(true);
						} else if(e.getKey() == UiZone.East) {	
//							System.out.println("Updating E Display");
							rightScrollPane.setVisible(false);
							rightScrollPane.setViewportView(e.getValue());
							rightScrollPane.setVisible(true);
						} else if(e.getKey() == UiZone.North) {
//							System.out.println("Updating N Display");
							northPanel.add(e.getValue());
							northPanel.setVisible(true);
						} else if(e.getKey() == UiZone.South) {	
//							System.out.println("Updating S Display");
							southPanel.add(e.getValue());
							southPanel.setVisible(true);
						}
					}
					previousDisplay = updateDisplay;
					updateDisplay = null;
					mainFrame.revalidate();
				} else {
					System.out.println("SEVERE: Update Display was called, but updateDisplay was null");
				}
			}
		} catch(Exception e) {
			System.out.println("SEVERE: Exception updating display");
			e.printStackTrace();
		}
	}
}
