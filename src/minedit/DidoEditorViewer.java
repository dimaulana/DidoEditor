/**
 * 
 *
 * @author Dido Maulana
 * @version 2015-09-11
 */

package minedit;



import java.awt.EventQueue;

public class DidoEditorViewer {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					DidoEditor frame = new DidoEditor();
					frame.setVisible(true);
	
			}
	});
}
}