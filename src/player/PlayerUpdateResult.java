package player;

public class PlayerUpdateResult {
	private int nEnergy;
	private int dEnergy = 0;
	private boolean energyFlag = true;

	private int nMoney;
	private int dMoney = 0;
	private boolean moneyFlag = true;

	public boolean isSuccess() {
		return energyFlag && moneyFlag;
	}

	public int getdEnergy() {
		return dEnergy;
	}

	public void setdEnergy(int dEnergy) {
		this.dEnergy = dEnergy;
	}

	public int getnEnergy() {
		return nEnergy;
	}

	public void setnEnergy(int nEnergy) {
		this.nEnergy = nEnergy;
	}

	public int getdMoney() {
		return dMoney;
	}

	public void setdMoney(int dMoney) {
		this.dMoney = dMoney;
	}

	public int getnMoney() {
		return nMoney;
	}

	public void setnMoney(int nMoney) {
		this.nMoney = nMoney;
	}
	
	public String getResult() {
		StringBuilder sb = new StringBuilder();
		
		if(isSuccess()) {
			if(dEnergy > 0) {
				sb.append("Gained");
				sb.append(dEnergy);
				sb.append(" energy. Now at ");
				sb.append(nEnergy);
				sb.append(".");
			} else if(dEnergy < 0) {
				sb.append("Expended ");
				sb.append(-dEnergy);
				sb.append(" energy. Now at ");
				sb.append(nEnergy);
				sb.append(".");
			}
			
			if(dMoney > 0) {
				sb.append("Earned ");
				sb.append(dMoney);
				sb.append(" money. Now at ");
				sb.append(nMoney);
				sb.append(".");
			} else if(dMoney < 0) {
				sb.append("Spent ");
				sb.append(-dMoney);
				sb.append(" money. Now at ");
				sb.append(nMoney);
				sb.append(".");
			}
		} else {
			if(!energyFlag) {
				sb.append("Not enough energy to perform action. ");
			}
			if(!moneyFlag) {
				sb.append("Not enough money to perform action. ");
			}
		}
		
		return sb.toString();
	}

	public boolean isEnergyFlag() {
		return energyFlag;
	}

	public void setEnergyFlag(boolean energyFlag) {
		this.energyFlag = energyFlag;
	}

	public boolean isMoneyFlag() {
		return moneyFlag;
	}

	public void setMoneyFlag(boolean moneyFlag) {
		this.moneyFlag = moneyFlag;
	}
}
