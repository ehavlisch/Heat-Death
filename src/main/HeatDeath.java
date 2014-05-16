package main;

import java.util.ArrayList;
import java.util.List;

import childrenItems.BasicFuelContainer;

import ai.*;

import combat.*;
import filesystem.GameFileManager;

import locations.Location;
import locations.LocationStatus;
import locations.LocationUpdateResult;
import locations.Sector;
import locations.World;
import player.Player;
import player.PlayerUpdateResult;
import ship.*;
import shipUpgrades.*;
import interiors.Interior;
import items.*;

import options.Option;

public class HeatDeath {

	protected Location current;
	protected Player player;
	protected int worldSize;
	protected int worldDensity;
	protected World world;
	protected Sector sector;

	protected boolean debug;

	protected GameFileManager fileManager;

	// 1.change arrayLists for connecting locations, options, and interiors to
	// maps to make lookups faster
	// 2.add a global id generation function in randomizer to assign each
	// location, option a unique id
	// 3.use the unique id to lookup things from the maps more quickly than
	// iterating through each array list
	// Is this necessary any more? The list or map would be traversed to display
	// all possible options
	// at a given location, using the locationButton and optionButton should
	// replace the need for this.

	// TODO add saving - in progress
	// Still need to add:
	// Player saving
	// Listing saved games
	// Loading save game

	// TODO graphical UI - in progress
	// Functional option buttons
	// Functional wormholes
	// Functional ship management
	// Remove panel generation from HeatDeathUI to make the class less massive

	// TODO change the way worlds are generated so the whole world isn't always
	// in memory.'
	// ideas: add a global seed that the world generation is based on. This will
	// allow the same world to be generated from
	// the existing nodes (that may have player interaction) and the seed even
	// after exiting.
	// this could make daily challenges like spelunky possible
	// idea 2: instead of generating all the locations at startup, add a
	// probability of a location occurring and just dynamically generate the
	// world
	// this would mean no two worlds could be the same, no daily challenges like
	// spelunky
	// idea 3: generate sectors that are mini worlds - small enough to reside in
	// memory. When the player changes sectors, save down one sector to hd.
	// then we can load the new sector into memory. Changing sectors might be
	// expensive, but we can keep less world in memory at one time.
	// we can randomly add wormholes to other sectors depending on the number of
	// locations visited without a connection to another sector
	// world size can explicitly be the number of sectors generated. World
	// density can be the number of wormholes in each sector
	// the initial world generation might get bad
	// the world can consist of a map<SectorName, List<String> locations> and
	// whatever sector the player is currently in

	// TODO depreciate command line functions from HeatDeath
	// TODO depreciate all command line IO functions in IOHandler

	// TODO Change how we load the localization strings to allow the user to
	// change languages

	public static void main(String[] args) {

		HeatDeath heatDeath = new HeatDeath();
		heatDeath.newGameQuick();
		// heatDeath.newGame();
		heatDeath.runGame();

	}

