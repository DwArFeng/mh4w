package com.dwarfeng.jier.mh4w.core.control;

import java.awt.EventQueue;

import javax.swing.JDialog;

public class DateTypeFrame extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DateTypeFrame dialog = new DateTypeFrame();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public DateTypeFrame() {
		setBounds(100, 100, 450, 300);

	}

}
