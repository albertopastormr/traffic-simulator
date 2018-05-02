package view.panel;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {

	private JLabel information;

	public InformationPanel(String information){
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.information = new JLabel(information);
		this.setVisible(true);
		this.add(this.information);
		this.setBorder(BorderFactory.createBevelBorder(1));
	}

	public JLabel getInformation() {
		return information;
	}

	public void setInformation(JLabel information) {
		this.information = information;
	}
}