	public void runGame() {

		// Test Combat
		/*
		 * Combat c = new Combat(current); player.ship.applyUpgrade(new
		 * WeaponLaserSm(), new ShipUpdateResult());
		 * player.ship.applyUpgrade(new WeaponLaserSm(), new
		 * ShipUpdateResult()); player.ship.applyUpgrade(new WeaponLaserSm(),
		 * new ShipUpdateResult()); player.ship.applyUpgrade(new
		 * WeaponLaserSm(), new ShipUpdateResult());
		 * 
		 * ArrayList<SuperAI> ais = new ArrayList<SuperAI>(2);
		 * 
		 * 
		 * Ship shipOne = new ShipEagle("Pirate Vessel 'Eagle'");
		 * shipOne.applyUpgrade(new WeaponLaserSm(), new ShipUpdateResult());
		 * 
		 * ais.add(new AgressiveAI(shipOne));
		 * 
		 * Ship shipTwo = new ShipSwordfish("Pirate Vessel 'Swordfish'");
		 * shipTwo.applyUpgrade(new WeaponLaserSm(), new ShipUpdateResult());
		 * 
		 * ais.add(new AgressiveAI(shipTwo));
		 * 
		 * c.initializeCombat(ais, player);
		 * 
		 * CombatUpdate cu = c.run(); current = cu.getRetreatLocation();
		 * player.update(cu.getPu()); player.ship.update(cu.getSu());
		 * 
		 * System.exit(1);
		 */

		String in;

		System.out.println(player.toString());

		current = sector.getFirstLocation(worldSize, worldDensity);

		// Main Game Loop
		while (player.getEnergy() > 0 && player.ship.getHull() > 0) {
			current.printLocation();
			in = IOHandler.getInput();
			in = index(in);
			in = travel(in);
			in = travelInterior(in);
			in = globalOptions(in);
			in = option(in);
			unrecognized(in);
		}

		if (player.getEnergy() <= 0) {
			System.out.println("Your stumble and you feel yourself fall as the world fades to black.");
		} else if (player.ship.getHull() <= 0) {
			System.out.println("Your ship's integrity collapses and you are crushed as the gravity well implodes.");
		} else {
			System.out.println("Unknown cause of death.");
		}
	}

	// TODO depreciate, use UI instead
	public void newGameQuick() {

		player = new Player(Randomizer.randomName());
		fileManager = new GameFileManager();
		fileManager.setP(player);
		fileManager.fileManagerClean();
		fileManager.fileManagerMakeGameFiles();

		worldSize = 30;
		worldDensity = 15;
		player.ship.setName(Randomizer.randomName());
		world = new World(worldSize, worldDensity, fileManager);
		sector = world.getActiveSector();
		debug = true;
	}

	// TODO depreciate, use UI instead
	public void newGame() {

		String in;
		debug = false;

		System.out.println("What is your name?");
		in = IOHandler.getInput();
		player = new Player(in);

		fileManager = new GameFileManager();
		fileManager.setP(player);
		fileManager.fileManagerMakeGameFiles();

		System.out.println("Name your ship:");
		in = IOHandler.getInput();
		player.ship.setName(in);

		//
		int worldSizeCap = 10;

		System.out.println("World Size? (1 to " + worldSizeCap + ")");
		System.out.println("1 -- Very Small");
		System.out.println("5 -- Normal");
		System.out.println("10 -- Huge");
		int index = IOHandler.getIndex(IOHandler.getInput());
		while (index < 0 || index > worldSizeCap) {
			System.out.println("Invalid world size");
			index = IOHandler.getIndex(IOHandler.getInput());
		}

		worldSize = index;
		index = -1;

		if (worldSize == 1) {
			worldDensity = 0;
		} else {
			System.out.println("World Density? (1 - " + worldSize + ")");
			index = IOHandler.getIndex(IOHandler.getInput());
			while (index < 0 || index > worldSize) {
				System.out.println("Invalid world density");
				in = IOHandler.getInput();
				index = IOHandler.getIndex(in);
			}
			worldDensity = index;
		}

		System.out.println("Creating world ...");
		world = new World(worldSize, worldDensity, fileManager);
		sector = world.getActiveSector();
		System.out.println("Done");

		if (debug) {
			System.out.println(world.getSize() + " locations created");
			System.out.println("Init Done");
		}
	}

	public String index(String in) {
		int input = IOHandler.getIndex(in);

		if (input >= 0 && input < current.getOptions().size()) {
			in = current.getOptions().get(input);
		}

		return in;
	}

	public String option(String option) {
		for (int i = 0; i < current.getNumOptions(); i++) {
			if (option.equalsIgnoreCase(current.getOption(i).getName())) {

				Option opt = current.getOption(i);

				updatePlayerShip(opt);

				warnings();
				return "";
			}
		}
		return option;
	}

