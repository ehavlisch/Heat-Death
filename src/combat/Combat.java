package combat;

import java.util.ArrayList;
import java.util.List;

import ai.SuperAI;

import player.Player;
import player.PlayerUpdate;

import ship.ShipUpdate;
import shipUpgrades.Weapon;
import locations.Location;
import main.IOHandler;
import main.RNG;

public class Combat {
	
	private List<Location> retreatLocations;
	private CombatEnvironment ce;
	private List<CombatShipContainer> cships;
	private boolean playerRetreating;
	private CombatUpdate playerCombatUpdate;
	private List<CombatShipContainer> destroyedCships;
	
	private FactionTable factionTable;
	
	// Constructor create the environment based on the location
	public Combat(Location loc) {
		retreatLocations = new ArrayList<Location>();
		for(int i = 0; i < loc.getNumConnections(); i++) {
			retreatLocations.add(loc.getConnection(i));
		}
		
		ce = loc.getCe();
		if (ce == null) {
			ce = new CERegular();
		}
		
		playerRetreating = false;
		playerCombatUpdate = new CombatUpdate();
		playerCombatUpdate.setSu(new ShipUpdate());
		playerCombatUpdate.setPu(new PlayerUpdate());
		playerCombatUpdate.setRetreatLocation(loc);
	}
	
	// TODO if the players are fighting at an armed station, it can be in the combat
	// create a ship to represent stations, arm them with energy and other vital combat statistics
	public void addStation(SuperAI ai) {
		this.cships.add(new CombatShipContainer(ai));
	}

	// Initiate combat, create combat ship containers for all ships
	public void initializeCombat(List<SuperAI> agents, Player player) {
		
		factionTable = player.getFactionTable();
		
		cships = new ArrayList<CombatShipContainer>(1 + agents.size());
		
		cships.add(new CombatShipContainer(player));
		
		for(SuperAI ai : agents) {
			cships.add(new CombatShipContainer(ai));
		}	
		
		destroyedCships = new ArrayList<CombatShipContainer>(agents.size() - 1);
	}
	
