package ai;

import combat.CombatAction;
import combat.CombatShipContainer;

public interface AI {
	CombatAction getAction(CombatShipContainer csc);
}