	// TODO depreciate, use update instead
	public void updatePlayerShip(Option opt) {
		opt.execute(current);
		Update update = opt.getUpdate();

		PlayerUpdateResult pur = player.update(update.getPu());
		if (pur.isSuccess()) {
			ShipUpdateResult sur = player.ship.update(update.getSu());
			if (sur.isSuccess()) {

				if (update.getLu() != null) {
					fileManager.fileManagerSaveSector(sector);
					String sectorName = world.lookupSectorNameById(update.getLu().getSector());
					sector = fileManager.loadSector(sectorName);
					sector.getWormholeLocation(update.getLu().getWormholeId());
				}

				String result = pur.getResult();
				if (!result.equalsIgnoreCase("")) {
					System.out.println(result);
				}
				result = sur.getResult();
				if (!result.equalsIgnoreCase("")) {
					System.out.println(result);
				}
			} else {
				player.undoUpdate(pur);
				System.out.println(sur.getResult());
			}
		} else {
			if (pur.getResult() != null) {
				System.out.println(pur.getResult());
			}
		}
	}

	public UpdateResult update(Update update) {
		if (update == null)
			System.out.println("Severe: update null crash incoming");

		UpdateResult ur = new UpdateResult();
		PlayerUpdateResult pur = player.update(update.getPu());
		if (pur.isSuccess()) {
			ShipUpdateResult sur = player.ship.update(update.getSu());
			if (sur.isSuccess()) {
				LocationUpdateResult lur = world.update(update.getLu(), fileManager);

				if (lur.isSuccess()) {
					if (lur.isNewLocation()) {
						current = lur.getLoc();
					}
					if (lur.isNewSector()) {
						sector = world.getActiveSector();
					}

					if (current.getUsed() == LocationStatus.Unused || current.getUsed() == LocationStatus.Unvisited) {
						current.setUsed(LocationStatus.Visited);
						sector.addConnection(current, worldSize, worldDensity);
						sector.addUsedConnection(current, worldSize, worldDensity);
					}

					player.applyUpdate(update.getPu());
					player.ship.applyUpdate(update.getSu());
				}

				ur = new UpdateResult(pur, sur, lur);
			} else {
				ur = new UpdateResult(pur, sur);
			}
		} else {
			ur = new UpdateResult(pur);
		}
		return ur;
	}

	// TODO depreciate, move into update
	public String travelInterior(String in) {
		Interior interior;
		ArrayList<Interior> interiorConnections = current.getInteriorConnections();
		if (interiorConnections != null && interiorConnections.size() > 0) {
			for (int i = 0; i < current.getInteriorConnections().size(); i++) {
				interior = current.getInteriorConnection(i);
				if (interior.getName().equalsIgnoreCase(in)) {
					int energyCost = interior.getEnergyCost();
					if (player.getEnergy() - energyCost <= 0) {
						// Deadly action to player
						System.out.println("Cannot enter " + interior.getName() + ". You do not have enough energy.");
						return "";
					} else {
						// Ok to go inside
						current = interior;
						player.subEnergy(interior.getEnergyCost());
						return "";
					}
				}
			}
		}
		return in;
	}

