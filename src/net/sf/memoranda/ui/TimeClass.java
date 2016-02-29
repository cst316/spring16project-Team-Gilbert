package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/*
 * created by snesmith 2/28/2106
 * this is used to count down project timer.
 */

public class TimeClass implements ActionListener{
	Long counter;
	
	public TimeClass(Long counter){
		this.counter = counter;
	}
	
	@Override
	public void actionPerformed(ActionEvent tc) {
	counter = counter - 1;
		
		if(counter >= 1){
			AgendaPanel.timerField.setText("Hours left: " + counter);
		}
		else {
			AgendaPanel.timer.stop();
			AgendaPanel.timerField.setText("Done!");
		}
	}
}
