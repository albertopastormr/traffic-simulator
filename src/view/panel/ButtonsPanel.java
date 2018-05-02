package view.panel;

import view.observer.ObserverButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel {


	public ButtonsPanel(String[] strings, ObserverButton observerButton){
		this.setLayout(new FlowLayout());

		for(String str : strings){
			JButton button = new JButton(str);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					observerButton.executeButton(str);
				}
			});
			this.add(button);
		}
	}
}