	// TODO depreciate, moved into interior
	/*
	 * Traveling function Arguments: input string of desired destination
	 * 
	 * The input string is checked against the names of all the connections if
	 * one of them matches, attempt to travel to that destination
	 * 
	 * Travel can fail if the player is too low on fuel or energy
	 */
	public String travel(String location) {
		Location test;
		for (int i = 0; i < current.getConnections().size(); i++) {
			test = current.getConnection(i);
			if (test != null && location.equalsIgnoreCase(test.getName())) {

				if (current.isInterior()) {
					int energyCost = ((Interior) current).getEnergyCost();
					if (player.getEnergy() - energyCost <= 0) {
						System.out.println("Cannot get to " + test.getName() + ". You do not have enough energy.");
						return "";
					} else {
						player.subEnergy(((Interior) current).getEnergyCost());
						current = test;
						return "";
					}
				}
				// Deduct Fuel to travel
				int fuelConsumption = player.ship.getEfficiency();
				fuelConsumption *= 1 + player.ship.overloaded();

				if (player.ship.getFuel() >= fuelConsumption) {
					current = test;
					player.ship.subFuel(fuelConsumption);

					// If the world hasn't been visited, add some connections
					if (current.getUsed().equals(LocationStatus.Unvisited)) {
						sector.addConnection(current, worldSize, worldDensity);
						sector.addUsedConnection(current, worldSize, worldDensity);
						current.setUsed(LocationStatus.Visited);
					}

					if (player.getEnergy() > 5) {
						// Enough energy to survive trip
						// printLocation(current);
						player.subEnergy(5);
						warnings();
					} else {
						System.out.println("You collapse before arriving. Game Over");
						// TODO handle exiting better
						System.exit(1);
					}
					return "";
				} else {
					// Not enough fuel to travel
					System.out.println("Out of Fuel -- Cannot Travel to " + test.getName());
					return "";
				}
			}
		}
		return location;
	}

	// TODO depreciate, no longer support text options
	public String globalOptions(String option) {

		if (option.equalsIgnoreCase("quit")) {

			System.out.println("Quitting");
			System.exit(1);
		}

		// if (option.equalsIgnoreCase("eat")) {
		//
		// System.out.println("Attempting to eat");
		// int amount = player.eat();
		// if (amount > 0)
		// System.out.println("Gained " + amount + " energy from eating.");
		// player.addEnergy(amount);
		// return "";
		// }

		if (option.equalsIgnoreCase("refuel")) {
			System.out.println("Attempting to refuel from cargo.");
			if (player.ship.getFuel() < player.ship.getMaxFuel()) {
				ShipUpdateResult sur = player.ship.refuel();
				if (sur.getdFuel() > 0) {
					System.out.println("Refueled " + sur.getdFuel() + " from the cargo. Now at " + sur.getnFuel() + ".");
					return "";
				} else {
					System.out.println("No fuel in cargo.");
					return "";
				}
			} else {
				System.out.println("Fuel currently full.");
				return "";
			}
		}

		if (option.equalsIgnoreCase("repair")) {

			System.out.println("How much do you want to repair? (all, #)");
			String in = IOHandler.getInput();
			if (in.equalsIgnoreCase("all")) {
				player.ship.repair();
			} else {
				int amount = IOHandler.getIndex(in);

				if (amount > 0) {
					player.ship.repair(amount);
					return "";
				} else {
					System.out.println("Invalid amount");
					return "";
				}
			}

		}

		if (option.equalsIgnoreCase("manageShip")) {
			manageShip();
			return "";
		}

		if (option.equalsIgnoreCase("energy")) {
			System.out.println("Current Energy: " + player.getEnergy());
			return "";
		}

		if (option.equalsIgnoreCase("fuel")) {
			System.out.println("Current Fuel: " + player.ship.getFuel());
			return "";

		}

		if (option.equalsIgnoreCase("ship")) {
			System.out.println(player.ship.toString());
			return "";
		}

		if (option.equalsIgnoreCase("player")) {
			System.out.println(player.toString());
			return "";
		}

		// DEBUG
		if (option.equalsIgnoreCase("progress") && debug) {
			sector.progress();
			return "";
		}

		// DEBUG
		if (option.equalsIgnoreCase("dumpWorld") && debug) {
			sector.dumpWorld();
			return "";
		}
		// DEBUG
		if (option.equalsIgnoreCase("addFuelContainer") && debug) {
			player.ship.addInventory(new BasicFuelContainer());
			return "";
		}

		// DEBUG
		// if (option.equalsIgnoreCase("food") && debug) {
		//
		// System.out.println("Giving the player a simple ration");
		// player.addItem(new SimpleRation());
		// return "";
		// }

		// DEBUG
		if (option.equalsIgnoreCase("givefuel") && debug) {
			player.ship.addFuel(490);
			return "";
		}

		// DEBUG
		if (option.equalsIgnoreCase("giveMoney") && debug) {
			player.addMoney(1000);
			return "";
		}
		return option;
	}

