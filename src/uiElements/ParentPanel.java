package uiElements;

import javax.swing.JPanel;

import ui.HeatDeathUI;

@SuppressWarnings("serial")
public class ParentPanel extends JPanel {
	
	protected HeatDeathUI heatDeathUI;
	
	public ParentPanel() {
		super();
	}

	public HeatDeathUI getHeatDeathUI() {
		return heatDeathUI;
	}

	public void setHeatDeathUI(HeatDeathUI heatDeathUI) {
		this.heatDeathUI = heatDeathUI;
	}
}
