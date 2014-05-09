package locations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import childrenLocations.*;

import filesystem.GameFileManager;

import main.RNG;
import main.Randomizer;

public class World {
	/*
	 * The name of each map entry should be a filename where we can read the
	 * sector information The MinLocation object holds whether the name of
	 * locations connected via wormhole
	 */
	
	private int size;
	private Sector activeSector;
	private WormholeLookup[][] wormholeLookup;
	private int worldDensity;
	private int worldSize;
	
	private HashMap<Integer, String> sectorLookup;
	
	public World(int worldSize, int worldDensity, GameFileManager fileManager) {
		size = 0;
		
		if(worldDensity == worldSize) {
			worldDensity--;
		}
		
		if(worldSize == 1) {
			worldDensity = 0;
		}
		
		this.worldDensity = worldDensity;
		this.worldSize = worldSize;
		
		wormholeLookup = new WormholeLookup[worldSize][worldDensity];

		sectorLookup = new HashMap<Integer, String>(worldSize);
		
		int wormholeIdCounter = 1;
		
		// minimum of one sector
		for (int sectorId = 0; sectorId < worldSize; sectorId++) {
			
			Sector s = new Sector(Randomizer.randomName(), sectorId);
			sectorLookup.put(sectorId, s.getName());
			s.initializeSector();
			
			// Populate unique locations list
			UniqueLocationRegistry ulr = new UniqueLocationRegistry();
			

			// TODO Add wormholes into the sector
			for(int wormholeRow = 0; wormholeRow < worldDensity; wormholeRow++) {
				if(wormholeLookup[sectorId][wormholeRow] == null) {
					
					int targetSector = RNG.random(sectorId+1, worldSize);
					
					WormholeLookup wormLookup = new WormholeLookup(wormholeIdCounter, targetSector);
					WormholeLookup targetLookup = new WormholeLookup(wormholeIdCounter, sectorId);
					
					Wormhole wormhole = new Wormhole(Randomizer.randomNameNumber(), wormholeIdCounter);
					System.out.println(wormhole.name + " setting Connecting Sector to : " + targetSector);
					wormhole.setConnectingSector(targetSector);
							
					int empty = getEmptyLookupRowIn(targetSector);
					if(empty != -1) {
						wormholeLookup[sectorId][wormholeRow] = wormLookup;
						wormholeLookup[targetSector][empty] = targetLookup;
						
						s.addLocation(wormhole);
						
						//System.out.println("Initialized wormhole " + wormholeIdCounter + " that connects " + sectorId + " and " + targetSector);
					} else {
						int[] newTarget = getNextPossibleConnection(sectorId);
						if(newTarget == null) {
							System.out.println("Sector " + sectorId + " may be impossible to reach.");
						} else {
							System.out.println(wormhole.name + " setting connecting sector to : " + newTarget[0]);
							wormhole.setConnectingSector(newTarget[0]);
							wormLookup.setSectorId(newTarget[0]);
						
							wormholeLookup[sectorId][wormholeRow] = wormLookup;
							wormholeLookup[newTarget[0]][newTarget[1]] = targetLookup;
							
							s.addLocation(wormhole);
							
							//System.out.println("Reinitialized wormhole " + wormholeIdCounter + " to connect " + sectorId + " to " + newTarget[0] + ".");
						
							//System.out.println("ERROR: Failed to create a linked wormhole");
						}
					}
					
					wormholeIdCounter++;
				} else {
					WormholeLookup wormLookup = wormholeLookup[sectorId][wormholeRow];
					wormLookup.matched();
					wormholeLookup[wormLookup.getSectorId()][getMatchRowIn(wormLookup.getSectorId(), wormLookup.getWormholeId())].matched();
					
					Wormhole wormhole = new Wormhole(Randomizer.randomNameNumber(), wormLookup.getWormholeId());
					wormhole.setConnectingSector(wormLookup.getSectorId());
					
					s.addLocation(wormhole);
					
					//System.out.println("Created matching wormhole for " + wormLookup.getWormholeId() + " in sector " + sectorId + ".");
				}
				
				size += s.size();
			}
			
			// TODO inject unique locations into sectors
			
			ArrayList<Location> locations = s.getAllLocations();
			
			List<Location> uniqueLocations = ulr.getSomeLocations(worldSize);
			if(uniqueLocations != null) {
				locations.addAll(uniqueLocations);
			}
			
			// Save the active sector in memory, save the others to files
			if(sectorId == worldSize-1) {
				activeSector = s;
			} else {
				if(fileManager == null) {
					System.out.println("SEVERE ERROR: File manager null!");
				}
				fileManager.fileManagerSaveSector(s);
			}
			
			System.out.println("Finished Creating Sector " + (sectorId+1) + "/" + worldSize + ": " + s.getName() + ".");
		}
		
		System.out.println("Finished creating world. " + size + " locations created.");
		System.out.println("Active sector: " + activeSector.getName());
	}
	
