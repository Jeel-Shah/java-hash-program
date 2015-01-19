package com.js.jhash;

/************************************************************

Program: JHash

File: JHash.java         

Main Method:  JHash_Executor

Description:  Will generate the MD5, CRC32 SHA-1 AND SHA-256 CHECKSUMS

Author:       Jeel Shah

Environment:  Variable

Notes: Started 19/01/2012, finished version 1 on 20/01/2012       

Revisions:    Added a compare dialog with no function 12:46AM (21/01/2012)
			  Added a compare dialog with function 1:07AM (21/01/2012)
			  Added fail safe, if user decides to export/compare without 21/01/2012
			  checksum generated then appropriate message will be displayed
			  Added custom titled borders for inputPanel and hashPanel (21/01/2012) 3:31 PM
			  Added System look and feel 3:43 PM (21/01/2012)
			  Changed the logo and the smiley 7:05 PM 21/01/2012
			  Added YES_NO Option on exit.
			  Changed the logo size and placement (now bottom left) 1:50AM (22/01/2012)
			  made all of the textfields not editable 1:50AM (22/01/2012)
			  Added checkboxes to allow user to chose the hashes 23/01/2012 7:55 AM
			  
 ************************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;	
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

//import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class JHash extends JFrame {

	/**
	 * Will output the MD5, SHA1,SHA256 and CRC32 of the selected file
	 */
	private static final long serialVersionUID = 1L;

	// Variables
	protected Hash hash;
	protected boolean isGen = false;

	// Components
	protected JPanel inputPanel = new JPanel();
	protected JPanel hashPanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	protected JPanel mainPanel = new JPanel();

	// Collection of JTextField
	protected JTextField fileLoc = new JTextField(35);
	protected JTextField output_MD5 = new JTextField(40);
	protected JTextField output_CRC32 = new JTextField(40);
	protected JTextField output_SHA1 = new JTextField(40);
	protected JTextArea output_SHA256 = new JTextArea(2,10);

	// Collection of ImageIcon
	protected ImageIcon exportIcon = new ImageIcon(getClass().getResource("export.png"));
	protected ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
	protected ImageIcon exitIcon = new ImageIcon(getClass().getResource("exit.png"));
	protected ImageIcon compareIcon = new ImageIcon(getClass().getResource("compare.png"));
	protected ImageIcon checkIcon = new ImageIcon(getClass().getResource("checkmark.png"));
	protected ImageIcon smileyIcon = new ImageIcon(getClass().getResource("smiley.png"));
	protected ImageIcon genIcon = new ImageIcon(getClass().getResource("generate.png"));

	// Collection of JLabels
	protected JLabel label_FileLoc = new JLabel("Select File Location: ");
	protected JLabel label_MD5 = new JLabel("MD5 ");
	protected JLabel label_CRC32 = new JLabel("CRC32 ");
	protected JLabel label_SHA1 = new JLabel("SHA-1 ");
	protected JLabel label_SHA256 = new JLabel("SHA-256 ");
	protected JLabel label_Logo = new JLabel(logoIcon,JLabel.RIGHT);

	// Collection of JButton
	protected JButton exportButton = new JButton("Export to file",exportIcon);
	protected JButton browseButton = new JButton("Browse..");
	protected JButton exitButton = new JButton("Exit.",exitIcon);
	protected JButton compareButton = new JButton("Compare",compareIcon);
	protected JButton genButton = new JButton("Generate",genIcon);

	// collection of JCheckboxes
	protected JCheckBox checkbox_MD5 = new JCheckBox("",true);
	protected JCheckBox checkbox_CRC32 = new JCheckBox("",true);
	protected JCheckBox checkbox_SHA1 = new JCheckBox("",true);
	protected JCheckBox checkbox_SHA256 = new JCheckBox("",true);

	public JHash() {
		// housekeeping
		super("JHash Version 1 - Hashcode calculator and verifier by Jeel Shah");
		setSize(770,665);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		// JButton customization here
		genButton.setEnabled(false);

		// text field customization and text area
		fileLoc.setEditable(false);
		fileLoc.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		output_MD5.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		output_CRC32.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		output_SHA1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		output_SHA256.setLineWrap(true);
		output_SHA256.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		/*set layouts for the panels and add the components to the panel*/
		mainPanel.setLayout(new GridBagLayout());

		// button panel
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setOpaque(false);

		// getting the prefferred size of the exportButton and then setting the dimensions
		// to the desired ones
		Dimension buttonSize = exportButton.getPreferredSize();
		buttonSize.width = 200;

		Dimension buttonSize1 = exitButton.getPreferredSize();
		buttonSize1.width = 150;

		Dimension buttonSize2 = compareButton.getPreferredSize();
		buttonSize2.width = 150;

		// setting desired size
		exportButton.setPreferredSize(buttonSize);
		exitButton.setPreferredSize(buttonSize1);
		compareButton.setPreferredSize(buttonSize2);

		GridBagConstraints buttonC = new GridBagConstraints();

		// putting the export button first
		buttonC.gridx = 0;
		buttonC.gridy = 0;
		buttonPanel.add(exportButton,buttonC);

		// adding compare button to buttonPanel - will be in between export and exit button
		buttonC.gridx = 1;
		buttonC.gridy = 0;
		buttonC.insets = new Insets(10,10,10,0);
		buttonPanel.add(compareButton,buttonC);

		// adding the generate button
		buttonC.gridx = 2;
		buttonC.gridy = 0;
		buttonPanel.add(genButton,buttonC);

		// exit button will right beside the export button
		buttonC.gridx = 3;
		buttonC.gridy = 0;
		//buttonC.insets = new Insets(10,10,10,10);
		buttonPanel.add(exitButton,buttonC);

		// input panel addition begins here
		inputPanel.setLayout(new GridBagLayout());

		// making a custom border
		TitledBorder inputTitle = new TitledBorder("Input");
		inputTitle.setTitleFont(new Font("Arial",Font.BOLD,16));
		inputTitle.setTitleColor(Color.black);

		inputPanel.setBorder(inputTitle);

		GridBagConstraints inputC = new GridBagConstraints();

		// label_FileLoc - location of file label
		inputC.fill = GridBagConstraints.HORIZONTAL;
		inputC.gridx = 0;
		inputC.gridy = 0;
		label_FileLoc.setFont(new Font("Arial",Font.PLAIN,14));
		label_FileLoc.setForeground(Color.black);
		inputPanel.add(label_FileLoc,inputC);

		// File location textfield
		inputC.fill = GridBagConstraints.HORIZONTAL;
		inputC.gridx = 1;
		inputC.gridy = 0;
		inputC.insets = new Insets(10,10,10,10);
		//inputC.gridwidth = 2;
		inputPanel.add(fileLoc,inputC);

		// browse button
		inputC.fill = GridBagConstraints.HORIZONTAL;
		inputC.gridx = 2;
		inputC.gridy = 0;
		inputC.gridwidth = 0;
		inputPanel.add(browseButton,inputC);

		inputPanel.setOpaque(false);

		/*adding hash panel
		 * */
		hashPanel.setLayout(new GridBagLayout());

		// making a custom TitledBorder for the hashPanel
		TitledBorder hashTitle = new TitledBorder("Hash");
		hashTitle.setTitleColor(Color.black);
		hashTitle.setTitleFont(new Font("Arial",Font.BOLD,16));

		hashPanel.setBorder(hashTitle);

		GridBagConstraints hashC = new GridBagConstraints();
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.weightx = 1;

		// adding the checkbox
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 0;
		hashC.gridy = 0;
		hashPanel.add(checkbox_MD5);

		// adding label_MD5
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 1;
		hashC.gridy = 0;
		hashC.insets = new Insets(10,10,10,10);
		label_MD5.setFont(new Font("Arial",Font.BOLD,16));
		label_MD5.setForeground(Color.black);

		hashPanel.add(label_MD5,hashC);

		// adding output_MD5
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 2;
		hashC.gridy = 0;
		hashC.gridwidth = 0;
		output_MD5.setEditable(false);
		hashPanel.add(output_MD5,hashC);

		// adding the checkbox_CRC32
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 0;
		hashC.gridy = 1;
		hashPanel.add(checkbox_CRC32);

		// adding label_CRC32
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 1;
		hashC.gridy = 1;
		hashC.insets = new Insets(10,10,10,10);
		label_CRC32.setFont(new Font("Arial",Font.BOLD,16));
		label_CRC32.setForeground(Color.black);

		hashPanel.add(label_CRC32,hashC);

		// adding output_CRC32
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 2;
		hashC.gridy = 1;
		hashC.gridwidth = 0;
		output_CRC32.setEditable(false);
		hashPanel.add(output_CRC32,hashC);

		// adding checkbox_SHA1
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 0;
		hashC.gridy = 2;
		hashPanel.add(checkbox_SHA1);

		// adding label_SHA1
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 1;
		hashC.gridy = 2;
		hashC.insets = new Insets(10,10,10,10);
		label_SHA1.setFont(new Font("Arial",Font.BOLD,16));
		label_SHA1.setForeground(Color.black);

		hashPanel.add(label_SHA1,hashC);

		// adding output_SHA1
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 2;
		hashC.gridy = 2;
		hashC.gridwidth = 0;
		output_SHA1.setEditable(false);
		hashPanel.add(output_SHA1,hashC);

		// adding checkbox_SHA256
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 0;
		hashC.gridy = 3;
		hashPanel.add(checkbox_SHA256);

		// adding label_SHA256
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 1;
		hashC.gridy = 3;
		hashC.insets = new Insets(10,10,10,10);
		label_SHA256.setFont(new Font("Arial",Font.BOLD,14));
		label_SHA256.setForeground(Color.black);
		hashPanel.add(label_SHA256,hashC);

		// adding output_SHA256
		hashC.fill = GridBagConstraints.HORIZONTAL;
		hashC.gridx = 2;
		hashC.gridy = 3;

		output_SHA256.setEditable(false);
		hashPanel.add(output_SHA256,hashC);

		hashPanel.setOpaque(false);// making it transperant

		/* add components mainPanel here
		 */
		GridBagConstraints mainC = new GridBagConstraints();

		// adding input panel
		mainC.fill = GridBagConstraints.HORIZONTAL;
		mainC.gridx = 0;
		mainC.gridy = 0;
		mainPanel.add(inputPanel,mainC);

		// adding hash panel
		mainC.fill = GridBagConstraints.HORIZONTAL;
		mainC.gridx = 0;
		mainC.gridy = 1;
		mainPanel.add(hashPanel,mainC);

		// adding the button panel
		mainC.fill = GridBagConstraints.HORIZONTAL;
		mainC.gridx = 0;
		mainC.gridy = 2;
		mainPanel.add(buttonPanel,mainC);


		// adding the logo
		mainC.fill = GridBagConstraints.HORIZONTAL;
		mainC.gridx = 0;
		mainC.gridy = 3;

		mainPanel.add(label_Logo,mainC);


		// mainPanel customization
		mainPanel.setOpaque(false);
		mainPanel.setForeground(Color.white);

		add(mainPanel);

		pack();
		setVisible(true);

		// allocation of the action listeners
		browseButton.addActionListener(new browseListener());
		exitButton.addActionListener(new exitListener());
		exportButton.addActionListener(new exportListener());
		// adding a quick n' dirty action listener to make a simple call
		compareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isGen) {
					JOptionPane.showMessageDialog(null, "Please generate checksums first"
							,"NO CHECKSUM ERROR",JOptionPane.ERROR_MESSAGE);
				}else {
					new compareDialog();
				}
			}
		});
		genButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputHashes();
			}
		});
	}
	// action listener for browse button, It will get the selected file then call another action listener
	// which will show the hashes
	class browseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// none of the check boxes are checked, return message indicating to check a box
			if(!checkbox_MD5.isSelected() && !checkbox_CRC32.isSelected() && !checkbox_SHA1.isSelected() && 
					!checkbox_SHA256.isSelected()) {
				JOptionPane.showMessageDialog(null, "Please check a checkbox","No checkbox selected",JOptionPane.ERROR_MESSAGE);
			}else {
				JFileChooser fc = new JFileChooser();
				int check = fc.showOpenDialog(null);

				if(check == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					genButton.setEnabled(true);
					try {
						hash = new Hash(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					fileLoc.setText(file.getAbsolutePath());
					outputHashes();
				}
			}
		}
	}

	// action listener for exit button
	class exitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object[] options = new Object[] {"Yes, Please","No way!"};

			int n = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?","Exit",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,exitIcon,options,options[1]);

			if(n == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	// action listener for export button
	class exportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!isGen) {
				JOptionPane.showMessageDialog(null, "Please generate checksums first"
						,"NO CHECKSUM ERROR",JOptionPane.ERROR_MESSAGE);
			}else {
				JFileChooser fc = new JFileChooser();
				File f;
				int check = fc.showSaveDialog(null);
				if(check == JFileChooser.APPROVE_OPTION) {
					if(fc.getSelectedFile() != null) {
						f = fc.getSelectedFile();
						if(!f.getName().endsWith(".txt")) {
							JOptionPane.showMessageDialog
							(null, "Please re-try by entering the file extension .txt", "Extension Error",JOptionPane.ERROR_MESSAGE);
						}else {
							try {
								BufferedWriter bw = new BufferedWriter(new FileWriter(f));
								bw.write("MD5: "+output_MD5.getText());
								bw.newLine();
								bw.write("CRC32: "+output_CRC32.getText());
								bw.newLine();
								bw.write("SHA1: "+output_SHA1.getText());
								bw.newLine();
								bw.write("SHA-256: "+output_SHA256.getText());
								bw.flush();
								bw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	// method that will set the fields once we have gotten the file.
	public void outputHashes() {
		try {
			boolean isOutput = false;
			if(checkbox_MD5.isSelected()) {
				String MD5 = hash.getMD5();
				output_MD5.setText(MD5);
				isGen = true;
				isOutput = true;
			}

			if(checkbox_CRC32.isSelected()) {
				String CRC32 = hash.getCRC32();
				output_CRC32.setText(CRC32);
				isGen = true;
				isOutput = true;
			}

			if(checkbox_SHA1.isSelected()) {
				String SHA1 = hash.getSHA1();
				output_SHA1.setText(SHA1);
				isGen = true;
				isOutput = true;
			}

			if(checkbox_SHA256.isSelected()) {
				String SHA256 = hash.getSHA256();
				output_SHA256.setText(SHA256);
				isGen = true;
				isOutput = true;
			}

			if(!isOutput) {
				JOptionPane.showMessageDialog(null, "No checkboxes are selected. " +
						"Please check one of boxes and click \'Generate\'.","No checkbox selected",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// compare dialog
	class compareDialog extends JDialog{
		private static final long serialVersionUID = 1L;

		private JComboBox jb = new JComboBox(new String[] {"MD5","CRC32","SHA1","SHA-256"});
		private JTextField jt = new JTextField(20);
		private JButton compareButton = new JButton("Compare");
		private JPanel panel = new JPanel();

		public compareDialog() {
			// house keeping
			setTitle("Compare!");
			setSize(500,100);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setResizable(true);

			// textfield customization here
			jt.setText("Put checksum here");
			jt.setForeground(new Color(150,150,150)); // very light gray

			// setting the panel layout and doing some simple customization
			// adding a border to the panel "Select - paste - compare"
			panel.setLayout(new GridBagLayout());
			panel.setBorder(BorderFactory.createTitledBorder("Select - paste - compare"));

			// GridBagConstraints that will dicate the postioning of the components
			GridBagConstraints panelC = new GridBagConstraints();

			// combo box constraints, will be centered at 0,0
			panelC.anchor = GridBagConstraints.HORIZONTAL;
			panelC.gridx = 0;
			panelC.gridy = 0;
			panel.add(jb);

			// text field constraints, will be centered at 0,1
			panelC.anchor = GridBagConstraints.HORIZONTAL;
			panelC.gridx = 1;
			panelC.gridy = 0;
			panelC.insets = new Insets(10,10,10,10);
			panel.add(jt);

			// getting the preffered dimensions of the JButton
			Dimension pSize = compareButton.getPreferredSize();
			pSize.width = 100;
			compareButton.setPreferredSize(pSize);

			/// now adding to panel and using panelC
			panelC.anchor = GridBagConstraints.HORIZONTAL;
			panelC.gridx = 2;
			panelC.gridy = 1;
			panel.add(compareButton);

			add(panel);// adding panel

			setVisible(true);

			// compare button listener
			compareButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String innerText = jt.getText();
					if(innerText.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Enter text please");
					}else {
						switch(jb.getSelectedIndex()) {
						// case 0 means MD5
						case 0:
							String outputText_MD5 = output_MD5.getText();
							if(!outputText_MD5.isEmpty()) {
								compareText(outputText_MD5,innerText);
							}else {
								JOptionPane.showMessageDialog(null, "Please generate a MD5" +
										" checksum!", "No checksum error", JOptionPane.ERROR_MESSAGE);	
							}
							break;

							// case 1 means CRC32
						case 1:
							String outputText_CRC32 = output_CRC32.getText();
							if(!outputText_CRC32.isEmpty()) {
								compareText(outputText_CRC32,innerText);
							}else {
								JOptionPane.showMessageDialog(null, "Please generate a CRC32" +
										" checksum!", "No checksum error", JOptionPane.ERROR_MESSAGE);	
							}
							break;

							// case 2 means SHA1
						case 2:
							String outputText_SHA1 = output_SHA1.getText();
							if(!outputText_SHA1.isEmpty()) {
								compareText(outputText_SHA1,innerText);
							}else {
								JOptionPane.showMessageDialog(null, "Please generate a SHA1" +
										" checksum!", "No checksum error", JOptionPane.ERROR_MESSAGE);	
							}
							break;

							// case 3 means SHA-256
						case 3:
							String outputText_SHA256 = output_SHA256.getText();
							if(!outputText_SHA256.isEmpty()) {
								compareText(outputText_SHA256,innerText);
							}else {
								JOptionPane.showMessageDialog(null, "Please generate a SHA256" +
										" checksum!", "No checksum error", JOptionPane.ERROR_MESSAGE);	
							}
							break;
						}
					}
				}
			});

			// adding a focus listener on JTextField, once the textField has focus
			// the text will be removed
			jt.addFocusListener(new FocusListener() {
				// once the text field has focus, remove the text
				public void focusGained(FocusEvent e) {
					jt.setText("");
					jt.setForeground(Color.black);
				}
				public void focusLost(FocusEvent e) {

				}
			});
		}

		// compares the two string and prints the appropriate message
		private void compareText(String a, String b) {
			if(a.equals(b)) {
				JOptionPane.showMessageDialog(null, 
						"MATCH!","COMPARE RESULTS",JOptionPane.INFORMATION_MESSAGE,checkIcon);
			}else {
				JOptionPane.showMessageDialog(null, 
						"NO MATCH!","COMPARE RESULTS",JOptionPane.ERROR_MESSAGE);
			}
		}

	}// ends the class of the compareDialog
}