	public CombatUpdate run() {		
		CombatShipContainer next = null;
		

		System.out.println("Combat!");
		System.out.println("The environment is: " + ce.getName() + ".");
		
		// Continue until there are no combat ships left, the player is destroyed, or combat is disengaged no hostile parties
		while(cships != null && cships.size() > 1) {
			next = getNextShip();
			
			// Player Action
			if(next.isPlayerShip()) {
				// If the player is retreating, then end combat, and travel to destination

				if(this.playerRetreating) {
					System.out.println("You retreat to " + this.playerCombatUpdate.getRetreatLocation().getName() + ".");
					ShipUpdate su = new ShipUpdate();
					su.setFuel(next.getShip().getEfficiency());
					this.playerCombatUpdate.setSu(su);
					return this.playerCombatUpdate;
				}

				ArrayList<String> combatOptions = new ArrayList<String>(4);
				combatOptions.add("Attack");
				combatOptions.add("Evade");
				combatOptions.add("Manouver");
				combatOptions.add("Retreat");
				combatOptions.add("Disengage");

				for(CombatShipContainer cship : cships) {
					if(!cship.isPlayerShip() && factionTable.getRelation(1, cship.getFactionId()) < -5) {
						combatOptions.remove(4);
						break;
					}					
				}
				//TODO if no hostile enemies, show disengage option to end combat

				
				System.out.println("It's your turn to move, select an action:");
				int actionIndex = IOHandler.getIndex(IOHandler.createOptionsTable(combatOptions));

				
				if(actionIndex == 0) {
					System.out.println("Attack who?");
					// List Targets - including self
					ArrayList<String> attackOptions = new ArrayList<String>(cships.size());

					for(CombatShipContainer cship : cships) {
						attackOptions.add(cship.getName() + " Faction: " + factionTable.lookup(cship.getFactionId()));
					}
					
					int attackIndex = IOHandler.getIndex(IOHandler.createOptionsTable(attackOptions));
					CombatShipContainer targetCsc = cships.get(attackIndex);
					
					int[] weaponIds = selectWeaponIds(next.getShip().getWeaponUpgrades());

					int relationToTarget = factionTable.getRelation(1, targetCsc.getFactionId());

					if(targetCsc.isPlayerShip()) {
						System.out.println("You attack yourself! It's a bold move, let's see if it pays off.");
					} else {
						// Attacking Allies
						if(relationToTarget > 5) {
							System.out.println("You turn your guns against your ally.");
							System.out.println(targetCsc.getName() + ": What are you doing? You're targeting us! Stand down!");
							factionTable.adjustRelation(1, targetCsc.getFactionId(), -5);
						} else if(relationToTarget >= 0) {
							System.out.println("You turn your guns against a neutral party.");
							System.out.println(targetCsc.getName() + ": What are you doing? You're targeting us! You won't get away with this!");
							factionTable.adjustRelation(1, targetCsc.getFactionId(), -6);
						}
					}
					
					CombatAttackResult car = next.attackWithWeapon(cships.get(attackIndex), weaponIds);
					
					String manouveringUpdate = car.manouveringUpdate();
					if(manouveringUpdate != null) System.out.println(manouveringUpdate);
					
					if(car.getTarget().isPlayerShip() && car.getCdr().isTargetDestroyed()) {
						
						System.out.println("You have successfully destroyed your own ship.");
						playerCombatUpdate.setCr(CombatResult.Destroyed);
						return playerCombatUpdate;
					} else if(car.getCdr().isTargetDestroyed()) {
						System.out.println("The target was destroyed!");
						destroyedCships.add(car.getTarget());
						cships.remove(attackIndex);
					} else if(car.getCdr().isHit()) {
						System.out.println(car.getTarget().getName() + " was hit! Their ship appears " + car.getCdr().getTargetVisualStatus() + ".");
						System.out.println("DEBUG" + " damage done: " + car.getCdr().getDamageInflicted());
						
						// Attacking Allies
						if(relationToTarget > 50) {
							System.out.println(car.getTarget().getName() + " yells over the radio: What the fuck are you doing?! I hope that was a mistake!");
							factionTable.adjustRelation(targetCsc.getFactionId(), next.getFactionId(), -10);
						} else if(relationToTarget > 10) {
							System.out.println(car.getTarget().getName() + " yells over the radio: What the fuck are you doing?! That was no mistake, prepare to fight!");
							factionTable.adjustRelation(targetCsc.getFactionId(), next.getFactionId(), -15);
						}
					} else {
						System.out.println("You missed your target!");
					}
					
				} else if(actionIndex == 1) {
					System.out.println("You begin evasive maneuvers!");
					next.updateDodge();
				} else if(actionIndex == 3) {
					// retreat
					System.out.println("Retreat where?");

					ArrayList<String> retreatOptions = new ArrayList<String>(retreatLocations.size());
					for(Location loc : retreatLocations) {
						retreatOptions.add(loc.getName());
					}
					this.playerRetreating = true;
					this.playerCombatUpdate.setRetreatLocation(retreatLocations.get(IOHandler.getIndex(IOHandler.createOptionsTable(retreatOptions))));
					System.out.println("Destination Acquired! - Preparing to jump!");

				} else if(actionIndex == 2) {
					next.setManouvering(true);
					System.out.println("You are begin agressive manouvers to score critical damage!");
					
				} else if(actionIndex == 4) {
					System.out.println("You radio you are ready to disengage your combat systems.");
					playerCombatUpdate.setCr(CombatResult.Disengage);
					break;
				}
				
			//End Player Action
			//AI Action
			} else {
				//TODO the ai should make a better decision based on other ships it's currently fighting. 
				// Like if it's out numbered 10 - 1, retreat, this only considers itself, 
				// maybe change to getAction(self, this) to reference the entire combat object
				CombatAction ca = next.getAi().getAction(next);

				//System.out.println("An enemy moves!");
				//System.out.println(next.getName() + " -- " + ca);
				
				ArrayList<CombatShipContainer> potentialTargets = getPotentialTargets(next);

				if(next.getDisengage() > 0) {
					ca = CombatAction.Disengage;
				} else if(potentialTargets.size() == 0) {
					ca = CombatAction.Disengage;
				}

				
				if(ca == CombatAction.Attack) {
					
					CombatShipContainer target = null;
					
					if(potentialTargets.size() > 1) {
						target = potentialTargets.get(0);
						for(CombatShipContainer csc : potentialTargets) {
							if(RNG.random(100) > 50) {
								if(csc.getShip().getVisualStatus().getTargetValue() > target.getShip().getVisualStatus().getTargetValue()) {
									target = csc;
								}
							} else {
								if(factionTable.getRelation(next.getFactionId(), target.getFactionId()) > factionTable.getRelation(next.getFactionId(), csc.getFactionId())) {
									target = csc;
								}
							}
						}
					} else if(potentialTargets.size() == 0) {
						System.out.println(next.getName() + "comes over the radio: There's no more need to fight, I'm ready to disengage.");
						next.incrementDisengage();
						ca = CombatAction.Disengage;
						target = null;
					} else {
						target = potentialTargets.get(0);
					}
					
					if(target != null) {
						CombatAttackResult car = next.attack(target);
						if(target.isPlayerShip()) {
							System.out.println("You were attacked by " + next.getName() + "!");
							if(car.getCdr().isTargetDestroyed()) {
								System.out.println("Your ship was destroyed by the attack!");
								playerCombatUpdate.setCr(CombatResult.Destroyed);
								return playerCombatUpdate;
							} else if(car.getCdr().isHit()) {
								System.out.println("Hull integrity decreased by: " + car.getCdr().getDamageInflicted() + ". Now at " + target.getShip().getHull() + "/" + target.getShip().getMaxHull() + ".");
							} else {
								System.out.println("The enemy missed!");
							}
						} else {
							// AI attacked AI
							System.out.println(next.getName() + " attacked " + target.getName() + "!");
							if(car.getCdr().isTargetDestroyed()) {
								System.out.println(next.getName() + " destroyed " + target.getName() + "!");
								destroyedCships.add(target);
								cships.remove(target);
							} else {
								System.out.println(target.getName() + " now appears " + target.getShip().getVisualStatus());
							}
						}
					}
				} else if(ca == CombatAction.Retreat) {
					if(next.isRetreating()) {
						//remove from the ships list
						//TODO move to a connecting planet for continuity?
						System.out.println(next.getName() + " has warped away.");
						this.cships.remove(next);
					} else {
						next.setRetreating(true);
					}
				} else if(ca == CombatAction.Evade) {
					next.updateDodge();
					System.out.println(next.getName() + " begins to manouver.");
				} else if(ca == CombatAction.Manouver) {
					next.setManouvering(true);
					System.out.println(next.getName() + " begins to manouver.");
				} else if(ca == CombatAction.Disengage) {
					//TODO all ships and the player must disengage to end combat
					
					if(next.getDisengage() > 4) {
						next.incrementDisengage();
						if(this.factionTable.getRelation(next.getFactionId(), 1) > 0) {
							System.out.println(next.getName() + ": I'm begging you, I don't want to do this! Disengage your combat systems!");
							this.factionTable.adjustRelation(next.getFactionId(), 1, -3);
						} else {
							System.out.println(next.getName() + ": You have crossed the line, prepare to fight!");
							this.factionTable.adjustRelation(next.getFactionId(), 1, -5);
							next.setDisengage(0);
						}
					} else if(next.getDisengage() > 3) {
						System.out.println(next.getName() + ": You are still preparing for an attack, break off manouvers now!");
						next.incrementDisengage();
						this.factionTable.adjustRelation(next.getFactionId(), 1, -1);
					} else if(next.getDisengage() > 2) {
						System.out.println(next.getName() + ": Still ready to disengage, what is your status?");
						next.incrementDisengage();
					} else if(next.getDisengage() > 0) {
						System.out.println(next.getName()+ ": I repeat, All hostile targets eliminated, I'm ready to disengage my combat systems.");
						next.incrementDisengage();
					} else {
						System.out.println(next.getName()+ ": All hostile targets eliminated, I'm ready to disengage my combat systems.");
						next.setDisengage(1);
					}
				}
			}
			next.updateSpeed();
		} // while there are ship to fight
			
		System.out.println("No more ships to fight");
		if(destroyedCships.size() > 0) {
			System.out.println("Scuttling destroyed ships...");
			for(CombatShipContainer csc : destroyedCships) {
				System.out.println("Looting: " + csc.getName());
				playerCombatUpdate.getSu().addScrap(RNG.random(csc.getShip().getMaxHull()/2));
				playerCombatUpdate.getSu().addFuel(RNG.random(csc.getShip().getMaxFuel()/4));
			}
		}

		return this.playerCombatUpdate;
	}
	
