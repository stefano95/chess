package com.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DifficultyPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Integer> levelsBox;
	
	public DifficultyPanel(Integer defaultValue) {
		Integer[] levels = {3, 4, 5, 6};				//4 options for the difficulty box
		levelsBox = new JComboBox<Integer>(levels);
		levelsBox.setPreferredSize(new Dimension(150, 50));	//size of the box
		levelsBox.setSelectedItem(defaultValue);
		JLabel label = new JLabel("DIFFICULTY: ");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		add(label);
		add(levelsBox);
	}
	
	public void setDifficultyListener(final ChessFrame frame) {
		levelsBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer level = (Integer) levelsBox.getSelectedItem();
				frame.setDifficulty(level);
			}
		});
	}
}

