/**
 *  A simple text editor that includes basic tools such as cut,copy,paste,save,word count etc.
 *  
 *  
 *  @author Dido Maulana
 *  @version 2015-09-11
*/

package minedit;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JMenuItem;

import java.awt.event.KeyEvent;


public class DidoEditor extends JFrame {
	private JPanel panel;
	private JFileChooser file = new JFileChooser();
	private JMenuItem menuSaveAs;
	private FindReplaceDialog find;
	private JTextArea textArea;
	private JEditorPane editor;
	private ActionListener save;
	
	
	private static final int FRAME_HEIGHT = 400;
	private static final int FRAME_WIDTH = 400;
	
	
	/** 
	 * Constructor of the frame with all the buttons and functions.
	 */
	public DidoEditor() 
	{
		setTitle("Dido's Mini Editor");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		createComponents();
	}
	
	
	/**
	 *  Creates the components within the frame. 
	 */
	private void createComponents()  
	{
		
		addWindowListener(new WindowAdapter() //Window Listener for the prompt before closing
		{
		    public void windowClosing(WindowEvent x)
		    {
		        JFrame dEditor = (JFrame)x.getSource();
		        Object[] options = {"Save", "Don't Save", "Cancel"};
		        int result = JOptionPane.showOptionDialog(dEditor, "Would you like to save any changes?", "Dido Editor",
		        		 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);	
		        	if (result == 0) {		        		
		        		File reader = file.getSelectedFile();
						if (x.getSource().equals(menuSaveAs) || reader == null) {
							if (file.showSaveDialog(DidoEditor.this) != JFileChooser.APPROVE_OPTION) 
								return;
								reader = file.getSelectedFile();
							}
							try {
								FileWriter writer = new FileWriter(reader);
								writer.write(textArea.getText());
								writer.close();
								DidoEditor.this.setTitle("Dido's Editor: " + file.getName());
							} catch (IOException a) {
								JOptionPane.showMessageDialog(DidoEditor.this, "File can't be saved");
							}
		        		
		        		
		        	}
		        	else if (result == 1) {
		        		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        	}
		        	else if (result == 2) {
		        		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		        	}
		        	
		        }
		    
		});
		
		textArea = new JTextArea();
		textArea.setSize(300,300);
		setContentPane(textArea);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menuFile);
		
		JMenuItem menuOpen = new JMenuItem("Open");
		menuOpen.setMnemonic(KeyEvent.VK_O);
		menuOpen.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent op) {
				if (file.showOpenDialog(DidoEditor.this) == JFileChooser.APPROVE_OPTION) {
					File f = file.getSelectedFile();
					try {
						FileReader reader = new FileReader(f);
						int filelength = (int) f.length();
						char[] buffer = new char[filelength];
						
						reader.read(buffer, 0, filelength);
						reader.close();
						textArea.setText(new String(buffer));
						setTitle("Dido's Editor: " + f.getName());
					} catch (IOException x) {
						JOptionPane.showMessageDialog(DidoEditor.this, "File couldn't be opened");
					}
				}
			}
		} 
		); 
		menuFile.add(menuOpen);
		
		menuSaveAs = new JMenuItem("Save As");
		JMenuItem menuSave = new JMenuItem("Save");
		
		ActionListener save = 
		new ActionListener() {
			public void actionPerformed(ActionEvent x) {
				File reader = file.getSelectedFile();
				if (x.getSource().equals(menuSaveAs) || reader == null) {
					if (file.showSaveDialog(DidoEditor.this) != JFileChooser.APPROVE_OPTION) 
						return;
						reader = file.getSelectedFile();
					}
					try {
						FileWriter writer = new FileWriter(reader);
						writer.write(textArea.getText());
						writer.close();
						DidoEditor.this.setTitle("Dido's Editor: " + file.getName());
					} catch (IOException a) {
						JOptionPane.showMessageDialog(DidoEditor.this, "File can't be saved");
					}
				}
			};
			menuSave.addActionListener(save);
			menuSaveAs.addActionListener(save);
			
			menuFile.add(menuSave);
			menuFile.add(menuSaveAs);
			
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		
		JMenuItem menuCut = new JMenuItem("Cut");
		menuCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent cu) {
				textArea.cut();
			}
		});
		menuEdit.add(menuCut);
		
		JMenuItem menuCopy = new JMenuItem("Copy");
		menuCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				textArea.copy();
			}
		});
		menuEdit.add(menuCopy);
		
		JMenuItem menuPaste = new JMenuItem("Paste");
		menuPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent p) {
				textArea.paste();
			}
		});
		menuEdit.add(menuPaste);
		
		find = new FindReplaceDialog(editor);
		JMenuItem menuFnR = new JMenuItem("Find/Replace");
		menuFnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent fr) {
				find.setVisible(true);
			}
		});
		menuEdit.add(menuFnR);
		
		JMenu menuCount = new JMenu("Counter");
		menuBar.add(menuCount);
		
		JMenuItem menuWord = new JMenuItem("Word Count"); //Solution to string/word counting
		
		menuWord.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent wc) {

				int stringCounter = 0;
				
				if (textArea.getSelectedText() == null) {
		
					String msg = textArea.getText().trim();
					String[] string = msg.split("\\s+");
					stringCounter = stringCounter + string.length;
				}
				
				else {
				String msg = textArea.getSelectedText().trim();
				String[] string = msg.split("\\s+");
         
				stringCounter = stringCounter + string.length;
			}
				JOptionPane.showMessageDialog(editor, "The word count is " + stringCounter);
			} 
			
		});
		menuCount.add(menuWord);
		
		JMenuItem menuChar = new JMenuItem("Character Count"); //Solution to character count
		menuChar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent cc) {
				int charCounter = 0;
			if (textArea.getSelectedText() == null)	
			{
				String msg = textArea.getText();
				for (int i = 0; i < msg.length(); i++) {
				      if (Character.isSpace(msg.charAt(i))) {
				    	  charCounter = charCounter + 0;
				      }
				      else {
				    	  charCounter = charCounter + 1;
				      }	      
				}	  
			}
			else {
				String msg = textArea.getSelectedText();
				for (int i = 0; i < msg.length(); i++) {
					if (Character.isSpace(msg.charAt(i))) {
						charCounter = charCounter + 0;
					}
					else {
						charCounter = charCounter + 1;
					}
				}
				
			}
			JOptionPane.showMessageDialog(editor, "The character count is " + charCounter);
			}
			
		});
		menuCount.add(menuChar);
		
		

	}
}
