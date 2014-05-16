package uiElements;

import javax.swing.JPanel;

import ui.HeatDeathUI;

@SuppressWarnings("serial")
public class ParentPanel extends JPanel {
	
	protected HeatDeathUI heatDeathUI;
	private boolean parented = false;
	
	public ParentPanel(HeatDeathUI parent) {
		super();
		this.heatDeathUI = parent;
		parented = true;
	}
	
	public ParentPanel() {
		super();
	}

	public HeatDeathUI getHeatDeathUI() {
		if(!parented) System.out.println("Critical: Calling get Heat Deat UI of non parented class!");
		return heatDeathUI;
	}

	public void setHeatDeathUI(HeatDeathUI heatDeathUI) {
		this.heatDeathUI = heatDeathUI;
	}
}
