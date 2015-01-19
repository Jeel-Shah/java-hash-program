package com.js.jhash;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JHash_Executor {

	public JHash_Executor() {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "UnsupportedLookAndFeelException Contact ");
		}
		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ClassNotFoundException Contact ");
		}
		catch (InstantiationException e) {
			JOptionPane.showMessageDialog(null, "InstantiationException Contact ");
		}
		catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "IllegalAccessException Contact ");
		}
		new JHash();
	}

	public static void main(String[] args) {
		new JHash_Executor();
	}

}
