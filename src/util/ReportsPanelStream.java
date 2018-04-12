package util;

import logic.RoadMap;
import model.TrafficSimulator;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class ReportsPanelStream extends OutputStream {
	RoadMap map;
	JTextArea textArea;
	ReportsPanelStream instance = null;
	public ReportsPanelStream(JTextArea textArea){
		super();
		this.textArea = textArea;
	}
	@Override
	public void write(int b) throws IOException {
		// empty, it's not used but it's necessary to overwrite to extends OutputStream
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.textArea.setText(new String(b, "UTF-8"));
	}
}
