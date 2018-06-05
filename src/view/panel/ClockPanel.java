package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockPanel extends JPanel {

	private JTextArea counterTextArea;
	private int counter;

	// CLOCK THREAD
	private Thread clockThread;

	public ClockPanel(MainWindow mainWindow){
		this.counter = 0;
		this.setLayout(new GridLayout(2,1));
		this.setMinimumSize(new Dimension(140,70));
		this.setPreferredSize(new Dimension(140,70));
		this.setMaximumSize(new Dimension(140,70));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1,3));

		// START CLOCK
		JButton startClock = new JButton(new ImageIcon("media/icons/startClockButton.png"));
		startClock.setToolTipText("Start clock count");
		startClock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( ClockPanel.this.clockThread == null) {
					ClockPanel.this.clockThread = new Thread(new Runnable() {
						@Override
						public void run() {
							while (!Thread.interrupted()) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										ClockPanel.this.executeClockTick();
									}
								});
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									Thread.currentThread().interrupt();
								}
							}
							ClockPanel.this.clockThread = null;
						}
					});
					ClockPanel.this.clockThread.start();
				}
			}
		});
		buttonsPanel.add(startClock);

		// STOP CLOCK
		JButton stopClock = new JButton(new ImageIcon("media/icons/stopClockButton.png"));
		stopClock.setToolTipText("Stop clock count");
		stopClock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ClockPanel.this.clockThread != null && ClockPanel.this.clockThread.isAlive())
					ClockPanel.this.clockThread.interrupt();
			}
		});
		buttonsPanel.add(stopClock);

		// RESET CLOCK
		JButton resetClock = new JButton(new ImageIcon("media/icons/resetClockButton.png"));
		resetClock.setToolTipText("Reset clock count");
		resetClock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						ClockPanel.this.resetClock();
					}
				});
			}
		});
		buttonsPanel.add(resetClock);

		this.counterTextArea = new JTextArea();
		this.counterTextArea.setEditable(false);
		this.counterTextArea.setFont(new Font("TimesNewRoman", Font.BOLD, 18));
		this.add(buttonsPanel);
		this.add(this.counterTextArea);
		this.counterTextArea.setText(String.valueOf(counter));
	}

	private void resetClock(){
		this.counter = 0;
		this.counterTextArea.setText(String.valueOf(counter));
	}

	private void executeClockTick(){
		this.counter++;
		this.counterTextArea.setText(String.valueOf(counter));
	}
}
