package minedit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.SwingConstants;


/** 
 * A modification of the code by Dr. Edward Brown.
 * 
 * Dialog find/replace for edit menu of mini-editor application. Constructor takes
 * editor pane instance from the main editor JFrame so it can make text changes directly
 * on the other Container.
 * Possible exercise: ask students to consider other ways of connecting dialog with editor 
 * pane in other Container.
 * 
 * Some of the indenting and code block organization by WindowBuilder generated code is 
 * bizarre.
 * 
 * @version 2014-3-4
 * 
 * @author brown
 *
 */
public class FindReplaceDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField findField;
	private JTextField replaceField;

	private JEditorPane editorPane;

	/**
	 * Launch the application.
	 * Left main here for debugging purposes. Empty non-visible editorpane
	 */
	public static void main(String[] args) {
		try {
			FindReplaceDialog dialog = new FindReplaceDialog(new JEditorPane());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FindReplaceDialog(JEditorPane editorPane) {
		setResizable(false);
		this.editorPane = editorPane;
		ActionListener fractionListener = new FindReplaceActionListener();

		setTitle("Find / Replace");
		setBounds(100, 100, 446, 126);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
						contentPanel.setLayout(new GridLayout(2, 2, 0, 0));
				
						JLabel lblNewLabel = new JLabel("Find:");
						lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						lblNewLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
						lblNewLabel.setAlignmentY(Component.TOP_ALIGNMENT);
						contentPanel.add(lblNewLabel);
		
		
				findField = new JTextField();
				findField.setAlignmentY(Component.TOP_ALIGNMENT);
				findField.setAlignmentX(Component.LEFT_ALIGNMENT);
				findField.setText("");
				contentPanel.add(findField);
				findField.setColumns(10);
			
				
				
						JLabel lblNewLabel_1 = new JLabel("Replace With:");
						lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
						lblNewLabel_1.setHorizontalTextPosition(SwingConstants.RIGHT);
						lblNewLabel_1.setAlignmentY(Component.TOP_ALIGNMENT);
						contentPanel.add(lblNewLabel_1);
		
				replaceField = new JTextField();
				replaceField.setAlignmentY(Component.TOP_ALIGNMENT);
				replaceField.setAlignmentX(Component.LEFT_ALIGNMENT);
				replaceField.setText("");
				contentPanel.add(replaceField);
				replaceField.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton findButton = new JButton("Find");
				findButton.addActionListener(fractionListener);
				buttonPane.add(findButton);
				getRootPane().setDefaultButton(findButton);
			}
			{
				JButton btnReplace = new JButton("Replace");
			    btnReplace.addActionListener(fractionListener);
			    buttonPane.add(btnReplace);
			}
			{
				JButton btnReplaceAll = new JButton("Replace All");
				btnReplaceAll.addActionListener(fractionListener);
				buttonPane.add(btnReplaceAll);
			}
			{
				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// close the window
						// FindReplaceDialog.this.setVisible(false);
						setVisible(false);
					}
				});
				buttonPane.add(closeButton);
			}
		}

		
	}
	
	/**
	 * Common listener for Replace/Find action buttons.
	 * 
	 *  @author brown
	 */
	class FindReplaceActionListener implements ActionListener {

		private String findItem;
		private String replaceItem;
		@Override
		public void actionPerformed(ActionEvent e) {
			findItem = findField.getText();
			replaceItem = replaceField.getText();
			switch( e.getActionCommand() ){
			case "Replace All":
				editorPane.setText(editorPane.getText().replaceAll(findItem, replaceItem));
				break;
			case "Find":
				try {
					findnext();
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "Replace":				
				if(editorPane.getSelectedText() != null)
					if( editorPane.getSelectedText().equals(findItem))
						editorPane.replaceSelection(replaceItem);
		        try {
					findnext();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        break;
			}
			// debugging: 
			// System.out.println(e);

		}
	    /**
	     * 
	     * Find the location of the next occurrence of the findItem- string in the editorpane
	     * and make it the editorPane's current selection. This only searches from the current
	     * selection checks to end-of-text; on hitting the end of the text it pops up a message
	     * dialog warning, and places the selection at the beginning
	     * of the editor pane.  This is not the best UI semantics, but is an easy way to do 
	     * something that makes sense.
	     * @throws UnsupportedEncodingException 
	     * @throws FileNotFoundException 
	     * 
	     */
	    private void findnext() throws FileNotFoundException, UnsupportedEncodingException {
	        int lpos = editorPane.getSelectionEnd();
	        int index = editorPane.getText().indexOf(findItem, lpos);
	        // debugging: System.out.println(index);
	        if (index < 0) {
	            JOptionPane.showMessageDialog(FindReplaceDialog.this, findItem + ": End of text reached");
	            editorPane.select(0, 0);
	        } else {
	            editorPane.select(index, index + findItem.length());
	            PrintWriter out = new PrintWriter("dido.txt", findField.getText());
	            out.println(findField.getText());
	            out.close();
	            editorPane.setFocusable(true);
	        }
	    }
	}

}
