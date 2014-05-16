package uiElements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameOptions.Resolution;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import main.Lang;

import ui.HeatDeathUI;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class GameOptionsPanel extends ParentPanel {

	private JComboBox<Resolution> resolutionComboBox;
	
	public GameOptionsPanel(HeatDeathUI parent) {
		super(parent);
		
		this.setLayout(new FormLayout(new ColumnSpec[] {
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
		}, new RowSpec[] {
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
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
		}));
		
		JLabel optionsLabel = new JLabel(Lang.optionsLabel);

		this.add(optionsLabel, "2, 2");
		
		resolutionComboBox = new JComboBox<Resolution>();
		resolutionComboBox.setModel(new DefaultComboBoxModel<Resolution>(Resolution.values()));
		this.add(resolutionComboBox, "2, 4");
		
		JButton optionsSaveButton = new JButton(Lang.saveButton);
		optionsSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving options");
				
				Resolution res = (Resolution) resolutionComboBox.getSelectedItem();
				heatDeathUI.setFrameSize(res.getWidth(), res.getHeight());
			}
		});
		this.add(optionsSaveButton, "2, 14");
	}
}
