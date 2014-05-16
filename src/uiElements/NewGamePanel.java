package uiElements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import ui.HeatDeathUI;

import main.Lang;
import main.Randomizer;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class NewGamePanel extends ParentPanel {
	private JTextField newGameNameTextField;
	private JTextField newGameShipTextField;
	private JSpinner newGameWorldSizeSpinner;
	private JSpinner newGameWorldDensitySpinner;
	
	public NewGamePanel(HeatDeathUI parent) {
		super(parent);
		
		this.setLayout(
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
			
		JLabel newGameNameLabel = new JLabel(Lang.newGameNameLabel);
		this.add(newGameNameLabel, "2, 2, right, default");
		
		newGameNameTextField = new JTextField();
		this.add(newGameNameTextField, "4, 2, fill, default");
		newGameNameTextField.setColumns(10);
		
		JLabel newGameShipLabel = new JLabel(Lang.newGameShipNameLabel);
		this.add(newGameShipLabel, "2, 4, right, default");
		
		newGameShipTextField = new JTextField();
		this.add(newGameShipTextField, "4, 4, fill, default");
		newGameShipTextField.setColumns(10);
		
		JLabel newGameWorldSizeLabel = new JLabel(Lang.newGameWorldSizeLabel);
		this.add(newGameWorldSizeLabel, "2, 6");
		
		newGameWorldSizeSpinner = new JSpinner();
		newGameWorldSizeSpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		this.add(newGameWorldSizeSpinner, "4, 6");
		
		JLabel newGameWorldDensityLabel = new JLabel(Lang.newGameWorldDensityLabel);
		this.add(newGameWorldDensityLabel, "2, 8");
		
		newGameWorldDensitySpinner = new JSpinner();
		newGameWorldDensitySpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		this.add(newGameWorldDensitySpinner, "4, 8");
		
		JButton newGameCreateButton = new JButton(Lang.newGameButton);
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
					heatDeathUI.setWorldSize((int) newGameWorldSizeSpinner.getValue());
					heatDeathUI.setWorldDensity((int) newGameWorldDensitySpinner.getValue());
					heatDeathUI.newGame(gameName, shipName);							
				}
			}
		});
		
		this.add(newGameCreateButton, "4, 10");
	}
}
