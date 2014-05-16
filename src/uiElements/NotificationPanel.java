package uiElements;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.Lang;

import ui.HeatDeathUI;
import ui.UiZone;

@SuppressWarnings("serial")
public class NotificationPanel extends ParentPanel {
	public NotificationPanel(String message, boolean critical, HeatDeathUI parent) {
		super(parent);
		JLabel warningLabel = new JLabel(message);
		if(critical) warningLabel.setForeground(Color.RED);
		this.add(warningLabel);
		
		JButton closeWarningButton = new JButton(Lang.closeButton);
		closeWarningButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				heatDeathUI.clearSinglePanel(UiZone.South);
			}
		});
		this.add(closeWarningButton);
	}
}