	public int[] getNextPossibleConnection(int sectorId) {
		for(int i = sectorId+1; i < worldSize; i++) {
			for(int j = 0; j < worldDensity; j++) {
				if(wormholeLookup[i][j] == null) {
					int[] retVal =  {i, j};
					return retVal;
				}
			}
		}
		return null;
	}
	
	public int getMatchRowIn(int sectorId, int wormholeId) {
		for(int i = 0; i < worldDensity; i++) {
			if(wormholeLookup[sectorId][i] != null && wormholeLookup[sectorId][i].getWormholeId() == wormholeId) return i;			
		}
		return -1;
	}
	
	public int getEmptyLookupRowIn(int sectorId) {
		if(sectorId == worldSize) {
			return -1;
		}
		for(int i = 0; i < worldDensity; i++) {
			if(wormholeLookup[sectorId][i] == null) return i;
		}
 		return -1;
	}
	
	public void getConnectedSectors(int wormholeid) {

		return;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Sector getActiveSector() {
		return activeSector;
	}
	
	public String lookupSectorNameById(int id) {
		if(sectorLookup != null) {
			return sectorLookup.get(id);
		} else {
			System.out.println("ERROR: Sector lookup uninitialized!");
			return null;
		}
	}

	public LocationUpdateResult update(LocationUpdate lu, GameFileManager fileManager) {
		if(lu != null) {
			LocationUpdateResult lur = new LocationUpdateResult();
			if(lu.isWormholeTravel()) {
				Integer targetSectorId = lu.getSector();
				Integer wormholeId = lu.getWormholeId();
				
				Wormhole startWormhole = (Wormhole) activeSector.getWormholeLocation(wormholeId);
				String startName = startWormhole.getName();
				
				if(targetSectorId != null && targetSectorId != activeSector.getSectorId()) {
					String sectorName = sectorLookup.get(targetSectorId);
					Sector loadedSector = fileManager.loadSector(sectorName);
					
					if(startWormhole.getConnectionName() == null) {
						Wormhole targetWormhole = (Wormhole) loadedSector.getWormholeLocation(wormholeId);
						String targetName = targetWormhole.getName();
						targetWormhole.setConnectionName(startName);
						startWormhole.setConnectionName(targetName);
					}
					
					fileManager.fileManagerSaveSector(activeSector);
					activeSector = loadedSector;
					lur.setSectorName(activeSector.getName());
				} else {
					System.out.println("ERROR: No target sector found");
				}
				
				Location targetLocation = activeSector.getWormholeLocation(wormholeId);
				lur.setLoc(targetLocation);
				
			} else {
			
				Location targetLocation = lu.getLocation();
				
				if(targetLocation == null) {
					System.out.println("WARN: Missing targetLocation in locationUpdate");
				} else {
					lur.setLoc(targetLocation);
				}
			}
			
			return lur;
		} else {
			return new LocationUpdateResult(true);
		}
	}

}