	public void warnings() {
		if (player.getEnergy() < 10) {
			System.out.println("Warning: Energy Critical - Your body is shutting down");
		}
		if (player.ship.getFuel() < player.ship.getEfficiency() * 3) {
			System.out.println("Warning: Fuel Critical -- " + player.ship.getFuel() + " remaining");
		}
		if (player.ship.overloaded() > 0) {
			System.out.println("Warning: Ship Overloaded! Fuel consumption increased.");
		}
	}

	public void unrecognized(String in) {
		if (!in.equalsIgnoreCase("")) {
			System.out.println("Unrecognized instruction: '" + in + "'");
		}
	}

	public void manageShip() {
		System.out.println("What do you want to manage?");
		System.out.println("0 -- Inventory");
		System.out.println("1 -- Upgrades");
		System.out.println("2 -- Scrap");
		String in = IOHandler.getInput();
		int index = IOHandler.getIndex(in);

		if (index == 0) {
			in = "inventory";
		} else if (index == 1) {
			in = "upgrades";
		} else if (index == 2) {
			in = "scrap";
		} else {
			in = "invalid";
		}

		if (in.equalsIgnoreCase("inventory")) {
			manageShipInventory();
		} else if (in.equalsIgnoreCase("upgrades")) {
			manageShipUpgrades();
		} else if (in.equalsIgnoreCase("scrap")) {
			manageShipScrap();
		} else if (in.equalsIgnoreCase("invalid")) {
			System.out.println("Invalid choice.");
		}
	}

	public void manageShipInventory() {
		System.out.println(player.ship.listInventory(true));
		String in = IOHandler.getInput();
		int index = IOHandler.getIndex(in);

		if (index > -1 && index < player.ship.getInventory().size()) {
			ShipUpdate su = player.ship.getInventory().get(index).interact();
			ShipUpdateResult sur = player.ship.update(su);
			String str = sur.getResult();
			System.out.println(str);
		} else {
			// traverse through the inventory trying to match the name? or just
			// invalid...
		}
	}

	public void manageShipUpgrades() {

		System.out.println("Which family of upgrades?");

		String[] manageOptions = { "Generic", "Engine", "Shield", "Hull", "Weapon", "Other" };

		int index = IOHandler.getIndex(IOHandler.createOptionsTable(manageOptions));

		List<Upgrade> upgrades = player.ship.getUpgrades(UpgradeFamily.lookup(index));

		if (upgrades.size() == 0) {
			System.out.println("No " + UpgradeFamily.lookup(index).toString() + " upgrades to manage.");
		} else {
			ArrayList<String> upgradeOptions = new ArrayList<String>(upgrades.size());

			for (Upgrade u : upgrades) {
				upgradeOptions.add(u.getName());
			}

			index = IOHandler.getIndex(IOHandler.createOptionsTable(upgradeOptions));

			System.out.println("TODO: Manage upgrades, remove, install, etc");
		}
	}

	public void manageShipScrap() {
		System.out.println("What do you want to do?");
		System.out.println("You have " + player.ship.getScrap() + " scrap.");
	}

	public void setCurrentLocation(Location loc) {
		current = loc;
	}

	public Location getCurrent() {
		return current;
	}

	public Player getPlayer() {
		return player;
	}

	public int getWorldSize() {
		return worldSize;
	}

	public int getWorldDensity() {
		return worldDensity;
	}

	public World getWorld() {
		return world;
	}

	public Sector getSector() {
		return sector;
	}

	public boolean isDebug() {
		return debug;
	}
	
	public void setWorldSize(int worldSize) {
		this.worldSize = worldSize;
	}
	
	public void setWorldDensity(int worldDensity) {
		this.worldDensity = worldDensity;
	}
}
