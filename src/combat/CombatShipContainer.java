package combat;

import java.util.List;

import player.Player;

import ai.SuperAI;

import main.RNG;
import ship.Ship;
import ship.ShipUpdate;
import shipUpgrades.Weapon;

public class CombatShipContainer {
	
	private SuperAI ai;
	private boolean isPlayer;
	
	private Ship ship;
	private int speed;
	private int base;
	private String name;
	
	private boolean retreating = false;
	private boolean manouvering = false;
	
	private int disengage = 0;
	
	private int dodge;
	
	public void initialize(Ship ship) {
		
		this.ship = ship;
		this.speed = ship.getSpeed();
		this.base = this.speed;
		this.dodge = ship.getSpeed()/2;
		this.name = ship.getName();
	}
	
	public CombatShipContainer(SuperAI superAI) {
		this.ai = superAI;
		initialize(superAI.getShip());
		this.isPlayer = false;
	}
	
	public CombatShipContainer(Player player) {
		initialize(player.ship);
		this.isPlayer = true;
	}
	
	public ShipUpdate getFullBarrageUpdate() {
		ShipUpdate su = new ShipUpdate();
		
		for(Weapon wep : ship.getWeaponUpgrades()) {
			wep.getDamage();
		}
		
		return su;
	}
	
	public CombatAttackResult attack(CombatShipContainer target) {
		List<Weapon> weapons = this.ship.getWeaponUpgrades();
		int[] weaponIds = new int[weapons.size()];
		for(int i = 0; i < weapons.size(); i++) {
			weaponIds[i] = i;
		}
		return attackWithWeapon(target, weaponIds);
	}
	
	public CombatAttackResult attackWithWeapon(CombatShipContainer target, int[] weaponIds) {
		if(weaponIds.length <= 0) {
			// didn't specify any weapons to attack with, error
		}
		
		ShipUpdate su = ship.fireWeapons(weaponIds);
		
		if(su.getHull() == 0) {
			// not enough fuel to fire weapons
		}
		CombatAttackResult car = new CombatAttackResult();
		car.setAttacker(this);
		car.setTarget(target);
		
		//TODO Tweak this number, the attacking ship should do more damage while manouvering with high speeds
		if(manouvering) {
			int shipSpeed = ship.getSpeed();
			su.setHull(su.getHull() * 2);

			//su.setHull(su.getHull() * (1 + (shipSpeed/100)));
			
			if(RNG.random(shipSpeed*2) < shipSpeed) {
				// add this message to the attackResult object
				manouvering = false;
				car.setWasManouvering(true, false);
			} else {
				car.setWasManouvering(true, true);
			}
		} else {
			car.setWasManouvering(false, false);
		}
		
		CombatDamageResult cdr = target.damage(su);
		car.setCdr(cdr);
		
		return car;
	}
	
	public CombatDamageResult damage(ShipUpdate su) {
		CombatDamageResult cdr = new CombatDamageResult();
		// dodge chance
		if(RNG.random(100) < dodge) {
			cdr.setHit(false);
			cdr.setDamageInflicted(0);
			cdr.setTargetDestroyed(false);
			cdr.setTargetVisualStatus(ship.getVisualStatus());
		} else {
			cdr = ship.combatUpdate(su);
		}
		
		return cdr;
	}
	
	public void updateSpeed() {
		speed = speed + base;
	}
	
	public boolean isPlayerShip() {
		return isPlayer;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public int getDodge() {
		return dodge;
	}

	public void setDodge(int dodge) {
		this.dodge = dodge;
	}
	
	public void updateDodge() {
		dodge *= (1/100 * speed);
		if(dodge >= 100) {
			dodge = 99;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRetreating() {
		return retreating;
	}

	public void setRetreating(boolean retreating) {
		this.retreating = retreating;
	}

	public boolean isManouvering() {
		return manouvering;
	}

	public void setManouvering(boolean manouvering) {
		this.manouvering = manouvering;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public int getFactionId() {
		if(isPlayer) {
			return 1;
		} else {
			return ai.getFactionId();
		}
	}

	public SuperAI getAi() {
		return ai;
	}

	public int getDisengage() {
		return disengage;
	}

	public void setDisengage(int disengage) {
		this.disengage = disengage;
	}
	
	public void incrementDisengage() {
		this.disengage++;
	}
	
	
}
