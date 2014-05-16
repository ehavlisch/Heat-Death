package uiElements;

import javax.swing.JButton;

import options.Option;

@SuppressWarnings("serial")
public class OptionButton extends JButton {
	
	private Option option;
	
	public OptionButton(String name, Option opt) {
		super(name);
		this.option = opt;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
}