	private CombatShipContainer getNextShip() {
		int low = cships.get(0).getSpeed();
		int lowIndex = 0;
		
		for(int i = 1; i < cships.size(); i++) {
			if(cships.get(i).getSpeed() < low) {
				low = cships.get(i).getSpeed();
				lowIndex = i;
			}
		}
		return cships.get(lowIndex);
	}
	
	private int[] selectWeaponIds(List<Weapon> weapons) {
		System.out.println("Fire which weapons?");
		
		String[] options = {"Full Barrage", "Select Weapons"};
		int weaponSelect = IOHandler.getIndex(IOHandler.createOptionsTable(options));
				
		if(weaponSelect == 0) {
			int[] weaponIds = new int[weapons.size()];
			for(int i = 0; i < weapons.size(); i++) {
				weaponIds[i] = i;
			}
			return weaponIds;
		}
		
		System.out.println("Select which weapons to use:");
		
		List<String> weaponOptions = new ArrayList<String>(weapons.size());
		for(Weapon wep : weapons) {
			weaponOptions.add(wep.getName() + " - " + wep.getFiringCost() + " - " + wep.getDamage());
		}
		
		int[] weaponIds = IOHandler.getIndexMultiple(IOHandler.createOptionsTable(weaponOptions), true);
				
		return weaponIds;
	}
	
	public ArrayList<CombatShipContainer> getPotentialTargets(CombatShipContainer current) {
		ArrayList<CombatShipContainer> potentialTargets = new ArrayList<CombatShipContainer>(cships.size()-1);
		for(CombatShipContainer csc : cships) {
			if(csc != current) {
				// -5 because the factions ought to have some wiggle room
				if(factionTable.getRelation(csc.getFactionId(), current.getFactionId()) < -5) {
					potentialTargets.add(csc);
				}
			}
		}
		return potentialTargets;
	}

	public FactionTable getFactionTable() {
		return factionTable;
	}

	public void setFactionTable(FactionTable factionTable) {
		this.factionTable = factionTable;
	}
}
